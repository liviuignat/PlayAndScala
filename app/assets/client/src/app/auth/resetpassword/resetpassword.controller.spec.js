'use strict';

describe('ResetPasswordController', function () {
  var $scope, $q, authService, alertService;

  beforeEach(function () {
    module('app');
  });

  beforeEach(inject(function ($injector, $controller, $rootScope, _$q_) {
    authService = $injector.get('AuthService');
    alertService = $injector.get('AlertService');
    $scope = $rootScope.$new();
    $q = _$q_;

    $controller('ResetPasswordController as model', {
      $scope: $scope,
      authService: authService,
      alertService: alertService
    });
  }));

  it('Scope model should be defined', function () {
    expect($scope.model).toBeDefined();
  });

  describe('When resetPassword is pressed', function () {
    var resetPasswordSpy;
    beforeEach(function () {
      resetPasswordSpy = spyOn(authService, 'resetPassword').and.returnValue($q.when({success: true}));
      $scope.model.data = {
        email: 'liviu@ignat.email'
      };
      $scope.model.resetPassword();
    });

    afterEach(function () {
       $scope.model.data = null;
    });

    it('Should call authService resetPassword', function () {
      expect(authService.resetPassword).toHaveBeenCalled();
    });

    describe('When resetPassword is successful', function () {
      beforeEach(function () {
        resetPasswordSpy.and.returnValue($q.when({success: true}));
        $scope.model.resetPassword();
        $scope.$apply();
      });

      it('Should navigate to search route', function () {
        expect($scope.model.notification.success).toBe(true);
      });
    });

    describe('When resetPassword is not successful', function () {
      var expectedErrorMessage = 'Error resetPassword';
      beforeEach(function () {
        spyOn(alertService, 'showAlert');
        resetPasswordSpy.and.returnValue($q.when({success: false, message: expectedErrorMessage}));
        $scope.model.resetPassword();
        $scope.$apply();
      });

      it('Should call alertService alert with the erorr message', function () {
        expect(alertService.showAlert).toHaveBeenCalledWith(expectedErrorMessage);
      });
    });
  });
});

