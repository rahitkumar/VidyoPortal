Ext.define('SuperApp.store.settings.GlobalFeatureSettings',{
    extend :'Ext.data.Store',
    
    requires :['SuperApp.model.settings.GlobalFeatureSettings'],
    
    model :'SuperApp.model.settings.GlobalFeatureSettings',
    
    proxy :{
        type:'ajax',
        actionMethods :{
            read :"GET"
        },
        url :'misc.html',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    }
});
