"use strict";

var myApp = angular.module("myApp", ['ngRoute'])
.config(['$routeProvider', '$locationProvider',
  function($routeProvider, $locationProvider) {
    $routeProvider
    .when('/Set', {
        templateUrl: './javascripts/configurationView.html',
        controller: 'memoryGameController'
    })  
    .when('/Go', {
        templateUrl: './javascripts/boardView.html',
        controller: 'memoryGameController'
    })
    .otherwise({
        redirectTo: '/Set'
    });
}]);