'use strict';

app.controller('SeminarController', function ($scope, $http, ConnectionService) {

    var seminarTypesUrl = 'http://localhost:49999/seminartypes/';

    $http.get(seminarTypesUrl).then(function successCallback(response) {
        $scope.seminarTypes = response.data;
    }, function errorCallback(response) {
        console.log(response.statusText);
    });

    $scope.setSelectedSeminar = function (seminar) {
        $scope.seminar = angular.copy(seminar);
        console.log('Selected Seminar:', $scope.seminar);
    };

    $scope.seminars = ConnectionService.getSeminars;
    $scope.createSeminar = ConnectionService.createSeminar;
    $scope.updateSeminar = ConnectionService.updateSeminar;
    $scope.deleteSeminar = ConnectionService.deleteSeminar;

});