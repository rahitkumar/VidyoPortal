Ext.define('SuperApp.model.settings.SearchOptions',{
    extend :'Ext.data.Model',
     
    fields :[{
        name : 'showDisabledRoomsEnabled',
        mapping : 'searchOptions > showDisabledRoomsEnabled',
        type :'boolean'
    }]
});
