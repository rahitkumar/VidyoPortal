/**
 * @class CustomizationController
 */
Ext
		.define(
				'AdminApp.view.settings.customization.CustomizationController',
				{
					extend : 'Ext.app.ViewController',
					alias : 'controller.CustomizationController',

					/** **** CLICK EVENTS FUNCTIONS *************** */

					/***********************************************************
					 * @function onClickSaveAboutInfo
					 */
					onClickSaveAboutInfo : function(btn) {
						var me = this, form = btn.up('aboutinfo'), params = form
								.getForm().getValues();

						if (btn.type == "default") {
							params["followSuper"] = true;
							Ext.Msg
									.confirm(
											l10n('confirmation'),
											l10n('you-are-about-to-replace-customized-about-info-with-default-content-continue'),
											function(res) {
												if (res == "yes") {
													me
															.saveView(
																	params,
																	'saveaboutinfo.ajax',
																	function() {
																		me
																				.getAboutInfoData();
																	});
												}
											});
							return;
						}
						me.saveView(params, 'saveaboutinfo.ajax', function() {
							me.getAboutInfoData();
						});
					},

					/***********************************************************
					 * @function onClickSaveSupportInfo
					 */
					onClickSaveSupportInfo : function(btn) {
						var me = this, form = btn.up('supportinfo'), params = form
								.getForm().getValues();

						if (btn.type == "default") {
							params["followSuper"] = true;
							Ext.Msg
									.confirm(
											l10n('confirmation'),
											l10n('you-are-about-to-replace-customized-contact-info-with-default-content-continue'),
											function(res) {
												if (res == "yes") {
													me
															.saveView(
																	params,
																	'savecontactinfo.ajax',
																	function() {
																		me
																				.getSupportInfoData();
																	});
												}
											});
							return;
						}

						me.saveView(params, 'savecontactinfo.ajax', function() {
							me.getSupportInfoData();
						});
					},

					onClickSaveNotification : function(btn) {
						var me = this, form = btn.up('notification'), radioGroup = form
								.down("notificationFlagGroup"), params = form
								.getForm().getValues();

						if (form && form.getForm().isValid()) {
							delete params["tenantID"];
							me.saveView(params, 'savenotification.ajax',
									function() {
										me.getNotificationData();
									});
						}
					},

					onClickDefaultNotification : function(btn) {
						var me = this, form = btn.up('notification');

						form.getForm().reset();
						me.getNotificationData();
					},

					onClickSaveInviteText : function(btn) {
						var me = this, grid = me.lookupReference('dialInGrid'), form = btn
								.up('invitetext'), viewModel = me
								.getViewModel(), gridStore = viewModel
								.getStore('DialInStore'), recs = gridStore, successCount = 0, recStr = "", count = recs
								.getCount(), responseCount = 0,isGridValid=1;

						var gridParams = {};
						var gridObj = {
							'dialInList' : gridParams
						};
						if (count || grid.gridDirtyFlag) {
							recs.each(function(rec, index) {
								if(!rec.get('countryID') ){
									
													isGridValid=0;
									return false ;
								}
								recStr += rec.get('countryID') + ":"
										+ rec.get("dialInNumber") + ":"
										+ rec.get("dialInLabel");
								if ((count - 1) !== index) {
									recStr += "|";
								}

							});
						}
						if(!isGridValid){
								Ext.Msg
											.alert(
													l10n("error"),
													l10n("invalid-entry-in-the-grid"),
													function() {
													});

													return;

						}
						if (form ) {
							var params = form.getForm().getValues();
							if (count || grid.gridDirtyFlag) {
								params["dialInNumbersGridChanged"] = true;
								params["dialInNumbers"] = recStr;
							}

							if (btn.type == "default") {
								params["followSuper"] = true;
								Ext.Msg
										.confirm(
												l10n('confirmation'),
												l10n('you-are-about-to-replace-customized-about-info-with-default-content-continue'),
												function(res) {
													if (res == "yes") {
														me
																.saveView(
																		params,
																		'saveinvitationsetting.ajax',
																		function() {
																			me
																					.getInviteTextData();
																		});
													}
												});
								return;
							} else {
								var temp = params.invitationEmailContent;
								if (temp != null
										&& (temp.match(/\[ROOMLINK\]/g) || []).length > 1) {
									Ext.Msg
											.alert(
													l10n("error"),
													l10n('roomlink-cannot-be-more-than-once'),
													function() {
													});
									return;
								}
							}
							me.saveView(params, 'saveinvitationsetting.ajax',
									function() {
										me.getInviteTextData();
									});
						}
					},

					onClickViewCustomizeLogo : function(btn) {
						var me = this, viewModel = me.getViewModel(), imageUrl = undefined;

						switch (btn.image) {
						case 'sp':
							imageUrl = viewModel.getData()["splogonameOne"]
									+ "?t=" + (new Date()).getTime();
							break;
						case 'up':
							imageUrl = viewModel.getData()["uplogonameTwo"]
									+ "?t=" + (new Date()).getTime();
							break;
						case 'vd':
							imageUrl = viewModel.getData()["vdlogonameThree"]
									+ "?t=" + (new Date()).getTime();
							break;
						}

						window.open(imageUrl, "_blank");
					},

					onClickUploadCustomizeLogo : function(btn) {
						var me = this, form = me.getView(), success = me.getSuperAdminLogoData;

						switch (btn.successUrl) {
						case "sp":
							success = function() {
								me.getSuperAdminLogoData();
								form.getForm().isValid();
							};
							break;
						case "up":
							success = function() {
								me.getPortalLogoData();
								form.getForm().isValid();
							};
							break;
						case "vd":
							success = function() {
								me.getVidyoDesktopData();
								form.getForm().isValid();
							};
							break;
						}

						if (form) {
							form
									.getForm()
									.submit(
											{
												url : btn.ajaxUrl,
												waitMsg : 'saving',
												success : function(form, action) {
													var xmlResponse = action.response.responseXML;
													var issuccess = Ext.DomQuery
															.selectValue(
																	'message @success',
																	xmlResponse);
													if (issuccess == "false") {
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
																		l10n('message'),
																		l10n('the-customized-logo-image-is-successfully-saved'),
																		function() {
																		});
														Ext.Ajax
																.request(
																		{
																			url : btn.reloadUrl,
																			success : success
																		}, me);
													}
												},
												failure : function(form, action) {
													Ext.Msg
															.alert(
																	l10n("failure"),
																	l10n("request-failed"));
												}
											});
						}
					},

					onClickRemoveCustomizeLogo : function(btn) {
						var me = this, form = me.lookupReference(btn.formName), confirmMsg = "", success = function() {
							switch (btn.successUrl) {
							case "sp":
								me.getSuperAdminLogoData();
								confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-super-admin-portal');
								break;
							case "up":
								me.getPortalLogoData();
								confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-user-portal');
								break;
							case "vd":
								me.getVidyoDesktopData();
								confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo');
								break;
							}
						};
						switch (btn.successUrl) {
						case "sp":
							confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-super-admin-portal');
							break;
						case "up":
							confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo-for-user-portal');
							break;
						case "vd":
							confirmMsg = l10n('are-you-sure-you-want-to-remove-current-logo');
							break;
						}
						Ext.Msg
								.confirm(
										l10n('confirmation'),
										confirmMsg,
										function(res) {
											if (res == "yes") {
												Ext.Ajax
														.request({
															url : btn.removeUrl,
															method : 'POST',
															success : function() {
																Ext.Msg
																		.alert(
																				l10n('message'),
																				l10n('the-customized-logo-image-is-successfully-removed'),
																				function() {
																				},
																				me);
																success();
															},
															failure : function() {

															}
														});
											}
										}, me);
					},

					onClickChangeLocationGP : function(btn) {
						var me = this, overlay = Ext.widget(
								'guideuploadoverlay', {
									guideType : btn.guideType,
									comboValue : btn.comboValue,
									parentview : me.view
								});

						if (overlay) {
							overlay.show();
						}
					},

					onChangeLocationGP : function(cb, newValue) {
						var me = this, view = me.view, viewModel = me
								.getViewModel();
						switch (cb.name) {
						case "vclocation":
							Ext.Ajax
									.request({
										url : 'guidelocation.ajax',
										method : 'GET',
										params : {
											langCode : newValue,
											guideType : 'admin'
										},
										success : function(response) {
											var result = response.responseXML, btn = view
													.down('button[guideType=admin]');
											viewModel.set("vConfrenceURL",
													response.responseText
															.trim());
											btn.comboValue = newValue;
										}
									});
							break;
						case "vdlocation":
							Ext.Ajax
									.request({
										url : 'guidelocation.ajax',
										method : 'GET',
										params : {
											langCode : newValue,
											guideType : 'desk'
										},
										success : function(response) {
											var result = response.responseXML, btn = view
													.down('button[guideType=desk]');
											viewModel.set("vDesktopURL",
													response.responseText
															.trim());
											btn.comboValue = newValue;
										}
									});
							break;
						}
					},

					onClickUploadGUOverlay : function(btn) {
						var me = this, view = me.view, parentView = view.parentview, viewModel = parentView
								.getViewModel(), form = view
								.down('form[name=storeLocalForm]');

						if (form && form.getForm().isValid()) {
							form
									.getForm()
									.submit(
											{
												url : 'uploadsupportfile.ajax',
												params : {
													langCode : view.comboValue,
													guideType : view.guideType,
												},
												success : function(form, action) {
													if (form.guideType == "admin") {
														viewModel
																.set(
																		"vConfrenceURL",
																		Ext.DomQuery
																				.selectValue(
																						"message",
																						action.response.responseXML)
																				.trim());
													} else {
														viewModel
																.set(
																		"vDesktopURL",
																		Ext.DomQuery
																				.selectValue(
																						"message",
																						action.response.responseXML)
																				.trim());
													}
													view.hide();
													Ext.Msg
															.show({
																title : 'Upload Status',
																msg : 'Upload complete',
																minWidth : 300,
																modal : true,
																icon : Ext.Msg.INFO
															});
												},
												failure : function(form, action) {
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
																		function() {
																		});
													}
												}
											});
						}
					},

					onClickSaveGUOverlay : function(btn) {
						var me = this, view = me.view, parentView = view.parentview, viewModel = parentView
								.getViewModel(), form = view
								.down('form[name=webServerForm]');

						parentView.setLoading(true);
						view.setLoading(true);
						if (form && form.getForm().isValid()) {
							Ext.Ajax
									.request({
										url : 'updatesupportfileurl.ajax',
										method : 'POST',
										params : {
											langCode : view.comboValue,
											guideType : view.guideType,
											urlValidated : 'n',
											url : form.getValues()["urlLocation"]
													.trim()
										},
										waitTitle : 'URL Update Progress',
										waitMsg : 'URL is being updated',
										success : function(response) {
											if (view.guideType == "admin") {
												viewModel
														.set(
																"vConfrenceURL",
																Ext.DomQuery
																		.selectValue(
																				"message",
																				response.responseXML)
																		.trim());
											} else {
												viewModel
														.set(
																"vDesktopURL",
																Ext.DomQuery
																		.selectValue(
																				"message",
																				response.responseXML)
																		.trim());
											}
											parentView.setLoading(false);
											view.setLoading(false);
											view.hide();
											Ext.Msg.show({
												title : 'Upload Status',
												msg : 'Upload complete',
												minWidth : 300,
												modal : true,
												icon : Ext.Msg.INFO
											});
										},
										failure : function(response) {
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
														'<br>' + errors,
														function() {
														});
											}
										}
									});
						}
					},

					/***********************************************************
					 * @function onClickSaveBanners
					 */
					onClickSaveBanners : function() {
						var me = this, form = me.lookupReference('banners');

						if (form) {
							me.saveView(form.getValues(), 'savebanners.ajax',
									function() {
										me.getBannersData();
									});
						}
					},

					/** **** SAVE RESPONSE FUNCTIONS *************** */

					/***********************************************************
					 * @function saveAboutInfoAjax
					 */

					saveView : function(params, url, callback) {
						Ext.Ajax.request({
							url : url,
							params : params,
							method : 'POST',
							success : function(res) {
								var xmlResponse = res.responseXML;
								var success = Ext.DomQuery.selectValue(
										'message @success', xmlResponse);
								if (success == "true") {
									Ext.Msg.alert(l10n('message'),
											l10n('saved'));
									callback();
								}
							},
							failure : function(res) {
								Ext.Msg.alert(l10n('failure'),
										l10n('request-failed'));
							}
						});
					},

					/** **** GET DATA FUNCTIONS *************** */

					/***********************************************************
					 * @function getAboutInfoData
					 */
					getAboutInfoData : function() {
						var me = this, view = me.view;
						Ext.Ajax
								.request({
									url : 'aboutinfo.ajax',
									success : function(response) {
										var result = response.responseXML, viewModel = view
												.getViewModel(), aboutInfo = Ext.DomQuery
												.selectValue('aboutInfo',
														result);

										viewModel.set("aboutInfo", aboutInfo
												.replace(/(\r\n|\n|\r)/gm, ""));
									}
								});
					},

					/***********************************************************
					 * @function getSupportInfoData
					 */
					getSupportInfoData : function() {
						var me = this, view = me.view;
						Ext.Ajax
								.request({
									url : 'contactinfo.ajax',
									success : function(response) {
										var result = response.responseXML, viewModel = view
												.getViewModel(), contactInfo = Ext.DomQuery
												.selectValue('contactInfo',
														result);

										viewModel.set("contactInfo",
												contactInfo.replace(
														/(\r\n|\n|\r)/gm, ""));
									}
								});
					},

					/***********************************************************
					 * @function getNotificationData
					 */
					getNotificationData : function() {
						var me = this, view = me.view, viewModel = me
								.getViewModel();

						viewModel
								.getStore('notificationStore')
								.load(
										{
											callback : function(recs) {
												if (recs.length) {
													var rec = recs[0];
													var enableNewAccountNotification = rec
															.get("enableNewAccountNotification");
													viewModel
															.set(
																	"tenantID",
																	rec
																			.get('tenantID') == undefined ? 0
																			: rec
																					.get('tenantID'));
													viewModel
															.set(
																	"fromEmail",
																	rec
																			.get('fromEmail') == undefined ? ''
																			: rec
																					.get('fromEmail'));
													viewModel
															.set(
																	"toEmail",
																	rec
																			.get('toEmail') == undefined ? ''
																			: rec
																					.get('toEmail'));
													viewModel
															.set(
																	"enableNewAccountNotification",
																	rec
																			.get('enableNewAccountNotification') == true ? true
																			: false);
												}
											}
										});
					},

					/***********************************************************
					 * @function getInvitationData
					 */
					getInviteTextData : function() {
						var me = this, view = me.view;

						Ext.Ajax
								.request({
									url : 'invitationsetting.ajax',

									success : function(response) {
										var result = response.responseXML, viewModel = view
												.getViewModel();

										viewModel
												.set(
														"invitationEmailContent",
														Ext.DomQuery
																.selectValue(
																		'invitationEmailContent',
																		result));
										viewModel
												.set(
														"invitationEmailContentHtml",
														Ext.DomQuery
																.selectValue(
																		'invitationEmailContentHtml',
																		result));
										viewModel.set("voiceOnlyContent",
												Ext.DomQuery.selectValue(
														'voiceOnlyContent',
														result));
										viewModel.set("webcastContent",
												Ext.DomQuery.selectValue(
														'webcastContent',
														result));
										viewModel
												.set(
														"invitationEmailSubject",
														Ext.DomQuery
																.selectValue(
																		'invitationEmailSubject',
																		result));
										me.onDialGridRender();
									}
								});
					},

					/*
					 * getSuperAdminLogoData : function() { var me = this, view =
					 * me.view, viewModel = me.getViewModel();
					 * 
					 * viewModel.getStore('customizeUserPortalStore').load({
					 * callback : function(recs) { if(recs.length) { var rec =
					 * recs[0]; viewModel.set("splogonameOne",
					 * rec.get('splogoname')); if(rec.get('splogoname')) {
					 * viewModel.set('isSPBtnDisabled', false); } else {
					 * viewModel.set('isSPBtnDisabled', true); } } } }); },
					 */

					getPortalLogoData : function() {
						var me = this, view = me.view, viewModel = me
								.getViewModel();

						viewModel.getStore('customizeUserPortalStore').load(
								{
									callback : function(recs) {
										if (recs.length) {
											var rec = recs[0];
											if (rec.get('uplogoname')) {
												viewModel.set("splogonameOne",
														rec.get('uplogoname'));
												viewModel.set(
														'isUPBtnDisabled',
														false);
											} else {
												viewModel
														.set('isUPBtnDisabled',
																true);
											}
										}
									}
								});
					},

					getVidyoDesktopData : function() {
						var me = this, view = me.view, viewModel = me
								.getViewModel();

						viewModel.getStore('vidyoDesktopStore').load(
								{
									callback : function(recs) {
										if (recs.length) {
											var rec = recs[0];
											if (rec.get('vdlogoname')) {
												viewModel.set(
														'isVDBtnDisabled',
														false);
												viewModel.set(
														"vdlogonameThree",
														rec.get('vdlogoname'));
											} else {
												viewModel
														.set('isVDBtnDisabled',
																true);
											}
										}
									}
								});
					},

					getCustomizeLogosData : function() {
						var me = this, view = me.view;

						// me.getSuperAdminLogoData();

						me.getPortalLogoData();
						me.getVidyoDesktopData();
					},

					getGuideLocationData : function() {
						var me = this, view = me.view, viewModel = view
								.getViewModel();

						viewModel.getStore('sysLocation').load(
								{
									callback : function(rec) {
										var rec = rec[0];

										me.lookupReference('cGuidelocation')
												.select(rec);
										me.lookupReference('dGuidelocation')
												.select(rec);
									}
								});
						Ext.Ajax.request({
							url : 'guidelocation.ajax',
							method : 'GET',
							params : {
								langCode : 'en',
								guideType : 'admin'
							},
							success : function(response) {
								var result = response.responseXML;
								viewModel.set("vConfrenceURL",
										response.responseText.trim());
							}
						});
						Ext.Ajax.request({
							url : 'guidelocation.ajax',
							method : 'GET',
							params : {
								langCode : 'en',
								guideType : 'desk'
							},
							success : function(response) {
								var result = response.responseXML;
								viewModel.set("vDesktopURL",
										response.responseText.trim());
							}
						});
					},

					getBannersData : function() {
						var me = this, view = me.view;

						Ext.Ajax
								.request({
									url : 'banners.ajax',
									success : function(response) {
										var result = response.responseXML, viewModel = view
												.getViewModel(), showLogin = Ext.DomQuery
												.selectValue('showLoginBanner',
														result).trim() == "true" ? true
												: false;
										showWelcome = Ext.DomQuery.selectValue(
												'showWelcomeBanner', result)
												.trim() == "true" ? true
												: false;

										me.lookupReference('showLoginBanner')
												.setValue(showLogin);
										me.lookupReference('showWelcomeBanner')
												.setValue(showWelcome);
										viewModel.set("loginBanner",
												Ext.DomQuery.selectValue(
														'loginBanner', result));
										viewModel
												.set(
														"welcomeBanner",
														Ext.DomQuery
																.selectValue(
																		'welcomeBanner',
																		result));
									}
								});
					},

					onClickUploadCustomizeDesktopEndpoint : function(btn) {
						var me = this, form = me.lookupReference(btn.formName);

						if (form) {
							form
									.getForm()
									.submit(
											{
												url : btn.ajaxUrl,
												waitMsg : 'saving',
												success : function(form, action) {
													var xmlResponse = action.response.responseXML;
													var issuccess = Ext.DomQuery
															.selectValue(
																	'message @success',
																	xmlResponse);
													if (issuccess == "false") {
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
														me
																.getAdminCustomizeEndpointData();
													} else {
														Ext.Msg
																.alert(
																		l10n("message"),
																		"Upload succeeded.",
																		function() {
																			me
																					.getAdminCustomizeEndpointData();
																		})
													}
												},
												failure : function(form, action) {
													if (action === undefined
															|| action.response === undefined) {
														Ext.Msg
																.alert(
																		l10n("message"),
																		"Please choose a file for upload.");
														return;
													}
													var xmlResponse = action.response.responseXML;
													var issuccess = Ext.DomQuery
															.selectValue(
																	'message @success',
																	xmlResponse);
													if (issuccess == "false") {
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
														me
																.getAdminCustomizeEndpointData();
													} else {
														Ext.Msg
																.alert(
																		l10n("message"),
																		"Upload failed.",
																		function() {
																			me
																					.getAdminCustomizeEndpointData();
																		})
													}
												}
											});
						}
					},

					getAdminCustomizeEndpointData : function() {
						var me = this, viewModel = me.getViewModel();

						viewModel
								.getStore('AdminCustomizeEndpointStore')
								.load(
										{
											callback : function(recs) {
												if (recs && recs.length) {
													var rec = recs[0];
													if (rec
															.get('adminCustomizeDesktopExists') == "true") {
														viewModel
																.set(
																		'isNeoDesktopCustomization',
																		true);
													} else {
														viewModel
																.set(
																		'isNeoDesktopCustomization',
																		false);
													}
													viewModel
															.set(
																	'allowTenantOverride',
																	rec
																			.get('allowTenantOverride'));
												}
											}
										});
					},

					onClickDownloadCustomizeDesktopEndpoint : function() {
						window.open("downloadAdminDesktopCustomization.html",
								"_blank");
					},

					onClickRemoveCustomizeDesktopEndpoint : function(btn) {
						var me = this, form = me.lookupReference(btn.formName);
						Ext.Ajax
								.request({
									url : 'removedesktopcustomization.ajax',
									success : function(response) {
										var xmlResponse = response.responseXML;
										var issuccess = Ext.DomQuery
												.selectValue(
														'message @success',
														xmlResponse);
										if (issuccess == "false") {
											var responseId = Ext.DomQuery
													.selectNode('id',
															xmlResponse);
											var responseMsg = Ext.DomQuery
													.selectNode('msg',
															xmlResponse);
											Ext.Msg.alert(
													responseId.textContent,
													responseMsg.textContent);
										} else {
											Ext.Msg
													.alert(
															l10n("message"),
															"Remove succeeded.",
															function() {
																me
																		.getAdminCustomizeEndpointData();
															});
										}
									},
									failure : function(form, action) {
										var xmlResponse = response.responseXML;
										var issuccess = Ext.DomQuery
												.selectValue(
														'message @success',
														xmlResponse);
										if (issuccess == "false") {
											var responseId = Ext.DomQuery
													.selectNode('id',
															xmlResponse);
											var responseMsg = Ext.DomQuery
													.selectNode('msg',
															xmlResponse);
											Ext.Msg.alert(
													responseId.textContent,
													responseMsg.textContent);
										} else {
											Ext.Msg
													.alert(
															l10n("message"),
															"Remove failed.",
															function() {
																me
																		.getAdminCustomizeEndpointData();
															});
										}
									}
								});
					},
					onDialGridRender : function() {
						var me = this, viewModel = me.getViewModel();
						var store = viewModel.getStore('DialInStore');

						store.load();

					},
					onDialGridChange : function(view, newData) {
						var grid = this.lookupReference('dialInGrid');
						grid.getView().refresh();
					},

					onAddClick : function() {
						// Create a model instance
						var me = this, viewModel = me.getViewModel(), grid = me
								.lookupReference('dialInGrid'), record = grid
								.getSelection()[0], gridEditPlugin = grid
								.getPlugins()[0], gridStore = grid.getStore();
						console.log('gridStore');
						gridStore.each(function(record) {
							console.log(record.get('countryName'));
						}, this);
						var rec = new AdminApp.model.settings.DialNo({
							countryID : '',
							name : '',
							phoneCode : '',
							dialInNumber : '',
							dialInLabel : ''

						});
						gridStore.insert(0, rec);
						gridEditPlugin.startEdit(rec, 0);
					},

					onRemoveClick : function(grid, rowIndex) {
						var grid = this.lookupReference('dialInGrid'), record = grid
								.getSelection()[0], gridEditPlugin = grid
								.getPlugins()[0], gridStore = grid.getStore();
						if (record) {
							gridStore.remove(record);
							grid.gridDirtyFlag = true;
						}

					}

				});
