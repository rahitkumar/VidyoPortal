Ext.define('SuperApp.view.settings.security.SecurityViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.SecurityViewModel',

    data : {
        isRestartPending : true,
        //SSL Private Key
        KeyInfoSize : '',
        PrivateHashKey : '',

        //SSL CSR
        country : '',
        state : '',
        city : '',
        company : '',
        division : '',
        domain : '',
        email : '',
        csr : '',

        //Server Certificate
        ServerCertificateDesc : '',
        sckey : '',
        keyMatch : true,

        //CA Certificate
        cacontent : '',
        cacert : '',

        //Advance
        ocspEnabledFlag :'',
        ocspOverrideFlag : '',
        ocspDefaultResponder : '',
        useDefaultRootCerts :'',
        sslOptionsFlag : false,
        sslText : l10n('super-security-ssl-enable'),
        
        //Passwords
        passwordExpiredPeriod : 0,
        failCount :0,
        inactiveDays : 0,
        expiryDays : 0,
        minPINLength :6,
        
        //Applications
        isDisabled : true,
        
        isPasswordView : false,
        encryption : l10n('enable-encryption'),
        
        defaultRoot : "off"
    },

    //Applications
    stores : {
        applicationStore : {
            fields : [{
                name : 'appName'
            }, {
                name : 'networkInterface'
            }, {
                name : 'unsecurePort'
            }, {
                name : 'securePort'
            }, {
                name : 'ocsp',
                type : 'boolean'
            }],
            storeId : "applicationstore",
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'security/security_applications_get.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        networkInterfaceStore : {
        fields : [{
            name : 'id'
        }, {
            name : 'interfaceName'
        }],
        storeId : "networkinterfacestore",
            proxy : {
                type : 'ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                url : 'security/security_applications_network_interfaces.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
    }
    }
});