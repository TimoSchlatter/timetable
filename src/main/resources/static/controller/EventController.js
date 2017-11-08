'use strict';

app.controller('EventController', function ($scope, $http, ConnectionService) {

    var cohortsUrl = 'http://localhost:49999/cohorts/';
    var eventsUrl = 'http://localhost:49999/events/';
    var subjectsUrl = 'http://localhost:49999/subjects/';
    var subjectTypesUrl = 'http://localhost:49999/subjecttypes/';

    /* Sort centuries to get all by default selection */
    var collectAllCenturies = function (cohorts) {
        $scope.cohortsAdvanced = angular.copy(cohorts);
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            angular.forEach(cohort.maniples, function (maniple) {
                maniple.centuries.unshift({name: "Alle"});
            });
            cohort.maniples.unshift({name: "Alle", centuries: [{name: "Alle"}]});
        });
        $scope.cohortsAdvanced.unshift({name: "Alle", maniples: [{name: "Alle", centuries: [{name: "Alle"}]}]});
    };

    $scope.rooms = ConnectionService.getRooms;
    $scope.docents = ConnectionService.getDocents;

    var getData = function () {
        $http.get(eventsUrl).then(function successCallback(response) {
            $scope.events = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
        $http.get(cohortsUrl).then(function successCallback(response) {
            collectAllCenturies(response.data);
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
        $http.get(subjectsUrl).then(function successCallback(response) {
            $scope.subjects = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
        $http.get(subjectTypesUrl).then(function successCallback(response) {
            $scope.subjectTypes = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    var resetGroups = function () {
        $scope.event.cohort = $scope.cohortsAdvanced[0];
        $scope.event.maniple = $scope.event.cohort.maniples[0];
        $scope.event.century = $scope.event.maniple.centuries[0];
    };

    $scope.changedCohort = function () {
        $scope.event.maniple = $scope.event.cohort.maniples[0];
        $scope.event.century = $scope.event.maniple.centuries[0];
    };

    $scope.changedManiple = function () {
        $scope.event.century = $scope.event.maniple.centuries[0];
    };

    $scope.setSelectedEvent = function (event) {
        $scope.event = angular.copy(event);
        console.log('Selected Event:', $scope.event);
        resetGroups();
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
            eventResize: $scope.alertOnResize
        }
    };
});