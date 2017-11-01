'use strict'

app.controller('ExamController', function($scope , $http) {

    $http.get('http://localhost:49999/exams').
    then(function(response) {
        $scope.exams = response.data;
    });

    $http.get('http://localhost:49999/courses').
    then(function(response) {
        $scope.courses = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.currentId = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/exams/' + $scope.id;
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
        var examData = {
            "course": $scope.course,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.post('http://localhost:49999/exams', examData)
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
        var examData = {
            "course": $scope.course,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.put('http://localhost:49999/exams', examData)
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