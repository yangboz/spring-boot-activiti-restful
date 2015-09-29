var DynamicEnvironment = DynamicEnvironment || {};
//Helper functions here.
/**
 * You can have as many environments as you like in here
 * just make sure the host matches up to your hostname including port
 */
var _environment;
var _environments = {
    local: {
        host: 'http://localhost:8100',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_endpoint: 'http://localhost:8085/api/'
            ,clientId:'clientApp'
            ,clientSecret:'1NDgzZGY1OWViOWRmNjI5ZT'
            ,ldap_partition_base_on:'dc=www'
        }
    },
    dev: {
        host: 'localhost:8082',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_endpoint: 'http://localhost:8082/api/'
            ,clientId:'clientApp'
            ,clientSecret:'1NDgzZGY1OWViOWRmNjI5ZT'
            ,ldap_partition_base_on:'dc=www'
        }
    },
    test: {
        host: '123.57.78.65:8082',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_endpoint: 'http://123.57.78.65:8082/api/'
            ,clientId:'clientApp'
            ,clientSecret:'1NDgzZGY1OWViOWRmNjI5ZT'
            ,ldap_partition_base_on:'dc=123,dc=57,dc=78,dc=65'
        }
    },
    stage: {
        host: 'stage.com',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_endpoint: 'http://stage.com/api/'
            ,clientId:'clientApp'
            ,clientSecret:'1NDgzZGY1OWViOWRmNjI5ZT'
            ,ldap_partition_base_on:'dc=www'
        }
    },
    prod: {
        host: 'prod.com',
        config: {
            /**
             * Add any config properties you want in here for this environment
             */
            api_endpoint: 'http://prod.com/api/'
            ,clientId:'clientApp'
            ,clientSecret:'1NDgzZGY1OWViOWRmNjI5ZT'
            ,ldap_partition_base_on:'dc=www'
        }
    }
};
_getEnvironment = function () {
    var host = window.location.host;

    if (_environment) {
        return _environment;
    }

    for (var environment in _environments) {
        if (typeof _environments[environment].host && _environments[environment].host == host) {
            _environment = environment;
            return _environment;
        }
    }

    return "local";//default
};
DynamicEnvironment.get = function (property) {
    var result = _environments[_getEnvironment()].config[property];
    console.log("DynamicEnvironment.get():",result);
    return result;
};