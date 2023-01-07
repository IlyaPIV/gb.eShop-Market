myApp.controller('ordersListController', function ($scope, $http, $location) {

    let moduleURL = ordersApiURI + "/orders";

    $scope.loadOrders = function () {
        $http.get(moduleURL)
            .then(function (response) {
                    console.log(response.data);
                    $scope.ordersList = response.data.ordersList;
                }, function errorGetData(reason) {
                    console.log(reason);
                }
            );
    };

    $scope.goToPay = function (orderId) {
        $location.path('/order_pay/' + orderId);
    };

    $scope.isOrderNotPayed = function (order) {
        return order.status === "NEW" && order.paymentMethod === "NOT_CHOISED";
    };

    $scope.loadOrders();
});