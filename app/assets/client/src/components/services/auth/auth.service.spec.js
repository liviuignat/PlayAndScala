'use strict';

describe('AuthService', function (){
   var $http, $rootScope, service;

  beforeEach(function () {
    module('app');
  });

  beforeEach(inject(function ($injector) {
    $http = $injector.get('$httpBackend');
    $rootScope = $injector.get('$rootScope');
    service = $injector.get('AuthService');
  }));

  it('Should have the service injected and defined', function () {
    expect(service).toBeDefined();
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
        $http.expectPOST(url, loginData).respond(200);

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
        $http.expectPOST(url, loginData).respond(401);

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
});
