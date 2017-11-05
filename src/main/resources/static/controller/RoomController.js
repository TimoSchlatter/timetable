'use strict';

app.controller('RoomController', function ($scope, $http) {

    var roomsUrl = 'http://localhost:49999/rooms/';
    var roomTypesUrl = 'http://localhost:49999/roomtypes/';

    $http.get(roomTypesUrl).then(function successCallback(response) {
        $scope.roomTypes = response.data;
    }, function errorCallback(response) {
        console.log(response.statusText);
    });

    var getData = function () {
        $http.get(roomsUrl).then(function successCallback(response) {
            $scope.rooms = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    };

    getData();

    $scope.setSelectedRoom = function (room) {
        $scope.room = angular.copy(room);
    };

    $scope.createData = function () {
        $http.post(roomsUrl, JSON.stringify(this.room))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.updateData = function () {
        $http.put(roomsUrl + this.room.id, JSON.stringify(this.room))
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };

    $scope.deleteData = function () {
        $http.delete(roomsUrl + this.room.id)
            .then(function successCallback(data) {
                console.log(data);
                getData();
            }, function errorCallback(data, status, header, config) {
                console.log(data, status, header, config);
            });
    };
});