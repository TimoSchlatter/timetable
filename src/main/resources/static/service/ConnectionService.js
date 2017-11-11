/*
 AngularJS Service
 Service has substitutable objects that are wired together using dependency injection (DI).
 It is used to organize the timetable objects across the web application.
 @author Jonas Jacobsen and Jonas Gehrke
 */

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
    var docentsLastUpdateTime;
    var docentsUrl = 'http://localhost:49999/docents/';
    var getDocents = function () {
        getData(docentsUrl, function (data) {
            docents = data;
            docentsLastUpdateTime = Date.now();
        });
    };

    /* RoomsControl */
    var rooms = [];
    var roomsLastUpdateTime;
    var roomsUrl = 'http://localhost:49999/rooms/';
    var getRooms = function () {
        getData(roomsUrl, function (data) {
            rooms = data;
            roomsLastUpdateTime = Date.now();
        });
    };

    /* CenturiesControl */
    var centuries = [];
    var centuriesLastUpdateTime;
    var centuriesUrl = 'http://localhost:49999/centuries/';
    var getCenturies = function () {
        getData(centuriesUrl, function (data) {
            centuries = data;
            centuriesLastUpdateTime = Date.now();
        });
    };

    /*ManiplesControl */
    var maniples = [];
    var maniplesLastUpdateTime;
    var maniplesUrl = 'http://localhost:49999/maniples/';
    var getManiples = function () {
        getData(maniplesUrl, function (data) {
            maniples = data;
            maniplesLastUpdateTime = Date.now();
        });
    };

    /*CohortsControl */
    var cohorts = [];
    var cohortsLastUpdateTime;
    var cohortsUrl = 'http://localhost:49999/cohorts/';
    var getCohorts = function () {
        getData(cohortsUrl, function (data) {
            cohorts = data;
            cohortsLastUpdateTime = Date.now();
        });
    };

    /*CoursesControl */
    var courses = [];
    var coursesLastUpdateTime;
    var coursesUrl = 'http://localhost:49999/courses/';
    var getCourses = function () {
        getData(coursesUrl, function (data) {
            courses = data;
            coursesLastUpdateTime = Date.now();
        });
    };

    /* SeminarsControl */
    var seminars = [];
    var seminarsLastUpdateTime;
    var seminarsUrl = 'http://localhost:49999/seminars/';
    var getSeminars = function () {
        getData(seminarsUrl, function (data) {
            seminars = data;
            seminarsLastUpdateTime = Date.now();
        });
    };

    /* SeminarsGroupsControl */
    var seminarGroups = [];
    var seminarGroupsLastUpdateTime;
    var seminarGroupsUrl = 'http://localhost:49999/seminargroups/';
    var getSeminarGroups = function () {
        getData(seminarGroupsUrl, function (data) {
            seminarGroups = data;
            seminarGroupsLastUpdateTime = Date.now();
        });
    };

    /* SubjectsControl */
    var subjects = [];
    var subjectsLastUpdateTime;
    var subjectsUrl = 'http://localhost:49999/subjects/';
    var getSubjects = function () {
        getData(subjectsUrl, function (data) {
            subjects = data;
            subjectsLastUpdateTime = Date.now();
        });
    };

    /* EventsControl */
    var events = [];
    var eventsLastUpdateTime;
    var eventsUrl = 'http://localhost:49999/events/';
    var getEvents = function () {
        getData(eventsUrl, function (data) {
            events = data;
            eventsLastUpdateTime = Date.now();
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
            updateData(docentsUrl + docent.id, angular.toJson(docent), updateAllDataModels);
        },
        deleteDocent: function (docent) {
            deleteData(docentsUrl, docent.id, updateAllDataModels);
        },
        getDocents: function () {
            return docents;
        },
        getDocentsLastUpdateTime: function () {
            return docentsLastUpdateTime;
        },
        createRoom: function (room) {
            createData(roomsUrl, angular.toJson(room), getRooms);
        },
        updateRoom: function (room) {
            updateData(roomsUrl + room.id, angular.toJson(room), updateAllDataModels);
        },
        deleteRoom: function (room) {
            deleteData(roomsUrl, room.id, updateAllDataModels);
        },
        getRooms: function () {
            return rooms;
        },
        getRoomsLastUpdateTime: function () {
            return roomsLastUpdateTime;
        },
        createCentury: function (manipleId, century) {
            century.type = 'century';
            createData(maniplesUrl + manipleId + '/addCentury', angular.toJson(century), getCohorts);
        },
        updateCentury: function (century) {
            updateData(centuriesUrl + century.id, angular.toJson(century), updateAllDataModels);
        },
        deleteCentury: function (manipleId, century) {
            deleteData(maniplesUrl + manipleId + '/deleteCentury/', century.id, updateAllDataModels);
        },
        getCenturies: function () {
            return centuries;
        },
        getCenturiesLastUpdateTime: function () {
            return centuriesLastUpdateTime;
        },
        createManiple: function (cohortId, maniple) {
            maniple.type = 'maniple';
            createData(cohortsUrl + cohortId + '/addManiple', angular.toJson(maniple), getCohorts);
        },
        updateManiple: function (maniple) {
            updateData(maniplesUrl + maniple.id, angular.toJson(maniple), updateAllDataModels);
        },
        deleteManiple: function (cohortId, maniple) {
            deleteData(cohortsUrl + cohortId + '/deleteManiple/', maniple.id, updateAllDataModels);
        },
        getManiples: function () {
            return maniples;
        },
        getManiplesLastUpdateTime: function () {
            return maniplesLastUpdateTime;
        },
        createCohort: function (cohort) {
            cohort.type = 'cohort';
            createData(cohortsUrl, angular.toJson(cohort), getCohorts);
        },
        updateCohort: function (cohort) {
            updateData(cohortsUrl + cohort.id, angular.toJson(cohort), updateAllDataModels);
        },
        deleteCohort: function (cohort) {
            deleteData(cohortsUrl, cohort.id, updateAllDataModels);
        },
        getCohorts: function () {
            return cohorts;
        },
        getCohortsLastUpdateTime: function () {
            return cohortsLastUpdateTime;
        },
        createCourse: function (course) {
            course.type = 'course';
            createData(coursesUrl, angular.toJson(course), getCourses);
        },
        updateCourse: function (course) {
            updateData(coursesUrl + course.id, angular.toJson(course), updateAllDataModels);
        },
        deleteCourse: function (course) {
            deleteData(coursesUrl, course.id, updateAllDataModels);
        },
        getCourses: function () {
            return courses;
        },
        getCoursesLastUpdateTime: function () {
            return coursesLastUpdateTime;
        },
        createSeminar: function (seminar) {
            seminar.type = 'seminar';
            createData(seminarsUrl, angular.toJson(seminar), getSeminars);
        },
        updateSeminar: function (seminar) {
            updateData(seminarsUrl + seminar.id, angular.toJson(seminar), updateAllDataModels);
        },
        deleteSeminar: function (seminar) {
            deleteData(seminarsUrl, seminar.id, updateAllDataModels);
        },
        getSeminars: function () {
            return seminars;
        },
        getSeminarsLastUpdateTime: function () {
            return seminarsLastUpdateTime;
        },
        createSeminarGroup: function (seminarGroup) {
            seminarGroup.type = 'seminargroup';
            createData(seminarGroupsUrl, angular.toJson(seminarGroup), getSeminarGroups);
        },
        updateSeminarGroup: function (seminarGroup) {
            updateData(seminarGroupsUrl + seminarGroup.id, angular.toJson(seminarGroup), updateAllDataModels);
        },
        deleteSeminarGroup: function (seminarGroup) {
            deleteData(seminarGroupsUrl, seminarGroup.id, updateAllDataModels);
        },
        getSeminarGroups: function () {
            return seminarGroups;
        },
        getSeminarGroupsLastUpdateTime: function () {
            return seminarGroupsLastUpdateTime;
        },
        createSubject: function (subject) {
            createData(subjectsUrl, angular.toJson(subject), getSubjects);
        },
        updateSubject: function (subject) {
            updateData(subjectsUrl + subject.id, angular.toJson(subject), updateAllDataModels);
        },
        deleteSubject: function (subject) {
            deleteData(subjectsUrl, subject.id, updateAllDataModels);
        },
        getSubjects: function () {
            return subjects;
        },
        getSubjectsLastUpdateTime: function () {
            return subjectsLastUpdateTime;
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
        getEventsLastUpdateTime: function () {
            return eventsLastUpdateTime;
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