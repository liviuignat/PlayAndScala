'use strict';

describe('LoginController', function () {
  var $scope, $q, $location, authService, alertService;

  beforeEach(function () {
    module('app');
  });

  beforeEach(inject(function ($injector, $controller, $rootScope, _$location_, _$q_) {
    authService = $injector.get('AuthService');
    alertService = $injector.get('AlertService');
    $scope = $rootScope.$new();
    $q = _$q_;
    $location = _$location_;

    $controller('LoginController as model', {
      $scope: $scope,
      $location: $location,
      authService: authService,
      alertService: alertService
    });
  }));

  it('Scope model should be defined', function () {
    expect($scope.model).toBeDefined();
  });

  describe('When login is pressed', function () {
    var loginSpy;
    beforeEach(function () {
      loginSpy = spyOn(authService, 'login').and.returnValue($q.when({success: true}));
      $scope.model.user = {
        email: 'liviu@ignat.email',
        password: 'test123'
      };
      $scope.model.login();
    });

    afterEach(function () {
       $scope.model.user = null;
    });

    it('Should call authService login', function () {
      expect(authService.login).toHaveBeenCalled();
    });

    describe('When login is successful', function () {
      beforeEach(function () {
        spyOn($location, 'path');
        loginSpy.and.returnValue($q.when({success: true}));
        $scope.model.login();
        $scope.$apply();
      });

      it('Should navigate to search route', function () {
        expect($location.path).toHaveBeenCalledWith('search');
      });
    });

    describe('When login is not successful', function () {
      var expectedErrorMessage = 'Error login';
      beforeEach(function () {
        spyOn(alertService, 'showAlert');
        loginSpy.and.returnValue($q.when({success: false, message: expectedErrorMessage}));
        $scope.model.login();
        $scope.$apply();
      });

      it('Should call alertService alert with the erorr message', function () {
        expect(alertService.showAlert).toHaveBeenCalledWith(expectedErrorMessage);
      });
    });
  });
});
