'use strict';

describe('AuthService', function (){
   var $http, $rootScope, service, md5;

  beforeEach(module('app', function () {
    angular.module('app').factory('AuthInterceptor', mocks.AuthInterceptorMock.getInstance);
  }));

  beforeEach(inject(function ($injector) {
    $http = $injector.get('$httpBackend');
    $rootScope = $injector.get('$rootScope');
    service = $injector.get('AuthService');
    md5 = $injector.get('md5');
  }));

  it('Should have the service injected and defined', function () {
    expect(service).toBeDefined();
  });

  describe('When user creates a new account', function () {
    var url = '/api/auth/create';
    var createAccountData = {
      email: 'liviu@ignat.email',
      password: 'test123',
      firstName: 'Liviu',
      lastName: 'Ignat'
    };

    describe('When login is successful with status code 201', function () {
      var response;
      beforeEach(function () {
        $http.expectPOST(url).respond(201);

        service.createAccount(createAccountData).then(function (result) {
          response = result;
        });

        $http.flush();
      });

      it('Should have the response successful', function () {
        expect(response).toBeDefined();
        expect(response.success).toBe(true);
      });
    });

    describe('When login is successful with status code 401', function () {
      var response;
      beforeEach(function () {
        $http.expectPOST(url).respond(401);

        service.createAccount(createAccountData).then(function (result) {
          response = result;
        });

        $http.flush();
      });

      it('Should NOT have a successful response', function () {
        expect(response).toBeDefined();
        expect(response.success).toBe(false);
      });
    });
  });

  describe('When user logs in', function () {
    var url = '/api/auth/login';
    var loginData = {
      email: 'liviu@ignat.email',
      password: 'test123'
    };

    describe('When login is successful with status code 200', function () {
      var loginServiceResponse;
      beforeEach(function () {
        $http.expectPOST(url).respond(200);

        service.login(loginData).then(function (result) {
          loginServiceResponse = result;
        });

        $http.flush();
      });

      it('Should have the response successful', function () {
        expect(loginServiceResponse).toBeDefined();
        expect(loginServiceResponse.success).toBe(true);
      });
    });

    describe('When login is successful with status code 401', function () {
      var loginServiceResponse;
      beforeEach(function () {
        $http.expectPOST(url).respond(401);

        service.login(loginData).then(function (result) {
          loginServiceResponse = result;
        });

        $http.flush();
      });

      it('Should NOT have a successful response', function () {
        expect(loginServiceResponse).toBeDefined();
        expect(loginServiceResponse.success).toBe(false);
      });
    });
  });

  describe('When user resets password', function () {
      var url = '/api/auth/resetpassword';
      var postData = {
        email: 'liviu@ignat.email'
      };

      describe('When reset password is successful with status code 200', function () {
        var resetPasswordResponse;
        beforeEach(function () {
          $http.expectPOST(url).respond(200);

          service.resetPassword(postData).then(function (result) {
            resetPasswordResponse = result;
          });

          $http.flush();
        });

        it('Should have the response successful', function () {
          expect(resetPasswordResponse).toBeDefined();
          expect(resetPasswordResponse.success).toBe(true);
        });
      });

      describe('When reset password is successful with status code 401', function () {
        var resetPasswordResponse;
        beforeEach(function () {
          $http.expectPOST(url).respond(401);

          service.resetPassword(postData).then(function (result) {
            resetPasswordResponse = result;
          });

          $http.flush();
        });

        it('Should NOT have a successful response', function () {
          expect(resetPasswordResponse).toBeDefined();
          expect(resetPasswordResponse.success).toBe(false);
        });
      });
    });
});
