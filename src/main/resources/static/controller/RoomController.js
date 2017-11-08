'use strict';

app.controller('RoomController', function ($scope, ConnectionService) {

    $scope.rooms = ConnectionService.getRooms;
    $scope.createRoom = ConnectionService.createRoom;
    $scope.updateRoom = ConnectionService.updateRoom;
    $scope.deleteRoom = ConnectionService.deleteRoom;
    $scope.roomTypes = ConnectionService.getRoomTypes;

    $scope.setSelectedRoom = function (room) {
        $scope.room = angular.copy(room);
        console.log('Selected Room:', $scope.room);
    };
});