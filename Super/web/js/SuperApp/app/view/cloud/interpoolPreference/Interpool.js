Ext.define('SuperApp.view.cloud.interpoolPreference.Interpool', {
    extend: 'Ext.panel.Panel',
    
    xtype: 'interpool',
    
   	requires: [
   		'SuperApp.view.cloud.interpoolPreference.InterpoolModel',
   		'SuperApp.view.cloud.interpoolPreference.InterpoolController',
       	'Ext.container.Container',
       	'SuperApp.view.cloud.interpoolPreference.Pools'
    ],
    
    controller: 'interpool',
    viewModel: {
    	type: 'interpool'
    },
    
    layout: {
    	type: 'vbox',
    	align: 'stretch'
    },	
    
    items: [{
    	xtype: 'container',
    	id: 'svgContainer',
    	height: 350,
    	layout: 'fit'
    }, {
    	xtype: 'pools',
    	flex: 1
    }]
});
