'use strict'

app.controller('DashboardController', function($scope , $http) {

    $scope.generateData = function () {
        $http.get('http://localhost:49999/generateData').then(function successCallback(response) {
            console.log(response.data);
        }, function errorCallback(response) {
            console.error(response.statusText);
        });
    }

});