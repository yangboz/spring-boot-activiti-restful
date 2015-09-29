angular.module('starter.controllers', [])
//
    .controller('MainCtrl', function ($scope, $http, $rootScope, $location, $ionicModal, $ionicLoading, $ionicNavBarDelegate,
                                      CONFIG_ENV, $log, $cordovaToast, $queue, Enum, $state,
                                      ExpenseService, ItemService, TaskService, CompanyService,
                                      ProcessDefinitionsService, LDAPService, $geoLocation, VendorService,
                                      CategoryService, ProcessInstancesService,
                                      $ionicActionSheet, toaster, CategoryService,$ionicViewService) {
//Websocket/Stomp handler:
        $rootScope.connectStomp = function (username, password, queueName) {
            //
            var client = Stomp.client(CONFIG_ENV.stomp_uri, CONFIG_ENV.stomp_protocol);
            client.connect(username, password,
                function () {
                    client.subscribe(queueName,
                        function (message) {
                            $log.debug(JSON.parse(message.body));
                            //console.log(message.body);
                            if (window.plugins && window.plugins.toast) {
                                window.plugins.toast.showShortCenter(message.body);
                            }
                            else {
                                toaster.pop('note', "消息通知", message.body);
                                //$ionicLoading.show({template: message.body, noBackdrop: true, duration: 10000});
                                /*
                                $ionicActionSheet.show({
                                    buttons: [
                                        {text: '<b>查看</b>'}
                                    ],
                                    destructiveText: '',
                                    titleText: '消息通知:' + message.body,
                                    cancelText: '忽略',
                                    cancel: function () {
                                        // add cancel code..
                                        $rootScope.numberOfTasks += 1;
                                    },
                                    buttonClicked: function (index) {
                                        //$log.debug("$ionicActionSheet clicked button index:",index);
                                        //Go to task tab view
                                        $state.transitionTo("tab.tasks");
                                        //Switch slide box to task
                                        $rootScope.selectedViewIndex = 1;
                                        //Then update the badge icon.
                                        $rootScope.numberOfTasks = 0;
                                        return true;
                                    },
                                    destructiveButtonClicked: function () {
                                        //
                                    }
                                });
                                */
                            }
                        },
                        {priority: 9}
                    );
                    //client.send(queueName, {priority: 9}, "Pub/Sub over STOMP from"+username);//For testing...
                }
            );
        }
///GoBack
        $rootScope.goBack = function () {
            $ionicNavBarDelegate.back();
        };
///Loading
        $rootScope.showLoading = function () {
            $ionicLoading.show({
                //template: 'Loading...'
                template: "<img id='spinner' src='img/spinner.gif'>"
            });
        };
        $rootScope.hideLoading = function () {
            $ionicLoading.hide();
        };
///WebSocket
//    $WebSocket.onopen(function() {
//        console.log('connection');
//        $WebSocket.send('message')
//    });
//
//    $WebSocket.onmessage(function(event) {
//        console.log('message: ', event.data);
//    });
        ///LoginModal
        $ionicModal.fromTemplateUrl('templates/modal-login.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-login.html init!!!");
            $scope.loginModal = modal;
            $scope.loginModal.user = {
                username: "employee1",
                password: "passwordpassword"
            };
            //Login Modal
            if (window.localStorage[CONFIG_ENV.WIN_LOCAL_STORAGE_NAME]) {
                $scope.loginModal.hide();
            } else {
//     $urlRouterProvider.otherwise('/login');
                $scope.loginModal.show();
            }
        });
        ///ItemListModal
        $ionicModal.fromTemplateUrl('templates/modal-item-list.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-item-list.html init!!!");
            $scope.itemListModal = modal;
        });
        ///UserListModal
        $ionicModal.fromTemplateUrl('templates/modal-user-list.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-user-list.html init!!!");
            $scope.userListModal = modal;
        });
        ///ManagerListModal
        $ionicModal.fromTemplateUrl('templates/modal-manager-list.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-manager-list.html init!!!");
            $scope.managerListModal = modal;
        });
        ///VendorListModal
        $ionicModal.fromTemplateUrl('templates/modal-vendor-list.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-vendor-list.html init!!!");
            $rootScope.vendorListModal = modal;
        });
        ///ItemFilterListModal
        $ionicModal.fromTemplateUrl('templates/modal-item-filter-list.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-item-filter-list.html init!!!");
            $rootScope.itemFilterListModal = modal;
        });
        ///CategoryListModal
        $ionicModal.fromTemplateUrl('templates/modal-category-list.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-vendor-list.html init!!!");
            $rootScope.categoryListModal = modal;
        });
        ///PdfViewModal
        $ionicModal.fromTemplateUrl('templates/modal-pdf-viewer.html', {
            scope: $scope,
            backdropClickToClose: false
        }).then(function (modal) {
//        console.log("modal-vendor-list.html init!!!");
            $rootScope.pdfViewerModal = modal;
        });
///Basic
        $rootScope.$on("$stateChangeStart", function () {
            //Login Modal,only hide();
            if (window.localStorage[CONFIG_ENV.WIN_LOCAL_STORAGE_NAME]) {
                $scope.loginModal.hide();
            }
            //ShowLoading
            $rootScope.showLoading();
        });

        $rootScope.$on("$stateChangeSuccess", function () {
            //ShowLoading
            $rootScope.hideLoading();
        });

///FixtureData

        $scope.userAgent = navigator.userAgent;

        //Cleanup the modal when we're done with it!
        $scope.$on('$destroy', function () {
            $scope.loginModal.remove();
            $rootScope.itemListModal.remove();
            $rootScope.userListModal.remove();
            $rootScope.managerListModal.remove();
            $rootScope.vendorListModal.remove();
            $rootScope.itemFilterListModal.remove();
            $rootScope.categoryListModal.remove();
            $rootScope.pdfViewerModal.remove();
        });
        // Execute action on hide modal
        $scope.$on('modal.hidden', function () {
            // Execute action
        });
        // Execute action on remove modal
        $scope.$on('modal.removed', function () {
            // Execute action
        });
///App Toast,@see: http://blog.nraboy.com/2014/09/show-native-toast-notifications-using-ionic-framework/
        $scope.showToast = function (message, duration, location) {
            //TODO:device,web switch handler apply.
            $ionicLoading.show({template: message, noBackdrop: true, duration: duration == 'long' ? 5000 : 2000});
//        $cordovaToast.show(message, duration, location).then(function(success) {
//            console.log("The toast was shown");
//        }, function (error) {
//            console.log("The toast was not shown due to " + error);
//        });
        }
        //Data list.
        $rootScope.items = [];
        $rootScope.expenses = [];
        $rootScope.tasks = [];
        $rootScope.itemsSel = [];//selected items.
        $rootScope.itemIDsSel = [];//selected item ids.
        $rootScope.itemIDsSelAmount = 0;//total number of selected item's amount.
        $rootScope.expenses = [];
        $rootScope.employeeIDs = [];
        $rootScope.employeeIDsSel = [];
        $rootScope.managerIDs = [];
        $rootScope.managerIDsSel = [];
        $rootScope.vendorIdSel = -1;
        $rootScope.vendors = [];
        $rootScope.vendorSel = null;//selected vendor.
        $rootScope.categorySel = null;//selected category.
        $rootScope.tags = [];
        ///User related
        $rootScope.loggedin = true;
        $rootScope.loggedUser = null;
        $rootScope.username = null;
        $rootScope.password = null;
        $rootScope.company = null;
        ///Company related
        $rootScope.comanies = [];
        $rootScope.companyInfo = {};
        $rootScope.companyInfo.processDefinitionId = null;
        $rootScope.companyInfo.processDefinitionKey = null;
        ///ActiveMQ
        $rootScope.activemqQueueName = null;
        //Badge numbers for task notification.
        $rootScope.numberOfTasks = 0;
        ///GeoLocation,@see: http://rajeevkannav.blogspot.sg/2014/11/ionic-geolocation-using-ngcordova.html
        $log.info("GeoLocation:", $geoLocation.getGeolocation());
        $rootScope.latitude = $geoLocation.getGeolocation().lat;
        $rootScope.longitude = $geoLocation.getGeolocation().lng;
        ///View index at tab_tasks
        $rootScope.selectedViewIndex = 0;
        //Common functions
        ///
        $rootScope.loadExpenses = function () {
            ExpenseService.get({owner: $rootScope.username}, function (response) {
                //ExpenseService.get({owner: $rootScope.username}, function (response) {
                $log.info("ExpenseService.get() success, response:", response);
                $rootScope.expenses = response.data;
            }, function (error) {
                // failure handler
                $log.error("ExpenseService.get() failed:", JSON.stringify(error));
            });
        }
        ///
        $rootScope.loadTasks = function () {
            //TaskService.get({}, function (response) {
            TaskService.get({assignee: $rootScope.username}, function (response) {
                $log.debug("TaskService.get() success!", response);
                $rootScope.tasks = response.data;
            }, function (error) {
                // failure handler
                $log.error("TaskService.get() failed:", JSON.stringify(error));
            });
        }
        ///
        $rootScope.loadItems = function () {
            ItemService.get({owner: $rootScope.username, used: true}, function (response) {
                $log.debug("ItemService.get() success!", response);
                $rootScope.items = response.data;
            }, function (error) {
                // failure handler
                $log.error("ItemService.get() failed:", JSON.stringify(error));
            });
        }
        ///
        $rootScope.loadCompanies = function () {
            //
            CompanyService.get({}, function (response) {
                $log.debug("CompanyService.get(default) success!", response.data);
                $rootScope.companies = response.data;
                $rootScope.company = $rootScope.companies[0];//default value;
            }, function (error) {
                // failure handler
                $log.error("CompanyService.get() failed:", JSON.stringify(error));
            });
        }
        ///
        $rootScope.loadCompanyInfo = function () {
            //
            $log.debug("$rootScope.company:", $rootScope.company, "$rootScope.company.id:", $rootScope.company.id);
            CompanyService.get({"companyId": $rootScope.company.id}, function (response) {
                $log.debug("CompanyService.get(default) success!", response.data);
                $rootScope.companyInfo = response.data;//
            }, function (error) {
                // failure handler
                $log.error("CompanyService.get() failed:", JSON.stringify(error));
            });
        }
        ///
        $rootScope.loadProcessDefinitionsInfo = function () {
            ProcessDefinitionsService.get({}, function (response) {
                var lastIndex = response.data.length - 1;
                $log.debug("ProcessDefinitionsService.get() success!", response.data);
                $rootScope.companyInfo.processDefinitionId = response.data[lastIndex].id;
                $rootScope.companyInfo.processDefinitionKey = response.data[lastIndex].key;
                //Then assemble activemq unique queue name.
                $rootScope.activemqQueueName = $rootScope.companyInfo.processDefinitionKey
                + "/" + $rootScope.companyInfo.processDefinitionId
                + "/" + $rootScope.username;
                $log.info("activemqQueueName:", $rootScope.activemqQueueName);
                //Connect to STOMP server with ActiveMQ QueueName.
                $rootScope.connectStomp($rootScope.username, $rootScope.password, $rootScope.activemqQueueName);
            }, function (error) {
                // failure handler
                $log.error("ProcessDefinitionsService.get() failed:", JSON.stringify(error));
            });
        }
        //
        $rootScope.getLdapPartition = function (ou) {
            var ouEncode = "ou=" + ou +",";//ou=employees,dc=example,dc=com
            var subOu = "ou="+$rootScope.company.domain+",";
            var partition = ouEncode+subOu+CONFIG_ENV.LDAP_PARTITION_BASE_ON;
            $log.debug("getLdapPartition:", partition);
            return partition;
        }
        ///LDAP related
        $rootScope.loadLDAPUsers = function () {
            //var ouEncode_0 = "ou=" + Enum.groupNames[0] + ",";//employee
            var partition_0 = $rootScope.getLdapPartition(Enum.groupNames[0]);
            LDAPService.get({
                baseOn: partition_0,//e.g:dc=rushucloud,dc=com
                filter: CONFIG_ENV.LDAP_FILTER
            }, function (response) {
                $log.info("LDAPService.get(0) success, response:", response);
                $rootScope.employeeIDs = response.data;
            }, function (error) {
                // failure handler
                $log.error("LDAPService.get(0) failed:", JSON.stringify(error));
            });
            //var ouEncode_1 = "ou=" + Enum.groupNames[1] + ",";//manager
            var partition_1 = $rootScope.getLdapPartition(Enum.groupNames[1]);
            LDAPService.get({
                baseOn: partition_1,
                filter: CONFIG_ENV.LDAP_FILTER
            }, function (response) {
                $log.info("LDAPService.get(1) success, response:", response);
                $rootScope.managerIDs = response.data;
            }, function (error) {
                // failure handler
                $log.error("LDAPService.get(1) failed:", JSON.stringify(error));
            });
        }
        ///
        $rootScope.loadVendors = function () {
            //
            VendorService.get({
                latitude: $rootScope.latitude,
                longitude: $rootScope.longitude,
                category: $rootScope.categorySel.name
            }, function (response) {
                var jsonObj = JSON.parse(response.data);
                $log.info("VendorService.get() success, response(json):", jsonObj);
                $rootScope.vendors = jsonObj.businesses;
            }, function (error) {
                // failure handler
                $log.error("VendorService.get() failed:", JSON.stringify(error));
            });
        }
        //StartProcessInstance
        $rootScope.processInstanceVariables = {};//Default value;
        $rootScope.getProcessInstanceVariables = function (expenseObj){
            //Assemble variables
            var anewProcessInstanceVariables = [
                {'name': 'employeeName', 'value': $rootScope.username}
                , {'name': 'taskName', 'value': expenseObj.name}
                , {'name': 'dueDate', 'value': expenseObj.date}
                , {'name': 'participantIds', 'value': expenseObj.participantIds}
                , {'name': 'amountOfMoney', 'value': expenseObj.amount}
                , {'name': 'reimbursementMotivation', 'value': expenseObj.name}
                , {'name': 'assignee', 'value': expenseObj.managerId}
                , {'name': 'candidateUsers', 'value': expenseObj.participantIds}
                , {'name': 'candidateGroups', 'value': Enum.groupNames[0]}
            ]
            $log.debug("anewProcessInstanceVariables:", anewProcessInstanceVariables);
            return anewProcessInstanceVariables;
        }
        //@see: http://www.activiti.org/userguide/#N12EE4
        $rootScope.curProcessInstanceId = -1;
        //
        $rootScope.startProcessInstance = function (expenseObj) {
            $log.debug("$scope.startProcessInstance:" + expenseObj.id);
            //Then submitStartForm to start process
            var anewProcessInstance = new ProcessInstancesService();
            anewProcessInstance.processDefinitionKey = $rootScope.companyInfo.processDefinitionKey;
            anewProcessInstance.businessKey = $rootScope.companyInfo.businessKey;
            //Assemble variables
            anewProcessInstance.variables = $rootScope.getProcessInstanceVariables(expenseObj);
            //Save
            anewProcessInstance.$save(function (resp, putResponseHeaders) {
                $log.info("startProcessInstance() success, response:", resp);
                $rootScope.curProcessInstanceId = resp.id;
                //View history back to Expense tab inside of task table.
                //$ionicNavBarDelegate.back();
                $ionicViewService.getBackView().go();
                //Then update the expense status.
                $rootScope.patchExpense(expenseObj);
            }, function (error) {
                // failure handler
                $log.error("startProcessInstance.$save() failed:", JSON.stringify(error));
            });
        }
        //Expense status/processInstanceId patch
        $rootScope.patchExpense = function(expenseObj) {
            //Update expense with Activiti Process Instance id.
            var patchExpense = new ExpenseService(expenseObj.id);
            patchExpense.pid = $rootScope.curProcessInstanceId;
            patchExpense.owner = $rootScope.username;
            patchExpense.status = Enum.expenseStatus.Submitted;
            //Save
            patchExpense.$patch({expenseId: expenseObj.id}, function (resp, putResponseHeaders) {
                $log.info("patchExpenseItem() success, response:", resp);
                //View history back to Expense tab inside of task table.
                $ionicNavBarDelegate.back();
                //Refresh expenses
                $rootScope.loadExpenses();
            }, function (error) {
                // failure handler
                $log.error("patchExpenseItem() failed:", JSON.stringify(error));
            });
        }
        //Save the expense item at first.
        $rootScope.saveExpenseReport = function (startProcessInstance) {
            //return $log.debug($rootScope.companyInfo.processDefinitionId);
            var anewExpense = new ExpenseService();
            anewExpense.name = $rootScope.processInstanceVariables.name;
            anewExpense.owner = $rootScope.username;
            anewExpense.date = $rootScope.processInstanceVariables.date;
            anewExpense.itemIds = $rootScope.itemIDsSel.toString();
            anewExpense.managerId = $rootScope.managerIDsSel[0];
            anewExpense.participantIds = $rootScope.employeeIDsSel.toString();
            anewExpense.status = startProcessInstance ? Enum.expenseStatus.Submitted : Enum.expenseStatus.Saved;
            anewExpense.amount = $rootScope.itemIDsSelAmount;
            //Save
            anewExpense.$save(function (resp, putResponseHeaders) {
                $log.info("saveExpenseItem() success, response:", resp);
                //SubmitStartForm to start process if necessary.
                if (startProcessInstance) {
                    $rootScope.startProcessInstance(resp);
                } else {
                    //View history back to Expense tab inside of task table.
                    $ionicNavBarDelegate.back();
                    //Refresh expenses
                    $rootScope.loadExpenses();
                }
            }, function (error) {
                // failure handler
                $log.error("saveExpenseItem() failed:", JSON.stringify(error));
            });
        }
        //default
        $rootScope.categoryList = [];
        //
        $rootScope.loadCategories = function () {
            //
            CategoryService.get({}, function (response) {
                $log.info("CategoryService.get() success, response(json):", response);
                $rootScope.categoryList = response.data[0].children;
                $log.debug("$scope.categoryList:", $scope.categoryList);
                //
                $rootScope.categoryListModal.show();
            }, function (error) {
                // failure handler
                $log.error("CategoryService.get() failed:", JSON.stringify(error));
            });
        }
    })
//TabsCtrl,@see:http://codepen.io/anon/pen/GpmLn
    .controller('TabsCtrl', function ($scope, $ionicTabsDelegate) {
        $scope.goHome = function () {
            console.log($ionicTabsDelegate.$getByHandle('my-tabs'));
            console.log($ionicTabsDelegate.$getByHandle('my-tabs').selectedIndex());
            $ionicTabsDelegate.$getByHandle('my-tabs').select(0);
        }
    })
    .controller('TabCtrlDash', function ($scope, $rootScope, $ionicViewService) {
        $rootScope.DashHistoryID = $ionicViewService.getCurrentView().historyId;
        console.log('TabCtrlDash,homeHistoryID:', $rootScope.DashHistoryID);
    })
    .controller('TabLocalCtrlDash', function ($scope, $rootScope, $state, $ionicViewService) {
        console.log('TabLocalCtrlDash init!');
        $scope.onTabSelected = function () {
            $state.go('tab.dash');
            $ionicViewService.goToHistoryRoot($rootScope.DashHistoryID);
        }
    })
    .controller('TabLocalCtrlTasks', function ($scope, $rootScope, $state, $ionicViewService) {
        console.log('TabLocalCtrlTasks init!');
        $scope.onTabSelected = function () {
            $state.go('tab.tasks');
            $ionicViewService.goToHistoryRoot($rootScope.TaskHistoryID);
        }
    })
    .controller('TabCtrlTasks', function ($scope, $rootScope) {
        //Slide-box view
        $scope.changeViewIndex = function (index) {
            //$log.info("TabCtrlTasks selected view index:", index);
            $rootScope.selectedViewIndex = index;
            if (index == 0) {
                $rootScope.loadExpenses();
            }
            if (index == 1) {
                $rootScope.loadTasks();
            }
        };
    })
    .controller('ExpenseCtrl', function ($scope, $rootScope, CONFIG_ENV, ExpenseService, $log, $http) {
        //DELETE
        $scope.removeExpense = function (expenseId) {
            //
            $http.delete(CONFIG_ENV.api_endpoint + 'expenses/' + expenseId, {})
                .success(function (data, status) {
                    $log.debug("ExpenseService.delete:", data);
                    //Refresh expense list
                    $rootScope.loadExpenses();
                })
                .error(function (data, status, headers, config) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    $log.error("ExpenseService.delete failure", data);
                });
        }
    })
    //
    .controller('ExpenseDetailCtrl', function ($scope, $rootScope, $stateParams, ExpenseService, $log, CONFIG_ENV) {
        //$log.info("$stateParams.expenseId:", $stateParams.expenseId);
        $scope.expense = {};
        //@see:https://github.com/paveisistemas/ionic-image-lazy-load
        $scope.processInstanceDiagramUrl = "";//For display the workflow diagram.
        //
        $scope.loadExpenseDetail = function(){
            ExpenseService.get({expenseId: $stateParams.expenseId}, function (response) {
                $log.debug("ExpenseDetailCtrl.get() response:", response);
                $scope.expense = response;
                //
                $scope.processInstanceDiagramUrl = CONFIG_ENV.api_endpoint+"runtime/process-instances/"+$scope.expense.pid+"/diagram";
            }, function (error) {
                // failure handler
                $log.error("ExpenseDetailCtrl.get() failed:", JSON.stringify(error));
            });
        }
    })
//@example:http://krispo.github.io/angular-nvd3/#/
    .controller('ReportsCtrl', function ($scope, $rootScope, Enum, $log, ReportService, ReportPDFService, pdfDelegate, CONFIG_ENV) {
        /* Chart options */
        //@example:http://plnkr.co/edit/jOoJik?p=preview
        /* Chart options */
        $scope.reportChartOptions = {
            chart: {
                type: 'pieChart',
                height: 350,
                donut: true,
                x: function (d) {
                    return d[0];
                },
                y: function (d) {
                    return d[1];
                },
                showLabels: true,
                pie: {
                    startAngle: function (d) {
                        return d.startAngle / 2 - Math.PI / 2
                    },
                    endAngle: function (d) {
                        return d.endAngle / 2 - Math.PI / 2
                    }
                },
                transitionDuration: 500,
                legend: {
                    margin: {
                        top: 5,
                        right: 70,
                        bottom: 5,
                        left: 0
                    }
                }
            }
        };

        /* Chart data load*/
        $scope.loadReportsData = function () {
            ReportService.get({owner: $rootScope.username}, function (response) {
                $log.info("ReportService.get() success, response:", response);
                $rootScope.reportChartData = response.data;
            }, function (error) {
                // failure handler
                $log.error("ReportService.get() failed:", JSON.stringify(error));
            });
        }
        //Local variables
        $scope.pdfReportVariables = {title:"",subtitle:"",background:false,fullpage:false};
        //@see: https://github.com/winkerVSbecks/angular-pdf-viewer
        $scope.relativity = 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/149125/relativity.pdf';
        $scope.material = 'https://s3-us-west-2.amazonaws.com/s.cdpn.io/149125/material-design-2.pdf';
        $scope.pdfUrl = $scope.material;
        //
        $scope.getPDFReport = function () {
            //
            ReportPDFService.get({
                title: $scope.pdfReportVariables.title,
                subtitle: $scope.pdfReportVariables.subtitle,
                background: $scope.pdfReportVariables.background,
                fullpage: $scope.pdfReportVariables.fullpage
            }, function (response) {
                $log.info("ReportPDFService.get() success, response(json):", response);
                $scope.pdfUrl = CONFIG_ENV.api_endpoint+"/reports/" +response.data;
                //@see: https://github.com/winkerVSbecks/angular-pdf-viewer
                $rootScope.pdfViewerModal.show();
                //
                pdfDelegate
                    .$getByHandle('reportsCtrl-pdf-container')
                    .load($scope.pdfUrl);
            }, function (error) {
                // failure handler
                $log.error("ReportPDFService.get() failed:", JSON.stringify(error));
            });
        }
    })
    .controller('UsersCtrl', function ($scope, $http, UserService, $rootScope, $location, $log) {
        //UserListModal related
        //@see: http://stackoverflow.com/questions/14514461/how-can-angularjs-bind-to-list-of-checkbox-values
        $scope.toggleEmployeeListSelection = function (userId) {
            var idx = $rootScope.employeeIDsSel.indexOf(userId);
            if (idx > -1) {
                $rootScope.employeeIDsSel.splice(idx, 1);
            } else {
                $rootScope.employeeIDsSel.push(userId);
            }
            $log.debug("toggleEmployeeListSelection:", $rootScope.employeeIDsSel);
            //IdentityLink

        }
        $scope.toggleManagerListSelection = function (userId) {
            var idx = $rootScope.managerIDsSel.indexOf(userId);
            if (idx > -1) {
                $rootScope.managerIDsSel.splice(idx, 1);
            } else {
                $rootScope.managerIDsSel.push(userId);
            }
            $log.debug("toggleManagerListSelection:", $rootScope.managerIDsSel);
        }
    })

    .controller('LoginCtrl', function ($scope, $http, UserService, Base64, $rootScope, $location, $log,
                                       ProcessService, JobService, ExecutionService,
                                       HistoryService, CONFIG_ENV, Enum, $queue ,OAuthTokenService) {
        //
        $scope.selectedCompany = function (company) {
            //Update
            $log.debug("selectedCompany:", company);
            $rootScope.company = company;
        }
        $scope.getOAuthToken = function(){
            //curl -X POST -vu clientapp:1NDgzZGY1OWViOWRmNjI5ZT
            // http://localhost:8082/eip-rushucloud/oauth/token
            // -H "Accept: application/json"
            // -d "password=bar&username=foo&grant_type=password&scope=read%20write&client_secret=123456&client_id=clientapp"
            $http.defaults.headers.common['Accept'] = 'application/json';
            //
            var getOAuthTokenService = new OAuthTokenService(CONFIG_ENV.CLIENT_ID,CONFIG_ENV.CLIENT_SECRET);
            getOAuthTokenService.clientId = CONFIG_ENV.CLIENT_ID;
            getOAuthTokenService.clientSecret = CONFIG_ENV.CLIENT_SECRET;
            getOAuthTokenService.username = "foo";
            getOAuthTokenService.password = "bar";
            //
            //$http.defaults.headers.common['Authorization'] = 'Bearer ' + access_token);
            //
            getOAuthTokenService.$save(function (response, putResponseHeaders) {
                $log.info("OAuthTokenService() success:",response);
            }, function (error) {
                // failure handler
                $log.error("OAuthTokenService() failed:", JSON.stringify(error));
            });
        }
        //
        $scope.userLogin = function () {
            //return $scope.getOAuthToken();
//        $log.debug("$scope.loginModal.user.username:",$scope.loginModal.user.username,",$scope.loginModal.user.password:",$scope.loginModal.user.password);
            $http.defaults.headers.common['Authorization'] = 'Basic ' + Base64.encode($scope.loginModal.user.username + ":" + $scope.loginModal.user.password);

            UserService.get({user: $scope.loginModal.user.username}, function (response) {
                //
                $log.debug("UserService.get(login) success!", response);
                $rootScope.loggedin = true;
                $rootScope.loggedUser = response;
                $rootScope.username = $scope.loginModal.user.username;
                $rootScope.password = $scope.loginModal.user.password;
                $location.path('/dashboard');
                //Remove login modal
                $scope.loginModal.hide();
                //$log.debug("$rootScope.username:", $rootScope.username, ",$rootScope.password:", $rootScope.password);
                //Default getTasks;
                /*
                 //getProcesses test
                 //ProcessService.get({user: $rootScope.username}, function (response) {
                 //    $log.debug("ProcessService.get() success!", response);
                 //    $rootScope.processes = response.data;
                 //});
                 //getJobs test
                 JobService.get({user: $rootScope.username}, function (response) {
                 $log.debug("JobService.get() success!", response);
                 $rootScope.jobs = response.data;
                 });
                 //getExecutions test
                 ExecutionService.get({user: $rootScope.username}, function (response) {
                 $log.debug("ExecutionService.get() success!", response);
                 $rootScope.executions = response.data;
                 });
                 //getHistory(historic-process-instances) test
                 HistoryService.get({user: $rootScope.username}, function (response) {
                 $log.debug("HistoryService.get() success!", response);
                 $rootScope.historices = response.data;
                 });
                 */
                //
                var ajaxCallback = function (itemFunc) {
                        //console.log("itemFunc:"+itemFunc);
                        itemFunc.apply();
                    },
                    options = {
                        delay: 2000, //delay 2 seconds between processing items
                        paused: true, //start out paused
                        complete: function () {
                            console.log('$queue(after user login), all complete!');
                        }
                    };
                // create an instance of a queue
                //@see: https://github.com/jseppi/angular-queue
                // note that the first argument - a callback to be used on each item - is required
                var myQueue = $queue.queue(ajaxCallback, options);
                myQueue.add($rootScope.loadItems); //add one item at DashTab
                myQueue.add($rootScope.loadExpenses); //add one item at TaskTab
                myQueue.add($rootScope.loadTasks); //add one item at TaskTab
                //getCompanyInfo(businessKey,processDefinitionKey)
                myQueue.add($rootScope.loadCompanyInfo);
                //Then getProcessDefinitionsService
                myQueue.add($rootScope.loadProcessDefinitionsInfo);
                ///Search LDAP users by ou(organization unit)
                myQueue.add($rootScope.loadLDAPUsers);
                ///pre load vendors for testing.
                //myQueue.add($rootScope.loadVendors);
                //myQueue.addEach([$rootScope.loadExpenses, $rootScope.loadTasks]); //add multiple items
                myQueue.start(); //must call start() if queue starts paused
            });
        };
    })
    .controller('ItemDetailCtrl', function ($scope, $rootScope, $stateParams, ItemService, $log) {
        $log.info("$stateParams.itemId:", $stateParams.itemId);
        //
        ItemService.get({itemId: $stateParams.itemId}, function (response) {
            $log.debug("ItemService.getTaskInfo success!", response);
            $scope.item = response;
            $log.debug("ItemDetailCtrl $scope.item", $scope.item);
        });
    })
    .controller('ItemsCtrl', function ($scope, $http, Base64, $rootScope, $location, $log,
                                       ItemService, Enum, $ionicNavBarDelegate) {
        //ng-model
        $rootScope.newItem = {"name": "", "vendors": "", "invoices": "", "date": "", "owner": ""};
        $scope.preferencesItemType = Enum.itemType;
        $scope.prefType = Enum.itemType[0];//Default setting.
        $scope.setTypeSelected = function (type) {
            $scope.prefType = type;
        }
        //Query
        //CREATE,
        $scope.createItem = function () {
            //
            var anewItem = new ItemService($scope.newItem);
            anewItem.amount = $scope.newItem.amount;
            anewItem.name = $scope.newItem.name;
            anewItem.invoices = $rootScope.newItem.invoices;
            anewItem.category = $rootScope.categorySel.id;
            anewItem.vendors = $rootScope.vendorSel.business_id;
            anewItem.date = $rootScope.newItem.date;
            anewItem.owner = $rootScope.username;
            anewItem.type = $scope.prefType.data;
            anewItem.place =  $rootScope.vendorSel.address;
            //return $log.debug("createItem(),$scope.newItem:", anewItem);
            //Save
            anewItem.$save(function (t, putResponseHeaders) {
                $log.info("createItem() success, response:", t);
                //Refresh item list
                $rootScope.loadItems();
                //Reset value
                $scope.newItem = {"name": "", "vendors": "", "invoices": "", "date": "", "owner": ""};
                //View history back to Expense tab inside of task table.
                $ionicNavBarDelegate.back();
            });
        }
        //DELETE
        $scope.removeItem = function (itemId) {
            ItemService.delete({itemId: itemId}, function (data) {
                $log.debug("ItemService.delete:", data);
                //Refresh item list
                $rootScope.loadItems();
            });
            //
        }
        $scope.orderValue = 'asc';//desc
        //ORDER
        $scope.orderItems = function () {
            $scope.orderValue = ($scope.orderValue == 'asc') ? 'desc' : 'asc';
            //
            ItemService.get({order: $scope.orderValue}, function (response) {
                $log.debug("ItemService.get(order) success!", response);
                $rootScope.items = response.data;
            });
        }
        //ItemListModal related
        //@see: http://stackoverflow.com/questions/14514461/how-can-angularjs-bind-to-list-of-checkbox-values
        $scope.toggleItemListSelection = function (item, index) {
            var idx = $rootScope.itemIDsSel.indexOf(item.id);
            if (idx > -1) {
                $rootScope.itemIDsSel.splice(idx, 1);
                $rootScope.itemIDsSelAmount -= $rootScope.items[index].amount;
                $rootScope.itemsSel.splice(idx, 1);
            } else {
                $rootScope.itemIDsSel.push(item.id);
                $rootScope.itemIDsSelAmount += $rootScope.items[index].amount;
                $rootScope.itemsSel.push(item);
            }
            $log.debug("toggleItemListSelection:", $rootScope.itemIDsSel, $rootScope.itemIDsSelAmount, $rootScope.itemsSel);
        }
    })
    .controller('TasksCtrl', function ($scope, $rootScope, $http, Base64, $location, $log,
                                       $ionicNavBarDelegate,
                                       ProcessDefinitionService,
                                       TaskService, ProcessInstancesService, ExpenseService, Enum,
                                       ProcessDefinitionIdentityLinkService,
                                       $ionicActionSheet, $ionicPopup) {
        //Add a candidate starter to a process definition
        $scope.identityLinks = function () {
            //return $log.info($rootScope.companyInfo.processDefinitionId);
            //Add a candidate starter to a process definition
            var anewProcessDefintionIdentityLinkService = new ProcessDefinitionIdentityLinkService();
            anewProcessDefintionIdentityLinkService.user = $rootScope.username;
            anewProcessDefintionIdentityLinkService.$save(
                {processDefinitionId: $rootScope.companyInfo.processDefinitionId + '/identitylinks'},
                function (t, putResponseHeaders) {
                    $log.info("saveProcessDefintionIdentityLinkService() success, response:", t);
                }, function (error) {
                    // failure handler
                    $log.error("saveProcessDefintionIdentityLinkService() failed:", JSON.stringify(error));
                });
        }
        //
        $scope.orderValue = 'asc';//desc
        //ORDER
        $scope.orderTasks = function () {
            $scope.orderValue = ($scope.orderValue == 'asc') ? 'desc' : 'asc';
            //Refresh task list
            $rootScope.loadTasks();
        };
        /**
         * Used to determine whether to show the claim button or not
         */
            //$scope.isUnclaimedTask = function () {
            //    return $scope.candidateGroup && $scope.candidateGroup.id != noGroupId;
            //};
            //Claim task to report manager
        $scope.claimTask = function (taskId) {
//        $http.put('service/task/' + taskId + "/claim").
//        success(function (data, status, headers, config) {
//            // After a successful claim, simply refresh the task list with the current search params
//            $log.debug('Claim task success: ' + status);
//        }).
//        error(function (data, status, headers, config) {
//            $log.info('Couldn\'t claim task : ' + status);
//        });

            var action = new TaskService();
            action.action = Enum.taskActions.Claim;
            action.$save({"taskId": taskId}, function (resp) {
                //after finishing remove the task from the tasks list
                $log.debug("TaskService.claim() success!", resp);
                //refresh reports list view.
                $rootScope.loadTasks();
            });
        };
        //
        $scope.data = {};
        $scope.data.motivation = null;
        $scope.data.justification = null;
        //
        $scope.handleTask = function (decision, taskId) {
            //
            if (decision)//approve
            {
                $ionicPopup.show({
                    template: '<textarea placeholder="motivation" ng-model="data.motivation">',
                    title: 'Audit with motivation',
                    subTitle: 'Please input some things',
                    scope: $scope,
                    buttons: [
                        {text: 'Cancel'},
                        {
                            text: '<b>Approve</b>',
                            type: 'button-positive',
                            onTap: function (e) {
                                if (!$scope.data.motivation) {
                                    //don't allow the user to close unless he enters wifi password
                                    e.preventDefault();
                                } else {
                                    //Next func call.
                                    $scope.completeTask("accountancy1",decision, taskId, $scope.data.motivation);
                                    return $scope.data.motivation;
                                }
                            }
                        },
                    ]
                });
            } else {//reject
                $ionicPopup.show({
                    template: '<textarea placeholder="motivation" ng-model="data.motivation">',
                    title: 'Audit with motivation',
                    subTitle: 'Please input some things:',
                    scope: $scope,
                    buttons: [
                        {text: 'Cancel'},
                        {
                            text: '<b>Reject</b>',
                            type: 'button-positive',
                            onTap: function (e) {
                                if (!$scope.data.motivation) {
                                    //don't allow the user to close unless he enters wifi password
                                    e.preventDefault();
                                } else {
                                    //Next func call.
                                    $scope.completeTask($rootScope.username,decision, taskId, $scope.data.motivation);
                                    return $scope.data.motivation;
                                }
                            }
                        },
                    ]
                });
            }
        }
        //CompleteTask
        $scope.completeTask = function (assignee,decision, taskId, motivation) {
            var action = new TaskService();
            action.action = Enum.taskActions.Complete;
            action.variables = [
                {'name': 'reimbursementApproved', 'value': decision}
                , {'name': 'managerMotivation', 'value': motivation}
                //,{'name':'recipientName','value':$rootScope.participantIds}
                ,{'name':'assignee','value':assignee}
                //,{'name':'employeeName','value':$rootScope.username}
            ];
            action.$save({"taskId": taskId}, function (resp) {
                //after finishing remove the task from the tasks list
                $log.debug("TaskService.complete() success!", resp);
                //refresh reports list view.
                $rootScope.loadTasks();
            }, function (error) {
                // failure handler
                $log.error("TaskService.complete() failed:", JSON.stringify(error));
            });

        };
        $scope.re_completeTask = function (decision, taskId, motivation) {
            var action = new TaskService();
            action.action = Enum.taskActions.Complete;
            action.variables = [
                {'name': 'resendRequest', 'value': decision}
                , {'name': 'reimbursementMotivation', 'value': motivation}
            ];
            action.$save({"taskId": taskId}, function (resp) {
                //after finishing remove the task from the tasks list
                $log.debug("TaskService.re_complete() success!", resp);
                //refresh reports list view.
                $rootScope.loadTasks();
            }, function (error) {
                // failure handler
                $log.error("TaskService.re_complete() failed:", JSON.stringify(error));
            });

        };
        //ResolveTask
        $scope.resolveTask = function (taskId) {
            var action = new TaskService();
            action.action = Enum.taskActions.Resolve;
            action.$save({"taskId": taskId}, function (resp) {
                //after finishing remove the task from the tasks list
                $log.debug("TaskService.resolve() success!", resp);
                //refresh reports list view.
                $rootScope.loadTasks();
            });
        };
        //DeleteTask
        $scope.deleteTask = function (taskId) {
            var action = new TaskService();
            action.$delete({"taskId": taskId}, function (resp) {
                //after finishing remove the task from the tasks list
                $log.debug("TaskService.delete() success!", resp);
                //refresh reports list view.
                $rootScope.loadTasks();
            });
        };
        //Add an involved user to a process instance
        //@see: http://activiti.org/userguide/index.html#N1400A
        //POST runtime/process-instances/{processInstanceId}/identitylinks
        //$scope.addInvolvedUsers = function (args) {
        //}
        //View ng-if
        $scope.isHandleView = function (task) {
            return (task.taskDefinitionKey == Enum.taskDefinitionKeys.Handle);
        }
        $scope.isAdjustView = function (task) {
            return (task.taskDefinitionKey == Enum.taskDefinitionKeys.Adjust);
        }
        $scope.isAccountancyView = function (task) {
            return (task.taskDefinitionKey == Enum.taskDefinitionKeys.Accountancy);
        }
        ///
        $scope.adjustTask = function (taskId) {

            $ionicPopup.show({
                template: '<textarea placeholder="motivation" ng-model="data.motivation">',
                title: 'Adjust with motivation',
                subTitle: 'Please input some things',
                scope: $scope,
                buttons: [
                    {text: 'Cancel'},
                    {
                        text: '<b>Resend</b>',
                        type: 'button-positive',
                        onTap: function (e) {
                            if (!$scope.data.motivation) {
                                //don't allow the user to close unless he enters wifi password
                                e.preventDefault();
                            } else {
                                //Next func call.
                                //start process instance again with justification.
                                $scope.re_completeTask(true, taskId, $scope.data.motivation);
                                return $scope.data.motivation;
                            }
                        }
                    },
                ]
            });
        }
        ///
        $scope.accountancyTask = function (taskId) {

            $ionicPopup.show({
                template: '<textarea placeholder="motivation" ng-model="data.motivation">',
                title: 'Accountancy with motivation',
                subTitle: 'Please input some things',
                scope: $scope,
                buttons: [
                    {text: 'Cancel'},
                    {
                        text: '<b>Audit</b>',
                        type: 'button-positive',
                        onTap: function (e) {
                            if (!$scope.data.motivation) {
                                //don't allow the user to close unless he enters wifi password
                                e.preventDefault();
                            } else {
                                //Next func call for accountancy
                                return $scope.data.motivation;
                            }
                        }
                    },
                ]
            });
        }
    })
    .controller('TaskDetailCtrl', function ($scope, $rootScope, $stateParams, TaskService, $log) {
        $log.info("$stateParams.taskId:", $stateParams.taskId);
        //
//    TaskService.get({taskId:$stateParams.taskId}, function (response) {
        TaskService.get({taskId: $stateParams.taskId}, function (response) {
            $log.debug("TaskService.getTaskInfo success!", response);
            $scope.task = response;
            $log.debug("TaskDetailCtrl $scope.task", $scope.task);
        });
    })
    .controller('VendorsCtrl', function ($scope, $rootScope, $stateParams, $log, VendorService) {
        $scope.toggleVendorListSelection = function (vendor) {
            $rootScope.vendorIdSel = vendor.business_id;
            $rootScope.vendorSel = vendor;
            $log.debug("toggleVendorListSelection:", $rootScope.vendorSel);
        }
    })
    .controller('CategoryCtrl', function ($scope, $rootScope, $stateParams, $log, CategoryService) {
        //@see: http://stackoverflow.com/questions/14514461/how-can-angularjs-bind-to-list-of-checkbox-values
        $scope.toggleCategoryListSelection = function (category) {
            $rootScope.categorySel = category;
            $log.debug("toggleCategoryListSelection:", $rootScope.categorySel);
            //Then filter the vendors
            $rootScope.loadVendors();
        }
    })
    .controller('TagsCtrl', function ($scope, $rootScope, $stateParams, $log) {
        //
        $rootScope.tagList = [];
    })
    .controller('GroupsCtrl', function ($scope, $rootScope, $location, GroupService, $ionicModal) {
        if (typeof  $rootScope.loggedin == 'undefined' || $rootScope.loggedin == false) {
            $location.path('/login');
            return;
        }
        $scope.groups = GroupService.get();

        /**
         * Initial data of new group
         * @type {{id: string, name: string, type: string}}
         */
        $scope.newGroup = {"id": "", "name": "", "type": "security-role"};

        /**
         * Create group function
         * @param newGroup
         */
        $scope.createGroup = function (newGroup) {
            var group = new GroupService(newGroup);
            group.name = newGroup.name;
            group.id = newGroup.id;
            group.$save(function (u, putResponseHeaders) {
                $scope.groups.data.push(u);
                $scope.isCollapsed = true;
                $scope.newGroup.id = "";
                $scope.newGroup.name = "";
            });
        };

        /**
         * Remove Group
         * @param group
         */
        $scope.removeGroup = function (group) {
            GroupService.delete({"group": group.id}, function (data) {
                $scope.groups = GroupService.get();
            });
        };
    })
//
    .controller('InvoiceCtrl', function ($scope, $rootScope, $location, $log, $http, CONFIG_ENV, FileUploader, Enum, InvoiceService) {
        $scope.fromComputer = true;
        $scope.imageURI = null;//For update the display image view.
        // init variables
        $scope.data = {};
        $scope.obj;
        var pictureSource;   // picture source
        var destinationType; // sets the format of returned value
        var url;
        // get upload URL for FORM
        $scope.data.uploadurl = CONFIG_ENV.api_endpoint + "upload";
        $scope.data.uploadFolderURI = CONFIG_ENV.api_endpoint + CONFIG_ENV.UPLOAD_FOLDER;

        // on DeviceReady check if already logged in (in our case CODE saved)
        ionic.Platform.ready(function () {
            //console.log("ready get camera types");
            if (navigator.camera) {
                // website handling
                $scope.fromComputer = false;
                //pictureSource=navigator.camera.PictureSourceType.PHOTOLIBRARY;
                pictureSource = navigator.camera.PictureSourceType.CAMERA;
                destinationType = navigator.camera.DestinationType.FILE_URI;
            }
        });
        // take picture
        $scope.takePicture = function () {
            if ($scope.fromComputer) {
                $scope.takePictureFromComputer();
            } else {
                $scope.takePictureFromDevice();
            }
        }
        // take picture from mobile device
        $scope.takePictureFromDevice = function () {
            //console.log("got camera button click");
            var options = {
                quality: 50,
                destinationType: destinationType,
                sourceType: pictureSource,
                encodingType: 0
            };
            if (!navigator.camera) {
                // error handling
                return;
            }
            navigator.camera.getPicture(
                function (imageURI) {
                    //console.log("got camera success ", imageURI);
                    $scope.mypicture = imageURI;
                },
                function (err) {
                    //console.log("got camera error ", err);
                    // error handling camera plugin
                },
                options);
        };

        //TODO:@see: http://blog.nraboy.com/2014/09/use-android-ios-camera-ionic-framework/
        $scope.takeCamPicture = function () {
            var options = {
                quality: 75,
                destinationType: Camera.DestinationType.DATA_URL,
                sourceType: Camera.PictureSourceType.CAMERA,
                allowEdit: true,
                encodingType: Camera.EncodingType.JPEG,
                targetWidth: 300,
                targetHeight: 300,
                popoverOptions: CameraPopoverOptions,
                saveToPhotoAlbum: false
            };

            $cordovaCamera.getPicture(options).then(function (imageData) {
                $scope.imgURI = "data:image/jpeg;base64," + imageData;
            }, function (err) {
                // An error occured. Show a message to the user
            });
        }
        //@see: http://codepen.io/ajoslin/pen/qwpCB?editors=101
        $scope.fileName = 'nothing';
        $scope.imageFile;
        //@see: http://stackoverflow.com/questions/17922557/angularjs-how-to-check-for-changes-in-file-input-fields
        $scope.onFileChangeHandler = function () {
            $scope.imageFile = event.target.files[0];
            $log.debug("openFileDialog->file:", $scope.imageFile);
            $scope.fileName = $scope.imageFile.name;
            //$scope.$apply();
        }
        //
        $scope.takePictureFromComputer = function () {
            //console.log('fire! $scope.takePictureFromComputer()');
            ionic.trigger('click', {target: document.getElementById('id_file_invoice')});
        };
        //
        var uploader = $scope.uploader = new FileUploader({
            url: $scope.data.uploadurl + "?owner=" + $rootScope.username + "&name=" + Enum.getTimestamp()
        });

        // FILTERS
        uploader.filters.push({
            name: 'customFilter',
            fn: function (item /*{File|FileLikeObject}*/, options) {
                return this.queue.length < 10;
            }
        });

        // CALLBACKS

        uploader.onWhenAddingFileFailed = function (item /*{File|FileLikeObject}*/, filter, options) {
            console.info('onWhenAddingFileFailed', item, filter, options);
        };
        uploader.onAfterAddingFile = function (fileItem) {
            //console.info('onAfterAddingFile', fileItem);
            //$log.debug(uploader,uploader.queue);
            uploader.queue[0].upload();
        };
        uploader.onAfterAddingAll = function (addedFileItems) {
            //console.info('onAfterAddingAll', addedFileItems);
        };
        uploader.onBeforeUploadItem = function (item) {
            //console.info('onBeforeUploadItem', item);
        };
        uploader.onProgressItem = function (fileItem, progress) {
            //console.info('onProgressItem', fileItem, progress);
        };
        uploader.onProgressAll = function (progress) {
            //console.info('onProgressAll', progress);
        };
        uploader.onSuccessItem = function (fileItem, response, status, headers) {
            //console.info('onSuccessItem', fileItem, response, status, headers);
        };
        uploader.onErrorItem = function (fileItem, response, status, headers) {
            //console.info('onErrorItem', fileItem, response, status, headers);
        };
        uploader.onCancelItem = function (fileItem, response, status, headers) {
            //console.info('onCancelItem', fileItem, response, status, headers);
        };
        uploader.onCompleteItem = function (fileItem, response, status, headers) {
            //console.info('onCompleteItem', fileItem, response, status, headers);
            $log.debug("Uploader->onCompleteItem.response:", response.picture.ico);
            //Update Invoice Image view
            $scope.imgURI = $scope.data.uploadFolderURI + response.picture.ico;
            $log.debug("$scope.imgURI:", $scope.imgURI, "id:", response.id);
            $rootScope.newItem.invoices = response.id;
        };
        uploader.onCompleteAll = function () {
            //console.info('onCompleteAll');
        };

        //console.info('uploader', uploader);
    })
;
