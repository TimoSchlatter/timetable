'use strict'

app.controller('DashboardController', function($scope , $http, AlertService, ConnectionService, $filter) {

    $scope.generateData = ConnectionService.generateData;
    $scope.dataGenerated = ConnectionService.dataGenerated;
    $scope.events = ConnectionService.getEvents;
    $scope.eventsByGroup = ConnectionService.getEventsByGroup;
    $scope.eventsByRoom = ConnectionService.getEventsByRoom;
    $scope.eventsByDocent = ConnectionService.getEventsByDocent;
    $scope.createEvent = ConnectionService.createEvent;
    $scope.updateEvent = ConnectionService.updateEvent;
    $scope.deleteEvent = ConnectionService.deleteEvent;
    $scope.rooms = ConnectionService.getRooms;
    $scope.docents = ConnectionService.getDocents;
    $scope.subjects = ConnectionService.getSubjects;
    $scope.cohorts = ConnectionService.getCohorts;
    $scope.maniples = ConnectionService.getManiples;
    $scope.centuries = ConnectionService.getCenturies;
    $scope.subjectTypes = ConnectionService.getSubjectTypes;
    $scope.seminarGroups = ConnectionService.getSeminarGroups;

    $scope.calenderEvents = [[]];

    $scope.selectableEntities = ['Alle', 'Dozent', 'Raum', 'Kohorte', 'Manipel', 'Zenturie', 'Seminargruppe'];
    $scope.selectedEntity = $scope.selectableEntities[0];

    $scope.$watch('cohorts()', function () {
        buildCohortsAdvanced($scope.cohorts());
    });

    $scope.$watch('events()', function () {
        $scope.updateAllEvents();
    });

    $scope.updateAllEvents = function () {
        if ($scope.selectedEntity === $scope.selectableEntities[0]) {
            convertEventsToCalender($scope.events());
        }
    };

    $scope.convertEventsByToCalender = function (entity, getEventsBy) {
        if (entity) {
            getEventsBy(entity, function (data) {
                convertEventsToCalender(data);
            });
        }
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

    $scope.modalToEvent = function () {
        var date = $scope.modalDate.split('.');
        $scope.event.date = date[2] + '-' + date[1] + '-' + date[0];
        $scope.event.docents = $scope.modalSelectedDocents;
        $scope.event.endTime = $scope.modalEndTime + ':00';
        $scope.event.group = getSelectedGroup();
        $scope.event.startTime = $scope.modalStartTime + ':00';
        $scope.event.subject = $scope.modalSubject;
        $scope.event.rooms = $scope.modalSelectedRooms;
        console.log(angular.toJson($scope.event));
        return $scope.event;
    };

    var eventToModal = function () {
        var date = $scope.event.date.split('-');
        $('#inputDate').datepicker('update', date[2] + '.' + date[1] + '.' + date[0]);
        $scope.modalSelectedDocents = $scope.event.docents;
        $scope.modalEndTime = $scope.event.endTime.slice(0, -3);
        $scope.modalStartTime = $scope.event.startTime.slice(0, -3);
        $scope.modalSubjectType = $scope.event.subject.subjectType;
        var selectedSubjects = $filter('filter')($scope.subjects(), {id: $scope.event.subject.id});
        $scope.modalSubject = selectedSubjects[0];
        setSelectedGroup($scope.event.group);
        $scope.modalSelectedRooms = $scope.event.rooms;
    };

    var getSelectedGroup = function () {
        if ($scope.modalSubjectType === 'SEMINAR') {
            return $scope.modalSelectedSeminarGroup;
        } else if ($scope.modalSelectedCentury !== $scope.modalSelectedManiple.centuries[0]) {
            return $scope.modalSelectedCentury;
        } else if ($scope.modalSelectedManiple !== $scope.modalSelectedCohort.maniples[0]) {
            var modalSelectedManiple = angular.copy($scope.modalSelectedManiple);
            delete modalSelectedManiple.centuries;
            return modalSelectedManiple;
        } else if ($scope.modalSelectedCohort !== $scope.cohortsAdvanced[0]) {
            var modalSelectedCohort = angular.copy($scope.modalSelectedCohort);
            delete modalSelectedCohort.maniples;
            return modalSelectedCohort;
        } else {
            return undefined;
        }
    };

    var setSelectedGroup = function (group) {
        if (group.type === 'seminargroup') {
            var selectedSubjects = $filter('filter')($scope.seminarGroups(), {id: group.id});
            $scope.modalSelectedSeminarGroup = selectedSubjects[0];
            return;
        }
        $scope.modalSelectedCohort = $scope.cohortsAdvanced[0];
        $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
        angular.forEach($scope.cohortsAdvanced, function (cohort) {
            angular.forEach(cohort.maniples, function (maniple) {
                angular.forEach(maniple.centuries, function (century) {
                    if (group.id === century.id) {
                        $scope.modalSelectedCohort = cohort;
                        $scope.modalSelectedManiple = maniple;
                        $scope.modalSelectedCentury = century;
                    }
                });
                if (group.id === maniple.id) {
                    $scope.modalSelectedCohort = cohort;
                    $scope.modalSelectedManiple = maniple;
                    $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
                }
            });
            if (group.id === cohort.id) {
                $scope.modalSelectedCohort = cohort;
                $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
                $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
            }
        });
    };

    $scope.setSelectedEvent = function (event) {
        $scope.event = angular.copy(event);
        console.log('Selected Event:', $scope.event);
        eventToModal();
    };

    $scope.changedCohort = function () {
        $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
    };

    $scope.changedManiple = function () {
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
    };

    var convertEventsToCalender = function (events) {
        $scope.calenderEvents[0] = [];
        angular.forEach(events, function (event) {
            var event = {
                id: event.id,
                title: event.subject.module.title,
                start: event.date + 'T' + event.startTime,
                end: event.date + 'T' + event.endTime,
                allDay: false,
                editable: false
            };
            $scope.calenderEvents[0].push(event);
        });
    };

    $scope.alertEventOnClick = function (event) {
        var selectedEvent = $filter('filter')($scope.events(), {id: event.id});
        $scope.setSelectedEvent(selectedEvent[0]);
        $('#addEditModal').modal('show');
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
                left: 'month agendaWeek agendaDay',
                center: 'title',
                right: 'today prev,next'
            },
            eventClick: $scope.alertEventOnClick,
            eventDrop: $scope.alertOnDrop,
            eventResize: $scope.alertOnResize,
        }
    };
});