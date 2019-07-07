/**
 * @class SuperApp.view.settings.security.SecurityOverlay
 */
Ext.define("SuperApp.view.settings.security.SecurityOverlay", {
    extend : 'Ext.window.Window',
    alias : 'widget.securityoverlay',

    reference : 'securityoverlay',

    closable : true,

    closeAction : 'hide',

    resizable : false,

    width : 500,

    autoHeight : true,

    border : true,

    frame : true,

    layout : 'fit',

    modal : true,

    initComponent : function() {
        var me = this,
            viewtype = me.viewtype,
            useDefaultRootCerts = me.useDefaultRootCerts == "true" ? true : false;
              Ext.apply(Ext.form.VTypes, {
             uploadSecurityBundle: function(val, field) {                              
                                 var fileName = /^.*\.(p7b|pfx)$/i;
                                 return fileName.test(val);
                           },                 
             uploadSecurityBundleText: l10n('file-must-be-in-p7b-pfx-format')
  });
        Ext.apply(Ext.form.VTypes, {
            confirmMatch : function(val, field) {
                if (field.initialPassField) {
                    var pwd = me.down('textfield[itemId=' + field.initialPassField + ']');
                    return (val == pwd.getValue());
                }
                return true;
            },
            confirmMatchText : l10n('password-not-match')
        });

        switch (viewtype) {
        case "securitysettings":
            me.title = l10n('select-bundle-pkcs-vidyo');
            me.items = [{
                xtype : 'form',
                border : false,
                fileUpload : true,
                frame : false,
                errorReader : Ext.create('Ext.data.XmlReader', {
                    record : 'field',
                    model : Ext.create("SuperApp.model.settings.Field"),
                    success : '@success'
                }),
                reference : 'bundlecertificate',
                autoHeight : true,
                defaults : {
                    margin : 5
                },
                layout : {
                    type : 'auto',
                    align : 'stretch'
                },
                items : [{
                    xtype : 'component',
                    html : '<span style="color:red; font-size:10px;">' + l10n('super-security-advanced-warning-importing-will-replace-any-current-ssl-configurations') + '</span>'
                }, {
                    xtype : 'fileuploadfield',
                    labelWidth : 250,
                    width : '95%',
                    emptyText : l10n('super-security-ssl-select-a-p7b-pfx-or-vidyo-file'),
                    fieldLabel : l10n('super-security-ssl-bundle'),
                    hideLabel : false,
                    labelSeparator : ':',
                    name : 'certBundleFile',
                    validateOnChange : true,
                    allowBlank : false,
                    msgTarget : 'under',
                     vtype:'uploadSecurityBundle',
                    buttonConfig : {
                        text : '',
                        iconCls : 'icon-upload'
                    }
                }, {
                    xtype : 'textfield',
                    labelWidth : 250,
                    width : '95%',
                    inputType : 'password',
                    fieldLabel : l10n('super-security-ssl-password-if-any'),
                    hideLabel : false,
                    labelSeparator : ':',
                    name : 'bundlePassword',
                    allowBlank : true
                }, {
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                }]
            }];
            me.buttons = ['->', {
                text : l10n('upload'),
                listeners : {
                    click : 'onClickCertificateBundleUpload'
                }
            }, '->'];
            break;
        case "rootcertificate":

            me.title = l10n('select-file');
            me.items = [{
                xtype : 'form',
                baseCls : 'x-plain',
                reference : 'rootform',
                fileUpload : true,
                frame : false,
                autoShow: true,
                autoHeight : true,
                errorReader : Ext.create('Ext.data.XmlReader', {
                    record : 'field',
                    model : Ext.create("SuperApp.model.settings.Field"),
                    success : '@success'
                }),
                items : [{
                    xtype : 'fileuploadfield',
                    width : '95%',
                    emptyText : l10n('security-super-ssl-select-a-pem-crt-cer-der-file'),
                    fieldLabel : l10n('super-security-ssl-bundle'),
                    hideLabel : false,
                    name : 'rootCertsFile',
                    regex : (/.(cer|crt|pem|der)$/i),
                    validateOnChange : true,
                    allowBlank : false,
                    msgTarget : 'under',
                    buttonConfig : {
                        text : '',
                        iconCls : 'icon-upload'
                    }
                }, {
                    xtype : 'radiogroup',
                    hideLabel : false,
                    items : [{
                        boxLabel : l10n('security-super-ssl-replace-existing'),
                        name : 'actionType',
                        inputValue : 'replace'
                    }, {
                        boxLabel : l10n('security-super-ssl-append-to-existing'),
                        name : 'actionType',
                        inputValue : 'append',
                        checked : true
                    }]
                }, {
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                }]
            }];
            me.buttons = ['->', {
                text : l10n('upload'),
                 listeners : {
                    click : 'onClickRootUpload'
                }
            }, '->'];

            break;
        case "clientcacert":
            me.title = l10n('configure-client-ca-certificates');
            me.items = [{
                xtype : 'form',
                baseCls : 'x-plain',
                ui : 'footer',
                cls : 'white-footer',
                border : false,
                frame : false,
                errorReader : Ext.create('Ext.data.XmlReader', {
                    record : 'field',
                    model : Ext.create("SuperApp.model.settings.Field"),
                    success : '@success'
                }),
                autoHeight : true,
                reference : 'clientcacert',
                margin : 5,
                padding : 5,
                defaults : {
                    labelStyle : 'margin:0 0 0 -11px;font-weight:bold;text-align:right;'
                },
                items : [{
                    xtype : 'radiogroup',
                    hideLabel : false,
                    fieldLabel : l10n('default-trusted-client-ca-root-certificates'),
                    reference : 'defaultcarootcert',
                    columns : 1,
                    labelSeparator : ' ',
                    labelWidth : 300,
                    items : [{
                        boxLabel : l10n('enabled'),
                        name : 'useDefault',
                        inputValue : 'on',
                        bind : {
                            value : '{defaultRoot}'
                        }
                    }, {
                        boxLabel : l10n('disabled'),
                        name : 'useDefault',
                        inputValue : 'off'
                    }]
                }, {
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                }],
            }];
            me.buttons = ['->', {
                text : l10n('save'),
                listeners : {
                    click : 'onClickSaveClientCert'
                }
            }, {
                text : l10n('cancel'),
                listeners : {
                    click : 'onClickCancelClientCert'
                }
            }, '->'];
            break;
        case 'cacertview' :
            me.title = l10n('select-server-ca-certficate');

            me.items = [{
                xtype : 'form',
                baseCls : 'x-plain',
                cls : 'white-footer',
                errorReader : Ext.create('Ext.data.XmlReader', {
                    record : 'field',
                    model : Ext.create("SuperApp.model.settings.Field"),
                    success : '@success'
                }),
                border : false,
                frame : false,
                autoHeight : true,
                reference : 'cacertview',
                margin : 5,
                padding : 5,
                defaults : {
                    labelStyle : 'margin:0 0 0 -11px;font-weight:bold;text-align:right;'
                },
                items : [{
                    xtype : 'component',
                    html : '<span style="color:red; font-size:10px;">' + l10n('super-security-ssl-manage-cert-int-note') + '</span>'
                }, {
                    xtype : 'fileuploadfield',
                    width : '95%',
                    emptyText : l10n('security-super-ssl-select-a-pem-crt-cer-der-file'),
                    fieldLabel : l10n('security-super-ssl-ca-certificate'),
                    hideLabel : false,
                    regex : (/.(cer|crt|pem|der)$/i),
                    allowBlank : false,
                    name : 'uploadFile',
                    labelSeparator : ' ',
                    buttonConfig : {
                        text : '',
                        iconCls : 'icon-upload'
                    }
                }, {
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                }],
                buttons : ['->', {
                text : l10n('upload'),
                formBind : true,
                listeners : {
                    click : 'onClickServerCACertUpload'
                }
            }, '->']
            }];
            break;
        case "passwordWindow" :
            me.title = l10n('export-bundle');
            me.setWidth(400);
            me.items = [{
                xtype : 'form',
                defaults : {
                    msgTarget : 'under',
                    width : 300,
                    margin : 5
                },
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                border : 0,
                errorReader : new Ext.data.XmlReader({
                    success : '@success',
                    record : 'field'
                }, ['id', 'msg']),
                ui : 'footer',
                cls : 'white-footer',
                items : [{
                    xtype : 'textfield',
                    inputType : 'password',
                    allowBlank : false,
                    fieldLabel : l10n('password'),
                    name : 'bundlePassword',
                    enableKeyEvents : true,
                    itemId : 'bundlePassword',
                    listeners : {
                        keydown : function(text, event) {
                            if (event.keyCode == 9) {
                                me.down('textfield[name=bundlePassword2]').focus();
                                event.preventDefault();
                            }
                        }
                    }
                }, {
                    xtype : 'textfield',
                    fieldLabel : l10n('confirm-password'),
                    allowBlank : false,
                    inputType : 'password',
                    name : 'bundlePassword2',
                    itemId : 'bundlePassword2',
                    vtype : 'confirmMatch',
                    initialPassField : 'bundlePassword',
                    enableKeyEvents : true,
                    listeners : {
                        keydown : function(text, event) {
                            if (event.shiftKey && event.keyCode == 9) {
                                me.down('textfield[name=bundlePassword]').focus();
                                event.preventDefault();
                            }
                        }
                    }
                }],
                buttonAlign : 'center',
                buttons : [{
                    text : l10n('export'),
                    listeners : {
                        click : 'onExportBundle'
                    }
                }]
            }];
            break;
        }

        me.callParent(arguments);
    }
});
