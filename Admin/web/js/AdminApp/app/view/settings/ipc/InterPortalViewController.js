/**
 * @controller InterPortalViewController
 */
Ext.define('AdminApp.view.settings.ipc.InterPortalViewController',{
    extend : 'Ext.app.ViewController',
    alias : 'controller.InterPortalViewController',
    
    /***
     * @function getInterPortalData
     * @desc load data for the interportal grid.
     */
    
    getIPCData : function() {
    	var me = this,
            viewmodel = me.getViewModel(),
            ipclistgridStore = viewmodel.getStore("ipclistgridStore");
            
            ipclistgridStore.removeAll();
            ipclistgridStore.load({
                callback :function(recs) {
                    var rec =recs[0];
                     viewmodel.set("ipcId", rec.get('ipcId').trim());
                    viewmodel.set("tenantId", rec.get('tenantId').trim());
                    if(rec.get('isIpcSuperManaged'))
                    viewmodel.set("isIpcSuperManaged", rec.get('isIpcSuperManaged'));
                    if(rec.get('tenantIpcDetail'))
                    viewmodel.set("tenantIpcDetail", rec.get('tenantIpcDetail'));
                    if(!rec.get('HostName')) {
                    ipclistgridStore.remove(ipclistgridStore.getAt(0));
                }
                }
            });
 			   
    },
    onChangeIPCControlMode : function(cg, newVal) {
    	if(newVal.allowBlockGroup == 'block'){
    		this.lookupReference("ipcDomainGrid").setTitle(l10n('ipc-grid-title-block-label'));
    	}else{
    		this.lookupReference("ipcDomainGrid").setTitle(l10n('ipc-grid-title-allow-label'));
    	}
    	this.lookupReference('ipcSaveBtn').enable();
    },
    
    /***
     * @function  onClickDomainGridAdd
     * @param {Object} grid
     */
    onClickDomainGridAdd : function(btn) {
        var me = this,
            grid = btn.up('grid'),
            rec = btn.rec;
            
        Ext.Msg.prompt(l10n('confirmation'), l10n('ipc-msgbox-prompt-message'), function(res,text) {
            if(res == "ok") {
                var store = grid.getStore(),
                flag = false,
                regex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\")).(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
                if(text.match(regex)) {
                    store.each(function(res) {
                        if(res.get('HostName') == text) {
                            flag = true;
                            return;
                        }
                    });
                    if(!flag) {
                        store.add({
                            'IpcWhiteListId' :'',
                            'HostName' : text,
                            'whiteList' :''
                        });
                        me.lookupReference('ipcSaveBtn').enable();
                    } else {
                        Ext.MessageBox.alert(l10n('error'), l10n('ipc-msgbox-duplicate-alert'));
                    }
                }else {
                	 Ext.Msg.alert(l10n('error'), l10n('valid-domain-address'));
                }
            }
        });
    },
    
    /***
     * @function onClickDomainGridRemove
     * @param {object} btn
     */
    onClickDomainGridRemove : function(btn) {
        var me = this,
            grid = btn.up('grid'),
            gridStore = grid.getStore(),
            form = grid.up('form'),
            selected = grid.getSelection(),
            hiddenField = form.down('hiddenfield'),
            selectedId;
            
        if(selected.length) {
        	Ext.Msg.confirm(l10n('ipc-msgbox-delete-confirm-title'), l10n('ipc-msgbox-delete-confirm-messg'), function(btn) {
        		if(btn == "yes") {
		            selectedId = selected[0].get('IpcWhiteListId');
		            if(hiddenField.getValue()) {
		                hiddenField.setValue(hiddenField.getValue() + ',' + selectedId);
		            } else {
		                hiddenField.setValue(selectedId);
		            }
		            me.lookupReference('ipcSaveBtn').enable();
		            gridStore.remove(selected[0]);
        		}
        	});
        } else {
        	Ext.Msg.alert(l10n('ipc-msgbox-delete-msg'), l10n('ipc-msgbox-delete-msg'));
        }
    },
    
    /***
     * @function onClickIPCSave
     * @param {Object} btn
     */
    onClickIPCSave : function(btn) {
        var me = this,
            form = btn.up('form'),
            params = form.getValues(),
            grid = form.down('grid'),
            hiddenField = addedDomainsList = [],
            deletedDomainIds = [];
            
        grid.getStore().each(function(rec) {
            if(rec.get("IpcWhiteListId") == "") {
                addedDomainsList.push(Ext.String.htmlEncode(rec.get('HostName')));
            }
            deletedDomainIds.push(Ext.String.htmlEncode(rec.get("IpcWhiteListId")));
        });
        params["addedDomains"] = addedDomainsList.join().trim();
        params["ipcId"] = me.getViewModel().get("ipcId");
        params["tenantId"] = me.getViewModel().get("tenantId");
        params["deletedIds"] = deletedDomainIds.join().toString().trim();
        Ext.Ajax.request({
            url :'saveadminipcsettings.ajax',
            method :'POST',
            params : params,
            success : function(res)  {
                var xmlResponse = res.responseXML,
                    success = Ext.DomQuery.selectValue('message @success',xmlResponse);
                if(success == "true") {
                    Ext.Msg.alert(l10n('message'),l10n('saved'));
                } else {
                    Ext.Msg.alert(Ext.DomQuery.selectValue('id', xmlResponse), Ext.DomQuery.selectValue('msg', xmlResponse));
                }
                me.lookupReference('ipcSaveBtn').disable();
                me.getIPCData();
            }
        });
    },
    ipcliststoreload: function( store, records, successful){
    	if(records[0]) {
            if (records[0].get("WhiteList") == 1) {
				this.lookupReference("allowedList").setValue(true);
				this.lookupReference("blockedList").setValue(false);
			} else {
				this.lookupReference("blockedList").setValue(true);
				this.lookupReference("allowedList").setValue(false);
			}
		}else {
			this.lookupReference("blockedList").setValue(true);
		}
        //this.getViewModel().getStore("ipclistgridStore").removeAll();
		this.lookupReference('ipcSaveBtn').disable();
    }
    
});
