Ext.define('AdminApp.view.settings.auth.LDAPAttributeMappingGrid', {
    extend : 'Ext.window.Window',
    alias : 'widget.ldapAttributemappingWin',
    modal : true,
    border : false,
    closeAction:'destroy',
    viewModel : {
        type : 'AuthenticationViewModel'
    },

    controller : 'AuthenticationViewController',
    errorReader: {
       type:'xml',
       record: 'field',
       model: 'AdminApp.model.Field',
       success: '@success'
    },
    title:l10n('ldap-attributes-mapping'),
    initComponent : function() {
    	var me = this;
    	me.items=[{
    	        	  xtype:'grid',
    	        	  width:400,
    	        	  minHeight:300,
    	        	  reference: 'ldapattributemappinggrid',
    	        	  bind:{
    	        		  store: '{ldapAtrributGridStore}'
    	        	  },
    	        	  plugins: {
                 	       ptype: 'rowediting',
                 	       clicksToEdit: 2,
                 	       autoCancel: false,
                 	       pluginId: 'ldapRowEditId',
         			  },
    	        	  selModel:{selType:'rowmodel',mode:'SINGLE'},
    	        	  tbar:[{
	        	            text: l10n('dublicate'),
	        	            icon:'js/AdminApp/resources/images/icon_add.gif',
	        	            disabled: true,
	        	            reference:'duplicateBtn',
	        	            listeners :{
	                            click : 'duplicateldapAttr'
	                        }
	        	        },{
	        	            text: l10n('delete'),
	        	            icon:'js/AdminApp/resources/images/icon_remove.gif',
	        	            disabled: true,
	        	            reference:'deleteBtn',
	        	            handler:'deleteldapAttr'
	        	      }],
    	        	  columns:[{
    	      			text: 'ID',
    	    			dataIndex: 'valueID',
    	    			hidden: true
    	    		},{
    	    			text: 'mappingID',
    	    			dataIndex: 'mappingID',
    	    			hidden: true
    	    		},{
    	    			text: l10n('portal-attribute-value'),
    	    			dataIndex: 'vidyoValueName',
    	    			sortable: false,
    	    			menuDisabled: true,
    	    			width:'50%',
    	    			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                			return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
                		}
    	    		},{
    	    			text: l10n('ldap-attribute-value'),
    	    			dataIndex: 'ldapValueName',
    	    			sortable: false,
    	    			menuDisabled: true,
    	    			width:'48%',
    	    			renderer: function(value, metaData, record, rowIndex, colIndex, store) {
    						var scope = me;
    						var idpAttributeValue = scope.lookupReference("ldapattributemappinggrid").columns[3];
                    		var idpAttribute = Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(record.get('ldapValueName')));
                    		idpAttributeValue.setEditor({
                    			xtype: 'textfield',
                        		allowBlank: true,
                        		maxLength: 1024,
                        		value: idpAttribute 
                    		});
                    
                			return Ext.util.Format.htmlEncode(Ext.util.Format.stripTags(value));
    				} 
    	    	}],
    	    	listeners:{
    	    		render:'ldapAttributemappingLoad',
    	    		cellclick:'ldapAttrGridClick'
    	    	}	  
          }]
    	 me.callParent(arguments);
    },
    buttonAlign:'center',
    buttons:[{
    	text:l10n('save'),
    	reference:'ldapAttSave',
    	handler:'ldapAttrSave'
    },{
    	text:l10n('cancel'),
    	handler:'ldapAttributeCancel'
    }]
});