app.factory('ConnectionService', function ($http, AlertService) {

    var getData = function (url, functionToSetModel) {
        $http.get(url).then(function successCallback(response) {
            functionToSetModel(response.data);
        }, function errorCallback(response) {
            console.error(response.statusText);
        });
        return this.response;
    };

    var createData = function (urlToCreateData, objectToCreateData, functionToUpdateModel) {
        $http.post(urlToCreateData, objectToCreateData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                console.error(data, status, header, config);
            });
    };

    var updateData = function (urlToUpdateData, objectToUpdateData, functionToUpdateModel) {
        $http.put(urlToUpdateData, objectToUpdateData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
                console.error(data, status, header, config);
            });
    };

    var deleteData = function (urlToDeleteData, idToDeleteData, functionToUpdateModel) {
        $http.delete(urlToDeleteData + idToDeleteData)
            .then(function successCallback(data) {
                console.log(data);
                functionToUpdateModel();
            }, function errorCallback(data, status, header, config) {
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
            createData(docentsUrl, JSON.stringify(docent), getDocents);
        },
        updateDocent: function (docent) {
            updateData(docentsUrl + docent.id, JSON.stringify(docent), getDocents);
        },
        deleteDocent: function (docent) {
            deleteData(docentsUrl, docent.id, getDocents);
        },
        getDocents: function () {
            return docents;
        },
        createRoom: function (room) {
            createData(roomsUrl, JSON.stringify(room), getRooms);
        },
        updateRoom: function (room) {
            updateData(roomsUrl + room.id, JSON.stringify(room), getRooms);
        },
        deleteRoom: function (room) {
            deleteData(roomsUrl, room.id, getRooms);
        },
        getRooms: function () {
            return rooms;
        },
        createCentury: function (manipleId, century) {
            century.type = 'century';
            createData(maniplesUrl + manipleId + '/addCentury', JSON.stringify(century), getCohorts);
        },
        updateCentury: function (century) {
            updateData(centuriesUrl + century.id, JSON.stringify(century), getCohorts);
        },
        deleteCentury: function (manipleId, century) {
            deleteData(maniplesUrl + manipleId + '/deleteCentury/', century.id, getCohorts);
        },
        createManiple: function (cohortId, maniple) {
            maniple.type = 'maniple';
            createData(cohortsUrl + cohortId + '/addManiple', JSON.stringify(maniple), getCohorts);
        },
        updateManiple: function (maniple) {
            updateData(maniplesUrl + maniple.id, JSON.stringify(maniple), getCohorts);
        },
        deleteManiple: function (cohortId, maniple) {
            deleteData(cohortsUrl + cohortId + '/deleteManiple/', maniple.id, getCohorts);
        },
        createCohort: function (cohort) {
            cohort.type = 'cohort';
            createData(cohortsUrl, JSON.stringify(cohort), getCohorts);
        },
        updateCohort: function (cohort) {
            updateData(cohortsUrl + cohort.id, JSON.stringify(cohort), getCohorts);
        },
        deleteCohort: function (cohort) {
            deleteData(cohortsUrl, cohort.id, getCohorts);
        },
        getCohorts: function () {
            return cohorts;
        },
        createCourse: function (course) {
            course.type = 'course';
            createData(coursesUrl, JSON.stringify(course), getCourses);
        },
        updateCourse: function (course) {
            updateData(coursesUrl + course.id, JSON.stringify(course), getCourses);
        },
        deleteCourse: function (course) {
            deleteData(coursesUrl, course.id, getCourses);
        },
        getCourses: function () {
            return courses;
        },
        createSeminar: function (seminar) {
            seminar.type = 'seminar';
            createData(seminarsUrl, JSON.stringify(seminar), getSeminars);
        },
        updateSeminar: function (seminar) {
            updateData(seminarsUrl + seminar.id, JSON.stringify(seminar), getSeminars);
        },
        deleteSeminar: function (seminar) {
            deleteData(seminarsUrl, seminar.id, getSeminars);
        },
        getSeminars: function () {
            return seminars;
        },
        createSubject: function (subject) {
            createData(subjectsUrl, JSON.stringify(subject), getSubjects);
        },
        updateSubject: function (subject) {
            updateData(subjectsUrl + subject.id, JSON.stringify(subject), getSubjects);
        },
        deleteSubject: function (subject) {
            deleteData(subjectsUrl, subject.id, getSubjects);
        },
        getSubjects: function () {
            return subjects;
        },
        createEvent: function (event) {
            createData(eventsUrl, JSON.stringify(event), getEvents());
        },
        updateEvent: function (event) {
            updateData(eventsUrl + event.id, JSON.stringify(event), getEvents());
        },
        deleteEvent: function (event) {
            deleteData(eventsUrl, event.id, getEvents());
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
            });
        }
    }
});