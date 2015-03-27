/*jshint esnext: true */
(function (angular) {
  var $inject = [
    '$http',
    '$q',
    'md5'
  ];

  class AuthService {
    constructor($http, $q, md5) {
      this.$http = $http;
      this.$q = $q;
      this.md5 = md5;
    }

    login(model) {
      var deferred = this.$q.defer();

      var payload = angular.copy(model);
      payload.password = this.md5.createHash(model.password || '');

      this.$http.post('/api/auth/login', payload).success((data, status) => {
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
      var deferred = this.$q.defer();

      this.$http.post('/api/auth/create', model).success((data, status) => {
        if (status === 201) {
          deferred.resolve(new AuthResponse(true));
        }
        deferred.resolve(new AuthResponse(false, 'User already exists'));
      }).error(() => {
        deferred.resolve(new AuthResponse(false, 'Unexpected error from the server'));
      }).catch(() => {
        deferred.resolve(new AuthResponse(false, 'Unexpected error from the server'));
      });

      return deferred.promise;
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
