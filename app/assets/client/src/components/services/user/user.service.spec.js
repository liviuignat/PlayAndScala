describe('UserService', function (){
   var $http, $rootScope, service, md5;

  beforeEach(module('app', function ($httpProvider) {
    $httpProvider.interceptors = [];
  }));

  beforeEach(inject(function ($injector) {
    $http = $injector.get('$httpBackend');
    $rootScope = $injector.get('$rootScope');
    service = $injector.get('UserService');
    md5 = $injector.get('md5');
  }));

  it('Should have the service injected and defined', function () {
    expect(service).toBeDefined();
  });

  describe('When getting user by search query', function () {
    var url = '/api/user?q=any_query';
    describe('When response is not successful', function () {
      var response;
      beforeEach(function () {
        $http.expectGET(url).respond(400);

       service.searchUser('any_query').then(function (result) {
         response = result;
       });

        $http.flush();
      });

      it('Should have the response []', function () {
        expect(response).toBeDefined();
        expect(response).toEqual([]);
      });
    });
  });

  describe('When getting user details by id', function () {
    var user = { id: 'dsadas232' };
    var url = '/api/user/' + user.id;

    describe('When response is successful', function () {
      var response;
      beforeEach(function () {
        $http.expectGET(url).respond(200, user);
        service.getUserDetail(user.id).then(function (result) {
          response = result;
        });

        $http.flush();
      });

      it('Should have the response equal to the user', function () {
        expect(response).toBeDefined();
        expect(response).toEqual(user);
      });
    });
  });
});
