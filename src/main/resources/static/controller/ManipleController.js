'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Maniple View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Jacobsen
 */

app.controller('ManipleController', function ($scope, ConnectionService) {

    // CRUD function calls for cohorts via ConnectionService
    $scope.cohorts = ConnectionService.getCohorts;
    $scope.createManiple = ConnectionService.createManiple;
    $scope.updateManiple = ConnectionService.updateManiple;
    $scope.deleteManiple = ConnectionService.deleteManiple;
    $scope.maniplesLastUpdateTime = ConnectionService.getManiplesLastUpdateTime;

    $scope.$watch('cohorts()', function () {
        var lastSelectedCohort = ($scope.cohortsAdvanced ? $scope.cohortsAdvanced.indexOf($scope.selectedCohort) : 0);
        buildCohortsAdvanced($scope.cohorts());
        $scope.selectedCohort = $scope.cohortsAdvanced[lastSelectedCohort];
    });

    // Sort centuries to get all by default selection
    var buildCohortsAdvanced = function (cohorts) {
        $scope.cohortsAdvanced = angular.copy(cohorts);
        var allManiples = [];
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            allManiples = allManiples.concat(cohort.maniples);
        });
        $scope.cohortsAdvanced.unshift({name: "Alle", maniples: allManiples});
    };

    // set active selected maniple
    $scope.setSelectedManiple = function (maniple) {
        $scope.maniple = angular.copy(maniple);
        setModalSelectedCohort(maniple.id);
        console.log('Selected Maniple:', $scope.maniple);
    };

    // set cohort value in update modal
    var setModalSelectedCohort = function (id) {
        $scope.modalSelectedCohort = undefined;
        angular.forEach($scope.cohorts(), function (cohort) {
            angular.forEach(cohort.maniples, function (maniple) {
                if (id === maniple.id) {
                    $scope.modalSelectedCohort = cohort;
                }
            });
        });
    };
});