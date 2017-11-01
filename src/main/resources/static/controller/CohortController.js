'use strict'

app.controller('CohortController', function($scope , $http) {

    $http.get('http://localhost:49999/cohorts').
    then(function(response) {
        $scope.cohorts = response.data;
    });

    $scope.currentId = function (itemId) {
        $scope.itemId = itemId;
    }

    $scope.deleteData = function() {
        var url = 'http://localhost:49999/cohorts/' + $scope.itemId;
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
        var cohortData = {
            "name": $scope.name,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.post('http://localhost:49999/cohorts', cohortData)
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
        var cohortData = {
            "name": $scope.name,
            "minChangeoverTime": $scope.minChangeoverTime
        };

        $http.put('http://localhost:49999/cohorts', cohortData)
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