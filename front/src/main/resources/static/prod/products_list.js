angular.module('front').controller('productListController', function ($scope, $http, $location) {

    let shoppingCartURL = cartsApiURI + "carts";
    let currentPageIndex = 1;

    $scope.totalSum = 0;

    $scope.loadProducts = function (pageIndex = 1) {
        $http.get(coreApiURI + "products?page=" + pageIndex).then(function (response) {
            //console.log(response.data);
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
        $http.get(shoppingCartURL + "/totalCost").then(function (response) {
            //console.log(response);
            $scope.totalSum = Math.round(response.data * 100) / 100;
        });
    };

    $scope.btnBuyProduct = function (product) {
        //console.log(product);
        $http({
            url: shoppingCartURL,
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
    };

    $scope.btnCheckout = function () {
        $location.path('/checkout');
    };


    /*
    * PRODUCTS MANAGEMENT
     */

    $scope.btnAddNew = function () {
        $location.path('/new_product');
    };

    $scope.btnEditProduct = function (productId) {
        console.log('edit product ID=' + productId);
    };

    $scope.btnDeleteProduct = function (productId) {
        $http.delete(coreApiURI + "products/" + productId)
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