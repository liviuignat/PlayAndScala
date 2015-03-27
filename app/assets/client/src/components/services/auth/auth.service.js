/*jshint esnext: true */
(function (angular) {
  var $inject = [
    '$http',
    '$q'
  ];

  class AuthService {
    constructor($http, $q) {
      this.$http = $http;
      this.$q = $q;
    }

    login(model) {
      var deferred = this.$q.defer();

      this.$http.post('/api/auth/login', model).success((data, status) => {
        if (status === 200) {
          deferred.resolve(new AuthResponse(true));
        }
        deferred.resolve(new AuthResponse(false, 'User or password does not exist'));
      }).error(() => {
        deferred.resolve(new AuthResponse(false, 'Unexpected error from the server'));
      }).catch(() => {
        deferred.resolve(new AuthResponse(false, 'Unexpected error from the server'));
      });

      return deferred.promise;
    }

    createAccount(model) {
      return this.$http.post('/api/auth/create', model);
    }

    resetPassword(model) {
      return  this.$http.post('/api/auth/resetpassword', model);
    }
  }

  class AuthResponse {
    constructor(success, errorMessage) {
      this.success = success;
      this.message = errorMessage;
    }
  }

  AuthService.$inject = $inject;
  angular.module('app')
    .service('AuthService', AuthService);

})(angular);
