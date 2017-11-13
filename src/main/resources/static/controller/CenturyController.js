'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Century View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Jacobsen and Jonas Gehrke
 */

app.controller('CenturyController', function ($scope, ConnectionService) {

    // RUD Calls for Centuries via ConnectionService
    $scope.cohorts = ConnectionService.getCohorts;
    $scope.updateCentury = ConnectionService.updateCentury;
    $scope.deleteCentury = ConnectionService.deleteCentury;
    $scope.centuriesLastUpdateTime = ConnectionService.getCenturiesLastUpdateTime;

    // Create Call via ConnectionService
    $scope.createCentury = function () {
        this.century.type = 'century';
        ConnectionService.createCentury(this.modalSelectedManiple.id, this.century);
    };

    // Watcher for cohort-array to build all centuries and set selectable dropdowns
    $scope.$watch('cohorts()', function () {
        var lastSelectedCohort = ($scope.cohortsAdvanced ? $scope.cohortsAdvanced.indexOf($scope.selectedCohort) : 0);
        var lastSelectedManiple = ($scope.cohortsAdvanced ? $scope.selectedCohort.maniples.indexOf($scope.selectedManiple) : 0);
        buildCohortsAdvanced($scope.cohorts());
        $scope.selectedCohort = $scope.cohortsAdvanced[lastSelectedCohort];
        $scope.selectedManiple = $scope.selectedCohort.maniples[lastSelectedManiple];
    });

    // Sort centuries to get all by default selection
    var buildCohortsAdvanced = function () {
        $scope.cohortsAdvanced = angular.copy($scope.cohorts());
        var allCenturies = [];
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            var cohortCenturies = [];
            angular.forEach(cohort.maniples, function (maniple) {
                cohortCenturies = cohortCenturies.concat(maniple.centuries);
            });
            cohort.maniples.unshift({name: "Alle", centuries: cohortCenturies});
            allCenturies = allCenturies.concat(cohortCenturies);
        });
        $scope.cohortsAdvanced.unshift({name: "Alle", maniples: [{name: "Alle", centuries: allCenturies}]});
    };

    // set cohort and maniple value in update modal
    var setModalSelectedValues = function (id) {
        $scope.modalSelectedCohort = undefined;
        $scope.modalSelectedManiple = undefined;
        angular.forEach($scope.cohorts(), function (cohort) {
            angular.forEach(cohort.maniples, function (maniple) {
                angular.forEach(maniple.centuries, function (century) {
                    if (id === century.id) {
                        $scope.modalSelectedCohort = cohort;
                        $scope.modalSelectedManiple = maniple;
                    }
                });
            });
        });
    };

    $scope.setSelectedCentury = function (century) {
        $scope.century = angular.copy(century);
        setModalSelectedValues(century.id);
        console.log('Selected Century:', $scope.century);
    };
});