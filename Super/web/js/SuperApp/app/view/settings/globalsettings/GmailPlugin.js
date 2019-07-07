Ext.define('SuperApp.view.settings.globalsettings.GmailPlugin', {
    extend : 'Ext.form.Panel',
    alias : 'widget.gmailplugin',

    defaults : {
        padding : 8,
        margin : 8
    },

    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    initComponent : function() {
        var me = this,
            rec = me.fieldRec;

        if (rec.get('gmailPluginInstalled')) {
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
                        text : '<span class="header-title">'+l10n('gmail-plugin')+'</span>',
                        textAlign : 'center'
                    }, '->']
                }, {
                    xtype : 'textfield',
                    fieldLabel : l10n('version'),
                    labelAlign : 'right',
                    readOnly : true,
                    labelWidth : 150,
                    width  : '50%',
                    allowBlank  : false,
                    labelAlign : 'left',
                    value : rec.get('gmailPluginVersion')
                }, {
                    xtype : 'checkbox',
                    validation : true,
                    labelAlign : 'right',
                    allowBlank  : false,
                    fieldLabel : l10n('delete-gmail-plugin'),
                    labelSeparator : ':',
                    width : '50%',
                    labelAlign : 'left',
                    name : 'gmailCheck',
                    labelWidth : 150,
                    listeners : {
                        change : me.onChangeCheckBoxGmailPlugin,
                        scope : me
                    }
                }, {
                    xtype : 'panel',
                    border : 0,
                    defaults : {
                        margin : 5,
                        xtype : 'button'
                    },
                    layout : {
                        layout : 'hbox',
                        align : 'stretch'
                    },
                    items : [{
                        text : l10n('save'),
                        action: 'save',
                        disabled : true,
                        formBind : true,
                        listeners : {
                            click : me.onClickGmailPluginSave,
                            scope : me
                        }
                    }]
                }]
            }];
        } else {
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
                        text : '<span class="header-title">'+l10n('gmail-plugin')+'</span>',
                        textAlign : 'center'
                    }, '->']
                }, {
                    xtype : 'label',
                    text : l10n('install-gmail-plugin-to-enable-on-vidyoportal'),
                    padding : 15,
                    margin : 15
                }]
            }];
        }

        me.callParent(arguments);

    },

    onChangeCheckBoxGmailPlugin : function(cb, newValue) {
        var me = this;

        if (newValue) {
            me.down('button[action=save]').enable();
        }
    },

    loadRecord : function(rec) {
        var me = this;
    },

    onClickGmailPluginSave : function() {
        var me = this,
            params = me.down('form').getValues();

        Ext.Ajax.request({
            url : 'deletegmailplugin.ajax',
            params : params,
            method : 'POST',
            success : function(res) {
                var xmlResponse = res.responseXML;
                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                if (success == "true") {
                    Ext.Msg.alert(l10n('message'), l10n('deleted'));
                }
            }
        });
    }
});
