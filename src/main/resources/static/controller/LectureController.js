'use strict'

app.controller('LectureController', function($scope , $http) {

    $http.get('http://localhost:49999/lectures').
    then(function(response) {
        $scope.lectures = response.data;
    });

    $scope.updateData = function (){
        var data = $.param({
            firstName: $scope.firstName,
            lastName: $scope.lastName,
            age: $scope.age
        });

        $http.put('http://localhost:49999/lectures', data)
            .success(function (data) {
                $scope.ServerResponse = data;
            })
            .error(function (data, status, header, config) {
                $scope.ServerResponse =  htmlDecode("Data: " + data +
                    "\n\n\n\nstatus: " + status +
                    "\n\n\n\nheaders: " + header +
                    "\n\n\n\nconfig: " + config);
            });
    }

})