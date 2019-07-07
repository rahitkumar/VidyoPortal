/**
 * @class GuestController
 */
Ext.define('AdminApp.view.settings.guest.GuestController',{
    extend : 'Ext.app.ViewController',
    alias : 'controller.GuestController',
    
    getGuestSettingsData : function() {
        var me = this,
            viewModel = me.getViewModel();
            
        viewModel.getStore('groupStore').load();
        viewModel.getStore('proxyStore').load();
        viewModel.getStore('locationTagStore').load();
        
        me.getGuestSettingsComboData();
    },
    
    getGuestSettingsComboData : function() {
        var me = this,
            groupCombo = me.lookupReference('groupguest'),
            proxyCombo = me.lookupReference('proxyguest'),
            locationCombo = me.lookupReference('locationguest');
        
        Ext.Ajax.request({
            url : 'guestconf.ajax',
            method : 'GET',
            success : function(res) {
                var result = res.responseXML;
                groupCombo.reset();
                proxyCombo.reset();
                locationCombo.reset();
                groupCombo.setValue(Ext.DomQuery.selectValue('groupID', result));
                proxyCombo.setValue(Ext.DomQuery.selectValue('proxyID', result));
                locationCombo.setValue(Ext.DomQuery.selectValue('locationID', result));
            }, 
            failure : function(res) {
                Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
            }
        });
    },
    
    /***
     * @function onClickSaveGuestView
     * @param {Object} btn
     */
    onClickSaveGuestView : function(btn) {
        var me = this,
            form = btn.up('form');
            
        if(form && form.getForm().isValid()) {
            Ext.Ajax.request({
                url : 'saveguestconf.ajax',
                method : 'POST',
                params : form.getValues(),
                scope : me,
                success : function(res) {
                    var result = res.responseXML,
                        success = Ext.DomQuery.selectValue('message @success', result);
                    
                    if(success == "true") {
                        Ext.Msg.alert(l10n('message'),l10n('saved'));
                        me.getGuestSettingsComboData();
                    }
                }
            });
        }
    }
});