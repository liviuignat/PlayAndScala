(function (angular) {
  var $inject = [
    '$injector',
    '$rootScope',
    '$timeout',
    '$q',
    '$log',
    '$window'
  ];

  let self;

  class AuthInterceptor {
    constructor($injector, $rootScope, $timeout, $q, $log, $window) {
      self = this;
      this.$injector = $injector;
      this.$rootScope = $rootScope;
      this.$timeout = $timeout;
      this.$q = $q;
      this.$log = $log;
      this.$window = $window;
    }

    static getInstance($injector, $rootScope, $timeout, $q, $log, $window) {
      return new AuthInterceptor($injector, $rootScope, $timeout, $q, $log, $window);
    }

    request(config) {
      return self.onRequest(config);
    }

    requestError(rejection) {
      return self.onRequestError(rejection);
    }

    init() {
      return this.$timeout(() => {
        this.$location =  this.$injector.get('$location');
        this.authService = this.$injector.get('AuthService');
      });
    }

    tryRefreshToken() {
      return this.authService.refreshAuthTokenIfNotExpired();
    }

    onLoggedOut() {
      this.$location.path('login');
      return this.authService.logout();
    }

    onRequest(config) {
      var deferred = this.$q.defer();

      this.init().then(() => {
        if(config.isSecure) {
          var isLoggedIn = this.authService.isLoggedIn();
          this.$log.info('Is logged in: ' + isLoggedIn);

          if(isLoggedIn) {
            return this.tryRefreshToken().then(() => {
              return this.authService.getAuthToken();
            });
          } else {
            return this.onLoggedOut();
          }
        }
      }).then((autToken) => {
        if(autToken && autToken.token) {
          config.headers.Authorization = 'Bearer ' + autToken.token;
        }
        deferred.resolve(config);
      }).catch(() => {
        return this.onLoggedOut().then(() => {
          deferred.resolve(config);
        });
      });

      return deferred.promise;
    }

    onRequestError(rejection) {
      return this.$q.reject(rejection);
    }
  }

  AuthInterceptor.getInstance.$inject = $inject;
  angular.module('app')
    .factory('AuthInterceptor', AuthInterceptor.getInstance);

})(angular);
