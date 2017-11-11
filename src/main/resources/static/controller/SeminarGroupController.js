'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the SeminarGroup View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Gehrke
 */

app.controller('SeminarGroupController', function ($scope, ConnectionService) {

    $scope.seminarGroups = ConnectionService.getSeminarGroups;
    $scope.createSeminarGroup = ConnectionService.createSeminarGroup;
    $scope.updateSeminarGroup = ConnectionService.updateSeminarGroup;
    $scope.deleteSeminarGroup = ConnectionService.deleteSeminarGroup;
    $scope.seminarGroupsLastUpdateTime = ConnectionService.getSeminarGroupsLastUpdateTime;

    $scope.setSelectedSeminarGroup = function (seminarGroup) {
        $scope.seminarGroup = angular.copy(seminarGroup);
        console.log('Selected SeminarGroup:', $scope.seminarGroup);
    };
});
