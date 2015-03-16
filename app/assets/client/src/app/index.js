'use strict';
/*jshint esnext: true */

import LoginController from './auth/login/login.controller';
import ResetPasswordController from './auth/resetpassword/resetpassword.controller';

angular.module('app', ['ngAnimate', 'ngCookies', 'ngTouch', 'ngSanitize', 'ngResource', 'ngRoute', 'ui.bootstrap'])
  .controller('ResetPasswordController', ResetPasswordController)
  .controller('LoginController', LoginController)

  .config(function ($routeProvider, $locationProvider) {
    $routeProvider
      .when('/', {
        templateUrl: '/app/auth/login/login.tpl.html',
        controller: 'LoginController'
      })
      .when('/resetpassword', {
        templateUrl: '/app/auth/resetpassword/resetpassword.tpl.html',
        controller: 'ResetPasswordController'
      })
      .otherwise({
        redirectTo: '/'
      });

    $locationProvider.html5Mode(true).hashPrefix('!');
  });
