(function() {
  'use strict';

  angular
    .module('app')
    .controller('HomeCtrl', HomeCtrl);

  HomeCtrl.$inject = ['$scope', 'Auth', 'Results'];

  function HomeCtrl($scope, Auth, Results) {
    $scope.site = [];

    $scope.getResults = function() {

    }

  }
})();