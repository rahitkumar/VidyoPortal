/**
 * @class Roles
 */
Ext.define('AdminApp.model.settings.Roles', {
    extend : 'Ext.data.Model',
    
    fields : [{
        name : 'roleID'
    }, {
        name : 'roleName'
    }, {
        name : 'roleDescription'
    }]
});
