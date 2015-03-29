'use strict';

describe('SearchUserController', function () {
  var $scope, $q, $location, userService;

  beforeEach(function () {
    module('app');
  });

  beforeEach(inject(function ($injector, $controller, $rootScope, _$location_, _$q_) {
    userService = $injector.get('UserService');
    $scope = $rootScope.$new();
    $q = _$q_;
    $location = _$location_;

    $controller('SearchUserController as model', {
      $scope: $scope,
      $location: $location,
      userService: userService
    });
  }));

  it('Scope model should be defined', function () {
    expect($scope.model).toBeDefined();
  });
});

