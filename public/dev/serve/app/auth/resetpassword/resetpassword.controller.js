"use strict";
(function(app) {
  'use strict';
  var ResetPasswordController = function ResetPasswordController($scope) {
    $scope.email = 'liviu@ignat.email';
  };
  ($traceurRuntime.createClass)(ResetPasswordController, {}, {});
  app.controller('ResetPasswordController', ResetPasswordController);
  ResetPasswordController.$inject = ['$scope'];
}(angular.module('app')));
