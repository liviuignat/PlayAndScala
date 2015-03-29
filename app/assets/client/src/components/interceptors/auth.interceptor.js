(function (angular) {
  var $inject = [
    '$injector',
    '$log',
    '$timeout',
    '$q'
  ];

  class AuthInterceptor {
    constructor($injector, $log, $timeout, $q) {
      this.$q = $q;
      this.$log = $log;
      this.$timeout = $timeout;
      this.$injector = $injector;

      $timeout(() => {
        this.authService = this.$injector.get('AuthService');
        this.init();
      });
    }

    static getInstance($q, $log, authService) {
      return new AuthInterceptor($q, $log, authService);
    }

    init() {
    }

    request(config) {
      debugger;
      var deferred = $q.defer();
      var isLoggedIn = this.authService.isLoggedIn();
      this.$log = 'is logged in: ' + isLoggedIn;
      return deferred.promise;
    }
  }

  AuthInterceptor.getInstance.$inject = $inject;
  angular.module('app')
    .factory('AuthInterceptor', AuthInterceptor.getInstance);

})(angular);
