angular.module('front').controller('productListController', function ($scope, $http, $location) {

    let apiURL = contextPath + "api/v2";
    let productsURL = "/products";
    let shoppingCartURL = "/cart";
    let currentPageIndex = 1;

    $scope.totalSum = 0;

    $scope.loadProducts = function (pageIndex = 1) {
        $http.get(apiURL + productsURL + "?page=" + pageIndex).then(function (response) {
            console.log(response.data);
            $scope.productList = response.data;
            $scope.paginationArray = $scope.generatePagesIndexes(1, response.data);
        });
    };

    /*
    * SHOPPING
     */

    $scope.btnMinusClick = function (product) {
        if (product.count > 0) {
            product.count -= 1;
        }
    };

    $scope.btnPlusClick = function (product) {
        product.count += 1;
    };

    $scope.getTotalCostInCart = function () {
        $http.get(apiURL + shoppingCartURL + "/totalCost").then(function (response) {
            console.log(response);
            $scope.totalSum = response.data;
        });
    };

    $scope.btnBuyProduct = function (product) {
        console.log(product);
        $http({
            url: apiURL + shoppingCartURL,
            method: 'POST',
            params: {
                id: product.id,
                title: product.title,
                count: product.count,
                cost: product.cost
            }
        }).then(function () {
            product.count = 0;
            $scope.getTotalCostInCart();
        }, function (error) {
            console.log(error);
        });

    }
    /*
    * PRODUCTS MANAGEMENT
     */

    $scope.btnAddNew = function () {
        $location.path('/new_product');
    };

    $scope.btnEditProduct = function (productId) {
        console.log('edit product ID=' + productId);
    }

    $scope.btnDeleteProduct = function (productId) {
        $http.delete(apiURL + productsURL + "/" + productId)
            .then(function (response) {
                $scope.loadProducts();
            });
    };

    /*
    * INIT METHODS
     */

    $scope.loadProducts();
    $scope.getTotalCostInCart();

});