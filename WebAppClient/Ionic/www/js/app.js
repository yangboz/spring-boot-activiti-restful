//@ref:http://alfrescoblog.com/2014/09/04/angular-js-activiti-webapp-part-iii-final/
// Ionic Starter App
// Have fun: http://codepen.io/mikkokam/pen/Geotz
// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.services' is found in services.js
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers', 'starter.services', 'nvd3', 'ngResource', 'ngCordova'
    , 'angularMoment', 'angularFileUpload', 'ngQueue', 'angularTreeview', 'ionicLazyLoad','ngAnimate', 'toaster', 'pdf'])
//console.log("app:",app);
//angular.module('starter', ['ionic', 'starter.controllers', 'starter.services','nvd3','angular-websocket'])
///Config
//.config(function(WebSocketProvider){
//    WebSocketProvider.prefix('').uri('ws://127.0.0.1:9080');
// })
    .config(['$resourceProvider', function ($resourceProvider) {
        // Don't strip trailing slashes from calculated URLs
        $resourceProvider.defaults.stripTrailingSlashes = false;
    }])
    //Support RESTful PATCH
    //@see: http://stackoverflow.com/questions/20305615/configure-angularjs-module-to-send-patch-request
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.defaults.headers.patch = {
            'Content-Type': 'application/json;charset=utf-8'
        }
        //@see: http://forum.ionicframework.com/t/ionicloading-in-http-interceptor/4599/7
        $httpProvider.interceptors.push('TrendicityInterceptor');
    }])
////$log configure
    .config(['$logProvider', function ($logProvider) {
        $logProvider.debugEnabled(true);
        //TODO:https://github.com/ThomasBurleson/angularjs-logDecorator
    }])
///ENV_config
    .constant('CONFIG_ENV', {
//var API_URL = "http://www.rushucloud.com:90/activiti-rest/";///usr/share/tomcat6/webapps/h5
//var API_URL = "http://localhost:8080/activiti-rest/";
//var API_URL = "/activiti-rest/";
//        'api_endpoint': 'http://localhost:8082/eip-rushucloud/'
        'api_endpoint': DynamicEnvironment.get('api_endpoint')
        //'api_endpoint': 'http://localhost:8080/activiti-rest/service/',
        , 'api_version': '5.16.3'
        , 'stomp_uri': 'ws://127.0.0.1:61614/stomp'
        //'stomp_uri':'ws://182.92.232.131:61614/stomp',
        , 'stomp_protocol': 'v11.stomp'
        , 'LDAP_PARTITION_BASE_ON': DynamicEnvironment.get('ldap_partition_base_on')//e.g:'dc=www'//default LDAP partition string,e.g: ou=employees,ou=rushucloud.com,dc=www
        , 'LDAP_FILTER': '(objectclass=person)'//default LDAP partition filter
        , 'WIN_LOCAL_STORAGE_NAME': 'auth_rsc'//name for window local storage.
        , 'UPLOAD_FOLDER': 'uploads/'//for image file upload
        , 'CLIENT_ID': DynamicEnvironment.get('clientId')
        , 'CLIENT_SECRET': DynamicEnvironment.get('clientSecret')
    })
    .constant('$ionicLoadingConfig', {
        //template: '<h3><icon ios="ion-ios7-reloading" android="ion-loading-c" default="ion-refreshing"></icon></h3>Loading...'
        template: "<img id='spinner' src='img/spinner.gif'>"
    })
///App run
    .run(function ($ionicPlatform, $log,$cordovaGeolocation, $geoLocation) {
        $ionicPlatform.ready(function () {
            // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
            // for form inputs)
            if (window.cordova && window.cordova.plugins.Keyboard) {
                cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            }
            if (window.StatusBar) {
                // org.apache.cordova.statusbar required
                StatusBar.styleDefault();
            }
            $log.debug("ionic.Platform info(platform,device,version,):",
                ionic.Platform.platform()
                , ionic.Platform.device()
                , ionic.Platform.version()
                , ionic.Platform.isWebView()
            );
            //@see: http://rajeevkannav.blogspot.sg/2014/11/ionic-geolocation-using-ngcordova.html
            $cordovaGeolocation
                .getCurrentPosition()
                .then(function (position) {
                    $geoLocation.setGeolocation(position.coords.latitude, position.coords.longitude)
                }, function (err) {
                    $log.error("$cordovaGeolocation",err);
                    $geoLocation.setGeolocation(39.977718050284, 116.37114806696);//Default location.
                });

            // begin a watch
            var options = {
                frequency: 1000,
                timeout: 3000,
                enableHighAccuracy: true
            };

            var watch = $cordovaGeolocation.watchPosition(options);
            watch.promise.then(function () { /* Not  used */
                },
                function (err) {
                    $log.error("$cordovaGeolocation",err);
                    $geoLocation.setGeolocation(37.38, -122.09);
                }, function (position) {
                    $geoLocation.setGeolocation(position.coords.latitude, position.coords.longitude)
                });

        });
    })

    .config(function ($stateProvider, $urlRouterProvider) {

        // Ionic uses AngularUI Router which uses the concept of states
        // Learn more here: https://github.com/angular-ui/ui-router
        // Set up the various states which the app can be in.
        // Each state's controller can be found in controllers.js
        $stateProvider

            // setup an abstract state for the tabs directive
            .state('tab', {
                url: "/tab",
                abstract: true,
                templateUrl: "templates/tabs.html"
            })

            // Each tab has its own nav history stack:

            .state('tab.dash', {
                url: '/dash',
                views: {
                    'tab-dash': {
                        templateUrl: 'templates/tab-dash.html',
                        controller: 'TabCtrlDash'
                    }
                }
            })
            .state('tab.item-detail', {
                url: '/dash/:itemId',
                views: {
                    'tab-dash': {
                        templateUrl: 'templates/detail-item.html',
                        controller: 'ItemDetailCtrl'
                    }
                }
            })
            .state('tab.item-add', {
                url: '/item-add',
                views: {
                    'tab-dash': {
                        templateUrl: 'templates/add-item.html',
                        controller: 'ItemsCtrl'
                    }
                }
            })
            .state('tab.tasks', {
                url: '/tasks',
                views: {
                    'tab-tasks': {
                        templateUrl: 'templates/tab-tasks.html',
                        controller: 'TabCtrlTasks'
                    }
                }
            })
            .state('tab.task-detail', {
                url: '/task/:taskId',
                views: {
                    'tab-tasks': {
                        templateUrl: 'templates/detail-task.html',
                        controller: 'TaskDetailCtrl'
                    }
                }
            })
            .state('tab.task-add', {
                url: '/task-add',
                views: {
                    'tab-tasks': {
                        templateUrl: 'templates/add-task.html',
                        controller: 'TasksCtrl'
                    }
                }
            })
            .state('tab.expense-detail', {
                url: '/expense/:expenseId',
                views: {
                    'tab-tasks': {
                        templateUrl: 'templates/detail-expense.html',
                        controller: 'ExpenseDetailCtrl'
                    }
                }
            })
            .state('tab.reports', {
                url: '/reports',
                views: {
                    'tab-reports': {
                        templateUrl: 'templates/tab-reports.html',
                        controller: 'ReportsCtrl'
                    }
                }
            })
            .state('tab.reports-export', {
                url: '/reports-export',
                views: {
                    'tab-reports': {
                        templateUrl: 'templates/export-reports.html',
                        controller: 'ReportsCtrl'
                    }
                }
            })
            .state('tab.my', {
                url: '/my',
                views: {
                    'tab-my': {
                        templateUrl: 'templates/tab-my.html',
                        controller: 'UsersCtrl'
                    }
                }
            })
            .state('tab.processes', {
                url: '/processes',
                views: {
                    'tab-reports': {
                        templateUrl: 'templates/tab-processes.html',
                        controller: 'TabCtrlProcesses'
                    }
                }
            })
            .state('tab.process-detail', {
                url: '/process/:processId',
                views: {
                    'tab-reports': {
                        templateUrl: 'templates/detail-process.html',
                        controller: 'ProcessDetailCtrl'
                    }
                }
            })
            .state('tab.instances', {
                url: '/instances',
                views: {
                    'tab-reports': {
                        templateUrl: 'templates/tab-instances.html',
                        controller: 'TabCtrlInstances'
                    }
                }
            })
            .state('tab.instances-detail', {
                url: '/instances/:instancesId',
                views: {
                    'tab-reports': {
                        templateUrl: 'templates/detail-instances.html',
                        controller: 'InstancesDetailCtrl'
                    }
                }
            })
        ;
        // if none of the above states are matched, use this as the fallback
        $urlRouterProvider.otherwise('/tab/dash');
    })
;

