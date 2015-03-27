'use strict';

describe('CreateAccountController', function () {
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

    $controller('CreateAccountController as model', {
      $scope: $scope,
      $location: $location,
      authService: authService,
      alertService: alertService
    });
  }));

  it('Scope model should be defined', function () {
    expect($scope.model).toBeDefined();
  });

  describe('When createAccount is pressed', function () {
    var createAccountSpy;
    beforeEach(function () {
      createAccountSpy = spyOn(authService, 'createAccount').and.returnValue($q.when({success: true}));
      $scope.model.user = {
        email: 'liviu@ignat.email',
        password: 'test123'
      };
      $scope.model.createAccount();
    });

    afterEach(function () {
       $scope.model.user = null;
    });

    it('Should call authService createAccount', function () {
      expect(authService.createAccount).toHaveBeenCalled();
    });

    describe('When createAccount is successful', function () {
      beforeEach(function () {
        spyOn($location, 'path');
        createAccountSpy.and.returnValue($q.when({success: true}));
        $scope.model.createAccount();
        $scope.$apply();
      });

      it('Should navigate to search route', function () {
        expect($location.path).toHaveBeenCalledWith('search');
      });
    });

    describe('When createAccount is not successful', function () {
      var expectedErrorMessage = 'Error createAccount';
      beforeEach(function () {
        spyOn(alertService, 'showAlert');
        createAccountSpy.and.returnValue($q.when({success: false, message: expectedErrorMessage}));
        $scope.model.createAccount();
        $scope.$apply();
      });

      it('Should call alertService alert with the erorr message', function () {
        expect(alertService.showAlert).toHaveBeenCalledWith(expectedErrorMessage);
      });
    });
  });
});
