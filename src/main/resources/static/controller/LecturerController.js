'use strict'

app.controller('LecturerController', function($scope , $http) {

    $scope.ph_numbr = /^(\+?(\d{1}|\d{2}|\d{3})[- ]?)?\d{3}[- ]?\d{3}[- ]?\d{4}$/;

    $http.get('http://localhost:49999/docents').
    then(function(response) {
        $scope.docents = response.data;
    });

    $scope.addLecturer = function () {
        this.setCurrentItemId(-1);
        $scope.forename = '';
        $scope.surname = '';
        $scope.email = '';
        $scope.phone_Number = '';
        $scope.title = '';
        $scope.isPermanentlyEmployed = ''
        $scope.minChangeoverTime = '';
    }

    $scope.editLecturer = function (item) {
        this.setCurrentItemId(item.id)
        $scope.forename = item.forename;
        $scope.surname = item.surname;
        $scope.email = item.email;
        $scope.phone_Number = item.phoneNumber;
        $scope.title = item.title;
        $scope.isPermanentlyEmployed = item.permanentlyEmployed;
        $scope.minChangeoverTime = item.minChangeoverTime;

    }

    $scope.setCurrentItemId = function (itemId) {
        $scope.itemId = itemId;
        $scope.actualModal = ($scope.itemId == -1);
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
            "permanentlyEmployed": $scope.isPermanentlyEmployed,
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
            "PermanentlyEmployed": $scope.isPermanentlyEmployed,
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