'use strict'

app.controller('LecturerController', function($scope , $http) {

    $http.get('http://localhost:49999/docents').
    then(function(response) {
        $scope.docents = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.id = itemId;
    }


    $scope.deleteData = function(id) {
        var url = 'http://localhost:49999/docents/' + id;
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
        var lecturerData = {
            "forename": $scope.forename,
            "surname": $scope.surname,
            "email": $scope.email,
            "phoneNumber": $scope.phoneNumber,
            "title": $scope.title,
            "isPermanentlyEmployed": true,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.post('http://localhost:49999/docents', lecturerData)
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
        var lecturerData = {
            "forename": $scope.forename,
            "surname": $scope.surname,
            "email": $scope.email,
            "phoneNumber": $scope.phoneNumber,
            "title": $scope.title,
            "isPermanentlyEmployed": true,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.put('http://localhost:49999/docents', lecturerData)
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