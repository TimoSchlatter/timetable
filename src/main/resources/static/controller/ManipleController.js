'use strict';

app.controller('ManipleController', function ($scope, $http, ConnectionService) {

    $scope.cohorts = ConnectionService.getCohorts;
    /* Sort centuries to get all by default selection */
    var collectAllManiple = function () {
        $scope.cohortsAdvanced = angular.copy($scope.cohorts);
        var allManiples = [];
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            allManiples = allManiples.concat(cohort.maniples);
        });
        $scope.cohortsAdvanced.unshift({name: "Alle", maniples: allManiples});
    };

    var setModalSelectedCohort = function (id) {
        $scope.modalSelectedCohort = undefined;
        angular.forEach($scope.cohorts, function (cohort) {
            angular.forEach(cohort.maniples, function (maniple) {
                if (id === maniple.id) {
                    $scope.modalSelectedCohort = cohort;
                }
            });
        });
    };

    // var getData = function () {
    //     $http.get(cohortsUrl).then(function successCallback(response) {
    //         $scope.cohorts = response.data;
    //         collectAllManiple();
    //         $scope.selectedCohort = $scope.cohortsAdvanced[0];
    //     }, function errorCallback(response) {
    //         console.log(response.statusText);
    //     });
    // };
    //
    // getData();

    $scope.setSelectedManiple = function (maniple) {
        $scope.maniple = angular.copy(maniple);
        setModalSelectedCohort(maniple.id);
        console.log('Selected Maniple:', $scope.maniple);
    };

    // $scope.createData = function () {
    //     this.maniple.type='maniple';
    //     $http.post(cohortsUrl + this.modalSelectedCohort.id + '/addManiple', JSON.stringify(this.maniple))
    //         .then(function successCallback(data) {
    //             console.log(data);
    //             getData();
    //         }, function errorCallback(data, status, header, config) {
    //             console.log(data, status, header, config);
    //         });
    // };
    //
    // $scope.updateData = function () {
    //     $http.put(maniplesUrl + this.maniple.id, JSON.stringify(this.maniple))
    //         .then(function successCallback(data) {
    //             console.log(data);
    //             getData();
    //         }, function errorCallback(data, status, header, config) {
    //             console.log(data, status, header, config);
    //         });
    // };
    //
    // $scope.deleteData = function () {
    //     $http.delete(cohortsUrl + this.modalSelectedCohort.id + '/deleteManiple/' + this.maniple.id)
    //         .then(function successCallback(data) {
    //             console.log(data);
    //             getData();
    //         }, function errorCallback(data, status, header, config) {
    //             console.log(data, status, header, config);
    //         });
    // };
});