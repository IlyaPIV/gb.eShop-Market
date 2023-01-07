var myApp = angular.module('front', ['ngRoute', 'ngStorage']);

const gatewayPath = "http://localhost:5555/";

const coreApiURI = gatewayPath + "core/api/v2/";
const cartsApiURI = gatewayPath + "carts/api/v1/";
const ordersApiURI = gatewayPath + "orders/api/v1/";
const authApiURI = gatewayPath + "auth/api/v1/";

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
                .when('/edit_product', {
                    templateUrl: 'prod/create_edit/product.html',
                    controller: 'editProductForm'
                })
                .when('/checkout', {
                    templateUrl: 'checkout/checkout.html',
                    controller: 'checkoutController'
                })
                .when('/orders', {
                    templateUrl: 'orders/orders_list.html',
                    controller: 'ordersListController'
                })
                .when('/order_pay/:orderId', {
                    templateUrl: 'order_payment/order_pay.html',
                    controller: 'orderPaymentController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        })
        .run(function ($rootScope, $http, $localStorage) {
            if ($localStorage.eMarketUser) {
                try {
                    let jwt = $localStorage.eMarketUser.token;
                    let payload = JSON.parse(atob(jwt.split('.')[1]));
                    let currentTime = parseInt(new Date().getTime()) / 1000;
                    if (currentTime > payload.exp) {
                        console.log("Token is expired!!!");
                        delete $localStorage.eMarketUser;
                        $http.defaults.headers.common.Authorization = '';
                    }
                } catch (e) {
                }
            }

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
        $http.post(authApiURI + 'login', $scope.user)
            .then(function successCallBack(response) {
                if (response.data.token) {
                    // console.log(response.data);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.eMarketUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        authorities: response.data.authorities
                    };
                    console.log($localStorage.eMarketUser);

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



