/**
 * @CLASS CustomizeLogo
 */
Ext.define('AdminApp.view.settings.customization.CustomizeLogo', {
     extend : 'Ext.form.Panel',
    alias : 'widget.customizelogo',
    reference : 'VDLogoArchiveForm',
   
   height:800,
    requires : ['AdminApp.view.settings.customization.CustomizationViewModel', 'AdminApp.view.settings.customization.CustomizationController'],

    viewModel : {
        type : 'CustomizationViewModel'
    },
    title : {
        text : '<span class="header-title">' + l10n('customize-logo') + '</span>',
        textAlign : 'center'
    },
   

    errorReader : Ext.create('Ext.data.XmlReader', {
                record : 'field',
                model : Ext.create("AdminApp.model.Field"),
                success : '@success'
    }),
    controller : 'CustomizationController',
    
    items:[{
            xtype : 'fieldset',
            height : '100%',

             layout : {
                type : 'vbox',
                align : 'center',
                pack : 'center',
            },
            title:l10n('vidyodesktop-download-page-logo-jpg-gif-png'),
            items : [{
                xtype : 'hidden',
                name : csrfFormParamName,
                value : csrfToken
            }, {
                xtype : 'fileuploadfield',
                emptyText : l10n('select-an-image-file')+'(*.jpg, *.gif, *.png)',
                fieldLabel : l10n('upload-logo'),
                hideLabel : false,
                padding : 5,
                margin : 5,
              width : '60%',
                //regex : (/.(jpg|gif|png)$/i),
                labelSeparator : ':',
                name : 'VDLogoArchive',
                //validateOnChange : true,
                allowBlank : false
              
            }]
        }],
        buttonAlign : 'center',
        buttons : [{
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
       }]
});
