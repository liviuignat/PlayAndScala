'use strict';
/*jshint esnext: true */

class ResetPasswordController {
  constructor ($scope) {
    $scope.email = 'liviu@ignat.email';
  }
}

ResetPasswordController.$inject = ['$scope'];

export default ResetPasswordController;
