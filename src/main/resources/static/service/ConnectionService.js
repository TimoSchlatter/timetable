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

    /* DocentControl */
    var docents = [];
    var setDocents = function (data) {
        docents = data
    };
    var docentsUrl = 'http://localhost:49999/docents/';
    getData(docentsUrl, setDocents);

    /* RoomControl */
    var rooms = [];
    var setRooms = function (data) {
        rooms = data
    };
    var roomsUrl = 'http://localhost:49999/rooms/';
    getData(roomsUrl, setRooms);

    /* CenturyControl */
    var centuries = [];
    var setCenturies = function (data) {
        centuries = data
    };
    var centuryUrl = 'http://localhost:49999/centuries/';
    getData(centuryUrl, setCenturies);

    /*ManipleControl */
    var maniple = [];
    var setManiples = function (data) {
        maniples = data;
    };
    var manipleUrl =  'http://localhost:49999/maniples/';
    getData(manipleUrl, setManiples);

    /*CohortControl */
    var cohort = [];
    var setCohorts = function (data) {
        cohorts = data;
    };
    var cohortUrl =  'http://localhost:49999/cohorts/';
    getData(cohortUrl, setCohorts);

    /*CourseControl */
    var course = [];
    var setCourses = function (data) {
        courses = data;
    };
    var courseUrl =  'http://localhost:49999/courses/';
    getData(courseUrl, setCourses);

    /* SeminarControl */
    var seminars = [];
    var setSeminars = function (data) {
        seminars = data;
    };
    var seminarUrl =  'http://localhost:49999/seminars/';
    getData(seminarUrl, setSeminars);

    /* SubjectControl */
    var seminars = [];
    var setSubjects = function (data) {
        subjects = data;
    };
    var subjectUrl =  'http://localhost:49999/subjects/';
    getData(subjectUrl, setSubjects);


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
            createData(centuryUrl, JSON.stringify(century), centuryUrl, setCenturies);
        },
        updateCentury: function (century) {
            updateData(centuryUrl + century.id, JSON.stringify(century), centuryUrl, setCenturies);
        },
        deleteCentury: function (century) {
            deleteData(centuryUrl, century.id, centuryUrl, setCenturies);
        },
        getCenturies: function () {
            return centuries;
        },
        createManiple: function (maniple) {
            maniple.type = 'maniple';
            createData(manipleUrl, JSON.stringify(maniple), manipleUrl, setManiples);
        },
        updateManiple: function (maniple) {
            updateData(manipleUrl + maniple.id, JSON.stringify(maniple), manipleUrl, setManiples);
        },
        deleteManiple: function (maniple) {
            deleteData(manipleUrl, maniple.id, manipleUrl, setManiples);
        },
        getManiples: function () {
            return maniples;
        },
        createCohort: function (cohort) {
            cohort.type='cohort';
            createData(cohortUrl, JSON.stringify(cohort), cohortUrl, setCohorts);
        },
        updateCohort: function (cohort) {
            updateData(cohortUrl + cohort.id, JSON.stringify(cohort), cohortUrl, setCohorts);
        },
        deleteCohort: function (cohort) {
            deleteData(cohortUrl, cohort.id, cohortUrl, setCohorts);
        },
        getCohorts: function () {
            return cohorts;
        },
        createCourse: function (course) {
            course.type = 'course';
            createData(courseUrl, JSON.stringify(course), courseUrl, setCourses);
        },
        updateCourse: function (course) {
            updateData(courseUrl + course.id, JSON.stringify(course), courseUrl, setCourses);
        },
        deleteCourse: function (course) {
            deleteData(courseUrl, course.id, courseUrl, setCourses);
        },
        getCourses: function () {
            return courses;
        },
        createSeminar: function (seminar) {
            seminar.type = 'seminar';
            createData(seminarUrl, JSON.stringify(seminar), seminarUrl, setSeminars);
        },
        updateSeminar: function (seminar) {
            updateData(seminarUrl + seminar.id, JSON.stringify(seminar), seminarUrl, setSeminars);
        },
        deleteSeminar: function (seminar) {
            deleteData(seminarUrl, seminar.id, seminarUrl, setSeminars);
        },
        getSeminars: function () {
            return seminars;
        },
        createSubject: function (subject) {
            createData(subjectUrl, JSON.stringify(subject), subjectUrl, setSubjects);
        },
        updateSubject: function (subject) {
            updateData(subjectUrl + subject.id, JSON.stringify(subject), subjectUrl, setSubjects);
        },
        deleteSubject: function (subject) {
            deleteData(subjectUrl, subject.id, subjectUrl, setSubjects);
        },
        getSubjects: function () {
            return subjects;
        }
    }
});