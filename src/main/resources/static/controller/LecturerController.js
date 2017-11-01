'use strict'

app.controller('LecturerController', function($scope , $http) {

    // language=JSRegexp
    $scope.ph_numbr = /^(\+?(\d{1}|\d{2}|\d{3})[- ]?)?\d{3}[- ]?\d{3}[- ]?\d{4}$/;

    var url = 'http://localhost:49999/docents/';

    $http.get(url).
    then(function(response) {
        $scope.docents = response.data;
    });

    $scope.addLecturer = function () {
        var item = {};
        this.setSelectedLecturerId(-1);
        this.setLecturer(item);
    };

    $scope.editLecturer = function (item) {
        this.setSelectedLecturerId(item.id);
        this.setLecturer(item);
    };

    $scope.setLecturer = function (item) {
        $scope.forename = item.forename;
        $scope.surname = item.surname;
        $scope.email = item.email;
        $scope.phoneNumber = item.phoneNumber;
        $scope.title = item.title;
        $scope.isPermanentlyEmployed = item.permanentlyEmployed;
        $scope.minChangeoverTime = item.minChangeoverTime;
    };

    $scope.getCurrentLecturerJson = function () {
        return {
            "forename": $scope.forename,
            "surname": $scope.surname,
            "email": $scope.email,
            "phoneNumber": $scope.phoneNumber,
            "title": $scope.title,
            "PermanentlyEmployed": $scope.isPermanentlyEmployed,
            "minChangeoverTime": $scope.minChangeoverTime
        };
    };

    $scope.setSelectedLecturerId = function (lecturerId) {
        $scope.actualModal = (lecturerId === -1);
    };

    $scope.createData = function() {
        $http.post(url, $scope.getCurrentLecturerJson())
            .then(function successCallback(data) {
                window.location.reload();
                console.log(data);
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function (){
        $http.put(url, this.getCurrentLecturerJson())
            .then(function successCallback(data) {
                window.location.reload();
                console.log(data);
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
        });
        this.setSelectedLecturerId(-1);
    };

    $scope.deleteData = function() {
        $http.delete(url + $scope.lecturerId)
            .then(function successCallback(data) {
                window.location.reload();
                console.log(data);
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
        this.setSelectedLecturerId(-1);
    };
});