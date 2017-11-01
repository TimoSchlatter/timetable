'use strict'

app.controller('CourseController', function($scope , $http) {

    $http.get('http://localhost:49999/courses').
    then(function(response) {
        $scope.courses = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.id = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/courses/' + $scope.id;
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
        var courseData = {
            "field": $scope.field,
            "number": $scope.number,
            "title": $scope.title,
            "shortTitle": $scope.shortTitle
        };

        $http.post('http://localhost:49999/courses', courseData)
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
        var courseData = {
            "field": $scope.field,
            "number": $scope.number,
            "title": $scope.title,
            "shortTitle": $scope.shortTitle
        };

        $http.put('http://localhost:49999/courses', courseData)
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