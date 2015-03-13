(function () {
  angular.module('app')
    .config(['$httpProvider', '$routeProvider', '$locationProvider',
      function ($httpProvider, $routeProvider, $locationProvider) {
        $routeProvider
          .when('/search', {
            title: 'Search users page',
            templateUrl: '/components/search-user/search-user.tpl.html',
            controller: 'SearchUserController',
            reloadOnSearch: false
          })

        $locationProvider.html5Mode({
          enabled: true,
          requireBase: false
        });
      }
    ]);
})();
