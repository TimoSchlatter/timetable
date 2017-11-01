'use strict'

app.controller('ManipelController', function($scope , $http) {

    $http.get('http://localhost:49999/maniples').
    then(function(response) {
        $scope.manipel = response.data;
    });

    $http.get('http://localhost:49999/maniples').
        then(function(response) {
            $scope.cohorts = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.itemId = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/maniples/' + $scope.itemId;
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

    $scope.createData = function(cohorteId) {
        var manipleData = {
            "name": $scope.name,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        var url = 'http://localhost:49999/cohorts/' + cohorteId + '/addManipel';


        $http.post(url, manipleData)
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
        var manipleData = {
            "name": $scope.name,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.put('http://localhost:49999/maniples', manipleData)
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