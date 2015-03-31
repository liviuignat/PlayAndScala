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
  'angular-md5']);

  angular.module('app').config(function ($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
  });

  angular.module('app').config(function ($routeProvider, $locationProvider) {
    $routeProvider.caseInsensitiveMatch = true;
    $routeProvider
      .when('/login', {
        templateUrl: 'app/auth/login/login.tpl.html',
        controller: 'LoginController',
        controllerAs:'model',
        publicAccess: true
      })
      .when('/register', {
        templateUrl: 'app/auth/createaccount/createaccount.tpl.html',
        controller: 'CreateAccountController',
        controllerAs:'model',
        publicAccess: true
      })
      .when('/resetpassword', {
        templateUrl: 'app/auth/resetpassword/resetpassword.tpl.html',
        controller: 'ResetPasswordController',
        controllerAs:'model',
        publicAccess: true
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

    $locationProvider.html5Mode(true).hashPrefix('!');
  });
  angular.module('app').run(function($injector, $window) {
    var appBase = '/app/';
    var isOnApp = $window.location.pathname.indexOf(appBase) === 0;
    var authService = $injector.get('AuthService');
    var $rootScope = $injector.get('$rootScope');

    $rootScope.isLoggedIn = authService.isLoggedIn();

    if(isOnApp) {
      var $route = $injector.get('$route');
      var $location = $injector.get('$location');

      var routesOpenToPublic = [];
      angular.forEach($route.routes, function(route, path) {
        if(route.publicAccess) {
          (routesOpenToPublic.push(path));
        }
      });

      $rootScope.$on('$routeChangeStart', function() {
        var closedToPublic = (-1 === routesOpenToPublic.indexOf($location.path()));
        if(closedToPublic && !authService.isLoggedIn()) {
           $location.path('/login');
        }
      });
    }
  });
