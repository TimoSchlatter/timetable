'use strict'

app.controller('DashboardController', function($scope , $http, AlertService) {

    $scope.generateData = function () {
        $http.get('http://localhost:49999/generateData').then(function successCallback(response) {
            console.log(response.data);
            AlertService.add('Erfolg', 'Daten wurden generiert!');
        }, function errorCallback(response) {
            console.error(response.statusText);
            AlertService.add('Error', 'Datengenerierung fehlgeschlagen!')
        });
    }



});