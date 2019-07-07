<%@ include file="header_html.jsp" %>

<div id="maincontent">

	<div id="content-panel">&nbsp;</div>

</div>

<%@ include file="footer_html.jsp" %>
<script type='text/javascript' src='<c:url value="/js/VTypes.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/FileUploadField.js"/>'></script>
<script type="text/javascript">

Ext.apply(Ext.form.VTypes, {
    passwordOne : function(val, field) {
        if (field.compareToField) {
            var pwd = Ext.getCmp(field.compareToField);
            if(pwd.getValue() != '') {
                if(val == pwd.getValue()) {
                    pwd.clearInvalid();
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
        return true;
    },
    passwordOneText : '<spring:message code="SuperAccount.password.not.match"/>',

    passwordTwo : function(val, field) {
        if (field.compareToField) {
            var pwd = Ext.getCmp(field.compareToField);
            if(pwd.getValue() != '') {
                if(val == pwd.getValue()) {
                    pwd.clearInvalid();
                    return true;
                } else {
                    return false;
                }
            }
            return true;
        }
        return true;
    },
    passwordTwoText : '<spring:message code="SuperAccount.password.not.match"/>'

});

Ext.apply(Ext.form.VTypes, {
	portNumber:function (val, field) {
		var reg = /^[0-9]+$/;
		return reg.test(val);
	},
	portNumberText:'<spring:message javaScriptEscape="true" code="the.format.is.wrong.must.only.contain.numeric.values"/>'
});

Ext.onReady(function () {
	Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'side';
    Ext.Ajax.timeout = 120000;

    var msg = function (title, msg, callback) {
		Ext.Msg.show({
			title:title,
			msg:msg,
			minWidth:200,
			modal:true,
			icon:Ext.Msg.INFO,
			buttons:Ext.Msg.OK,
			closable: false,
			fn:callback
		});
	};

	var localmenu = new Ext.Panel({
		collapsible:false,
		autoHeight:true,
		border:false,
		frame:false,
		autoLoad:{url:'<c:url value="menu_content.html?settings=1&security=1"/>'}
	});

	///////////////////////////////////  keyPanel
	var keyInfo = Ext.data.Record.create([
		{name:'bits', type:'int'},
		{name:'keyHash', type:'string'}
	]);

	var keyReader = new Ext.data.XmlReader({
		totalRecords:'results',
		record:'row'
	}, keyInfo);


	var keyDS = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({method: "POST", url:'<c:url value="security/security_key.ajax"/>', timeout:120000}),
		reader:keyReader,
		listeners:{
			load: function(store,recs) {
				Ext.getCmp("bits").setValue(recs[0].get("bits"));
				Ext.getCmp("keyHash").setValue(recs[0].get('keyHash'));
			}
		}
	})

	var keyForm = new Ext.FormPanel({
		url:'<c:url value="security/security_generate_key.ajax"/>',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				}, [
			'id', 'msg'
		]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[
			{
				xtype:'label',
				html:'<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.private.key.notes"/></div>'
			},{
				xtype: 'combo',
				id: "bits",
				name: "bits",
				typeAhead: false,
				triggerAction: "all",
				editable: false,
				store: [['1024','1024'],['2048','2048'],['4096', '4096']],
				fieldLabel: "<spring:message javaScriptEscape="true" code="key.size"/> (bits)"
			}],
		buttons:[{
			id: "keyRegenerateButton",
			text: '<spring:message javaScriptEscape="true" code="super.security.ssl.manage.key.regenerate"/>',
			handler: doKeyGenerate
		}]

	});

	function doKeyGenerate() {
		Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', '<spring:message javaScriptEscape="true" code="super.security.ssl.key.are.you.sure.you.want.to.generate.a.new.private.key.this.will.require.new.ssl.csr.and.server.certificates"/>', function (btn) {
			if (btn == 'yes') {

				keyForm.getForm().submit({
					waitTitle:'<spring:message javaScriptEscape="true" code="super.security.ssl.generate.private.key"/>',
					waitMsg:'<spring:message javaScriptEscape="true" code="super.security.ssl.generating.private.key"/>',
					success:function (form, action) {
						msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="super.security.ssl.generated.private.key"/>");
						keyDS.load();
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
							msg('Error', '<spring:message javaScriptEscape="true" code="super.security.ssl.generate.private.key.failed"/>', function () {
							});
						}
					}
				});
			}

		});
	}

	var uploadKeyWin;
	var uploadKeyForm = new Ext.FormPanel({
		baseCls:'x-plain',
		fileUpload:true,
		frame:false,
		autoHeight:true,
		bodyStyle:'padding: 10px 10px 0px 10px',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				},
				[ 'id', 'msg' ]
		),
		defaults:{
			anchor:'95%',
			allowBlank:false,
			msgTarget:'side'
		},
		items:[
			{
				xtype:'label',
				html:'<div style="padding: 5px 0px; color: red;"><spring:message javaScriptEscape="true" code="security.ssl.key.warning.importing.a.new.private.key.will.invalidate.any.existing.csr.or.server.certs"/> <spring:message javaScriptEscape="true" code="you.cannot.undo.this.operation"/></div>'
			},
			{
				xtype:'fileuploadfield',
				emptyText:'<spring:message javaScriptEscape="true" code="super.security.key.upload.file.types"/>',
				fieldLabel:'<spring:message javaScriptEscape="true" code="super.security.ssl.private.key.tab"/>',
				hideLabel:false,
				labelSeparator:':',
				name:'uploadFile',
				buttonCfg:{
					text:'',
					iconCls:'icon-upload'
				}
			},
            {
                xtype: 'textfield',
                inputType: 'password',
                hideLabel:false,
                labelSeparator:':',
                fieldLabel: '<spring:message javaScriptEscape="true" code="password"/>',
                name: 'keyPassword',
                required: true
            }
		],
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="upload"/>',
				disabled:false,
				handler:function () {
					if (uploadKeyForm.getForm().isValid()) {
						uploadKeyForm.getForm().submit({
							url:'<c:url value="security/security_upload_file.ajax"/>',
							params: {uploadType: "key", '${_csrf.parameterName}' : '${_csrf.token}' },
							waitTitle:'<spring:message javaScriptEscape="true" code="uploading.file"/>',
							waitMsg:'<spring:message javaScriptEscape="true" code="your.file.is.being.uploaded"/>',
							success:function (form, action) {
								uploadKeyForm.getForm().reset();
								uploadKeyWin.hide();
								keyDS.load();
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
									msg('Error', 'Error occurred.', function () {
									});
								}
							}
						});

					}
				}
			}
		]
	});

	function doKeyUpload() {
		if (!uploadKeyWin) {
			uploadKeyWin = new Ext.Window({
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.select.private.key"/>',
				closable:true,
				closeAction:'hide',
				resizable:false,
				width:500,
				autoHeight:true,
				border:true,
				frame:true,
				layout:'fit',
				modal: true,
				items:[
					uploadKeyForm
				]
			});

		}
		uploadKeyWin.show(this);

	}

    var downloadKeyWin;
    var downloadKeyForm = new Ext.FormPanel({
        baseCls:'x-plain',
        fileUpload:true,
        frame:false,
        autoHeight:true,
        bodyStyle:'padding: 10px 10px 0px 10px',
        errorReader:new Ext.data.XmlReader({
                    success:'@success',
                    record:'field'
                },
                [ 'id', 'msg' ]
        ),
        defaults:{
            allowBlank:false,
            msgTarget:'side'
        },
        items:[
            {
                xtype: 'textfield',
                inputType: 'password',
                vtype: 'passwordOne',
                compareToField: "exportPassword2",
                id: "exportPassword1",
                hideLabel:false,
                labelSeparator:':',
                fieldLabel: '<spring:message javaScriptEscape="true" code="password"/>',
                name: 'keyPassword',
                required: true
            },
            {
                xtype: 'textfield',
                inputType: 'password',
                vtype: 'passwordTwo',
                compareToField: "exportPassword1",
                id: "exportPassword2",
                hideLabel:false,
                labelSeparator:':',
                fieldLabel: '<spring:message javaScriptEscape="true" code="confirm.password"/>',
                name: 'keyPassword2',
                required: true
            }
        ],
        buttons:[
            {
                text:'<spring:message javaScriptEscape="true" code="export"/>',
                disabled:false,
                handler:function () {
                    if (downloadKeyForm.getForm().isValid()) {
                        downloadKeyWin.hide();
                        downloadKeyForm.getForm().submit({
                            url:'<c:url value="security/security_export_key.ajax"/>',
                            params: {'${_csrf.parameterName}' : '${_csrf.token}' },
                            success:function (form, action) {
                                downloadKeyForm.getForm().reset();
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
                                    msg('Error', 'Error occurred.', function () {
                                    });
                                }
                            }
                        });

                    }
                }
            }
        ]
    });

    function doKeyDownload() {
        if (!downloadKeyWin) {
            downloadKeyWin = new Ext.Window({
                title:'<spring:message javaScriptEscape="true" code="export.private.key"/>',
                closable:true,
                closeAction:'hide',
                resizable:false,
                width:325,
                autoHeight:true,
                border:true,
                frame:true,
                layout:'fit',
                modal: true,
                items:[
                    downloadKeyForm
                ]
            });

        }
        downloadKeyForm.getForm().reset();
        downloadKeyWin.show(this);
    }

	var keyPanel = new Ext.Panel({
		frame: true,
		items:[
			{
				xtype: 'fieldset',
				title: '<spring:message javaScriptEscape="true" code="super.security.ssl.key.information"/>',
				autoHeight: true,
				items:[
					keyForm
				]

			},
			{
				xtype: 'fieldset',
				title: '<spring:message javaScriptEscape="true" code="super.security.ssl.private.key"/>',
				autoHeight: true,
				items:[
					new Ext.FormPanel({
                        defaults: {
                            labelStyle: 'font-weight:bold;text-align:right;',
                            labelSeparator: ":"
                        },
						items: [
							{
								xtype:'label',
								html:'<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.ssl.manage.key.note"/></div>'
							},
							{
								xtype: 'textfield',
								id: "keyHash",
                                readOnly: true,
                                width: 500,
                                fieldLabel: "SHA256"
							}
						],
						buttons: [
							{text: "<spring:message javaScriptEscape="true" code="import.private.key"/>", id: "keyUploadButton", handler: doKeyUpload},
                            {text: "<spring:message javaScriptEscape="true" code="export.private.key"/>", id: "keyExportButton", handler: doKeyDownload}
						]
					})
				]

			}]

	});

	/////////////////////////////////// csrPanel
	var csrInfo = Ext.data.Record.create([
		{name:'country', type:'string'},
		{name:'state', type:'string'},
		{name:'city', type:'string'},
		{name:'company', type:'string'},
		{name:'division', type:'string'},
		{name:'domain', type:'string'},
		{name:'email', type:'string'},
		{name:'csr', type:'string'},
		{name: 'csrMatchesKey', type:'boolean'}
	]);

	var csrReader = new Ext.data.XmlReader({
		totalRecords:'results',
		record:'row'
	}, csrInfo);


	var csrDS = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({method: "POST",url:'<c:url value="security/security_csr.ajax"/>', timeout:120000}),
		reader:csrReader,
		listeners:{
			load: function(store,recs) {
				Ext.getCmp("country").setValue(recs[0].get("country"));
				Ext.getCmp("state").setValue(recs[0].get('state'));
				Ext.getCmp("city").setValue(recs[0].get('city'));
				Ext.getCmp("company").setValue(recs[0].get('company'));
				Ext.getCmp("division").setValue(recs[0].get('division'));
				Ext.getCmp("domain").setValue(recs[0].get('domain'));
				Ext.getCmp("email").setValue(recs[0].get('email'));
				Ext.getCmp("csr").setValue(recs[0].get('csr'));
				if (recs[0].get('csr') != '') {
					Ext.getCmp("csrGenerateButton").setText("<spring:message javaScriptEscape="true" code="super.security.ssl.manage.key.regenerate"/>");
					if (recs[0].get('csrMatchesKey') == false) {
						showStatusMessage("<spring:message javaScriptEscape="true" code="super.security.ssl.manage.csr.current.key.mismatch.error"/>");
					} else {
						showStatusMessage("");
					}
				}
			}
		}
	})


	var csrForm =  new Ext.FormPanel({
		url:'<c:url value="security/security_generate_csr.ajax"/>',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				}, [
			'id', 'msg'
		]
		),
		labelWidth: 400,
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;',
			labelSeparator: ""
		},
		items:[{
			xtype: 'textfield',
			fieldLabel: '<span class="red">*</span><a href="http://www.iso.org/iso/country_codes/iso_3166_code_lists/country_names_and_code_elements.htm" target="_blank"><spring:message javaScriptEscape="true" code="super.security.ssl.certificate.country.code"/></a>',
			id: "country",
			name: "country",
			allowBlank: false,
			width: "85%"
		},{
			xtype: 'textfield',
			fieldLabel: '<span class="red">*</span><spring:message javaScriptEscape="true" code="super.security.ssl.csr.state.name"/>',
			id: "state",
			name: "state",
			allowBlank: false,
			width: "85%"
		},{
			xtype: 'textfield',
			fieldLabel: '<span class="red">*</span><spring:message javaScriptEscape="true" code="super.security.ssl.csr.locality.name"/>',
			id: "city",
			name: "city",
			allowBlank: false,
			width: "85%"
		},{
			xtype: 'textfield',
			fieldLabel: '<span class="red">*</span><spring:message javaScriptEscape="true" code="super.security.ssl.csr.organization.name"/>',
			id: "company",
			name: "company",
			allowBlank: false,
			width: "85%"
		},{
			xtype: 'textfield',
			fieldLabel: '<span class="red">*</span><spring:message javaScriptEscape="true" code="super.security.ssl.csr.organizational.unit.name"/>',
			id: "division",
			name: "division",
			allowBlank: false,
			width: "85%"
		},{
			xtype: 'textfield',
			fieldLabel: "<span class='red'>*</span><spring:message javaScriptEscape="true" code="super.security.ssl.csr.common.name"/>",
			id: "domain",
			name: "domain",
			allowBlank: false,
			width: "85%"
		},{
			xtype: 'textfield',
			id: "email",
			name: "email",
			fieldLabel: '<spring:message javaScriptEscape="true" code="super.security.ssl.csr.email.address"/>',
			width: "85%"
		}],
		buttons:[
			{
				text: '<spring:message javaScriptEscape="true" code="reset"/>',
				handler: function() {csrDS.load()}
			},
			{
			id: "csrGenerateButton",
			text: '<spring:message javaScriptEscape="true" code="super.security.ssl.csr.generate"/>',
			handler: doCSRGenerate
		}]
	});

	function doCSRGenerate() {
		csrForm.getForm().submit({
			waitTitle:'<spring:message javaScriptEscape="true" code="generate.csr"/>',
			waitMsg:'<spring:message javaScriptEscape="true" code="security.super.ssl.generating.csr"/>',
			success:function (form, action) {
				msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="csr.is.successfully.generated"/>");
				csrDS.load();
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
					msg('Error', '<spring:message javaScriptEscape="true" code="failed.to.generate.csr"/>', function () {
					});
				}
			}
		});

	}

	var csrPanel = new Ext.Panel({
		frame: true,
		items:[
			{
				items:[{
					xtype: 'fieldset',
					title: '<spring:message javaScriptEscape="true" code="super.security.ssl.csr.content"/>',
					autoHeight: true,
					style: "margin-right: 5px;",
					items:[
						{
							xtype:'label',
							html: "<div style='padding-bottom: 10px;'><spring:message javaScriptEscape="true" code='super.security.private.csr.notes'/></div>"
						},
						csrForm
					]
				}]
			},{
				items:[{
					xtype: 'fieldset',
					title: 'CSR',
					autoHeight: true,
					layout: 'fit',
					items:[
						{
							xtype:'label',
							html: "<div style='padding-bottom: 10px;'><spring:message javaScriptEscape="true" code="super.security.ssl.manage.csr.note"/></div>"
						},
						{
						xtype: 'textarea',
						readOnly: true,
						id: "csr",
						hideLabel: true,
						height: 200,
						width: "95%",
						style: "font: normal 12px monospace, Courier"
					}]
				}]
			}
		]

	});

	/////////////////////////////////// serverCertPanel

	var certInfo = Ext.data.Record.create([
		{name:'certParsed', type:'string'},
		{name:'cert', type:'string'},
		{name: 'crtMatchesKey', type: 'boolean'},
		{name: 'crtMatchesDomain', type: 'boolean'},
		{name: 'fqdn', type: 'string'}
	]);

	var certReader = new Ext.data.XmlReader({
		totalRecords:'results',
		record:'row'
	}, certInfo);


	var certDS = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({method: "POST",url:'<c:url value="security/security_server_cert.ajax"/>', timeout:120000}),
		reader:certReader,
		listeners:{
			load: function(store,recs) {
				Ext.get("certParsed").update(recs[0].get('certParsed'));
				Ext.getCmp("cert").setValue(recs[0].get('cert'));
				if (recs[0].get('cert') != '') {
					Ext.getCmp("certGenerateButton").setText("<spring:message javaScriptEscape="true" code="super.security.ssl.regenerate.self.signed"/>");
					var warning = "";
					if (recs[0].get('crtMatchesKey') == false) {
						warning = "<spring:message javaScriptEscape="true" code="super.security.server.certificate.does.not.match.private.key"/>";
					}
					if (recs[0].get('crtMatchesDomain') == false) {
						warning = warning + " " + "<spring:message javaScriptEscape="true" code="fqdn.is.not.covered.by.the.server.certificate"/>";
						warning = warning + " [FQDN: " + recs[0].get('fqdn') + "]";
					}
					showStatusMessage(warning);
				}
				Ext.getCmp("certResetButton").disable();
				Ext.getCmp("certUpdateButton").disable();
			}
		}
	})

	function doCertGenerate() {
		Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', '<spring:message javaScriptEscape="true" code="super.security.ssl.cert.are.you.sure.you.want.to.generate.a.self.signed.certiciate.this.will.remove.any.current.server.certificate"/>', function (btn) {
			if (btn == 'yes') {

				var url = '<c:url value="security/security_generate_cert.ajax"/>';
				if (!this.generateCertForm) {
					var generateCertFormEl = Ext.get("container").createChild({
						tag:'form',
						style:'display:none'
					});
					this.generateCertForm = new Ext.form.BasicForm(generateCertFormEl, {
                        method: "POST",
						url:url,
						errorReader:new Ext.data.XmlReader({
									success:'@success',
									record:'field'
								}, [
							'id', 'msg'
						]
						)
					});
				}
				this.generateCertForm.submit({
                    timeout: 120000,
					waitTitle:'<spring:message javaScriptEscape="true" code="super.security.ssl.generate.self.signed"/>',
					waitMsg:'<spring:message javaScriptEscape="true" code="super.security.ssl.resetting.ssl.security.settings"/>',
					success:function (form, action) {
                        if (isPageSecure()) {
                            msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="super.security.ssl.generated.sever.certificate"/> Your connection will now be reset.",
                                    function() { refreshPageRebootWarning() });
                        } else {
                            msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="super.security.ssl.generated.sever.certificate"/>");
                            certDS.load();
                        }
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
							msg('Error', '<spring:message javaScriptEscape="true" code="super.security.ssl.generate.self.signed.certificate.failed"/>', function () {
							});
						}
					}
				});
			}

		});
	}

    function isPageSecure() {
        return location.protocol == 'https:';
    }

    function refreshPageRebootWarning() {
        setRebootMessageCookie();
        location.reload();
    }

	var serverCertForm = new Ext.Panel({
		items:[
			{
				autoEl: {
					tag: 'div',
					id: "certParsed"
				}
			}],
		buttons:[{
			text: '<spring:message javaScriptEscape="true" code="super.security.ssl.generate.self.signed"/>',
			id: "certGenerateButton",
			handler: doCertGenerate
		}]

	});

	var serverCertPanel = new Ext.Panel({
		frame: true,
		layout: "anchor",
		items:[{
			items:[
				{
					xtype: 'fieldset',
					title: '<spring:message javaScriptEscape="true" code="security.super.ssl.contents"/>',
					autoHeight: true,
					items:[
						{
							xtype:'label',
							html: '<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.private.cert.notes"/></div>'
						},
						serverCertForm
					]
				},{
					xtype: 'fieldset',
					title: '<spring:message javaScriptEscape="true" code="security.super.ssl.super.security.ssl.certificate.tab"/>',
					autoHeight: true,
					items:[
						new Ext.FormPanel({
							items: [
								{
									xtype:'label',
									html: '<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.ssl.manage.cert.note"/></div>'
								},
								{
									xtype: 'textarea',
									id: "cert",
									style: "font: normal 12px monospace, Courier",
									width: "95%",
									hideLabel: true,
									height: 200,
									listeners: {
										focus: function(textarea){
                                            Ext.getCmp('certResetButton').setDisabled(false);
                                            Ext.getCmp('certUpdateButton').setDisabled(false);
										}
									}
								}
							],
							buttons: [
								{text: "<spring:message javaScriptEscape="true" code="reset"/>", id: "certResetButton", disabled: true, handler: function() {certDS.load()}},
								{text: "<spring:message javaScriptEscape="true" code="apply"/>", id: "certUpdateButton", disabled: true, handler: confirmCertUpdate},
								{text: "<spring:message javaScriptEscape="true" code="upload1"/>", id: "certUploadButton", handler: doCertUpload}
							]
						})
					]
				}
			]
		}]
	});

	var uploadCertWin;
	var uploadCertForm = new Ext.FormPanel({
		baseCls:'x-plain',
		fileUpload:true,
		frame:false,
		autoHeight:true,
		bodyStyle:'padding: 10px 10px 0px 10px',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				},
				[ 'id', 'msg' ]
		),
		defaults:{
			anchor:'95%',
			allowBlank:false,
			msgTarget:'side'
		},
		items:[
			{
				xtype:'fileuploadfield',
				emptyText:'<spring:message javaScriptEscape="true" code="security.super.ssl.select.a.pem.crt.cer.der.file"/>',
				fieldLabel:'<spring:message javaScriptEscape="true" code="security.super.ssl.certificate"/>',
				hideLabel:false,
				labelSeparator:':',
				name:'uploadFile',
				buttonCfg:{
					text:'',
					iconCls:'icon-upload'
				}
			}
		],
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="upload"/>',
				disabled:false,
				handler:function () {
					if (uploadCertForm.getForm().isValid()) {
						uploadCertForm.getForm().submit({
							url:'<c:url value="security/security_upload_file.ajax"/>',
							params: {uploadType: "cert", '${_csrf.parameterName}' : '${_csrf.token}' },
							waitTitle:'<spring:message javaScriptEscape="true" code="uploading.file"/>',
							waitMsg:'<spring:message javaScriptEscape="true" code="your.file.is.being.uploaded"/>',
							success:function (form, action) {
                                if (isPageSecure()) {
                                    msg("<spring:message javaScriptEscape="true" code="success"/>", "Server certificate was successfully installed. Your connection will now be reset.", function () {
                                        refreshPageRebootWarning();
                                    })
                                } else {
                                    uploadCertForm.getForm().reset();
                                    uploadCertWin.hide();
                                    certDS.load();
                                }
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
									msg('Error', 'Error occurred.', function () {
									});
								}
							}
						});

					}
				}
			}
		]
	});

	function doCertUpload() {
		if (!uploadCertWin) {
			uploadCertWin = new Ext.Window({
				title:'<spring:message javaScriptEscape="true" code="security.super.ssl.select.server.certificate"/>',
				closable:true,
				closeAction:'hide',
				resizable:false,
				width:500,
				autoHeight:true,
				border:true,
				frame:true,
				layout:'fit',
				modal: true,
				items:[
					uploadCertForm
				]
			});

		}
		uploadCertWin.show(this);

	}

	function confirmCertUpdate() {
		Ext.Msg.confirm(
				"<spring:message javaScriptEscape="true" code="confirmation"/>",
				"<spring:message javaScriptEscape="true" code="security.super.are.you.sure.you.want.to.update.the.server.certificate"/>",
				function(e) { if (e == 'yes') {doServerCertUpdate();}}
		);
	}

	function doServerCertUpdate() {
		if (!this.updateServerCertForm) {
			var updateServerCertFormEl = Ext.get("container").createChild({
				tag:'form',
				style:'display:none'
			});
			this.updateServerCertForm = new Ext.form.BasicForm(updateServerCertFormEl, {
                method: "POST",
				url:'<c:url value="security/security_server_cert_update.ajax"/>',
				method: "POST",
				errorReader:new Ext.data.XmlReader({
							success:'@success',
							record:'field'
						}, [
					'id', 'msg'
				]
				)
			});
		}
		this.updateServerCertForm.submit({
			params : { cert : Ext.getCmp("cert").getValue() },
			success:function (form, action) {
                if (isPageSecure()) {
                    msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="security.super.ssl.updated.server.certificate"/> Your connection will now be reset.",
                    function() { refreshPageRebootWarning(); });
                } else {
                    msg("<spring:message javaScriptEscape="true" code="success"/>", "<spring:message javaScriptEscape="true" code="security.super.ssl.updated.server.certificate"/>");
                    certDS.load();
                }
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
					msg('Error', '<spring:message javaScriptEscape="true" code="security.super.ssl.failed.to.update.server.certificate"/>', function () {
					});
				}
			}
		});

	}

	/////////////////////////////////// intermediateCertsPanel

	var caCertInfo = Ext.data.Record.create([
		{name:'caCertParsed', type:'string'},
		{name:'caCert', type:'string'}
	]);

	var caCertReader = new Ext.data.XmlReader({
		totalRecords:'results',
		record:'row'
	}, caCertInfo);


	var caCertDS = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({method: "POST",url:'<c:url value="security/security_server_ca_cert.ajax"/>', timeout:120000}),
		reader:caCertReader,
		listeners:{
			load: function(store,recs) {
				Ext.get("caCertParsed").update(recs[0].get("caCertParsed"));
				Ext.getCmp("caCert").setValue(recs[0].get('caCert'));
			}
		}
	})

	var intermediateCertsContent = new Ext.Panel({
		items:[
			{
				autoEl: {
					tag: 'div',
					id: "caCertParsed"
				}
			}]

	});

	var intermediateCertsPanel = new Ext.Panel({
		frame: true,
		layout: "anchor",
		autoHeight: true,
		items:[
			{
				xtype: 'fieldset',
				title: '<spring:message javaScriptEscape="true" code="security.super.ssl.contents"/>',
				autoHeight: true,
				items:[
					{
						xtype:'label',
						html: '<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.private.certint.notes"/></div>'
					},
					intermediateCertsContent
				]

			},{
				xtype: 'fieldset',
				title: '<spring:message javaScriptEscape="true" code="security.super.ssl.server.ca.certificate.s"/>',
				autoHeight: true,
				items:[
					new Ext.FormPanel({
						items: [
							{
								xtype:'label',
								html: '<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.ssl.manage.cert.int.note"/></div>'
							},
							{
								xtype: 'textarea',
								id: "caCert",
								hideLabel: true,
								readOnly: true,
								height: 200,
								width: "95%",
								style: "font: normal 12px monospace, Courier"
							}
						],
						buttons: [
							{text: "<spring:message javaScriptEscape="true" code="upload1"/>", id: "certIntUploadButton", handler: doCertIntUpload}
						]
					})
				]

			}
		]

	});


	var uploadCertIntWin;
	var uploadCertIntForm = new Ext.FormPanel({
		baseCls:'x-plain',
		fileUpload:true,
		frame:false,
		autoHeight:true,
		bodyStyle:'padding: 10px 10px 0px 10px',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				},
				[ 'id', 'msg' ]
		),
		defaults:{
			anchor:'95%',
			allowBlank:false,
			msgTarget:'side'
		},
		items:[
			{
				xtype:'label',
				html:'<div style="padding: 5px 0px; color: red;"><spring:message javaScriptEscape="true" code="super.security.ssl.manage.cert.int.note"/></div>'
			},
			{
				xtype:'fileuploadfield',
				emptyText:'<spring:message javaScriptEscape="true" code="security.super.ssl.select.a.pem.crt.cer.der.file"/>',
				fieldLabel:'<spring:message javaScriptEscape="true" code="security.super.ssl.ca.certificate"/>',
				hideLabel:false,
				labelSeparator:':',
				name:'uploadFile',
				buttonCfg:{
					text:'',
					iconCls:'icon-upload'
				}
			}
		],
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="upload"/>',
				disabled:false,
				handler:function () {
					if (uploadCertIntForm.getForm().isValid()) {
						uploadCertIntForm.getForm().submit({
							url:'<c:url value="security/security_upload_file.ajax"/>',
							params: {uploadType: "certInt", '${_csrf.parameterName}' : '${_csrf.token}'},
							waitTitle:'<spring:message javaScriptEscape="true" code="uploading.file"/>',
							waitMsg:'<spring:message javaScriptEscape="true" code="your.file.is.being.uploaded"/>',
							success:function (form, action) {
                                if (isPageSecure()) {
                                    msg("<spring:message javaScriptEscape="true" code="success"/>", "Server CA Certificates were successfully installed. Your connection will now be reset.", function () {
                                        refreshPageRebootWarning();
                                    });
                                } else {
                                    uploadCertIntForm.getForm().reset();
                                    uploadCertIntWin.hide();
                                    caCertDS.load();
                                }
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
									msg('Error', 'Error occurred.', function () {
									});
								}
							}
						});

					}
				}
			}
		]
	});

	function doCertIntUpload() {
		if (!uploadCertIntWin) {
			uploadCertIntWin = new Ext.Window({
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.select.server.ca.certificate"/>',
				closable:true,
				closeAction:'hide',
				resizable:false,
				width:500,
				autoHeight:true,
				border:true,
				frame:true,
				layout:'fit',
				modal: true,
				items:[
					uploadCertIntForm
				]
			});

		}
		uploadCertIntWin.show(this);

	}
	/////////////////////////////////// applicationsPanel

	var applicationsPanel;
	//shorthand alias
	var fm = Ext.form;

	var networkInterface = new Ext.data.Record.create([
		{name: 'id', type: 'string'},
		{name: 'interfaceName', type: 'string'}
	]);

	var networkInterfaceReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row',
		id: 'id'
	}, networkInterface);

	var interfaceDS = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({method: "POST",url: '<c:url value="security/security_applications_network_interfaces.ajax"/>', timeout: 120000}),
		reader: networkInterfaceReader
	});

	Ext.grid.CheckColumn = function(config){
		Ext.apply(this, config);
		if(!this.id){
			this.id = Ext.id();
		}
		this.renderer = this.renderer.createDelegate(this);
	};

	Ext.grid.CheckColumn.prototype ={
		init : function(grid){
			this.grid = grid;
			this.grid.on('render', function(){
				var view = this.grid.getView();
				view.mainBody.on('mousedown', this.onMouseDown, this);
			}, this);
		},

		onMouseDown : function(e, t){
			if(t.className && t.className.indexOf('x-grid3-cc-'+this.id) != -1){
				e.stopEvent();
				var index = this.grid.getView().findRowIndex(t);
				var record = this.grid.store.getAt(index);
				record.set(this.dataIndex, !record.data[this.dataIndex]);
				this.grid.getStore().each(function(eachRecord){
					if (eachRecord.get('networkInterface') == record.get('networkInterface') &&
							eachRecord.get('securePort') == record.get('securePort')) {
						if(record.get('ocsp')) {
							eachRecord.set('ocsp',true);
						}else {
							eachRecord.set('ocsp',false);
						}

					}
				});
			}
		},

		renderer : function(v, p, record){
			p.css += ' x-grid3-check-col-td';
			return '<div class="x-grid3-check-col'+(v?'-on':'')+' x-grid3-cc-'+this.id+'">&#160;</div>';
		}

	};

	// custom column plugin example
	var checkColumn = new Ext.grid.CheckColumn({
		header: "OCSP",
		dataIndex: 'ocsp',
		width: 150
	});

	var appsColModel = new Ext.grid.ColumnModel([
		{ header:'<spring:message javaScriptEscape="true" code="super.security.applications.tab"/>', dataIndex:'appName', align:'left', width:150},
		{ header: 'Network Interface',
			dataIndex:'networkInterface',
			align:'left',
			editor: new Ext.form.ComboBox({
				triggerAction: 'all',
				mode: 'local',
				store: interfaceDS,
				lazyRender:true,
				listClass: 'x-combo-list-small',
				valueField: 'id',
				displayField: 'interfaceName',
				editable: false,
                listeners: {
                    'select': function(combo, row, index) {
                        applicationsPanel.stopEditing();
                    }
                }
			}),
			width: 150},
		{ header:'HTTP', dataIndex:'unsecurePort', align:'left', sortable:false, width:150, editor:new fm.NumberField({allowBlank:false, vtype:'portNumber', minValue:1, maxValue:65535, name:'unsecport'})},
		{ header:'HTTPS', dataIndex:'securePort', align:'left', sortable:false, width:150, editor:new fm.NumberField({allowBlank:false, vtype:'portNumber', minValue:1, maxValue:65535, name:'secport'})},
			checkColumn
	]);

	var appConfigInfo = Ext.data.Record.create([
		{name:'appName', type:'string'},
		{name:'networkInterface'},
		{name:'securePort', type:'int'},
		{name:'unsecurePort', type:'int'},
		{name:'ocsp', type:'boolean'}
	]);

	var appConfigInfoReader = new Ext.data.XmlReader({
		totalRecords:'results',
		record:'row'
	}, appConfigInfo);

	var appds = new Ext.data.Store({
		proxy:new Ext.data.HttpProxy({method: "POST",url:'<c:url value="security/security_applications_get.ajax"/>', timeout:120000}),
		reader:appConfigInfoReader
	})

	var applicationsPanel = new Ext.grid.EditorGridPanel({
		header:false,
		store:appds,
		cm:appsColModel,
		plugins:checkColumn,
		autoWidth:true,
		height:250,
		clicksToEdit:1,
		frame:true,
		loadMask:false,
		autoScroll:true,
		viewConfig:{
			forceFit:true
		},
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="reset"/>',
				tooltip:'<spring:message javaScriptEscape="true" code="security.super.ssl.revert.to.previously.saved.version"/>',
				handler: function() {appds.reload();}
			},
			{
				text:'<spring:message javaScriptEscape="true" code="save"/>',
				tooltip:'<spring:message javaScriptEscape="true" code="security.super.ssl.save.application.settings"/>',
				handler:doApplySettings

			}
		],
		buttonAlign:'center'
	});

	applicationsPanel.on('beforeedit', function (e) {
		/**
		 * e will have the following properties
		 * grid - This grid
		 * record - The record being edited
		 * field - The field name being edited
		 * value - The value being set
		 * row - The grid row index
		 * column - The grid column index
		 * cancel - Set this to true to cancel the edit or return false from your handler.
		 */
		//Make Grid uneditable if 'IS' is the field and value is 1/true
		if (e.record.get('appName') == 'user') {
			//return false to cancel the edit
			return false;
		}

	});

	applicationsPanel.on('afteredit', function (e) {
		var field = e.field;
		var value = e.value;
        if (field == 'unsecurePort') {
            return;
        } else {
            var record = e.record;
            e.grid.getStore().each(function(eachRecord){
                if (eachRecord.get('networkInterface') == record.get('networkInterface')  &&
                        eachRecord.get('securePort') == record.get('securePort')) {
                    if(record.get('ocsp')) {
                        eachRecord.set('ocsp',true);
                    }else {
                        eachRecord.set('ocsp',false);
                    }

                }
            });
        }
	});

	function doApplySettings() {
        applicationsPanel.stopEditing();
		var modifiedRows = applicationsPanel.getStore().getModifiedRecords();
		//Construct post params
		var params = "";
		var superApp = false;
		var superAppSecPort = '';
		var superAppUnsecPort = '';
		for (i = 0; i < modifiedRows.length; i++) {
			if (modifiedRows[i].data.unsecurePort == modifiedRows[i].data.securePort) {
				msg('<spring:message javaScriptEscape="true" code="error"/>', '<br>' + '<spring:message javaScriptEscape="true" code="ports.have.to.be.different"/>', function () {
				});
				return;
			}
			params += modifiedRows[i].data.appName + ":" + modifiedRows[i].data.networkInterface + ":" +
					modifiedRows[i].data.unsecurePort + ":" + modifiedRows[i].data.securePort + ":" + modifiedRows[i].data.ocsp;
			if (i != (modifiedRows.length - 1)) {
				params += "|";
			}
			if (modifiedRows[i].data.appName == 'super') {
				superApp = true;
			}
		}
		if (params == "") {
			msg('<spring:message javaScriptEscape="true" code="error"/>', '<br>' + '<spring:message javaScriptEscape="true" code="no.modifications"/>', function () {
			});
			return;
		}
		Ext.Ajax.request({
			url:'<c:url value="security/security_applications_update.ajax"/>',
			params:{appconfig:params}, // send the edited column and new value to background
			success:function (res) {
				var reader = new Ext.data.XmlReader({
							success:'@success',
							record:'field'
						}, [
					'id', 'msg'
				]
				);

				var results = reader.read(res);
				if (results.success) {
					msg('<spring:message javaScriptEscape="true" code="status"/>', '<spring:message javaScriptEscape="true" code="changes.successfully.applied"/>',
							function () {
								if (!superApp) {
									applicationsPanel.getStore().reload();
									applicationsPanel.getStore().rejectChanges();
								} else {
									window.location = '<c:url value="logout.html"/>';
								}
							}
					);
				} else {
					var errors = '';
					if ((results != null) && (!results.success)) {
						for (var i = 0; i < results.records.length; i++) {
							errors += results.records[i].data.msg + '<br>';
						}
					}
					if (errors != '') {
						msg('Error', '<br>' + errors, function () {
						});
					}
				}

			},
			failure:function (res) {
				msg('<spring:message javaScriptEscape="true" code="error"/>', 'Error occurred while saving.', function () {
				});
			}
		});
	}

	///////////////////////////////// advancedPanel


	var ocspForm = new Ext.FormPanel({
		url: '<c:url value="security/security_advanced_ocsp.ajax" />',
		labelWidth: 200,
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				}, [
			'id', 'msg'
		]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items: [
			{
				xtype:'label',
				html: '<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.private.ocsp.notes"/></div>'
			},
			{
				xtype: 'checkbox',
				name: 'ocspEnable',
				checked: <c:out value="${model.ocspEnabledFlag}"/>,
				id: 'ocspEnable',
				fieldLabel: "<spring:message javaScriptEscape="true" code="super.security.private.ocsp.enable"/>",
				listeners: {
					check: function(checkbox, checked) {
						if (checked) {
							Ext.getCmp("overrideResponder").setDisabled(false);
							Ext.getCmp("defaultResponder").setDisabled(true);
						} else {
							Ext.getCmp("overrideResponder").setDisabled(true);
							Ext.getCmp("defaultResponder").setDisabled(true);
						}
					}
				}

			},
			{
				xtype: 'checkbox',
				name: 'overrideResponder',
				id: 'overrideResponder',
				checked: <c:out value="${model.ocspOverrideFlag}"/>,
				fieldLabel: "<spring:message javaScriptEscape="true" code="super.security.private.ocsp.override.responder"/>",
				listeners: {
					check: function(checkbox, checked) {
						if (checked) {
							Ext.getCmp("defaultResponder").setDisabled(false);
						} else {
							Ext.getCmp("defaultResponder").setDisabled(true);
						}
					}
				}
			},
			{
				xtype: 'textfield',
				name: 'defaultResponder',
				id: 'defaultResponder',
				fieldLabel: 'Responder URL',
				value: '<c:out value="${model.ocspDefaultResponder}"/>',
				allowBlank: false,
				width: 300
			}
		],
		buttons: [
			{ text: 'Save OCSP Settings', id: 'ocspSaveButton', handler: saveOCSPSettings}
		]
	});

	Ext.getCmp("overrideResponder").setDisabled(!Ext.getCmp("ocspEnable").getValue());
	Ext.getCmp("defaultResponder").setDisabled(!Ext.getCmp("overrideResponder").getValue());

	var advancedActionsPanel = new Ext.Panel({
		header:false,
		layout:'table',
		layoutConfig: {
			columns:2,
			align: "right"
		},
		items:[
			{
				xtype: 'label',
				html: '<div style="padding: 5px"><spring:message javaScriptEscape="true" code="super.security.ssl.advanced.upload.security.settings.from.a.p7b.pfx.or.vidyo.file"/></div>'
			},
			{
				xtype:'button',
				id:'importBundle',
				text:'<spring:message javaScriptEscape="true" code="super.ssl.advanced.button.import.certificate.bundle"/>',
				handler:doUpload,
				tooltip:'<spring:message javaScriptEscape="true" code="super.security.ssl.upload.p7b.pfx.vidyo.file"/>',
				minWidth: 210
			},
			{
				xtype: 'label',
				html: '<div style="padding: 5px"><spring:message javaScriptEscape="true" code="super.security.ssl.advanced.upload.trusted.client.ca.root.certs"/></div>'
			},
			{

				xtype:'button',
				id:'importRoot',
				text:'<spring:message javaScriptEscape="true" code="super.ssl.advanced.button.import.client.ca.certificates"/>',
				handler:doImportRoot,
				tooltip:'<spring:message javaScriptEscape="true" code="super.security.ssl.upload.client.ca.root.certificates"/>',
				minWidth: 210
			},
			{
				xtype: 'label',
				html: '<div style="padding: 5px"><spring:message javaScriptEscape="true" code="super.security.ssl.export.current.security.settings"/></div>'
			},
			{
				xtype:'button',
				id:'exportBundle',
				iconCls: 'save',
				text:'<spring:message javaScriptEscape="true" code="super.ssl.advanced.button.export.security.bundle"/>',
				handler:doExportWithPassword,
				tooltip:'<spring:message javaScriptEscape="true" code="super.security.ssl.export.vidyo.file"/>',
				minWidth: 210
			},
			{
				xtype: 'label',
				html: '<div style="padding: 5px"><spring:message javaScriptEscape="true" code="super.security.ssl.reset.current.security.settings.to.defaults"/></div>'
			},
			{
				xtype:'button',
				id:'factoryReset',
				iconCls: 'reset',
				text:'<spring:message javaScriptEscape="true" code="super.ssl.advanced.button.reset.security"/>',
				handler:doReset,
				tooltip:'<spring:message javaScriptEscape="true" code="super.security.ssl.reset.all.security.settings.to.factory.defaults"/>',
				minWidth: 210
			},
            {
                xtype: 'label',
                html: '<div style="padding: 5px">Configure trusted client CA root certificates</div>'
            },
            {
                xtype:'button',
                id:'useVidyoDefault',
                text:'Configure Client CA Certificates...',
                handler:doConfigureRoot,
                tooltip:'Enable or disable the use of the default client CA root certificates.',
                minWidth: 210
            }
		]
	});

	var ocspNotSupportedPanel = new Ext.FormPanel({
		items: {
			xtype:'label',
			html: '<div style="padding-bottom: 10px;"><spring:message javaScriptEscape="true" code="super.security.private.ocsp.apache.not.supported"/> <spring:message javaScriptEscape="true" code="super.security.private.ocsp.apache.version"/> <c:out value="${model.apacheVersion}"/></div>'
		}
	});

	var advancedPanel = new Ext.Panel({
		frame: true,
		items:[
			{
				xtype: 'fieldset',
				title: 'Actions',
				autoHeight: true,
				items:[
					advancedActionsPanel
				]

			},
			{
				xtype: 'fieldset',
				title: 'OCSP',
				autoHeight: true,
				items:[
						<c:choose>
							<c:when test="${model.ocspSupportFlag}">
								ocspForm
							</c:when>
							<c:otherwise>
								ocspNotSupportedPanel
							</c:otherwise>
						</c:choose>
				]

			}]
	});

	function saveOCSPSettings() {
		ocspForm.getForm().submit({
			success: function(form, action) {
				msg("Success", "<spring:message javaScriptEscape="true" code="super.security.private.ocsp.update.success"/>", null);
			},
			failure: function(form, action) {
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
					msg('Error', 'Failed to save OCSP settings.', function () {
					});
				}
			}

		});
	}


	var uploadWin;
	var uploadForm = new Ext.FormPanel({
		baseCls:'x-plain',
		fileUpload:true,
		frame:false,
		autoHeight:true,
		bodyStyle:'padding: 10px 10px 0px 10px',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				},
				[ 'id', 'msg' ]
		),
		defaults:{
			anchor:'95%',
			allowBlank:false,
			msgTarget:'side'
		},
		items:[
			{
				xtype:'label',
				html:'<div style="padding: 5px 0px; color: red;"><spring:message javaScriptEscape="true" code="super.security.advanced.warning.importing.will.replace.any.current.ssl.configurations"/></div>'
			},
			{
				xtype:'fileuploadfield',
				emptyText:'<spring:message javaScriptEscape="true" code="super.security.ssl.select.a.p7b.pfx.or.vidyo.file"/>',
				fieldLabel:'<spring:message javaScriptEscape="true" code="super.security.ssl.bundle"/>',
				hideLabel:false,
				labelSeparator:':',
				name:'certBundleFile',
				buttonCfg:{
					text:'',
					iconCls:'icon-upload'
				}},
			{
				xtype:'textfield',
				inputType:'password',
				fieldLabel:'<spring:message javaScriptEscape="true" code="super.security.ssl.password.if.any"/>',
				hideLabel:false,
				labelSeparator:':',
				name:'bundlePassword',
				allowBlank:true
			}
		],
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="upload"/>',
				disabled:false,
				handler:function () {
					if (uploadForm.getForm().isValid()) {
						uploadForm.getForm().submit({
                            timeout: 120000,
							url:'<c:url value="security/security_advanced_upload_bundle.ajax"/>',
                            params: {'${_csrf.parameterName}' : '${_csrf.token}' },
							waitTitle:'<spring:message javaScriptEscape="true" code="uploading.file"/>',
							waitMsg:'<spring:message javaScriptEscape="true" code="your.file.is.being.uploaded"/>',
							success:function (form, action) {
								if (uploadForm.getForm().findField("certBundleFile").getValue().indexOf(".vidyo") != -1) {
									Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', '<spring:message javaScriptEscape="true" code="super.secuirty.ssl.settings.have.been.reset.do.you.want.to.reboot.the.server.now"/>', function (btn) {
										if (btn == 'yes') {
											Ext.MessageBox.alert('<spring:message javaScriptEscape="true" code="system.rebooting"/>', '<spring:message javaScriptEscape="true" code="please.refresh.the.page.after.system.restarted"/>');
											doReboot();
										} else {
											showRebootWarning();
										}
									});
								} else {
									uploadForm.getForm().reset();
									uploadWin.hide();
									Ext.Msg.show({
										title:'<spring:message javaScriptEscape="true" code="upload.success"/>',
										msg:'<spring:message javaScriptEscape="true" code="successfuly.uploaded.file"/>',
										minWidth:300,
										modal:true,
										icon:Ext.Msg.INFO,
										fn:function () {
										}
									});
								}
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
									msg('Error', 'Error occurred.', function () {
									});
								}
							}
						});

					}
				}
			}
		]
	});

	function doUpload() {
		if (!uploadWin) {
			uploadWin = new Ext.Window({
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.select.bundle.pkcs.7.pkcs.12.or.vidyo"/>',
				closable:true,
				closeAction:'hide',
				resizable:false,
				width:500,
				autoHeight:true,
				border:true,
				frame:true,
				layout:'fit',
				modal: true,
				items:[
					uploadForm
				]
			});

		}
		uploadWin.show(this);

	}


	var exportBundleWin;
	var exportBundleForm = new Ext.FormPanel({
		baseCls:'x-plain',
		fileUpload:true,
		frame:false,
		autoHeight:true,
		bodyStyle:'padding: 10px 10px 0px 10px',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				},
				[ 'id', 'msg' ]
		),
		defaults:{
			allowBlank:false,
			msgTarget:'side'
		},
		items:[
			{
				xtype: 'textfield',
				inputType: 'password',
				vtype: 'passwordOne',
				compareToField: "exportBundlePassword2",
				id: "exportBundlePassword1",
				hideLabel:false,
				labelSeparator:':',
				fieldLabel: '<spring:message javaScriptEscape="true" code="password"/>',
				name: 'bundlePassword',
				required: true
			},
			{
				xtype: 'textfield',
				inputType: 'password',
				vtype: 'passwordTwo',
				compareToField: "exportBundlePassword1",
				id: "exportBundlePassword2",
				hideLabel:false,
				labelSeparator:':',
				fieldLabel: '<spring:message javaScriptEscape="true" code="confirm.password"/>',
				name: 'bundlePassword2',
				required: true
			}
		],
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="export"/>',
				disabled:false,
				handler:function () {
					if (exportBundleForm.getForm().isValid()) {
						exportBundleWin.hide();
						exportBundleForm.getForm().submit({
							timeout: 120000,
							url:'<c:url value="security/security_advanced_export_bundle.ajax"/>',
							params: {'${_csrf.parameterName}' : '${_csrf.token}' },
							success:function (form, action) {
								exportBundleForm.getForm().reset();
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
									msg('Error', 'Error occurred.', function () {
									});
								}
							}
						});

					}
				}
			}
		]
	});

	function doExportWithPassword() {
		if (!exportBundleWin) {
			exportBundleWin = new Ext.Window({
				title:'<spring:message javaScriptEscape="true" code="export.security.bundle"/>',
				closable:true,
				closeAction:'hide',
				resizable:false,
				width:325,
				autoHeight:true,
				border:true,
				frame:true,
				layout:'fit',
				modal: true,
				items:[
					exportBundleForm
				]
			});

		}
		exportBundleForm.getForm().reset();
		exportBundleWin.show(this);
	}

	function doReset() {
		Ext.Msg.confirm(
				'<spring:message javaScriptEscape="true" code="reset"/>',
				'<spring:message javaScriptEscape="true" code="super.security.ssl.reset.all.security.settings.to.factory.defaults.ssl.will.be.disabled.and.a.restart.will.be.necessary"/>',
				processReset
		);
	}


	function processReset(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_advanced_factory_reset.ajax"/>';
			if (!this.addFactoryResetForm) {
				var addFactoryResetFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.addFactoryResetForm = new Ext.form.BasicForm(addFactoryResetFormEl, {
                    method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
						'id', 'msg'
					]
					)
				});
			}
			this.addFactoryResetForm.submit({
                timeout: 120000,
				waitTitle:'Reset Security',
				waitMsg:'Resetting SSL Security Settings...',
				success:function (form, action) {
					Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', '<spring:message javaScriptEscape="true" code="super.secuirty.ssl.settings.have.been.reset.do.you.want.to.reboot.the.server.now"/>', function (btn) {
						if (btn == 'yes') {
							Ext.MessageBox.alert('<spring:message javaScriptEscape="true" code="system.rebooting"/>', '<spring:message javaScriptEscape="true" code="please.refresh.the.page.after.system.restarted"/>');
							doReboot();
						}else {
							showRebootWarning();
						}
					});
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
						msg('Error', 'Reset failed', function () {
						});
					}
				}
			});
		}
	}

    var useDefaultRootCerts = <c:out value="${model.useDefaultRootCerts}"/>;
    var configureRootWin;
    var configureRootForm = new Ext.FormPanel(
            {
                baseCls:'x-plain',
                frame:false,
                autoHeight:true,
                labelAlign: 'side',
                labelWidth: 320,
                bodyStyle:'padding: 5px 0px 0px 0px',
                errorReader:new Ext.data.XmlReader({
                            success:'@success',
                            record:'field'
                        },
                        [ 'id', 'msg' ]
                ),
                defaults: {
                    labelStyle: 'margin:0 0 0 -11px;font-weight:bold;text-align:right;'
                },
                items:[
                    {
                        xtype:'radiogroup',
                        hideLabel:false,
                        fieldLabel:'Default trusted client CA root certificates:<br />Any changes will require a restart.',
                        columns: 1,
                        labelSeparator:' ',
                        items:[
                            {boxLabel:'Enabled', name:'useDefault', inputValue:'on', checked: useDefaultRootCerts},
                            {boxLabel:'Disabled', name:'useDefault', inputValue:'off', checked: !useDefaultRootCerts}
                        ]
                    }
                ],
                buttons:[
                    {
                        text:'<spring:message javaScriptEscape="true" code="save"/>',
                        disabled:false,
                        handler:function () {
                            if (configureRootForm.getForm().isValid()) {
                                configureRootForm.getForm().submit({
                                    timeout: 120000,
                                    url:'<c:url value="security/security_use_default_root.ajax"/>',
                                    waitTitle:'Saving settings...',
                                    waitMsg:'Saving settings....',
                                    success:function (form, action) {
                                        configureRootWin.hide();
                                        Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', 'Configuration saved. Do you want to reboot the server now?', function (btn) {
                                            if (btn == 'yes') {
                                                Ext.MessageBox.alert('<spring:message javaScriptEscape="true" code="system.rebooting"/>', '<spring:message javaScriptEscape="true" code="please.refresh.the.page.after.system.restarted"/>');
                                                doReboot();
                                            } else {
                                                showRebootWarning();
                                            }
                                        });
                                    },
                                    failure:function (form, action) {
                                        configureRootForm.getForm().reset();
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
                                            msg('Error', 'Error occurred.', function () {
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        text: 'Cancel',
                        handler: function() {
                            configureRootForm.getForm().reset();
                            configureRootWin.hide();
                        }
                    }
                ]
            }
    );

    function doConfigureRoot() {
        if (!configureRootWin) {
            configureRootWin = new Ext.Window({
                title:'Configure Client CA Certificates',
                closable:true,
                closeAction:'hide',
                resizable:false,
                width:500,
                autoHeight:true,
                border:true,
                frame:true,
                layout:'fit',
                modal: true,
                items:[
                    configureRootForm
                ]
            });
        }
        configureRootWin.show(this);
    }

	var importRootWin;
	var importRootForm = new Ext.FormPanel({
		baseCls:'x-plain',
		fileUpload:true,
		frame:false,
		autoHeight:true,
		bodyStyle:'padding: 10px 10px 0px 10px',
		errorReader:new Ext.data.XmlReader({
					success:'@success',
					record:'field'
				},
				[ 'id', 'msg' ]
		),
		defaults:{
			anchor:'95%',
			allowBlank:false,
			msgTarget:'side'
		},
		items:[
			{
				xtype:'fileuploadfield',
				emptyText:'<spring:message javaScriptEscape="true" code="security.super.ssl.select.a.pem.chain.file"/>',
				fieldLabel:'Bundle:',
				hideLabel:false,
				name:'rootCertsFile',
				labelSeparator:' ',
				buttonCfg:{
					text:'',
					iconCls:'icon-upload'
				}},
			{
				xtype:'radiogroup',
				hideLabel:false,
				fieldLabel:' ',
				labelSeparator:' ',

				items:[
					{boxLabel:'<spring:message javaScriptEscape="true" code="security.super.ssl.replace.existing"/>', name:'actionType', inputValue:'replace'},
					{boxLabel:'<spring:message javaScriptEscape="true" code="security.super.ssl.append.to.existing"/>', name:'actionType', inputValue:'append', checked:true}
				]
			}
		],
		buttons:[
			{
				text:'<spring:message javaScriptEscape="true" code="upload"/>',
				disabled:false,
				handler:function () {
					if (importRootForm.getForm().isValid()) {
						importRootForm.getForm().submit({
                            timeout: 120000,
							url:'<c:url value="security/security_advanced_upload_root.ajax"/>',
                            params: {'${_csrf.parameterName}' : '${_csrf.token}' },
							waitTitle:'<spring:message javaScriptEscape="true" code="uploading.file"/>',
							waitMsg:'<spring:message javaScriptEscape="true" code="your.file.is.being.uploaded"/>',
							success:function (form, action) {
								importRootForm.getForm().reset();
								importRootWin.hide();
								Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', '<spring:message javaScriptEscape="true" code="super.ssl.upload.successful.do.you.want.to.reboot.the.server.now"/>', function (btn) {
									if (btn == 'yes') {
										Ext.MessageBox.alert('<spring:message javaScriptEscape="true" code="system.rebooting"/>', '<spring:message javaScriptEscape="true" code="please.refresh.the.page.after.system.restarted"/>');
										doReboot();
									} else {
										showRebootWarning();
									}
								});
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
									msg('Error', 'Error occurred.', function () {
									});
								}
							}
						});

					}
				}
			}
		]
	});

	function doImportRoot() {
		if (!importRootWin) {
			importRootWin = new Ext.Window({
				title:'Select file',
				closable:true,
				closeAction:'hide',
				resizable:false,
				width:500,
				autoHeight:true,
				border:true,
				frame:true,
				layout:'fit',
				modal: true,
				items:[
					importRootForm
				]
			});
		}
		importRootWin.show(this);
	}


	var refresher = {
		run:do_refresh,
		interval:10000
	};

    var serverTimeFail = false;
    function do_refresh() {

        Ext.Ajax.request({
            disableCaching : true,
            method: 'POST',
            url : '<c:url value="serverstartedtime.ajax"/>',
            success : function (response, options) {
                var xml = response.responseXML;

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


    function doReboot() {
		var url = '<c:url value="securedmaint/maintenance_system_restart.ajax"/>';
		if (!this.addRebootForm) {
			var addRebootFormEl = Ext.get("container").createChild({
				tag:'form',
				style:'display:none'
			});
			this.addRebootForm = new Ext.form.BasicForm(addRebootFormEl, {
                method: "POST",
				url:url,
				errorReader:new Ext.data.XmlReader({
							success:'@success',
							record:'field'
						}, [
					'id', 'msg'
				]
				)
			});
		}
		Ext.TaskMgr.start(refresher);
		this.addRebootForm.submit({
			waitTitle:'<spring:message javaScriptEscape="true" code="the.system.is.going.down.for.reboot.now"/>',
			waitMsg:'<spring:message javaScriptEscape="true" code="system.rebooting"/>',
			success:function (form, action) {
				Ext.Msg.show({
					title:'<spring:message javaScriptEscape="true" code="the.system.is.going.down.for.reboot.now"/>',
					msg:'<spring:message javaScriptEscape="true" code="system.rebooting"/>',
					minWidth:300,
					modal:true,
					icon:Ext.Msg.INFO,
					fn:function () {
					}
				});
			},
			failure:function (form, action) {
				var errors = '';
				if ((action.result != null) && (!action.result.success)) {
					for (var i = 0; i < action.result.errors.length; i++) {
						errors += action.result.errors[i].msg + '<br>';
					}
				}
				if (errors != '') {
					msg('<spring:message javaScriptEscape="true" code="error"/>', '<br>' + errors, function () {
					});
				}
				else {
					msg('<spring:message javaScriptEscape="true" code="the.system.is.going.down.for.reboot.now"/>', '<spring:message javaScriptEscape="true" code="system.rebooting"/>', function () {
					});
				}
			}
		});
	}

	var sslEnabledFlag = <c:out value="${model.sslEnabledFlag}"/>;
	var httpsOnlyFlag = <c:out value="${model.httpsOnlyFlag}"/>;
	var httpsOnlyFlagNoRedirect = <c:out value="${model.httpsOnlyFlagNoRedirect}"/>;
	var privilegedMode = <c:out value="${model.privilegedMode}"/>;
	
	var refreshUI = function() {
		if (sslEnabledFlag == 1) {
			Ext.getCmp('secureLockIcon').show();

			Ext.getCmp('keyRegenerateButton').setDisabled(true);
			Ext.getCmp('keyUploadButton').setDisabled(true);
			Ext.getCmp('bits').setDisabled(true);

			Ext.getCmp('certResetButton').setDisabled(true);
			Ext.getCmp('certUpdateButton').setDisabled(true);
			//Ext.getCmp('certUploadButton').setDisabled(true);
			//Ext.getCmp('certGenerateButton').setDisabled(true);
			//Ext.getCmp('cert').setDisabled(true);
            //document.getElementById("cert").readOnly = true;

			//Ext.getCmp('certIntUploadButton').setDisabled(true);

			Ext.getCmp('importBundle').setDisabled(true);
			//Ext.getCmp('importRoot').setDisabled(true);
			Ext.getCmp('factoryReset').setDisabled(true);

			Ext.getCmp("enableSSLButton").setText("<spring:message javaScriptEscape="true" code="super.security.ssl.disable"/>");
			Ext.getCmp("httpsOnlyCheckbox").setDisabled(false);
		} else {
			Ext.getCmp('secureLockIcon').hide();

			Ext.getCmp('keyRegenerateButton').setDisabled(false);
			Ext.getCmp('keyUploadButton').setDisabled(false);
			Ext.getCmp('bits').setDisabled(false);

			Ext.getCmp('certGenerateButton').setDisabled(false);
			Ext.getCmp('certUploadButton').setDisabled(false);
		    Ext.getCmp('cert').setDisabled(false);
            document.getElementById("cert").readOnly = false;

			Ext.getCmp('certIntUploadButton').setDisabled(false);

			Ext.getCmp('importBundle').setDisabled(false);
			Ext.getCmp('importRoot').setDisabled(false);
			Ext.getCmp('factoryReset').setDisabled(false);

			Ext.getCmp("enableSSLButton").setText("<spring:message javaScriptEscape="true" code="super.security.ssl.enable"/>");

			Ext.getCmp("httpsOnlyCheckbox").suspendEvents(false);
			Ext.getCmp("httpsOnlyCheckbox").setValue(false);
			Ext.getCmp("httpsOnlyCheckbox").resumeEvents();
			Ext.getCmp("httpsOnlyCheckbox").setDisabled(true);

			Ext.getCmp("httpsOnlyNoRedirectCheckbox").suspendEvents(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setValue(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").resumeEvents();
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setDisabled(true);
		}

		Ext.getCmp("httpsOnlyCheckbox").suspendEvents(false);
		if (httpsOnlyFlag == 1) {
			Ext.getCmp("httpsOnlyCheckbox").setValue(true);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setDisabled(false);


			Ext.getCmp("httpsOnlyNoRedirectCheckbox").suspendEvents(false);
			if (httpsOnlyFlagNoRedirect == 1) {
				Ext.getCmp("httpsOnlyNoRedirectCheckbox").setValue(true);
			} else {
				Ext.getCmp("httpsOnlyNoRedirectCheckbox").setValue(false);
			}
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").resumeEvents();

		} else {
			Ext.getCmp("httpsOnlyCheckbox").setValue(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setDisabled(true);

			Ext.getCmp("httpsOnlyNoRedirectCheckbox").suspendEvents(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setValue(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").resumeEvents();
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setDisabled(true);
		}
		Ext.getCmp("httpsOnlyCheckbox").resumeEvents();


	}

	////////////////////////////////

	//var privateKeyTabLoaded = false;
	//var csrTabLoaded = false;
	//var certTabLoaded = false;
	//var intermediateTabLoaded = false;
	//var applicationsTabLoaded = false;

	var tabs = new Ext.TabPanel({
		activeTab:0,
		plain:false,
		autoWidth:true,
		autoHeight:true,
		enableTabScroll:true,
		deferredRender:false,
		layoutOnTabChange:true,
		defaults:{
			autoHeight:true
		},
		items:[
			{
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.private.key.tab"/>',
				listeners:{
					activate:function (p) {
						<c:if test="${model.privilegedMode}">
							keyDS.load();
						</c:if>
						activeDestination = '';
					}
				},
				items:[
					keyPanel
				]
			},
			{
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.csr.tab"/>',
				buttonAlign:'center',
				listeners:{
					activate:function (p) {
						//if (!csrTabLoaded) {
							csrDS.load();
						//	csrTabLoaded = true;
						//}
						activeDestination = '';
					}
				},
				items:[
					csrPanel
				]
			},
			{
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.certificate.tab"/>',
				buttonAlign:'center',
				listeners:{
					activate:function (p) {
						//if (!certTabLoaded) {
							certDS.load();
						//	certTabLoaded = true;
						//}
						activeDestination = '';
					}
				},
				items:[
					serverCertPanel
				]
			},
			{
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.certificates.bundle.tab"/>',
				listeners:{
					activate:function (p) {
						//if (!intermediateTabLoaded) {
							caCertDS.load();
						//	intermediateTabLoaded = true;
						//}
						activeDestination = '';
					}
				},
				items:[
					intermediateCertsPanel
				]
			},
			{
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.applications.tab"/>',
				listeners:{
					activate:function (p) {
						//if (!applicationsTabLoaded) {
						interfaceDS.load();
							appds.load();
						//	applicationsTabLoaded = true;
						//}
						activeDestination = '';
					}
				},
				items:[
					applicationsPanel
				]
			}
			,
			{
				title:'<spring:message javaScriptEscape="true" code="super.security.ssl.certificates.advanced.tab"/>',
				listeners:{
					activate:function (p) {
						activeDestination = '';
					}
				},
				items:[
					advancedPanel
				]
			}
		]
	});


	var toggleSSL = function() {
		if (sslEnabledFlag == 1) {
			Ext.Msg.confirm(
					'<spring:message javaScriptEscape="true" code="super.security.ssl.disable"/>',
					'<spring:message javaScriptEscape="true" code="super.security.ssl.are.you.sure.you.want.to.disable.ssl"/>',
					processDisableSSL
			);

		} else {
			Ext.Msg.confirm(
					'<spring:message javaScriptEscape="true" code="super.security.ssl.enable"/>',
					'<spring:message javaScriptEscape="true" code="super.security.ssl.are.you.sure.you.want.to.enable.ssl"/>',
					processEnableSSL
			);
		}
	}

	function processEnableSSL(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_command.ajax"/>';
			if (!this.enableSSLForm) {
				var enableSSLFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.enableSSLForm = new Ext.form.BasicForm(enableSSLFormEl, {
                    method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
						'id', 'msg'
					]
					)
				});
			}
			this.enableSSLForm.submit({
				waitTitle:'<spring:message javaScriptEscape="true" code="super.security.ssl.enable"/>',
				waitMsg:'<spring:message javaScriptEscape="true" code="super.security.ssl.enabling.ssl.security.settings"/>',
				params : { command : "enable"},
				success:function (form, action) {
					sslEnabledFlag = 1;
					refreshUI();
					msg('<spring:message javaScriptEscape="true" code="success"/>', '<spring:message javaScriptEscape="true" code="super.security.ssl.ssl.has.been.enabled"/>', handleSSLChangeRedirect);
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
						msg('Error', '<spring:message javaScriptEscape="true" code="super.security.ssl.ssl.enable.failed"/>', function () {
						});
					}
				}
			});
		}
	}

	function processDisableSSL(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_command.ajax"/>';
			if (!this.disableSSLForm) {
				var disableSSLFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.disableSSLForm = new Ext.form.BasicForm(disableSSLFormEl, {
                    method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
						'id', 'msg'
					]
					)
				});
			}
			this.disableSSLForm.submit({
				waitTitle:'<spring:message javaScriptEscape="true" code="super.security.ssl.disable"/>',
				waitMsg:'<spring:message javaScriptEscape="true" code="super.security.ssl.disabling.ssl.security.settings"/>',
				params : { command : "disable"},
				success:function (form, action) {
					sslEnabledFlag = 0;
					refreshUI();
                    if (httpsOnlyFlag == 1) {
                        setRebootMessageCookie();
                    }
					msg('<spring:message javaScriptEscape="true" code="success"/>', '<spring:message javaScriptEscape="true" code="super.security.ssl.ssl.has.been.disabled"/>', handleSSLChangeRedirect);
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
						msg('Error', '<spring:message javaScriptEscape="true" code="super.security.ssl.ssl.disable.failed"/>', function () {
						});
					}
				}
			});
		}
	}

    function createCookie(name,value,days) {
        if (days) {
            var date = new Date();
            date.setTime(date.getTime()+(days*24*60*60*1000));
            var expires = "; expires="+date.toGMTString();
        }
        else var expires = "";
        document.cookie = name+"="+value+expires+"; path=/";
    }

    function readCookie(name) {
        var nameEQ = name + "=";
        var ca = document.cookie.split(';');
        for(var i=0;i < ca.length;i++) {
            var c = ca[i];
            while (c.charAt(0)==' ') c = c.substring(1,c.length);
            if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
        }
        return null;
    }

    function eraseCookie(name) {
        createCookie(name,"",-1);
    }

    function setRebootMessageCookie() {
        createCookie("rebootMsg", "1", 1);
    }

    function checkForRebootMessage() {
        if (readCookie("rebootMsg")) {
            eraseCookie("rebootMsg");
            Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="confirmation"/>', '<spring:message javaScriptEscape="true" code="super.secuirty.ssl.settings.have.been.reset.do.you.want.to.reboot.the.server.now"/>', function (btn) {
                if (btn == 'yes') {
                    Ext.MessageBox.alert('<spring:message javaScriptEscape="true" code="system.rebooting"/>', '<spring:message javaScriptEscape="true" code="please.refresh.the.page.after.system.restarted"/>');
                    doReboot();
                } else {
                    showRebootWarning();
                }
            });
        }
    }

	function processHTTPSOnly(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_command.ajax"/>';
			if (!this.processHTTPSOnlyForm) {
				var processHTTPSOnlyFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.processHTTPSOnlyForm = new Ext.form.BasicForm(processHTTPSOnlyFormEl, {
                    method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
						'id', 'msg'
					]
					)
				});
			}
			this.processHTTPSOnlyForm.submit({
				waitTitle:'<spring:message javaScriptEscape="true" code="super.security.ssl.enable.https.only"/>',
				waitMsg:'<spring:message javaScriptEscape="true" code="super.security.ssl.enabling.https.only"/>',
				params : { command : "forcedEnable"},
				success:function (form, action) {
					httpsOnlyFlag = 1;
					httpsOnlyFlagNoRedirect = 0;
					refreshUI();
					setRebootMessageCookie();
					msg('<spring:message javaScriptEscape="true" code="success"/>', '<spring:message javaScriptEscape="true" code="super.security.ssl.https.only.is.on"/>', handleSSLChangeRedirect);
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
						msg('Error', '<spring:message javaScriptEscape="true" code="super.security.ssl.https.only.failed"/>', function () {
						});
					}
				}
			});
		} else {
				Ext.getCmp("httpsOnlyCheckbox").suspendEvents(false);
				Ext.getCmp("httpsOnlyCheckbox").setValue(false);
				Ext.getCmp("httpsOnlyCheckbox").resumeEvents();
		}
	}

	function processHTTPSOnlyWithRedirect(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_command.ajax"/>';
			if (!this.processHTTPSOnlyForm) {
				var processHTTPSOnlyFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.processHTTPSOnlyForm = new Ext.form.BasicForm(processHTTPSOnlyFormEl, {
					method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
								'id', 'msg'
							]
					)
				});
			}
			this.processHTTPSOnlyForm.submit({
				waitTitle:'Enable HTTP to HTTPS redirect',
				waitMsg:'Enabling HTTP to HTTPS redirect...',
				params : { command : "forcedEnable"},
				success:function (form, action) {
					httpsOnlyFlag = 1;
					httpsOnlyFlagNoRedirect = 0;
					refreshUI();
					msg('<spring:message javaScriptEscape="true" code="success"/>', 'Enabled HTTP to HTTPS redirect.', function() {});
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
						msg('Error', 'Failed to enable HTTP to HTTPS redirect.', function () {
						});
					}
				}
			});
		} else {
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").suspendEvents(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setValue(true);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").resumeEvents();
		}
	}

	function processHTTPSOnlyNoRedirect(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_command.ajax"/>';
			if (!this.processHTTPSOnlyForm) {
				var processHTTPSOnlyFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.processHTTPSOnlyForm = new Ext.form.BasicForm(processHTTPSOnlyFormEl, {
					method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
								'id', 'msg'
							]
					)
				});
			}
			this.processHTTPSOnlyForm.submit({
				waitTitle:'Disable HTTP to HTTPS redirect',
				waitMsg:'Disabling HTTP to HTTPS redirect...',
				params : { command : "forcedEnableNoRedirect"},
				success:function (form, action) {
					httpsOnlyFlagNoRedirect = 1;
					refreshUI();
					//setRebootMessageCookie();
					msg('<spring:message javaScriptEscape="true" code="success"/>', 'Disabled HTTP to HTTPS redirect.', function() {});
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
						msg('Error', 'Failed to disable HTTP to HTTPS redirect.', function () {
						});
					}
				}
			});
		} else {
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").suspendEvents(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").setValue(false);
			Ext.getCmp("httpsOnlyNoRedirectCheckbox").resumeEvents();
		}
	}

	function processDisableHTTPSOnly(e) {
		if (e == 'yes') {
			var url = '<c:url value="security/security_command.ajax"/>';
			if (!this.processDisableHTTPSOnlyForm) {
				var processDisableHTTPSOnlyFormEl = Ext.get("container").createChild({
					tag:'form',
					style:'display:none'
				});
				this.processDisableHTTPSOnlyForm = new Ext.form.BasicForm(processDisableHTTPSOnlyFormEl, {
                    method: "POST",
					url:url,
					errorReader:new Ext.data.XmlReader({
								success:'@success',
								record:'field'
							}, [
						'id', 'msg'
					]
					)
				});
			}
			this.processDisableHTTPSOnlyForm.submit({
				waitTitle:'<spring:message javaScriptEscape="true" code="super.security.ssl.disable.https.only"/>',
				waitMsg:'<spring:message javaScriptEscape="true" code="super.security.ssl.disabling.https.only"/>',
				params : { command : "forcedDisable"},
				success:function (form, action) {
					httpsOnlyFlag = 0;
					refreshUI();
					msg('<spring:message javaScriptEscape="true" code="success"/>', '<spring:message javaScriptEscape="true" code="super.security.ssl.https.only.is.off"/>', handleSSLChangeRedirect);
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
						msg('Error', '<spring:message javaScriptEscape="true" code="super.security.ssl.disabling.https.only.failed"/>', function () {
						});
					}
				}
			});
		} else {
			Ext.getCmp("httpsOnlyCheckbox").suspendEvents(false);
			Ext.getCmp("httpsOnlyCheckbox").setValue(true);
			Ext.getCmp("httpsOnlyCheckbox").resumeEvents();
		}
	}


	var handleSSLChangeRedirect= function() {
		if (location.protocol == 'https:') {
			if (!sslEnabledFlag) {
				var restOfUrl = window.location.href.substr(6);
				window.location = "http:" + restOfUrl;
			} else {
                checkForRebootMessage();
            }
		} else {
			if (httpsOnlyFlag) {
				var restOfUrl = window.location.href.substr(5);
				window.location = "https:" + restOfUrl;
			}
		}
	}

	var buttonPanel = new Ext.Panel({
		layout: "column",
		items:[
			{
				xtype:'label',
				html:'<spring:message javaScriptEscape="true" code="super.security.ssl.enable.description"/> &nbsp;&nbsp;',
				width: 200
			},{
				xtype: "label",
				html: "&nbsp;&nbsp;"
			},
			{
				border: false,
				items: {
					xtype: "button",
					id: "enableSSLButton",
					text: '<spring:message javaScriptEscape="true" code="super.security.ssl.enable"/>',
					handler: toggleSSL
				}
			}, {
				xtype: "label",
				html: "&nbsp;&nbsp;"
			},
			{
				xtype: "checkbox",
				id: "httpsOnlyCheckbox",
				boxLabel: '<spring:message javaScriptEscape="true" code="super.security.forced.https"/>',
				listeners: {
					check: function(checkbox, checked) {
						if (checked) {
							Ext.Msg.confirm(
									'<spring:message javaScriptEscape="true" code="super.security.ssl.enable.https.only"/>',
									'<spring:message javaScriptEscape="true" code="super.security.ssl.are.you.sure.you.want.to.enable.https.only"/>',
									processHTTPSOnly
							);
						} else {
							Ext.Msg.confirm(
									'<spring:message javaScriptEscape="true" code="super.security.ssl.disable.https.only"/>',
									'<spring:message javaScriptEscape="true" code="super.security.ssl.are.you.sure.you.want.to.disable.the.https.only.feature"/>',
									processDisableHTTPSOnly
							);
						}
					}
				}
			}, {
				xtype: "label",
				html: "&nbsp;&nbsp;"
			},
			{
				xtype: "checkbox",
				id: "httpsOnlyNoRedirectCheckbox",
				boxLabel: '<spring:message javaScriptEscape="true" code="disable.http.to.https.redirect"/>',
				listeners: {
					check: function(checkbox, checked) {
						if (checked) {
							Ext.Msg.confirm(
									'<spring:message javaScriptEscape="true" code="disable.http.to.https.redirect"/>',
									'<spring:message javaScriptEscape="true" code="are.you.sure.you.want.to.disable.the.http.to.https.redirect"/>',
									processHTTPSOnlyNoRedirect
							);
						} else {
							Ext.Msg.confirm(
									'<spring:message javaScriptEscape="true" code="enable.http.to.https.redirect"/>',
									'<spring:message javaScriptEscape="true" code="are.you.sure.you.want.to.enable.the.http.to.https.redirect"/>',
									processHTTPSOnlyWithRedirect
							);
						}
					}
				}
			}, {
				xtype: "label",
				html: "&nbsp;&nbsp;"
			},
			{
				xtype: "label",
				id: "secureLockIcon",
				html: "<div style='width: 50px;'><img style='float: right' src='themes/vidyo/i/icon_padlock.png'/></div>"
			}
		]
	});

	var securityPanel = new Ext.Panel({
		title:'<spring:message javaScriptEscape="true" code="security"/>',
		frame:true,
		items:[
			buttonPanel,
			tabs
		],
		bbar: new Ext.StatusBar({
			id: 'statusBar',
			defaultText: '',
			busyText: ''
		})
	});

	var statusBar = Ext.getCmp('statusBar');
	var statusText = '';
	var restartWarning = false;
	var restartText = '<spring:message javaScriptEscape="true" code="super.security.ssl.restart.is.pending.settings.maintenance.system.restart.reboot"/>';

	//Ext.Ajax.on('beforerequest', function() {statusBar.showBusy() },statusBar);
	Ext.Ajax.on('requestcomplete',function() { statusBar.clearStatus(); statusBar.setText(statusText); if(!statusText.trim() && !privilegedMode){showPrivModeWarning();}} , statusBar);
	Ext.Ajax.on('requestexception',function() { statusBar.clearStatus(); statusBar.setText(statusText); if(!statusText.trim() && !privilegedMode){showPrivModeWarning();}}, statusBar);

	function showRebootWarning() {
		statusText = "<div style=\"color: red; padding-top: 3px;\">" + restartText + "</div>";
		statusBar.setText(statusText);
		restartWarning = true;
	}

	function showStatusMessage(str) {
		if (restartWarning == false) {
			statusBar.setText("<div style=\"color: red; padding-top: 3px;\">" +str + "</div>");
		} else {
			statusBar.setText("<div style=\"color: red; padding-top: 3px;\">"  + str + " " + restartText + "</div>");
		}
		if(!privilegedMode) {
			showPrivModeWarning();
		}		
	}
	
	function showPrivModeWarning(){
    	// Show Privileged mode error if Privileged Mode is not enabled
		var privStatusText = "<div style=\"color: red; font-weight:bold; padding-top: 3px;\">" + "Privileged Mode is not enabled. Security settings cannot be viewed or modified" + "</div>";
		Ext.getCmp('statusBar').setText(privStatusText);		
	}	

	new Ext.Panel({
		renderTo:'content-panel',
		border:false,
		items:[
			{
				layout:'column',
				border:false,
				items:[
					{
						columnWidth:.25,
						baseCls:'x-plain',
						bodyStyle:'padding:5px 5px 5px 5px',
						id:'local',
						items:[
							localmenu
						]
					},
					{
						columnWidth:.75,
						baseCls:'x-plain',
						bodyStyle:'padding:5px 5px 5px 5px',
						items:[
							securityPanel
						]
					}
				]
			}
		]
	});

	refreshUI();

    checkForRebootMessage();
    if(!privilegedMode) {
        buttonPanel.setDisabled(true);
        tabs.setDisabled(true);
    }    
});

</script>