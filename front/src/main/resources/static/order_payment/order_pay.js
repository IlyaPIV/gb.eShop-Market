myApp.controller('orderPaymentController', function ($scope, $http, $location, $routeParams) {

    let orderControllerURI = "orders/";

    $scope.loadOrder = function () {
        $http({
            url: ordersApiURI + orderControllerURI + $routeParams.orderId,
            method: 'GET'
        }).then(function (response) {
            $scope.order = response.data;
            $scope.renderPaymentButtons();
        });
    };

    $scope.renderPaymentButtons = function () {
        paypal.Buttons({
            enableStandardCardFields: true,
            createOrder: function (data, actions) {
                return fetch(ordersApiURI + 'paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function (response) {
                    return response.text();
                });
            },

            onApprove: function (data, actions) {
                return fetch(ordersApiURI + 'paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function (response) {
                    response.text().then(msg => alert(msg));
                });
            },

            onCancel: function (data) {
                console.log("Order canceled: " + data);
            },

            onError: function (err) {
                console.log(err);
            }
        }).render('#paypal-buttons');
    }

    $scope.loadOrder();
});