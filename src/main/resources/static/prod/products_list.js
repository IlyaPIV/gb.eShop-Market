angular.module('front').controller('productListController', function ($scope, $http, $location) {

    const moduleURL = "http://localhost:9090/eshop/api/v2/products";
    let currentPageIndex = 1;

    $scope.loadProducts = function (pageIndex = 1) {
        $http.get(moduleURL + "?page=" + pageIndex).then(function (response) {
            console.log(response.data);
            $scope.productList = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, response.data);
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

    $scope.btnMinusClick = function (product) {
        if (product.count > 0) {
            product.count -= 1;
        }
    };

    $scope.btnPlusClick = function (product) {
        product.count += 1;
    };


    $scope.btnRefreshList = function () {
        $scope.loadProducts();
    };

    $scope.btnAddNew = function () {

        const formData = new FormData();
        formData.append('name', 'input test name');
        formData.append('cost', 666);

        // $http.post(modulePath + "/", JSON.stringify(formData))
        $http({
            url: moduleURL,
            method: 'POST',
            params: {
                name: 'input test name',
                cost: 666
            }
        }).then(function (response) {
            $scope.loadProducts();
        });
    };

    $scope.loadProducts();
});