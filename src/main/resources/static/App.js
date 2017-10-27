'use strict'

var app = angular .module('roomManagement', ["ngRoute"]);

    app.controller('HeaderController', function(){
    });

    app.controller('DashboardController', function($scope, $http) {
                 console.log("test");
                 $http.get('http://localhost:49999/rooms').
                 then(function(response) {
                     $scope.rooms = response.data;
                 });
                 console.log($scope.rooms);
             });

    app.config(function($routeProvider){
        $routeProvider.when("/", {
            templateUrl: "view/dashboard.html",
            name: "Dashboard",
            controller: "DashboardController"
        }).when("/room", {
            templateUrl: "view/room.html",
            name: "RoomView",
            controller: "RoomController"
        }).when("/lecturer", {
            templateUrl: "view/lecturer.html",
            name: "Lecturer",
            controller: "LecturerController"
        }).when("/lecture", {
            templateUrl: "view/lecture.html",
            name: "Lecture",
            controller: "LectureController"
        }).when("/group", {
            templateUrl: "view/group.html",
            name: "Group",
            controller: "GroupController"
        }).otherwise({
        redirectTo: "/"})
    });

    app.factory('routeNavigation', function($route, $location) {
          var routes = [];
          angular.forEach($route.routes, function (route, path) {
            if (route.name) {
              routes.push({
                path: path,
                name: route.name,
                controller: route.controller
              });
            }
          });
          return {
            routes: routes,
            activeRoute: function (route) {
              return route.path === $location.path();
            }
          };
    });
