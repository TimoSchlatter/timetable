'use strict';

app.controller('LectureController', function ($scope, $http) {

    var lecturesUrl = 'http://localhost:49999/lectures/';

    var getData = function () {
        $http.get(lecturesUrl).then(function successCallback(response) {
            $scope.rooms = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedCourse = function (course) {
        $scope.lecture = angular.copy(lecture);
        console.log($scope.lecture.id);
    };

    $scope.createData = function () {
        $http.post(lecturesUrl, JSON.stringify(this.lecture))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(lecturesUrl + this.lecture.id, JSON.stringify(this.lecture))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(lecturesUrl + this.lecture.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});