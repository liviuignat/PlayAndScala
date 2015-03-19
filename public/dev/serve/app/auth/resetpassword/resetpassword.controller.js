"use strict";
(function(app) {
  'use strict';
  var ResetPasswordController = function ResetPasswordController($scope) {
    $scope.email = 'liviu@ignat.email';
  };
  ($traceurRuntime.createClass)(ResetPasswordController, {}, {});
  ResetPasswordController.$inject = ['$scope'];
  app.controller('ResetPasswordController', ResetPasswordController);
}(angular.module('app')));
