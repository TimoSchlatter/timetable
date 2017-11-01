'use strict'

app.controller('DocentController', function ($scope, $http) {

    // language=JSRegexp
    $scope.ph_numbr = /^(\+?(\d{1}|\d{2}|\d{3})[- ]?)?\d{3}[- ]?\d{3}[- ]?\d{4}$/;

    var url = 'http://localhost:49999/docents/';

    var getData = function () {
        $http.get(url).then(function successCallback(response) {
            $scope.docents = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedDocent = function (docent) {
        $scope.docent = docent;
    };

    $scope.createData = function () {
        $http.post(url, JSON.stringify(this.docent))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(url, JSON.stringify(this.docent))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(url + this.docent.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });

    };
});