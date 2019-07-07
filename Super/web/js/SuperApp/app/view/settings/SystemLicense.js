Ext.define('SuperApp.view.settings.SystemLicense', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.systemlicense',
    viewModel : {
        type : 'SettingsViewModel'
    },
       border: false,
    frame: false,
    requires : ['SuperApp.view.settings.SettingsViewModel', 'SuperApp.model.settings.SLFormModel', 'SuperApp.model.settings.Field'],
    items : [{
        xtype : 'form',
        reference : 'sysLicenseForm',
        bodyPadding: 5,
        title: {
            text: '<span class="header-title">'+l10n('upload-system-license')+'</span>',
            textAlign :'center'
        },
        border: true,
        width: '100%',
        height: '100%',
        layout: {
        	type :'vbox',
        	align : 'center',
        	pack: 'center'
        },
        reader : {
            type : 'xml',
            totalRecords : 'results',
            record : 'row',
            id : 'vmID',
            model : 'SuperApp.model.settings.SLFormModel'
        },
        errorReader : {
            type : 'xml',
            record : 'field',
            model : 'SuperApp.model.settings.Field',
            success : '@success'
        },
        items : [{
            xtype: 'fieldset',
            width : '100%',
            layout: {
            	type :'vbox',
            	align : 'center',
            	pack: 'center'
            },
            items: [{
                xtype : 'textfield',
                reference : 'licenseTextField',
                bind : {
                    fieldLabel : '{licenseText}',
                    value : '{fqdn}'
                },
                labelAlign : 'right',
                name : 'vmID',
                reference : 'vmId',
                readOnly : true,
                width: 500,
                labelWidth: 125
            }, {
                xtype : 'filefield',
                name : 'client-path',
                fieldLabel : l10n('SuperSystemLicense-upload-system-license-file'),
                msgTarget : 'side',
                allowBlank : false,
                blankText : '',
                labelAlign : 'right',
                buttonConfig : {
                    text : 'Browse'
                },
                width: 500,
                labelWidth: 125
            },{
                xtype : 'hidden',
                name : csrfFormParamName,
                value : csrfToken
            }]
        }],
        buttonAlign: 'center',
        buttons : [
			{
			    text : l10n('super-security-ssl-manage-upload'),
			    formBind : true, //only enabled once the form is valid
			    disabled : true,
			    handler : 'systemLicenseUpload'
			}
        ]
    },{
            xtype : 'grid',
            width : '100%',
            scroll : 'vertical',
            title : {
            	text: '<span style="font-size:15px">'+l10n('system-license')+'</span>',
            	textAlign : 'center'
            },            
            reference : 'sysLicenseGrid',
            bind : {
                store : '{systemLicenseGridStore}'
            },
            border: true,
            columns : [{
                text : l10n('SuperSystemLicense-feature'),
                dataIndex : 'feature',
                flex : 1,
                style : {
                    'text-align' : 'center',
                    'align' : 'center'
                }
            }, {
                text : l10n('SuperSystemLicense-license'),
                flex : 1,
                dataIndex : 'license',
                style : {
                    'text-align' : 'center',
                    'align' : 'center'
                }
            }]
        }]
	});
