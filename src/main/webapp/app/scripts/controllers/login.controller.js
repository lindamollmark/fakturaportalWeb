'use strict';

/**
 * @ngdoc function
 * @name springBootClientApp.controller:LoginCtrl
 * @description
 * # loginCtrl helps with the functions login in and out
 * Controller of the springBootClientApp
 */
angular.module('springBootClientApp')

  .controller('LoginCtrl', function ($scope, $rootScope, authService, $location) {
    $scope.credentials = {
      username: '',
      password: ''
    };
    $scope.error = false;

    $scope.login = function (credentials) {
      authService.login(credentials).then(function (response) {
        // hides and shows a div depending if you are logged in or not
        $rootScope.existingUser = response.data;
        if(response.data == true){
          $rootScope.currentUser = credentials;
          var path = "/";
          $location.path(path);
        }
        else{
          // if you don't have the right name or password this gives you a error message.
          $scope.error = true;
        }
      });
    };

    $scope.logout = function(){
      $rootScope.existingUser = false;
      $rootScope.currentUser = {};
      var path = "/";
      $location.path(path);
    }
  });
