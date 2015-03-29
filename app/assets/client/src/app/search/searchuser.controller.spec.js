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

  describe('When user is selected', function () {
    var user = {id: 'idaso9324'};
    beforeEach(function () {
      spyOn($location, 'path');
      $scope.model.userSelect(user);
    });

    it('Should navigate to user screen details', function () {
      expect($location.path).toHaveBeenCalledWith('user/' + user.id);
    });
  });

  describe('When user is searched', function () {
    var search = 'test';
    beforeEach(function () {
      spyOn(userService, 'searchUser').and.returnValue($q.when());
      $scope.model.data.search = search;
      $scope.model.search();
    });

    it('Should call user service search', function () {
      expect(userService.searchUser).toHaveBeenCalledWith(search);
    });
  });
});

