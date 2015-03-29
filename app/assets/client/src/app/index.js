'use strict';
angular.module('app', [
  'ngAnimate',
  'ngCookies',
  'ngBiscuit',
  'ngTouch',
  'ngSanitize',
  'ngResource',
  'ngRoute',
  'ui.bootstrap',
  'angular-md5'])

  .config(function ($routeProvider, $locationProvider, $httpProvider) {
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
      .when('/search', {
        templateUrl: 'app/search/searchuser.tpl.html',
        controller: 'SearchUserController',
        controllerAs:'model'
      })
      .when('/user/:id', {
        templateUrl: 'app/user/detail/userdetail.tpl.html',
        controller: 'UserDetailController',
        controllerAs:'model'
      })
      .otherwise({
        redirectTo: '/login'
      });

    $httpProvider.interceptors.push('AuthInterceptor');

    $locationProvider.html5Mode(true).hashPrefix('!');
  });
