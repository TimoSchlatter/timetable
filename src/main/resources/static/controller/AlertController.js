'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Alert Service in the index.html.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Gehrke
 */

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