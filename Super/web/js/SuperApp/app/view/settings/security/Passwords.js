Ext.define('SuperApp.view.settings.security.Passwords', {
    extend : 'Ext.form.Panel',
    alias : 'widget.passwords',
    reference : 'passwords',
    width : '100%',
    border : false,

    layout : {
        type : 'vbox',
        align : 'stretch'
    },
    
    title : {
        text : l10n('passwords-tablabel'),
        textAlign : 'center'
    },

    initComponent : function() {
        var me = this;
        me.items = [{
            xtype : 'fieldset',
            width : '98%',
            padding : 0,
            margin : 5,
            layout : {
                type : 'vbox',
                align : 'center'
            },
            defaults : {
                labelWidth : 250,
                width : 500
            },
            items : [{
                xtype : 'numberfield',
                fieldLabel : l10n('super-customize-passwordsform-expiredays-label'),
                name : 'expieryDays',
                reference : 'expieryDays',
                bind : {
                    value : '{expiryDays}'
                },
                allowDecimals : false,
                minValue : 0,
            }, {
                xtype : 'numberfield',
                fieldLabel : l10n('super-customize-passwordsform-inactivedays-label'),
                name : 'inactiveDays',
                reference : 'inactiveDays',
                allowDecimals : false,
                bind : {
                    value : '{inactiveDays}'
                },
                minValue : 0
            }, {
                xtype : 'numberfield',
                fieldLabel : l10n('super-customize-passwordsform-failcount-label'),
                name : 'failCount',
                reference : 'failCount',
                allowDecimals : false,
                bind : {
                    value : '{failCount}'
                },
                minValue : 0

            }, {
                xtype : 'checkbox',
                fieldLabel : l10n('enforce-password-complexity-rules'),
                name : 'passwordComplexity',
                reference : 'passwordComplexity',
            }, {
                xtype : 'checkbox',
                fieldLabel : l10n('disable-password-recovery-for-super-accounts'),
                name : 'disableForgetPasswordSuper',
                reference : 'disableForgetPasswordSuper',
            }, //
            {
                xtype : 'numberfield',
                minValue : 1,
                enforceMaxLength : true,
                maxLength : 5,
                name : 'sessionExpPeriod',
                bind : {
                    value : '{sessionExpPeriod}'
                },
                decimalPrecision: 0,
                maskRe: /[0-9]/,
                fieldLabel : l10n('session-exp-period'),
            }, {
                xtype : 'numberfield',
                allowBlank :false,
                msgTarget : 'under',
                minValue : 3,
                maxValue : 12,
                name : 'minPINLength',
                bind : {
                    value : '{minPINLength}'
                },
                fieldLabel : l10n('minimum-pin-length')

            }]
        }];
        me.buttons = ['->', {
            text : l10n('save'),
            formBind: true,
           listeners : {
               click : 'onClickSavePasswords'
           }
       }, {
           text : l10n('default'),
           handler : 'passwordsDefault'
       }, {
           text : l10n('reset'),
           handler : 'passwordsReset'
       }, '->'];
        me.callParent(arguments);
    }
});
