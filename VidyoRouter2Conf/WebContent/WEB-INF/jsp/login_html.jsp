<%@ page import="com.vidyo.service.RouterConfigServiceImpl" %>
<%@ include file="include_html.jsp" %>

	<body>

		<div id="container">

			<div id="header">
				<div id="logo"><img src="themes/vidyo/i/logo.gif" border="0"></div>
			</div>

			<div id="maincontent">

                <c:choose>
                    <c:when test="${model.accountLocked}">
                            <div class="msg-warning">
                                <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="warning"/></vidyo:replaceString></h1>
                                <p>&raquo; Account locked for 60 seconds due to too many failed attempts.</p>
                            </div>
                    </c:when>
                    <c:when test="${not empty param.error}">
                        <div class="msg-warning">
                            <c:if test="${param.error == 1}">
                                <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="warning"/></vidyo:replaceString></h1>
                                <p>&raquo; <vidyo:replaceString from="\\\\'" to="'"><spring:message code="your.username.or.password.are.invalid"/></vidyo:replaceString></p>
                            </c:if>
                            <c:if test="${param.error == 2}">
                                <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="access.denied"/></vidyo:replaceString></h1>
                                <p>&raquo; <vidyo:replaceString from="\\\\'" to="'"><spring:message code="we.re.sorry.but.you.are.not.authorized.to.perform.the.requested.operation"/></vidyo:replaceString></p>
                            </c:if>
                            <c:if test="${param.error == 3}">
                                <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="warning"/></vidyo:replaceString></h1>
                                <p>&raquo; <vidyo:replaceString from="\\\\'" to="'"><spring:message code="your.session.is.expired.please.login.again"/></vidyo:replaceString></p>
                            </c:if>
                            <c:if test="${param.error == 4}">
                                <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="warning"/></vidyo:replaceString></h1>
                                <p>&raquo; <vidyo:replaceString from="\\\\'" to="'"><spring:message code="user.login.elsewhere.concurent.login"/></vidyo:replaceString></p>
                            </c:if>
                            <c:if test="${param.error == 5}">
                                <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="warning"/></vidyo:replaceString></h1>
                                <p>&raquo; <vidyo:replaceString from="\\\\'" to="'"><spring:message code="user.account.locked.out"/></vidyo:replaceString></p>
                            </c:if>
                        </div>
                    </c:when>
                </c:choose>

				<div id="themes" style="float: left">&nbsp;</div>

				<div id="login" style="padding-top: 300px;">&nbsp;</div>

                <div id="usgmsg" style="margin-top: -130px;">&nbsp;</div>

				<div id="timeoutmsg">&nbsp;</div>

				<div id="cookieMessg" style="visibility: hidden;">
					 <h1><vidyo:replaceString from="\\\\'" to="'"><spring:message code="warning"/></vidyo:replaceString></h1>
					 <p>&raquo;  <vidyo:replaceString from="\\\\'" to="'"><spring:message code="enable.browser.cookies"/></vidyo:replaceString></p>
				</div>

			</div>


<script type='text/javascript' src='<c:url value="/js/IconCombo.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/FieldOverride.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ComboLoadValue.js"/>'></script>

<script type="text/javascript">

Ext.onReady(function(){
     var  loginBanner = "";
    var  blnShowBanner = false;
    var showLoginBanner = '<c:out value="${model.showLoginBanner}"/>';
    if(showLoginBanner == 'true') {
      blnShowBanner = true;
    }


	Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

	if(!window.navigator.cookieEnabled) {
		console.log("Entering method");
		Ext.get('themes').hide();
		Ext.fly('login').setStyle('padding-top', '10px');
		Ext.get('login').hide();
		Ext.fly('cookieMessg').addClass('msg-warning');
		Ext.get('cookieMessg').show();
		return;
	}

	Ext.QuickTips.init();

	Ext.apply(Ext.form.VTypes, {
		username : function(val, field) {
			var usernameVal = Ext.getCmp('username').getValue();
	        if(usernameVal.length > 40) {
	        	field.markInvalid('<spring:message code="invalid.user.name"/>');
	        	Ext.getCmp('loginBtn').disable();
	        	Ext.getCmp('password').disable();
	        	return false;
	        }
	        Ext.getCmp('loginBtn').enable();
	        Ext.getCmp('password').enable();
	        return true;
		},
		usernameText : '<spring:message code="invalid.user.name"/>'
	});

	var msg = function(title, msg, callback){
		Ext.Msg.show({
			title: title,
			msg: msg,
			minWidth: 200,
			modal: true,
			icon: Ext.Msg.INFO,
			buttons: Ext.Msg.OK,
			fn: callback
		});
	};

     var usgMsg = function(title, msg, callback){
			Ext.Msg.show({
				title: title,
				msg: msg,
				minWidth: 800,
				modal: true,
				icon: Ext.Msg.WARNING,
				buttons:[
					{
					text: 'Acknowledge'
					}],
				fn: callback
			});
		};

		var timeoutMsg = function(title, msg, callback){
			Ext.Msg.show({
				title: title,
				msg: msg,
				minWidth: 300,
				modal: true,
				icon: Ext.Msg.WARNING,
				buttons:[
					{
					text: 'Acknowledge'
					}],
				fn: callback
			});
		};


	var userName = new Ext.form.TextField({
		width: 250,
		name: 'username',
		id: 'username',
		vtype: 'username',
		fieldLabel: '<spring:message code="user.name"/>',
		listeners: {
			keyup: function(el,type) {
				if (el.getValue() != '' && password.getValue() != '') {
					loginBtn.enable();
				} else {
					loginBtn.disable();
				}
			}
		}
	});

	var password = new Ext.form.TextField({
		width: 250,
		name: 'password',
		id: 'password',
		inputType: 'password',
		fieldLabel: '<spring:message code="password"/>',
		listeners: {
			keyup: function(el,type) {
				if (el.getValue() != '' && userName.getValue() != '') {
					loginBtn.enable();
				} else {
					loginBtn.disable();
				}
			}
		}
	});

	var loginBtn = new Ext.Button({
		text: '<spring:message code="log.in"/>',
		id: 'loginBtn',
		disabled: true,
		handler: function(){
			if (loginForm.getForm().isValid()) {
				<c:if test="${not empty param.session_lang}">
					<c:if test ="${param.session_lang == 'on'}">
						loginForm.getForm().getEl().dom.action = '<c:url value="config_security_check"><c:param name="session_lang" value="on"/></c:url>';
					</c:if>
				</c:if>
				<c:if test="${empty param.session_lang}">
					loginForm.getForm().getEl().dom.action = '<c:url value="config_security_check"/>';
				</c:if>
				loginForm.getForm().getEl().dom.submit();
			}
		}
	});


	var forgotPanelHasFocus = false;
	var mapForgotButton;
	var forgotPanel = new Ext.Panel({
		autoHeight: true,
		autoWidth: true,
		layout: 'table',
		layoutConfig: {
			columns: 2
		},
		items: [{
			xtype: 'panel',
			width: 106,
			autoWidth: false,
			autoHeight: true
		},{
			xtype: 'label',
			//text: '<spring:message code="forgot.password1"/>',
			html: '<a href="#" style="color:#8888CC"><spring:message code="forgot.password1"/></a>',
			style: 'color:#8888CC; font-size:0.9em; font-weight:bold;text-align:right;text-decoration:underline; ',
			listeners: {
				render: function(c){
					c.getEl().on({
						click: function(el){
						  	forgotwin.show(this);

							if(!mapForgotButton) {
								mapForgotButton = new Ext.KeyMap("forgotForm", {
									key: Ext.EventObject.ENTER,
									fn: function(){
										if (!forgotBtn.disabled) {
											forgotForm.getForm().submit({
												url: 'forgot.ajax',
												success: function (form, action) {
													if (action.result.success) {
														msg('<spring:message code="password.sent"/>', '<spring:message code="please.check.your.e.mail.account.for.instructions.on.completing.the.password.reset.process"/>', function(){
															Ext.getCmp('emailForgot').reset();
															Ext.getCmp('forgotWin').hide();
														});
													}
												},
												failure: function(form, action) {
													//var errorMsg = '<spring:message code="password.reset.invalid.email"/>';
													var errors = '';
													if (!action.result.success) {
														for(var i in action.result.errors) {
															if(action.result.errors[i] && action.result.errors[i].id && action.result.errors[i].msg != '') {
																errors += action.result.errors[i].msg + '<br>';
															}
														}
														if(errors != "") {
															msg('<spring:message code="message"/>', errors, function(){Ext.getCmp('emailForgot').reset(); Ext.getCmp('forgotWin').hide();});
														}
													}

												}
											});
										}
									}
									, scope: forgotBtn
								});
							}
						}
					});
				}
			}
		}]
	});

    // Regular portal needs Forgot password Panel

	var loginForm = new Ext.form.FormPanel({
		autoWidth: true,
		bodyStyle: 'padding: 10px;',
		border: true,
		frame: true,
		labelAlign: 'side',
		standardSubmit: true,
		labelWidth: 100,
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[
			userName,
			password,
            {
                xtype: 'hidden',
                name: '${_csrf.parameterName}',
                value: '${_csrf.token}'
            }
		],
		buttons: [
			loginBtn
		]
	});

	var win = new Ext.Window({
		title: 'VidyoRouter Configuration Login',
		closable: false,
		resizable: false,
		width: 450,
		autoHeight: true,
		renderTo: 'login',
		border: true,
		frame: true,
		items: [
			loginForm
		]
	});

	win.on({
		show: {
			fn: function() {
				userName.focus(true,100);
			},
			scope: this,
			delay: 500
		}
	});


    var ackBtn = new Ext.Button({
			text: 'Acknowledge',
			disabled: true,
            id: 'ackBtn',
			handler: function(){
				usgWin.hide();
				win.show();
			}
		});

		(function(){
			ackBtn.focus();
			ackBtn.enable();
        }).defer(3000);

		var usgMsgForm = new Ext.form.FormPanel({
			autoWidth: true,
			autoHeight: true,
            id: 'usgMsgForm',
			bodyStyle: 'padding: 10px;',
			border: true,
			frame: true,
			labelWidth: 0,
            html:'<c:if test="${not empty model.banner}"><c:out value="${model.banner}" escapeXml="false"/></c:if>'
		});

		var usgWin = new Ext.Window({
			title: 'Home Page Banner',
			closable: false,
			resizable: true,
			width: 730,
			//autoHeight: true,
			height: 400,
			renderTo: 'usgmsg',
			border: false,
			frame: true,
			items: [
				usgMsgForm
			],
			buttonAlign: 'center',
			buttons:  [
				ackBtn
			]
		});

        usgWin.on({
			'show' : {
				fn: function() {

				},
				scope: this,
				delay: 500
			}
		});

    var ackTimeoutBtn = new Ext.Button({
			text: 'Acknowledge',
			disabled: true,
			handler: function(){
				timeoutMsgWin.hide();
				usgWin.show();
			}
		});

		(function(){
			ackTimeoutBtn.focus();
			ackTimeoutBtn.enable();
        }).defer(60000);

		var timeoutMsgForm = new Ext.form.FormPanel({
			autoWidth: true,
			bodyStyle: 'padding: 10px;',
			border: true,
			frame: true,
			labelWidth: 0,
			html: '<spring:message code="user.account.locked.out"/>',
			buttons: [
				ackTimeoutBtn
			]
		});
		var timeoutMsgWin = new Ext.Window({
			title: 'Account Lockout',
			closable: false,
			resizable: false,
			width: 300,
			autoHeight: true,
			renderTo: 'timeoutmsg',
			border: true,
			frame: true,
			items: [
				timeoutMsgForm
			]
		});

    win.show();
    <c:if test="${param.error == 5}">
			timeoutMsgWin.show();
		</c:if>
		<c:choose>
	      <c:when test="${param.error == 5}">
	      	usgWin.hide();
	      </c:when>
	      <c:otherwise>
	      	timeoutMsgWin.hide();
		   if(blnShowBanner) {
                usgWin.show();
            }else{
                usgWin.hide();
                win.show();
            }
	      </c:otherwise>
	</c:choose>

	new Ext.KeyMap(document, {
		key: Ext.EventObject.ENTER,
		fn: function(){
			if (!loginBtn.disabled) {
				if (loginForm.getForm().isValid()) {
					<c:if test="${not empty param.session_lang}">
						<c:if test ="${param.session_lang == 'on'}">
							loginForm.getForm().getEl().dom.action = '<c:url value="config_security_check"><c:param name="session_lang" value="on"/></c:url>';
						</c:if>
					</c:if>
					<c:if test="${empty param.session_lang}">
						loginForm.getForm().getEl().dom.action = '<c:url value="config_security_check"/>';
					</c:if>
					loginForm.getForm().getEl().dom.submit();
				}
			}
		},
		scope: this
	});


    // Required for Regular portal not required for DISA

    var email = new Ext.form.TextField({
		width: 340,
		name: 'email',
		id: 'emailForgot',
		fieldLabel: '<spring:message code="e.mail.address"/>',
		vtype:'email',
		helpText: '<spring:message code="please.provide.your.e.mail.address.to.recover.your.password"/>'
		/*listeners: {
			keyup: function(el,type) {
				if (el.getValue() != '') {
					forgotBtn.enable();
				} else {
					forgotBtn.disable();
				}
			}
		}*/
	});

	var forgotBtn = new Ext.Button({
		text: '<spring:message code="submit"/>',
		id: 'forgotBtn',
		disabled: true,
		formBind:true,
		handler: function(){
			forgotForm.getForm().submit({
				url: 'forgot.ajax',
				success: function (form, action) {
					if (action.result.success) {
						msg('<spring:message code="password.sent"/>', '<spring:message code="please.check.your.e.mail.account.for.instructions.on.completing.the.password.reset.process"/>', function(){
							Ext.getCmp('emailForgot').reset();
							Ext.getCmp('forgotWin').hide();
						});
					}
				},
				failure: function(form, action) {
					//var errorMsg = '<spring:message code="password.reset.invalid.email"/>';
					var errors = '';
					if (!action.result.success) {
						for(var i in action.result.errors) {
							if(action.result.errors[i] && action.result.errors[i].id && action.result.errors[i].msg != '') {
								errors += action.result.errors[i].msg + '<br>';
							}
						}
						if(errors != "") {
							msg('<spring:message code="message"/>', errors, function(){Ext.getCmp('emailForgot').reset(); Ext.getCmp('forgotWin').hide();});
						}
					}

				}
			});
		}
	});

	var forgotForm = new Ext.form.FormPanel({
		autoWidth: true,
		bodyStyle: 'padding: 10px;',
		border: true,
		frame: true,
		id: 'forgotForm',
		labelAlign: 'side',
		labelWidth: 100,
		monitorValid: true,
		errorReader: new Ext.data.XmlReader({
			record : 'field',
			success: '@success'
			},[
			'id', 'msg'
			]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[
			email
		],
		buttons: [
			forgotBtn
		]
	});

	var forgotwin = new Ext.Window({
		title: '<spring:message code="recover.your.password"/>',
		id:'forgotWin',
		closable: true,
		closeAction: 'hide',
		resizable: false,
		width: 500,
		autoHeight: true,
		border: true,
		frame: true,
		items: [
			forgotForm
		],
		listeners: {
			show: function(el,type) {
				email.reset();
				email.focus(true,100);
			},
			hide: function(el,type) {
				userName.focus(true,100);
			}
		}
	});


});

</script>
<%@ include file="footer_html.jsp" %>