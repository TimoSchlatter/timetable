'use strict'

app.controller('SeminarController', function($scope , $http) {

    $http.get('http://localhost:49999/seminars').
    then(function(response) {
        $scope.seminars = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.itemId = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/seminars/' + $scope.itemId;
        console.log($scope.index);
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
        var seminarData = {
            "title": $scope.title,
            "maxNumberOfParticipants": $scope.maxNumberOfParticipants,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.post('http://localhost:49999/seminars', seminarData)
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
        var seminarData = {
            "title": $scope.title,
            "maxNumberOfParticipants": $scope.maxNumberOfParticipants,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.put('http://localhost:49999/seminars', seminarData)
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