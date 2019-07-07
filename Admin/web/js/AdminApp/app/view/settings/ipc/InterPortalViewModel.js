/**
 * @class InterPortalViewModel
 */
Ext.define('AdminApp.view.settings.ipc.InterPortalViewModel',{
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.InterPortalViewModel',
    
    stores : {
        ipclistgridStore : {
            fields : [{
                name : 'tenantId',
                mapping : 'tenantId',
                convert : function(val) {
                    if (val)
                        return val.trim();
                }
            },{
                name : 'ipcId',
                mapping : 'ipcId',
                convert : function(val) {
                    if (val)
                        return val.trim();
                }
            },{
            	name:'isIpcSuperManaged',
                mapping : 'isIpcSuperManaged',
                type : 'boolean'
            }, {
                name : 'HostName',
                convert : function(val) {
                    if (val)
                        return val.trim();
                }
            }, {
                name : 'IpcWhiteListId',
                convert : function(val) {
                    if (val)
                        return val.trim();
                }
            },{
                name : 'WhiteList',
                convert : function(val) {
                    if (val)
                        return val.trim();
                }
            }],
            proxy : {
                type : 'ajax',
                url:'ipcsettings.ajax',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'tenantIpcDetail',
                    rootProperty : 'dataset'
                }
            },
            listeners:{
            	load: 'ipcliststoreload'
            }
        }
    },
    
    data :{
        allowblockgroup : 'block',
        ipcId:'',
        tenantID:'',
        isIpcSuperManaged:'',
        tenantIpcDetail:[]
    }
    
});
