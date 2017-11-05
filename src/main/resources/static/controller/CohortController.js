'use strict';

app.controller('CohortController', function ($scope, $http) {

    var cohortsUrl = 'http://localhost:49999/cohorts/';

    var getData = function () {
        $http.get(cohortsUrl).then(function successCallback(response) {
            $scope.cohorts = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedCohort = function (cohort) {
        $scope.cohort = angular.copy(cohort);
        console.log('Selected Cohort:', $scope.cohort);
    };

    $scope.createData = function () {
        this.cohort.type='cohort';
        $http.post(cohortsUrl, JSON.stringify(this.cohort))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(cohortsUrl + this.cohort.id, JSON.stringify(this.cohort))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(cohortsUrl + this.cohort.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});
