'use strict';

app.controller('SubjectController', function ($scope, $http, ConnectionService) {

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

    $scope.setSelectedSubject = function (subject) {
        $scope.subject = angular.copy(subject);
        console.log('Selected Subject:', $scope.subject);
    };

    $scope.subjects = ConnectionService.getSubjects;
    $scope.createSubject = ConnectionService.createSubject;
    $scope.updateSubject = ConnectionService.updateSubject;
    $scope.deleteSubject = ConnectionService.deleteSubject;

});