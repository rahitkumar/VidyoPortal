<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="include_html.jsp" %>

<%@ page import="org.springframework.context.i18n.LocaleContextHolder" %>
<%@ page import="java.util.Locale" %>
<%@ page session="false" %>


<link rel="stylesheet" type="text/css" href="js/AdminApp/resources/vidyo-ext.css">
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

        
            Ext.application({
                name : 'Reset',
                launch : function() {
                	Ext.onReady(function(){
                	
                	<c:if test="${model.success == 'true'}">
                	   Ext.Msg.alert(l10n('password-reset'),l10n('your-password-has-been-reset-and-e-mailed-to-you-please-check-your-e-mail-account-for-your-new-password') ,function(){window.location='<c:url value="login.html"/>';});
                	   
                	

                	</c:if>
                	<c:if test="${model.success == 'false'}">
                	
                	Ext.Msg.alert(l10n('password-reset-failed'),l10n('there-was-a-problem-resetting-your-password-this-reset-key-may-have-expired-please-try-resetting-your-password-again') ,function(){window.location='<c:url value="login.html"/>';});
             	   
                	
                	</c:if>

                	});
               

                 
                    
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
