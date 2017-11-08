
    app.factory('AlertService', ['$rootScope', '$timeout', function ($rootScope, $timeout) {

        var alertService = {};

        // clear alert after routing
        $rootScope.$on('$stateChangeSuccess', function () {
            alertService.clear();
        });

        // alerts array on root scope so that all controllers can access it
        $rootScope.alerts = [];
        return {

            add: function (type, msg, section) {
                if (section == undefined) section = 1;  //default section number
                $rootScope.alerts.push({
                    'type': type, 'msg': msg, 'section': section, close: function () {
                        return alertService.closeAlert(this);
                    }
                });
                $('#modalAlert').modal('show');
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
            }
        };
    }]);