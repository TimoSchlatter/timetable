'use strict';

app.controller('SeminarController', function ($scope, $http) {

    var seminarUrl = 'http://localhost:49999/seminars/';

    var getData = function () {
        $http.get(seminarUrl).then(function successCallback(response) {
            $scope.seminars = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedDocent = function (seminar) {
        $scope.seminar = angular.copy(seminar);
        console.log($scope.seminar.id);
    };

    $scope.createData = function () {
        $http.post(seminarUrl, JSON.stringify(this.seminar))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(seminarUrl + this.seminar.id, JSON.stringify(this.seminar))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(seminarUrl + this.seminar.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});