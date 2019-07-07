 Ext.define('AdminApp.view.settings.auth.SAML', {
    extend: 'Ext.form.Panel',
    alias: 'widget.saml',
       
    bodyPadding: 20,
    reference:'samlform',
        controller : 'AuthenticationViewController',
    trackResetOnLoad: true,
        bodyStyle: 'background:#F6F6F6;',


initComponent: function () {
        var me =this;
me.items=[{
            
                    xtype: 'hiddenfield',
                    name: 'samlflag',
                    value: false
                },{
                    xtype: 'hiddenfield',
                    name: 'samlsavebutton',
                    value: false,
                    formBind:true,
                    listeners:{
                         enable:'enableAuthSaveButton',
                        disable:'disableAuthSaveButton'
                     }
                },{               

                xtype: 'fieldset',
               
                width: '100%',
                hideBorders: true,
               
                title: 'SAML',
                autoHeight: true,
                defaults: {
                    margin: 3,
                    padding: 3,
                    labelWidth: 250
                },
                items: [ {
                    xtype: 'textarea',
                    name: 'samlIdpMetadata',
                    reference: 'samlIdpMetadata',
                    fieldLabel: l10n('identity-provider-idp-metadata-xml'),
                    allowBlank: false,
                    dirty:false,
                    width: '70%',
                    height: 200
                   
                }, {
                    xtype: 'textfield',
                    name: 'samlSpEntityId',
                    reference: 'samlSpEntityId',
                    fieldLabel: l10n('entity-id'),
                    allowBlank: false,
                    width: 500
                  
                }, {
                    xtype: 'radiogroup',
                    fieldLabel: l10n('security-profile'),
                    columns: [100, 100],
                    vertical: true,
                    name: 'samlSecurityProfileGroup',
                    items: [{
                        boxLabel: 'MetaIOP',
                        name: 'samlSecurityProfile',
                        inputValue: "METAIOP"
                    }, {
                        boxLabel: 'PKIX',
                        name: 'samlSecurityProfile',
                        inputValue: "PKIX",
                        checked:true
                    }]
                }, {
                    xtype: 'radiogroup',
                    fieldLabel: l10n('ssl-tls-profile'),
                    columns: [100, 100],
                    vertical: true,
                    name: 'samlSSLProfileGroup',
                    items: [{
                        boxLabel: 'MetaIOP',
                        name: 'samlSSLProfile',
                        inputValue: "METAIOP"
                    }, {
                        boxLabel: 'PKIX',
                        name: 'samlSSLProfile',
                        inputValue: "PKIX",
                        checked:true
                    }]
                }, {
                    xtype: 'radiogroup',
                    fieldLabel: l10n('sign-metadata'),
                    columns: [100, 100],
                    vertical: true,
                    name: 'samlSignMetadataGroup',
                    items: [{
                        boxLabel: l10n('yes'),
                        name: 'samlSignMetadata',
                        inputValue: "YES"
                    }, {
                        boxLabel: l10n('no'),
                        name: 'samlSignMetadata',
                        inputValue: "NO",
                        checked:true
                    }]
                }, {
                    xtype:'panel',                   
                   bodyStyle: 'background:#F6F6F6;',
                   layout: {
                type: 'hbox',
                align:'center'
              },
                    items:[{
                    xtype: 'combo',
                    labelWidth:250,
                    width:350,
                    fieldLabel: l10n('saml-provision-type'),
                    value: 1,
                    name: 'samlmappingflag',
                    reference: 'samlProvisionType',
                    allowBlank: false,
                    editable: false,   
                    store: [
                        ['0', 'Local'],
                        ['1', 'SAML']
                    ],
                    listeners: {
                        select: 'samlProvisiontypeComboSelect'
                    }
                }, {
                        xtype: 'button',
                        text: l10n('edit-idp-attribute-mapping'),
                        tooltip: l10n('click-to-open-idp-attribute-mappings'),
                        name: 'samlmappingbtn',
                        reference: 'samlmappingbtn',
                        viewtype: 'editSAMLAttribute',
                     
                        cls: 'footerlinks',
                        testLdap: false,
                        listeners: {
                            click: 'onClickConnectionTest'
                        }
                    }
                ]},
                {
                    xtype: 'textfield',
                    name: 'idpAttributeForUsername',
                    reference: 'idpAttributeForUsername',
                    width: 500,
                    hidden:true,
                    disabled:true,
                    fieldLabel: l10n('idp-attribute-for-username'),
                    enableKeyEvents: true,
                    allowBlank:false
                }, {
                    xtype: 'panel',
                    margin: 5,
                    border: false,
                    buttonAlign: 'center',
                    buttons:[{
                     
                            name: 'samlSPMetadata',
                            text: l10n('view-service-provider-sp-metadata-xml'),
                            helpText: l10n('view-service-provider-sp-metadata-xml'),
                    
                            handler: 'samlSPMetadata'
                        }]
                }]
            

       
        
       }];
       me.callParent();

},
        
       
     listeners: {
        dirtychange: function(form,isDirty) {
            //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
            if(isDirty && form.isValid()){
                Ext.getCmp('authsave').enable();
            }else{
               Ext.getCmp('authsave').disable(); 
            }
         },
 
      }
         
    
       
 }); 
