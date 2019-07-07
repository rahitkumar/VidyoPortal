Ext.define('SuperApp.view.settings.globalsettings.VidyoMobile', {
    extend : 'Ext.form.Panel',
    alias : 'widget.vidyomobile',
    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController'],
    controller : "GlobalFeatureSettingsController",
    title : {
        text : l10n('mobileaccess'),
        textAlign : 'center'
    },
    height : '100%',
    trackResetOnLoad: true,
    buttonAlign : 'center',
    initComponent: function() {
        var me = this,
            rec = this.fieldRec;

        me.items = [{
            xtype : 'fieldset',
            width : '100%',
    		height : '100%',
    		bodyStyle : 'padding: 10px',
    		layout : {
    			type : 'vbox',
    			align : 'center',
    			pack : 'center',
    		},
            items : [{
                xtype : 'radiogroup',
                columns : 1,
                fieldLabel : '<b>' + rec.get('vidyoMobileTitle') + '</b>',
                labelWidth : 250,
                width : 500,
                margin : 5,
                vertical : true,
                items : [{
                    name : 'mobileAccessGroup',
                    boxLabel : l10n('vidyoMobile'),
                    inputValue : 1
                }, {
                    name : 'mobileAccessGroup',
                    boxLabel : l10n('neoMobile'),
                    inputValue : 2
                 }, {
                    name : 'mobileAccessGroup',
                    boxLabel : l10n('disabled'),
                    inputValue : 0
                }, {
                	name : 'mobileAccessGroup',
                	boxLabel : l10n('individual-setting-per-tenant'),
                	inputValue : 3,
                	disabled: true
               }]
            }]
        }];
        me.buttons = [{
            text : l10n('save'),
            rec : rec,
            listeners : {
                click : 'onClickVidyoMobileSave'
            }
        }, {
            text : l10n('cancel'),
            rec : rec,
            listeners : {
                click : 'resetVidyoMobileForm'
            }
        }];
        me.callParent();
    },

    loadRecord : function(rec) {
        var me = this,
            check_value = 0;

       if (rec.get('mobileLogin')) {
            check_value = rec.get('mobileLogin');
        }

        me.down('radiogroup').setValue({
            mobileAccessGroup : check_value
        });
    }
});
