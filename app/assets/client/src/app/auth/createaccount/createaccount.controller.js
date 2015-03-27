(function (angular) {
  var $inject = [];

  class CreateAccountController {
    constructor () {
    }
  }

  CreateAccountController.$inject = $inject;
  angular.module('app')
    .controller('CreateAccountController', CreateAccountController);
})(angular);
