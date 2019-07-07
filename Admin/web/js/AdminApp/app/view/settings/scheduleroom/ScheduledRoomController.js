Ext.define('AdminApp.view.settings.scheduleroom.ScheduledRoomController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.ScheduledRoomController',

    control : {
    },

    getScheduleRoomData : function() {
        var me = this,
            viewModel = me.getViewModel(),
            rg = me.lookupReference('schRoomAccessGroup');

        /* Ext.Ajax.request({
         url : 'getmanagescheduledroom.ajax',
         method : 'GET',
         success : function(res) {
         var result = res.responseXML;
         rg.setValue({
         'schRoomAccessGroup' : Ext.DomQuery.select('schRoomDisabledTenantLevel') == "true" ? "disabled" : "enabled"
         });
         viewModel.set('schRoomDisabledTenantLevel', Ext.DomQuery.selectValue('schRoomDisabledTenantLevel', result) == "false" ? false : true);
         viewModel.set('disableSchRoom', Ext.DomQuery.selectValue('schRoomEnabledSystemLevel', result) == "false" ? true : false);//disabled false
         viewModel.set('showLabel', Ext.DomQuery.selectValue('schRoomEnabledSystemLevel', result) == "true" ? true : false);//hidden true
         viewModel.set('schRoomEnabledSystemLevel', Ext.DomQuery.selectValue('schRoomEnabledSystemLevel') == "true" ? true : false);
         }
         });
         */
        viewModel.getStore('scheduleStore').load({
            callback : function(res) {
                if (res.length) {
                    var rec = res[0];
                    rg.setValue(rec.get('schRoomDisabledTenantLevel') == true ? "disabled" : "enabled"
                    );
                    viewModel.set('schRoomDisabledTenantLevel', rec.get('schRoomDisabledTenantLevel'));
                    viewModel.set('disableSchRoom', !rec.get('schRoomEnabledSystemLevel'));
                    //disabled false
                    viewModel.set('showLabel', rec.get('schRoomEnabledSystemLevel'));
                    //hidden true
                    viewModel.set('schRoomEnabledSystemLevel', rec.get('schRoomEnabledSystemLevel'));
                    viewModel.set('prevScheduledRoom', rec);
                }
            }
        });
    },

    /***
     * @function onChangeSchedAccessGroup
     * @param {Object} rg
     * @param {Object} newVal
     */
    onChangeSchedAccessGroup : function(rg, newVal) {
        var me = this,
            form = rg.up('form'),
            rec = rg.rec,
            prefixField = form.child('textfield'),
            saveBtn = form.down('button[text=Save]');

        if (newVal["schRoomAccessGroup"] == "disabled") {
            prefixField.setHidden(true);
            prefixField.disable();
            saveBtn.enable();
        } else {
            prefixField.enable();
            prefixField.setHidden(false);
            prefixField.setValue(rec.get('ScheduledRoomPrefix'));
            if (!form.isValid()) {
                saveBtn.disable();
            }
        }
    },
    onClickScheduledRoomCancel : function(btn) {
        this.getScheduleRoomData();
    },
    onClickScheduledRoomSave : function(btn) {
        var me = this,
            form = btn.up('form'),
            rec = me.getViewModel().get('prevScheduledRoom'),
            params = form.getValues(),
            disableStr = l10n('admin-disable-schedule-room-feature');
        prevState = {};

        prevState["schRoomDisabledTenantLevel"] = rec.get('schRoomDisabledTenantLevel') ? "enabled" : "disabled";

        if (prevState["ScheduledRoomEnabled"] == params["schRoomAccessGroup"]) {
            Ext.Msg.alert(l10n('label-alert'), l10n('super-schroom-feature-no-changes-made'));
            return;
        }
        if (params["schRoomAccessGroup"] == "disabled") {
            Ext.Msg.confirm(l10n('confirmation'), disableStr, function(response) {
                if (response == "yes") {
                    Ext.Ajax.request({
                        url : 'savescheduledroom.ajax',
                        params : params,
                        method : 'POST',
                        success : function(res) {
                            var xmlResponse = res.responseXML;
                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                            if (success == "true") {
                                Ext.Msg.alert(l10n('message'), l10n('saved'));
                            }
                        }
                    });
                }
            });
            return;
        }
        Ext.Msg.confirm(l10n('confirmation'), l10n('super-enable-scheduled-room-feature'), function(response) {
            if (response == "yes") {
		        Ext.Ajax.request({
		            url : 'savescheduledroom.ajax',
		            params : params,
		            method : 'POST',
		            success : function(res) {
		                var xmlResponse = res.responseXML;
		                var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
		                if (success == "true") {
		                	Ext.Msg.alert(l10n('message'), l10n('saved'));
		                }
		            }
		        });
            }
        });

    },

    onChangeNumberField : function(tf, newValue) {
        var me = this,
            form = tf.up('form');
        if (newValue) {
            form.down('button[text=Save]').enable();
        } else {
            form.down('button[text=Save]').disable();
        }
    }
});
