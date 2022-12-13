angular.module('front').controller('checkoutController', function ($scope, $http, $location) {

    let cartControllerURI = "carts";
    let orderControllerURI = "orders";

    $scope.checkout = {
        address: 'Here will be shipping address',
        paymentMethod: 'CASH',
        deliveryDays: 5
    };

    $scope.getShoppingCart = function () {
        $http.get(cartsApiURI + cartControllerURI)
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
        $http.post(coreApiURI + orderControllerURI, $scope.checkout)
            .then(function (response) {
                console.log(response);
                $location.path('/products');
            }, function (error) {
                console.log(error);
            })

    }

    $scope.getShoppingCart();

});