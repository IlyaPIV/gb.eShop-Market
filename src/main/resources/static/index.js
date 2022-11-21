var myApp = angular.module('front', ['ngRoute', 'ngStorage']);

const contextPath = "http://localhost:9090/eshop/";

(function (app) {
    app
        .config(function ($routeProvider) {
            $routeProvider
                .when('/', {
                    templateUrl: 'main_page/mainPage.html',
                    controller: 'mainPageController'
                })
                .when('/products', {
                    templateUrl: 'prod/products.html',
                    controller: 'productListController'
                })
                .when('/all_users', {
                    templateUrl: 'users/users_list.html',
                    controller: 'usersListController'
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

myApp.controller('navController', function ($rootScope, $scope, $http, $localStorage) {

    $scope.tryToAuth = function () {
        $http.post(contextPath + 'auth', $scope.user)
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

});



