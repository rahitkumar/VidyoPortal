Ext.define('AdminApp.view.settings.auth.UserType', {
    extend: 'Ext.container.Container',
    alias: 'widget.usertype',

    width: '100%',
    layout: {
        type: 'hbox',
        align: 'center'


    },
    bodyPadding: 10,

    // renderTo: 'itemselector',

    viewModel: {
        type: 'AuthenticationViewModel'
    },


    initComponent: function () {
        var me = this,
            itemSelectorStore = me.getViewModel().getStore('roleStore'),
            toRolesStore = me.getViewModel().getStore('toRolesStore');

        me.items = [{
            xtype: 'fieldset',
            title: l10n('use-selected-authentication-for-selected-user-types'),
            reference: 'usertypeview',

            autoHeight: true,

            width: '100%',
            align: 'center',
            defaults: {
                hideLabel: true
            },
            items: [{
                xtype: 'itemselector',
                name: 'authFor',
                cls: 'x-boundlist-selected-authenitication-page',
                baseCls: 'baseCls-itemselector',
                reference: 'itemselector',
                store: itemSelectorStore,
                width: '100%',
                hideNavIcons: true,
                displayField: 'roleName',
                valueField: 'roleID',
                allowBlank: false,
                msgTarget: 'side',
                fromTitle: l10n('available-types'),
                fromStore: toRolesStore,
                toTitle: l10n('selected-types'),
                listeners: {
                    validitychange: function (obj, isValid) {
                       if(!isValid){
                           Ext.getCmp('authsave').disable();
                       }else{
                          if(Ext.getCmp('isldapconnectiontestsuccess') && Ext.getCmp('isldapconnectiontestsuccess').getValue()=='true'){
                               Ext.getCmp('authsave').enable();
                          } else if(Ext.getCmp('iswsconnectiontestsuccess') && Ext.getCmp('iswsconnectiontestsuccess').getValue()=='true'){
                               Ext.getCmp('authsave').enable();
                          } 
                       }
                    }
                }
            }]
        }],
            me.callParent(arguments);
    }
});