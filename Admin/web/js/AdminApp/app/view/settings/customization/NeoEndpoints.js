Ext.define('AdminApp.view.settings.customization.NeoEndpoints', {
     extend : 'Ext.panel.Panel',
    alias : 'widget.neoendpoints',
    border: false,
    padding: 0,
    width: "100%",

    requires : ['AdminApp.view.settings.customization.CustomizationViewModel', 'AdminApp.view.settings.customization.CustomizationController'],

    viewModel : {
        type : 'CustomizationViewModel'
    },
    title : {
        text : '<span class="header-title">' + "VidyoConnect Endpoints" + '</span>',
        textAlign : 'center'
    },

    controller : 'CustomizationController',


        items: [{
            xtype: 'fieldset',
            padding: 0,
            width: "100%",
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            title: '<span class="header-title" style="font-size:13px;">Desktop/Web Endpoints</span>',
            items: [{
                xtype: 'form',
                border: false,
                layout: {
                    type: 'vbox',
                    align: 'center'
                },
                errorReader : Ext.create('Ext.data.XmlReader', {
                    record : 'field',
                    model : Ext.create("AdminApp.model.Field"),
                    success : '@success'
                }),
                reference: 'DesktopEndpointUploadForm',
                items: [ {
                    xtype: 'displayfield',
                    value: "Customization of endpoints forbidden by super administrator.",
                    bind: {
                        hidden: '{allowTenantOverride}'
                    }
                },{
                    xtype: 'fileuploadfield',
                    emptyText: "Select a .zip file",
                    fieldLabel: "Desktop/Web Customization Package",
                    width: 600,
                    labelWidth: 300,
                    hideLabel: false,
                    labelSeparator: ':',
                    name: 'desktopPackage',
                    allowBlank: false,
                    buttonConfig: {
                        text: '',
                        iconCls: 'icon-upload'
                    }, bind: {
                        hidden: '{!allowTenantOverride}'
                    }
                }, {
                    xtype: 'hidden',
                    name: csrfFormParamName,
                    value: csrfToken
                }],
                buttons: ['->', {
                    text: l10n('upload'),
                    formBind: true,
                    formName: 'DesktopEndpointUploadForm',
                    ajaxUrl: 'adminsavedesktopcustomization.ajax',
                    reloadUrl: 'adminendpointcustomization.ajax',
                    successUrl: 'sp',
                    listeners: {
                        click: 'onClickUploadCustomizeDesktopEndpoint'
                    }, bind: {
                        hidden: '{!allowTenantOverride}'
                    }
                }, {
                    text: l10n('download'),
                    image: 'sp',
                    bind: {
                        disabled: '{!isNeoDesktopCustomization}',
                        hidden: '{!allowTenantOverride}'
                    },
                    listeners: {
                        click: 'onClickDownloadCustomizeDesktopEndpoint'
                    }
                }, {
                    text: l10n('remove'),
                    bind: {
                        disabled: '{!isNeoDesktopCustomization}',
                        hidden: '{!allowTenantOverride}'
                    },
                    formName: 'DesktopEndpointUploadForm',
                    reloadUrl: 'adminendpointcustomization.ajax',
                    successUrl: 'sp',
                    listeners: {
                        click: 'onClickRemoveCustomizeDesktopEndpoint'
                    }
                }, '->']
            }]
        }]
});
