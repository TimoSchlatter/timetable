app.factory('ConnectionService', function ($http, AlertService) {

    var getData = function (url, functionToSetModel) {
        $http.get(url).then(function successCallback(response) {
            functionToSetModel(response.data);
        }, function errorCallback(response) {
            AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich vom Server abgerufen werden. [' + response.data.error + ']');
            console.error(response);
        });
        return this.response;
    };

    var createData = function (urlToCreateData, objectToCreateData, functionToUpdateModel) {
        $http.post(urlToCreateData, objectToCreateData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server gesichert werden. [' + data.data.error + ']');
                console.error(data, status, header, config);
            });
    };

    var updateData = function (urlToUpdateData, objectToUpdateData, functionToUpdateModel) {
        $http.put(urlToUpdateData, objectToUpdateData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server akualisiert werden. [' + data.data.error + ']');
                console.error(data, status, header, config);
            });
    };

    var deleteData = function (urlToDeleteData, idToDeleteData, functionToUpdateModel) {
        $http.delete(urlToDeleteData + idToDeleteData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                AlertService.add('Fehler', 'Die Daten konnten nicht erfolgreich auf dem Server gelöscht werden. [' + data.data.error + ']');
                console.error(data, status, header, config);
            });
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
    var centuriesUrl = 'http://localhost:49999/centuries/';

    /*ManiplesControl */
    var maniplesUrl = 'http://localhost:49999/maniples/';

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

    var getFullDataModel = function () {
        getDocents();
        getRooms();
        getCohorts();
        getCourses();
        getSeminars();
        getSubjects();
        getEvents();
        getSubjectTypes();
        getSeminarTypes();
        getRoomTypes();
    };
    getFullDataModel();

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
        createEvent: function (event) {
            createData(eventsUrl, angular.toJson(event), getEvents);
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
            getData('http://localhost:49999/generateData', function (data) {
                getFullDataModel();
                AlertService.add('Erfolgreich', 'Die Simulationsdaten wurden erfolgreich generiert!');
            });
        }
    }
});