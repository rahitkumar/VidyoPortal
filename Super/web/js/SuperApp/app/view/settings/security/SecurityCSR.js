/**
 * @class SecurityCSR
 */

Ext.define('SuperApp.view.settings.security.SecurityCSR', {
    extend : 'Ext.form.Panel',
    alias : 'widget.securitycsr',

    requires : ['SuperApp.view.settings.security.SecurityViewModel', 'SuperApp.view.settings.security.SecurityController'],

    layout : {
        type : 'vbox',
        align : 'center'
    },
    title : {
    	text : '<span class="header-title">' + l10n('super-security-ssl-manage-csr')+ '</span>',
    	textAlign : 'center'
    },
    reference : 'securitycsr',
    buttonAlign : 'center',
    initComponent : function() {
        var me = this;
        me.items = [{
                xtype : 'fieldset',
                title : l10n('super-security-ssl-csr-content'),
                width : '100%',
                padding : 5,
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                defaults : {
                    labelWidth : 300
                },
                items : [{
                    xtype : 'label',
                    margin : 0,
                    width : '80%',
                    text : l10n('super-security-private-csr-notes')
                }, {
                    xtype : 'textfield',
                    name : 'country',
                    fieldLabel : '<span class="red-label">*</span><a href=\"http://www.iso.org/iso/country_codes/iso_3166_code_lists/country_names_and_code_elements.htm\" target=\"_blank\">'+l10n('country-code')+'</a>',
                    align : 'center',
                    regex:/^[A-Za-z]{2}$/,
                    allowBlank : false,
                    bind : {
                        value : '{country}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'state',
                    allowBlank : false,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('super-security-ssl-csr-state-name'),
                    bind : {
                        value : '{state}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'city',
                    allowBlank : false,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('super-security-ssl-csr-locality-name'),
                    bind : {
                        value : '{city}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'company',
                    allowBlank : false,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('super-security-ssl-csr-organization-name'),
                    bind : {
                        value : '{company}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'division',
                    allowBlank : false,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('super-security-ssl-csr-organizational-unit-name'),
                    bind : {
                        value : '{division}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'domain',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('super-security-ssl-csr-common-name'),
                    allowBlank : false,
                    bind : {
                        value : '{domain}'
                    }
                }, {
                    xtype : 'textfield',
                    name : 'email',
                    fieldLabel : l10n('super-security-ssl-csr-email-address'),
                    bind : {
                        value : '{email}'
                    }
                }]
            }, {
            	xtype : 'fieldset',
            	title : l10n('CSR'),
            	width : '100%',
            	items : [{
            	    xtype : 'label',
            	    name : 'servercertnotes',
            	    text : l10n('super-security-ssl-manage-csr-note')
            	}, {
            	    xtype : 'textareafield',
            	    name : 'csr',
            	    fieldCls : 'key-text',
            	    bind : {
            		value : '{csr}'
            	    },
            	    labelAlign : 'left',
            	    readOnly : true,
            	    height : 300,
            	    width : '100%'
            	}]
            }];
        me.buttons = [{
            text : l10n('reset'),
            listeners : {
                click : 'onClickCSRReset'
            }
        }, {
            text : l10n('super-security-ssl-manage-key-regenerate'),
            formBind : true,
            listeners : {
                click : 'onClickSCRRegenerate'
            }
        }];
        me.callParent(arguments);
    }
}); 