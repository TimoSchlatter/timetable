'use strict';

app.controller('CourseController', function ($scope, $http) {

    var coursesUrl = 'http://localhost:49999/courses/';

    var getData = function () {
        $http.get(coursesUrl).then(function successCallback(response) {
            $scope.courses = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedCourse = function (course) {
        $scope.course = angular.copy(course);
        console.log('Selected Course:', $scope.course);
    };

    $scope.createData = function () {
        this.course.type='course';
        $http.post(coursesUrl, JSON.stringify(this.course))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(coursesUrl + this.course.id, JSON.stringify(this.course))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(coursesUrl + this.course.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});