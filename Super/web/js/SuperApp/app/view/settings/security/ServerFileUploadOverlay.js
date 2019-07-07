/**
 * @Class FileUploadOverlay
 *
 */
Ext.define('SuperApp.view.settings.security.ServerFileUploadOverlay', {
    extend : 'Ext.window.Window',
    alias : 'widget.serverfileupload',

    modal : true,

    title : l10n('super-security-ssl-select-private-key'),

    padding : 2,

    modal : true,

    defaults : {
        padding : 5
    },

    border : false,

    initComponent : function() {
        var me = this;

        me.items = [{
            xtype :'form',
            errorReader : Ext.create('Ext.data.XmlReader', {
                record : 'field',
                model : Ext.create("SuperApp.model.settings.Field"),
                success : '@success'
            }),
            border : false,
            items :[{
                xtype : 'fileuploadfield',
                emptyText : l10n('security-super-ssl-select-a-pem-crt-cer-der-file'),
                fieldLabel : l10n('security-super-ssl-certificate'),
                hideLabel : false,
                width : 400,
                regex : (/.(pem|cer|crt|der)$/i),
                labelSeparator : ':',
                name : 'uploadFile',
                validateOnChange : true,
                allowBlank : false,
                buttonConfig : {
                    text : '',
                    iconCls : 'icon-upload'
                }
            },{
            	xtype:'hidden',
     			name: csrfFormParamName,
     			value: csrfToken
             }],
            bbar : ['->', {
                text : l10n('upload'),
                formBind : true,
                listeners : {
                    click : 'uploadServerCertificateFile'
                }
            }, '->']
        }];

        me.callParent(arguments);
    }
});
