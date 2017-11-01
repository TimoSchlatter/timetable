'use strict'

app.controller('CenturyController', function($scope , $http) {

    $http.get('http://localhost:49999/centuries').
    then(function(response) {
        $scope.century = response.data;
    });

    $http.get('http://localhost:49999/maniples').
    then(function(response) {
        $scope.maniple = response.data;
    });

    $http.get('http://localhost:49999/cohorts').
    then(function(response) {
        $scope.cohorts = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.itemId = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/centuries/' + $scope.itemId;
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

    $scope.createData = function(manipleId) {
        var centuryData = {
            "name": $scope.name,
            "numberOfStudents": $scope.numberOfStudents,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        var url = 'http://localhost:49999/maniples/' + manipleId + '/addCentury';


        $http.post(url, centuryData)
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
        var centuryData = {
            "name": $scope.name,
            "numberOfStudents": $scope.numberOfStudents,
            "minChangeoverTime": $scope.minChangeoverTime
        };


        $http.put('http://localhost:49999/maniples', centuryData)
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