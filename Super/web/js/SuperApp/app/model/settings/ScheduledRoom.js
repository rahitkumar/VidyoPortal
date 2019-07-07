Ext.define('SuperApp.model.settings.ScheduledRoom',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'ScheduledRoomEnabled',
        type :'boolean'
    },{
        name :'publicRoomEnabledGlobal',
        type :'boolean'
    },
     {
        name :'publicRoomMaxRoomNoPerUser'
    },
      {
        name :'publicRoomMinNoExt'
    },

    {
        name :'ScheduledRoomPrefix'
    },{
        name :'ScheduledRoomHelp'
    },{
        name :'ScheduleRoomEnabledLabel'
    },{
        name :'ScheduleRoomPrefixLabel'
    },{
        name :'ScheduleRoomNoChangeSubmitDesc'
    },{
        name :'ScheduleRoomConfirmMsgBoxDesc'
    },]
    
});
