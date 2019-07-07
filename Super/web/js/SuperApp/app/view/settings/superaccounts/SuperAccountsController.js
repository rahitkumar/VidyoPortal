/**
 * @author SuperAccountsController
 */
Ext.define('SuperApp.view.settings.superaccounts.SuperAccountsController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.SuperAccountsController',

    getSuperAccountsData : function() {
        var me = this,
            viewModel = me.getViewModel(),
            enableStatus = me.lookupReference('enableStatus'),
            store = viewModel.getStore('superAccountsStore');
            
        me.view.getLayout().setActiveItem(0);
        
        store.load({
            params : {
                enable : enableStatus.getValue()
            }
        });
    },

    onChangeEnabledSA : function(cg, newValue) {
        var me = this,
            view = me.view,
            gridStore = me.getViewModel().getStore('superAccountsStore');

        gridStore.clearFilter();
        if (newValue) {
            gridStore.filterBy(function(rec) {
                if (rec.get('enable') == 'Yes') {
                    return true;
                }
            });
        } else {
            gridStore.filterBy(function(rec) {
                if (rec.get('enable') != 'Yes') {
                    return true;
                }
            });
        }
    },

    manageSuperAccountsFilter : function(field) {
        var val = field.getValue(),
        store = this.view.getViewModel().getStore("superAccountsStore");
        if (val.length === 0) {
            store.clearFilter();
        } else {
            store.filter(field.getName(), val);
        }
    },

    onCellClickSuperAccountsGrid : function(view, cell, cellIndex, record,row, rowIndex, e) {
    	if(cellIndex != 1) {
    		return;
    	}
        var me = this;
        var memberID = record.get("memberID");
        var editSuperUserWin = Ext.create('SuperApp.view.settings.superaccounts.EditSuperAcct', {
            floatParent : this.view
        });
        var editForm = editSuperUserWin.down('form');        
        editForm.load({
            url : 'superaccount.ajax',
            params : {
                memberID : memberID
            },
            success : function(form, action) {
            	editForm.down('fieldset').show();
                //set checkbox value after loading recording.
            	editForm.down('checkbox[name=enable]').setValue(Ext.DomQuery.selectValue('enable', action.response.responseXML) == "1" ? 'on' : 'off');
            },
            failure : function(form, action) {
                Ext.Msg.alert(l10n('error'), l10n('failure'));
            }
        });
        editSuperUserWin.show();
    },

    onClickGridAddSA : function() {
        var addUsersWin = Ext.create('SuperApp.view.settings.superaccounts.AddSuperAcct', {
            floatParent : this.view
        });
        addUsersWin.show();
    },

    onClickCancelAddFromSA : function() {
    	//some reason previous window existing.quick fix using form
        var window = this.lookupReference('addSuperUserWin'),
         form = this.lookupReference('addSuperAccountForm');
        if(!form) {
           window = this.lookupReference('editSuperUserWin');
        }
      
        if(window) {
        	window.close();
        }
    },

    onClickGridDeleteSA : function() {
        var me = this,
            grid = me.lookupReference('superaccountsgrid'),
            selectedRec = grid.getSelection();
        if (selectedRec.length) {
            Ext.Msg.confirm(l10n('confirmation'), l10n('do-you-want-to-delete-selected-super-account'), function(res) {
                if (res == "yes") {
                    Ext.Array.each(selectedRec, function(rec) {
                        Ext.Ajax.request({
                            url : 'deletesuper.ajax',
                            params : {
                                memberID : rec.get('memberID')
                            },
                            success : function(response) {
                            	if (Ext.DomQuery.selectValue('message/@success', response.responseXML).trim() == "true") {
                            		var viewModel = me.getViewModel(),
                                    store = viewModel.getStore('superAccountsStore');
	                                //reload the data after deleting the record from the grid.
	                                store.load();
                            	} else {
                            		Ext.Msg.alert(l10n('error'), l10n('delete-failed'));
                            	}
                            }
                        });
                    });
                }
            });

        } else {
            Ext.Msg.alert(l10n('error'), l10n('please-select-at-least-one-super-account-to-delete'));
        }
    },

    onSaveSuperAccount : function() {
        var me = this;
        form = me.lookupReference('addSuperAccountForm');
        if(!form) {
        	form = me.lookupReference('editSuperAccountForm');
        }
        values = form.getForm().getValues();
        var uName = values['username'];
        if (form && form.getForm().isValid()) {
            Ext.Ajax.request({
                url : 'savesuperaccount.ajax',
                params : values,
                method : 'POST',
                success : function(response) {
                    if (Ext.DomQuery.selectValue('message/@success', response.responseXML).trim() == "true") {
                        var gridStore = me.getViewModel().getStore('superAccountsStore');
                        me.getSuperAccountsData();
                        me.onClickCancelAddFromSA();
                    } else {
                        var errorMsg= Ext.DomQuery.selectValue('msg', response.responseXML);
                        if(!(typeof errorMsg==="undefined") && errorMsg== 'invalidusername'){
                        	Ext.Msg.alert(l10n("message"), '"' + Ext.util.Format.htmlEncode(uName) + '" ' + l10n('invalid-username-match-js-message'))
                        } else {
                        	Ext.Msg.alert(l10n("message"), errorMsg);
                        }
                    }
                },
                failure : function(response) {
                    Ext.Msg.alert(l10n('error'), l10n('failure'));
                }
            });
        }
    },
    changePassCollapse : function(p) {
        Ext.each(p.items, function(i) {
            if ( i instanceof Ext.form.Field) {
                i.reset();
                i.disable();
            }
        }, this);
        this.lookupReference("savesuperaccounts").setDisabled(false);
    },
    changePassExpand : function(p) {
        p.items.each(function(i) {
            if ( i instanceof Ext.form.Field) {
                i.enable();
            }
        }, this);
        this.lookupReference("savesuperaccounts").setDisabled(true);
    },
    changePassRender : function(p) {
        p.items.each(function(i) {
            if ( i instanceof Ext.form.Field) {
                i.disable();
            }
        }, this);
        this.lookupReference("savesuperaccounts").setDisabled(false);
    },

    remoteValidateUserName : function(textfield, newValue) {
        var scope = this;
        var readonly = scope.getViewModel().get("usernameReadOnly");
        if (readonly) {
            textfield.textValid = true;
            textfield.textInvalid = '';
            textfield.validate();
        } else {
            Ext.Ajax.request({
                url : 'checksupername.ajax',
                method : 'GET',
                params : {
                    username : newValue
                },
                scope : textfield,
                success : function(response) {
                    var roomExtExists = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
                    if (roomExtExists == 'true') {
                    	textfield.validFlag = l10n('username-not-available');
                    	textfield.validate();
                    } else {
                    	textfield.validFlag = true;
                    	textfield.validate();
                    }
                },
                failure : function(response) {
                    Ext.Msg.alert(l10n('error'), l10n('failure'));
                }
            });
        }

    },
    cancelAddSuperAcct : function() {
    	this.lookupReference("addSuperUserWin").close();
    },
    cancelEditSuperAcct : function() {
    	this.lookupReference("editSuperUserWin").close();
    }
});
