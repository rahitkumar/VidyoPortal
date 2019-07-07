/**
 * @class LocationTagsController
 */
Ext.define('AdminApp.view.settings.locationtag.LocationTagsController',{
    extend : 'Ext.app.ViewController',
    alias : 'controller.LocationTagsController',
    
    /***
     * @function getLocationTagData
     */
    getLocationTagData : function() {
        var me = this,
            viewModel = me.getViewModel();
        
        viewModel.getStore('locationtagStore').load({
            callback : function(recs) {
                me.manageLocatonTag();
            }
        });
        
    },
    
    /***
     * @function manageLocatonTag
     */
    manageLocatonTag : function() {
        var me = this,
            locationcombo = me.lookupReference('locationcombo');
        
        Ext.Ajax.request({
            url : 'managelocationtag.ajax',
            method : 'GET',
            success : function(res) {
                var result = res.responseXML;
                locationcombo.setValue(Ext.DomQuery.selectValue('locationID', result));
            }
        });
    },
    
    /***
     * @function onClickAdvanceLocationTag
     */
    onClickAdvanceLocationTag : function() {
        var me = this,
            view = me.view,
            viewModel = me.getViewModel();
            
        view.getLayout().setActiveItem(1);
        //view.up('panel').setTitle('Assign Location Tag to Groups');
        viewModel.getStore('locationtagStore').reload();
        viewModel.getStore('groupStore').load();
        view.down('checkbox').setValue(false);
    },
    
    /***
     * @function onClickAdvanceCancel
     */
    onClickAdvanceCancel : function() {
        var me = this,
            view = me.view;
            
        view.getLayout().setActiveItem(0);
       // view.up('panel').setTitle('Location Tags');
    }, 
    
    /***
     * @function onChangeSelectAllGroup
     * @param {Object} checkbox
     * @param {Object} val
     */
    onChangeSelectAllGroup : function(checkbox, val) {
        var me = this,
            grid = me.lookupReference('availgroupgrid');
        
        if(grid && val) {
            grid.getSelectionModel().selectAll();
            return;
        }
        grid.getSelectionModel().deselectAll();
    },
    
    /***
     * 
     * @param {Object} btn
     */
    onClickAssignLocationGroup : function(btn) {
        var me = this,
            locationgrid  = me.lookupReference('locationgrid'),
            groupgrid = me.lookupReference('availgroupgrid');
            
        Ext.Msg.confirm(l10n('confirmation'),l10n('you-are-about-to-assign-selected-location-tag-to-all-members-of-selected-group-s-do-you-want-to-continue'), function(res) {
            if(res == "yes") {
                var locationSelection = locationgrid.getSelection(),
                    groupSelection = groupgrid.getSelection();
                    groupIDs = '';
                    
                Ext.Array.each(groupSelection, function(rec) {
                    if(groupIDs == '') {
                        groupIDs = rec.get('groupID');
                    } else {
                        groupIDs += ','+ rec.get('groupID');
                    }
                });
                Ext.Ajax.request({
                    url : 'assignlocationtogroups.ajax',
                    method : 'POST',
                    params : {
                        locationID : locationSelection[0].get('locationID'),
                        groupIDs : groupIDs
                    },
                    success : function(res) {
                        Ext.Msg.alert(l10n('message'),l10n('location-tag-successfully-assigned'));
                    }
                });
            }
        });
    },
    
    onClickSaveLocationTags : function(btn) {
    	var me = this,
    		form = btn.up('form');
    	
    	Ext.Ajax.request({
    		url : 'savedefaultlocationtagid.ajax',
    		params : form.getForm().getValues(),
    		success : function(res) {
    			var result = res.responseXML;
    			if(Ext.DomQuery.selectValue('message @success', result)) {
    				Ext.Msg.alert(l10n('message'),l10n('location-tag-saved'));
    				me.getLocationTagData();
    			}
    		},
    		failure : function(res) {
    			Ext.Msg.alert(l10n('faliure'),l10n('request-failed'));
    		}
    	})
    }
});
