/**
 * @CLASS CustomizeLogo
 */
Ext.define('SuperApp.view.settings.customization.CustomizeLogo', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.customizelogo',
    reference : 'customizelogo',
    border : false,
    title : {
    	text : '<span class="header-title">'+l10n('customize-logo')+'</span>',
    	textAlign : 'center'
    },
    initComponent : function() {
        var me = this;
         me.items = [{
                xtype : 'fieldset',
                width : '100%',
                padding : 0,
                layout : {
                    type : 'vbox',
                    align : 'stretch'
                },
                title : '<span class="header-title" style="font-size:13px;">'+l10n('super-admin-portal-logo')+' (.jpg, .gif, .png)'+'</span>',
                items : [{
                    xtype : 'form',
                    border : false,
                    width : '100%',
                    layout : {
                        type : 'vbox',
                        align : 'center'
                    },
                    reference : 'SPLogoArchiveForm',
                    errorReader : Ext.create('Ext.data.XmlReader', {
                        record : 'field',
                        model : Ext.create("SuperApp.model.settings.Field"),
                        success : '@success'
                    }),
                    items : [{
                        xtype : 'fileuploadfield',
                        emptyText : l10n('select-a-image-file-jpg-gif-png'),
                        fieldLabel : l10n('upload-logo'),
                        hideLabel : false,
                        width : 400,
                        labelSeparator : ':',
                        name : 'SPLogoArchive',
                        allowBlank : false,
                        buttonConfig : {
                            text : '',
                            iconCls : 'icon-upload'
                        }
                    },
                    {
        				xtype: 'label',
        		
        				style : {
                            'font-weight' : 'normal',
                            'color':'red'
                        },
        				text:'(145px * 50px)'
        			},{
                        xtype : 'hidden',
                        name : csrfFormParamName,
                        value : csrfToken
                    }],
                    buttons : ['->', {
                        text : l10n('upload'),
                        formBind : true,
                        formName : 'SPLogoArchiveForm',
                        ajaxUrl : 'securedmaint/savecustomizedlogo.ajax',
                        reloadUrl : 'customizedlogo.ajax',
                        successUrl : 'sp',
                        listeners : {
                            click : 'onClickUploadCustomizeLogo'
                        }
                    }, {
                        text : l10n('view'),
                        image : 'sp',
                        bind : {
                            disabled : '{isSPBtnDisabled}'
                        },
                        listeners : {
                            click : 'onClickViewCustomizeLogo'
                        }
                    }, {
                        text : l10n('remove'),
                        bind : {
                            disabled : '{isSPBtnDisabled}'
                        },
                        removeUrl : 'securedmaint/removecustomizedlogo.ajax',
                        successUrl : 'sp',
                        listeners : {
                            click : 'onClickRemoveCustomizeLogo'
                        }
                    }, '->']
                }]
            }, {
                xtype : 'fieldset',
                width : '100%',
                padding : 0,
                layout : {
                    type : 'vbox',
                    align : 'stretch'
                },
                title : '<span class="header-title" style="font-size:13px;">'+l10n('vidyodesktop-download-page-logo-jpg-gif-png')+'</span>',
                items : [{
                    xtype : 'form',
                    border : false,
                    width : '100%',
                    layout : {
                        type : 'vbox',
                        align : 'center'
                    },
                    reference : 'VDLogoArchiveForm',
                    errorReader : Ext.create('Ext.data.XmlReader', {
                        record : 'field',
                        model : Ext.create("SuperApp.model.settings.Field"),
                        success : '@success'
                    }),
                    items : [{
                        xtype : 'fileuploadfield',
                        emptyText : l10n('select-a-image-file-jpg-gif-png'),
                        fieldLabel : l10n("upload-logo"),
                        hideLabel : false,
                        width : 400,
                        labelSeparator : ':',
                        name : 'VDLogoArchive',
                        allowBlank : false,
                        buttonConfig : {
                            text : '',
                            iconCls : 'icon-upload'
                        }
                    }, {
                        xtype : 'hidden',
                        name : csrfFormParamName,
                        value : csrfToken
                    }],
                    buttons : ['->', {
                        text : l10n('upload'),
                        formBind : true,
                        formName : 'VDLogoArchiveForm',
                        ajaxUrl : 'securedmaint/savecustomizedimagelogo.ajax',
                        reloadUrl : 'customizedimagelogo.ajax',
                        successUrl : 'vd',
                        listeners : {
                            click : 'onClickUploadCustomizeLogo'
                        }
                    }, {
                        text : l10n('view'),
                        bind : {
                            disabled : '{isVDBtnDisabled}'
                        },
                        image : 'vd',
                        listeners : {
                            click : 'onClickViewCustomizeLogo'
                        }
                    }, {
                        text : l10n('remove'),
                        removeUrl : 'securedmaint/removecustomizedimagelogo.ajax',
                        successUrl : 'vd',
                        bind : {
                            disabled : '{isVDBtnDisabled}'
                        },
                        listeners : {
                            click : 'onClickRemoveCustomizeLogo'
                        }
                    }, '->']
                }]
            }];

        me.callParent(arguments);
    }
});
