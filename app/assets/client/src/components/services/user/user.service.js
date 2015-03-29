(function (angular) {
  var $inject = [
    '$http',
    '$q'
  ];

  class UserService {
    constructor($http, $q) {
      this.$http = $http;
      this.$q = $q;

      this.cancelationOfSearchRequest = this.$q.defer();
    }

    searchUser(query) {
      this.cancelSearchRequest();

      var deferred = this.$q.defer();
      var url = '/api/user?q=' + query;

      this.currentRequest = this.$http({
        method: 'GET',
        url: url,
        timeout: this.cancelationOfSearchRequest.promise,
      }).success((data, status) => {
        if (status === 200) {
          deferred.resolve(data);
        }
        deferred.resolve([]);
      }).error(() => {
        deferred.resolve([]);
      }).catch(() => {
        deferred.resolve([]);
      });

      return deferred.promise;
    }

    cancelSearchRequest() {
      if(this.$http.pendingRequests.length) {
        this.cancelationOfSearchRequest.resolve();
        this.cancelationOfSearchRequest = this.$q.defer();
      }
    }
  }

  UserService.$inject = $inject;
    angular.module('app')
      .service('UserService', UserService);

})(angular);
