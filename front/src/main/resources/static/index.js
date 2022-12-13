var myApp = angular.module('front', ['ngRoute', 'ngStorage']);

const contextPath = "http://localhost:9090/eshop/";

const coreApiURI = "http://localhost:9090/core/api/v2/";
const cartsApiURI = "http://localhost:9091/shopping-carts/api/v1/";
const ordersApiULI = "http://localhost:9092/eshop-orders/api/v1/";

(function (app) {
    app
        .config(function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'main_page/mainPage.html',
                    controller: 'mainPageController'
                })
                .when('/products', {
                    templateUrl: 'prod/products_list.html',
                    controller: 'productListController'
                })
                .when('/all_users', {
                    templateUrl: 'users/users_list.html',
                    controller: 'usersListController'
                })
                .when('/manage_products', {
                    templateUrl: 'prod/manage/products_manage.html',
                    controller: 'productListController'
                })
                .when('/new_product', {
                    templateUrl: 'prod/create_edit/product.html',
                    controller: 'editProductForm'
                })
                .when("/edit_product", {
                    templateUrl: 'prod/create_edit/product.html',
                    controller: 'editProductForm'
                })
                .when('/checkout', {
                    templateUrl: 'checkout/checkout.html',
                    controller: 'checkoutController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        })
        .run(function ($rootScope, $http, $localStorage) {
            if ($localStorage.eMarketUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.eMarketUser.token;
            }

            $rootScope.isUserLoggedIn = function () {
                return !!$localStorage.eMarketUser;
            };

            $rootScope.hasUserRole = function (rolesArray) {
                if ($localStorage.eMarketUser == null) return false;
                let authorities = $localStorage.eMarketUser.authorities;
                for (let i = 0; i < authorities.length; i++) {
                    if (rolesArray.includes(authorities[i])) return true;
                }
                return false;
            };
        });

})(myApp);

myApp.controller('navController', function ($scope, $http, $localStorage) {

    $scope.tryToAuth = function () {
        $http.post(coreApiURI + 'auth', $scope.user)
            .then(function successCallBack(response) {
                if (response.data.token) {
                    // console.log(response.data);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.eMarketUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        authorities: response.data.authorities
                    };

                    $scope.user.username = null;
                    $scope.user.password = null;

                }
            }, function errorCallBack(response) {
                alert(response.data.message);
                console.log(response);
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();
    };

    $scope.clearUser = function () {
        delete $localStorage.eMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $scope.generatePagesIndexes = function (startPage, pageData) {
        let arr = [];
        endPage = pageData.totalPages;
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }

        return arr;
    }

});



