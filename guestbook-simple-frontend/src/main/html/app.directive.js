var guestbook = angular.module('guestbookApp.directive', ['ui.bootstrap', 'guestbookApp.config'])
    .directive('app', ['configuration', function (configuration) {
        return {
            restrict: 'E',
            template: '<div style="width: 50%; margin-left: 20px">' +
            '<h2>Guestbook <i>(version {{app.version}})</i></h2>' +
            '<form>' +
            '<fieldset>' +
            '<input ng-model="app.userName" placeholder="Username" class="form-control" type="text" name="input"><br>' +
            '<input ng-model="app.msg" placeholder="Messages" class="form-control" type="text" name="input"><br>' +
            '<button type="button" class="btn btn-primary" ng-click="app.storeInBackend()">Submit</button>' +
            '</fieldset>' +
            '</form>' +
            '<ul>' +
            '<li ng-repeat="msg in app.messages track by $index">' +
            '{{msg.content}} ' +
            '(publié par <b>{{msg.userName || "un inconnu"}}</b> <span class="text-info">datetime: {{msg.metadata.datetimeString}}, server: {{msg.metadata.apiServerName}}</span>)' +
            '</li>' +
            '</ul>' +
            '<p class="bg-danger"><i><b>{{app.backendResponse}}</b></i></p>' +
            '</div>',
            controllerAs: 'app',
            controller: AppController
        }
    }])

function AppController($http, configuration) {
    this.messages = [{content: "si tu vois ce message c'est que ça n'a pas fonctionné :("}];
    this.version = "unknown";
    var self = this;
    // console.log(configuration)
    var apiServerRootUrl = configuration.apiServerRootUrl
    // console.log(apiServerRootUrl)
    $http.get(apiServerRootUrl + "/api/v1/guestbook/messages")
        .then(
            function (response) {
                console.log("[/guestbook/messages] status code: " + response.status);
                console.log("[/guestbook/messages] received " + response.data.length + " elements");
                self.messages = response.data;
            },
            function (response) {
                console.log("[/guestbook/messages] status code: " + response.status);
                self.backendResponse = "Something is wrong with the backend :(";
            });

    $http.get(apiServerRootUrl + "/api/v1/version")
        .then(
            function (response) {
                console.log("[/version] status code: " + response.status);
                self.version = response.data.version;
            });

    this.storeInBackend = function () {
        console.log("storeInBackend: got content [" + this.msg + "] and userName[" + this.userName + "]");
        var postBody = {
            content: this.msg,
            userName: this.userName
        };
        this.msg = "";
        this.userName = "";
        $http.post("/api/v1/guestbook/messages", postBody)
            .then(
                function (response) {
                    self.messages.unshift(response.data);
                },
                function (response) {
                    console.log("[/guestbook/messages] status code: " + response.status);
                    self.backendResponse = "Houston, something went wrong while storing to the backend";
                }
            );
    };
}
