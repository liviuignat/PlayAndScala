(function (angular) {
  var $inject = [
    '$q'
  ];

  class AlertService {
    constructor($q) {
      this.$q = $q;
    }

    showAlert(message) {
      alert(message);
      this.$q.when();
    }

    showConfirm(message) {
      var result = confirm(message);
      this.$q.when(result);
    }
  }

  AlertService.$inject = $inject;
  angular.module('app')
    .service('AlertService', AlertService);

})(angular);
