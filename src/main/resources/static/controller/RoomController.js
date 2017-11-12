'use strict';

/*
 AngularJS Controller
 Controller is defined to augment the Room View.
 The Controller is attached to the DOM via the ng-controller directive.
 @author Jonas Gehrke
 */

app.controller('RoomController', function ($scope, ConnectionService) {

    $scope.rooms = ConnectionService.getRooms;
    $scope.createRoom = ConnectionService.createRoom;
    $scope.updateRoom = ConnectionService.updateRoom;
    $scope.deleteRoom = ConnectionService.deleteRoom;
    $scope.roomTypes = ConnectionService.getRoomTypes;
    $scope.roomsLastUpdateTime = ConnectionService.getRoomsLastUpdateTime;

    $scope.setSelectedRoom = function (room) {
        $scope.room = angular.copy(room);
        console.log('Selected Room:', $scope.room);
    };
});