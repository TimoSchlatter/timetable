'use strict';

app.controller('ManipleController', function ($scope, $http) {

    var cohortsUrl = 'http://localhost:49999/cohorts/';
    var maniplesUrl = 'http://localhost:49999/maniples/';

    var getData = function () {
        $http.get(cohortsUrl).then(function successCallback(response) {
            $scope.cohorts = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
        $http.get(maniplesUrl).then(function successCallback(response) {
            $scope.maniples = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedManiple = function (maniple) {
        $scope.maniple = angular.copy(maniple);
    };

    $scope.setSelectedCohort = function () {
        $scope.maniples = angular.copy($scope.cohort);
    };

    $scope.createData = function () {
        $http.post(maniplesUrl, JSON.stringify(this.maniple))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(maniplesUrl + this.maniple.id, JSON.stringify(this.maniple))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(maniplesUrl + this.maniple.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});