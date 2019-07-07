Ext.define('SuperApp.view.settings.ScheduledRoom', {
    extend: 'Ext.form.Panel',
    alias: 'widget.scheduledroom',
    requires: ['SuperApp.view.settings.ScheduledRoomController', 'Ext.Msg'],
    controller: 'ScheduledRoomController',
    height: '100%',
    title: {
        text: '<span class="header-title">' + l10n('super-room-attributes') + '</span>',
        textAlign: 'center'
    },
    buttonAlign: 'center',
    trackResetOnLoad: true,
    initComponent: function() {
        var me = this,
            rec = me.fieldRec;

        me.items = [{
            xtype: 'fieldset',
            width: '90%',           
            bodyStyle: 'padding: 10px',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left',
            },
            title:l10n('super-scheduled-room-label'),
            items: [{
                xtype: 'checkbox',
                columns: 1,
                name: 'ScheduledRoomEnabled',
                fieldLabel: l10n('super-schroom-feature-enable-disable-label'),
                labelWidth: 400,
                inputValue: '1',
                uncheckedValue: '0',
                width: 500,
                rec: rec,
                value: rec.get('ScheduledRoomEnabled'),
                listeners: {
                    change: 'onChangeSchedAccessGroup'
                }
            }, {
                xtype: 'textfield',
                maskRe: /[0-9]/,
                name: 'ScheduledRoomPrefix',
                fieldLabel: "<span style='color=red'>*</span><span style='color=black'>" + l10n('super-scheduled-room-prefix') + "</span>",
                minLength: 1,
                maxLength: 3,
                labelWidth: 400,
                width: 500,
                enforceMaxLength: true,
                disabled: !rec.get('ScheduledRoomEnabled'),
                value: rec.get('ScheduledRoomPrefix'),
                allowBlank: false
               
            }]
        }, {
            xtype: 'fieldset',
            width: '90%',
          
            bodyStyle: 'padding: 10px',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left',
            },
            title: l10n('super-public-room') ,
          
            items: [{
                xtype: 'checkbox',
                columns: 1,
                name: 'publicRoomEnabledGlobal',
                reference:'publicRoomEnabledGlobal',
                fieldLabel: l10n('allow-public-room-creation-by-users'),
                inputValue: '1',
                uncheckedValue: '0',
                labelWidth: 400,
                width: 500,
                            
            }, {
                xtype: 'numberfield',
                name: 'publicRoomMaxRoomNoPerUser',
                fieldLabel: l10n('maximum-number-of-rooms-per-user'),
                minValue: 1,
                maxValue: 5,
                maxLength: 1,
                enforceMaxLength : true,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false,
                labelWidth: 400,
                width: 500,                
                allowBlank: false,
                allowDecimals:false,
                allowExponential : false,                
                bind: {
                    disabled: '{!publicRoomEnabledGlobal.checked}',

                }
           }/*,
           {
                xtype: 'numberfield',
                name: 'publicRoomMinNoExt',
                fieldLabel: l10n('minimum-number-of-digits-for-extensions'),
                minValue: 8,
                maxValue: 12,
                maxLength: 2,
                enforceMaxLength : true,
                labelWidth: 400,
                allowBlank: false,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false,
                allowDecimals:false,
                allowExponential : false,                
                width: 500,
                  bind: {
                    disabled: '{!publicRoomEnabledGlobal.checked}',

                }
           }*/]
        }];

        me.buttons = [{
            text: l10n('save'),
           id:'room-save-button',
            formBind: true,
            listeners: {
                click: 'onClickScheduledRoomSave'
            }
        }, {
            text: l10n('cancel'),
            handler:  function() {
            this.up('form').getForm().reset();
            }
        }];
        me.callParent(arguments);
    },
       listeners: {
        dirtychange: function(form,isDirty) {
            //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
            if(isDirty && form.isValid()){
                Ext.getCmp('room-save-button').enable();
            }else{
               Ext.getCmp('room-save-button').disable(); 
            }
         },
 
        afterRender: function() {
            //bug in ext js.. if you enable formbind=true, it enables the button when you load page.
            //without a delay, you cant never disable it. this is acceptable as per the ext js doc.
           setTimeout(function() {
            Ext.getCmp('room-save-button').disable();
        }, 100);
          
      }
      }
});