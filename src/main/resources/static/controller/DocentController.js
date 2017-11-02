'use strict';

app.controller('DocentController', function ($scope, $http) {

    // language=JSRegexp
    $scope.ph_numbr = /^(\+?(\d{1}|\d{2}|\d{3})[- ]?)?\d{3}[- ]?\d{3}[- ]?\d{4}$/;

    var docentsUrl = 'http://localhost:49999/docents/';

    var getData = function () {
        $http.get(docentsUrl).then(function successCallback(response) {
            $scope.docents = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedDocent = function (docent) {
        $scope.docent = angular.copy(docent);
    };

    $scope.createData = function () {
        $http.post(docentsUrl, JSON.stringify(this.docent))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(docentsUrl + this.docent.id, JSON.stringify(this.docent))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(docentsUrl + this.docent.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});