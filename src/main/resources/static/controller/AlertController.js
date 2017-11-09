'use strict';

app.controller('AlertController', function ($scope, AlertService, ConnectionService) {

    $scope.closeAlert = function () {
       AlertService.clear();
    }

    $scope.setIgnore = function () {
        AlertService.clear();
        ConnectionService.createEventInsteadOfCollision();
    }
});