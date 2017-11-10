
    app.factory('AlertService', ['$rootScope', '$timeout', function ($rootScope) {

        var alertService = {};

        // Globale Variable zur Bereinigung von Hinweismeldungen
        $rootScope.$on('$stateChangeSuccess', function () {
            alertService.clear();
        });

        // Hier werden alle Hinweismeldungen zwischengespeichert
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
            },

            addEventAlert: function (type, msg) {

                $rootScope.alertEvents.push({
                    'type': type, 'msg': msg, close: function () {
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
            clear: function () {
                $rootScope.alerts = [];
                $rootScope.alertEvents = [];
            }
        };
    }]);