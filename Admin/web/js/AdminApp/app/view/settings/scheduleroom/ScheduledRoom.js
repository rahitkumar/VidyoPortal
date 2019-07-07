Ext.define('AdminApp.view.settings.scheduleroom.ScheduledRoom', {
    extend: 'Ext.form.Panel',
    alias: 'widget.scheduledroom',

    requires: ['AdminApp.view.settings.scheduleroom.ScheduledRoomViewModel', 'AdminApp.view.settings.scheduleroom.ScheduledRoomController'],

    controller: 'ScheduledRoomController',
    title: {
        text: '<span class="header-title">' + l10n('super-scheduled-room-label') + '</span>',
        textAlign: 'center'
    },

    viewModel: {
        type: 'ScheduledRoomViewModel'
    },


    border: false,
    height: 800,

    items: [{
        xtype: 'fieldset',
        height: '100%',
    
        layout: {
            type: 'vbox',
            align: 'center',
            pack: 'center',
        },
        items: [{
            xtype: 'checkbox',
            columns: 1,
            labelWidth: 300,
            width: 600,
            name: 'schRoomAccessGroup',
            reference: 'schRoomAccessGroup',
            fieldLabel: l10n('admin-schroom-feature-enable-disable-label'),
            inputValue: 'enabled',
            uncheckedValue: 'disabled',
            bind: {
                disabled: '{disableSchRoom}'
            }

        }, {
            xtype: 'label',
            html: '<div style="text-align:center;font-weight:bold;">' + l10n('admin-schroom-feature-disabled-label') + '</div>',
            width: '100%',
            flex: 1,
            bind: {
                hidden: '{showLabel}'
            }
        }]
    }],

    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        formBind: true,
        bind: {
            disabled: '{disableSchRoom}'
        },
        listeners: {
            click: 'onClickScheduledRoomSave'
        }
    },{
        text: l10n('cancel'),
         listeners: {
            click: 'onClickScheduledRoomCancel'
        }
    }
    ]
});