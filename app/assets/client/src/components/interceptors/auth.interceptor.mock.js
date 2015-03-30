(function (mocks) {
  class AuthInterceptorMock {
    constructor() {
    }

    static getInstance() {
      return new AuthInterceptorMock();
    }

    request(config) {
      return config;
    }

    requestError(rejection) {
      return rejection;
    }
  }

  AuthInterceptorMock.getInstance.$inject = [];
  mocks.AuthInterceptorMock = AuthInterceptorMock;

})(window.mocks = window.mocks || {});

