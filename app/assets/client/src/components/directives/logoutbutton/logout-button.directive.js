(function (angular) {
  var $inject = [
    '$q',
    'AuthService'
  ];

  class LogoutButtonDirective {
    constructor($q, authService) {
      this.template = '<a ng-click="click($event)" href="#">Logout</a>';
      this.restrict = 'E';
      this.replace = true;

      this.$q = $q;
      this.authService = authService;
    }

    static getInstance($q, authService) {
      return new LogoutButtonDirective($q, authService);
    }

    link(scope) {
      scope.click = this.click.bind(this);
    }

    click(evt) {
      this.authService.logout();
      evt.preventDefault();
    }
  }

  LogoutButtonDirective.$inject = $inject;
  angular.register('app')
    .directive('logoutButton', LogoutButtonDirective);

})(angular);
