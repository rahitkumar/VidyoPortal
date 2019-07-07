Ext.define('SuperApp.view.settings.globalsettings.VidyoDesktop', {
    extend : 'Ext.form.Panel',
    alias : 'widget.vidyodesktop',
    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController'],

    controller : "GlobalFeatureSettingsController",

    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'fieldset',
            width : '98%',
            minHeight : 385,
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
                    text : '<span class="header-title">'+l10n('vidyo-desktop')+'</span>',
                    textAlign : 'center'
                }, '->']
            }, {
                xtype : 'checkbox',
                fieldLabel : '<b>'+l10n('make-16-tiles-layout-available-on-your-vidyoportal')+'</b>',
                name : 'tiles16Available',
                margin : 5,
                inputValue : "on",
                labelWidth : 350,
                width : 700
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
                    listeners : {
                        click : 'onClickVidyoDesktopSave'
                    }
                }]
            }]
        }];

        me.callParent(arguments);
    },

    loadRecord : function(rec) {
        var me = this;
        me.down('checkbox').setValue(rec.get('tiles16Available') ? "on" : "off");
    }
});
