/**
 * @class CustomizationViewModel
 */
Ext.define('SuperApp.view.settings.customization.CustomizationViewModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.CustomizationViewModel',

    data : {
        //About
        aboutInfo : '',
        //Support
        contactInfo : '',
        //Notification
        tenantID : '',
        notificationFlag : '',
        fromEmail : '',
        toEmail : '',
        enableNewAccountNotification : '',
        smtpHost : 'localhost',
        smtpPort : '25',
        smtpSecurity : 'NONE',
        smtpUsername : '',
        smtpPassword : '',
        smtpTrustAllCerts : '',
        //Invitation View
        invitationEmailContent : '',
        invitationEmailContentHtml : '',
        voiceOnlyContent : '',
        webcastContent : '',

        //Customized Logos
        splogonameOne : '',
        uplogonameOne : '',
        vdlogonameOne : '',
        isSPBtnDisabled : true,
        
        splogonameTwo : '',
        uplogonameTwo : '',
        vdlogonameTwo : '',
        isUPBtnDisabled : true,
        
        splogonameThree : '',
        uplogonameThree : '',
        vdlogonameThree : '',
        isVDBtnDisabled : true,

        superCustomizeDesktopExists : false,
        allowTenantOverride : true,

        //Guide Properties
        vConfrenceURL : '',
        vDesktopURL : '',
        dLocationComboVal : '',
        cLocationComboVal : '',

        
        sysLocationValue : '',
        validateUrl:'n',
        roomKeyLength:0,
        roomLinkFormatVal:''
    },
    stores : {
        sysLocation : {
            fields :[{
                name : 'langCode'
            },{
                name: 'langName'
            }],
            proxy :{
                type : 'ajax',
                url : 'syslang.ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        notificationStore : {
            fields : [{
                name : 'tenantID'
            },{
                name : 'fromEmail'
            },{
                name : 'toEmail'
            },{
                name : 'enableNewAccountNotification'
            },{
                name : 'smtpHost'
            },{
                name : 'smtpPort'
            },{
                name : 'smtpSecurity'
            },{
                name : 'smtpUsername'
            },{
                name : 'smtpPassword'
            },{
                name : 'smtpTrustAllCerts'
            },{
                name : 'notificationFlag'
            }],
            proxy :{
                type : 'ajax',
                url : 'notification.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        PortalLogoStore : {
            model : 'SuperApp.model.settings.CustomizeLogos',
            proxy : {
                type : 'ajax',
                url : 'customizeduserportallogo.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        vidyoDesktopStore : {
            model : 'SuperApp.model.settings.CustomizeLogos',
            proxy : {
                type : 'ajax',
                url : 'customizedimagelogo.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        AdminLogoStore : {
            model : 'SuperApp.model.settings.CustomizeLogos',
            proxy : {
                type : 'ajax',
                url : 'customizedlogo.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        SuperCustomizeEndpointStore : {
            model : 'SuperApp.model.settings.SuperCustomizeEndpoints',
            proxy : {
                type : 'ajax',
                url : 'superendpointcustomization.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
     DialInStore: {	
            model:'SuperApp.model.settings.DialNo', 
            
         proxy : {
                type : 'ajax',
                url : 'superInvtSttngDialInCntyGridAjax.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
    autoLoad:true
},
        
   
    DialInComboStore:{	
   	fields:[{name: 'countryID', type: 'int'},
        			{name: 'name', type: 'string'},
                    {name: 'phoneCode', type: 'string'},
                     {name: 'flagFileName', type: 'string'}
                    ],
                    
    
   proxy : {
                type : 'ajax',
                url : 'invtSttngDialInCntyListAjax.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            storeId:'dialInComboStore',
    autoLoad:true
}
    }
}); 