'use strict';

app.controller('CourseController', function ($scope, ConnectionService) {

    $scope.courses = ConnectionService.getCourses;
    $scope.createCourse = ConnectionService.createCourse;
    $scope.updateCourse = ConnectionService.updateCourse;
    $scope.deleteCourse = ConnectionService.deleteCourse;

    $scope.setSelectedCourse = function (course) {
        $scope.course = angular.copy(course);
        console.log('Selected Course:', $scope.course);
    };

});