angular.module('front').controller('usersListController', function ($scope, $http) {

    let moduleURL = coreApiURI + "/users";
    let currentPageIndex = 1;

    $scope.loadUsers = function (pageIndex = 1) {
        $http.get(moduleURL + "?page=" + pageIndex).then(function (response) {
            console.log(response.data);
            $scope.usersList = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, response.data);
        }, function errorGetData(response) {
            console.log(response);
        });
    };


    $scope.loadUsers();
});