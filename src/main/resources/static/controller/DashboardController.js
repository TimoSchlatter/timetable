'use strict'

app.controller('DashboardController', function($scope , $http, AlertService, ConnectionService, $filter) {

    $scope.generateData = ConnectionService.generateData;
    $scope.dataGenerated = ConnectionService.dataGenerated;
    $scope.events = ConnectionService.getEvents;
    $scope.eventsByGroup = ConnectionService.getEventsByGroup;
    $scope.eventsByRoom = ConnectionService.getEventsByRoom;
    $scope.eventsByDocent = ConnectionService.getEventsByDocent;
    $scope.eventsLastUpdateTime = ConnectionService.getEventsLastUpdateTime;
    $scope.createEvent = ConnectionService.createEvent;
    $scope.updateEvent = ConnectionService.updateEvent;
    $scope.deleteEvent = ConnectionService.deleteEvent;
    $scope.rooms = ConnectionService.getRooms;
    $scope.vacantRooms = ConnectionService.getVacantRooms;
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

    var isSubFormValid = function () {
        return $scope.modalSubForm.$valid && (($scope.modalSubjectType === 'SEMINAR') === isDefaultSelectedCohort());
    };

    var isDefaultSelectedCohort = function () {
        return $scope.modalSelectedCohort === $scope.cohortsAdvanced[0];
    };

    var isDefaultSelectedManiple = function () {
        return $scope.modalSelectedManiple === $scope.modalSelectedCohort.maniples[0];
    };

    var isDefaultSelectedCentury = function () {
        return $scope.modalSelectedCentury === $scope.modalSelectedManiple.centuries[0];
    };

    $scope.updateVacantRooms = function () {
        if (isSubFormValid()) {
            $scope.getModalEvent();
            setVacantRooms($scope.event);
        } else {
            $scope.vacantRoomSet = [];
        }
    };

    var setVacantRooms = function (event) {
        $scope.vacantRooms(event, function (data) {
            $scope.vacantRoomSet = data;
        });
    };

    $scope.updateAllEvents = function () {
        if ($scope.selectedEntity === $scope.selectableEntities[0]) {
            convertEventsToCalender($scope.events());
        }
    };

    $scope.convertEventsByEntityToCalender = function (entity, functionEventsByEntity) {
        if (entity) {
            functionEventsByEntity(entity, function (data) {
                convertEventsToCalender(data);
            });
        }
    };

    var getTime = function (timeString) {
        var timeTokens = timeString.split(':');
        return new Date(1970, 0, 1, timeTokens[0], timeTokens[1], timeTokens[2]);
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

    $scope.getModalEvent = function () {
        $scope.event.date = $filter('date')($scope.modalDate, 'yyyy-MM-dd');
        $scope.event.docents = $scope.modalSelectedDocents;
        $scope.event.endTime = $filter('date')($scope.modalEndTime, 'HH:mm:ss');
        $scope.event.group = getSelectedGroup();
        $scope.event.startTime = $filter('date')($scope.modalStartTime, 'HH:mm:ss');
        $scope.event.subject = $scope.modalSubject;
        $scope.event.rooms = $scope.modalSelectedRooms;
        console.log(angular.toJson($scope.event));
        return $scope.event;
    };

    var setEventToModal = function (event) {
        if (event.id) {
            setExistingEventToModal(event)
            setVacantRooms(event);
        } else {
            setNewEventToModal();
        }
    };

    var setExistingEventToModal = function (event) {
        $('#inputDate').datepicker('update', event.date);
        $scope.modalEndTime = getTime($scope.event.endTime);
        $scope.modalStartTime = getTime($scope.event.startTime);
        $scope.modalSubjectType = event.subject.subjectType;
        var selectedSubjects = $filter('filter')($scope.subjects(), {id: event.subject.id});
        $scope.modalSubject = selectedSubjects[0];
        setSelectedGroup(event.group);
        $scope.modalSelectedDocents = event.docents;
        $scope.modalSelectedRooms = event.rooms;
    };

    var setNewEventToModal = function () {
        $('#inputDate').datepicker('update', '');
        $scope.modalEndTime = undefined;
        $scope.modalStartTime = undefined;
        $scope.modalSubjectType = undefined;
        $scope.modalSubject = undefined;
        $scope.modalSelectedCohort = $scope.cohortsAdvanced[0];
        $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
        $scope.modalSelectedSeminarGroup = undefined;
        $scope.modalSelectedDocents = [];
        $scope.modalSelectedRooms = [];
        $scope.modalRepeatWeeks = 1;
    };

    var getSelectedGroup = function () {
        if ($scope.modalSubjectType === 'SEMINAR') {
            return $scope.modalSelectedSeminarGroup;
        } else if (!isDefaultSelectedCentury()) {
            return $scope.modalSelectedCentury;
        } else if (!isDefaultSelectedManiple()) {
            var modalSelectedManiple = angular.copy($scope.modalSelectedManiple);
            delete modalSelectedManiple.centuries;
            return modalSelectedManiple;
        } else if (!isDefaultSelectedCohort()) {
            var modalSelectedCohort = angular.copy($scope.modalSelectedCohort);
            delete modalSelectedCohort.maniples;
            return modalSelectedCohort;
        } else {
            return undefined;
        }
    };

    var setSelectedGroup = function (group) {
        if (group && group.type === 'seminargroup') {
            var selectedSubjects = $filter('filter')($scope.seminarGroups(), {id: group.id});
            $scope.modalSelectedSeminarGroup = selectedSubjects[0];
            return;
        }
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
        setEventToModal($scope.event);
    };

    $scope.changedCohort = function () {
        $scope.modalSelectedManiple = $scope.modalSelectedCohort.maniples[0];
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
        $scope.updateVacantRooms();
    };

    $scope.changedManiple = function () {
        $scope.modalSelectedCentury = $scope.modalSelectedManiple.centuries[0];
        $scope.updateVacantRooms();
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
        }
    };
});