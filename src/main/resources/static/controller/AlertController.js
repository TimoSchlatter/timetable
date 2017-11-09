'use strict';

app.controller('AlertController', function ($scope, AlertService, $rootScope) {

    $scope.closeAlert = function () {
       AlertService.clear();
    }

    $scope.setIgnore = function () {
        $rootScope.ignoreCollisions = true;
    }
});