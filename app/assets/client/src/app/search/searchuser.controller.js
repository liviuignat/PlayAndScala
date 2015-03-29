(function (angular) {
  var $inject = [
  ];

  class SearchUserController {
    constructor () {
      this.data = {
        search: ''
      };
    }

    search() {
    }
  }

  SearchUserController.$inject = $inject;
  angular.module('app')
    .controller('SearchUserController', SearchUserController);
})(angular);
