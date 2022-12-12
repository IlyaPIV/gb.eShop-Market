angular.module('front').controller('checkoutController', function ($scope, $http, $location) {

    let apiURL = contextPath + "api/v2";
    let shoppingCartURL = "/cart";
    let orderURL = "/order";
    let addressesURL = "/address";

    $scope.getShoppingCart = function () {
        $http.get(apiURL + shoppingCartURL)
            .then(function (response) {
                console.log(response.data);
                $scope.shoppingCart = response.data.products;
                $scope.cartProductTotal = response.data.totalProductsCost;
                $scope.cartShippingTotal = response.data.totalItems * response.data.shippingCostPerItem;
                $scope.cartPaymentTotal = $scope.cartProductTotal + $scope.cartShippingTotal;
            }, function (error) {
                console.log(error);
            });
    };

    $scope.btnPlaceOrder = function () {
        //создание и сохранение заказа

        //очистка корзины
        $http.delete(apiURL + shoppingCartURL + "/all").then(function () {
            $location.path('/products');
        });
    }

    $scope.getShoppingCart();

});