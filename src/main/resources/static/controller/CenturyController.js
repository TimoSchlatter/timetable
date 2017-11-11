'use strict';

app.controller('CenturyController', function ($scope, ConnectionService) {

    $scope.cohorts = ConnectionService.getCohorts;
    $scope.updateCentury = ConnectionService.updateCentury;
    $scope.deleteCentury = ConnectionService.deleteCentury;
    $scope.centuriesLastUpdateTime = ConnectionService.getCenturiesLastUpdateTime;

    $scope.createCentury = function () {
        this.century.type = 'century';
        ConnectionService.createCentury(this.modalSelectedManiple.id, this.century);
    };

    $scope.$watch('cohorts()', function () {
        buildCohortsAdvanced($scope.cohorts());
        $scope.setSelectedValues();
    });

    /* Sort centuries to get all by default selection */
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

    $scope.setSelectedValues = function () {
        if (!$scope.selectedCohort) {
            $scope.selectedCohort = $scope.cohortsAdvanced[0];
        }
        $scope.selectedManiple = $scope.selectedCohort.maniples[0];
    };

    $scope.setSelectedCentury = function (century) {
        $scope.century = angular.copy(century);
        setModalSelectedValues(century.id);
        console.log('Selected Century:', $scope.century);
    };
});