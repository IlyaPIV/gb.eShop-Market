angular.module('front').controller('editProductForm', function ($scope, $http, $location) {

    $scope.btnCancel = function () {
        $location.path('/manage_products');
    };

    $scope.btnSubmitProduct = function () {
        if ($scope.thisProduct == null) {
            alert('Форма не заполнена');
            return;
        }
        $http.post(contextPath + 'api/v2/products', $scope.thisProduct)
            .then(function successCallback(response) {
                $scope.thisProduct = null;
                alert('Продукт успешно создан');
                $location.path('/manage_products');
            }, function failureCallback(response) {
                console.log(response);
                alert(response.data.messages);
            });
    };
})