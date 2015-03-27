'use strict';
angular.module('app', [
  'ngAnimate',
  'ngCookies',
  'ngTouch',
  'ngSanitize',
  'ngResource',
  'ngRoute',
  'ui.bootstrap',
  'angular-md5'])

  .config(function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/login', {
        templateUrl: 'app/auth/login/login.tpl.html',
        controller: 'LoginController',
        controllerAs:'model'
      })
      .when('/register', {
        templateUrl: 'app/auth/createaccount/createaccount.tpl.html',
        controller: 'CreateAccountController',
        controllerAs:'model'
      })
      .when('/resetpassword', {
        templateUrl: 'app/auth/resetpassword/resetpassword.tpl.html',
        controller: 'ResetPasswordController',
        controllerAs:'model'
      })
      .otherwise({
        redirectTo: '/login'
      });

    $locationProvider.html5Mode(true).hashPrefix('!');
  });
