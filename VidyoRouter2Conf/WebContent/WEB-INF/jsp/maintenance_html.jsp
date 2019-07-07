<%@ include file="header_html.jsp" %>

	<div id="maincontent">

		<div id="content-panel">&nbsp;</div>

    </div>

<div id="diagnostics-win" class="x-hidden">
    <div class="x-window-header"><spring:message htmlEscape="true" code="view.diagnostics.report"/></div>
    <div id="diagnostics-panel">
        <div id="diagnostics-report">

        </div>
    </div>
</div>

<script type='text/javascript' src='<c:url value="/js/FileUploadField.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/VTypes.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ComboLoadValue.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/DateFieldFix.js"/>'></script>

<script type="text/javascript">

Ext.apply(Ext.form.VTypes, {
	daterange: function(val, field) {
		var date = field.parseDate(val);
		if (!date) {
            return;
		}
		if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
			var start = Ext.getCmp(field.startDateField);
			start.setMaxValue(date);
			this.dateRangeMax = date;
		}
		else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
			var end = Ext.getCmp(field.endDateField);
			end.setMinValue(date);
			this.dateRangeMin = date;
		}
        return true;
	}
});

Ext.apply(Ext.form.VTypes, {
	vidyoEmail: function(val) {
		var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,})$/;
		return reg.test(val);
	},
	vidyoEmailText: ' <spring:message code="must.be.a.valid.e.mail.address"/>'
});

Ext.apply(Ext.form.VTypes, {
	password: function(val, field) {
		if (field.initialPassField) {
			var pwd = Ext.getCmp(field.initialPassField);
			return (val == pwd.getValue());
		}
		return true;
	},
	passwordText: '<spring:message code="keystore.password.does.notmatch"/>'
});

Ext.override(Ext.form.RadioGroup, {
	setValue: function(id, value){
		if (this.rendered) {
			if (arguments.length > 1) {
				var f = this.getBox(id);
				if(f){
					f.setValue(value);
					if (f.checked) {
						this.items.each(function(item){
							if (item !== f) {
								item.setValue(false);
							}
						}, this);
					}
				}
			}else{
				this.setValueForItem(id);
			}
		}else{
			this.values = arguments;
		}
		return this;
	},

	setValueForItem: function(val){
		this.items.each(function(item){
			if (item.inputValue == val) {
				item.setValue(true);
			}
		}, this);
	}
});

Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';

	var msg = function(title, msg, callback){
		msg_width(title, msg, callback, 300)
	};

	var msg_width = function(title, msg, callback, wid){
		Ext.Msg.show({
			title: title,
			msg: msg,
			minWidth: wid,
			modal: true,
			icon: Ext.Msg.INFO,
			buttons: Ext.Msg.OK,
			fn: callback
		});
	};

	var localmenu = new Ext.Panel({
		collapsible: false,
		autoHeight: true,
		border: false,
		frame: false,
		autoLoad: {url: '<c:url value="menu_content.html?settings=1&maintenance=1"/>'}
	});

	function formatEnable(value){
		return (value == 1);
	}

	function convertCheckboxToInt(o){
		return (o.checked) ? 1 : 0;
	}

	function convertBooleanToInt(o){
		return (o.getValue() == "true") ? 1: 0;
	}

/*
Logs Grid Panel
*/
	var sm = new Ext.grid.CheckboxSelectionModel();

	var cm = new Ext.grid.ColumnModel([
		sm,
		{ header: '<spring:message javaScriptEscape="true" code="file.name1"/>',  dataIndex: 'fileName', align:'left' , width: 200},
		{ header: '<spring:message javaScriptEscape="true" code="router.config.last.modified"/>',  dataIndex: 'lastModified', align:'left', sortable: false, width: 200 },
		{ header: '<spring:message javaScriptEscape="true" code="router.config.size"/>',  dataIndex: 'size', align:'left', sortable: false}
	]);

	var backupFile = Ext.data.Record.create([
		{name: 'fileName', type: 'string'},
		{name: 'lastModified', type: 'string'},
		{name: 'size', type: 'string'}
	]);

	var backupFileReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row',
		id: 'timestamp'
	}, backupFile);

	var ds = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({ method: 'POST', url: '<c:url value="securedmaint/maintenance_logs.ajax"/>', timeout: 120000 }),
		reader: backupFileReader,
		remoteSort: true
	});

	function rendertimestampMS(value){
		return new Date(+value);
	}
	
	var maintenancePanelSize = 400;

	var grid = new Ext.grid.GridPanel({
		header: false,
		store: ds,
		cm: cm,
		sm: sm,
		frame:true,
		loadMask: true,
		buttons: [{
			text: '<spring:message code="download"/>',
			tooltip: '<spring:message code="download.the.selected.file.from.server"/>',
			iconCls: 'save',
			handler: doDownload
		},{
			text: '<spring:message javaScriptEscape="true" code="router.config.download.audit.logs"/>',
			tooltip: '<spring:message javaScriptEscape="true" code="router.config.download.audit.logs"/>',
			iconCls: 'save',
			handler: doDownloadAuditLogs
		},{
            text: 'Refresh',
            tooltip: 'Refresh log file list.',
            handler: doRefreshLogs
        }
		],
		buttonAlign:'left'
	});
	<c:if test="${(model.privilegedMode)}">
		ds.load();
	</c:if>
    function doRefreshLogs() {
        ds.reload();
    }


	function doDownloadAuditLogs() {
		var itemURL = "<c:url value="audit_logs_download.html"/>";
		window.open(itemURL, '<spring:message code="download"/>');
	}
/*
Maintenance: Backup DB
*/
	function doBackup() {
		Ext.MessageBox.confirm('<spring:message code="confirmation"/>', '<spring:message code="do.you.want.to.create.a.backup.of.current.db.snapshot"/>', doBackupConfirm);
	}
	function doBackupConfirm(btn) {
		if (btn == 'yes') {
			if (!this.addBackupForm){
				var addBackupFormEl = Ext.get("container").createChild({  tag: 'form', style: 'display:none'  });
				this.addBackupForm = new Ext.form.BasicForm(addBackupFormEl, { url: '<c:url value="securedmaint/maintenance_backup_db.ajax"/>'  });
			}
			this.addBackupForm.errorReader =new Ext.data.XmlReader({
				success: '@success',
				record : 'field'
				},
				['id', 'msg']
			);
			this.addBackupForm.submit({
				waitTitle: '<spring:message code="backup"/>',
				waitMsg: '<spring:message code="backup.the.server.database"/>',
				success: function (form, action) {
					msg('<spring:message code="status"/>', '<spring:message code="db.backup.successfully.done"/>', function(){ds.reload();});
				},
				failure:function(form, action) {
					var errors = '';
					if ((action.result != null) && (!action.result.success)) {
						for(var i=0; i < action.result.errors.length; i++){
							errors += action.result.errors[i].msg + '<br>';
						}
					}
					if(errors != ''){
						msg('<spring:message code="error"/>', '<br>' + errors, function(){});
					}
					else{
						msg('<spring:message code="error"/>', '<spring:message code="timeout"/>', function(){});
					}
				}
			});
		}
	}

/*
Maintenance: download Backup
*/
	function doDownload() {
		var m = grid.getSelectionModel().getSelections();
		if (m.length == 0){
			Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.one.file.to.download"/>');
		} else {
			var m = grid.getSelectionModel().getSelections();
			var location = '<c:url value="logs_download.html"/>';
			var fileNames="";
			for (var i = 0; i < m.length; i++) {
				if (i == 0) {
					fileNames = m[i].get("fileName");
				} else
					fileNames = fileNames + "+" + m[i].get("fileName");
			}
			location = location+"?fileNames=" + fileNames;
			window.location = location;
			grid.getSelectionModel().clearSelections();
		}
	}


/*
Maintenance: delete Backup
*/
	function doDelete() {
		var m = grid.getSelectionModel().getSelections();
		if(m.length > 0) {
			Ext.MessageBox.confirm('<spring:message code="confirmation"/>', '<spring:message code="do.you.really.want.to.delete.the.selected.backup"/>', doDeleteConfirm);
		} else {
			Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.one.file.to.delete"/>');
		}
	}

	function doDeleteConfirm(btn) {
		if(btn == 'yes') {
			var m = grid.getSelectionModel().getSelections();
			var jsonData = "[";
			for(var i = 0, len = m.length; i < len; i++) {
				Ext.Ajax.request({
                    method: 'POST',
					url: '<c:url value="securedmaint/maintenance_delete_backup.ajax"/>',
					params: {fileName: m[i].get("fileName")},
					success: function() {ds.remove(m[i]);}
				});
			}
			ds.load();
		}
	}

/*
Maintenance: Restore Backup
*/
	function doRestore() {
		var m = grid.getSelectionModel().getSelections();
		if(m.length == 1 ) {
			Ext.MessageBox.confirm('<spring:message code="confirmation"/>',
					'<spring:message code="do.you.really.want.to.restore.database.with.the.selected.backup"/>' + '<br/>' +
					'<spring:message code="all.endpoint.software.need.to.be.re.uploaded.after.database.restored"/>',
					doRestoreConfirm);
		}
		else {
			Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.one.backup.file.to.restore"/>');
		}
	}

	function doRestoreConfirm(btn) {
		if(btn == 'yes') {
			var m = grid.getSelectionModel().getSelections();
			if (!this.requireRstoreForm){
				var requireRstoreFormEl = Ext.get("container").createChild({  tag: 'form', style: 'display:none'  });
				this.requireRstoreForm = new Ext.form.BasicForm(requireRstoreFormEl, { url: '<c:url value="securedmaint/maintenance_restore_db.ajax"/>' }
				);
			}
			this.requireRstoreForm.errorReader =new Ext.data.XmlReader({
				success: '@success',
				record : 'field'
				},
				['id', 'msg']
			);
			this.requireRstoreForm.submit({
				waitTitle: '<spring:message code="restore"/>',
				waitMsg: '<spring:message code="restoring.server.database"/>',
				params: {filename: m[0].get("fileName")},
				success: function (form, action) {
					msg('<spring:message code="status"/>', '<spring:message code="server.database.is.successfully.restored"/>'+'<br/>' +'<spring:message code="please.re.upload.endpoint.software"/>',
						function(){
							grid.getSelectionModel().clearSelections();
							ds.reload();
						}
					);
				},
				failure:function(form, action) {
					var errors = '';
					if ((action.result != null) && (!action.result.success)) {
						for(var i=0; i < action.result.errors.length; i++){
							errors += action.result.errors[i].msg + '<br>';
						}
					}
					if(errors != ''){
						msg('<spring:message code="error"/>', '<br>' + errors, function(){});
					}
					else{
						msg('<spring:message code="error"/>', '<spring:message code="timeout"/>', function(){});
					}
				}
			});
		}
	}

/*
Maintenance: Factory Defaults
*/
	function doRestoreFactoryDB() {
		Ext.MessageBox.confirm(
			'<spring:message code="confirmation"/>',
			'<spring:message code="do.you.really.want.to.restore.all.data.to.factory.defaults"/>',
			doRestoreFactoryDBConfirm
		);
	}

	function doRestoreFactoryDBConfirm(btn) {
		if(btn == 'yes') {
			if(!this.addRestoreDBForm){
				var addRestoreDBFormEl = Ext.get("container").createChild({
					tag: 'form',
					style: 'display:none'
				});
				this.addRestoreDBForm = new Ext.form.BasicForm(addRestoreDBFormEl, {
					url: '<c:url value="securedmaint/maintenance_factory_defaults.ajax"/>'
				});
			}
			this.addRestoreDBForm.errorReader =new Ext.data.XmlReader({
				success: '@success',
				record : 'field'
				},
				['id', 'msg']
			);
			this.addRestoreDBForm.submit({
				waitTitle: '<spring:message code="restoring"/>',
				waitMsg:   '<spring:message code="restore.database.to.factory.defaults"/>',
				success: function (form, action) {
					msg('<spring:message code="restore.database.success"/>', '<spring:message code="the.server.database.was.successfully.restored"/>', function(){ds.reload();});
				},
				failure:function(form, action) {
					var errors = '';
					if (!action.result.success) {
						if(action.result.errors) {
							for(i=0; i< action.result.errors.length; i++){
								 errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br/>';
							}
						}
					}
					if(errors != ''){
						msg('<spring:message code="restore.database.failed"/>', '<br>' + errors, function(){});
					}
					else{
						msg('<spring:message code="restore.database.failed"/>', '<spring:message code="timeout"/>', function(){});
					}
				}
			});
		}
	}

/*
Maintenance: Upload Backup
*/
	var uploadwin;
	var uploadform = new Ext.FormPanel({
		baseCls: 'x-plain',
		fileUpload: true,
		frame: false,
		autoHeight: true,
		bodyStyle: 'padding: 10px 10px 0 10px;',
		errorReader: new Ext.data.XmlReader({
			success: '@success',
			record : 'field'
			},[
			'id', 'msg'
			]
		),
		defaults: {
			anchor: '95%',
			allowBlank: false,
			msgTarget: 'side'
		},
		items: [{
			xtype: 'fileuploadfield',
			emptyText: '<spring:message code="select.a.file"/>',
			fieldLabel: '',
			hideLabel: true,
			labelSeparator: '',
			name: 'DBarchive',
			buttonCfg: {
				text: '',
				iconCls: 'icon-upload'
			}
		}],
		buttons: [{
			text: '<spring:message code="upload"/>',
			handler: function(){
				if(uploadform.getForm().isValid()){
					uploadform.getForm().submit({
						url: '<c:url value="securedmaint/maintenance_upload_db.ajax"/>',
						waitTitle: '<spring:message code="uploading.backup.file"/>',
						waitMsg: '<spring:message code="your.file.is.being.uploaded"/>',
						success: function (form, action) {
							uploadform.getForm().reset();
							uploadwin.hide();
							msg('<spring:message code="file.upload.success"/>',
								'<spring:message code="your.backup.file.was.successfully.uploaded"/>',
								function(){
									ds.load();
								}
							);
						},
						falure: function(form, action) {
							var errors = '';
							if ((action.result != null) && (!action.result.success)) {
								for(var i=0; i < action.result.errors.length; i++){
									errors += action.result.errors[i].msg + '<br>';
								}
							}
							if(errors != ''){
								msg('<spring:message code="error"/>', '<br>' + errors, function(){});
							}
							else{
								msg('<spring:message code="error"/>', '<spring:message code="timeout"/>', function(){});
							}
						}
					});
				}
			}
		}]
	});
	function doUpload() {
		if (!uploadwin) {
			uploadwin = new Ext.Window({
				title: '<spring:message code="uploading.backup.file"/>',
				closable: true,
				closeAction: 'hide',
				resizable: false,
				width: 500,
				autoHeight: true,
				border: true,
				frame: true,
				layout: 'fit',
				items: [
					uploadform
				],
				listeners: {
					show: function(el,type) {
						uploadform.getForm().reset();
					}
				}
			});
		}
		uploadwin.show(this);
	}

/*
Reset panel
*/


	function do_refresh() {
		do_upgrade_refresh();
	}

	var refresher = {
		run: do_refresh,
		interval: 10000
	};
	
	var serverTimeFail = false;
	function do_upgrade_refresh() {

		Ext.Ajax.request({
			disableCaching : true,
            method: 'POST',
			url : '<c:url value="serverstartedtime.ajax"/>',
			success : function (response, options) {
				var xml = response.responseXML;

				
//				console.log("do_upgrade_refresh() Success ---> ");
//				console.log("xml ---> " + xml);
//				console.log("serverTimeFail ---> " + serverTimeFail);
//				console.log("response.readyState ---> " + response.readyState);
//				console.log("response.status ---> " + response.status);

				if((xml == null || xml == undefined) && serverTimeFail == false) {
					serverTimeFail = true;
					Ext.Msg.show({
						title: '<spring:message code="the.system.is.going.down.for.reboot.now"/>',
						msg: '<spring:message code="system.rebooting"/>',
						minWidth: 300,
						modal: true,
						icon: Ext.Msg.INFO,
						fn: function(){ }
					});
				} else if( serverTimeFail == true && response.status == 200) {
					serverTimeFail = false;
                    window.location = '<c:url value="logout.html"/>';
				}
			},
			failure : function (response, options) {
				var xml = response.responseXML;

//				console.log("do_upgrade_refresh() Failure ---> ");
//				console.log("xml ---> " + xml);
//				console.log("serverTimeFail ---> " + serverTimeFail);
//				console.log("response.readyState ---> " + response.readyState);
//				console.log("response.status ---> " + response.status);

				if(serverTimeFail == false) {
					serverTimeFail = true;
					Ext.Msg.show({
						title: '<spring:message code="the.system.is.going.down.for.reboot.now"/>',
						msg: '<spring:message code="system.rebooting"/>',
						minWidth: 300,
						modal: true,
						icon: Ext.Msg.INFO,
						fn: function(){ }
					});
				}
			}
		});
	}
	
	var upgrade_refresher = {
			run: do_upgrade_refresh,
			interval: 10000
	};

	var resetpanel = new Ext.FormPanel({

		labelWidth: 200,
		labelAlign: "right",
		items: [{
			xtype:'textfield',
			fieldLabel: "<spring:message code="user.name"/>",
			id: "username",
			allowBlank: false,
			width: 200
		},{
			xtype:'textfield',
			inputType: "password",
			id: "password",
			allowBlank: false,
			fieldLabel: "<spring:message code="password"/>",
			width: 200
		}],
		buttons: [{
			text: '<spring:message code="restart"/>',
			tooltip: '<spring:message code="do.restart.of.server"/>',
			iconCls: 'reset',
			handler: function() {
				Ext.MessageBox.confirm('<spring:message code="confirmation"/>', '<spring:message code="do.you.want.to.restart.server"/>', function(btn) {
					if(btn == 'yes') {
						Ext.MessageBox.alert('<spring:message code="system.rebooting"/>', '<spring:message code="please.refresh.the.page.after.system.restarted"/>' );
						doReboot();
					}
				});
			}
		},{
			text: '<spring:message code="shutdown"/>',
			tooltip: '<spring:message code="shutdown.the.server"/>',
			iconCls: 'shutdown',
			handler: function() {
				Ext.MessageBox.confirm('<spring:message code="confirmation"/>', '<spring:message code="shutdown.server.will.terminate.all.running.processes.shutdown"/>', function(btn) {
					if(btn == 'yes') {
						doShutdown();
					}
				});
			}
		}   ]
	});

	function doShutdown() {
		var url = '<c:url value="securedmaint/maintenance_system_reset.ajax"/>';
		if (!this.addShutdownForm) {
			var addShutdownFormEl = Ext.get("container").createChild({
				tag: 'form',
				style: 'display:none'
			});
			this.addShutdownForm = new Ext.form.BasicForm(addShutdownFormEl, {
                method: "POST",
				url: url,
				errorReader: new Ext.data.XmlReader({
					success: '@success',
					record : 'field'
					},[
					'id', 'msg'
					]
				)
			});
		}
		this.addShutdownForm.submit({
			params : {
				username : Ext.getCmp("username").getValue(),
				password: Ext.getCmp("password").getValue(),
				command: "shutdown"
			},
			waitTitle: '<spring:message code="shutdown"/>',
			waitMsg: '<spring:message code="shutdown.the.server"/>',
			success: function (form, action) {
				msg('<spring:message code="success"/>',
						'<spring:message code="your.server.is.turned.off"/>',
						function(){ }
				);
			},
			failure: function(form, action) {
				var errors = '';
				if ((action.result != null) && (!action.result.success)) {
					for(var i=0; i < action.result.errors.length; i++){
						errors += action.result.errors[i].msg + '<br>';
					}
				}
				if(errors != ''){
					msg('<spring:message code="error"/>', '<br>' + errors, function(){});
				}
				else{
					msg('<spring:message code="error"/>', '<spring:message code="timeout"/>', function(){});
				}
			}
		});
	}

	function doReboot() {
		var url = '<c:url value="securedmaint/maintenance_system_reset.ajax"/>';
		if (!this.addRebootForm) {
			var addRebootFormEl = Ext.get("container").createChild({
				tag: 'form',
				style: 'display:none'
			});
			this.addRebootForm = new Ext.form.BasicForm(addRebootFormEl, {
                method: "POST",
				url: url,
				errorReader: new Ext.data.XmlReader({
					success: '@success',
					record : 'field'
					},[
					'id', 'msg'
					]
				)
			});
		}
		Ext.TaskMgr.start(refresher);
		this.addRebootForm.submit({
			params : {
				username : Ext.getCmp("username").getValue(),
				password: Ext.getCmp("password").getValue(),
				command: "restart"
			},
			waitTitle: '<spring:message code="the.system.is.going.down.for.reboot.now"/>',
			waitMsg: '<spring:message code="system.rebooting"/>',
			success: function (form, action) {
				Ext.Msg.show({
					title: '<spring:message code="the.system.is.going.down.for.reboot.now"/>',
					msg: '<spring:message code="system.rebooting"/>',
					minWidth: 300,
					modal: true,
					icon: Ext.Msg.INFO,
					fn: function(){ }
				});
			},
			failure:function(form, action) {
				var errors = '';
				if ((action.result != null) && (!action.result.success)) {
					for(var i=0; i < action.result.errors.length; i++){
						errors += action.result.errors[i].msg + '<br>';
					}
				}
				if(errors != ''){
					msg('<spring:message code="error"/>', '<br>' + errors, function(){});
				}
				else{
					msg('<spring:message code="the.system.is.going.down.for.reboot.now"/>', '<spring:message code="system.rebooting"/>', function(){});
				}
			}
		});
	}


/*
Upload/Update panel
*/
	var updatepanelBtn = new Ext.Panel({
		buttons:[{
			text: '<spring:message code="upload.and.update"/>',
			handler: doUpdate,
			tooltip: '<spring:message code="upload.the.new.server.software.file.to.server.and.run.update.process"/>'
		}],
		buttonAlign:'center'
	});
	
	var upgradeFileName;
	function do_system_upgrade() {

		Ext.TaskMgr.start(upgrade_refresher);
		
		var upgradeProgressWindow = Ext.Msg.wait('<spring:message code="your.server.software.file.is.being.updated"/>' ,
			'<spring:message code="message"/>');
		
		var upgradeUrl = '<c:url value="securedmaint/maintenance_system_upgrade.ajax"/>'
		if(upgradeUrl.indexOf('?') > 0) {
			upgradeUrl += "&" + Ext.urlEncode({PORTALarchive : upgradeFileName});
		} else {
			upgradeUrl += "?" + Ext.urlEncode({PORTALarchive : upgradeFileName});
		}
		console.log("upgradeUrl ---> " + upgradeUrl);
		Ext.Ajax.request({
            method: "POST",
			disableCaching : true,
			url : upgradeUrl,
			success : function (response, options) {
				
//				console.log("do_system_upgrade() Success ---> ");
//				console.log("response.readyState ---> " + response.readyState);
//				console.log("response.status ---> " + response.status);

				if (response.status == 200) {
					var xml = response.responseXML;
					var successVal = Ext.DomQuery.selectNode("message", xml).getAttribute("success").toLowerCase();
					
					Ext.TaskMgr.stop(upgrade_refresher);
					upgradeProgressWindow.hide();
					
					if(successVal == "true") {
						msg('<spring:message code="message"/>', '<spring:message code="your.server.software.file.was.successfully.uploaded.and.updated"/>', function(){});
					} else {
						var errors = '';
						var errorNodes = Ext.DomQuery.select("errors", xml);
						if (successVal != null && successVal == "false") {
							for(var i=0; i < errorNodes.length; i++){
								errors += Ext.DomQuery.selectValue("msg", errorNodes[i]) + '<br>';
							}
						}
						
						if(errors != ''){
							msg('<spring:message code="message"/>', '<br>' + errors, function(){});
						}
						else{
							msg('<spring:message code="error"/>', '<spring:message code="cannot.update.router.software.from.the.uploaded.file"/>', function(){});
						}
					}
				}
			},
			failure : function (response, options) {

//				console.log("do_system_upgrade () Failure ---> ");
//				console.log("response.readyState ---> " + response.readyState);
//				console.log("response.status ---> " + response.status);

				if (response.status == 200) {
					
					Ext.TaskMgr.stop(upgrade_refresher);
					upgradeProgressWindow.hide();
	
					var errors = '';
					var xml = response.responseXML;
					var errorNodes = Ext.DomQuery.select("errors", xml);
	
					for(var i=0; i < errorNodes.length; i++){
						errors += Ext.DomQuery.selectValue("msg", errorNodes[i]) + '<br>';
					}
	
					
					if(errors != ''){
						msg('<spring:message code="message"/>', '<br>' + errors, function(){});
					}
					else{
						msg('<spring:message code="error"/>', '<spring:message code="cannot.update.router.software.from.the.uploaded.file"/>', function(){});
					}
				}
			}
		});
	}

	var pingerTask = {
		run: function(){
			Ext.Ajax.request({
				disableCaching: true,
				method: 'POST',
				url: '<c:url value="serverstartedtime.ajax"/>'
			});
		},
		interval: 14000 //14s
	};
	var pingerRunner = new Ext.util.TaskRunner();
	function startPinger() {
		pingerRunner.start(pingerTask);
	}
	function stopPinger() {
		pingerRunner.stop(pingerTask);
	}

	var updatewin;
	var updateform = new Ext.FormPanel({
		baseCls: 'x-plain',
		fileUpload: true,
		frame: false,
		autoHeight: true,
		bodyStyle: 'padding: 10px 10px 0 10px;',
		errorReader: new Ext.data.XmlReader({
			success: '@success',
			record : 'field'
			},
			[ 'id', 'msg' ]
		),
		defaults: {
			anchor: '95%',
			allowBlank: false,
			msgTarget: 'side'
		},
		items: [{
			xtype: 'fileuploadfield',
			emptyText: '<spring:message code="select.a.vidyo.file"/>',
			fieldLabel: '',
			hideLabel: true,
			labelSeparator: '',
			name: 'PORTALarchive',
			id: 'upgradeFileName',
			buttonCfg: {
				text: '',
				iconCls: 'icon-upload'
			}
		}],
		buttons: [{
			text: '<spring:message code="upload"/>',
			disabled: false,
			handler: function() {
				if(updateform.getForm().isValid()) {
					startPinger();
//					console.log("upgradeFileName---> " + updateform.items.get("upgradeFileName").value);
					upgradeFileName = updateform.items.get("upgradeFileName").value;
					upgradeFileName = upgradeFileName.replace(/^.*(\\|\/|\:)/, '');
//					console.log("upgradeFileName after removing path---> " + upgradeFileName);
					updateform.getForm().submit({
                        params: {'${_csrf.parameterName}' : '${_csrf.token}' },
						url: '<c:url value="securedmaint/maintenance_system_upgrade_upload.ajax"/>',
						waitTitle: '<spring:message code="uploading.new.server.software.file"/>',
						waitMsg: '<spring:message code="your.server.software.file.is.being.uploaded"/>',
						success: function (form, action) {
							stopPinger();
							updateform.getForm().reset();
							updatewin.hide();

							do_system_upgrade();
						},
						failure: function(form, action) {
							stopPinger();
							updateform.getForm().reset();
							updatewin.hide();
							
							msg('<spring:message code="error"/>', '<spring:message code="file.upload.failed"/>', function(){});
						}
					});
				}
			}
		}]
	});

	function doUpdate() {
		if (!updatewin) {
			updatewin = new Ext.Window({
				title: '<spring:message code="uploading.new.server.software.file"/>',
				closable: true,
				closeAction: 'hide',
				resizable: false,
				width: 500,
				autoHeight: true,
				border: true,
				frame: true,
				layout: 'fit',
				items: [
					updateform
				]
			});
		}
		updatewin.show(this);
	}
	
	var upgrade_install_log_sm = new Ext.grid.CheckboxSelectionModel({
		singleSelect: true
	});

    var upgrade_install_log_cm = new Ext.grid.ColumnModel([
        upgrade_install_log_sm,
        { header: '<spring:message code="file.name1"/>',  dataIndex: 'fileName', align:'left' },
        { header: '<spring:message code="creation.date"/>',  dataIndex: 'timestamp', align:'left', sortable: true}
    ]);

    var upgradeInstallLogReport = Ext.data.Record.create([
        {name: 'fileName', type: 'string'},
        {name: 'timestamp', type: 'string'}
    ]);

    var upgradeInstallLogReportReader = new Ext.data.XmlReader({
        totalRecords: 'results',
        record: 'row'
    }, upgradeInstallLogReport);

    var upgrade_install_log_ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({method: "POST",  url: '<c:url value="securedmaint/maintenance_install_log_list.ajax"/>', timeout: 120000 }),
        reader: upgradeInstallLogReportReader,
        remoteSort: false
    });

    var upgrade_install_log_grid = new Ext.grid.GridPanel({
        header: true,
        store: upgrade_install_log_ds,
        cm: upgrade_install_log_cm,
        sm: upgrade_install_log_sm,
        autoWidth: true,
        height: (maintenancePanelSize-245),
        frame:true,
        loadMask: true,
        title: '<spring:message code="maintenance.install.logs"/>',
        viewConfig: {
            forceFit: true
        },
        buttons: [{
			text: '<spring:message code="download"/>',
			tooltip: '<spring:message code="download.the.selected.file.from.server"/>',
			iconCls: 'save',
			handler: doInstallLogDownload
		}],
		buttonAlign:'left'
    });
    
    
    var upgrade_install_patch_cm = new Ext.grid.ColumnModel([
        { header: '<spring:message code="patch.name"/>', dataIndex: 'patch', align:'left', menuDisabled: true },
        { header: '<spring:message code="creation.date"/>', dataIndex: 'timestamp', align:'left', menuDisabled: true}
    ]);
    
    var installedPatches = Ext.data.Record.create([
        {name: 'patch', type: 'string'},
        {name: 'timestamp', type: 'string'}
    ]);

    var installedPatchesReader = new Ext.data.XmlReader({
        totalRecords: 'results',
        record: 'row'
    }, installedPatches);
    
    var installed_patches_ds = new Ext.data.Store({
        method: "POST",
    	url: '<c:url value="maintenance_installed_patches.html"/>',
    	reader: installedPatchesReader
    });

    var upgrade_install_patch_grid = new Ext.grid.GridPanel({
        header: true,
        store: installed_patches_ds,
        cm: upgrade_install_patch_cm,
        autoWidth: true,
        height: (maintenancePanelSize-215),
        frame:true,
        loadMask: true,
        title: '<spring:message code="manitenance.installed.patches"/>',
        viewConfig: {
            forceFit: true
        }
    });

    var installlogpanelgrid = new Ext.Panel({
		border: false,
		items:[{
			border: false,
			items: [{
				baseCls:'x-plain',
				bodyStyle:'padding:5px 2px',
				id: 'install_log_grid',
				items:[
					upgrade_install_log_grid
				]
			}]
		}]
	});
    
    var installpatchpanelgrid = new Ext.Panel({
		border: false,
		items:[{
			border: false,
			items: [{
				baseCls:'x-plain',
				bodyStyle:'padding:5px 2px',
				id: 'install_patch_grid', 
				items:[
					upgrade_install_patch_grid
				]
			}]
		}]
	});
	
	var updatepanel = new Ext.Panel({
		frame: true,
		autoScroll: true,
		autoHeight: true,
		autoWidth: true,
		items: [
			updatepanelBtn,
			installlogpanelgrid,
			installpatchpanelgrid
		]
	});
	
	/*
	Maintenance: download Install logs
	*/
		function doInstallLogDownload() {
			var m = upgrade_install_log_grid.getSelectionModel().getSelections();
			if(m.length == 1) {
				var m = upgrade_install_log_grid.getSelectionModel().getSelections();
				var location = '<c:url value="maintenance_download_install_log.html"/>';
				location = location+"?f="+m[0].get("fileName");
				window.location = location;
				grid.getSelectionModel().clearSelections();
			} else if (m.length == 0){
				Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.one.file.to.download"/>');
			} else {
				Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.only.one.file.at.a.time.to.download"/>');
			}
		}

/*
Maintenance TabPanel UI:
	|Database|System Upgrade|System Restart|Security|CDR|

Following 2 tab's visibilities are based on license values
	|Status Notify|Ext DB|
*/
	var activeDestination = '';

	var configServerForm = new Ext.FormPanel({

		url:'<c:url value="securedmaint/update_config_server.ajax"/>',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				}, [
			'id', 'msg'
		]
		),

		labelWidth: 200,
		labelAlign: "right",
		items: [{
			allowBlank: false,
			xtype:'textfield',
			fieldLabel: "<spring:message javaScriptEscape="true" code="router.config.configuration.server"/>",
			name: "configServerAddress",
			value: "<c:out value="${model.configServerAddress}"/>",
			width: 200
		},
			{
				xtype:"label",
				html:"<span style=\"padding-left: 210px\">(e.g. \"localhost\" or \"192.168.0.1:8080\")</span>"
			}],
		buttons:[{
			text: "<spring:message code="apply"/>",
			handler: updateConfigServer
		}]
	});

	function updateConfigServer() {
        Ext.MessageBox.confirm('Confirm', "All active conferences will be terminated. Are you sure you want to continue?", function (btn) {
            if (btn == 'yes') {
                Ext.Msg.show({
                    title: 'Saving',
                    msg: 'Saving configuration, please wait...',
                    minWidth: 300,
                    modal: true,
                    icon: Ext.Msg.INFO,
                    fn: function(){ }
                });
                configServerForm.getForm().submit({
                    success:function (form, action) {
                        msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="router.config.successfully.saved.configuration.server"/>");
                    },
                    failure:function (form, action) {
                        var errors = '';
                        if ((action.result != null) && (!action.result.success)) {
                            for (var i = 0; i < action.result.errors.length; i++) {
                                errors += action.result.errors[i].msg + '<br>';
                            }
                        }
                        if (errors != '') {
                            msg('Error', '<br>' + errors, function () {
                            });
                        }
                        else {
                            msg('Error', '<spring:message javaScriptEscape="true" code="router.config.error.occurred.saving.configuration.server"/>', function () {
                            });
                        }
                    }
                });
            }
        });
	}

	var logForm = new Ext.FormPanel({
		url:'<c:url value="securedmaint/update_log_settings.ajax"/>',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				}, [
			'id', 'msg'
		]
		),

		labelWidth: 200,
		labelAlign: "right",
		items: [{
			allowBlank: false,
			xtype:'textfield',
			fieldLabel: "<spring:message javaScriptEscape="true" code="router.config.log.level.category"/>:",
			name: "logLevel",
			value: "<c:out value="${model.logLevel}"/>",
			width: 200
		},
			{
				allowBlank: false,
				xtype:'textfield',
				fieldLabel: "<spring:message javaScriptEscape="true" code="router.config.max.log.file.size.kb"/>:",
				name: "logMaxFileSize",
				value: "<c:out value="${model.logFileMaxSize}"/>",
				width: 200
			},{
				allowBlank: false,
				xtype:'textfield',
				fieldLabel: "<spring:message javaScriptEscape="true" code="router.config.log.file.name"/>:",
				name: "logFileName",
                readOnly: true,
				value: "<c:out value="${model.logFileName}"/>",
				width: 200
			}
		],
		buttons:[{
			text: "<spring:message code="apply"/>",
			handler: updateLogSettings
		}]
	});

	function updateLogSettings() {
		logForm.getForm().submit({
			success:function (form, action) {
				msg("<spring:message javaScriptEscape="true" code="success"/>", "Successfully, saved logging settings.");
			},
			failure:function (form, action) {
				var errors = '';
				if ((action.result != null) && (!action.result.success)) {
					for (var i = 0; i < action.result.errors.length; i++) {
						errors += action.result.errors[i].msg + '<br>';
					}
				}
				if (errors != '') {
					msg('Error', '<br>' + errors, function () {
					});
				}
				else {
					msg('Error', '<spring:message javaScriptEscape="true" code="router.config.error.occurred.saving.logging.settings"/>', function () {
					});
				}
			}
		});
	}


    /*
     Diagnostics Grid Panel
     */
    var dia_sm = new Ext.grid.CheckboxSelectionModel();

    var dia_cm = new Ext.grid.ColumnModel([
        dia_sm,
        { header: '<spring:message code="file.name1"/>',  dataIndex: 'fileName', align:'left' },
        { header: '<spring:message code="creation.date"/>',  dataIndex: 'timestamp', align:'left', sortable: false, width: 190 }
    ]);

    var diaReport = Ext.data.Record.create([
        {name: 'fileName', type: 'string'},
        {name: 'timestamp', type: 'string'}
    ]);

    var diaReportReader = new Ext.data.XmlReader({
        totalRecords: 'results',
        record: 'row',
        id: 'timestamp'
    }, diaReport);

    var dia_ds = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({ method: 'POST', url: '<c:url value="securedmaint/maintenance_diagnostics_list.ajax"/>', timeout: 120000 }),
        reader: diaReportReader,
        remoteSort: false
    });

    var dia_grid = new Ext.grid.GridPanel({
        header: false,
        store: dia_ds,
        cm: dia_cm,
        sm: dia_sm,
        autoWidth: true,
        autoHeight: true,
        //layout:'border',
        frame:true,
        loadMask: true,
        viewConfig: {
            forceFit: true
        }
    });

    var diagnosticsViewerWin;
    var diagnosticsPanel = new Ext.form.FormPanel({
        frame: true,
        labelAlign: 'right',
        labelWidth: 200,
        autoWidth: true,
        bodyStyle: 'padding: 0 10px 0 0;',
        defaults: {
            labelStyle: 'font-weight:bold;text-align:right;'
        },
        items: [{
           xtype: 'label',
            text: '<spring:message javaScriptEscape="true" code="diagnostics.history"/>',
            style: 'font-weight: bold'
        }, dia_grid],
        buttons: [{
            text: '<spring:message javaScriptEscape="true" code="run"/>',
            tooltip: '<spring:message javaScriptEscape="true" code="run.diagnostics"/>',
            handler: function() {
                var box = Ext.MessageBox.wait('<spring:message javaScriptEscape="true" code="please.wait.while.the.diagnostics.complete"/>', '<spring:message javaScriptEscape="true" code="running.diagnostics"/>');
                Ext.Ajax.request({
                    method: 'POST',
                    url: '<c:url value="securedmaint/maintenance_diagnostics_run.ajax"/>',
                    success: function(d) { box.hide();
                        if (d.responseText.indexOf('false') == -1) {
                            msg("Result", "<spring:message javaScriptEscape="true" code="successfully.ran.diagnostics.please.refresh.page.in.a.few.minutes.to.see.the.results"/>");
                            dia_ds.reload();
                        } else {
                            msg("Result", "<spring:message javaScriptEscape="true" code="unable.to.run.diagnostics"/>");
                        }
                    },
                    failure: function() { box.hide(); msg("Error", "<spring:message javaScriptEscape="true" code="unable.to.run.diagnostics"/>");}
                });
            }
        },{
            text: '<spring:message javaScriptEscape="true" code="view"/>',
            tooltip: '<spring:message javaScriptEscape="true" code="view.diagnostics"/>',
            handler: function() {
                if(!diagnosticsViewerWin){
                    diagnosticsViewerWin = new Ext.Window({
                        applyTo     : 'diagnostics-win',
                        layout      : 'fit',
                        width       : 500,
                        height      : 400,
                        closeAction :'hide',
                        plain       : true,
                        autoScroll  : true,
                        items       : new Ext.Panel({
                            applyTo        : 'diagnostics-panel',
                            deferredRender : false,
                            border         : false
                        }),
                        buttons: [{
                            text     : 'Close',
                            handler  : function(){
                                diagnosticsViewerWin.hide();
                            }
                        }]
                    });

                }
                var m = dia_grid.getSelectionModel().getSelections();

                if (m.length == 0){
                    Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message javaScriptEscape="true" code="please.select.one.file.to.view"/>');
                    return;
                }
                else if (m.length > 1){
                    Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message javaScriptEscape="true" code="please.select.only.one.file.to.view"/>');
                    return;
                }

                dia_grid.getSelectionModel().clearSelections();

                Ext.Ajax.request({
                    url: '<c:url value="maintenance_diagnostics_view.html"/>?f=' +m[0].get("fileName"),
                    success: function(d) { Ext.get("diagnostics-report").update(d.responseText) },
                    failure: function() { Ext.get("diagnostics-report").update("<b style='color: red;'>error loading report</b>") }
                });
                diagnosticsViewerWin.show();
            }
        },{
            text: '<spring:message javaScriptEscape="true" code="download"/>',
            tooltip: '<spring:message javaScriptEscape="true" code="download.diagnostics"/>',
            iconCls: 'save',
            handler: function() {
                var m = dia_grid.getSelectionModel().getSelections();

                if (m.length == 0){
                    Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.one.file.to.download"/>');
                    return;
                }
                else if (m.length > 1){
                    Ext.MessageBox.alert('<spring:message code="error"/>', '<spring:message code="please.select.only.one.file.at.a.time.to.download"/>');
                    return;
                }

                dia_grid.getSelectionModel().clearSelections();
                window.location = '<c:url value="maintenance_diagnostics_download.html"/>?f=' +m[0].get("fileName");
            }
        }]
    });


    var tabs = new Ext.TabPanel({
		activeTab: 0,
		plain: false,
		autoWidth: true,
		autoHeight: true,
		enableTabScroll: true,
		deferredRender: false,
		layoutOnTabChange:true,
		defaults: {
			autoHeight: true
		},
		items:[{
			title: '<spring:message javaScriptEscape="true" code="router.config.basic"/>',
			frame: true,
			items:[
				configServerForm
			]
		},{
			title: '<spring:message javaScriptEscape="true" code="router.config.log.settings"/>',
			frame: true,
			items: [ logForm]
		},
			{
				title:'<spring:message javaScriptEscape="true" code="router.config.download.logs"/>',
				listeners: {
					activate: function(p) {
						activeDestination = '';
					}
				},
				items:[
					new Ext.Panel({
						layout: 'fit',
						height: 400,
						items:[
							grid
						]
					})
				]

	},

<c:if test="${model.standaloneRouter}">
            {
			title:'<spring:message code="system.upgrade"/>',
			frame:true,
			buttonAlign:'center',
			listeners: {
				activate: function(p) {
					activeDestination = 'SA_MaintenanceSystemUpgrade';
					upgrade_install_log_ds.load();
					installed_patches_ds.load();
				}
			},
			items: [
				updatepanel
			]
		},
</c:if>
            {
                title: '<spring:message javaScriptEscape="true" code="diagnostics"/>',
                listeners: {
                    activate: function(p) {
                        activeDestination = 'SA_SettingsDiagnostics';
                        dia_ds.load();
                    }
                },
                items: [
                    diagnosticsPanel
                ]
            },
            {
			title:'<spring:message code="system.restart"/>',
			frame:true,
			iconCls: 'reset',
			listeners: {
				activate: function(p) {
					activeDestination = 'SA_Maintenance SystemRestart';
				}
			},
			items: [
				resetpanel
			]
		}
		]
	});

	var maintenancePanel = new Ext.Panel({
		title: '<spring:message code="maintenance"/>' + ' <small class="red">[<c:out value="${model.portalversion}"/>]</small>' + ' <small><c:out value="${model.vidyoRouterType}"/></small> ',
		frame: true,
		items: [
			tabs
		]
	});
	
	<c:if test="${!model.privilegedMode}">
		tabs.setDisabled(true);
	</c:if>

/*
Maintenance: Top Level UI
	-Left: Local Menu
	-Right: Maintenance Tabbed Panels
*/
	new Ext.Panel({
		renderTo: 'content-panel',
		border: false,
		items:[{
			layout: 'column',
			border: false,
			items: [{
				columnWidth:.25,
				baseCls:'x-plain',
				bodyStyle:'padding:5px 5px 5px 5px',
				id: 'local',
				items:[
					localmenu
				]
			},{
				columnWidth:.75,
				baseCls:'x-plain',
				bodyStyle:'padding:5px 5px 5px 5px',
				items:[
					maintenancePanel
				]
			}]
		}]
	});

});

</script>

<%@ include file="footer_html.jsp" %>