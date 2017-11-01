'use strict'

app.controller('LectureController', function($scope , $http) {

    $http.get('http://localhost:49999/lectures').
    then(function(response) {
        $scope.lectures = response.data;
    });

    $http.get('http://localhost:49999/lectures').
    then(function(response) {
        $scope.courses = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.itemId = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/lectures/' + $scope.itemId;
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
        var lectureData = {
            "course": $scope.course,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.post('http://localhost:49999/lectures', lectureData)
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
        var lectureData = {
            "course": $scope.course,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.put('http://localhost:49999/exams', lectureData)
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