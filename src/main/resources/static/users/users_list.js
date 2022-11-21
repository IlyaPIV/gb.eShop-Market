angular.module('front').controller('usersListController', function ($scope, $http, $location) {

    let moduleURL = contextPath + "api/v2/users";
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

    $scope.generatePagesIndexes = function (startPage, pageData) {
        let arr = [];
        endPage = pageData.totalPages;
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }

        return arr;
    }

    $scope.btnDeleteProduct = function (productId) {
        $http.delete(moduleURL + "/" + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    };


    $scope.loadUsers();
});