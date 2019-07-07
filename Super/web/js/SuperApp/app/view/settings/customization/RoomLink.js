Ext.define('SuperApp.view.settings.customization.RoomLink', {
    extend : 'Ext.form.Panel',
    alias : 'widget.roomlink',
    requires :['SuperApp.store.settings.RoomLink'],
    border : true,
    height : '100%',
    trackResetOnLoad: true,
    buttonAlign : 'center',
    title : {
        text : l10n('settings-customization-room-link'),
        textAlign : 'center'
    },

    initComponent : function() {
        var me = this;
        Ext.apply(Ext.form.VTypes, {
            roomKeyLengthVType: function(val, field) {
                var keyLength = me.down('textfield[name=roomKeyLength]').getValue();
                var re = /^[0-9]+$/;
                return re.test(keyLength);
            },
            roomKeyLengthText: l10n('the-format-is-wrong-must-only-contain-numeric-values')
        });
            me.items = [
             {
            xtype : 'fieldset',
            width : '100%',
                height : '100%',
                bodyStyle : 'padding: 10px',
                layout : {
                        type : 'vbox',
                        align : 'center'
                },
            items : [{
             xtype : 'numberfield',
             name : 'roomKeyLength',
             reference : 'roomKeyLength',
             labelWidth : 300,
             width : 700,
             maxLength: 2,
             enforceMaxLength : true,
             minValue : 8,
             maxValue : 25,
             allowBlank : false,
             allowExponential: false,
             allowDecimals : false,
             maskRe: /[0-9]/,
             fieldLabel : l10n('settings-customization-room-key-length'),
             bind : {
                 value : '{roomKeyLength}'
             }
            }, {
                xtype : 'radiogroup',
                    columns : 1,
                    fieldLabel : l10n('settings-customization-room-link-format'),
                    labelWidth : 300,
                    width : 700,
                    vertical : true,
                    name : 'roomLinkFormatGroup',
                    reference : 'roomLinkFormatGroup',
                    items : [{
                        name : 'roomLinkFormatGroup',
                        boxLabel : l10n('settings-customization-room-link-format-flex'),
                        inputValue : 'flex'
                        }, {
                        name : 'roomLinkFormatGroup',
                        boxLabel : l10n('settings-customization-room-link-format-join'),
                        inputValue : 'join'
                        }],
                    bind: {value: '{roomLinkFormatVal}'}
            }]
            }];
            me.buttons = [{
                text : l10n('save'),
                id : 'roomLinkSave',
                listeners : {
                    click : 'onClickRoomLinkSave'
                },
                formBind: true,
                disabled : true
            },
            {
                text : l10n('cancel'),
                handler : 'getRoomLinknData'
            }];
            me.callParent(arguments);
        }/*,
        listeners : {
	           dirtychange: function(form,isDirty) {
	               //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
	               if(isDirty && form.isValid()){
	                   Ext.getCmp('roomLinkSave').enable();
	               }else{
	                  Ext.getCmp('roomLinkSave').disable(); 
	               }
	            },
	    
	           afterRender: function() {
	               //bug in ext js.. if you enable formbind=true, it enables the button when you load page.
	               //without a delay, you cant never disable it. this is acceptable as per the ext js doc.
		              setTimeout(function() {
		               Ext.getCmp('roomLinkSave').disable();
		           }, 100);
	        	}
	         }*/
});

