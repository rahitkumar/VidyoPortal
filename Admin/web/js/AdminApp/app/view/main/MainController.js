/**
 * This class is the main view for the application. It is specified in app.js as the
 * "autoCreateViewport" property. That setting automatically applies the "viewport"
 * plugin to promote that instance of this class to the body element.
 *
 * TODO - Replace this content of this view to suite the needs of your application.
 */
Ext.define('AdminApp.view.main.MainController', {
    extend: 'Ext.app.ViewController',

    requires: [
        'Ext.window.MessageBox','AdminApp.view.meetingRooms.MeetingRoomsMain','AdminApp.view.groups.GroupsMain','AdminApp.view.settings.SettingsView'
    ],

    alias: 'controller.main',
    
    control:{
        'admintabpanel': {
        	tabchange: 'onTabChange',
			afterrender: 'afterTabPanelRender'
        },
        'userview': {
        	activate: 'onUserViewActivate'
        }
    },

    onTabChange: function(tabPanel, newTab, oldTab, eOpts) {
    	var tab = newTab.cls;
    	if(tab != 'aboutus' && tab != 'support' && tab != 'terms'){
    		var tabs = [],
            ownerCt = tabPanel.ownerCt,
            tokenDelimiter = ':', 
            oldToken, newToken;

	        tabs.push(newTab.id);
	        tabs.push(tabPanel.id);
	
	        while (ownerCt && ownerCt.is('tabpanel')) {
	            tabs.push(ownerCt.id);
	            ownerCt = ownerCt.ownerCt;
	        }
	        
	        newToken = tabs.reverse().join(tokenDelimiter);
	        
	        oldToken = Ext.History.getToken();
	       
	        if (oldToken === null || oldToken.search(newToken) === -1) {
	            Ext.History.add(newToken);
	           // newTab.items.get(0).lookupController().addToHistory(newToken);
	        }
    	}
    	
    },
    
    afterTabPanelRender: function(tabPanel) {
    	var me = this;
    	Ext.History.on('change', function(token) {
    		if(me.getViewModel().get('callItemClick')) {
	            var parts, length, i;
	            if (token) {
	            	var tokenDelimiter = ':';
	            		
	                parts = token.split(tokenDelimiter);
	                length = parts.length;
	                
	                // setActiveTab in all nested tabs
	                var i = 0;
	                if(parts.length > 1 && Ext.getCmp(parts[i]) && Ext.getCmp(parts[i + 1])) {
	                	Ext.getCmp(parts[i]).setActiveTab(Ext.getCmp(parts[i + 1]));
	                	i = 2;
	                    if(parts.length > 3 && parts[i] && Ext.getCmp(parts[i])) {
	                    
	                    // This is for Settings tab 
	                    	Ext.defer(function() {
		                    	if(parts[i].search('treepanel') !== -1 && Ext.getCmp(parts[i]) && Ext.getCmp(parts[i]).getStore() && Ext.getCmp(parts[i]).getStore().getNodeById(parts[i + 1])) {
			                    	var nodePath = Ext.getCmp(parts[i]).getStore().getNodeById(parts[i + 1]).getPath();
			                    	Ext.getCmp(parts[i]).selectPath(nodePath);
			                    	//if(me.getViewModel().get('callItemClick')) {
			                    		Ext.getCmp(parts[i]).fireEvent('itemclick', null, parts[i + 1]);
			                    	//}	
			                    	//me.getViewModel().set('callItemClick', true);
			                    	
			                // This is for Tenants tab
			                    } else if(parts[i].search('grid') !== -1 && Ext.getCmp(parts[i]) && Ext.getCmp(parts[i]).getStore()) {
			                    	var tenantsGrid = Ext.getCmp(parts[i]);
			                    	var gridStore = tenantsGrid.getStore();
			                    	gridStore.each(function(tenantItem) {
			                    		if(tenantItem.get('recordId') === parts[i + 1] ) {
			                    			tenantsGrid.getSelectionModel().select(tenantItem);
			                    			tenantsGrid.fireEvent('rowclick', null, parts[i + 1]);
			                    		}
			                    	});
			                    }
			                }, 10); 
	                    }
	                }
	            }
            }
            me.getViewModel().set('callItemClick', true);
        });
        
        var activeTab = tabPanel.getActiveTab();
        this.onTabChange(tabPanel, activeTab);
        
   	},
    
    onUserViewActivate: function(panel) {
    	if(panel.up('tabpanel').items.getAt(1).items.length === 0) {
	    	panel.up('tabpanel').items.getAt(1).add(Ext.create('AdminApp.view.meetingRooms.MeetingRoomsMain'));
	    	panel.up('tabpanel').items.getAt(2).add(Ext.create('AdminApp.view.groups.GroupsMain'));
	    	panel.up('tabpanel').items.getAt(3).add(Ext.create('AdminApp.view.settings.SettingsView'));
	    	
	    	if(initialHistoryToken && initialHistoryToken !== Ext.History.getToken()) {
	    		Ext.History.add(initialHistoryToken);
	    		
            	this.getViewModel().set('callItemClick', true);
	    		Ext.History.fireEvent('change', initialHistoryToken);
	    		initialHistoryToken = null;
	    	}
	    }
    	/*if(initialHistoryToken) {
    		initialHistoryToken = null;
    		Ext.History.back();
    	}*/
    },
    aboutuslinkclick: function() {
    	if(!aboutUsPanel){
    		var aboutUsPanel = Ext.create('Ext.window.Window', {
    			closeAction: 'hide',
    			title:l10n('about_us'),
    	        modal: true,
    	        animateTarget: 'about',
    	        animCollapse: true,
    			header: true,
    			width: 580,
    			height: 485,
    			border:false,
    			closable: true,
    			shadowOffset: 30,
    			layout: 'fit',
    			constrain:true,
    			resizable:false,
    		    items:[{
    		             xtype : 'form',
    		             border : false,
    		             margin:10,
    		             id:'aboutForm',
    		             scrollable:true,
    		             errorReader : new Ext.data.XmlReader({
    		                 successProperty : '@success',
    		                 record : 'field'
    		             }, ['id', 'msg'])
    		  }],
    		  buttonAlign:'center',
    		  buttons:[{
    			  text:l10n('close'),
    			  handler:function(){
    				  aboutUsPanel.hide();
    			  }
    		  }],
    		  listeners: {
    			render:function(){
    				   var form = Ext.getCmp('aboutForm');
    		      	  Ext.Ajax.request({
    					   url:'about_content.html',
						   method: 'GET',
    					   params: form.getForm().getValues(),
    					   success:function(res){
    						 var aboutInfo = res.responseText;
    		 	             if(aboutInfo && aboutInfo != null && aboutInfo != ''){
    		 	            	form.setHtml(aboutInfo);
    		 	             }else{
    		 	            	form.setHtml(l10n('about-us-full'));
    		 	             }
    						},failure: function(){
    							Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
    					   }
    				   }); 
    			}
    		  }
    	   }).show();
    	}
    },
	supportlinkclick:function() {
		if(!supportPanel){
			var supportPanel = Ext.create('Ext.window.Window',{
			    closeAction: 'hide',
			    title:l10n('contact_us'),
		        modal: true,
		        animateTarget: 'support',
		        animCollapse: true,
				header: true,
				width: 560,
				height: 350,
				border:false,
				closable: true,
				shadowOffset: 30,
				layout: 'fit',
				constrain:true,
				resizable:false,
			    items:[{
			             xtype : 'form',
			             border : false,
			             margin:10,
			             id:'supportForm',
			             scrollable:true,
			             errorReader : new Ext.data.XmlReader({
			                 successProperty : '@success',
			                 record : 'field'
			             }, ['id', 'msg'])
			   }],
			   buttonAlign:'center',
			   buttons:[{
				  text:l10n('close'),
				  handler:function(){
					  supportPanel.hide();
				  }
			   }],
			   listeners:{
				   render:function(){
					   var form = Ext.getCmp('supportForm');
		           	   Ext.Ajax.request({
		    			   url:'contact_content.html',
						   method: 'GET',
		    			   params: form.getForm().getValues(),
		    			   success:function(res){
		    				     var xmlResponse = res.responseText;
		    				    form.setHtml(xmlResponse);
		    				},failure: function(){
		    					Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
		    			   }
		        	   });
				   }
			   }
		  }).show();
		}
    },
    termslinkclick:function() {
		if(!termsPanel){
			 var termsPanel = Ext.create('Ext.window.Window',{
				    closeAction: 'hide',
				    title:l10n('terms_of_services'),
			        modal: true,
			        animateTarget: 'termsofservices',
			        animCollapse: true,
					header: true,
					width: 800,
					height: 470,
					border:false,
					closable: true,
					shadowOffset: 30,
					layout: 'fit',
					constrain:true,
					resizable:false,
			        items:[{
			                    xtype : 'form',
			                    border : false,
			                    margin:10,
			                    id:'termsForm',
			                    scrollable:true,
			                    errorReader : new Ext.data.XmlReader({
			                        successProperty : '@success',
			                        record : 'field'
			                    }, ['id', 'msg'])
			        }],
			        buttonAlign:'center',
			  	    buttons:[{
			  		  text:l10n('close'),
			  		  handler:function(){
			  			termsPanel.hide();
			  		  }
			  	    }],
			        listeners:{
			        	render:function(){
			        		var form = Ext.getCmp('termsForm');
			            	Ext.Ajax.request({
			     			   url:'terms_content.html',
							   method: "GET",
			     			   params: form.getForm().getValues(),
			     			   success:function(res){
			     				     var xmlResponse = res.responseText;
			     				     form.setHtml(xmlResponse);
			     				},failure: function(){
			     					Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
			     			   }
			         		});
			        	}
			        }
			  }).show();
		}
    },
        logoutAdmin: function(){
        	logoutAdmin();
        },
        goToHome: function(){
        	window.location.reload();
        }, 
    helplinkclick: function(){
	     var scope=this;
	   	 Ext.Ajax.request({
	   		url:'guidelocation.ajax',
			method: "GET",
	   		success: function(res){
	   			var xmlResponse =  res.responseText;
	   			window.open(xmlResponse);
	   		},
	   		failure: function(){
	   			Ext.Msg.alert(l10n('failure'),l10n('request-failed'));
	   		}
	   	 });
    }
});
