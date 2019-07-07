Ext.define('SuperApp.view.settings.endpointsoftware.FileServer', {
    extend : 'Ext.form.Panel',
    xtype : 'fileServerForm',
    alias : 'widget.fileServer',

    requires: ['SuperApp.view.settings.endpointsoftware.EndpointSoftwareController', 'SuperApp.view.settings.endpointsoftware.EndpointSoftwareViewModel'],

    viewModel: {
        type: 'EndpointSoftwareViewModel'
    },

    controller: 'EndpointSoftwareController',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
	bodyStyle: 'background:#F6F6F6',
    title : {
        text : '<span class="header-title">'+l10n('manage-endpoint-software-submenu-fileserver')+'</span>',
        textAlign : 'center'
    },
    defaults : {
        margin : 5
    },
    buttonAlign:'center',
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'fieldset',
            width : '100%',
            padding : 0,
            margin : 5,
            layout : {
                type : 'vbox',
                align : 'center'
            },
            items : [{
                xtype : 'form',
                reference : 'fileServerForm',
                bodyStyle: 'background:#F6F6F6',
                layout : {
                    type : 'hbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'radiogroup',
                    fieldLabel : l10n('fileserver-label'),
                    labelWidth : 160,
                    width : 520,
                    name : 'fileServerMode',
                    reference : 'fileServerMode',
                    bind : {
                        value : '{uploadMode}',
                    },
                    columns : 1,
                    flex: 2,
                    items : [{
                        name : 'fileServerModeGroup',
                        boxLabel : l10n('fileserver-vidyoportal'),
                        inputValue : 'VidyoPortal',
                        bind : {
                            value : '{uploadMode}'
                        }
                    }, {
                        name : 'fileServerModeGroup',
                        boxLabel : l10n('fileserver-externalFileServer'),
                        inputValue : 'External',
                        boxLabelWidth : 100,
                        bind : {
                            value : '{uploadMode}'
                        }
                    }],
                    /*listeners : {
                        change : 'onChangeFileServerMode'
                    }*/
                }]
            }]
        }];
        me.buttons= [{
            text : l10n('save'),
            disabled : true,
            formBind: true,
            reference : 'fileServerSave',
            listeners : {
                click : 'onClickFileServerSave'
            }
        }, {
            text : l10n('cancel'),
            listeners : {
                click : 'getFileServerData'
            }
        }];
        	
        me.callParent(arguments);
    },

    listeners : {
        beforerender : 'getFileServerData'
    }
});
