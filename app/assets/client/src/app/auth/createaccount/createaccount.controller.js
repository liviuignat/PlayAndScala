(function (angular) {
  var $inject = [
    '$location',
    'AuthService',
    'AlertService'
  ];

  class CreateAccountController {
    constructor ($location, authService, alertService) {
      this.$location = $location;
      this.authService = authService;
      this.alertService = alertService;

      this.user = {
        email: '',
        password: '',
        repeatPassword: '',
        firstName: '',
        lastName: ''
      };
    }

    createAccount() {
      return this.authService.createAccount(this.user).then(result => {
        if(!result.success) {
          this.alertService.showAlert(result.message);
          return;
        }

        this.$location.path('search');
      }).catch(() => {
        this.alertService.showAlert('Unknown error occurred');
      });
    }
  }

  CreateAccountController.$inject = $inject;
  angular.module('app')
    .controller('CreateAccountController', CreateAccountController);
})(angular);
