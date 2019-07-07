Ext.define('SuperApp.view.settings.globalsettings.VidyoWeb', {
    extend : 'Ext.form.Panel',
    alias : 'widget.vidyoweb',

    title : {
        text : '<span class="header-title">'+l10n('vidyo-web')+'</span>',
        textAlign : 'center'
    },

    requires : ['SuperApp.view.settings.globalsettings.GlobalFeatureSettingsController'],

    controller : "GlobalFeatureSettingsController",
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
    			align : 'center'
    		},
            items : [{
                xtype : 'textfield',
                fieldLabel : l10n('version'),
                editable : false,
                labelWidth : 300,
                width : 600,
                name : 'vidyoWebVersion'
            }, {
                xtype : 'checkbox',
                columns : 1,
                reference:'vidyoWebAvailable',
                name : 'vidyoWebAvailable',
                labelWidth : 300,
                width : 600,
                inputValue :'enabled',
                fieldLabel : rec.get('vidyoWebAvailableTitle') + '<div style="font-size:10px;color:#666;">' + rec.get('vidyoWebAvailableDesc') + '</div>',
                labelSeparator:'',
				listeners : {
					change : function(cb, checked) {
						var fieldset = cb.ownerCt;
						Ext.Array.forEach(fieldset.query('[name=vidyoWebEnabled]'),
								function(field) {
									if (!checked){
										field.setValue(false);
									}
									field.setDisabled(!checked);
									field.el.animate({
										opacity : !checked ? 0.3 : 1
									});
								});
					}
				}
            }, {
                xtype : 'checkbox',
                labelWidth : 300,
                width : 600,
                inputValue :'enabled',
                fieldLabel : rec.get('vidyoWebEnabledTitle') + '<div style="font-size:10px;color:#666;">' + rec.get('vidyoWebEnabledDesc') + '</div>',
                labelSeparator:'',
                name : 'vidyoWebEnabled'
            }]
        }];
        me.buttons = [{
            text : l10n('save'),
            listeners : {
                click : "onClickVidyoWebSave"
            }},{
                text : l10n('cancel'),
                listeners : {
                    click : "resetVidyoWebForm"
        }}];
        me.callParent();
    },

    loadRecord : function(rec) {
        var me = this;
        me.getForm().loadRecord(rec);
        me.lookupReference('vidyoWebAvailable').fireEvent('change', me,rec.get('vidyoWebAvailable'));
    }
});
