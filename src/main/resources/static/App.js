'use strict'

var app = angular.module('roomManagement', ["ngRoute", 'ui.calendar']);

    app.config(function($routeProvider){
        $routeProvider.when("/", {
            templateUrl: "view/dashboard.html",
            name: "Dashboard",
            controller: "DashboardController"
        }).when("/rooms", {
            templateUrl: "view/room.html",
            name: "Rooms",
            controller: "RoomController"
        }).when("/docents", {
            templateUrl: "view/docent.html",
            name: "Docents",
            controller: "DocentController"
        }).when("/courses", {
            templateUrl: "view/course.html",
            name: "Courses",
            controller: "CourseController"
        }).when("/cohorts", {
            templateUrl: "view/cohort.html",
            name: "Cohorts",
            controller: "CohortController"
        }).when("/maniples", {
            templateUrl: "view/maniple.html",
            name: "Maniples",
            controller: "ManipleController"
        }).when("/centuries", {
            templateUrl: "view/century.html",
            name: "Centuries",
            controller: "CenturyController"
        }).when("/events", {
            templateUrl: "view/event.html",
            name: "Events",
            controller: "EventController"
        }).when("/subjects", {
            templateUrl: "view/subject.html",
            name: "Subjects",
            controller: "SubjectController"
        }).when("/seminars", {
            templateUrl: "view/seminar.html",
            name: "Seminars",
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
