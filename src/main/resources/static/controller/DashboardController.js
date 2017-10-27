'use strict'

app.controller('DashboardController', function($scope , $http) {
    console.log("test");
    $http.get('http://localhost:49999/rooms').
    then(function(response) {
        $scope.rooms = response.data;
    });
    console.log($scope.rooms);
})