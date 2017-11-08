'use strict'

app.controller('DashboardController', function($scope , $http, AlertService, ConnectionService) {

    $scope.eventsSource = [];
    $scope.events = [];

    $scope.alertEventOnClick = function (event) {
        $('#modalUpdate').modal('show');
        $scope.selectedEventId = event.id;
    };

    $scope.uiConfig = {
        calendar:{
            monthNames: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
            monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
            dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
            dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
            locale: 'de',
            height: 500,
            editable: true,
            header:{
                left: 'month basicWeek basicDay agendaWeek agendaDay',
                center: 'title',
                right: 'today prev,next'
            },
            eventClick: $scope.alertEventOnClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize,
        }

    };

    $scope.generateData = function () {
        $http.get('http://localhost:49999/generateData').then(function successCallback(response) {
            console.log(response.data);
            AlertService.add('Erfolg', 'Daten wurden generiert!');
        }, function errorCallback(response) {
            console.error(response.statusText);
            AlertService.add('Error', 'Datengenerierung fehlgeschlagen!')
        });
    };

    var convert = function (events2) {
        for(var i=0;i<events2.length;i++) {
            var event = {
                id: events2[i].id,
                title: events2[i].subject.module.title,
                start: events2[i].date + 'T' + events2[i].startTime,
                end: events2[i].date + 'T' + events2[i].endTime,
                allDay: false,
                editable: false
            }
            $scope.events.push(event);
        }
    };

    $http.get('http://localhost:49999/events').then(function successCallback(response) {
        console.log(response.data);
        var events2 = response.data;
        convert(events2);
    }, function errorCallback(response) {
        console.error(response.statusText);
    });
    $scope.eventsSource = [$scope.events];


    


});