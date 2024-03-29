myApp.controller('checkoutController', function ($scope, $http, $location) {

    let cartControllerURI = "carts";
    let orderControllerURI = "orders";

    $scope.checkout = {
        address: 'Here will be shipping address',
        paymentMethod: 'NOT_CHOISED',
        deliveryDays: 3
    };

    $scope.changePaymentMethod = function (method) {
        $scope.checkout.paymentMethod = method;
        //console.log(method);
    };

    $scope.getShoppingCart = function () {
        $http.get(cartsApiURI + cartControllerURI)
            .then(function (response) {
                // console.log(response.data);
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
        $http.post(ordersApiURI + orderControllerURI, $scope.checkout)
            .then(function (response) {
                // console.log(response);
                $location.path('/products');
            }, function (error) {
                console.log(error);
            })
    }


    $scope.getShoppingCart();

});