'use strict';

/*
 AngularJS Service
 It is used to organize the alerts across the web application.
 @author Jonas Gehrke
 */

app.factory('AlertService', ['$rootScope', '$timeout', function ($rootScope) {

    var alertService = {};

    // global variable for clearing alerts
    $rootScope.$on('$stateChangeSuccess', function () {
        alertService.clear();
    });


    $rootScope.alerts = [];
    $rootScope.alertEvents = [];
    return {

        // add an alert to rootScope element
        add: function (type, msg) {
            $rootScope.alerts.push({
                'type': type, 'msg': msg, close: function () {
                    return alertService.closeAlert(this);
                }
            });
            $('#modalAlert').modal('show');
        },

        // add collison alert to rootScope element
        addEventAlert: function (type, data) {
            $rootScope.alertEvents.push({
                'type': type, 'msg': data, close: function () {
                    return alertService.closeAlert(this);
                }
            });
            $('#modalEventAlert').modal('show');
        },
        closeAlert: function (alert) {
            this.closeAlertIdx($rootScope.alerts.indexOf(alert));
        },
        closeAlertIdx: function (index) {
            return $rootScope.alerts.splice(index, 1);
        },

        // clear alert arrays
        clear: function () {
            $rootScope.alerts = [];
            $rootScope.alertEvents = [];
        }
    };
}]);