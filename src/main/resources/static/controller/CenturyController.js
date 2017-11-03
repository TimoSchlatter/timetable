'use strict';

app.controller('CenturyController', function ($scope, $http) {

    var cohortsUrl = 'http://localhost:49999/cohorts/';
    var maniplesUrl = 'http://localhost:49999/maniples/';
    var centuriesUrl = 'http://localhost:49999/centuries/';

    /* Sort centuries to get all by default selection */
    var collectAllCenturies = function () {
        $scope.cohortsAdvanced = angular.copy($scope.cohorts);
        var allCenturies = [];
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            var cohortCenturies = [];
            angular.forEach(cohort.maniples, function (maniple) {
                cohortCenturies = cohortCenturies.concat(maniple.centuries);
            });
            cohort.maniples.push({name: "Alle", centuries: cohortCenturies});
            allCenturies = allCenturies.concat(cohortCenturies);
        });
        $scope.cohortsAdvanced.push({name: "Alle", maniples: [{name: "Alle", centuries: allCenturies}]});
    };

    var setModalSelectedValues = function (id) {
        angular.forEach($scope.cohorts, function (cohort) {
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

    var getData = function () {
        $http.get(cohortsUrl).then(function successCallback(response) {
            $scope.cohorts = response.data;
            collectAllCenturies();
            $scope.setSelectedValues();
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedValues = function () {
        if (!$scope.selectedCohort) {
            $scope.selectedCohort = $scope.cohortsAdvanced[$scope.cohortsAdvanced.length - 1];
        }
        $scope.selectedManiple = $scope.selectedCohort.maniples[$scope.selectedCohort.maniples.length - 1];
    };

    $scope.setSelectedCentury = function (century) {
        $scope.century = angular.copy(century);
        if (century.id) {
            setModalSelectedValues(century.id);
        } else {
            $scope.modalSelectedCohort = undefined;
            $scope.modalSelectedManiple = undefined;
        }
    };

    $scope.createData = function () {
        $http.post(maniplesUrl + this.modalSelectedManiple.id + '/addCentury', JSON.stringify(this.century))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(centuriesUrl + this.century.id, JSON.stringify(this.century))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(maniplesUrl + this.modalSelectedManiple.id + '/deleteCentury/' + this.century.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});