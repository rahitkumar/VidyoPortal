// Store for the Combo box in Main Page which displays all Types of Components
Ext.define('SuperApp.store.components.ComponentFilter', {
    extend : 'Ext.data.JsonStore',
    fields : ['id', 'description', 'name'],
    proxy : {
        type : 'ajax',
        actionMethods : {
            create : "POST",
            read : "GET",
            update : "POST",
            destroy : "POST"
        },
        url : "getcomponenttypes.ajax",
        reader : {
            type : 'json',
            rootProperty : 'items'
        }
    },
    autoLoad : true,
    listeners : {
        load : function(store, records, successful, eOpts) {
            if (successful) {
                var allRecord = Ext.create('Ext.data.Model', {
                    fields : ['id', 'description', 'name'],
                    id : '0',
                    description : '',
                    name : 'All'
                });
                store.insert(0, allRecord);
            }
        }
    }
}); 