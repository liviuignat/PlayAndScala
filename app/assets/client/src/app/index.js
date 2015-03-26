'use strict';
/*jshint esnext: true */

angular.module('app', ['ngAnimate', 'ngCookies', 'ngTouch', 'ngSanitize', 'ngResource', 'ngRoute', 'ui.bootstrap'])

  .config(function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/login', {
        templateUrl: 'app/auth/login/login.tpl.html',
        controller: 'LoginController'
      })
      .when('/register', {
        templateUrl: 'app/auth/createaccount/createaccount.tpl.html',
        controller: 'CreateAccountController'
      })
      .when('/resetpassword', {
        templateUrl: 'app/auth/resetpassword/resetpassword.tpl.html',
        controller: 'ResetPasswordController'
      })
      .otherwise({
        redirectTo: '/login'
      });

    $locationProvider.html5Mode(true).hashPrefix('!');
  });
