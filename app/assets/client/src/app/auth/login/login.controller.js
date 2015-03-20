/*jshint esnext: true */
(function (app) {
  'use strict';

  class LoginController {
    constructor ($scope) {
      $scope.user = {
        email: 'liviu@ignat.email',
        password: 'test123'
      };
    }
  }

  LoginController.$inject = ['$scope'];
  app.controller('LoginController', LoginController);

}(angular.module('app')));
