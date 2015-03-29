/*jshint esnext: true */
(function (angular) {
  var $inject = [
    '$http',
    '$q',
    '$window',
    'cookieStore',
    'md5'
  ];

  var REFRESH_TOKEN_COOKIE_KEY = 'session_id';
  var AUTH_TOKEN_CACHE_KEY = 'session_auth_token';

  class AuthService {
    constructor($http, $q, $window, $cookies, md5) {
      this.$http = $http;
      this.$q = $q;
      this.$window = $window;
      this.$cookies = $cookies;
      this.md5 = md5;
    }

    isLoggedIn() {
      var refreshToken = this.$cookies.get(REFRESH_TOKEN_COOKIE_KEY);
      return (refreshToken || '').length > 0;
    }

    getServerToken() {
      return this.$q.when({
        token: 'harcoded_token',
        autoRereshToken: 'auto_refresh'
      });
    }

    getAuthToken() {
      var value = this.$window.sessionStorage.getItem(AUTH_TOKEN_CACHE_KEY);
      var json = JSON.parse(value);
      var isExpired = json.expires < Date.now();
      this.$q.when(isExpired ? null : json.token);
    }

    setAuthToken(token) {
      var json = {
        token: token,
        expires: Date.now() + (3600 * 1000)
      };
      this.$window.sessionStorage.setItem(AUTH_TOKEN_CACHE_KEY, JSON.stringify(json));
      this.$q.when();
    }

    refreshAuthToken() {
      var newToken = 'refreshed_auth_token';
      this.setAuthToken(newToken);
    }

    login(model) {
      var deferred = this.$q.defer();
      var failResponse = new AuthResponse(false, 'User or password does not exist');
      var payload = angular.copy(model);

      payload.password = this.md5.createHash(model.password || '');

      this.$http.post('/api/auth/login', payload).success((data, status) => {
        if (status === 200) {
          this.getServerToken().then((tokenData) => {
            this.$cookies.put(REFRESH_TOKEN_COOKIE_KEY, tokenData.autoRereshToken, {
              path: '/',
              secure: false,
              expires: Date.now() + (30 * 24 * 3600 * 1000)
            });
            this.setAuthToken(tokenData.token);
          }).then(() => {
            deferred.resolve(new AuthResponse(true));
          }).catch(function () {
            deferred.resolve(failResponse);
          });
        }
      }).error(() => {
        deferred.resolve(failResponse);
      }).catch(() => {
        deferred.resolve(failResponse);
      });

      return deferred.promise;
    }

    logout() {
      this.$window.sessionStorage.removeItem(AUTH_TOKEN_CACHE_KEY);
      this.$cookies.remove(REFRESH_TOKEN_COOKIE_KEY);
      this.$q.when();
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
      var deferred = this.$q.defer();

      this.$http.post('/api/auth/resetpassword', model).success((data, status) => {
        if (status === 200) {
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
