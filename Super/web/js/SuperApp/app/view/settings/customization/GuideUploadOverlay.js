/**
 * @class GuideUploadOverlay
 */
Ext.define('SuperApp.view.settings.customization.GuideUploadOverlay', {
    extend : 'Ext.window.Window',
    alias : 'widget.guideuploadoverlay',
    reference : 'guideuploadoverlay',

    modal : true,

    title : l10n('upload-new-guide-file-pdf-doc-docx'),

    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    guideType : undefined,

    comboValue : undefined,

    parentview : undefined,

    initComponent : function() {
        var me = this;

        me.items = [{
            xtype : 'radiogroup',
            colums : 1,
            margin : '10 10 10 80',
            padding : 5,
            name : 'GuidePropertyGroup',
            items : [{
                name : 'GuidePropertyGroup',
                boxLabel : l10n('store-locally'),
                inputValue : "storelocal",
                checked : true
            }, {
                name : 'GuidePropertyGroup',
                boxLabel : l10n('link-to-a-different-web-server'),
                inputValue : "diffwebserver"
            }],
            listeners : {
                change : function(rg, newVal) {
                    var value = newVal["GuidePropertyGroup"];

                    switch (value) {
                    case "storelocal" :
                        me.down('form[name=webServerForm]').setHidden(true);
                        me.down('form[name=storeLocalForm]').setHidden(false);
                        break;
                    case "diffwebserver" :
                        me.down('form[name=webServerForm]').setHidden(false);
                        me.down('form[name=storeLocalForm]').setHidden(true);
                        break;
                    }
                }
            }
        }, {
            xtype : 'form',
            name : 'storeLocalForm',
            reference : 'storeLocalForm',
            border : false,
            ui : 'footer',
            cls : 'white-footer',
            errorReader : Ext.create('Ext.data.XmlReader', {
                record : 'field',
                model : Ext.create("SuperApp.model.settings.Field"),
                success : '@success'
            }),
            items : [{
                xtype : 'fileuploadfield',
                emptyText : l10n('select-file'),
                hideLabel : false,
                padding : 5,
                width : 500,
                margin : 15,
                regex : (/.(pdf|doc|docx)$/i),
                labelSeparator : ':',
                name : 'guideUpload',
                validateOnChange : true,
                allowBlank : false,
                buttonConfig : {
                    text : '',
                    iconCls : 'icon-upload'
                },
                fieldLabel : l10n('upload-file'),
                labelWidth : 100
            }, {
                xtype : 'hidden',
                name : csrfFormParamName,
                value : csrfToken
            }],
            buttons : ['->', {
                text : l10n('upload'),
                formBind : true,
                listeners : {
                    click : 'onClickUploadGUOverlay'
                }

            }, {
                text : l10n('cancel'),
                listeners : {
                    click : function() {
                        me.close();
                    }
                }
            }, '->']
        }, {
            xtype : 'form',
            hidden : true,
            layout : {
                type : 'auto',
                align : 'stretch'
            },
            border : false,
            ui : 'footer',
            cls : 'white-footer',
            name : 'webServerForm',
            reference : 'webServerForm',
            margin : 5,
            items : [{
                xtype : 'textfield',
                fieldLabel : l10n('enter-url'),
                allowBlank : false,
                name : 'urlLocation',
                margin : 5,
                width : 500,
                vtype : 'url'
            }],
            buttons : ['->', {
                text : l10n('save'),
                formBind : true,
                listeners : {
                    click : 'onClickSaveGUOverlay'
                }
            }, {
                text : l10n('cancel'),
                listeners : {
                    click : function() {
                        me.close();
                    }
                }
            }, '->']
        }];

        me.callParent(arguments);
    }
});
