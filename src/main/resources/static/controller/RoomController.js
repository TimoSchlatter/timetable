'use strict'

app.controller('RoomController', function($scope , $http) {

    $http.get('http://localhost:49999/rooms').
    then(function(response) {
        $scope.rooms = response.data;
    });

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/rooms/' + $scope.id;
        $http.delete(url)
            .then(function (data) {
                $scope.ServerResponse = data;
            })
            .then(function (data, status, header, config) {
                $scope.ServerResponse =  htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
            });
    }

    $scope.createData = function() {
        var roomData = {
            "building": $scope.building,
            "number": $scope.number,
            "maxSeats": $scope.maxSeats,
            "roomType": $scope.roomType
        };

        $http.post('http://localhost:49999/rooms', roomData)
            .then(function (data) {
                $scope.ServerResponse = data;
            })
            .then(function (data, status, header, config) {
                $scope.ServerResponse =  htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
            });
    }

    $scope.updateData = function (){
        var roomData = {
            "building": $scope.building,
            "number": $scope.number,
            "maxSeats": $scope.maxSeats,
            "roomType": $scope.roomType
        };

        $http.put('http://localhost:49999/rooms', roomData)
            .then(function (data) {
                $scope.ServerResponse = data;
            })
            .then(function (data, status, header, config) {
                $scope.ServerResponse =  htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
            });
    }

})