//@examle:http://www.kdmooreconsulting.com/blogs/authentication-with-ionic-and-angular-js-in-a-cordovaphonegap-mobile-web-application/
//
angular.module('starter.services', [])
//Service REST API
//@see:http://www.activiti.org/userguide/#N1301E
///UserService
    .factory('UserService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'identity/users/:user', {user: "@user"});
        return data;
    })
///GroupService
    .factory('GroupService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'identity/groups/:group', {group: "@group"});
        return data;
    })
///GroupUserService
    .factory('GroupUserService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.endpoint + 'identity/groups/:group/members/:userId', {
            group: "@group",
            userId: "@userUrlId"
        });
        return data;
    })
///TaskService
    .factory('TaskService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'runtime/tasks/:taskId', {taskId: "@taskId"}, {
            update: {method: 'PUT', params: {taskId: "@taskId"}}
        });
        return data;
    })
///HistoryService
    .factory('HistoryService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'history/historic-process-instances/:processInstanceId', {processInstanceId: "@processInstanceId"});
        return data;
    })
///ProcessInstancesService
    .factory('ProcessService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'process-instances/:processInstanceId', {processInstanceId: "@processInstanceId"});
        return data;
    })
///ProcessDefinitionService
    .factory('ProcessDefinitionService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'repository/process-definitions/:processDefinitionId', {processDefinitionId: "@processDefinitionId"}, {
                update: {method: 'PUT', params: {processDefinitionId: "@processDefinitionId"}}
            }
        );
        return data;
    })
///ProcessDefinitionsService
    .factory('ProcessDefinitionsService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'repository/process-definitions', {});
        return data;
    })
///ProcessInstanceService
    .factory('ProcessInstanceService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'process-instance/:processInstance', {processInstance: "@processInstance"});
        return data;
    })
///ProcessInstancesService
//@see http://www.activiti.org/userguide/#N13DF1
    .factory('ProcessInstancesService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'runtime/process-instances', {});
        return data;
    })
///ProcessDefinitionIdentityLinkService
    //@see http://www.activiti.org/userguide/#N138FE,append str /identitylinks
    .factory('ProcessDefinitionIdentityLinkService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'repository/process-definitions/:processDefinitionId',
            {processDefinitionId: "@processDefinitionId"});
        return data;
    })
///JobsService
    .factory('JobService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'management/jobs/:jobId', {jobId: "@jobId"});
        return data;
    })
///ExecutionsService
    .factory('ExecutionService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'runtime/executions/:executionId', {executionId: "@executionId"});
        return data;
    })
///GroupService
    .factory('GroupService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'identity/groups/:group', {group: "@group"});
        return data;
    })
///CompanyService
    .factory('CompanyService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'company/:companyId', {
            companyId: "@companyId"
        });
        return data;
    })
///ItemService
    .factory('ItemService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'items/:itemId', {
            owner: "@owner",
            itemId: "@itemId",
            used: "@used"
        });
        return data;
    })
///ExpenseService
    .factory('ExpenseService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'expenses/:expenseId', {
            owner: "@owner",
            expenseId: "@expenseId",
            pid: "@pid",//Process definition id.
            status: "@status"
        }, {
            patch: {method: 'PATCH', params: {expenseId: "@expenseId"}}
        });
        return data;
    })
///ReportService
    .factory('ReportService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'report', {owner: "@owner"});
        return data;
    })
///ReportPDFService
    .factory('ReportPDFService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'report/pdf', {
            title: "@title",
            subtitle: "@subtitle",
            background: "@background",
            fullpage: "@fullpage"
        });
        return data;
    })
///LDAPService
    .factory('LDAPService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'ldap/search', {
            baseOn: "@baseOnStr",
            filter: "@filterStr"
        });
        return data;
    })
///InvoiceService
    .factory('InvoiceService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'upload', {owner: "@owner"});
        return data;
    })
///CategoryService
.factory('CategoryService', function ($resource, CONFIG_ENV) {
    var data = $resource(CONFIG_ENV.api_endpoint + 'category', {root: "@root",parentId:"@parentId"});
    return data;
})
///VendorService
    .factory('VendorService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'vendors/:vendorId', {
            latitude: "@latitude"
            , longitude: "@longitude"
            , category: "@category"
        });
        return data;
    })
///OauthTokenService
    .factory('OAuthTokenService', function ($resource, CONFIG_ENV) {
        var data = $resource(CONFIG_ENV.api_endpoint + 'oauth/token', {
            clientId: "@clientId"
            , clientSecret: "@clientSecret"
        });
        return data;
    })
///HTTP Header communication.
    .factory('Base64', function () {
        var keyStr = 'ABCDEFGHIJKLMNOP' +
            'QRSTUVWXYZabcdef' +
            'ghijklmnopqrstuv' +
            'wxyz0123456789+/' +
            '=';
        return {
            encode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                do {
                    chr1 = input.charCodeAt(i++);
                    chr2 = input.charCodeAt(i++);
                    chr3 = input.charCodeAt(i++);

                    enc1 = chr1 >> 2;
                    enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                    enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                    enc4 = chr3 & 63;

                    if (isNaN(chr2)) {
                        enc3 = enc4 = 64;
                    } else if (isNaN(chr3)) {
                        enc4 = 64;
                    }

                    output = output +
                    keyStr.charAt(enc1) +
                    keyStr.charAt(enc2) +
                    keyStr.charAt(enc3) +
                    keyStr.charAt(enc4);
                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";
                } while (i < input.length);

                return output;
            },

            decode: function (input) {
                var output = "";
                var chr1, chr2, chr3 = "";
                var enc1, enc2, enc3, enc4 = "";
                var i = 0;

                // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
                var base64test = /[^A-Za-z0-9\+\/\=]/g;
                if (base64test.exec(input)) {
                    alert("There were invalid base64 characters in the input text.\n" +
                    "Valid base64 characters are A-Z, a-z, 0-9, '+', '/',and '='\n" +
                    "Expect errors in decoding.");
                }
                input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

                do {
                    enc1 = keyStr.indexOf(input.charAt(i++));
                    enc2 = keyStr.indexOf(input.charAt(i++));
                    enc3 = keyStr.indexOf(input.charAt(i++));
                    enc4 = keyStr.indexOf(input.charAt(i++));

                    chr1 = (enc1 << 2) | (enc2 >> 4);
                    chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                    chr3 = ((enc3 & 3) << 6) | enc4;

                    output = output + String.fromCharCode(chr1);

                    if (enc3 != 64) {
                        output = output + String.fromCharCode(chr2);
                    }
                    if (enc4 != 64) {
                        output = output + String.fromCharCode(chr3);
                    }

                    chr1 = chr2 = chr3 = "";
                    enc1 = enc2 = enc3 = enc4 = "";

                } while (i < input.length);

                return output;
            }
        };
    })
    //@see http://stackoverflow.com/questions/16627860/angular-js-and-ng-swith-when-emulating-enum
    .factory('Enum', [function () {
        var service = {
            //
            expenseStatus: {
                Approved: "Approved",
                Saved: "Saved",
                Submitted: "Submitted",
                Rejected: "Rejected",
                Completed: "Completed"
            }
            //
            , itemType: [
                //ApproveAhead:
                {
                    name: "预审批",
                    data: "ApproveAhead"
                },
                //CostComsumed:
                {
                    name: "已消费",
                    data: "CostConsumed"
                }
            ]
            //LDAP ou name list.
            , groupNames: [
                "employees", "management", "accountancy"
            ]
            //Task action list.@see: http://www.activiti.org/userguide/#N14A5B
            , taskActions: {
                Complete: "complete",
                Claim: "claim",
                Delegate: "delegate",
                Resolve: "resolve"
            }
            , getUUID: function () {
                // http://www.ietf.org/rfc/rfc4122.txt
                var s = [];
                var hexDigits = "0123456789abcdef";
                for (var i = 0; i < 36; i++) {
                    s[i] = hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
                }
                s[14] = "4";  // bits 12-15 of the time_hi_and_version field to 0010
                s[19] = hexDigits.substr((s[19] & 0x3) | 0x8, 1);  // bits 6-7 of the clock_seq_hi_and_reserved to 01
                s[8] = s[13] = s[18] = s[23] = "-";

                var uuid = s.join("");
                return uuid;
            }
            , getTimestamp: function () {
                var now = new Date;
                var utc_timestamp = Date.UTC(now.getUTCFullYear(), now.getUTCMonth(), now.getUTCDate(),
                    now.getUTCHours(), now.getUTCMinutes(), now.getUTCSeconds(), now.getUTCMilliseconds());
                return utc_timestamp;
            }
            , taskDefinitionKeys: {
                Handle: "handleReimbursementRequestTask"
                , Adjust: "adjustReimbursementRequestTask"
                , Accountancy: "handleReimbursementAccountancyTask"
            }
        };
        return service;
    }])
    ///Utilities
    ///For local storage.
    .factory('$localStorage', ['$window', function ($window) {
        return {
            set: function (key, value) {
                $window.localStorage[key] = value;
            },
            get: function (key, defaultValue) {
                return $window.localStorage[key] || defaultValue;
            },
            setObject: function (key, value) {
                $window.localStorage[key] = JSON.stringify(value);
            },
            getObject: function (key) {
                return JSON.parse($window.localStorage[key] || '{}');
            }
        }
    }])
    ///
    .factory('$geoLocation', function ($localStorage) {
        return {
            setGeolocation: function (latitude, longitude) {
                var _position = {
                    latitude: latitude,
                    longitude: longitude
                }
                $localStorage.setObject('geoLocation', _position);
            },
            getGeolocation: function () {
                return glocation = {
                    lat: $localStorage.getObject('geoLocation').latitude,
                    lng: $localStorage.getObject('geoLocation').longitude
                }
            }
        }
    })
    ///@see: http://forum.ionicframework.com/t/ionicloading-in-http-interceptor/4599/7
    .factory('TrendicityInterceptor',
    function ($injector, $q, $log) {

        var hideLoadingModalIfNecessary = function () {
            var $http = $http || $injector.get('$http');
            if ($http.pendingRequests.length === 0) {
                $injector.get('$ionicLoading').hide();
            }
        };

        return {
            request: function (config) {
                $injector.get('$ionicLoading').show();

                // Handle adding the access_token or auth request.

                return config;
            },
            requestError: function (rejection) {
                hideLoadingModalIfNecessary();
                return $q.reject(rejection);
            },
            response: function (response) {
                hideLoadingModalIfNecessary();
                return response;
            },
            responseError: function (rejection) {
                hideLoadingModalIfNecessary();
                //http status code check
                $log.error("detected what appears to be an Instagram auth error...", rejection);
                if (rejection.status == 400) {
                    rejection.status = 401; // Set the status to 401 so that angular-http-auth inteceptor will handle it
                }
                return $q.reject(rejection);
            }
        };
    }
);
;