'use strict';

app.controller('DocentController', function ($scope, ConnectionService) {

    // language=JSRegexp
    $scope.ph_numbr = /^(\+?(\d{1}|\d{2}|\d{3})[- ]?)?\d{3}[- ]?\d{3}[- ]?\d{4}$/;

    $scope.setSelectedDocent = function (docent) {
        $scope.docent = angular.copy(docent);
        console.log('Selected Docent:', $scope.docent);
    };

    $scope.docents = ConnectionService.getDocents;
    $scope.createDocent = ConnectionService.createDocent;
    $scope.updateDocent = ConnectionService.updateDocent;
    $scope.deleteDocent = ConnectionService.deleteDocent;
});