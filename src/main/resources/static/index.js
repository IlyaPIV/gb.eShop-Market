var myApp = angular.module('front', ['ngRoute', 'ngStorage']);

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
                .otherwise({
                    redirectTo: '/'
                });
        })
        .run(function ($rootScope, $http, $localStorage) {
            if ($localStorage.eMarketUser) {
                $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.eMarketUser.token;
            }
        });

})(myApp);

myApp.controller('navController', function ($rootScope, $scope, $http, $localStorage) {
    const apiURL = 'http://localhost:9090/eshop/';

    $scope.tryToAuth = function () {
        $http.post(apiURL + 'auth', $scope.user)
            .then(function successCallBack(response) {
                if (response.data.token) {
                    console.log(response.data);

                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.eMarketUser = {
                        username: $scope.user.username,
                        token: response.data.token,
                        authorities: response.data.authorities
                    };

                }
            }, function errorCallBack(response) {
                console.log(response);
            });
    };

    $scope.tryToLogout = function () {
        $scope.clearUser();

        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };

    $scope.clearUser = function () {
        delete $localStorage.eMarketUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        return !!$localStorage.eMarketUser;
    };
});



