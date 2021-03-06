  'use strict';

/**
 * @ngdoc function
 * @name webappApp.controller:ClientCtrl
 * @description
 * # ClientCtrl helps with the functions regarding an existing client.
 * Controller of the webappApp
 */

angular.module('springBootClientApp')
  .controller('clientCtrl', function($scope, $routeParams, $http, clientService, $location) {
    var self = this;
    var id = $routeParams.id;
    var clientID = {id: id};
    this.tab = 1;

    this.selectTab = function (setTab) {
      this.tab = setTab;
    };
    this.isSelected = function (checkTab) {
      return this.tab === checkTab;
    };

    clientService.fetchClient(clientID).then(function (response) {
      $scope.client = angular.fromJson(response.data);
    });

    $scope.delete = function () {
      clientService.remove(clientID).then(function (response) {
        var message = (response.data);
        if (message) {
          var path = "/clientList";
          $location.path(path);
        }
        else {
          alert('Kund har fakturor, och går därmed inte att radera!');
        }
      });
    };

    $scope.update = function () {
      var client = {};
      client = $scope.client;
      clientService.updateClient(client).then(function (response) {
        self.client = angular.fromJson(response.data);
        $scope.updateMessage = 'Kund uppdaterad';
      });
    };
  });



