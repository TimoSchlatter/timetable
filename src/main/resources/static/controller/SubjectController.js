'use strict';

app.controller('SubjectController', function ($scope, $http, $filter) {

    var subjectsUrl = 'http://localhost:49999/subjects/';
    var subjectTypesUrl = 'http://localhost:49999/subjecttypes/';

    $http.get(subjectTypesUrl).then(function successCallback(response) {
        $scope.subjectTypes = response.data;
        $scope.subjectTypesAdvanced = [];
        angular.forEach(response.data, function (value, key) {
            $scope.subjectTypesAdvanced.push({key: key, name: value});
        });
        $scope.subjectTypesAdvanced.unshift({key: '', name: "Alle"});
        $scope.selectedSubjectType = $scope.subjectTypesAdvanced[0];
    }, function errorCallback(response) {
        console.log(response.statusText);
    });

    var getData = function () {
        $http.get(subjectsUrl).then(function successCallback(response) {
            $scope.subjects = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedSubject = function (subject) {
        $scope.subject = angular.copy(subject);
        console.log($scope.subject.id);
    };

    $scope.createData = function () {
        $http.post(subjectsUrl, JSON.stringify(this.subject))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(subjectsUrl + this.subject.id, JSON.stringify(this.subject))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(subjectsUrl + this.subject.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});