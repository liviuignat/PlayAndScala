 /*jshint esnext: true */
class ResetPasswordController {
  constructor ($scope) {
    $scope.email = 'liviu@ignat.email';
  }
}

ResetPasswordController.$inject = ['$scope'];
angular.module('app')
  .controller('ResetPasswordController', ResetPasswordController);
