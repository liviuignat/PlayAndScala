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
  app.controller('LoginController', LoginController);
  LoginController.$inject = ['$scope'];
}(angular.module('app')));
