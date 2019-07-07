Ext.define('SuperApp.view.settings.ScheduledRoomController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.ScheduledRoomController',

    /***
     * @function onChangeSchedAccessGroup
     * @param {Object} rg
     * @param {Object} newVal
     */
    onChangeSchedAccessGroup : function(rg, newVal) {
        var me = this,
            form = me.view,
            rec = rg.rec,
            prefixField = form.down('textfield'),
            saveBtn = form.down('button[text='+l10n('save')+']');

        if (newVal == false) {
            //prefixField.setHidden(true);
            prefixField.disable();
            saveBtn.enable();
        } else {
            prefixField.enable();
            //prefixField.setHidden(false);
            prefixField.setValue(rec.get('ScheduledRoomPrefix'));
            if(prefixField.getValue()) {
                saveBtn.enable();
            } else {
                saveBtn.disable();
            }
        }
    },

    onClickScheduledRoomSave : function(btn) {
        var me = this,
            form = btn.up('form'),
          
            params = form.getValues();
          

        if (form.getForm().isValid()) {
            var scheduleRoomConfirmMsgBoxDescMsg = l10n('super-enable-scheduled-room-feature');
            Ext.Msg.confirm(l10n("confirmation"), scheduleRoomConfirmMsgBoxDescMsg, function(response) {
            if (response == "yes") {
                    Ext.Ajax.request({
                        url : 'savescheduledroom.ajax',
                        params : params,
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('success'), l10n('saved'));
                                Ext.getStore('scheduledroom').load({
                                    callback : function(rec) {
                                        me.view.up('settingsMainView').getViewModel().set('prevScheduledRoom', rec[0]);
                                        form.loadRecord(rec[0]);
                                    }
                                });
                            } else {
                                Ext.Msg.alert(l10n('error'), Ext.DomQuery.selectValue('msg', xmlResponse));
                            }
                        },
                        failure : function() {
                            Ext.Msg.alert(l10n('error'), l10n('error'));
                        }
                    });
                }
            });
        } else {
            Ext.Msg.alert(l10n('error'), l10n('please-enter-room-prefix'));
        }

    },
	
  

});
