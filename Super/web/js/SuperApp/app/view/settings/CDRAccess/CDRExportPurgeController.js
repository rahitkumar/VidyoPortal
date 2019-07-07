Ext.define('SuperApp.view.settings.CDRAccess.CDRExportPurgeController', {
    extend : 'Ext.app.ViewController',
    alias : 'controller.CDRExportPurgeController',
    oneTenant : function(obj, ev, eo) {
        if (obj.inputValue == 'one' && obj.rawValue == true) {
            this.lookupReference("exportPurgeCombo").enable();
            this.lookupReference('oneallvalue').setValue('one');
    }
    },
    allTenants : function(obj, ev, eo) {
        if (obj.inputValue == 'all' && obj.rawValue == true) {
            this.lookupReference("exportPurgeCombo").disable();
            this.lookupReference('oneallvalue').setValue('all');
        }
    },
    invalidDate : function() {
        this.lookupReference("export").disable();
        this.lookupReference("purge").disable();
    },
    validDate : function() {
        if (this.lookupReference('enddate').isValid()) {
            this.lookupReference("export").enable();
            this.lookupReference("purge").enable();
        }
    },
    cdrExpPurgeComboLoad : function(store, records, successful, eOpts) {
        this.lookupReference("exportPurgeCombo").setValue(records[0].data.tenantName);
    },
    getCDRParamValue : function(obj) {
        var strReturn = '';
        try {
            strReturn = obj.getRawValue().trim();
        } catch (err) {
            strReturn = '';
        }
        return strReturn;
    },
    getExportCdrUrl : function() {
        var strReturn = "securedmaint/maintenance_cdr_export.ajax?";
        strReturn += 'oneall=' + this.lookupReference('oneallvalue').getValue();
        if (this.lookupReference('oneallvalue').getValue() == 'one') {
            strReturn += '&tenantName=' + this.getCDRParamValue(this.lookupReference("exportPurgeCombo"));
        }
        strReturn += '&dateperiod=' + this.lookupReference('dateperiodvalue').getValue();
        if (this.lookupReference('dateperiodvalue').getValue() == 'range') {
            strReturn += '&startdate=' + this.getCDRParamValue(this.lookupReference('startdate'));
            strReturn += '&enddate=' + this.getCDRParamValue(this.lookupReference('enddate'));
        }
        return strReturn;
    },
    getExportCdrUrlCount : function() {
        var strReturn = "securedmaint/cdrexportcount.ajax?";
        strReturn += 'oneall=' + this.lookupReference('oneallvalue').getValue();
        if (this.lookupReference('oneallvalue').getValue() == 'one') {
            strReturn += '&tenantName=' + this.getCDRParamValue(this.lookupReference("exportPurgeCombo"));
        }
        strReturn += '&dateperiod=' + this.lookupReference('dateperiodvalue').getValue();
        if (this.lookupReference('dateperiodvalue').getValue() == 'range') {
            strReturn += '&startdate=' + this.getCDRParamValue(this.lookupReference('startdate'));
            strReturn += '&enddate=' + this.getCDRParamValue(this.lookupReference('enddate'));
        }
        return strReturn;
    },
    exportBtnHandler : function() {
        var scope = this;
        Ext.Ajax.request({
            url : this.getExportCdrUrlCount(),
            method: 'GET',
            waitMsg : l10n('please-wait'),
            success : function(result, request) {

                var errors = '';
                var xml = result.responseXML;
                if (xml != null) {
                    var msgNode = xml.getElementsByTagName("message");
                    if (msgNode != null) {
                        message = msgNode[0];
                        var success = message.getAttribute("success");
                        if (!success) {
                            Ext.Msg.alert(l10n('error'), l10n('export-failed'));
                        }
                    }
                }

                scope.lookupReference('export').disable();
                try {
                    Ext.destroy(Ext.get('downloadIframe'));
                } catch(e) {
                }

                Ext.DomHelper.append(document.body, {
                    tag : 'iframe',
                    id : 'downloadIframe',
                    frameBorder : 0,
                    width : 0,
                    height : 0,
                    css : 'display:none; visibility:hidden; height:0px;',
                    src : scope.getExportCdrUrl()
                });
                setTimeout(function() {
                    scope.lookupReference('export').enable();
                }, 10000);
            },
            failure : function(form, action) {
                Ext.Msg.alert(localizedSettingPage['error'], localizedSettingPage['exportfailed']);
            }
        });
    },
    doCDRPurge2 : function(btn) {
        var scope = this;
        console.log(scope);
        if (btn == "yes") {

            scope.lookupReference("cdrExportPurgeForm").submit({
                url : 'securedmaint/maintenance_cdr_purge.ajax',
                success : function(form, action) {

                    var errors = '';
                    if ((action.result != null) && (action.result.errors != null)) {
                        for (var i = 0; i < action.result.errors.length; i++) {
                            errors += action.result.errors[i].msg + '<br>';
                        }
                    }
                    if (errors != '') {
                        Ext.Msg.alert("", errors, function() {
                            scope.getView().close();
                        });
                    } else {
                        Ext.Msg.alert(l10n('success'), l10n('cdr-records-has-been-deleted'), function() {
                            scope.getView().close();
                        });
                    }
                },
                failure : function(form, action) {
                    Ext.Msg.alert(l10n('error'), l10n('purge-failed'));
                }
            });
        }
    },
    purgeBtnHandler : function() {
        var me = this;
        var dateperiod = this.lookupReference("dateperiodvalue").getValue();
        if (dateperiod == 'range') {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('are-you-sure-you-want-to-permanently-delete-all-cdr-records-between') + this.lookupReference('startdate').getRawValue() + l10n('and') + this.lookupReference('enddate').getRawValue(), this.doCDRPurge2, me);
        } else {
            Ext.MessageBox.confirm(l10n('confirmation'), l10n('all-cdr-records-msgbox-delete-confirm-messg'), this.doCDRPurge2, me);
        }
    },
    datefieldRadio : function(cb, checked) {

        if (checked) {
            this.lookupReference('startdate').enable();
            this.lookupReference('enddate').enable();
            this.lookupReference('dateperiodvalue').setValue('range');
        }

    }
});
