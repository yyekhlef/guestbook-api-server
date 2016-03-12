var redisApp = angular.module('redis', ['ui.bootstrap']);

/**
 * Constructor
 */
function RedisController() {
}

RedisController.prototype.onRedis = function () {
    var data = {
        content: this.scope_.msg
    };
    this.scope_.messages.unshift(data);
    this.scope_.msg = "";
    this.http_.post("/api/v1/guestbook/messages", data)
    .success(angular.bind(this, function (data) {
        this.scope_.redisResponse = "Updated.";
    }));
};

redisApp.controller('RedisCtrl', function ($scope, $http, $location) {
    $scope.controller = new RedisController();
    $scope.controller.scope_ = $scope;
    $scope.controller.location_ = $location;
    $scope.controller.http_ = $http;

    $scope.controller.http_.get("/api/v1/guestbook/messages")
        .success(function (data, status) {
            console.log("status code: " + status)
            console.log("received " + data.length + " elements");
            $scope.messages = data;
        });
});
