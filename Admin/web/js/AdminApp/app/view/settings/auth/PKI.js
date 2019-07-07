Ext.define('AdminApp.view.settings.auth.PKI', {
    extend: 'Ext.form.Panel',
    alias: 'widget.pki',
    requires: ['AdminApp.view.settings.auth.PKICertificateUpload'],
    bodyPadding: 20,
    reference:'pkiform',
 
    controller: 'AuthenticationViewController',
    trackResetOnLoad: true,
    bodyStyle: 'background:#F6F6F6;',
    


 viewModel: {
        type: 'AuthenticationViewModel'
    },


    initComponent: function () {
        var me = this,
            certificateStore = me.getViewModel().getStore('certificateStore'),
            certificateStorePERM = me.getViewModel().getStore('certificateStorePERM');
   me.items=[{
        xtype: 'fieldset',
        title: l10n('pki-title'),
        layout: {
            type: 'vbox',
            align: 'center'
        },
        items: [{
            xtype: 'hiddenfield',
            name: 'cacflag',
            value: false
        },
            {
                xtype: 'combobox',
                name: 'userNameExtractfrom',
                reference: 'userNameExtractfrom',
                width: 350,
                labelWidth: 165,
                fieldLabel: l10n('pki-determine-username-from'),
                store: [['0', l10n('pki-usernameextractfrom-commonname')], ['1', l10n('pki-usernameextractfrom-userprinciplename')]],
                value: '0'


            }, {
                xtype: 'fieldset',
                width: '100%',
                title: l10n('pki-enable-ocsp-revocationcheck'),
                checkboxName: 'ocspcheck',
                reference: 'oscpcheck',
                checkboxToggle: true,
                defaultType: 'checkbox', // each item will be a checkbox,
                layout: {
                    type: 'vbox',
                    align: 'center'
                },

                items: [{
                    xtype: 'checkbox',
                          width: 476,
                    labelWidth: 226,
                    name: 'ocsprespondercheck',
                    reference: 'ocsprespondercheck',
                    fieldLabel: l10n('pki-ocsp-override-responder'),
                    inputValue: '1',
                    uncheckedValue: '0',
                    listeners: {
                        change: 'ocspoverriderspderchk'
                    },
                }, {
                        fieldLabel: l10n('pki-default-responder'),
                        name: 'ocspresponder',

                        reference: 'ocspresponder',
                        width: 700,
                        labelWidth: 340,
                        xtype: 'textfield',

                    },
                    {
                        xtype: 'checkbox',
                        width: 476,
                    labelWidth: 226,
                        inputValue: '1',
                        uncheckedValue: '0',
                        fieldLabel:l10n('pki-enable-nonce'),
                        name: 'ocspnonce',

                        reference: 'ocspnonce',
                    }]
            }, {
                xtype: 'fieldset',
                width: '100%',
                title: l10n('pki-admin-operator-auth-title'),
                layout: {
                    type: 'vbox',
                    align: 'center'
                },
                items: [
                   {


                        xtype: 'combobox',
                        fieldLabel: l10n('pki-admin-operator-auth-type'),
                        store: [['0', 'Local'], ['1', 'LDAP']],
                        value: '0',
                        name: 'cacldapflag',
                        reference:'cacauthtype',
                     width: 333,
                labelWidth: 158,
                        listeners: {
                            change: 'pkiAuthTypeChange',
                            
                        }
                    },
                    {
                        xtype:'panel',
                        reference:'pkildappanel',
                        width:'100%'
                    }
                ]},{
                        xtype:'label',
                        text:l10n('pki-approval-notification'),
                        cls:'red-label',
                        hidden:true,
                        reference:'notificationlabel'
                         
                    },{ xtype:'tabpanel',
                reference:'pkitabpanel',
                width: '100%',
                border:true,
 defaults: {
        bodyPadding: 5,
     
    },
                     items:[{

                        title:l10n('pki-approval-pending'),
                        items:[{
                        xtype: 'form',
                        width: '100%',
                        reference:'certficategrid',
                        border:true,
            
                        layout: {
                            type: 'vbox',
                            align: 'center'
                        },
                        bodyStyle: 'background:#F6F6F6;',
                        items: [{

                            xtype: 'grid',
                            title: l10n('pki-uploaded-ca-certificate-list'),
                         reference:'certficategridTemp',
                            width: '100%',
                            viewConfig: { 
                                stripeRows: false, 
                                getRowClass: function(record) { 
                                    if(new Date(record.get('notAfter')) < new Date())
                                    {
                                    	return 'child-row' ; 
                                    }
                                    if(new Date(record.get('notBefore')) > new Date())
                                    {
                                    	return 'child-row' ; 
                                    }
                                    
                                } 
                            } ,
                               emptyText:l10n('pki-no-certificate-found'),
                          
                                store: certificateStore,
                           

                            columns: [ {xtype: 'rownumberer'},{


                                text: 'Serial No',
                                //renderer: me.columnChange,
                                flex:1,
                                dataIndex: 'serialNo'
                            },{


                                text: l10n('pki-trusted-ca-certificate'),
                                //renderer: me.columnChange,
                                flex:1,
                                dataIndex: 'certificateName'
                            }, {

                                    text: 'Not Before',
                                    width:200,

                                    //renderer: me.columnChange,
                                    dataIndex: 'notBefore'
                                },
                                {

                                    text: 'Not After',
                                    width:200,
                                    //renderer: me.columnChange,
                                    dataIndex: 'notAfter'
                                }],



                            bbar: [{
                xtype: 'button',             
                iconCls: 'x-fa fa-refresh',
                handler: 'refreshCertificateStore',
                tooltip: l10n('AdminLicense-refresh')
            },{
                                xtype: 'button',
                                text: l10n('add'),
                                 	iconCls : 'x-fa fa-plus-circle',
                                 handler: 'appendCACCertificate',
                                tooltip: 'Add/append a certificate'
                            },
                                {
                                    xtype: 'button',
                                    text: l10n('pki-replace-all'),
                                    iconCls: 'x-fa fa-history',
                                    handler: 'replaceCACCertificate',
                                    
                                    tooltip: l10n('pki-tooltip-replace-certificate')
                                },
                                {
                                    xtype: 'button',
                                    text: 'Export',
                                    btnType:'stage',
                                    reference:'exportStg',
                                    iconCls: 'x-fa fa-download',
                                     handler: 'exportCACCerficates',
                                    tooltip: 'Export all certifcates',      
                                     listeners:{
                                        afterRender:'enableDisableExport'
                                    }                             
                                }],
                        }
                        ]
                     }]},{

                         

                        title:'In Use',
                        items:[{
                        xtype: 'form',
                        width: '100%',
                       
                        border:true,
            
                       hidden:false,
                        layout: {
                            type: 'vbox',
                            align: 'center'
                        },
                        bodyStyle: 'background:#F6F6F6;',
                        items: [{

                            xtype: 'grid',
                            title: 'Approved CA Certificate List',
                            reference:'certficategridPerm',
                            width: '100%',
                            viewConfig: { 
                                stripeRows: false, 
                                getRowClass: function(record) { 
                                    if(new Date(record.get('notAfter')) < new Date())
                                    {
                                    	return 'child-row' ; 
                                    }
                                    if(new Date(record.get('notBefore')) > new Date())
                                    {
                                    	return 'child-row' ; 
                                    }
                                    
                                } 
                            } ,
                                 emptyText: 'No certificate found',
                           
                                store: certificateStorePERM,
                            

                            columns: [ {xtype: 'rownumberer'},{


                                text: 'Serial No',
                                //renderer: me.columnChange,
                                flex:1,
                                dataIndex: 'serialNo'
                            },{


                                text: 'Trusted CA Certificate',
                                //renderer: me.columnChange,
                                flex:1,
                                dataIndex: 'certificateName'
                            }, {

                                    text: 'Not Before',
                                    width:200,

                                    //renderer: me.columnChange,
                                    dataIndex: 'notBefore'
                                },
                                {

                                    text: 'Not After',
                                    width:200,
                                    //renderer: me.columnChange,
                                    dataIndex: 'notAfter'
                                }],



                            bbar: [{
                xtype: 'button',             
                iconCls: 'x-fa fa-refresh',
                handler: 'refreshCertificateStorePerm',
                tooltip: l10n('AdminLicense-refresh')
                            },
                                {
                                    xtype: 'button',
                                    text: 'Export',
                                    btnType:'perm',
                                    iconCls: 'x-fa fa-download',
                                    reference:'exportPerm',
                                    handler: 'exportCACCerficates',
                                    tooltip: 'Export all certifcates',
                                    listeners:{
                                        afterRender:'enableDisableExport'
                                    }
                                    
                                }],
                        }
                        ]
                     
                     }]}]

                    }]
            }


        ];
        me.callParent();
    },
        listeners: {
            dirtychange: function (form, isDirty) {
                //for ldap
                if (isDirty && form.isValid() && Ext.getCmp('isldapconnectiontestsuccess') ){
                   if(Ext.getCmp('isldapconnectiontestsuccess').getValue()=='true' ){                     
                        Ext.getCmp('authsave').enable();
                   }else{
                       //connection test is invalid
                         Ext.getCmp('authsave').disable();
                   }
                } 
                //local. local doesnt have ldapconnectoin component
                if (isDirty && form.isValid() && !( Ext.getCmp('isldapconnectiontestsuccess') )){
                    Ext.getCmp('authsave').enable();
                }else{
                     Ext.getCmp('authsave').disable();
                }
            },
                validitychange: function (obj, isValid) {
                       if(!isValid){
                          //Ext.getCmp('isldapconnectiontestsuccess').setValue(false); 
                           Ext.getCmp('authsave').disable();
                       }else{
                           if( Ext.getCmp('isldapconnectiontestsuccess') ){
                               //if ldapconnection is fine
                               if(Ext.getCmp('isldapconnectiontestsuccess').getValue()=='true'){
                                   if(obj.isDirty()){
                                      Ext.getCmp('authsave').enable();
                                   }
                               }else{
                                     Ext.getCmp('authsave').disable();
                               }
                          
                       }else{
                           if(obj.isDirty()){
                                      Ext.getCmp('authsave').enable();
                                   }
                       }
            }

                }
        }
        

    });