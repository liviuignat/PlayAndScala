 (function (angular) {
  var $inject = [];

  class ResetPasswordController {
    constructor () {
    }
  }

  ResetPasswordController.$inject = $inject;
  angular.module('app')
    .controller('ResetPasswordController', ResetPasswordController);

})(angular);
