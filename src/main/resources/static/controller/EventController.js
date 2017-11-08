'use strict';

app.controller('EventController', function ($scope, ConnectionService) {

    $scope.events = ConnectionService.getEvents;
    $scope.updateEvent = ConnectionService.updateEvent;
    $scope.deleteEvent = ConnectionService.deleteEvent;
    $scope.rooms = ConnectionService.getRooms;
    $scope.docents = ConnectionService.getDocents;
    $scope.subjects = ConnectionService.getSubjects;
    $scope.cohorts = ConnectionService.getCohorts;
    $scope.subjectTypes = ConnectionService.getSubjectTypes;

    $scope.$watch('cohorts()', function () {
        buildCohortsAdvanced($scope.cohorts());
    });

    $scope.createEvent = function () {
        $scope.event.date = $scope.modalDate.split('.').reverse();//[2018,4,40];
        $scope.event.docents = $scope.modalSelectedDocents;
        $scope.event.endTime = $scope.modalEndTime.split(':');//[18,30];
        $scope.event.group = getSelectedGroup();
        $scope.event.startTime = $scope.modalStartTime.split(':');
        ;
        $scope.event.subject = $scope.modalSubject;
        $scope.event.rooms = $scope.modalSelectedRooms;
        ConnectionService.createEvent($scope.event);
    };

    var buildCohortsAdvanced = function (cohorts) {
        $scope.cohortsAdvanced = angular.copy(cohorts);
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            angular.forEach(cohort.maniples, function (maniple) {
                maniple.centuries.unshift({name: "Alle"});
            });
            cohort.maniples.unshift({name: "Alle", centuries: [{name: "Alle"}]});
        });
        $scope.cohortsAdvanced.unshift({name: "Alle", maniples: [{name: "Alle", centuries: [{name: "Alle"}]}]});
    };

    $scope.setSelectedEvent = function (event) {
        $scope.event = angular.copy(event);
        console.log('Selected Event:', $scope.event);
        resetGroups();
    };

    var getSelectedGroup = function () {
        if ($scope.modalSelectedCentury !== $scope.modalSelectedManiple.centuries[0]) {
            return $scope.modalSelectedCentury;
        } else if ($scope.modalSelectedManiple !== $scope.modalSelectedCohort.maniples[0]) {
            return $scope.modalSelectedManiple;
        } else if ($scope.modalSelectedCohort !== $scope.cohortsAdvanced[0]) {
            return $scope.modalSelectedCohort;
        } else {
            return undefined;
        }
    };

    var resetGroups = function () {
        $scope.modalSelectedCohort = $scope.cohortsAdvanced[0];
        $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
    };

    $scope.changedCohort = function () {
        $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
    };

    $scope.changedManiple = function () {
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
    };

    // $scope.uiConfig = {
    //     calendar:{
    //         monthNames: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
    //         monthNamesShort: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
    //         dayNames: ['Sonntag','Montag','Dienstag','Mittwoch','Donnerstag','Freitag','Samstag'],
    //         dayNamesShort: ['So','Mo','Di','Mi','Do','Fr','Sa'],
    //         locale: 'de',
    //         height: 500,
    //         editable: true,
    //         header:{
    //             left: 'month basicWeek basicDay agendaWeek agendaDay',
    //             center: 'title',
    //             right: 'today prev,next'
    //         },
    //         eventClick: $scope.alertEventOnClick,
    //         eventDrop: $scope.alertOnDrop,
    //         eventResize: $scope.alertOnResize
    //     }
    // };
});