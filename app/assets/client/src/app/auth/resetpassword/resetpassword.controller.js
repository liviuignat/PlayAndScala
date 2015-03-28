 (function (angular) {
  var $inject = [
    'AuthService',
    'AlertService'
  ];

  class ResetPasswordController {
    constructor (authService, alertService) {
      this.authService = authService;
      this.alertService = alertService;

      this.data = {
        email: ''
      };

      this.notification = {
        success: false
      };
    }

    resetPassword() {
      this.notification.success = false;

      return this.authService.resetPassword(this.data).then(result => {
        if(!result.success) {
          this.alertService.showAlert(result.message);
          return;
        }

        this.notification.success = true;
      }).catch(() => {
        this.alertService.showAlert('Unknown error occurred');
      });
    }
  }

  ResetPasswordController.$inject = $inject;
  angular.module('app')
    .controller('ResetPasswordController', ResetPasswordController);

})(angular);
