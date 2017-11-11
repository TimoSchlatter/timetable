'use strict';

app.controller('AlertController', function ($scope, AlertService, ConnectionService) {

    // function call for close alerts
    $scope.closeAlert = function () {
       AlertService.clear();
    }

    // function call for ignore collision to create or update event
    $scope.setIgnore = function () {
        AlertService.clear();
        ConnectionService.createEventInsteadOfCollision();
    }
});