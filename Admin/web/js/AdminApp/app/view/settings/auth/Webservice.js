Ext.define('AdminApp.view.settings.auth.Webservice', {
    extend: 'Ext.container.Container',
    alias: 'widget.webservice',

    bodyPadding: 20,

    controller: 'AuthenticationViewController',
initComponent: function () {
        var me =this;
me.items=[{
            
                xtype:'form',
                reference:'webserviceform',
                   bodyStyle: 'background:#F6F6F6;',
                items:[{
                    xtype: 'fieldset',
                    hideBorders: true,
                    width: '100%',                
                    scrollable:true,
                    reference: 'webservice',
                    title: l10n('authentication-using-web-service'),
                    autoHeight: true,
                    defaults: {
                    margin: 3,
                    padding: 3,
                    labelWidth: 150
                },
                items: [{
                    xtype: 'hiddenfield',
                    name: 'iswsconnectiontestsuccess',
                    id:'iswsconnectiontestsuccess'
                },{
                    xtype: 'hiddenfield',
                    name: 'wsflag'
                }, {
                    xtype: 'hiddenfield',
                    name: 'enableAdminAPI'
                }, {
                    xtype: 'textfield',
                    name: 'wsurl',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('url'),
                    allowBlank: false,
                    width: 500
                }, {
                    xtype: 'textfield',
                    name: 'wsusername',
                    reference: 'wsusername',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('username'),
                    allowBlank: false,
                    width: 500
                }, {
                    xtype: 'textfield',
                    name: 'wspassword',
                    reference: 'wspassword',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('password'),
                    allowBlank: false,
                    inputType: 'password',
                    width: 500
                }]
            }],

        buttonAlign: 'center',
        buttons: [{

            text: l10n('connection-test'),
              viewtype: 'webserver',
                        testLdap: false,
                        cls: 'enabled-buttons',
                        tooltip: l10n('check-connection-to-ws-server-and-user-authentication-before-saving'),
                        listeners: {
                            click: 'onClickConnectionTest'
                        }
        }]
    },
        {
                    xtype: 'usertype'
                }
    ];
    me.callParent();
}

});