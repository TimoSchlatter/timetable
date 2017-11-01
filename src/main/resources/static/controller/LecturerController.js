'use strict'

app.controller('LecturerController', function($scope , $http) {

    $scope.ph_numbr = /^(\+?(\d{1}|\d{2}|\d{3})[- ]?)?\d{3}[- ]?\d{3}[- ]?\d{4}$/;
    $scope.updateId = 0;

    $http.get('http://localhost:49999/docents').
    then(function(response) {
        $scope.docents = response.data;
    });

    $scope.setCurrentItemId = function (itemId) {
        $scope.itemId = itemId;
        $scope.actualModal = ($scope.itemId == -1);
        $scope.apply();
    }


    $scope.deleteData = function() {
        var url = 'http://localhost:49999/docents/' + $scope.itemId;
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
        this.setCurrentItemId(-1);
    }

    $scope.createData = function() {
        var lecturerData = {
            "forename": $scope.forename,
            "surname": $scope.surname,
            "email": $scope.email,
            "phoneNumber": $scope.phoneNumber,
            "title": $scope.title,
            "permanentlyEmployed": $scope.anstellung,
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
        this.setCurrentItemId(-1);
    }

})