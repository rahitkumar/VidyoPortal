/**
 * @author ScheduledRoomViewModel
 */
Ext.define('AdminApp.view.settings.scheduleroom.ScheduledRoomViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.ScheduledRoomViewModel',

    data : {
        schRoomEnabledSystemLevel : '',
        disableSchRoom : '',
        prevScheduledRoom : ''
    },

    stores : {
        scheduleStore : {
            fields : [{
                name : 'schRoomDisabledTenantLevel',
                type : 'boolean'
            }, {
                name : 'schRoomEnabledSystemLevel',
                type : 'boolean'
            }],
            proxy : {
                type : 'ajax',
                url : 'getmanagescheduledroom.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        }
    }
});
