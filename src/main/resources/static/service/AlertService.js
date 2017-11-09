
    app.factory('AlertService', ['$rootScope', '$timeout', function ($rootScope) {

        var alertService = {};

        // clear alert after routing
        $rootScope.$on('$stateChangeSuccess', function () {
            alertService.clear();
        });

        // alerts array on root scope so that all controllers can access it
        $rootScope.alerts = [];
        $rootScope.alertEvents = [];
        return {

            add: function (type, msg) {
                $rootScope.alerts.push({
                    'type': type, 'msg': msg, close: function () {
                        return alertService.closeAlert(this);
                    }
                });
                $('#modalAlert').modal('show');
                // If you want alerts to disappear automatically after few seconds.
                // $timeout(function(){
                //     alertService.closeAlert(this);
                // }, 5000);
            },

            addEventAlert: function (type, msg) {

                $rootScope.alertEvents.push({
                    'type': type, 'msg': msg, close: function () {
                        return alertService.closeAlert(this);
                    }
                });
                $('#modalEventAlert').modal('show');
                // If you want alerts to disappear automatically after few seconds.
                // $timeout(function(){
                //     alertService.closeAlert(this);
                // }, 5000);
            },
            closeAlert: function (alert) {
                this.closeAlertIdx($rootScope.alerts.indexOf(alert));
            },
            closeAlertIdx: function (index) {
                return $rootScope.alerts.splice(index, 1);
            },
            clear: function () {
                $rootScope.alerts = [];
                $rootScope.alertEvents = [];
            }
        };
    }]);