Ext.define('AdminApp.view.users.AddLegacyDevice', {
    extend : 'Ext.window.Window',
    alias : 'widget.addlegacydeviceview',
    border : false,
    width:600,
    minWidth:600,
    modal:true,
    constrain:true,
    closeAction:'hide',
    closable:true,
    reference:'addLegacyDeviceWin',
    resizeable:false,
     bind : {
                            title : '{title}'
             },
    initComponent : function() {
        this.items = [{
        	xtype:'form',
        	reference : 'addLegacyDeviceForm',
           
        	layout : {
                type : 'vbox',
                align : 'stretch'
            },
            errorReader : {
                type : 'xml',
                record : 'field',
                model : 'AdminApp.model.Field',
                success : '@success'
            },
            reader : {
                type : 'xml',
                totalRecords : 'results',
                record : 'row',
                id : 'memberID',
                model : 'AdminApp.model.users.AddLegacyDeviceModel'
            },
          
        	items:[{
                xtype : 'fieldset',
             
                width : '100%',
                margin : 5,
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [{
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                }, {
                    xtype : 'hidden',
                    name : 'memberID',
                    reference : 'memberId',
                    bind : {
                        value : '{memberID}'
                    }
                }, {
                    xtype : 'hidden',
                    name : 'roomTypeID',
                    reference:'roomTypeID',
                    value : 3
                }, {
                    xtype : 'hidden',
                    name : 'roleID',
                    reference:'roleID',
                    value : 6
                }, {
                    xtype : 'hidden',
                    name : 'roomID',
                    reference : 'roomId',
                    value : 0
                }, {
                    xtype : 'textfield',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('legacy-device-name'),
                    name : 'username',
                    reference : 'deviceName',
                    labelWidth : 150,
                    width : 400,
                    allowBlank : false,
                    maxLength: 80,
                    msgTarget : 'under',
                    margin : 10,
                    bind : {
                        readOnly : '{deviceNameReadOnly}'
                    }
                }, {
                    xtype : 'textfield',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('extension'),
                    name : 'roomExtNumber',
                    labelWidth : 150,
                    width : 400,
                    allowBlank : false,
                    maxLength: 64,
                    msgTarget : 'under',
                    margin : 10
                }]
             }],
            buttonAlign : 'center',
            buttons : [{
                        xtype : 'button',
                        text : l10n('save'),
                        formBind : true,
                        disabled : true,
                        handler : 'addLegacyDeviceSave'
                    }, {
                          xtype : 'button',
                        text : l10n('close'),
                        handler : 'legacyDeviceClose'
                    }]
            
        }];
         this.callParent();
    }
});
