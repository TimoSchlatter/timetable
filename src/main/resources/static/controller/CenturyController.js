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
    };

    getData();

    $scope.setSelectedCentury = function (century) {
        $scope.century = angular.copy(century);
    };

    $scope.setSelectedManiple = function () {
        $scope.centuries = angular.copy($scope.maniple);
    };

    $scope.setSelectedCohort = function () {
        $scope.maniples = angular.copy($scope.cohort);
    };

    $scope.createData = function () {
        $http.post(maniplesUrl + this.maniple.id + '/addCentury', JSON.stringify(this.century))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(centuriesUrl + this.century.id, JSON.stringify(this.century))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(maniplesUrl + this.maniple.id + '/deleteCentury/' + this.century.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});