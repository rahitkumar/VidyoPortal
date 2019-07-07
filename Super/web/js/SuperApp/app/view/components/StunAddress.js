Ext.define('SuperApp.view.components.StunAddress', {
    extend : 'Ext.form.Panel',
    alias : 'widget.stunaddress',

    border : false,

    title : l10n('stun-server-address'),

    titleAlign : 'center',

    reference : 'stunpart',

    width : 600,

    cls : 'white-header-footer',

    border : 1,

    layout : {
        type : 'hbox',
        align : 'stretch'
    },

    defaults : {
        margin : 5,
    },

    initComponent : function() {
        this.items = [{
            xtype : 'textfield',
            fieldLabel : l10n('ip-fqdn'),
            name : 'stunFqdn',
            labelAlign : 'right',
            maxLength : 256,
            maskRe: /[^ ]/
        }, {
            xtype : 'numberfield',
            fieldLabel : l10n('port'),
            name : 'stunPort',
            labelAlign : 'right',
            allowDecimals : false,
            hideTrigger : true,
            minValue : 0,
            maxValue : 65535
        }];
        this.callParent(arguments);
    }
});
