'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Seminar View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Gehrke
 */

app.controller('SeminarController', function ($scope, ConnectionService) {

    $scope.seminars = ConnectionService.getSeminars;
    $scope.createSeminar = ConnectionService.createSeminar;
    $scope.updateSeminar = ConnectionService.updateSeminar;
    $scope.deleteSeminar = ConnectionService.deleteSeminar;
    $scope.seminarTypes = ConnectionService.getSeminarTypes;
    $scope.seminarsLastUpdateTime = ConnectionService.getSeminarsLastUpdateTime;

    $scope.setSelectedSeminar = function (seminar) {
        $scope.seminar = angular.copy(seminar);
        console.log('Selected Seminar:', $scope.seminar);
    };
});