"use strict";
'use strict';
angular.module('app', ['ngAnimate', 'ngCookies', 'ngTouch', 'ngSanitize', 'ngResource', 'ngRoute', 'ui.bootstrap']).config(function($routeProvider, $locationProvider) {
  $routeProvider.when('/', {
    templateUrl: 'app/auth/login/login.tpl.html',
    controller: 'LoginController'
  }).when('/resetpassword', {
    templateUrl: 'app/auth/resetpassword/resetpassword.tpl.html',
    controller: 'ResetPasswordController'
  }).otherwise({redirectTo: '/'});
  $locationProvider.html5Mode(true).hashPrefix('!');
});
