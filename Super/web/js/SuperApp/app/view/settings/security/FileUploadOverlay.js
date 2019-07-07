/**
 * @Class FileUploadOverlay
 *
 */
Ext.define('SuperApp.view.settings.security.FileUploadOverlay', {
    extend : 'Ext.window.Window',
    alias : 'widget.fileupload',
    reference : 'fileupload',
    modal : true,
    title : l10n('super-security-ssl-select-private-key'),
    border : false,
    initComponent : function() {
        var me = this;

        me.items = [{
            xtype :'form',
            border : false,
            errorReader : Ext.create('Ext.data.XmlReader', {
                record : 'field',
                model : Ext.create("SuperApp.model.settings.Field"),
                success : '@success'
            }),
            items :[{
                xtype : 'label',
                text : l10n('security-ssl-key-warning-importing-a-new-private-key-will-invalidate-any-existing-csr-or-server-certs'),
                style : 'color:red; font-size:11px;',
                margin : 0,
                padding : 0
            }, {
                xtype : 'fileuploadfield',
                emptyText : l10n('super-security-key-upload-file-types'),
                fieldLabel : l10n('ssl-private-key'),
                regex : (/.(key)$/i),
                labelSeparator : ':',
                name : 'uploadFile',
                validateOnChange : true,
                allowBlank : false,
                labelWidth : 150,
                buttonConfig : {
                    text : '',
                    iconCls : 'icon-upload'
                }
            }, {
                xtype : 'textfield',
                inputType : 'password',
                fieldLabel : l10n('super-security-ssl-password-if-any'),
                labelSeparator : ':',
                name : 'keyPassword',
                labelWidth : 150
            },{
            	xtype:'hidden',
     			name: csrfFormParamName,
     			value: csrfToken
             }],
            buttons : ['->', {
                text : l10n('upload'),
                formBind : true,
                listeners : {
                    click : 'uploadKeyFile'
                }
            }, '->']
        }];

        me.callParent(arguments);
    }
});
