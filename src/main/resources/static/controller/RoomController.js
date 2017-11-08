'use strict';

app.controller('RoomController', function ($scope, $http, ConnectionService) {

    var roomTypesUrl = 'http://localhost:49999/roomtypes/';

    $http.get(roomTypesUrl).then(function successCallback(response) {
        $scope.roomTypes = response.data;
    }, function errorCallback(response) {
        console.log(response.statusText);
    });

    $scope.setSelectedRoom = function (room) {
        $scope.room = angular.copy(room);
        console.log('Selected Room:', $scope.room);
    };

    $scope.rooms = ConnectionService.getRooms;
    $scope.createRoom = ConnectionService.createRoom;
    $scope.updateRoom = ConnectionService.updateRoom;
    $scope.deleteRoom = ConnectionService.deleteRoom;
});