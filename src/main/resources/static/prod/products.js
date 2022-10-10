angular.module('prod-list-app', []).controller('listController', function ($scope, $http){
   const modulePath = "http://localhost:9090/eshop/products/rest";

   $scope.loadProducts = function (){
        $http.get(modulePath + "/all")
            .then(function (response){
                $scope.productList = response.data;
            });
   };

   $scope.loadProducts();

    $scope.btnDeleteProduct = function (productId) {
        $http.delete(modulePath + "/delete/" + productId)
            .then(function (response){
                $scope.loadProducts();
            });
    };

    $scope.counter = 0;

    $scope.btnMinusClick = function () {
        if ($scope.counter > 0) {
            $scope.counter -= 1;
        }
    };

    $scope.btnPlusClick = function () {
        $scope.counter += 1;
    };



    $scope.btnRefreshList = function (){
        $http.get(modulePath + "/refresh")
            .then(function (response){
                $scope.loadProducts();
                $scope.counter = 0;
            });
    };

    $scope.btnAddNew = function (){
        // var data = {
        //     name: 'input test name',
        //     cost: 666
        // };

        const formData = new FormData();
        formData.append('name', 'input test name');
        formData.append('cost', 666);

        // $http.post(modulePath + "/", JSON.stringify(formData))
        $http({url: modulePath + "/",
                method: 'POST',
                params: {
                    name: 'input test name',
                    cost: 666
                }
            }).then(function (response){
                $scope.loadProducts();
        });
    };

    $scope.btnOpenProduct = function (productId){
        $http.get(modulePath + "/tl/edit/" + productId);
    };
});