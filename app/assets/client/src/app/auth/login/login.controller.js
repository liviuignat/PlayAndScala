'use strict';
/*jshint esnext: true */

class LoginController {
  constructor ($scope) {
    $scope.user = {
      email: 'liviu@ignat.email',
      password: 'test123'
    };
  }
}

LoginController.$inject = ['$scope'];

export default LoginController;
