/**
 * @class CustomizationViewModel
 */
Ext.define('AdminApp.view.settings.customization.CustomizationViewModel', {
    extend : 'Ext.app.ViewModel',

    alias : 'viewmodel.CustomizationViewModel',

    data : {
        //About
        aboutInfo : '',
        //Support
        contactInfo : '',
        //Notification
        tenantID : '',
        fromEmail : '',
        toEmail : '',
        enableNewAccountNotification : '',
        smtpHost : '',
        smtpPort : '',
        smtpSecurity : '',
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

        adminCustomizeDesktopExists : false,
        allowTenantOverride : true,

        //Guide Properties
        vConfrenceURL : '',
        vDesktopURL : '',
        dLocationComboVal : '',
        cLocationComboVal : '',

        sysLocationValue : '',


    },
    stores : {
        sysLocation : {
            fields : [{
                name : 'langCode'
            }, {
                name : 'langName'
            }],
            proxy : {
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
            }, {
                name : 'fromEmail'
            }, {
                name : 'toEmail'
            }, {
                name : 'enableNewAccountNotification',type:'boolean'
            }],
            proxy : {
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
        customizeUserPortalStore : {
            model : 'AdminApp.model.settings.CustomizeLogos',
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
            model : 'AdminApp.model.settings.CustomizeLogos',
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
        AdminCustomizeEndpointStore : {
            model : 'AdminApp.model.settings.AdminCustomizeEndpoints',
            proxy : {
                type : 'ajax',
                url : 'adminendpointcustomization.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        DialInStore: {	
            model:'AdminApp.model.settings.DialNo',   
         proxy : {
                type : 'ajax',
                url : 'adminInvtSttngDialInCntyGridAjax.ajax',
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