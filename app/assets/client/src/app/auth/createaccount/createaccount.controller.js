/*jshint esnext: true */
class CreateAccountController {
  constructor ($scope) {
    $scope.user = {
      email: 'liviu@ignat.email',
      password: 'test123',
      repeatPassword: 'dsadas'
    };
  }
}

CreateAccountController.$inject = ['$scope'];
angular.module('app')
  .controller('CreateAccountController', CreateAccountController);
