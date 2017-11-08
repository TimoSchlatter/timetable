'use strict';

app.controller('AlertController', function ($scope, AlertService) {



    $scope.closeAlert = function () {
       AlertService.clear();
    }

});