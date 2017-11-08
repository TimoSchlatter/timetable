'use strict';

app.controller('ManipleController', function ($scope, ConnectionService) {

    $scope.cohorts = ConnectionService.getCohorts;

    $scope.$watch('cohorts()', function () {
        buildCohortsAdvanced($scope.cohorts());
        $scope.selectedCohort = $scope.cohortsAdvanced[0];
    });

    /* Sort centuries to get all by default selection */
    var buildCohortsAdvanced = function (cohorts) {
        $scope.cohortsAdvanced = angular.copy(cohorts);
        var allManiples = [];
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            allManiples = allManiples.concat(cohort.maniples);
        });
        $scope.cohortsAdvanced.unshift({name: "Alle", maniples: allManiples});
    };

    $scope.setSelectedManiple = function (maniple) {
        $scope.maniple = angular.copy(maniple);
        setModalSelectedCohort(maniple.id);
        console.log('Selected Maniple:', $scope.maniple);
    };

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