/**
 * @class GuestView
 */
Ext.define('AdminApp.view.settings.guest.GuestView', {
    extend : 'Ext.form.Panel',
    alias : 'widget.guestview',

    requires : ['AdminApp.view.settings.guest.GuestViewModel', 'AdminApp.view.settings.guest.GuestController'],
    
    viewModel : {
        type : 'GuestViewModel'
    },
     title : {
        text : '<span class="header-title">' + l10n('guest-s-settings') + '</span>',
        textAlign : 'center'
    },
    controller : 'GuestController',
    height : 800,
    errorReader : Ext.create('Ext.data.XmlReader', {
    record : 'field',
    model : Ext.create("AdminApp.model.Field"),
    success : '@success'
    }),
    items : [{
        
            xtype : 'fieldset',
            height : '100%',
           
            layout : {
                type : 'vbox',
                align : 'center',
                pack : 'center',
            },
            items : [{
                xtype : 'combo',
                name : 'groupID',
                reference : 'groupguest',
                fieldLabel : '<span class="red-label">*</span>'+l10n('guest-group'),
                labelWidth : 200,
                margin : '20',
                bind : {
                  store : '{groupStore}'
                },
                valueField : 'groupID',
                displayField : 'groupName',
                typeAhead : false,
                loadingText : l10n('searching'),               
                emptyText : l10n('select-a-group-for-all-guests'),
                editable : false,
                resizable : false,
                       
                width : 500
            }, {
                xtype : 'combo',
                name : 'proxyID',
                reference : 'proxyguest',
                fieldLabel : '<span class="red-label">*</span>'+l10n('guest-proxy'),
                labelWidth : 200,
                bind : {
                  store : '{proxyStore}'
                },
                valueField : 'proxyID',
                displayField : 'proxyName',
                typeAhead : false,
                loadingText : l10n('searching'),
              

                emptyText : l10n('select-a-proxy-for-all-guests'),
               
                editable : false,
                resizable : false,
               
                tabIndex : 2,
                width : 500
            }, {
                xtype : 'combo',
                name : 'locationID',
                reference : 'locationguest',
                fieldLabel : '<span class="red-label">*</span>'+l10n('guest-location-tag'),
                labelWidth : 200,
                bind : {
                    store : '{locationTagStore}'
                },
                valueField : 'locationID',
                displayField : 'locationTag',
                typeAhead : false,
                loadingText : l10n('searching'),
                triggerAction : 'all',
                emptyText : l10n('select-a-location-tag-for-all-guests'),
              
                editable : false,
               
                resizable : false,
              
             
               
                tabIndex : 3,
                width : 500
            
            }]
        }],
            buttonAlign : 'center',
            buttons : [{
                xtype : 'button',
                text : l10n('save'),
                formBind : true,
                handler :'onClickSaveGuestView'
               

            },
            {
                xtype : 'button',
                text : l10n('cancel'),
                handler :'getGuestSettingsData'
               

            }]
       
        
    });
