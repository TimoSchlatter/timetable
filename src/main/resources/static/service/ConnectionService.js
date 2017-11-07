app.factory('ConnectionService', function ($http) {

    var getData = function (url, functionToUpdate) {
        $http.get(url).then(function successCallback(response) {
            functionToUpdate(response.data);
        }, function errorCallback(response) {
            console.log(response.statusText);
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
        }
    }
});