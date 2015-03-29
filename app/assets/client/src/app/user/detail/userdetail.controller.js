(function (angular) {
  var $inject = [
    '$routeParams',
    'UserService',
    'AlertService'
  ];

  class UserDetailController {
    constructor ($routeParams, userService, alertService) {
      this.userService = userService;
      this.alertService = alertService;
      this.userId = $routeParams.id;

      this.userService.getUserDetail(this.userId).then((user) => {
        this.user = user;
      }).catch(() => {
        this.alertService.showAlert('Failed to load user from the server');
      });
    }
  }

  UserDetailController.$inject = $inject;
  angular.module('app')
    .controller('UserDetailController', UserDetailController);
})(angular);
