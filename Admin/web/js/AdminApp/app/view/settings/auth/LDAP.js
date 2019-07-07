Ext.define('AdminApp.view.settings.auth.LDAP', {
  extend: 'Ext.form.Panel',
    alias: 'widget.ldap',
        controller: 'AuthenticationViewController',
    trackResetOnLoad: true,
    reference:'ldapform',

    bodyPadding: 20,
      scrollable: false,
      bodyStyle: 'background:#F6F6F6;',
    requires: ['AdminApp.view.settings.auth.LDAPConnection',
                'AdminApp.view.settings.auth.LDAPMapping', 
                'AdminApp.view.settings.auth.UserType'],
                  
initComponent: function () {
        var me =this;
me.items=[{
            xtype: 'hiddenfield',
            name: 'ldapflag'
     
        },
            {
                xtype: 'hiddenfield',
                name: 'ldapmappingflag'
            },
           
            {
            
                xtype: 'fieldset',
                title: l10n('authentication-using-ldap'),
                width: '100%',
         layout: {
            type: 'vbox',
            align: 'center'
        },

                reference: 'ldap',                 
                defaults: {
                    margin: 3,
                    padding: 3,
                    labelWidth: 150,
                   // bodyStyle: 'background:#F6F6F6;'
                },
                items: [
                {
                
                        xtype: 'ldapconnection',
                },{
                    xtype: 'ldapmapping',
                },{
                    xtype: 'usertype',
                }]
                     
                   
       }];
       me.callParent();
}
});