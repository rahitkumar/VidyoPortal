Ext.define('AdminApp.view.settings.auth.RestWebservice', {
    extend: 'Ext.form.Panel',
    alias: 'widget.restwebservice',
       
    bodyPadding: 20,
    reference:'restwebserviceform',
        controller : 'AuthenticationViewController',
    trackResetOnLoad: true,
        bodyStyle: 'background:#F6F6F6;',

initComponent: function () {
        var me =this;
me.items=[{
    xtype: 'hiddenfield',
    name: 'restFlag'
},{
    xtype: 'hiddenfield',
    name: 'enableAdminAPI'
},{
            
                    xtype: 'form',
                    hideBorders: true,
                    width: '100%',                
                    scrollable:true,
                    reference: 'restwebserviceconnectionform',
                    title: l10n('authentication-using-rest-web-service'),
                    autoHeight: true,
                    defaults: {
                    margin: 3,
                    padding: 3,
                    labelWidth: 150
                },
                items: [  {
                    xtype: 'textfield',
                    name: 'restUrl',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('url'),
                    allowBlank: false,
                    width: 500
                }],

        buttonAlign: 'center',
        buttons: [{

            text: l10n('connection-test'),
              viewtype: 'restwebserver',
                        testrest: true,
                        cls: 'enabled-buttons',
                        tooltip: l10n('check-connection-to-ws-server-and-user-authentication-before-saving'),
                        listeners: {
                            click: 'onClickConnectionTest'
                        }
        }]},
        {
                    xtype: 'usertype'
                }
   ];
    me.callParent();
}

});