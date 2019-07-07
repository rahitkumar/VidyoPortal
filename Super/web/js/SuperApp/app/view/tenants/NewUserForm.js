Ext.define('SuperApp.view.tenants.NewUserForm', {
	extend: 'Ext.panel.Panel',
	xtype: 'newuser',
	title: '<span class="red-label">*</span>'+ l10n('new-user'),
    items:[{
            xtype: 'textfield',
            fieldLabel: '<span class="red-label">*</span>' + l10n('user-name'),
            name: 'username',
            maxLength: 80,
            allowBlank: false
        }, {
            xtype: 'textfield',
            inputType: 'password',
            fieldLabel: '<span class="red-label">*</span>' + l10n('password'),
            name: 'password1',
            vtype: 'password',
            initialPassField: 'password2',
            allowBlank: false,
            maskRe: /[^ ]/
        }, {
            xtype: 'textfield',
            fieldLabel: '<span class="red-label">*</span>' + l10n('verify-password'),
            vtype: 'password',
            inputType: 'password',
            name: 'password2',
            initialPassField: 'password1',
            allowBlank: false,
            maskRe: /[^ ]/
        }, {
            xtype: 'textfield',
            fieldLabel: '<span class="red-label">*</span>' + l10n('display-name'),
            name: 'memberName',
            maxLength: 80,
            allowBlank: false
        }, {
            xtype: 'textfield',
            fieldLabel: '<span class="red-label">*</span>' + l10n('e-mail-address'),
            name: 'emailAddress',
            vtype: 'email',
            allowBlank: false
        }, {
            xtype: 'textarea',
            fieldLabel: l10n('description'),
            name: 'userDescription'
        }]
});
