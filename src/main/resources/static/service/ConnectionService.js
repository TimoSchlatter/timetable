app.factory('ConnectionService', function ($http) {

    var getData = function (url, functionToUpdate) {
        $http.get(url).then(function successCallback(response) {
            functionToUpdate(response.data);
        }, function errorCallback(response) {
            console.error(response.statusText);
        });
        return this.response;
    };

    var createData = function (urlToCreateData, objectToCreateData, urlToGetData, functionToSetData) {
        $http.post(urlToCreateData, objectToCreateData)
            .then(function successCallback(data) {
                console.log(data);
                getData(urlToGetData, functionToSetData);
            }, function errorCallback(data, status, header, config) {
                console.error(data, status, header, config);
            });
    };

    var updateData = function (urlToUpdateData, objectToUpdateData, urlToGetData, functionToSetData) {
        $http.put(urlToUpdateData, objectToUpdateData)
            .then(function successCallback(data) {
                console.log(data);
                getData(urlToGetData, functionToSetData);
            }, function errorCallback(data, status, header, config) {
                console.error(data, status, header, config);
            });
    };

    var deleteData = function (urlToDeleteData, idToDeleteData, urlToGetData, functionToSetData) {
        $http.delete(urlToDeleteData + idToDeleteData)
            .then(function successCallback(data) {
                console.log(data);
                getData(urlToGetData, functionToSetData);
            }, function errorCallback(data, status, header, config) {
                console.error(data, status, header, config);
            });
    };

    /* DocentsControl */
    var docents = [];
    var setDocents = function (data) {
        docents = data
    };
    var docentsUrl = 'http://localhost:49999/docents/';
    getData(docentsUrl, setDocents);

    /* RoomsControl */
    var rooms = [];
    var setRooms = function (data) {
        rooms = data
    };
    var roomsUrl = 'http://localhost:49999/rooms/';
    getData(roomsUrl, setRooms);

    /* CenturiesControl */
    var centuries = [];
    var setCenturies = function (data) {
        centuries = data
    };
    var centuriesUrl = 'http://localhost:49999/centuries/';
    getData(centuriesUrl, setCenturies);

    /*ManiplesControl */
    var maniples = [];
    var setManiples = function (data) {
        maniples = data;
    };
    var maniplesUrl = 'http://localhost:49999/maniples/';
    getData(maniplesUrl, setManiples);

    /*CohortsControl */
    var cohorts = [];
    var setCohorts = function (data) {
        cohorts = data;
    };
    var cohortsUrl = 'http://localhost:49999/cohorts/';
    getData(cohortsUrl, setCohorts);

    /*CoursesControl */
    var courses = [];
    var setCourses = function (data) {
        courses = data;
    };
    var coursesUrl = 'http://localhost:49999/courses/';
    getData(coursesUrl, setCourses);

    /* SeminarsControl */
    var seminars = [];
    var setSeminars = function (data) {
        seminars = data;
    };
    var seminarsUrl = 'http://localhost:49999/seminars/';
    getData(seminarsUrl, setSeminars);

    /* SubjectsControl */
    var subjects = [];
    var setSubjects = function (data) {
        subjects = data;
    };
    var subjectsUrl = 'http://localhost:49999/subjects/';
    getData(subjectsUrl, setSubjects);

    /* SubjectTypes */
    var subjectTypes = {};
    var setSubjectTypes = function (data) {
        subjectTypes = data;
    };
    var subjectTypesUrl = 'http://localhost:49999/subjecttypes/';
    getData(subjectTypesUrl, setSubjectTypes);

    /* SeminarTypes */
    var seminarTypes = {};
    var setSeminarTypes = function (data) {
        seminarTypes = data;
    };
    var seminarTypesUrl = 'http://localhost:49999/seminartypes/';
    getData(seminarTypesUrl, setSeminarTypes);


    return {
        createDocent: function (docent) {
            createData(docentsUrl, JSON.stringify(docent), docentsUrl, setDocents);
        },
        updateDocent: function (docent) {
            updateData(docentsUrl + docent.id, JSON.stringify(docent), docentsUrl, setDocents);
        },
        deleteDocent: function (docent) {
            deleteData(docentsUrl, docent.id, docentsUrl, setDocents);
        },
        getDocents: function () {
            return docents;
        },
        createRoom: function (room) {
            createData(roomsUrl, JSON.stringify(room), roomsUrl, setRooms);
        },
        updateRoom: function (room) {
            updateData(roomsUrl + room.id, JSON.stringify(room), roomsUrl, setRooms);
        },
        deleteRoom: function (room) {
            deleteData(roomsUrl, room.id, roomsUrl, setRooms);
        },
        getRooms: function () {
            return rooms;
        },
        createCentury: function (century) {
            century.type = 'century'
            createData(centuriesUrl, JSON.stringify(century), centuriesUrl, setCenturies);
        },
        updateCentury: function (century) {
            updateData(centuriesUrl + century.id, JSON.stringify(century), centuriesUrl, setCenturies);
        },
        deleteCentury: function (century) {
            deleteData(centuriesUrl, century.id, centuriesUrl, setCenturies);
        },
        getCenturies: function () {
            return centuries;
        },
        createManiple: function (maniple) {
            maniple.type = 'maniple';
            createData(maniplesUrl, JSON.stringify(maniple), maniplesUrl, setManiples);
        },
        updateManiple: function (maniple) {
            updateData(maniplesUrl + maniple.id, JSON.stringify(maniple), maniplesUrl, setManiples);
        },
        deleteManiple: function (maniple) {
            deleteData(maniplesUrl, maniple.id, maniplesUrl, setManiples);
        },
        getManiples: function () {
            return maniples;
        },
        createCohort: function (cohort) {
            cohort.type='cohort';
            createData(cohortsUrl, JSON.stringify(cohort), cohortsUrl, setCohorts);
        },
        updateCohort: function (cohort) {
            updateData(cohortsUrl + cohort.id, JSON.stringify(cohort), cohortsUrl, setCohorts);
        },
        deleteCohort: function (cohort) {
            deleteData(cohortsUrl, cohort.id, cohortsUrl, setCohorts);
        },
        getCohorts: function () {
            return cohorts;
        },
        createCourse: function (course) {
            course.type = 'course';
            createData(coursesUrl, JSON.stringify(course), coursesUrl, setCourses);
        },
        updateCourse: function (course) {
            updateData(coursesUrl + course.id, JSON.stringify(course), coursesUrl, setCourses);
        },
        deleteCourse: function (course) {
            deleteData(coursesUrl, course.id, coursesUrl, setCourses);
        },
        getCourses: function () {
            return courses;
        },
        createSeminar: function (seminar) {
            seminar.type = 'seminar';
            createData(seminarsUrl, JSON.stringify(seminar), seminarsUrl, setSeminars);
        },
        updateSeminar: function (seminar) {
            updateData(seminarsUrl + seminar.id, JSON.stringify(seminar), seminarsUrl, setSeminars);
        },
        deleteSeminar: function (seminar) {
            deleteData(seminarsUrl, seminar.id, seminarsUrl, setSeminars);
        },
        getSeminars: function () {
            return seminars;
        },
        createSubject: function (subject) {
            createData(subjectsUrl, JSON.stringify(subject), subjectsUrl, setSubjects);
        },
        updateSubject: function (subject) {
            updateData(subjectsUrl + subject.id, JSON.stringify(subject), subjectsUrl, setSubjects);
        },
        deleteSubject: function (subject) {
            deleteData(subjectsUrl, subject.id, subjectsUrl, setSubjects);
        },
        getSubjects: function () {
            return subjects;
        },
        getSubjectTypes: function () {
            return subjectTypes;
        },
        getSeminarTypes: function () {
            return seminarTypes;
        }
    }
});