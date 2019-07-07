Ext
	.define(
				'SuperApp.view.settings.maintenance.MaintenanceController',
				{
		extend: 'Ext.app.ViewController',
		alias: 'controller.MaintenanceController',

		listen: {
			controller: {
				'*': {
					'rebootserver': 'restartrebootserver'
				}
			}
		},

		getDiagnosticsData: function () {
			var me = this, viewModel = me.getViewModel();

			viewModel.getStore('diagnosticsStore').removeAll();
			viewModel.getStore('diagnosticsStore').load();
		},

		getDatabaseData: function () {
			var me = this, viewModel = me.getViewModel();

			viewModel.getStore('databaseGridStore').load();
		},

		getSystemUpgradeData: function () {
			var me = this, viewModel = me.getViewModel();

			Ext.Ajax
				.request({
					url: 'maintenance.html',
					success: function (response) {
						var result = response.responseXML, portalversion = Ext.DomQuery
							.selectValue('portalversion',
							result), allowUpgrade = Ext.DomQuery
								.selectValue('allowUpgrade',
								result);

						viewModel.set('portalversion',
							portalversion.trim());
						viewModel
							.set(
							'allowUpgrade',
							allowUpgrade.trim() == "true" ? true
								: false);
					}
				});

			viewModel.getStore('installPatchesGridStore').load(
				{
					callback: function (recs) {
						viewModel.set('hideInstallPatchGrid',
							recs.length ? false : true);
					}
				});
		},

		onClickRunDiagnostics: function () {
			var me = this;
			var waitBox = Ext.Msg
				.wait(
				l10n('please-wait-while-the-diagnostics-complete'),
				l10n('running-diagnostics'));
			Ext.Ajax
				.request({
					url: "securedmaint/maintenance_diagnostics_run.ajax",
					success: function (d) {
						waitBox.hide();
						if (d.responseText.indexOf('false') == -1) {
							Ext.Msg
								.alert(
								l10n('result'),
								l10n('successfully-ran-diagnostics-please-refresh-page-in-a-few-minutes-to-see-the-results'));
						} else {
							Ext.Msg
								.alet(
								l10n('result'),
								l10n('unable-to-run-diagnostics'));
						}
						Ext.defer(function () {
							me.getDiagnosticsData();
						}, 500);
					},
					failure: function () {
						waitBox.hide();
						Ext.Msg
							.alert(
							l10n('error'),
							l10n('unable-to-run-diagnostics'));
					}
				});
		},

		onClickViewDiagnostics: function () {
			var me = this, grid = me
				.lookupReference('diagnosticslist'), selected = grid
					.getSelection();

			if (selected.length == 1) {
				me.viewDiagnosticsRunReport(selected[0]);
			} else if (selected.length > 1) {
				Ext.Msg
					.alert(
					l10n('error'),
					l10n('please-select-only-one-file-to-view'));
			} else {
				Ext.Msg.alert(l10n('error'),
					l10n('please-select-one-file-to-view'));
			}
		},

		viewDiagnosticsRunReport: function (rec) {
			var me = this;
			Ext.Ajax
				.request({
					url: 'securedmaint/maintenance_diagnostics_view.ajax?f='
					+ rec.get('fileName'),
					success: function (res) {
						diagnosticsViewerWin = Ext
							.create(
							'Ext.window.Window',
							{
								width: 700,
								height: 400,
								closeAction: 'hide',
								title: l10n('view-diagnostics-report'),
								scrollable: true,
								items: [{
									xtype: 'component',
									padding: 5,
									margin: 5,
									html: ''
								}],
								buttons: [{
									text: l10n('close'),
									handler: function () {
										diagnosticsViewerWin
											.hide();
									}
								}]
							});
						diagnosticsViewerWin
							.down('component')
							.setHtml(
							res.responseText.trim());
						diagnosticsViewerWin.show();

					},
					failure: function () {
					}
				});
		},

		onClickDownloadDiagnostics: function () {
			var me = this, grid = me
				.lookupReference('diagnosticslist'), selected = grid
					.getSelection();

			if (selected.length == 1) {
				window.location = 'securedmaint/maintenance_diagnostics_download.ajax?f='
					+ selected[0].get("fileName");
			} else if (selected.length > 1) {
				Ext.Msg
					.alert(
					l10n('error'),
					l10n('please-select-only-one-file-at-a-time-to-download'));
			} else {
				Ext.Msg.alert(l10n('error'),
					l10n('please-select-one-file-to-download'));
			}

		},

		sysUpgradeDownload: function () {
			var sel = this.lookupReference("sysUpgradeLogsGrid")
				.getSelectionModel().getSelection();
			if (sel.length == 0) {
				Ext.Msg.alert(l10n('error'),
					l10n('please-select-one-file-to-download'));
			} else {
				var location = 'securedmaint/maintenance_download_install_log.ajax?';
				location = location + "&f="
					+ sel[0].get("fileName");
				window.location = location;
			}
		},

		sysUpgradeUploadWin: function () {
			var scope = this;
			var sysUpgradeUploadWin = "";
			if (!sysUpgradeUploadWin) {
				sysUpgradeUploadWin = Ext
					.create(
					'Ext.window.Window',
					{
						width: '100%',
						border: false,
						modal: true,
						title: l10n('uploading-new-server-software-file'),
						closable: true,
						closeAction: 'destroy',
						resizable: false,
						width: 500,
						autoHeight: true,
						border: true,
						layout: 'fit',
						items: [{
							xtype: 'form',
							fileUpload: true,
							frame: false,
							border: false,
							ui: 'footer',
							cls: 'white-footer',
							modal: true,
							errorReader: Ext
								.create(
								'Ext.data.XmlReader',
								{
									record: 'field',
									model: Ext
										.create("SuperApp.model.settings.Field"),
									success: '@success'
								}),
							defaults: {
								anchor: '95%',
								allowBlank: false,
								msgTarget: 'side'
							},
							items: [
								{
									xtype: 'filefield',
									name: '',
									margin: '10 10 10 10',
									msgTarget: 'side',
									allowBlank: false,
									labelWidth: 200,
									width: 500,
									name: 'PORTALarchive',
									reference: 'upgradeFileName',
									emptyText: l10n('select-a-vidyo-file'),
									labelAlign: 'right',
									buttonText: 'Browse...'
								},
								{
									xtype: 'hidden',
									name: csrfFormParamName,
									value: csrfToken
								}],
							buttonAlign: 'center',
							buttons: [{
								text: l10n('upload'),
								handler: scope.sysUpgradeUpload,
								overlay: sysUpgradeUploadWin,
								scope: scope,
								formBind: true,
								disabled: true
							}]
						}]
					});
			}
			sysUpgradeUploadWin.show();
		},

		sysUpgradeUpload: function (btn) {
			timer.stop();//in slower network it will take more than 15 mins so we are stopping the timer
			var scope = this, viewModel = scope.getViewModel(), form = btn
				.up('form'), parameters = form.getForm()
					.findField('PORTALarchive').getValue();

			parameters = parameters.replace(/^.*(\\|\/|\:)/, '');

			if (form.getForm().isValid()) {
				if (!viewModel.get('allowUpgrade')) {
					Ext.Msg.alert(l10n('error'),
						l10n('portal-not-in-maintenance-mode'));
					return;
				}

				scope.portalUpgradeTimer();

				form
					.getForm()
					.submit(
					{
						url: 'securedmaint/maintenance_system_upgrade_upload.ajax',
						waitTitle: l10n('uploading-new-server-software-file'),
						waitMsg: l10n('your-server-software-file-is-being-uploaded'),
						success: function (frm, action) {
							timer.start();
							//assumming next process wont take more than 15 minutes
							scope.killAllTheTasks();//for stoping all timer tasks which was triggered for keeping the session alive.
							var xmlResponse = action.response.responseXML;
							var success = Ext.DomQuery
								.selectValue(
								'message @success',
								xmlResponse);

							if (success == "false") {
								var responseId = Ext.DomQuery
									.selectNode(
									'id',
									xmlResponse);
								var responseMsg = Ext.DomQuery
									.selectNode(
									'msg',
									xmlResponse);
								if (responseId
									&& responseMsg) {
									Ext.Msg
										.alert(
										responseId.textContent,
										responseMsg.textContent);
								} else {
									Ext.Msg
										.alert(
										l10n('error'),
										l10n('import-read-csv-error'));
									btn.up('form')
										.getForm()
										.reset();
									btn.up('window')
										.close();
								}
							} else {
								Ext.Msg
									.alert(
									l10n('success'),
									l10n('successfuly-uploaded-file'));
								btn.up('form')
									.getForm()
									.reset();
								scope
									.do_system_upgrade(parameters);
								// Ext.TaskManager.stop(scope.upload_session_refresher);
								// scope.getSystemUpgradeData();
							}
						},
						failure: function (frm, action) {
							timer.start();
							scope.killAllTheTasks();//for stoping all  tasks which was triggered for keeping the session alive.
						}
					});
			}
		},

		do_update: function () {
			var me = this;
			Ext.Ajax
				.request({
					disableCaching: true,
					url: 'serverstartedtime.ajax',
					success: function (response, options) {
						var xml = response.responseXML;
						if ((xml == null || xml == undefined)
							&& me.serverTimeFail == false) {
							me.serverTimeFail = true;
							Ext.Msg
								.show({
									title: l10n('the-system-is-going-down-for-reboot-now'),
									msg: l10n('update-successful-system-reboot-shortly'),
									minWidth: 300,
									modal: true,
									icon: Ext.Msg.INFO,
									fn: function () {
									}
								});
						} else if (me.serverTimeFail == true
							&& response.readyState == 4
							&& response.status == 200) {
							me.serverTimeFail = false;
							Ext.TaskMgr.stop(upgrade_refresher);
							msg(l10n('success'),
								l10n('restarted1'),
								function () {
									logoutSuper();
								});
						}
					},
					failure: function (response, options) {
						var xml = response.responseXML;
						if (me.serverTimeFail == false) {
							me.serverTimeFail = true;
							Ext.Msg
								.show({
									title: '',
									msg: l10n('update-successful-system-reboot-shortly'),
									minWidth: 300,
									modal: true,
									icon: Ext.Msg.INFO,
									fn: function () {
									}
								});
						}
					}
				});
		},

		startTaskManager: function () {
			var me = this, updateRefresh = {
				run: me.do_update,
				interval: 15000
			};
			me.serverTimeFail = false;
			Ext.TaskManager.start(updateRefresh);
		},

		stopTaskManager: function () {
			var me = this, updateRefresh = {
				run: me.do_update,
				interval: 15000
			};
			Ext.TaskManager.stop(updateRefresh);
		},

		do_system_upgrade: function (upgradeFileName) {
			var me = this, upgradeUrl = 'securedmaint/maintenance_system_upgrade.ajax?'
				+ Ext.urlEncode({
					'PORTALarchive': upgradeFileName
				}), upgradeProgressWindow = Ext.Msg
					.wait(
					l10n('your-server-software-file-is-being-updated'),
					l10n('message'));
			me.portalUpgradeTimer();
			Ext.Ajax
				.request({
					disableCaching: true,
					url: upgradeUrl,
					timeout: 900000,
					success: function (response, options) {
						if (response.status == 200) {
							var xml = response.responseXML;
							var successVal = Ext.DomQuery
								.selectValue(
								"message @success",
								xml);

							me.killAllTheTasks();
							upgradeProgressWindow.hide();

							if (successVal == "true") {
								Ext.Msg
									.alert(
									l10n('message'),
									l10n('server-software-file-successfully-uploaded-updated'));
							} else {
								var errors = '';
								var errorNodes = Ext.DomQuery
									.select("errors", xml);
								if (successVal != null
									&& successVal == "false") {
									for (var i = 0; i < errorNodes.length; i++) {
										if (Ext.DomQuery
											.selectValue(
											"msg",
											errorNodes[i])
											.indexOf(
											'FORWARD') !== -1) {
											var path = Ext.DomQuery
												.selectValue(
												"msg",
												errorNodes[i])
												.split(":",
												2);
											testLocation(path[1]);
											return;
										}
										errors += Ext.DomQuery
											.selectValue(
											"msg",
											errorNodes[i])
											+ '<br>';
									}
								}

								if (errors != '') {
									Ext.Msg.alert(
										l10n('message'),
										'<br>' + errors,
										function () {
										});
								} else {
									Ext.Msg
										.alert(
										l10n('error'),
										l10n('cannot-update-server-software-uploaded-file'),
										function () {
										});
								}
							}
						}
					},

					failure: function (response, options) {
						if (response.status == 200) {

							//me.stopTaskManager();
							me.killAllTheTasks();
							upgradeProgressWindow.hide();

							var errors = '';
							var xml = response.responseXML;
							var errorNodes = Ext.DomQuery
								.select("errors", xml);

							for (var i = 0; i < errorNodes.length; i++) {
								if (Ext.DomQuery.selectValue(
									"msg", errorNodes[i])
									.indexOf('FORWARD') !== -1) {
									var path = Ext.DomQuery
										.selectValue(
										"msg",
										errorNodes[i])
										.split(":", 2);
									testLocation(path[1]);
									return;
								}
								errors += Ext.DomQuery
									.selectValue("msg",
									errorNodes[i])
									+ '<br>';
							}

							if (errors != '') {
								msg(l10n('message'), '<br>'
									+ errors, function () {
									});
							} else {
								msg(
									l10n('error'),
									l10n('cannot-update-server-software-uploaded-file'),
									function () {
									});
							}
						}
					}
				});

		},

		databaseBackup: function () {
			var scope = this;
			Ext.apply(Ext.form.VTypes, {
				confirmMatch: function (val, field) {
					if (field.initialPassField) {
						var pwd = passWindow
							.down('textfield[itemId='
							+ field.initialPassField
							+ ']');
						return (val == pwd.getValue());
					}
					return true;
				},
				confirmMatchText: l10n('password-not-match')
			});
			var passWindow = Ext
				.create(
				'Ext.window.Window',
				{
					title: 'Backup',
					modal: true,
					items: [{
						xtype: 'form',
						defaults: {
							msgTarget: 'under',
							width: 300,
							margin: 5
						},
						layout: {
							type: 'vbox',
							align: 'center'
						},
						border: 0,
						errorReader: new Ext.data.XmlReader(
							{
								success: '@success',
								record: 'field'
							}, ['id', 'msg']),
						ui: 'footer',
						cls: 'white-footer',
						items: [
							{
								xtype: 'textfield',
								inputType: 'password',
								allowBlank: false,
								fieldLabel: l10n('password'),
								name: 'password',
								enableKeyEvents: true,
								itemId: 'password',
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=confirmPassword]')
												.focus();
											event
												.preventDefault();
										}
									}
								}
							},
							{
								xtype: 'textfield',
								fieldLabel: l10n('confirm-password'),
								allowBlank: false,
								inputType: 'password',
								name: 'confirmPassword',
								itemId: 'confirmPassword',
								vtype: 'confirmMatch',
								initialPassField: 'password',
								enableKeyEvents: true,
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.shiftKey
											&& event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=password]')
												.focus();
											event
												.preventDefault();
										}
									}
								}
							},
							{
								xtype: 'checkbox',
								fieldLabel: 'Include User Images',
								name: 'thumbnailBCFlag',
								reference: 'thumbnailBCFlag',
								checked: false,
								listeners: {
									render: function (c) {
										Ext.QuickTips.register({
											target: c.getEl(),
											text: 'Enable the checkbox if you want to include thumbnail images in the backup'
										});
									}
								}

							}],
						buttonAlign: 'center',
						buttons: [{
							text: l10n('backup'),
							formBind: true,
							listeners: {
								/**
								 * @method afterRender
								 * @inheritdoc
								 * @return {void}
								 */

								click: function () {
									var pwdVal = passWindow
										.down(
										'textfield[name=password]')
										.getValue();
									var chkBxValue = passWindow
										.down(
										'checkbox[name=thumbnailBCFlag]')
										.getValue();
									Ext.MessageBox
										.show({
											msg: l10n('creating-database-backup'),
											width: 300,
											wait: true,
											waitConfig: {
												interval: 200
											}
										});
									Ext.Ajax
										.setTimeout(180000);
									Ext.Ajax
										.request({
											url: 'securedmaint/maintenance_backup_db.ajax',
											method: 'POST',
											params: {
												'password': pwdVal,
												'thumbnailBCFlag': chkBxValue
											},
											waitMsg: l10n('backing-up'),
											success: function (
												response,
												options) {
												Ext.MessageBox
													.hide();
												var xmlResponse = response.responseXML;
												var success = Ext.DomQuery
													.selectValue(
													'message @success',
													xmlResponse);

												if (success == "false") {
													var responseId = Ext.DomQuery
														.selectNode(
														'id',
														xmlResponse);
													var responseMsg = Ext.DomQuery
														.selectNode(
														'msg',
														xmlResponse);
													Ext.Msg
														.alert(
														responseId.textContent,
														responseMsg.textContent);
												} else {
													Ext.Msg
														.alert(
														l10n('success'),
														l10n('server-database-backup-successful'),
														function () {
															scope
																.lookupReference(
																'databaseGrid')
																.getView()
																.getSelectionModel()
																.deselectAll();
															scope
																.getViewModel()
																.getStore(
																"databaseGridStore")
																.load();
														});
												}
											},
											failure: function (
												form,
												action) {
												Ext.MessageBox
													.hide();
												var errors = '';
												if (action.failureType === Ext.form.action.Action.CONNECT_FAILURE) {
													Ext.Msg
														.alert(
														l10n('error'),
														l10n('status')
														+ ": "
														+ action.response.status
														+ ': '
														+ action.response.statusText);
												}
												if (action.failureType === Ext.form.action.Action.SERVER_INVALID) {
													Ext.Msg
														.alert(
														l10n('invalid'),
														action.result.errormsg);
												}
											}
										});
									passWindow
										.destroy();
								}
							}
						}]
					}]
				});
			passWindow.show();
		},

		databaseRestoreConfirmation: function () {
			var sel = this.lookupReference('databaseGrid')
				.getSelectionModel().getSelection();
			var scope = this;
			if (sel.length == 1) {
				Ext.MessageBox
					.confirm(
					l10n('confirmation'),
					l10n('do-you-really-want-to-restore-database-with-the-selected-backup')
					+ '<br/>'
					+ l10n('all-endpoint-software-need-to-be-re-uploaded-after-database-restored'),
					scope.databaseRestore, scope);
			} else {
				Ext.MessageBox
					.alert(
					l10n('error'),
					l10n('please-select-one-backup-file-to-restore'));
			}
		},

		databaseRestore: function (btn) {
			var scope = this;

			Ext.apply(Ext.form.VTypes, {
				confirmMatch: function (val, field) {
					if (field.initialPassField) {
						var pwd = passWindow
							.down('textfield[itemId='
							+ field.initialPassField
							+ ']');
						return (val == pwd.getValue());
					}
					return true;
				},
				confirmMatchText: l10n('password-not-match')
			});
			var passWindow = Ext
				.create(
				'Ext.window.Window',
				{
					title: 'Restore',
					modal: true,
					items: [{
						xtype: 'form',
						defaults: {
							msgTarget: 'under',
							width: 300,
							margin: 5
						},
						layout: {
							type: 'vbox',
							align: 'center'
						},
						border: 0,
						errorReader: new Ext.data.XmlReader(
							{
								success: '@success',
								record: 'field'
							}, ['id', 'msg']),
						ui: 'footer',
						cls: 'white-footer',
						items: [
							{
								xtype: 'textfield',
								inputType: 'password',
								allowBlank: false,
								fieldLabel: l10n('password'),
								name: 'password',
								enableKeyEvents: true,
								itemId: 'password',
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=confirmPassword]')
												.focus();
											event
												.preventDefault();
										}
									}
								}
							},
							{
								xtype: 'textfield',
								fieldLabel: l10n('confirm-password'),
								allowBlank: false,
								inputType: 'password',
								name: 'confirmPassword',
								itemId: 'confirmPassword',
								vtype: 'confirmMatch',
								initialPassField: 'password',
								enableKeyEvents: true,
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.shiftKey
											&& event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=password]')
												.focus();
											event
												.preventDefault();
										}
									}
								}
							}],
						buttonAlign: 'center',
						buttons: [{
							text: l10n('restore'),
							formBind: true,
							listeners: {
								click: function () {
									var pwdVal = passWindow
										.down(
										'textfield[name=password]')
										.getValue();
									Ext.MessageBox
										.show({
											msg: l10n('restoring'),
											progressText: l10n('backing.up'),
											width: 300,
											wait: true,
											waitConfig: {
												interval: 200
											}
										});
									Ext.Ajax
										.request({
											url: 'securedmaint/maintenance_restore_db.ajax',
											method: 'POST',
											params: {
												filename: sel[0]
													.get("fileName"),
												password: pwdVal
											},
											waitMsg: l10n('restoring-server-database'),
											success: function (
												response,
												options) {
												Ext.MessageBox
													.hide();
												var xmlResponse = response.responseXML;
												var success = Ext.DomQuery
													.selectValue(
													'message @success',
													xmlResponse);

												if (success == "false") {
													var responseId = Ext.DomQuery
														.selectNode(
														'id',
														xmlResponse);
													var responseMsg = Ext.DomQuery
														.selectNode(
														'msg',
														xmlResponse);
													Ext.Msg
														.alert(
														responseId.textContent,
														responseMsg.textContent);
												} else {
													Ext.Msg
														.alert(
														l10n('label-status'),
														l10n('server-database-is-successfully-restored')
														+ ' <br/> '
														+ l10n('please-re-upload-endpoint-software'),
														function () {
															scope
																.lookupReference(
																'databaseGrid')
																.getView()
																.getSelectionModel()
																.deselectAll();
															scope
																.getViewModel()
																.getStore(
																"databaseGridStore")
																.load();
														});
												}

											},
											failure: function (
												response,
												options) {
												Ext.MessageBox
													.hide();
												Ext.Msg
													.alert(
													l10n('error'),
													l10n('failure'));
											}
										});

									passWindow
										.destroy();
								}
							}
						}]
					}]
				});
			var sel = this.lookupReference('databaseGrid')
				.getSelectionModel().getSelection();
			if (btn == 'yes') {
				passWindow.show();
			}
		},

		databaseDeleteConfirmation: function () {
			var scope = this;
			var sel = this.lookupReference('databaseGrid')
				.getSelectionModel().getSelection();
			if (sel.length > 0) {
				Ext.MessageBox
					.confirm(
					l10n('confirmation'),
					l10n('do-you-really-want-to-delete-the-selected-backup'),
					scope.databaseDelete, scope);
			} else {
				Ext.MessageBox.alert(l10n('error'),
					l10n('please-select-one-file-to-delete'));
			}
		},

		databaseDelete: function (btn) {
			var sel = this.lookupReference('databaseGrid')
				.getSelectionModel().getSelection();
			var scope = this;
			if (btn == 'yes') {
				var jsonData = "[";
				for (var i = 0, len = sel.length; i < len; i++) {
					Ext.Ajax
						.request({
							url: 'securedmaint/maintenance_delete_backup.ajax',
							params: {
								fileName: sel[i]
									.get("fileName")
							},
							success: function () {
								scope.getViewModel().getStore(
									"databaseGridStore")
									.load();
							}
						});
				}
				// scope.getViewModel().getStore("databaseGridStore").load();
			}
		},

		databaseDefaultsConfrimation: function () {
			var scope = this;
			Ext.MessageBox
				.confirm(
				l10n('confirmation'),
				l10n('do-you-really-want-to-restore-all-data-to-factory-defaults'),
				scope.databaseDefaults, scope);
		},

		databaseDefaults: function (btn) {
			var scope = this;
			Ext.apply(Ext.form.VTypes, {
				confirmMatch: function (val, field) {
					if (field.initialPassField) {
						var pwd = passWindow
							.down('textfield[itemId='
							+ field.initialPassField
							+ ']');
						return (val == pwd.getValue());
					}
					return true;
				},
				confirmMatchText: l10n('password-not-match')
			});
			var passWindow = Ext
				.create(
				'Ext.window.Window',
				{
					title: 'Backup',
					modal: true,
					items: [{
						xtype: 'form',
						defaults: {
							msgTarget: 'under',
							width: 300,
							margin: 5
						},
						layout: {
							type: 'vbox',
							align: 'center'
						},
						border: 0,
						errorReader: new Ext.data.XmlReader(
							{
								success: '@success',
								record: 'field'
							}, ['id', 'msg']),
						ui: 'footer',
						cls: 'white-footer',
						items: [
							{
								xtype: 'textfield',
								inputType: 'password',
								allowBlank: false,
								fieldLabel: l10n('password'),
								name: 'password',
								enableKeyEvents: true,
								itemId: 'password',
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=confirmPassword]')
												.focus();
											event
												.preventDefault();
										}
									}
								}
							},
							{
								xtype: 'textfield',
								fieldLabel: l10n('confirm-password'),
								allowBlank: false,
								inputType: 'password',
								name: 'confirmPassword',
								itemId: 'confirmPassword',
								vtype: 'confirmMatch',
								initialPassField: 'password',
								enableKeyEvents: true,
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.shiftKey
											&& event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=password]')
												.focus();
											event
												.preventDefault();
										}
									}
								}
							}, {
								xtype: 'checkbox',
								fieldLabel: 'Include User Images',
								name: 'thumbnailBCFlag',
								reference: 'thumbnailBCFlag',
								checked: false,
								listeners: {
									render: function (c) {
										Ext.QuickTips.register({
											target: c.getEl(),
											text: 'Enable the checkbox if you want to include thumbnail images in the backup'
										});
									}
								}

							}],
						buttonAlign: 'center',
						buttons: [{
							text: l10n('backup'),
							formBind: true,
							listeners: {
								click: function () {
									var pwdVal = passWindow
										.down(
										'textfield[name=password]')
										.getValue();
									var chkBxValue = passWindow
										.down(
										'checkbox[name=thumbnailBCFlag]')
										.getValue();
									Ext.Ajax
										.request({
											url: 'securedmaint/maintenance_factory_defaults.ajax',
											method: 'POST',
											params: {
												'password': pwdVal,
												'thumbnailBCFlag': chkBxValue
											},
											waitMsg: l10n('restore-database-to-factory-defaults'),
											success: function (
												response,
												options) {

												var xmlResponse = response.responseXML;
												var success = Ext.DomQuery
													.selectValue(
													'message @success',
													xmlResponse);

												if (success == "false") {
													var responseId = Ext.DomQuery
														.selectNode(
														'id',
														xmlResponse);
													var responseMsg = Ext.DomQuery
														.selectNode(
														'msg',
														xmlResponse);
													Ext.Msg
														.alert(
														responseId.textContent,
														responseMsg.textContent);
												} else {
													Ext.Msg
														.alert(
														l10n('restore-database-success'),
														l10n('the-server-database-was-successfully-restored'),
														function () {
															scope
																.getViewModel()
																.getStore(
																"databaseGridStore")
																.load();
														});

												}

											},
											failure: function (
												response,
												options) {
												Ext.Msg
													.alert(
													l10n('error'),
													l10n('failure'));
											}
										});
									passWindow
										.destroy();
								}
							}
						}]
					}]
				});
			if (btn == 'yes') {
				passWindow.show();
			}
		},

		databaseDownload: function () {
			var sel = this.lookupReference('databaseGrid')
				.getSelectionModel().getSelection();
			if (sel.length == 0) {
				Ext.Msg.alert(l10n('error'),
					l10n('please-select-one-file-to-download'));
			} else {
				var location = 'securedmaint/maintenance_download_db.ajax?';
				location = location + "&f="
					+ sel[0].get("fileName");
				window.location = location;
			}
		},

		databaseUploadWin: function () {
			var scope = this;
			var systemUploadwin = "";
			if (!systemUploadwin) {
				systemUploadwin = Ext
					.create(
					'Ext.window.Window',
					{
						width: '100%',
						border: false,
						modal: true,
						title: l10n('uploading-backup-file'),
						closable: true,
						closeAction: 'destroy',
						resizable: false,
						width: 500,
						autoHeight: true,
						border: true,
						layout: 'fit',
						reference: 'databaseUploadWin',
						items: [{
							xtype: 'form',
							fileUpload: true,
							frame: false,
							border: false,
							modal: true,
							ui: 'footer',
							cls: 'white-footer',
							margin: 10,
							reference: 'databaseUploadForm',
							errorReader: Ext
								.create(
								'Ext.data.XmlReader',
								{
									record: 'field',
									model: Ext
										.create("SuperApp.model.settings.Field"),
									success: '@success'
								}),
							defaults: {
								anchor: '95%',
								allowBlank: false,
								msgTarget: 'side'
							},
							items: [
								{
									xtype: 'hiddenfield',
									name: csrfFormParamName,
									value: csrfToken
								},
								{
									xtype: 'fileuploadfield',
									emptyText: l10n('select-a-file'),
									fieldLabel: '',
									hideLabel: true,
									labelSeparator: '',
									name: 'DBarchive'
								}],
							buttonAlign: 'center',
							buttons: [{
								text: l10n('upload'),
								handler: scope.databaseUpload,
								scope: scope,
								formBind: true,
								disabled: true
							}]
						}]
					});
			}
			systemUploadwin.show();

		},

		databaseUpload: function (btn) {
			var scope = this;
			var systemUploadform = btn.up("form");
			systemUploadform
				.getForm()
				.submit(
				{
					url: 'securedmaint/maintenance_upload_db.ajax',
					waitTitle: l10n('uploading-backup-file'),
					waitMsg: l10n('your-file-is-being-uploaded'),
					success: function (form, action) {

						var xmlResponse = action.response.responseXML;
						var success = Ext.DomQuery
							.selectValue(
							'message @success',
							xmlResponse);

						if (success == "false") {
							var responseMsg = Ext.DomQuery
								.selectNode('msg',
								xmlResponse);
							Ext.Msg
								.alert(
								l10n('save-failed'),
								responseMsg.textContent);
						} else {
							Ext.Msg
								.alert(
								l10n('upload-success'),
								l10n('your-backup-file-was-successfully-uploaded'),
								function () {
									btn
										.up(
										"window")
										.close();
									scope
										.getViewModel()
										.getStore(
										"databaseGridStore")
										.load();
								});
						}
					},
					falure: function (form, action) {
						Ext.Msg.alert(l10n('error'),
							l10n('failure'));
					}
				});
		},

		getExternalDatabaseData: function () {
			var me = this, viewModel = me.getViewModel();

			Ext.Ajax.request({
				url: 'securedmaint/externaldb.ajax',
				method: 'GET',
				success: function (res) {
					var result = res.responseXML, view = me
						.getView().down('externaldatabase');

					viewModel.set("hostport", Ext.DomQuery
						.selectValue('hostport', result));
					viewModel.set("username", Ext.DomQuery
						.selectValue('username', result));
					viewModel.set("password", Ext.DomQuery
						.selectValue('password', result));
					view.setExtDBOptions(viewModel);

				},
				failure: function (res) {
				}
			});
		},

		onChangeExtDBType: function (rb, checked) {
			var me = this, checkValue = checked['dbSource'], form = me
				.lookupReference('externaldatabase'), viewModel = me
					.getViewModel();

			if (checkValue == "default") {
				viewModel.set('isLocalDB', true);
			} else {
				viewModel.set('isLocalDB', false);
			}
		},

		onTestExternalData: function () {
			var me = this;
			form = me.lookupReference('externaldatabase');

			if (form) {
				Ext.Ajax
					.request({
						url: 'securedmaint/testexternaldb.ajax',
						method: 'POST',
						params: form.getValues(),
						success: function (res) {
							var isSuccess = Ext.DomQuery
								.selectValue(
								'message @success',
								res.responseXML), btn = me
									.lookupReference('externaldatasave');

							if (isSuccess == "true") {
								Ext.Msg.alert(l10n('success'),
									l10n('test-passed'),
									function () {
										btn.enable();
									});
							} else {
								Ext.Msg.alert(l10n('failure'),
									l10n('test-failer'),
									function () {
										btn.disable();
									});
							}
						},
						failure: function (res) {
							var errors = '', btn = me
								.lookupReference('externaldatasave');
							/*
							 * if ((action.result != null) &&
							 * (!action.result.success)) { for
							 * (var i = 0; i <
							 * action.result.errors.length; i++) {
							 * errors +=
							 * action.result.errors[i].msg + '<br>'; } }
							 */
							if (errors != '') {
								Ext.Msg.alert(l10n('message'),
									'<br>' + errors,
									function () {
										btn.disable();
									});
							} else {
								Ext.Msg.alert(l10n('message'),
									l10n("timeout"),
									function () {
										btn.disable();
									});
							}
						}
					});
			}
		},

		onSaveExternalData: function () {
			var me = this;
			form = me.lookupReference('externaldatabase');

			if (form) {
				Ext.Ajax
					.request({
						url: 'securedmaint/saveexternaldb.ajax',
						method: 'POST',
						params: form.getValues(),
						success: function (res) {
							var isSuccess = Ext.DomQuery
								.selectValue(
								'message @success',
								res.responseXML), btn = me
									.lookupReference('externaldatasave');
							if (isSuccess == "true") {
								Ext.MessageBox
									.confirm(
									l10n('server-restart-is-required'),
									l10n('do-you-want-to-restart-server'),
									function (res) {
										if (res == 'yes') {
											me
												.restartrebootserver('securedmaint/maintenance_system_restart.ajax');
										}
									});
							} else {
								var responseXML = res.responseXML, errorsXML = responseXML
									.getElementsByTagName('errors');
								errors = '';
								if (errorsXML) {
									for (var i = 0; i < errorsXML.length; i++) {
										errors += errorsXML[i]
											.getElementsByTagName('msg')[0].textContent
											+ '<br>';
									}
								}
								if (errors != '') {
									Ext.Msg.alert(
										l10n('message'),
										'<br>' + errors,
										function () {
											btn.disable();
										});
								} else {
									Ext.Msg.alert(
										l10n('message'),
										l10n("timeout"),
										function () {
											btn.disable();
										});
								}
							}
						},
						failure: function (res) {
							var errors = '', btn = me
								.lookupReference('externaldatasave');
							if ((action.result != null)
								&& (!action.result.success)) {
								for (var i = 0; i < action.result.errors.length; i++) {
									errors += action.result.errors[i].msg
										+ '<br>';
								}
							}
							if (errors != '') {
								Ext.Msg.alert(l10n('message'),
									'<br>' + errors,
									function () {
										btn.disable();
									});
							} else {
								Ext.Msg.alert(l10n('message'),
									l10n("timeout"),
									function () {
										btn.disable();
									});
							}
						}
					});
			}
		},

		onClickRestartReboot: function (btn) {
			var me = this;
			me.restartrebootserver(btn.sysUrl, btn.isReboot);
		},
		killAllTheTasks: function () {
			Ext.TaskManager.stopAll();


		},
		portalUpgradeTimer: function () {
			var me = this, pageLoadStamp, store = Ext
				.create(
				'Ext.data.Store',
				{
					fields: [
						'serverStartedTimestamp',
						'serverStarted'],
					storeId: 'serverstartstamp',
					remoteSort: false,
					proxy: {
						type: 'ajax',
						url: 'serverstartedtime.ajax',
						reader: {
							type: 'xml',
							totalRecords: 'results',
							record: 'row'
						}
					},
					autoLoad: true
				}),

				doRefresh = function () {
					if (store) {
						store
							.reload({
								callback: function (rec) {
									if (isFiveOThree) {
										Ext.TaskManager
											.stop(refresher);
										Ext.Msg
											.alert(
											l10n("success"),
											l10n('web-server-restarted-portal-refresh-now'),
											function () {
												window.location = "home.html?lang=en";
											});
									}

								}
							});
						var xml = store.getRange()[0];
						var serverStartedTimestampText = xml
							.get('serverStartedTimestamp');
						if (serverStartedTimestampText != undefined) {
							var serverStartedTimestamp = parseInt(serverStartedTimestampText
								.trim());
							if (pageLoadStamp < serverStartedTimestamp) {
								Ext.TaskManager.stop(refresher);

								logoutSuper();

							}
						}
					}
				}, refresher = {
					interval: 30000,
					scope: me,
					run: doRefresh
				};
			store.load(
				{
					callback: function (rec) {
						var rec = rec[0];
						pageLoadStamp = parseInt(rec
							.get('serverStartedTimestamp'));
						Ext.TaskManager
							.start(refresher);
					}
				});


		},
		restartrebootserver: function (url, isReboot) {
			var me = this, pageLoadStamp, str = isReboot ? l10n('do-you-want-to-reboot-the-server')
				: l10n('do-you-want-to-restart-the-web-server'), store = Ext
					.create(
					'Ext.data.Store',
					{
						fields: [
							'serverStartedTimestamp',
							'serverStarted'],
						storeId: 'serverstartstamp',
						remoteSort: false,
						proxy: {
							type: 'ajax',
							url: 'serverstartedtime.ajax',
							reader: {
								type: 'xml',
								totalRecords: 'results',
								record: 'row'
							}
						},
						autoLoad: true
					})
					.load(
					{
						callback: function (rec) {
							var rec = rec[0];
							pageLoadStamp = parseInt(rec
								.get('serverStartedTimestamp'));
						}
					}), doRefresh = function () {
						if (store) {
							store
								.reload({
									callback: function (rec) {
										if (isFiveOThree) {
											Ext.TaskManager
												.stop(refresher);
											Ext.Msg
												.alert(
												l10n("success"),
												l10n('web-server-restarted-portal-refresh-now'),
												function () {
													window.location = "home.html?lang=en";
												});
										}

									}
								});
							var xml = store.getRange()[0];
							var serverStartedTimestampText = xml
								.get('serverStartedTimestamp');
							if (serverStartedTimestampText != undefined) {
								var serverStartedTimestamp = parseInt(serverStartedTimestampText
									.trim());
								if (pageLoadStamp < serverStartedTimestamp) {
									Ext.TaskManager.stop(refresher);
									Ext.Msg.alert(l10n("success"),
										l10n("restarted1"), function () {
											logoutSuper();
										});
								}
							}
						}
					}, refresher = {
						interval: 5000,
						scope: me,
						run: doRefresh
					};

			Ext.MessageBox
				.confirm(
				l10n("confirmation"),
				str,
				function (res) {
					if (res == 'yes') {
						var headerReboot = isReboot ? l10n("notification")
							: l10n('restarting-web-server'), infoReboot = isReboot ? ''
								: l10n('please-refresh-page-after-web-server-restarted');
						waitMsg = isReboot ? l10n('the-system-is-going-down-for-reboot-now')
							: l10n('web-server-is-being-restarted');
						if (!isReboot) {
							Ext.MessageBox.alert(
								headerReboot,
								infoReboot);
						}
						Ext.TaskManager
							.start(refresher);
						Ext.Ajax
							.request({
								url: url,
								method: 'POST',
								success: function (
									res) {
									Ext.Msg
										.show({
											title: headerReboot,
											msg: waitMsg,
											minWidth: 300,
											modal: true,
											icon: Ext.Msg.INFO,
											fn: function () {
											}
										});
								},
								failure: function (
									form,
									action) {
									Ext.TaskManager
										.stop(refresher);
									var errors = '';
									if ((action.result != null)
										&& (!action.result.success)) {
										for (var i = 0; i < action.result.errors.length; i++) {
											errors += action.result.errors[i].msg
												+ '<br>';
										}
									}
									if (errors != '') {
										Ext.Msg
											.alert(
											l10n('error'),
											'<br>'
											+ errors,
											function () {
											});
									} else {
										Ext.Msg
											.alert(
											l10n('error'),
											l10n('failed-to-restart-web-server'),
											function () {
											});
									}
								}
							});
					}
				}, this);
		},

		getStatusNotifyData: function () {
			var me = this;
			me.getView().lookupReference('statusnotifyForm').load({
				            url: 'securedmaint/statusnotification.ajax',
							method: 'GET'
				        });
		},
		onSaveStatusNotify: function () {
			var me = this, form = me.getView().getForm();
			var uName = form.getValues()['username'];
			if (form) {
				Ext.Ajax
					.request({
						url: 'securedmaint/savestatusnotification.ajax',
						params: form.getValues(),
						method: 'POST',
						success: function (res) {
							var isSuccess = Ext.DomQuery
								.selectValue(
								'message @success',
								res.responseXML)
							if (isSuccess == 'true') {
								Ext.Msg.alert(l10n('message'),
									l10n("saved"));
							} else {
								Ext.Msg
									.alert(
									l10n('message'),
									'"'
									+ Ext.String.htmlEncode(uName)
									+ '" '
									+ l10n('invalid-username-match-js-message'))
							}
						},
						failure: function (res) {
							var errorMsg = l10n('save-failed');
							var errors = '';
							if ((action.result != null)
								&& (!action.result.success)) {
								for (var i = 0; i < action.result.errors.length; i++) {
									errors += action.result.errors[i].msg
										+ '<br>';
								}
							}
							if (errors != '') {
								Ext.Msg.alert(l10n('error'),
									errorMsg += '<br>'
									+ errors,
									function () {
									});
							} else {
								Ext.Msg.alert(l10n('error'),
									l10n('timeout'),
									function () {
									});
							}
						}
					});
			}
		},

		cancelStatusNotify: function () {
			this.getView().getForm().load({
	            url: 'securedmaint/statusnotification.ajax',
                method : 'GET'
	        });
		},

		getEventsNotifyData : function() {
			var me = this;
			me.getView().lookupReference('eventsNotificationServerForm').load({
	            url: 'geteventsnotificationservers.ajax',
	            method : 'GET'
	        });
		},

		saveEventsNotifyServer : function() {
			var me = this, form = me.getView().getForm();
			if (form) {
				Ext.Ajax
					.request({
						url: 'saveeventsnotificationservers.ajax',
						headers: { 'Content-Type': 'application/json' },
						params: Ext.JSON.encode(form.getValues()),
						method: 'POST',
						success: function (res) {
							var jsonResp = Ext.util.JSON.decode(res.responseText);
							Ext.Msg.alert(l10n('message'),
									l10n("saved"));
						},
						failure: function (res) {
							var jsonResp = Ext.util.JSON.decode(res.responseText);
							Ext.Msg.alert(l10n('error'),
									l10n("save-failed"));
						}
					});
			}
		},

		getSysLogData: function () {
			var me = this;
			me.getView().lookupReference('sysLogForm').load({
				            url: 'securedmaint/getsyslogconfig.ajax',
				        });
			/*var me = this, viewModel = me.getViewModel(), remoteLogRadio = me
					.lookupReference('syslogremotelogging'), encrptCheck = me
					.lookupReference('stunnelConfig'), sysLogStore = viewModel
					.getStore('sysLogFormStore');
			sysLogStore.load({
				callback : function(recs) {
					var rec = recs[0];
					if (recs.length) {
						remoteLogRadio.setValue({
							syslogConfig : rec
									.get('remote_logging')
						});
						if (rec.get('remote_logging') == "on") {
							viewModel.set('syslogflag', 'on');
							viewModel.set('ipaddress', rec
									.get('ip_address'));
							viewModel.set('ipdisabled', false);
							viewModel.set('portdisabled', false);

							encrptCheck
									.setValue(rec.get('stunnel'));
							viewModel.set('port', rec.get('port'));
						} else {
							viewModel.set('syslogflag', 'off');
							viewModel.set('ipaddress', '');
							viewModel.set('ipdisabled', true);
							viewModel.set('portdisabled', true);
							encrptCheck.setValue("off");
							viewModel.set('port', "");
						}

					}
				}
			});*/
		},

		cancelSyslog: function () {
			this.getView().getForm().reset();
		},

		cancelEventsNotifyServer : function() {
			var form = this.getView().getForm();
			form.load({
	            url: 'geteventsnotificationservers.ajax',
	            method : 'GET'
	        });
		},

		sysLogSaveForm: function () {

			Ext.Ajax.request({
				url: 'securedmaint/savesyslogconfig.ajax',
				method: 'POST',
				waitMsg: l10n('saving'),
				params: this.getView().getForm().getValues(),
				success: function (response, options) {
					var xmlResponse = response.responseXML;
					var success = Ext.DomQuery.selectValue(
						'message @success', xmlResponse);
					if (success == "false") {
						var responseId = Ext.DomQuery.selectValue(
							'id', xmlResponse);
						var responseMsg = Ext.DomQuery.selectValue(
							'msg', xmlResponse);
						Ext.Msg.alert(responseId, responseMsg);
					} else {
						Ext.Msg.alert(l10n("success"),
							l10n('syslogconfig-saved-success'));
					}
				},
				failure: function (form, action) {
					Ext.Msg.alert(l10n('error'), l10n('failure'));
				}
			});
		},

		onChangeRemoteLogging: function (rg, newVal) {
			var me = this, viewModel = me.getViewModel(), sysLogPanel = me
				.lookupReference('sysLogPanel');

			if (newVal["syslogConfig"] == "on") {
				sysLogPanel.show();
				viewModel.set('ipdisabled', false);
				viewModel.set('portdisabled', false);
				viewModel.set('syslogflag', 'on');
				return;
			}
			viewModel.set('syslogflag', 'off');
			viewModel.set('ipdisabled', true);
			viewModel.set('portdisabled', true);
			sysLogPanel.hide();
		},

		getVidyoPortalLogUrl: function (value) {
			var strReturn = 'securedmaint/maintenance_portal_logs_export.ajax?';
			// strReturn += 'startDate=' +
			// this.getAuditParamValue(this.lookupReference('startDate'));
			// strReturn += 'startDate=' +
			// formatDate(this.lookupReference('startDate').getValue());
			// strReturn += '&endDate=' +
			// formatDate(this.lookupReference('endDate').getValue());
			strReturn += 'password=' + value;
			return strReturn;
		},

		getAuditLogUrl: function () {
			var strReturn = 'securedmaint/maintenance_audit_export.ajax?', formatDate = function (
				date) {
				return Ext.Date.format(date, "Y-m-d");
			};
			// strReturn += 'startDate=' +
			// this.getAuditParamValue(this.lookupReference('startDate'));
			strReturn += 'startDate='
				+ formatDate(this.lookupReference('startDate')
					.getValue());
			strReturn += '&endDate='
				+ formatDate(this.lookupReference('endDate')
					.getValue());
			return strReturn;
		},

		getAuditLogCountUrl: function () {
			var strReturn = 'securedmaint/maintenance_audit_export_count.ajax?', formatDate = function (
				date) {
				return Ext.Date.format(date, "Y-m-d");
			};
			strReturn += 'startDate='
				+ formatDate(this.lookupReference('startDate')
					.getValue());
			strReturn += '&endDate='
				+ formatDate(this.lookupReference('endDate')
					.getValue());
			return strReturn;
		},

		auditExportBtn: function () {

			var scope = this;
			Ext.Ajax
				.request({
					url: this.getAuditLogCountUrl(),
					waitMsg: l10n('please-wait'),
					success: function (result, request) {
						var errors = '';
						var xml = result.responseXML;
						if (xml != null) {
							var msgNode = xml
								.getElementsByTagName("message");
							if (msgNode != null) {
								message = msgNode[0];
								var success = message
									.getAttribute("success");
								if (success == "false") {
									Ext.Msg
										.alert(
										l10n('failure'),
										xml
											.getElementsByTagName("msg")[0].textContent);
								} else {
									try {
										Ext
											.destroy(scope
												.lookupReference('downloadAuditLog'));
									} catch (ignored) {
									}
									Ext.DomHelper
										.append(
										document.body,
										{
											tag: 'iframe',
											reference: 'downloadAuditLog',
											frameBorder: 0,
											width: 0,
											height: 0,
											css: 'display:none; visibility:hidden; height:0px;',
											src: scope
												.getAuditLogUrl()
										});
								}
							}
						}
					},
					failure: function (form, action) {
						Ext.Msg.alert(l10n('failure'),
							l10n('request-failed'));
					}
				});
		},
		vidyoPortalLogsExportBtn: function () {
			var me = this;
			try {
				Ext.destroy(scope
					.lookupReference('downloadAuditLog'));
			} catch (ignored) {
			}
			Ext.apply(Ext.form.VTypes, {
				confirmMatch: function (val, field) {
					if (field.initialPassField) {
						var pwd = passWindow
							.down('textfield[itemId='
							+ field.initialPassField
							+ ']');
						return (val == pwd.getValue());
					}
					return true;
				},
				confirmMatchText: l10n('password-not-match')
			});
			var passWindow = Ext
				.create(
				'Ext.window.Window',
				{
					title: 'VidyoPortal Logs',
					modal: true,
					items: [{
						xtype: 'form',
						defaults: {
							msgTarget: 'under',
							width: 300,
							margin: 5
						},
						layout: {
							type: 'vbox',
							align: 'center'
						},
						border: 0,
						errorReader: new Ext.data.XmlReader(
							{
								success: '@success',
								record: 'field'
							}, ['id', 'msg']),
						ui: 'footer',
						cls: 'white-footer',
						items: [
							{
								xtype: 'label',
								text: l10n('zip-password-desc')
							},
							{
								xtype: 'textfield',
								inputType: 'password',
								fieldLabel: l10n('password'),
								name: 'password',
								enableKeyEvents: true,
								itemId: 'password',
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=confirmPassword]')
												.focus();
											event
												.preventDefault();
										}
									},
									blur: function () {
										if (passWindow
											.down('textfield[name=password]').value != "") {
											passWindow
												.down('textfield[name=confirmPassword]').allowBlank = false;
											passWindow
												.down(
												'form')
												.getForm()
												.isValid();
										} else {
											passWindow
												.down('textfield[name=confirmPassword]').allowBlank = true;
											passWindow
												.down(
												'form')
												.getForm()
												.isValid();
										}
									}
								}
							},
							{
								xtype: 'textfield',
								fieldLabel: l10n('confirm-password'),
								inputType: 'password',
								name: 'confirmPassword',
								itemId: 'confirmPassword',
								vtype: 'confirmMatch',
								initialPassField: 'password',
								enableKeyEvents: true,
								listeners: {
									keydown: function (
										text,
										event) {
										if (event.shiftKey
											&& event.keyCode == 9) {
											passWindow
												.down(
												'textfield[name=password]')
												.focus();
											event
												.preventDefault();
										}
									},
									blur: function () {
										if (passWindow
											.down('textfield[name=confirmPassword]').value != "") {
											passWindow
												.down('textfield[name=password]').allowBlank = false;
											passWindow
												.down(
												'form')
												.getForm()
												.isValid();
										} else {
											passWindow
												.down('textfield[name=password]').allowBlank = true;
											passWindow
												.down(
												'form')
												.getForm()
												.isValid();
										}
									}
								}
							}],
						buttonAlign: 'center',
						buttons: [{
							text: l10n('export'),
							formBind: true,
							listeners: {
								click: function () {
									var value = passWindow
										.down(
										'textfield[name=password]')
										.getValue();
									Ext.DomHelper
										.append(
										document.body,
										{
											tag: 'iframe',
											reference: 'downloadAuditLog',
											frameBorder: 0,
											width: 0,
											height: 0,
											css: 'display:none; visibility:hidden; height:0px;',
											src: me
												.getVidyoPortalLogUrl(value)
										});
									passWindow
										.destroy();
								}
							}
						}]
					}]
				});
			passWindow.show();
		},
		getSystemLogsData: function () {
			var me = this, viewModel = me.getViewModel();
			viewModel.getStore('sysUpgradeLogGridStore').load(
				{
					callback: function (recs) {
						viewModel.set('hideSysUpgradeLogGrid',
							recs.length ? false : true);
					}
				});
		},

		getCdrAccessData: function () {
			var me = this, viewModel = me.getViewModel(), cdrAccessStore = viewModel
				.getStore('cdrAccessStore');
			cdrAccessStore
				.load({
					callback: function (recs) {
						var rec = recs[0];
						if (recs.length) {
							viewModel.set("fipsEnabled", (rec.get('fipsEnabled') == "true" || rec.get('fipsEnabled') == true) == true ? true : false);
							viewModel.set('enabled', rec
								.get('enabled'));
							viewModel.set('id', rec.get('id'));
							viewModel.set('password', rec
								.get('password'));
							viewModel.set('ip', rec.get('ip'));
							viewModel.set('allowdelete', rec
								.get('allowdelete'));
							viewModel.set('allowdeny', rec
								.get('allowdeny'));
							/*
							 * if(rec.get('allowdeny') ==
							 * 'false') { setTimeout(function() {
							 * me.lookupReference('id').disable();
							 * me.lookupReference('password').disable();
							 * me.lookupReference('ip').disable();
							 * me.lookupReference('cdrAllowDelete').disable(); },
							 * 300); } else {
							 * setTimeout(function() {
							 * me.lookupReference('id').enable();
							 * me.lookupReference('password').enable();
							 * me.lookupReference('ip').enable();
							 * me.lookupReference('cdrAllowDelete').enable(); },
							 * 300); }
							 */
						}
					}
				});
		},
		cdrAccessSave: function () {
			var scope = this;
			this
				.lookupReference("cdrAccessForm")
				.getForm()
				.submit(
				{
					url: 'securedmaint/maintenance_cdr_save.ajax',
					params: {
						format: '1'
					},
					waitMsg: l10n('please-wait'),
					success: function (form, action) {
						var errors = '';
						var errorsId = "";
						if ((action.result != null)
							&& (action.result.errors != null)) {
							var errorsId = action.result.errors[0].id;
							var errors = action.result.errors[0].msg;
							for (var i = 1; i < action.result.errors.length; i++) {
								errorsId += "/"
									+ action.result.errors[i].id;
								errors += "<br>"
									+ action.result.errors[i].msg;
							}
						}
						if (errors != '') {
							Ext.Msg
								.alert(
								errorsId,
								errors,
								scope
									.getCdrAccessData());
						} else {
							Ext.Msg
								.alert(
								l10n("success"),
								l10n('cdr-changes-have-been-saved'),
								scope
									.getCdrAccessData());
						}
					},
					failure: function (form, action) {
						Ext.Msg.alert(l10n('error'),
							l10n('request-failed'));
					}
				});
		},
		oneTenant: function (obj, ev, eo) {
			if (obj.inputValue == 'one' && obj.rawValue == true) {
				this.lookupReference("exportPurgeCombo").enable();
				this.lookupReference('oneallvalue').setValue('one');
			}
		},
		allTenants: function (obj, ev, eo) {
			if (obj.inputValue == 'all' && obj.rawValue == true) {
				this.lookupReference("exportPurgeCombo").disable();
				this.lookupReference('oneallvalue').setValue('all');
			}
		},
		invalidDate: function () {
			this.lookupReference("export").disable();
			this.lookupReference("purge").disable();
		},
		validDate: function () {
			if (this.lookupReference('enddate').isValid()) {
				this.lookupReference("export").enable();
				this.lookupReference("purge").enable();
			}
		},
		cdrExpPurgeComboLoad: function (store, records, successful,
			eOpts) {
			this.lookupReference("exportPurgeCombo").setValue(
				records[0].data.tenantName);
		},
		getCDRParamValue: function (obj) {
			var strReturn = '';
			try {
				strReturn = obj.getRawValue().trim();
			} catch (err) {
				strReturn = '';
			}
			return strReturn;
		},
		getExportCdrUrl: function () {
			var strReturn = "securedmaint/maintenance_cdr_export.ajax?";
			strReturn += 'oneall='
				+ this.lookupReference('oneallvalue')
					.getValue();
			if (this.lookupReference('oneallvalue').getValue() == 'one') {
				strReturn += '&tenantName='
					+ this
						.getCDRParamValue(this
							.lookupReference("exportPurgeCombo"));
			}
			strReturn += '&dateperiod='
				+ this.lookupReference('dateperiodvalue')
					.getValue();
			if (this.lookupReference('dateperiodvalue').getValue() == 'range') {
				strReturn += '&startdate='
					+ this.getCDRParamValue(this
						.lookupReference('startdate'));
				strReturn += '&enddate='
					+ this.getCDRParamValue(this
						.lookupReference('enddate'));
			}
			return strReturn;
		},
		getExportCdrUrlCount: function () {
			var strReturn = "securedmaint/cdrexportcount.ajax?";
			strReturn += 'oneall='
				+ this.lookupReference('oneallvalue')
					.getValue();
			if (this.lookupReference('oneallvalue').getValue() == 'one') {
				strReturn += '&tenantName='
					+ this
						.getCDRParamValue(this
							.lookupReference("exportPurgeCombo"));
			}
			strReturn += '&dateperiod='
				+ this.lookupReference('dateperiodvalue')
					.getValue();
			if (this.lookupReference('dateperiodvalue').getValue() == 'range') {
				strReturn += '&startdate='
					+ this.getCDRParamValue(this
						.lookupReference('startdate'));
				strReturn += '&enddate='
					+ this.getCDRParamValue(this
						.lookupReference('enddate'));
			}
			return strReturn;
		},
		exportBtnHandler: function () {
			var scope = this;
			Ext.Ajax
				.request({
					url: this.getExportCdrUrlCount(),
					method: 'GET',
					waitMsg: l10n('please-wait'),
					success: function (result, request) {

						var errors = '';
						var xml = result.responseXML;
						if (xml != null) {
							var msgNode = xml
								.getElementsByTagName("message");
							if (msgNode != null) {
								message = msgNode[0];
								var success = message
									.getAttribute("success");
								if (!success) {
									Ext.Msg
										.alert(
										l10n('error'),
										l10n('export-failed'));
								}
							}
						}

						scope.lookupReference('export')
							.disable();
						try {
							Ext.destroy(Ext
								.get('downloadIframe'));
						} catch (e) {
						}

						Ext.DomHelper
							.append(
							document.body,
							{
								tag: 'iframe',
								id: 'downloadIframe',
								frameBorder: 0,
								width: 0,
								height: 0,
								css: 'display:none; visibility:hidden; height:0px;',
								src: scope
									.getExportCdrUrl()
							});
						setTimeout(function () {
							scope.lookupReference('export')
								.enable();
						}, 10000);
					},
					failure: function (form, action) {
						Ext.Msg
							.alert(
							localizedSettingPage['error'],
							localizedSettingPage['exportfailed']);
					}
				});
		},
		doCDRPurge2: function (btn) {
			var scope = this;
			console.log(scope);
			if (btn == "yes") {

				scope
					.lookupReference("cdrExportPurgeForm")
					.submit(
					{
						url: 'securedmaint/maintenance_cdr_purge.ajax',
						success: function (form, action) {

							var errors = '';
							if ((action.result != null)
								&& (action.result.errors != null)) {
								for (var i = 0; i < action.result.errors.length; i++) {
									errors += action.result.errors[i].msg
										+ '<br>';
								}
							}
							if (errors != '') {
								Ext.Msg.alert("",
									errors);
							} else {
								Ext.Msg
									.alert(
									l10n('success'),
									l10n('cdr-records-has-been-deleted'));
							}
						},
						failure: function (form, action) {
							Ext.Msg
								.alert(
								l10n('error'),
								l10n('purge-failed'));
						}
					});
			}
		},
		purgeBtnHandler: function () {
			var me = this;
			var dateperiod = this
				.lookupReference("dateperiodvalue").getValue();
			if (dateperiod == 'range') {
				Ext.MessageBox
					.confirm(
					l10n('confirmation'),
					l10n('are-you-sure-you-want-to-permanently-delete-all-cdr-records-between')
					+ this.lookupReference(
						'startdate')
						.getRawValue()
					+ l10n('and')
					+ this.lookupReference(
						'enddate')
						.getRawValue(),
					this.doCDRPurge2, me);
			} else {
				Ext.MessageBox
					.confirm(
					l10n('confirmation'),
					l10n('all-cdr-records-msgbox-delete-confirm-messg'),
					this.doCDRPurge2, me);
			}
		},
		onManageTenantsRowClick: function (grid, record, tr, rowIndex, e, eOpts) {
			if (e.target.className == "selectable-tenant-url") {

				window.open(window.location.protocol + "//" + record.get('tenantURL') + "/admin", '_blank');
			}
		},
		onClickApproveCert: function (button, event, eOpts) {
			var me = this, viewModel = me.getViewModel();
			var rec = button.getWidgetRecord();

			tenantId = rec.get('tenantID');


			Ext.Ajax
				.request({
					url: 'securedmaint/approve_tenant_cert.ajax',
					params: {
						tenantId: tenantId
					},
					waitMsg: l10n('please-wait'),
					success: function (resp) {
						var result = resp.responseXML,
							isSuccess = Ext.DomQuery.selectValue('message @success', result);

						if (isSuccess == "false") {
							toastPopUp('Failed', 'Approval failed.', 'warning');
						} else {

							toastPopUp('Success', 'Approved successfully.Web Server restart is required inorder to complete the request');



							viewModel.getStore('pkiTenantsPageStore').load(
								{
									callback: function (recs) {

										if (recs.length == 0) {

											try {
												var notificationObj = Ext.ComponentQuery.query('button[reference=restartnotification]')[0];
												notificationObj.setText(l10n('web-server-restart-is-pending'));
											}
											catch (e) {
												console.log(e);

											}
										}

									}
								});
						}
					},
					failure: function (form, action) {
						Ext.Msg.alert(l10n('failure'),
							l10n('request-failed'));
					}
				});


		},
		onClickLogFQDNSave: function () {
			var me = this, form = me.lookupReference('logfqdnform');
			if (form && form.isValid()) {

				var logfqdn=form.getForm().findField('logfqdn').getValue();
	var confLogAggrWindow = Ext
					.create(
					'Ext.window.Window',
					{
						 title : l10n('confirmation'),
						modal: true,
						items: [{
							xtype: 'form',
							defaults: {
								msgTarget: 'under',
								width: 500,
								margin: 5
							},
							layout: {
								type: 'vbox',
								align: 'center'
							},
							border: 0,
							errorReader: new Ext.data.XmlReader(
								{
									success: '@success',
									record: 'field'
								}, ['id', 'msg']),
							ui: 'footer',
							cls: 'white-footer',
							items: [

								{
									xtype: 'checkbox',
									fieldLabel:l10n('log-aggregation-enable-fieldlabel'),
									name: 'enableLogAggr',
									labelWidth:300,
									reference: 'enableLogAggr',
									checked: false,
									listeners: {
										render: function (c) {
											Ext.QuickTips.register({
												target: c.getEl(),
												text: l10n('log-aggregation-enable-all-tenants-tooltip'),
											});
										}
									}

								},{
									xtype:'label',
									html:l10n('log-aggregation-enalbe-note'),
								}],
							buttonAlign: 'center',
							buttons: [{
								text: l10n('apply'),
								formBind: true,
								listeners: {
									/**
									 * @method afterRender
									 * @inheritdoc
									 * @return {void}
									 */

									click: function () {
										var checkBoxVal=confLogAggrWindow
										.down(
										'checkbox[name=enableLogAggr]')
										.getValue();
								 me.saveLogAggregationAjax( form,logfqdn,checkBoxVal);
										confLogAggrWindow
											.close();
									}
								}
							}, {
									text: l10n('cancel'),
									handler: function () {
										confLogAggrWindow
											.close();
									}
								}]
						}]
					});
				if(Ext.isEmpty(logfqdn)){
					Ext.Msg.show({
                    title : l10n('confirmation'),
                    message :l10n('log-aggregation-disable-note'),
                    buttons : Ext.Msg.YESNO,
                    icon : Ext.Msg.QUESTION,
					 buttonText:{ 
                yes:l10n('apply'),
                no: l10n('cancel'),
            },
                    fn : function(btn) {
                        if (btn === 'yes') {
                 me.saveLogAggregationAjax(form,logfqdn);
                        }
                    }
                });
				}else{
					confLogAggrWindow.show();
				}
			
			
			}
		},

       saveLogAggregationAjax:function( form,logfqdn,checkboxVal)
	   {
		   var values = form.getForm().getValues();
		 
		   Ext.Ajax
			   .request({
				   url: 'securedmaint/maintenance_savelogfqdn.ajax',
				   params: {
					   'logfqdn': logfqdn,
					   'enableLogAggr': checkboxVal
				   },
				   method: 'POST',
				   success: function (res) {
					   var isSuccess = Ext.DomQuery
						   .selectValue(
						   'message @success',
						   res.responseXML)
					   if (isSuccess == 'true') {
						   form.getForm().setValues(values);
						   Ext.Msg.alert(l10n('message'),
							   l10n("saved"));
					   } else {
						   Ext.Msg.alert(l10n('error'),
							   l10n("failed"));
					   }
				   },
				   failure: function (res) {
					   Ext.Msg.alert(l10n('error'),
						   l10n("failed"));
				   }
			   });
	   },
		loadLogFQDNForm: function () {
			var me = this, form = me.lookupReference('logfqdnform');

		Ext.create('Ext.data.Store', {
          fields: ['logfqdn'],
        storeId :'fqdnlogstoreinternal',    
    
    proxy :{
        type :'ajax',
        actionMethods:  { read: "GET"},
        url :'securedmaint/maintenance_logfqdn.ajax',
        reader: {
            type: 'xml',
            totalRecords: 'results',
            record: 'row',
            rootProperty:'dataset'
        }
    },
        autoLoad : false
        }).load({
            callback : function(rec) {
                var rec = rec[0];
              form.getForm().loadRecord(rec);
            }
        });


			
			


		},
		resetFQDNLog: function () {
			var me = this, form = me.lookupReference('logfqdnform');
			form.getForm().reset();
		},
		datefieldRadio: function (cb, checked) {

			if (checked) {
				this.lookupReference('startdate').enable();
				this.lookupReference('enddate').enable();
				this.lookupReference('dateperiodvalue').setValue(
					'range');
			}

		}
				});
