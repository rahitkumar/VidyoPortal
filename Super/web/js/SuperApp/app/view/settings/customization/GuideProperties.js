/**
 * @Class GuideProperties
 */
Ext.define('SuperApp.view.settings.customization.GuideProperties', {
    extend : 'Ext.panel.Panel',
    alias : 'widget.guideproperties',

    reference : 'guideproperties',

    requires : ['SuperApp.view.settings.customization.GuideUploadOverlay'],

    border : false,

    defautls : {
        margin : 5
    },
    layout : {
        type : 'vbox',
        align : 'stretch'
    },

    initComponent : function() {
        var me = this;

        me.items = [
			{
			    xtype : 'form',
			    name : 'vidyoConferenceform',
			    width : '100%',
			    layout : {
					type : 'vbox',
					align : 'center'
			    },
			    title : {
			    	text : '<span class="header-title">'+l10n('adminguide-label')+'</span>',
			    	textAlign : 'center'
			    },
			    border : false,
			    errorReader : Ext.create('Ext.data.XmlReader', {
				record : 'field',
				model : Ext.create("SuperApp.model.settings.Field"),
					success : '@success'
			    }),
			    bodyStyle: {
			        padding: '10px'
			    },
			    items : [{
						xtype : 'combobox',
						fieldLabel : l10n('system-language'),
						labelWidth : 150,
						margin : 5,
						width : 300,
						name : 'vclocation',
						reference : 'cGuidelocation',
						bind : {
						    store : '{sysLocation}'
						},
						value : 'en',
						valueField : 'langCode',
						displayField : 'langName',
						triggerAction : 'all',
						allowBlank : true,
						editable : false,
					
						resizable : false,
						minChars : 1,
						listeners : {
						    change : 'onChangeLocationGP'
						}
				    }, {
					xtype : 'component',
					margin : 5,
					bind : {
					    html : '<span style="color:red"><a href={vConfrenceURL} target="_blank">{vConfrenceURL}</a></span>'
					}
				    }],
				    buttons : ['->', {
					text : l10n('change-location'),
					guideType : 'admin',
					comboValue : 'en',
					listeners : {
					    click : 'onClickChangeLocationGP'
					}
				    }, '->']
				}];
        	me.callParent(arguments);
    	}
	});
