Ext.define('AdminApp.view.settings.auth.LDAPMapping', {
    extend: 'Ext.container.Container',
    alias: 'widget.ldapmapping',

    bodyPadding: 20,

    width: '100%',
    layout: {
        type: 'hbox',
        align: 'center'


    },
    initComponent: function () {
        var me = this;
        me.items = [{

            xtype: 'fieldset',

            reference: 'ldapmapping',
            width: '100%',
            name: 'ldapmappingCheckbox',
            title: l10n('ldap-attributes-mapping'),
            autoHeight: true,

            checkboxToggle: true,
            collapsed: true,
            defaults: {
                margin: 8
            },

            items: [{
                xtype: 'button',
                text: l10n('edit-attributes-mapping'),
                tooltip: l10n('click-to-open-ldap-attributes-mapping-dialog-box'),
                name: 'ldapmappingbtn',
                testLdap: true,

                viewtype: 'editLDAPattribute',
                listeners: {
                    click: 'onClickConnectionTest'
                }
            }, {
                    xtype: 'button',
                    style: 'padding-left: 10px;',
                    text: l10n('test-attributes-mapping'),
                    tooltip: l10n('click-to-test-ldap-attributes-mapping-for-user'),
                    name: 'ldaptestmappingbtn',
                    testLdap: true,

                    viewtype: 'testattribute',
                    listeners: {
                        click: 'onClickConnectionTest'
                    }
                }]
            ,
            listeners: {
                collapse: 'ldapmappingCollapse',
                expand: 'ldapmappingExpand'
            }
        }];
        me.callParent();
    }
});