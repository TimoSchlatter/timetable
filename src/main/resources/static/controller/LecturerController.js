'use strict'

app.controller('LecturerController', function($scope , $http) {

    $http.get('http://localhost:49999/docents').
    then(function(response) {
        $scope.docents = response.data;
        console.log(response.data)
    });

    $scope.updateData = function (){
        var data = $.param({
           forename: $scope.forename,
            surname: $scope.surname,
            email: $scope.email,
            phoneNumber: $scope.phoneNumber,
            title: $scope.title,
            isPermanentlyEmployed: true
        });

        $http.put('http://localhost:49999/docents', data)
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