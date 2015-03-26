/*jshint esnext: true */
class LoginController {
  constructor ($scope, authService) {
    this.authService = authService;
    $scope.user = {
      email: 'liviu@ignat.email',
      password: 'test123'
    };
  }
}

LoginController.$inject = ['$scope', 'AuthService'];
angular.module('app')
  .controller('LoginController', LoginController);

