'use strict';

app.controller('CohortController', function ($scope, ConnectionService) {

    $scope.setSelectedCohort = function (cohort) {
        $scope.cohort = angular.copy(cohort);
        console.log('Selected Cohort:', $scope.cohort);
    };

    $scope.cohorts = ConnectionService.getCohorts;
    $scope.createCohort = ConnectionService.createCohort;
    $scope.updateCohort = ConnectionService.updateCohort;
    $scope.deleteCohort = ConnectionService.deleteCohort;

});
