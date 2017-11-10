app.factory('ConnectionService', function ($http, AlertService, $rootScope) {

    var callPut = false;

    var getData = function (url, functionToSetModel) {
        $http.get(url).then(function successCallback(response) {
            functionToSetModel(response.data);
        }, function errorCallback(response) {
            console.error(response.statusText);
            AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich vom Server abgerufen werden. [' + data.error + ']');
        });
        return this.response;
    };

    var createData = function (urlToCreateData, objectToCreateData, functionToUpdateModel) {
        $http.post(urlToCreateData, objectToCreateData)
            .then(function successCallback(data) {
                console.log(data);
                if(data.status == 200) {
                    AlertService.addEventAlert('Kollisionen', data.data.toString());
                    $rootScope.collisionEvent = angular.copy(objectToCreateData);
                    }
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server gesichert werden.');
                console.error(data, status, header, config);
            });
    };

    var updateData = function (urlToUpdateData, objectToUpdateData, functionToUpdateModel) {
        $http.put(urlToUpdateData, objectToUpdateData)
            .then(function successCallback(data) {
                console.log(data);
                if(data.status == 200) {
                    AlertService.addEventAlert('Kollisionen', data.data.toString());
                    $rootScope.collisionEvent = angular.copy(objectToUpdateData);
                    $rootScope.putEventUrl = angular.copy(urlToUpdateData);
                    callPut = true;
                }
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server akualisiert werden. [' + data.error + ']');
                console.error(data, status, header, config);
            });
    };

    var deleteData = function (urlToDeleteData, idToDeleteData, functionToUpdateModel) {
        $http.delete(urlToDeleteData + idToDeleteData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server gelöscht werden. [' + data.error + ']');
                console.error(data, status, header, config);
            });
    };

    var createEventInsteadOfCollision = function () {
        if(callPut){
            $rootScope.putEventUrl = $rootScope.putEventUrl +  '?ignoreCollisions=true';
            $http.put($rootScope.putEventUrl, $rootScope.collisionEvent)
                .then(function successCallback(data) {
                    console.log(data);
                    getEvents();
                }, function errorCallback(data, status, header, config) {
                    AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server gesichert werden.');
                    console.error(data, status, header, config);
                });
            callPut = false;
        } else {
            var postEventUrl = eventsUrl + '?ignoreCollisions=true';
            $http.post(postEventUrl, $rootScope.collisionEvent)
                .then(function successCallback(data) {
                    console.log(data);
                    getEvents();
                }, function errorCallback(data, status, header, config) {
                    AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server gesichert werden.');
                    console.error(data, status, header, config);
                });
        }
    };

    /* DocentsControl */
    var docents = [];
    var docentsUrl = 'http://localhost:49999/docents/';
    var getDocents = function () {
        getData(docentsUrl, function (data) {
            docents = data
        });
    };

    /* RoomsControl */
    var rooms = [];
    var roomsUrl = 'http://localhost:49999/rooms/';
    var getRooms = function () {
        getData(roomsUrl, function (data) {
            rooms = data
        });
    };

    /* CenturiesControl */
    var centuries = [];
    var centuriesUrl = 'http://localhost:49999/centuries/';
    var getCenturies = function () {
        getData(centuriesUrl, function (data) {
            centuries = data;
        });
    };

    /*ManiplesControl */
    var maniples = [];
    var maniplesUrl = 'http://localhost:49999/maniples/';
    var getManiples = function () {
        getData(maniplesUrl, function (data) {
            maniples = data;
        });
    };

    /*CohortsControl */
    var cohorts = [];
    var cohortsUrl = 'http://localhost:49999/cohorts/';
    var getCohorts = function () {
        getData(cohortsUrl, function (data) {
            cohorts = data;
        });
    };

    /*CoursesControl */
    var courses = [];
    var coursesUrl = 'http://localhost:49999/courses/';
    var getCourses = function () {
        getData(coursesUrl, function (data) {
            courses = data;
        });
    };

    /* SeminarsControl */
    var seminars = [];
    var seminarsUrl = 'http://localhost:49999/seminars/';
    var getSeminars = function () {
        getData(seminarsUrl, function (data) {
            seminars = data;
        });
    };

    /* SeminarsGroupsControl */
    var seminarGroups = [];
    var seminarGroupsUrl = 'http://localhost:49999/seminargroups/';
    var getSeminarGroups = function () {
        getData(seminarGroupsUrl, function (data) {
            seminarGroups = data;
        });
    };

    /* SubjectsControl */
    var subjects = [];
    var subjectsUrl = 'http://localhost:49999/subjects/';
    var getSubjects = function () {
        getData(subjectsUrl, function (data) {
            subjects = data;
        });
    };

    /* EventsControl */
    var events = [];
    var eventsUrl = 'http://localhost:49999/events/';
    var getEvents = function () {
        getData(eventsUrl, function (data) {
            events = data;
        });
    };

    /* SubjectTypes */
    var subjectTypes = {};
    var subjectTypesUrl = 'http://localhost:49999/subjecttypes/';
    var getSubjectTypes = function () {
        getData(subjectTypesUrl, function (data) {
            subjectTypes = data;
        });
    };

    /* SeminarTypes */
    var seminarTypes = {};
    var seminarTypesUrl = 'http://localhost:49999/seminartypes/';
    var getSeminarTypes = function () {
        getData(seminarTypesUrl, function (data) {
            seminarTypes = data;
        });
    };

    /* RoomTypes */
    var roomTypes = {};
    var roomTypesUrl = 'http://localhost:49999/roomtypes/';
    var getRoomTypes = function () {
        getData(roomTypesUrl, function (data) {
            roomTypes = data;
        });
    };

    /* Data Generated */
    var dataGenerated = {};
    var dataGeneratedUrl = 'http://localhost:49999/datagenerated/';
    var getDataGenerated = function () {
        getData(dataGeneratedUrl, function (data) {
            dataGenerated = data;
        });
    };

    var updateAllDataModels = function () {
        getDocents();
        getRooms();
        getCohorts();
        getManiples();
        getCenturies();
        getCourses();
        getSeminars();
        getSeminarGroups();
        getSubjects();
        getEvents();
        getSubjectTypes();
        getSeminarTypes();
        getRoomTypes();
        getDataGenerated();
    };
    updateAllDataModels();

    return {
        createDocent: function (docent) {
            createData(docentsUrl, angular.toJson(docent), getDocents);
        },
        updateDocent: function (docent) {
            updateData(docentsUrl + docent.id, angular.toJson(docent), getDocents);
        },
        deleteDocent: function (docent) {
            deleteData(docentsUrl, docent.id, getDocents);
        },
        getDocents: function () {
            return docents;
        },
        createRoom: function (room) {
            createData(roomsUrl, angular.toJson(room), getRooms);
        },
        updateRoom: function (room) {
            updateData(roomsUrl + room.id, angular.toJson(room), getRooms);
        },
        deleteRoom: function (room) {
            deleteData(roomsUrl, room.id, getRooms);
        },
        getRooms: function () {
            return rooms;
        },
        createCentury: function (manipleId, century) {
            century.type = 'century';
            createData(maniplesUrl + manipleId + '/addCentury', angular.toJson(century), getCohorts);
        },
        updateCentury: function (century) {
            updateData(centuriesUrl + century.id, angular.toJson(century), getCohorts);
        },
        deleteCentury: function (manipleId, century) {
            deleteData(maniplesUrl + manipleId + '/deleteCentury/', century.id, getCohorts);
        },
        getCenturies: function () {
            return centuries;
        },
        createManiple: function (cohortId, maniple) {
            maniple.type = 'maniple';
            createData(cohortsUrl + cohortId + '/addManiple', angular.toJson(maniple), getCohorts);
        },
        updateManiple: function (maniple) {
            updateData(maniplesUrl + maniple.id, angular.toJson(maniple), getCohorts);
        },
        deleteManiple: function (cohortId, maniple) {
            deleteData(cohortsUrl + cohortId + '/deleteManiple/', maniple.id, getCohorts);
        },
        getManiples: function () {
            return maniples;
        },
        createCohort: function (cohort) {
            cohort.type = 'cohort';
            createData(cohortsUrl, angular.toJson(cohort), getCohorts);
        },
        updateCohort: function (cohort) {
            updateData(cohortsUrl + cohort.id, angular.toJson(cohort), getCohorts);
        },
        deleteCohort: function (cohort) {
            deleteData(cohortsUrl, cohort.id, getCohorts);
        },
        getCohorts: function () {
            return cohorts;
        },
        createCourse: function (course) {
            course.type = 'course';
            createData(coursesUrl, angular.toJson(course), getCourses);
        },
        updateCourse: function (course) {
            updateData(coursesUrl + course.id, angular.toJson(course), getCourses);
        },
        deleteCourse: function (course) {
            deleteData(coursesUrl, course.id, getCourses);
        },
        getCourses: function () {
            return courses;
        },
        createSeminar: function (seminar) {
            seminar.type = 'seminar';
            createData(seminarsUrl, angular.toJson(seminar), getSeminars);
        },
        updateSeminar: function (seminar) {
            updateData(seminarsUrl + seminar.id, angular.toJson(seminar), getSeminars);
        },
        deleteSeminar: function (seminar) {
            deleteData(seminarsUrl, seminar.id, getSeminars);
        },
        getSeminars: function () {
            return seminars;
        },
        createSeminarGroup: function (seminarGroup) {
            seminarGroup.type = 'seminargroup';
            createData(seminarGroupsUrl, angular.toJson(seminarGroup), getSeminarGroups);
        },
        updateSeminarGroup: function (seminarGroup) {
            updateData(seminarGroupsUrl + seminarGroup.id, angular.toJson(seminarGroup), getSeminarGroups);
        },
        deleteSeminarGroup: function (seminarGroup) {
            deleteData(seminarGroupsUrl, seminarGroup.id, getSeminarGroups);
        },
        getSeminarGroups: function () {
            return seminarGroups;
        },
        createSubject: function (subject) {
            createData(subjectsUrl, angular.toJson(subject), getSubjects);
        },
        updateSubject: function (subject) {
            updateData(subjectsUrl + subject.id, angular.toJson(subject), getSubjects);
        },
        deleteSubject: function (subject) {
            deleteData(subjectsUrl, subject.id, getSubjects);
        },
        getSubjects: function () {
            return subjects;
        },
        createEvent: function (event, repeatWeeks) {
            createData(eventsUrl + '?repeatWeeks=' + repeatWeeks, angular.toJson(event), getEvents);
        },
        updateEvent: function (event) {
            updateData(eventsUrl + event.id, angular.toJson(event), getEvents);
        },
        deleteEvent: function (event) {
            deleteData(eventsUrl, event.id, getEvents);
        },
        getEvents: function () {
            return events;
        },
        getEventsByGroup: function (group, functionToSetModel) {
            return getData(eventsUrl + 'findByGroup?id=' + group.id, functionToSetModel);
        },
        getEventsByRoom: function (docent, functionToSetModel) {
            return getData(eventsUrl + 'findByRoom?id=' + docent.id, functionToSetModel);
        },
        getEventsByDocent: function (docent, functionToSetModel) {
            return getData(eventsUrl + 'findByDocent?id=' + docent.id, functionToSetModel);
        },
        getVacantRooms: function (event, functionToSetModel) {
            return getData('http://localhost:49999/vacantRooms?date=' + event.date + '&startTime=' + event.startTime + '&endTime=' + event.endTime
                + '&groupId=' + event.group.id + (event.id ? '&eventId=' + event.id : ''), functionToSetModel);
        },
        getSubjectTypes: function () {
            return subjectTypes;
        },
        getSeminarTypes: function () {
            return seminarTypes;
        },
        getRoomTypes: function () {
            return roomTypes;
        },
        generateData: function () {
            getData('http://localhost:49999/generatedata', function (data) {
                updateAllDataModels();
                AlertService.add('Erfolgreich', 'Die Simulationsdaten wurden erfolgreich generiert!');
            });
        },
        dataGenerated: function () {
            return dataGenerated;
        },
        createEventInsteadOfCollision: function () {
            createEventInsteadOfCollision();
        }
    }
});