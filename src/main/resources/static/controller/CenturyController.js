'use strict';

app.controller('CenturyController', function ($scope, $http) {

    var cohortsUrl = 'http://localhost:49999/cohorts/';
    var maniplesUrl = 'http://localhost:49999/maniples/';
    var centuriesUrl = 'http://localhost:49999/centuries/';

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
        $http.get(centuriesUrl).then(function successCallback(response) {
            $scope.centuries = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedCentury = function (century) {
        $scope.century = angular.copy(century);
    };

    $scope.createData = function () {
        $http.post(maniplesUrl + manipleId + '/addCentury', JSON.stringify(this.century))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(maniplesUrl + this.century.id, JSON.stringify(this.century))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(centuriesUrl + this.century.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});