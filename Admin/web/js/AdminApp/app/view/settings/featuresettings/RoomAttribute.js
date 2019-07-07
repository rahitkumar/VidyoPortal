/**
 * @class RoomAttribute
 */
Ext.define('AdminApp.view.settings.featuresettings.RoomAttribute', {
    extend: 'Ext.form.Panel',
    alias: 'widget.roomattribute',

    border: false,
    height: 800,

    requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel'],

    controller: 'FeatureSettingsController',

    viewModel: {
        type: 'FeatureSettingsViewModel'
    },

    title: {
        text: '<span class="header-title">' + l10n('room-attributes') + '</span>',
        textAlign: 'center'
    },
   trackResetOnLoad: true,
    items: [{

        xtype: 'fieldset',
        border:true,
       
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'center'
        },
         title: l10n('waiting-room-and-presenter-mode'),
        
        items: [
            {
                xtype: 'checkbox',
                name: 'lectureModeAllowed',
                width: 600,
                labelWidth: 400,
                reference: 'lectureModeAllowed',
                labelSeparator: '',
                fieldLabel: l10n('enable-waiting-room-and-presenter-mode'),
                inputValue: '1',
                listeners: {
                    'change' : function(cmp, newV, oldV) {
                        if (!newV) {
                            var what = this.up("form").lookupReference("waitingRoomsEnabled");
                            this.up("form").lookupReference("waitingRoomsEnabled").setValue(false);
                            this.up("form").lookupReference("lectureModeStrict").setValue(false);
                        }
                    }
                }
            },
            {
            xtype: 'checkbox',
            name: 'waitingRoomsEnabled',
            width: 600,
            labelWidth: 400,
                reference: 'waitingRoomsEnabled',
                labelSeparator: '',
                fieldLabel: l10n('automatically-start-all-meetings-in-waiting-room-mode'),
                inputValue: '1',
                bind: {
                    disabled: '{!lectureModeAllowed.checked}'
                }
            }, {
                xtype: 'radiogroup',
                name: 'waitingRoomStateGroup',
                hideLabel: false,
                bind: {
                    disabled: '{!waitingRoomsEnabled.checked}'
            },
           
            labelSeparator: '',
            columns: 1,
            items: [{
                fieldLabel: l10n('automatically-start-the-meeting-when-the-owner-joins'),
                 width: 600,
                labelWidth: 395,
                 checked: true,
                name: 'waitUntilOwnerJoins',
                 reference:'waitUntilOwnerJoins',
                inputValue: 1
               
            }, {
                fieldLabel: l10n('stay-in-waiting-room-mode-until-a-presenter-is-selected'),
                name: 'waitUntilOwnerJoins',
                reference:'waitUntilOwnerJoins',
                width: 600,
                labelWidth: 395,
                inputValue:0
              

            }]
        }, {
            xtype: 'checkbox',
            labelSeparator: '',
            fieldLabel: l10n('enforce-presenter-and-waiting-room-modes-for-supported-endpoints-only'),
                 width: 600,
            labelWidth: 400,
            name: 'lectureModeStrict',
            reference: 'lectureModeStrict',
            inputValue: '1',
             columns: 1,
            bind: {
                disabled: '{!lectureModeAllowed.checked}'
            }
            }]
    },
    {
        xtype: 'fieldset',

    
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'center',
        },
          title: l10n('super-scheduled-room-label') ,
            bind:{
                 hidden: '{!schRoomEnabledSystemLevel}',
            },
          
        

 
        items: [{
            xtype: 'checkbox',
            columns: 1,
            labelWidth: 400,
            width: 600,
            name: 'schRoomEnabledTenantLevel',
            reference: 'schRoomEnabledTenantLevel',
            fieldLabel: l10n('admin-schroom-feature-enable-disable-label'),          
            inputValue: '1',
            uncheckedValue: '0',
            bind:{
                 disabled: '{!schRoomEnabledSystemLevel}',
            },
            

        }]
    },
    {
            xtype: 'fieldset',
            width: '90%',
            bind:{
                hidden:'{!publicRoomEnabledGlobal}',
            },
          
            bodyStyle: 'padding: 10px',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left',
            },
            title: l10n('admin-public-room'),
          
            items: [{
                xtype: 'checkbox',
                columns: 1,
                name: 'publicRoomEnabledTenant',
                reference:'publicRoomEnabledTenant',
                fieldLabel: l10n('allow-public-room-creation-by-users'),
                labelWidth: 400,
                width: 600,
                inputValue: '1',
                uncheckedValue: '0',
                bind:{
                 disabled: '{!publicRoomEnabledGlobal}',
                },
                    

                
                            
            }, {
                xtype: 'numberfield',
                name: 'publicRoomMaxRoomNoPerUser',
                reference:'publicRoomMaxRoomNoPerUser',
                fieldLabel: l10n('maximum-number-of-rooms-per-user'),
                minValue: 1,
                maxValue:5,
                labelWidth: 400,
                width: 600,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false,
                allowBlank: false,
                bind: {
                    disabled: '{!isEnabled}',

                },
                listeners: {
                    change: function(field, value) {
                        value = Ext.String.htmlEncode(value);
                        field.setValue(value);
                    }
                }
                
           }]
        }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
         id:'room-save-button',
         formBind: true,
        listeners: {
            click: 'onClickSaveRoomAttribute'
        }
    } ,{
        text: l10n('cancel'),
       handler:  function() {
            this.up('form').getForm().reset();
        }
    }],
    listeners: {
        dirtychange: function(form,isDirty) {
            //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
            if(isDirty && form.isValid()){
                Ext.getCmp('room-save-button').enable();
            }else{
               Ext.getCmp('room-save-button').disable(); 
            }
         },
 
      }
});