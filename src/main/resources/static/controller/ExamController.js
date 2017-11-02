'use strict';

app.controller('ExamController', function ($scope, $http) {

    var examsUrl = 'http://localhost:49999/exams/';

    var getData = function () {
        $http.get(examsUrl).then(function successCallback(response) {
            $scope.exams = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedExam = function (exam) {
        $scope.exam = angular.copy(exam);
        console.log($scope.exam.id);
    };

    $scope.createData = function () {
        $http.post(examsUrl, JSON.stringify(this.exam))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(examsUrl + this.exam.id, JSON.stringify(this.exam))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(examsUrl + this.exam.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});