'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Cohort View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Gehrke
 */

app.controller('CohortController', function ($scope, ConnectionService) {

    //CRUD function calls for cohorts via ConnectionService
    $scope.cohorts = ConnectionService.getCohorts;
    $scope.createCohort = ConnectionService.createCohort;
    $scope.updateCohort = ConnectionService.updateCohort;
    $scope.deleteCohort = ConnectionService.deleteCohort;
    $scope.cohortsLastUpdateTime = ConnectionService.getCohortsLastUpdateTime;

    // set active selected cohort
    $scope.setSelectedCohort = function (cohort) {
        $scope.cohort = angular.copy(cohort);
        console.log('Selected Cohort:', $scope.cohort);
    };
});
