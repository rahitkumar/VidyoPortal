<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ include file="include_html.jsp" %>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<%
String country = locale.getCountry();
String html_lang = locale.getLanguage();
html_lang += country.equalsIgnoreCase("") ? "" : "-"+locale.getCountry();
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="<%= html_lang%>" lang="<%= html_lang%>">
    <!--redirect to login
    Intentionally added do not delete as its require to move to
    login screen when session out in IE.
    T3MZzeBOLmeyUIO0
    -->
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>
            <spring:message code="title.super"/>
        </title>

        <link rel="shortcut icon" href="favicon.ico" />
        <link rel="stylesheet" type="text/css" href="js/SuperApp/resources/css/vidyo-ext.css">
        </link>
        <script>
            var csrfToken = '<c:out value="${_csrf.token}" />';
            var csrfFormParamName = '<c:out value="${_csrf.parameterName}" />';

            var blnShowForgotPassword = true;
            var blnShowForgotPassword = '<c:out value="${model.showForgotPassword}"/>';
            if (blnShowForgotPassword == 'false') {
                blnShowForgotPassword = false;
            }

            var forgotPassHide = true;
            var showOnlyForgotPasspanel = false;
            if (blnShowForgotPassword) {
                forgotPassHide = false;
                showOnlyForgotPasspanel = true;
            }
            var showLoginBanner = '<c:out value="${model.showLoginBanner}"/>';
            var loginBannerText = '<c:out value="${model.loginBanner}" escapeXml="false"/>';

        </script>
        <script>
            function loadApplicationScripts() {
                Ext.onReady(function() {

                    var warningText = '';
                    if (loginError == '1') {
                        warningText = l10n('your-username-or-password-are-invalid');
                    } else if (loginError == '2') {
                        warningText = l10n('we-re-sorry-but-you-are-not-authorized-to-perform-the-requested-operation');
                    } else if (loginError == '3') {
                        warningText = l10n('your-session-is-expired-please-login-again');
                    } else if (loginError == '4') {
                        warningText = l10n('user-login-elsewhere-concurent-login');
                    } else if (loginError == '5') {
                        warningText = l10n('user-account-locked-out');
                    }

                    Ext.application({
                        name : 'Login',
                        launch : function() {

                            Ext.apply(Ext.form.VTypes, {
                                email: function (val,field){
                                    var re = /^\w+([.!#$%&'*+-/=?^_`{|}~,]\w+)*@\w+([.-]\w+)*\.\w{2,}$/;
                                    return re.test(val);
                                }
                            });

                            var errorReaderModel = Ext.create('Ext.data.Model', {
                                fields : ['id', 'msg']
                            });

                            var langds = Ext.create('Ext.data.Store', {
                                fields : [{
                                    name : 'langID',
                                    type : 'int'
                                }, {
                                    name : 'langCode',
                                    type : 'string'
                                }, {
                                    name : 'langName',
                                    type : 'string'
                                }, {
                                    name : 'langFlag',
                                    type : 'string'
                                }],
                                proxy : {
                                    type : 'ajax',
                                    url : 'langs.ajax',
                                    reader : {
                                        type : 'xml',
                                        totalRecords : 'results',
                                        record : 'row',
                                        id : 'langID'
                                    }
                                },
                                autoLoad : true
                            });

                            Ext.override(Ext.form.ComboBox, {
                                checkChangeEvents : Ext.isIE ? ['change', 'propertychange', 'keyup'] : ['change', 'input', 'textInput', 'keyup', 'dragdrop']
                            });

                            var loginBanner = function() {
                                var ackBtn = Ext.create('Ext.Button', {
                                    text : l10n('acknowledge'),
                                    disabled : true,
                                    handler : function(btn) {
                                        Ext.getCmp('username').focus(true, 10);
                                        btn.up('window').close();
                                    }
                                });
                                Ext.create('Ext.window.Window', {
                                    width : 700,
                                    height : 400,
                                    closable : false,
                                    title : l10n('home-page-banner'),
                                    autoScroll : true,
                                    modal : true,
                                    scroll : 'vertical',
                                    items : [{
                                        xtype : 'component',
                                        padding : 5,
                                        margin : 5,
                                        html : loginBannerText
                                    }],
                                    buttons : [ackBtn]
                                }).show();

                                    ackBtn.enable();

                            }
                            if (showLoginBanner == "true") {
                                loginBanner();
                            }

                            if (!aboutUsPanel) {
                                var aboutUsPanel = Ext.create('Ext.window.Window', {
                                    closeAction : 'hide',
                                    title : l10n('about_us'),
                                    modal : true,
                                    animateTarget : 'about',
                                    animCollapse : true,
                                    header : true,
                                    width : 600,
                                    height : 485,
                                    border : false,
                                    closable : true,
                                    shadowOffset : 30,
                                    layout : 'fit',
                                    constrain : true,
                                    resizable : false,
                                    items : [{
                                        xtype : 'form',
                                        border : false,
                                        margin : 10,
                                        id : 'aboutForm',
                                        overflowY : 'auto',
                                        errorReader : new Ext.data.XmlReader({
                                            successProperty : '@success',
                                            record : 'field'
                                        }, ['id', 'msg'])
                                    }],
                                    buttonAlign : 'center',
                                    buttons : [{
                                        text : l10n('close'),
                                        handler : function() {
                                            aboutUsPanel.hide();
                                        }
                                    }],
                                    listeners : {
                                        show : function() {
                                            var form = Ext.getCmp('aboutForm');
                                            Ext.Ajax.request({
                                                url : 'about_content.html',
                                                method: 'GET',
                                                params : form.getForm().getValues(),
                                                success : function(res) {
                                                    var aboutInfo = res.responseText;
                                                    if (aboutInfo && aboutInfo != null && aboutInfo != '') {
                                                        form.setHtml(aboutInfo);
                                                    } else {
                                                        form.setHtml(l10n('about-us-full'));
                                                    }
                                                },
                                                failure : function() {
                                                    Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                                                }
                                            });
                                        }
                                    }
                                });
                            }

                            if (!supportPanel) {
                                var supportPanel = Ext.create('Ext.window.Window', {
                                    closeAction : 'hide',
                                    title : l10n('contact_us'),
                                    modal : true,
                                    animateTarget : 'support',
                                    animCollapse : true,
                                    header : true,
                                    width : 560,
                                    height : 350,
                                    border : false,
                                    closable : true,
                                    shadowOffset : 30,
                                    layout : 'fit',
                                    constrain : true,
                                    resizable : false,
                                    items : [{
                                        xtype : 'form',
                                        border : false,
                                        margin : 10,
                                        id : 'supportForm',
                                        overflowY : 'auto',
                                        errorReader : new Ext.data.XmlReader({
                                            successProperty : '@success',
                                            record : 'field'
                                        }, ['id', 'msg'])
                                    }],
                                    buttonAlign : 'center',
                                    buttons : [{
                                        text : l10n('close'),
                                        handler : function() {
                                            supportPanel.hide();
                                        }
                                    }],
                                    listeners : {
                                        show : function() {
                                            var form = Ext.getCmp('supportForm');
                                            Ext.Ajax.request({
                                                url : 'contact_content.html',
                                                method: 'GET',
                                                params : form.getForm().getValues(),
                                                success : function(res) {
                                                    var xmlResponse = res.responseText;
                                                    form.setHtml(xmlResponse);
                                                },
                                                failure : function() {
                                                    Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                                                }
                                            });
                                        }
                                    }
                                });
                            }

                            if (!termsPanel) {
                                var termsPanel = Ext.create('Ext.window.Window', {
                                    closeAction : 'hide',
                                    title : l10n('terms_of_services'),
                                    modal : true,
                                    animateTarget : 'termsofservices',
                                    animCollapse : true,
                                    header : true,
                                    width : 800,
                                    height : 470,
                                    border : false,
                                    closable : true,
                                    shadowOffset : 30,
                                    layout : 'fit',
                                    constrain : true,
                                    resizable : false,
                                    items : [{
                                        xtype : 'form',
                                        border : false,
                                        margin : 10,
                                        id : 'termsForm',
                                        overflowY : 'auto',
                                        errorReader : new Ext.data.XmlReader({
                                            successProperty : '@success',
                                            record : 'field'
                                        }, ['id', 'msg'])
                                    }],
                                    buttonAlign : 'center',
                                    buttons : [{
                                        text : l10n('close'),
                                        handler : function() {
                                            termsPanel.hide();
                                        }
                                    }],
                                    listeners : {
                                        show : function() {
                                            var form = Ext.getCmp('termsForm');
                                            Ext.Ajax.request({
                                                url : 'terms_content.html',
                                                method: 'GET',
                                                params : form.getForm().getValues(),
                                                success : function(res) {
                                                    var xmlResponse = res.responseText;
                                                    form.setHtml(xmlResponse);
                                                },
                                                failure : function() {
                                                    Ext.Msg.alert(l10n('failure'), l10n('request-failed'));
                                                }
                                            });
                                        }
                                    }
                                });
                            }

                            var warningTextPanel = Ext.create('Ext.form.FieldSet', {
                                title : '<span class="warning-text">' + l10n('warning') + '</span>',
                                collapsible : false,
                                hidden : true,
                                defaultType : 'label',
                                layout : 'anchor',
                                cls : 'msg-warning',
                                items : [{
                                    text : warningText
                                }],
                                listeners : {
                                    beforerender : function() {
                                        if (loginError) {
                                            if (loginError != '5') {
                                                this.show();
                                            } else {
                                                var warningWindow = Ext.create('Ext.window.Window', {
                                                    title : l10n('account-lockout'),
                                                    closable : false,
                                                    resizable : false,
                                                    cls : 'white-footer',
                                                    width : 300,
                                                    autoHeight : true,
                                                    border : 0,
                                                    frame : false,
                                                    modal : true,
                                                    items : [{
                                                        xtype : 'form',
                                                        autoWidth : true,
                                                        bodyStyle : 'padding: 10px;',
                                                        border : true,
                                                        frame : true,
                                                        labelWidth : 0,
                                                        html : warningText,
                                                        buttons : ['->', {
                                                            text : l10n('acknowledge'),
                                                            disabled : true,
                                                            listeners : {
                                                                afterrender : function(btn) {

                                                                        btn.enable();

                                                                },
                                                                click : function() {
                                                                    warningWindow.destroy();
                                                                    if (showLoginBanner == "true") {
                                                                        loginBanner();
                                                                    }
                                                                }
                                                            }
                                                        }, '->']
                                                    }]
                                                });
                                                warningWindow.show();
                                            }
                                        }
                                    }
                                }
                            });

                            var langCombo = Ext.create('Ext.form.ComboBox', {
                                fieldLabel : '',
                                width : 200,
                                margin : '0 0 40 700',
                                store : langds,
                                align : 'right',
                                typeAhed : false,
                                valueField : 'langCode',
                                displayField : 'langName',
                                triggerAction : 'all',
                                queryMode : 'remote',
                                value : lang,
                                editable : false,
                                listeners : {
                                    render : function(cb) {
                                        var isLangAvail = false;
                                        langds.on('load', function(store) {
                                            store.each(function(rec) {
                                                if (rec.get('langCode') == lang) {
                                                    isLangAvail = true;
                                                }
                                            });
                                            if (!isLangAvail) {
                                                lang = "en";
                                                langCombo.setValue(lang);
                                            }
                                        });
                                    },
                                    select : function(cb, record, index) {
                                        window.location = 'login.html?lang=' + record.data.langCode + '&session_lang=on';
                                    }
                                }
                            });

                            if (!superLoginForm) {
                                var superLoginForm = Ext.create('Ext.panel.Panel', {
                                    width : 400,
                                    closable : false,
                                    title : l10n('login'),
                                    border : false,
                                    frame : true,
                                    margin : '0 0 40 500',
                                    shadowOffset : 50,
                                    resizable : false,
                                    cls : 'login-form',
                                    tools : [{
                                        id : 'help',
                                        qtip : l10n('help1'),
                                        handler : function(event, toolEl, panel) {
                                            var url = '<c:out value="${model.guideLoc}"/>#SuperAdmin_Login';
                                            var wname = 'VidyoPortalHelp';
                                            var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
                                            window.open(url, wname, wfeatures);
                                        }
                                    }],
                                    items : [{
                                        xtype : 'form',
                                        autoEl: {
                                            tag: 'form'
                                        },
                                        autoDestroy : true,
                                        focusOnToFront : true,
                                        border : false,
                                        width : 400,
                                        layout : {
                                            type : 'vbox'
                                        },
                                        defaultType : 'textfield',
                                        items : [{
                                            fieldLabel : l10n('user-name'),
                                            id : 'username',
                                            name : 'username',
                                            focusOnToFront : true,
                                            emptyText : l10n('user-name'),
                                            allowBlank : false,
                                            margin : '5 0 5 5',
                                            'inputAttrTpl': ['autocomplete="on"'],
                                            listeners : {
                                                afterrender : function() {
                                                    if (showLoginBanner == "false")
                                                        this.focus(true, 10);
                                                }
                                            }
                                        }, {
                                            fieldLabel : l10n('password'),
                                            name : 'password',
                                            emptyText : l10n('password'),
                                            inputType : 'password',
                                            allowBlank : false,
                                            enableKeyEvents : true,
                                            margin : '5 0 0 5',
                                            'inputAttrTpl': ['autocomplete="on"'],
                                            listeners : {
                                                keypress : function(textfield, eo) {
                                                    if (eo.getCharCode() == Ext.EventObject.ENTER) {
                                                        var form = this.up('form').getForm();
                                                        if (form.isValid()) {
                                                            var loginUrl = '';
                                                            if (sessionLang != '' && sessionLang == 'on') {
                                                                loginUrl = 'super_security_check?session_lang=on';
                                                            } else {
                                                                loginUrl = 'super_security_check';
                                                            }
                                                            var form = this.up('form').getForm();
                                                            form.url = loginUrl;
                                                            form.standardSubmit = true;
                                                            form.method = 'POST';
                                                            form.submit({
                                                                params : {
                                                                	_csrf : csrfToken
                                                                }
                                                            });
                                                        }
                                                    }
                                                }
                                            }
                                        }, {
                                            xtype : 'panel',
                                            height : 27,
                                            hidden : showOnlyForgotPasspanel
                                        }, {
                                            xtype : 'label',
                                            margin : '5 0 5 110',
                                            hidden : forgotPassHide,
                                            cls : 'linkclass',
                                            html : '<a href="#" style="color:#8888CC;font-weight:bold;">' + l10n('forgot-password') + '</a>',
                                            listeners : {
                                                render : function(c) {
                                                    c.getEl().on({
                                                        click : function(el) {
                                                            var forgotwin = Ext.create('Ext.window.Window', {
                                                                title : l10n('recover-your-password'),
                                                                closable : true,
                                                                closeAction : 'destroy',
                                                                resizable : false,
                                                                width : 500,
                                                                autoHeight : true,
                                                                border : false,
                                                                modal : true,
                                                                items : [{
                                                                    xtype : 'form',
                                                                    autoWidth : true,
                                                                    bodyPadding : 10,
                                                                    border : false,
                                                                    labelAlign : 'side',
                                                                    labelWidth : 100,
                                                                    monitorValid : true,
                                                                    errorReader : Ext.create('Ext.data.XmlReader', {
                                                                        record : 'field',
                                                                        model : errorReaderModel,
                                                                        success : '@success'
                                                                    }),
                                                                    items : [{
                                                                        xtype : 'textfield',
                                                                        width : 340,
                                                                        name : 'email',
                                                                        reference : 'emailAdd',
                                                                        fieldLabel : l10n('e-mail-address'),
                                                                        allowBlank : false,
                                                                        vtype : 'email',
                                                                        msgTarget : 'under',
                                                                        maxLength : 254,
                                                                        helpText : l10n('please-provide-your-e-mail-address-to-recover-your-password'),
                                                                        listeners : {
                                                                            specialkey : function(textfield, e) {
                                                                                if (e.getKey() == e.ENTER) {
                                                                                    this.up('form').getForm().submit({
                                                                                        url : 'forgot.ajax',
                                                                                        params : {
                                                                                        	_csrf : csrfToken
                                                                                        },
                                                                                        success : function(form, action) {
                                                                                            var xmlResponse = action.response.responseXML;
                                                                                            var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                                                                                            if (success == "false") {
                                                                                                var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                                                                                                var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                                                                                                Ext.Msg.alert(responseId.textContent, responseMsg.textContent, function() {
                                                                                                    forgotwin.hide();
                                                                                                });

                                                                                            } else {
                                                                                                Ext.Msg.alert(l10n('password-sent'), l10n('please-check-your-e-mail-account-for-instructions-on-completing-the-password-reset-process'), function() {
                                                                                                    forgotwin.hide();
                                                                                                });
                                                                                            }
                                                                                        },
                                                                                        failure : function(form, action) {
                                                                                            Ext.Msg.alert(l10n('failure'), l10n('password-reset-failed'));
                                                                                        }
                                                                                    });
                                                                                }
                                                                            }
                                                                        }
                                                                    }, {
                                                                        xtype : 'label',
                                                                        text : l10n('please-provide-your-e-mail-address-to-recover-your-password')
                                                                    }],
                                                                    buttonAlign : 'center',
                                                                    buttons : [{
                                                                        text : l10n('submit'),
                                                                        disabled : true,
                                                                        formBind : true,
                                                                        handler : function() {
                                                                            this.up('form').getForm().submit({
                                                                                url : 'forgot.ajax',
                                                                                params : {
                                                                                	_csrf : csrfToken
                                                                                },
                                                                                success : function(form, action) {
                                                                                    var xmlResponse = action.response.responseXML;
                                                                                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);
                                                                                    if (success == "false") {
                                                                                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                                                                                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                                                                                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent, function() {
                                                                                            forgotwin.hide();
                                                                                        });

                                                                                    } else {
                                                                                        Ext.Msg.alert(l10n('password-sent'), l10n('please-check-your-e-mail-account-for-instructions-on-completing-the-password-reset-process'), function() {
                                                                                            forgotwin.hide();
                                                                                        });
                                                                                    }
                                                                                },
                                                                                failure : function(form, action) {
                                                                                    Ext.Msg.alert(l10n('failure'), l10n('password-reset-failed'));
                                                                                }
                                                                            });
                                                                        }
                                                                    }]
                                                                }]
                                                            });
                                                            forgotwin.show();

                                                        }
                                                    });
                                                }
                                            }
                                        }],
                                        buttonAlign : 'center',
                                        buttons : [{
                                            text : l10n('login'),
                                            formBind : true,
                                            disabled : true,
                                            handler : function() {
                                                var loginUrl = '';
                                                if (sessionLang != '' && sessionLang == 'on') {
                                                    loginUrl = "super_security_check?session_lang=on";
                                                } else {
                                                    loginUrl = "super_security_check";
                                                }
                                                var form = this.up('form').getForm();
                                                if (form.isValid()) {
                                                    form.url = loginUrl;
                                                    form.standardSubmit = true;
                                                    form.method = 'POST';
                                                    form.submit({
                                                        params : {
                                                        	_csrf : csrfToken
                                                        }
                                                    });
                                                }
                                            }
                                        }]
                                    }]
                                });

                            }

                            Ext.create('Ext.panel.Panel', {
                                border : true,
                                title : '',
                                layout : {
                                    type : 'vbox'
                                },
                                renderTo : Ext.getBody(),
                                cls : 'container',
                                items : [{
                             	   xtype:'panel',
                            	   width : '100%',
                            	   bodyStyle:{
                                   	'background-color':'#5fa2dd'
                                   },

                            		items:[{
                                		xtype : 'form',
                                        border : false,
                                		margin : 10,
                                		height : 50,
                                		width : 145,
                                		cls : 'logoform',
                                		   bodyStyle:{
                                              	'background-color':'#5fa2dd'
                                              },
                                		loader : {
                                    		url : 'customizedlogoinmarkup.ajax',
                                    		autoLoad : true
                                	}
                            }]}, {
                                    xtype : 'panel',
                                    //cls : 'maincontent',
                                    border : false,
                                    bodyPadding : '0 20 0 20',
                                    layout : {
                                        type : 'vbox'
                                    },
                                    items : [warningTextPanel, langCombo]
                                }, {
                                    xtype : 'container',
                                    width : '100%',
                                    layout : {
                                        type : 'vbox',
                                        align : 'center'
                                    },
                                    items : [superLoginForm]
                                }, {
                                    xtype : 'panel',
                                    layout : {
                                        type : 'hbox'
                                    },
                                    flex : 1,
                                    width : '90%',
                                    border : false,
                                    padding : '100 0 0 0',
                                    cls : 'transparentCls',
                                    items : [{
                                        xtype : 'button',
                                        text : l10n('about_us'),
                                        id : 'about',
                                        cls : 'footerlinks',
                                        handler : function() {
                                            aboutUsPanel.show();
                                            supportPanel.hide();
                                            termsPanel.hide();

                                        }
                                    }, {
                                        xtype : 'button',
                                        text : l10n('contact_us'),
                                        cls : 'footerlinks',
                                        id : 'support',
                                        handler : function() {
                                            aboutUsPanel.hide();
                                            supportPanel.show();
                                            termsPanel.hide();
                                        }
                                    }, {
                                        xtype : 'button',
                                        text : l10n('terms_of_services'),
                                        cls : 'footerlinks',
                                        id : 'termsofservices',
                                        handler : function() {
                                            aboutUsPanel.hide();
                                            supportPanel.hide();
                                            termsPanel.show();
                                        }
                                    }, {
                                        xtype : 'button',
                                        cls : 'copyright',
                                        html : '&copy; Vidyo 2008-2019'
                                    }]
                                }]
                            });
                        }
                    });
                });
            }

            function querySt(Key) {
                var url = window.location.href;
                var KeysValues = url.split(/[\?&]+/);
                for ( i = 0; i < KeysValues.length; i++) {
                    KeyValue = KeysValues[i].split("=");
                    if (KeyValue[0] == Key) {
                        return KeyValue[1].split("#")[0];
                    }
                }
            };
            var loginError = querySt('login_error');
            var sessionLang = querySt('session_lang');
            var lang = querySt('lang');
            var l10n = document.webL10n.get;
            document.webL10n.setLanguage(lang);
            sessionStorage.setItem('lang', lang);
        </script>
    </head>

    <body class='bodyCls'>
    </body>
</html>