"use strict";
(function(app) {
  'use strict';
  var LoginController = function LoginController($scope) {
    $scope.user = {
      email: 'liviu@ignat.email',
      password: 'test123'
    };
  };
  ($traceurRuntime.createClass)(LoginController, {}, {});
  LoginController.$inject = ['$scope'];
  app.controller('LoginController', LoginController);
}(angular.module('app')));
