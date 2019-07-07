Ext.define('AdminApp.view.users.ImportUsers', {
    extend : 'Ext.window.Window',
    alias : 'widget.importusersview',
    border : false,
    width:600,
    minWidth:600,
    modal:true,
    constrain:true,
    closeAction:'hide',
    closable:true,
    reference:'importUsersWin',
    resizeable:false,
    title :  l10n('import-users'),
    initComponent : function() {
        this.items = [{
        	xtype:'form',
        	layout : {
                type : 'vbox',
                align : 'center'
            },
            width:'100%',
            reference : 'importUsersForm',
            errorReader : {
                type : 'xml',
                record : 'field',
                model : 'AdminApp.model.Field',
                success : '@success'
            },
            border : false,
            fileUpload : true,
        	items:[{
                xtype : 'fieldset',
                border : 1,
                padding : 0,
                width : '100%',
                margin : 5,
                fieldDefaults: {
                    labelWidth: 75,
                    anchor: '100%'
                },
                layout : {
                    type : 'vbox',
                    align : 'center'
                },
                items : [ {
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                },{
                    xtype : 'filefield',
                    margin : '10 10 19 10',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('import-batch-file-to-upload'),
                    allowBlank : false,
                    msgTarget : 'under',
                    width : 510,
                    labelWidth : 200,
                    emptyText : "Select a .csv or .veb file",
                    clearOnSubmit: false,
                    labelAlign : 'under',
                    anchor : '100%',
                  
                    reference : 'importUsersUploadField',
                    name : 'client-path'
                }, {
                    xtype : 'textfield',
                    inputType: 'password',
                    fieldLabel : l10n('password-for-veb'),
                     labelWidth : 198,
                      width : 507,
                    name : 'password',
                    emptyText : l10n('password-for-veb')
                }]
            }],
                buttonAlign : 'center',
                buttons : [{
                            xtype : 'button',
                            text : l10n('import-users'),
                            formBind : true,
                            disabled : true,
                            handler : 'importMembersSave'
                        },{
                        xtype : 'button',
                        text : l10n('close'),
                        handler : 'importMembersClose'
                    }]
                   
        }];
        this.callParent();
    }
});
