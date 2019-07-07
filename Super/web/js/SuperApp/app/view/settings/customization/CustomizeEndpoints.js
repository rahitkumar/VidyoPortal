/**
 * @CLASS CustomizeEndpoints
 */
Ext.define('SuperApp.view.settings.customization.CustomizeEndpoints', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.customizeEndpoints',
    reference : 'customizeEndpoints',
    border : false,
    title : {
    	text : '<span class="header-title">VidyoConnect Endpoints</span>',
    	textAlign : 'center'
    },
    initComponent : function() {
        var me = this;
         me.items = [{
                xtype : 'fieldset',
                padding : 0,
                layout : {
                    type : 'vbox',
                    align : 'stretch'
                },
                title : '<span class="header-title" style="font-size:13px;">Desktop/Web Endpoints</span>',
                items : [{
                    xtype : 'form',
                    border : false,
                    layout : {
                        type : 'vbox',
                        align : 'center'
                    },
                    reference : 'DesktopEndpointUploadForm',
                    errorReader : Ext.create('Ext.data.XmlReader', {
                        record : 'field',
                        model : Ext.create("SuperApp.model.settings.Field"),
                        success : '@success'
                    }),
                    items : [{
                        xtype : 'fileuploadfield',
                        emptyText : "Select a .zip file",
                        fieldLabel : "Desktop/Web Customization Package",
                        width: 600,
                        labelWidth: 300,
                        hideLabel : false,
                        labelSeparator : ':',
                        name : 'desktopPackage',
                        allowBlank : false,
                        buttonConfig : {
                            text : '',
                            iconCls : 'icon-upload'
                        }
                    },{
                        xtype : 'hidden',
                        name : csrfFormParamName,
                        value : csrfToken
                    }],
                    buttons : ['->', {
                        text : l10n('upload'),
                        formBind : true,
                        formName : 'DesktopEndpointUploadForm',
                        ajaxUrl : 'supersavedesktopcustomization.ajax',
                        reloadUrl : 'superendpointcustomization.ajax',
                        successUrl : 'sp',
                        listeners : {
                            click : 'onClickUploadCustomizeDesktopEndpoint'
                        }
                    }, {
                        text : l10n('download'),
                        image : 'sp',
                        bind : {
                            disabled : '{!isNeoDesktopCustomization}'
                        },
                        listeners : {
                            click : 'onClickDownloadCustomizeDesktopEndpoint'
                        }
                    }, {
                        text : l10n('remove'),
                        bind : {
                            disabled : '{!isNeoDesktopCustomization}'
                        },
                        formName : 'DesktopEndpointUploadForm',
                        reloadUrl : 'superendpointcustomization.ajax',
                        successUrl : 'sp',
                        listeners : {
                            click : 'onClickRemoveCustomizeDesktopEndpoint'
                        }
                    }, '->']
                }]
            },
             {
                 xtype : 'fieldset',
                 padding : 0,
                 layout : {
                     type : 'vbox',
                     align : 'stretch'
                 },
                 title : '<span class="header-title" style="font-size:13px;">Tenant Override</span>',
                 items : [{
                     xtype : 'form',
                     border : false,
                     layout : {
                         type : 'vbox',
                         align : 'center'
                     },
                     reference : 'EndpointOverrideForm',
                     errorReader : Ext.create('Ext.data.XmlReader', {
                         record : 'field',
                         model : Ext.create("SuperApp.model.settings.Field"),
                         success : '@success'
                     }),
                     items : [
                         {
                             xtype: 'checkbox',
                             columns: 1,
                             name: 'allowTenantOverride',
                             bind : {
                                 value : '{allowTenantOverride}'
                             },
                             fieldLabel: "Allow tenants to override customization",
                             labelWidth: 300,
                             inputValue: 'true',
                             uncheckedValue: 'false',
                             width: 600
                         }
                     ],
                     buttons : ['->', {
                         text : l10n('save'),
                         id: "customizeEndpointsSaveButton",
                         formBind : true,
                         formName : 'EndpointOverrideForm',
                         ajaxUrl : 'savedesktopcustomizationoverride.ajax',
                         reloadUrl : 'superendpointcustomization.ajax',
                         successUrl : 'sp',
                         listeners : {
                             click : 'onClickSaveCustomizeDesktopEndpointOverride'
                         }
                     }, {
                         text: l10n('cancel'),
                         handler:  'getSuperCustomizeEndpointData'
                     }, '->']
                 }]
             }];

        me.callParent(arguments);
    }
});
