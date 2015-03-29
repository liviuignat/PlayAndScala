(function (angular) {
  var $inject = [
    '$location',
    'UserService'
  ];

  class SearchUserController {
    constructor ($location, userService) {
      this.$location = $location;
      this.userService = userService;

      this.data = {
        search: ''
      };
    }

    search() {
      this.userService.searchUser(this.data.search).then((users) => {
        this.users = users;
      });
    }

    userSelect(user) {
      this.$location.path('user/' + user.id)
    }
  }

  SearchUserController.$inject = $inject;
  angular.module('app')
    .controller('SearchUserController', SearchUserController);
})(angular);
