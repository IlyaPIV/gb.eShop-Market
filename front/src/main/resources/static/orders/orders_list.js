myApp.controller('ordersListController', function ($scope, $http) {

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

    $scope.loadOrders();
});