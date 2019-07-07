Ext.define("AdminApp.view.roomSystems.RoomSystemView", {
extend: "Ext.panel.Panel",

requires: [
    "AdminApp.view.roomSystems.RoomSystemViewController",
    "AdminApp.view.roomSystems.RoomSystemViewModel"
],

xtype: 'roomsystems-roomsystemview',
controller: 'roomsystems-roomsystemview',
viewModel: {
    type: "roomsystems-roomsystemview"
},
    border:false,
items: [

    {
    xtype: 'grid',
    reference: 'manageVidyoRoomsGrid',
    width: '100%',
    border: false,
    scrollable:true,
    height: 800,
    columns: [{
        text: l10n('display-name'),
        dataIndex: 'memberName',
        width: '40%'
    }, {
        text: l10n('ip-address'),
        dataIndex: 'ipAddress',
        width: '30%',
        renderer: function(value, p, record) {
            if (record.data.memberName != 'No results') {
                var url = "https://" + record.data.ipAddress;
                return '<a href="' + url + '" target="_blank">' + value + '</a>';
            } else {
                return l10n('no-results');
            }
        }
    }, {
        text: l10n('status'),
        dataIndex: 'status',
        width: '30%',
        minWidth: 60
    }],
    bind: {
        store: '{manageVidyoRoomsStore}'
    },
    loadMask: true,
      tbar : {
            xtype : 'toolbar',
            docked : 'top',
            reference : 'topToolbar',
            items : [{
                xtype : 'textfield',
                itemId: 'memberName',
                labelWidth : 90,
                width : 200,
                fieldLabel : l10n('display-name'),
                reference : 'displayNameFilter',
                labelAlign : 'left',
                enableKeyEvents : true,
                listeners : {
                    change : 'manageVidyoRoomsGridLoad'
                }
            }]
        },

    dockedItems: [{
        xtype: 'pagingtoolbar',

        bind: {
            store: '{manageVidyoRoomsStore}'
        },
        dock: 'bottom',
        displayInfo: true,
        displayMsg: l10n('displaying-rooms-0-1-of-2'),
        emptyMsg: l10n('no-rooms-to-display')
    }],
    listeners: {
        sortChange: 'vidyoRoomsSortChange'
    }
}],
listeners : {
        render : 'onVidyoRoomRender'
     }
});