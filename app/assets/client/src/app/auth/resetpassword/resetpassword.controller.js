 /*jshint esnext: true */
(function (app) {
  'use strict';

  class ResetPasswordController {
    constructor ($scope) {
      $scope.email = 'liviu@ignat.email';
    }
  }

  ResetPasswordController.$inject = ['$scope'];
  app.controller('ResetPasswordController', ResetPasswordController);

}(angular.module('app')));
