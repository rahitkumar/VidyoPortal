Ext.define('AdminApp.view.roomSystems.RoomSystemViewController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.roomsystems-roomsystemview',

    manageVidyoRoomsGridLoad: function(field) {
        var val = field.getValue(),
        store = this.view.getViewModel().getStore("manageVidyoRoomsStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter(field.getItemId(), val);
        }
    },
    vidyoRoomsSortChange: function(ct, column, direction, eOpts) {
        this.getViewModel().set('vidyoRoomsSortDataIndex', column.dataIndex);
        this.getViewModel().set('vidyoRoomsSortDir', direction);
    },
    onVidyoRoomRender:function(){
    	//resetting values
    	if(this.lookupReference('displayNameFilter')) {
    		this.lookupReference('displayNameFilter').reset();
    	}
    	this.getViewModel().storeInfo.manageVidyoRoomsStore.load();
    },
    roomSystemsBeforeLoad : function(store) {
        var memberName = this.lookupReference('displayNameFilter') ? this.lookupReference('displayNameFilter').getValue() : '';
        var proxy = store.getProxy();
        proxy.setExtraParam('memberName', memberName);
    }
});