'use strict';

app.controller('CohortController', function ($scope, $http) {

    var url = 'http://localhost:49999/cohorts/';

    var getData = function () {
        $http.get(url).then(function successCallback(response) {
            $scope.cohorts = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedCohort = function (cohort) {
        $scope.cohort = angular.copy(cohort);
    };

    $scope.createData = function () {
        $http.put(url + this.cohort.id, JSON.stringify(this.docent))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(url, JSON.stringify(this.cohort))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(url + this.cohort.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });

    };
});
