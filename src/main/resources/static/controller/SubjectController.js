'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Subject View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Jacobsen
 */

app.controller('SubjectController', function ($scope, $http, ConnectionService) {

    $scope.subjects = ConnectionService.getSubjects;
    $scope.createSubject = ConnectionService.createSubject;
    $scope.updateSubject = ConnectionService.updateSubject;
    $scope.deleteSubject = ConnectionService.deleteSubject;
    $scope.subjectTypes = ConnectionService.getSubjectTypes;
    $scope.subjectsLastUpdateTime = ConnectionService.getSubjectsLastUpdateTime;

    $scope.$watch('subjectTypes()', function () {
        buildSubjectTypesAdvanced($scope.subjectTypes());
        $scope.selectedSubjectType = $scope.subjectTypesAdvanced[0];
    });

    var buildSubjectTypesAdvanced = function (subjectTypes) {
        $scope.subjectTypesAdvanced = [];
        angular.forEach(subjectTypes, function (value, key) {
            $scope.subjectTypesAdvanced.push({key: key, name: value});
        });
        $scope.subjectTypesAdvanced.unshift({key: '', name: "Alle"});
    };

    $scope.setSelectedSubject = function (subject) {
        $scope.subject = angular.copy(subject);
        if ($scope.subject.id) {
            $scope.setModulModel(subject);
            angular.forEach($scope.module(), function (modul) {
                if (modul.id === subject.module.id) {
                    $scope.subject.module = modul;
                }
            });
        }
        console.log('Selected Subject:', $scope.subject);
    };

    $scope.setModulModel = function (subject) {
        $scope.module = (subject.subjectType === 'SEMINAR' ? ConnectionService.getSeminars : ConnectionService.getCourses);
    };
});