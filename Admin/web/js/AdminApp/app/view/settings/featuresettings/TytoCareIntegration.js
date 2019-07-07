 /**
 * @class TytoCareIntegration
 */
Ext.define('AdminApp.view.settings.featuresettings.TytoCareIntegration', {
        extend: 'Ext.form.Panel',
        alias: 'widget.tytocareintegration',
        reference: 'tytocareintegrationform',
        border: false,
        height: 800,
        requires: ['AdminApp.view.settings.featuresettings.FeatureSettingsController', 'AdminApp.view.settings.featuresettings.FeatureSettingsViewModel',
        	'AdminApp.model.settings.TytoCareIntegrationModel'],
        controller: 'FeatureSettingsController',
        viewModel: {
            type: 'FeatureSettingsViewModel'
        },
        title: {
            text: '<span class="header-title">' + l10n('tytocare-integration') + '</span>',
            textAlign: 'center'
        },
        reader : {
    		type : 'xml',
    		model : 'AdminApp.model.settings.TytoCareIntegrationModel',
    		totalRecords : 'results',
    		record : 'row',
    		rootProperty : 'dataset'
    	},
    	errorReader : {
    		type : 'xml',
    		model : 'AdminApp.model.settings.TytoCareIntegrationModel',
    		record : 'row',
    		successProperty : '@success'
    	},
        items: [{
            xtype: 'fieldset',
            height: '100%',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'center',
            },
            items: [{
                xtype: 'hiddenfield',
                name: 'istytocareconnectionsuccess',
                id:'istytocareconnectionsuccess'
            }, {
                xtype: 'checkbox',
                name: 'enableTytoCareIntegration',
                reference:'enableTyto',
                margin: 20,
                columns: 1,
                width: 500,
                labelWidth: 300,
                inputValue: '1',
            	uncheckedValue: '0',
                fieldLabel: l10n('enable-tytocare'),
                // checked : (enableTytoCareIntegration == 1 ? true : false),
                bind:'{enableTytoCareIntegration}'
            }, {
                xtype : 'textfield',
		  		name : 'tytoUrl',
		  		width: 400,
                labelWidth: 100,
                maxLength : 2048,
                allowBlank: false,
		  		fieldLabel : l10n('tytocare-url'),
		  		msgType : 'under',
		  		bind: {
		  			value: '{tytoUrl}',
		  			disabled: '{!enableTyto.checked}'
		  		}
	    	}, {
                xtype : 'textfield',
				name : 'tytoUsername',
				width: 400,
                labelWidth: 100,
				maxLength : 256,
				allowBlank: false,
				fieldLabel : l10n('tytocare-username'),
				msgType : 'under',
				bind:{
					value: '{tytoUsername}',
					disabled: '{!enableTyto.checked}'
				}
	    	}, {
                xtype : 'textfield',
                inputType : 'password',
				name : 'tytoPassword',
				width: 400,
                labelWidth: 100,
				maxLength : 256,
				allowBlank: false,
				maskRe : /[^ ]/,
				fieldLabel : l10n('tytocare-password'),
				msgType : 'under',
				bind:{
					value: '{tytoPassword}',
					disabled: '{!enableTyto.checked}'
				}
	    	}, {
                    xtype: 'button',
                    viewtype: 'restwebserver',
                    cls: 'enabled-buttons',
                    //style: 'padding-left: 10px;',
                    text: l10n('connection-test'),
                    tooltip: l10n('check-connection-to-ws-server-and-user-authentication-before-saving'),
                    //tooltip: l10n('click-to-test-ldap-attributes-mapping-for-user'),
                    name: 'tytocaretestmappingbtn',
                    id: 'tytocaretestmappingbtn',
                    testrest: true,
                    bind:{
                    	disabled: '{!enableTyto.checked}'
                    	},
                    //viewtype: 'testattribute',
                    listeners: {
                        click: 'onClickTytoCareConnectionTest'
                    }
                }]
    }],
    buttonAlign: 'center',
    buttons: [{
        text: l10n('save'),
        formBind:true,
        name: 'tytocaresave',
        listeners: {
            click: 'onClickSaveTytoCareIntegration'
        }
    }, {
        text: l10n('cancel'),
        bind:{
        	disabled: '{!enableTyto.checked}'
        	},
        listeners: {
            click: 'onClickCancelTytoCareIntegration'
        }
    }]
});