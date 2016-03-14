var redisApp = angular.module('redis', ['ui.bootstrap']);

redisApp.directive('app', function(){
  return {
    restrict: 'E',
    template: '<div style="width: 50%; margin-left: 20px">' +
                  '<h2>Guestbook</h2>' +
                  '<form>' +
                      '<fieldset>' +
                          '<input ng-model="app.msg" placeholder="Messages" class="form-control" type="text" name="input"><br>'+
                          '<button type="button" class="btn btn-primary" ng-click="app.onRedis()">Submit</button>'+
                      '</fieldset>'+
                  '</form>'+
                  '<ul>'+
                      '<li ng-repeat="msg in app.messages track by $index">'+
                          '{{msg.content}}' +
                      '</li>' +
                  '</ul>' +
              '</div>',
      controller: AppController,
      controllerAs: 'app'
  }
})

function AppController($http){
      this.messages = [];

      $http.get("/api/v1/guestbook/messages")
      .success(function (data, status) {
          console.log("status code: " + status)
          console.log("received " + data.length + " elements");
          $scope.messages = data;
      });

      this.onRedis = function () {
          var data = {
              content: this.msg
          };
          this.messages.unshift(data);
          this.msg = "";
          $http.post("/api/v1/guestbook/messages", data)
          .success(angular.bind(this, function (data) {
              this.redisResponse = "Updated.";
          }));
      };
}
