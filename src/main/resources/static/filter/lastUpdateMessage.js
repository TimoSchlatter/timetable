'use strict';

app.filter('lastUpdateMessage', function ($filter) {
    return function (date) {
        var lastUpdateTime = $filter('date')(date, 'HH:mm:ss');
        return 'Die Daten wurden zuletzt um ' + lastUpdateTime + ' Uhr mit dem Server der Nordakademie synchronisiert'
    };
});