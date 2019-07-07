Ext.define('SuperApp.view.settings.maintenance.ExternalDatabase', {
    extend : 'Ext.form.Panel',
    alias : 'widget.externaldatabase',

    reference : 'externaldatabase',

    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    border : false,

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
                xtype : 'toolbar',
                width : '100%',
                border : 0,
                margin : 0,
                layout : {
                    align : 'center'
                },
                items : ['->', {
                    xtype : 'title',
                    text : '<span class="header-title">'+l10n('external-database')+'</span>',
                    textAlign : 'center'
                }, '->']
            }, {
                xtype : 'fieldset',
                width : '100%',
                title : l10n('database-connection-properties'),
                margin : 5,
                padding : 5,
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'radiogroup',
                    name : 'dbSource',
                    reference : 'dbsource',
                    items : [{
                        name : 'dbSource',
                        boxLabel : l10n('use-local-db'),
                        inputValue : 'default'
                    }, {
                        name : 'dbSource',
                        boxLabel : l10n('use-external-db'),
                        inputValue : 'ext'
                    }],
                    listeners : {
                        change : 'onChangeExtDBType'
                    }
                }]
            }, {
                xtype : 'fieldset',
                title : l10n('external-database-properties'),
                width : '100%',
                margin : 5,
                padding : 5,
                defaults : {
                    margin : 5
                },
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'textfield',
                    name : 'hostport',
                    fieldLabel : l10n('database-url'),
                    bind : {
                        value : '{hostport}',
                        disabled : '{isLocalDB}'
                    },
                    allowBlank : false,
                    width : 300
                }, {
                    xtype : 'textfield',
                    name : 'username',
                    bind : {
                        value : '{username}',
                        disabled : '{isLocalDB}'
                    },
                    fieldLabel : l10n('user'),
                    allowBlank : false,
                    width : 300
                }, {
                    xtype : 'textfield',
                    name : 'password',
                    bind : {
                        value : '{password}',
                        disabled : '{isLocalDB}'
                    },
                    fieldLabel : l10n('db-password'),
                    allowBlank : false,
                    inputType : 'password',
                    width : 300
                }]
            }, {
                xtype : 'toolbar',
                border : 0,
                cls :'white-footer',
                layout : {
                    layout : 'hbox',
                    align : 'stretch'
                },
                items : [{
                    xtype : 'button',
                    text : l10n('test'),
                    listeners : {
                        click : 'onTestExternalData'
                    }
                }, {
                    xtype : 'button',
                    text : l10n('save'),
                    formBind : true,
                    reference : 'externaldatasave',
                    listeners : {
                        click : 'onSaveExternalData'
                    }
                }]
            }]
        }];
        //  me.buttons = ['->', , '->'];

        me.callParent(arguments);
    },

    setExtDBOptions : function(viewModel) {
        var me = this,
            radiogroup = me.down('radiogroup[name=dbSource]');

        radiogroup.setValue({
            dbSource : viewModel.getData()['hostport'] == "localhost:3306" ? "default" : "ext"
        });
    }
});
