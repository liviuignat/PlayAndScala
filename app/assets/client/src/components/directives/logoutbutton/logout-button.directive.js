(function (angular) {
  var $inject = [
    '$q',
    '$window',
    'AuthService'
  ];

  class LogoutButtonDirective {
    constructor($q, $window, authService) {
      this.template = '<a ng-click="click($event)" href="#">Logout</a>';
      this.restrict = 'E';
      this.replace = true;

      this.$q = $q;
      this.$window = $window;
      this.authService = authService;
    }

    static getInstance($q, authService) {
      return new LogoutButtonDirective($q, authService);
    }

    link(scope) {
      scope.click = this.click.bind(this);
    }

    click(evt) {
      evt.preventDefault();

      return this.authService.logout().then(() => {
        this.$window.location.href = '/';
      });
    }
  }

  LogoutButtonDirective.$inject = $inject;
  angular.register('app')
    .directive('logoutButton', LogoutButtonDirective);

})(angular);
