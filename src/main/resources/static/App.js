'use strict'

var app = angular.module('roomManagement', ["ngRoute"]);

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
        }).when("/course", {
            templateUrl: "view/course.html",
            name: "Course",
            controller: "CourseController"
        }).when("/exam", {
            templateUrl: "view/exam.html",
            name: "Exam",
            controller: "ExamController"
        }).when("/kohorte", {
            templateUrl: "view/kohorte.html",
            name: "Kohorte",
            controller: "KohorteController"
        }).when("/manipel", {
            templateUrl: "view/manipel.html",
            name: "Manipel",
            controller: "ManipelController"
        }).when("/zenturie", {
            templateUrl: "view/zenturie.html",
            name: "Zenturie",
            controller: "ZenturieController"
        }).when("/seminar", {
            templateUrl: "view/seminar.html",
            name: "Seminar",
            controller: "SeminarController"
        }).otherwise({
        redirectTo: "/"})
    });

    app.config(['$locationProvider', function($locationProvider) {
        $locationProvider.hashPrefix('');
    }]);

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
