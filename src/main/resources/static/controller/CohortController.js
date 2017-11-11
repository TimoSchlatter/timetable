'use strict';

app.controller('CohortController', function ($scope, ConnectionService) {

    $scope.cohorts = ConnectionService.getCohorts;
    $scope.createCohort = ConnectionService.createCohort;
    $scope.updateCohort = ConnectionService.updateCohort;
    $scope.deleteCohort = ConnectionService.deleteCohort;
    $scope.cohortsLastUpdateTime = ConnectionService.getCohortsLastUpdateTime;

    $scope.setSelectedCohort = function (cohort) {
        $scope.cohort = angular.copy(cohort);
        console.log('Selected Cohort:', $scope.cohort);
    };
});
