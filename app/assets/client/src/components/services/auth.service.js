class AuthService {
  constructor($http, $q) {
    this.$http = $http;
    this.$q = $q;
  }

  static getInstance($http, $q) {
    return new AuthService($http);
  }
}

AuthService.getInstance.$inject = ['$http', '$q'];
angular.module('app')
  .factory('AuthService', AuthService.getInstance);
