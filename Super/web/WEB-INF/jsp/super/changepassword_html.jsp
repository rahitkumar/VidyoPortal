<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="include_html.jsp" %>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<%@ page session="false" %>

<link rel="stylesheet" type="text/css" href="js/SuperApp/resources/css/vidyo-ext.css">
</link>


<script type="text/javascript">
    var lang = sessionStorage.getItem('lang');
    var l10n = document.webL10n.get;
    document.webL10n.setLanguage(lang);
    function logoutSuper() {
        window.location = "login.html"
    }

    function loadApplicationScripts() {
        Ext.onReady(function() {

            var csrfToken = '<c:out value="${_csrf.token}" />';
            var csrfFormParamName = '<c:out value="${_csrf.parameterName}" />';

            Ext.application({
                name : 'Change Password',
                launch : function() {

                    var superChangePassordForm = Ext.define('changePasswordPanel', {
                        extend : 'Ext.form.Panel',
                        autoDestroy : true,
                        focusOnToFront : true,
                        title : l10n('change-password'),
                        titleAlign : 'center',
                        cls : 'white-footer',
                        border : 1,
                        bodyPadding : 10,
                        width : 450,
                        layout : 'anchor',
                        defaults : {
                            anchor : '100%'
                        },
                        defaultType : 'textfield',
                        items : [],
                        initComponent : function() {
                            var me = this;
                            Ext.apply(Ext.form.VTypes, {
                                password : function(val, field) {
                                    if (field.initialPassField) {
                                        var pwd = me.down('textfield[name=password1]');
                                        if (pwd.getValue() != '') {
                                            if (val == pwd.getValue()) {
                                                pwd.clearInvalid();
                                                superChangePassordForm.down('button').enable();
                                                return true;
                                            } else {
                                                superChangePassordForm.down('button').disable();
                                                return false;
                                            }
                                        }
                                        return true;
                                    }
                                    return true;
                                },
                                passwordText : l10n('password-not-match'),

                                password1 : function(val, field) {
                                    if (field.initialPassField) {
                                        var pwd = me.down('textfield[name=password2]');
                                        if (pwd.getValue() != '') {
                                            if (val == pwd.getValue()) {
                                                pwd.clearInvalid();
                                                superChangePassordForm.down('button').enable();
                                                return true;
                                            } else {
                                                superChangePassordForm.down('button').disable();
                                                return false;
                                            }
                                        }
                                        return true;
                                    }
                                    return true;
                                },
                                password1Text : l10n('password-not-match')

                            });

                            this.items = [{
                                xtype : 'hidden',
                                name : csrfFormParamName,
                                value : csrfToken
                            }, {
                                xtype : 'textfield',
                                name : 'password1',
                                reference : 'password1',
                                fieldLabel : '<span class="red-label">'+l10n('SuperAccount-password')+'</span>',
                                inputType : 'password',
                                allowBlank : false,
                                tabIndex : 4,
                                width : 400,
                                labelWidth : 180,
                                vtype : 'password1',
                                validateOnChange : true,
                                msgTarget : 'under',
                                initialPassField : 'password2'
                            }, {
                                xtype : 'textfield',
                                name : 'password2',
                                reference : 'password2',
                                fieldLabel : '<span class="red-label">'+l10n('SuperAccount-verify-password')+'</span>',
                                inputType : 'password',
                                allowBlank : false,
                                width : 400,
                                labelWidth : 180,
                                tabIndex : 5,
                                vtype : 'password',
                                validateOnChange : true,
                                msgTarget : 'under',
                                initialPassField : 'password1', // id of the initial password field
                                enableKeyEvents : true,
                                listeners : {
                                    keypress : function(textfield, eo) {
                                        if (!superChangePassordForm.getForm().isValid()) {
                                            return;
                                        }
                                        if (eo.getCharCode() == Ext.EventObject.ENTER) {
                                            Ext.Ajax.request({
                                                url : 'changepassword.ajax',
                                                waitMsg : l10n('saving'),
                                                params : superChangePassordForm.getForm().getValues(),
                                                method : 'POST',
                                                success : function(response) {
                                                    var xmlResponse = response.responseXML;
                                                    var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                                                    if (success == "false") {
                                                        var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                                                        var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                                                        Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                                                    } else {
                                                        window.location = 'home.html';
                                                    }
                                                },
                                                failure : function(form, action) {
                                                    Ext.Msg.alert(l10n("failure"), l10n('save-failed'));
                                                }
                                            });
                                        }
                                    }
                                }

                            }];

                            this.callParent();
                        },
                        buttonAlign : 'center',
                        buttons : [{
                            text : l10n('save'),
                            disabled : true,
                            reference : 'savebtn',
                            handler : function() {
                                Ext.Ajax.request({
                                    url : 'changepassword.ajax',
                                    waitMsg : l10n('saving'),
                                    params : superChangePassordForm.getForm().getValues(),
                                    method : 'POST',
                                    success : function(response) {
                                        var xmlResponse = response.responseXML;
                                        var success = Ext.DomQuery.selectValue('message @success', xmlResponse);

                                        if (success == "false") {
                                            var responseId = Ext.DomQuery.selectNode('id', xmlResponse);
                                            var responseMsg = Ext.DomQuery.selectNode('msg', xmlResponse);
                                            Ext.Msg.alert(responseId.textContent, responseMsg.textContent);
                                        } else {
                                            window.location = 'home.html';
                                        }
                                    },
                                    failure : function(form, action) {
                                        Ext.Msg.alert(l10n("failure"), l10n('save-failed'));
                                    }
                                });
                            },
                            tabIndex : 7
                        }]
                    });

                    superChangePassordForm = Ext.create('changePasswordPanel');

                    Ext.create('Ext.panel.Panel', {
                        border : false,
                        title : '',
                        layout : {
                            type : 'vbox',
                            align : 'stretch'
                        },
                        renderTo : Ext.getBody(),
                        cls : 'container',
                        items : [{
                            title : '',
                            xtype : 'panel',
                            height : 70,
                            width : '100%',
                            border : false,
                            split : false, // enable resizing
                            layout : {
                                type : 'hbox',
                                align : 'stretch'
                            },
                            items : [{
                                xtype : 'tbfill',
                                flex : 1,
                                cls : 'centerd'
                            }, {
                                xtype : 'panel',
                                border : 0,
                                margin : 0,
                                layout : {
                                    type : 'hbox',
                                    align : 'center'
                                },
                                flex : 8,
                                minWidth : 960,
                                padding : 0,
                                items : [{
                                    xtype : 'form',
                                    style : {
                                        'background-color' : '#FFFFFF'
                                    },
                                    cls : 'logoform',
                                    height : 50,
                                    width : 145,
                                    border : false,
                                    loader : {
                                        url : 'customizedlogoinmarkup.ajax',
                                        autoLoad : true
                                    }
                                }, {
                                    xtype : 'tbfill',
                                    flex : 6,
                                    style : {
                                        'background-color' : '#FFFFFF'
                                    }
                                }, {
                                    xtype : 'panel',
                                    margin : 5,
                                    padding : 5,
                                    border : 0,
                                    layout : {
                                        type : 'vbox',
                                        align : 'stretch'
                                    },
                                    items : [{
                                        xtype : 'label',
                                        html : '<a href="javascript:void(0)" onclick="logoutSuper()">' + l10n('logout') + '</a>',
                                        //margin : '10 5 5 5',
                                        cls : 'bold-label-style',
                                        style : {
                                            'background-color' : '#FFFFFF'
                                        }
                                    }]
                                }]
                            }, {
                                xtype : 'tbfill',
                                flex : 1,
                                cls : 'centerd'
                            }, {
                                xtype : 'container',
                                cls : 'extra-div-class',
                                width : 16,
                                reference : 'extraScrollContainer1'
                            }]
                        }, {
                            xtype : 'container',
                            width : '100%',
                            layout : {
                                type : 'vbox',
                                align : 'center'
                            },
                            items : [superChangePassordForm]
                        }, {
                            xtype : 'panel',
                            padding :5,
                            margin : 5,
                            layout : {
                                type : 'hbox'
                            },
                            flex : 1,
                            width : '90%',
                            border : false,
                            padding : '100 0 0 0',
                            cls : 'transparentBG',
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
                            }]
                        }]
                    });
                }
            });

        });
    }
</script>
