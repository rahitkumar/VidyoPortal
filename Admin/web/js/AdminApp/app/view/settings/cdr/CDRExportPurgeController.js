Ext.define('AdminApp.view.settings.cdr.CDRExportPurgeController', {
	extend : 'Ext.app.ViewController',
	alias : 'controller.CDRExportPurgeController',
    invalidDate: function(){
        this.lookupReference("export").disable();
    },
    validDate: function(){
        if (this.lookupReference('enddate').isValid()) {
      	  this.lookupReference("export").enable();
        }
    },
	getCDRParamValue: function(obj){
		var strReturn = '';
		try {
			strReturn = obj.getRawValue().trim();
		}
		catch (err) {
			strReturn = '';
		}
		return strReturn;
	},
	getExportCdrUrl:function(){
		var strReturn = 'securedmaint/cdrexport.ajax?';
        strReturn += 'oneall=' + this.lookupReference('oneallvalue').getValue();
        if (this.lookupReference('oneallvalue').getValue() == 'one') {
            strReturn += '&tenantName='+this.getViewModel().get("tenantName");
        }
        strReturn += '&dateperiod=' + this.lookupReference('dateperiodvalue').getValue();
        if (this.lookupReference('dateperiodvalue').getValue() == 'range') {
            strReturn += '&startdate=' + this.getCDRParamValue(this.lookupReference('startdate'));
            strReturn += '&enddate=' + this.getCDRParamValue(this.lookupReference('enddate'));
        }
        return strReturn;
	},
	exportBtnHandler: function(btn){
    	var scope = this,
    	    form = btn.up('form');
        Ext.Ajax.request({
            url: 'securedmaint/cdrexportcount.ajax',
            method: 'GET',
            waitMsg: l10n('please-wait'),
            params : form.getForm().getValues(),
            success: function (result, request) {
                var errors = '';
                var xml = result.responseXML;
                if (xml != null) {
                    var msgNode = xml.getElementsByTagName("message");
                    if (msgNode != null) {
                         message = msgNode[0];
                         var success = message.getAttribute("success");
                         if(!success){
                        	 Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
                         }
                    }
                }
               try {
                   Ext.destroy(Ext.get('downloadIframe'));
               } catch(e) {}
               Ext.DomHelper.append(document.body, {
                  tag: 'iframe',
                  id:'downloadIframe',
                  frameBorder: 0,
                  width: 0,
                  height: 0,
                  css: 'display:none; visibility:hidden; height:0px;',
                  src: scope.getExportCdrUrl()
               });
            },
            failure:function(form, action) {
            	Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
            }
        });
    },
    cdrExportPurgeRender: function(){
    	var scope = this;
    	Ext.Ajax.request({
    		url: 'getCDRAccessParams.ajax',
    		success: function(res){
    		   var xmlResponse = res.responseXML;
			   var CDRFormat = Ext.DomQuery.selectNode('CDRFormat', xmlResponse);
			   var tenantName = Ext.DomQuery.selectNode('tenantName', xmlResponse);
			   if(CDRFormat.textContent == '0'){
				   scope.getViewModel().set("CDRFormat",true);
			   }else{
				   scope.getViewModel().set("CDRFormat",false);
			   }
			   scope.getViewModel().set("tenantName",tenantName.textContent);
    		},
    		failure: function(){
    			Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
    		}
    	});
    	
    },
    cancelBtnHandler: function(){
      this.getExportCdrUrl();
    }
    
});
	