Ext.define('SuperApp.view.cloud.endPointRules.EndPointRule', {
    extend: 'Ext.panel.Panel',
    
    xtype: 'endPointRule',
    
    requires : ['Ext.container.Container', 'SuperApp.view.cloud.endPointRules.EPR', 'SuperApp.view.cloud.endPointRules.SingleRuleDetails', 'SuperApp.view.cloud.endPointRules.EPRController', 'SuperApp.view.cloud.endPointRules.EPRModel'],
    
    controller: 'EPR',
    
    viewModel: {
    	type: 'EPR'
    },
    width: 650,
    reference: 'endPointRuleContainer',
    
    border : 0,

    layout: {
        type: 'card',
        animation: {
            type: 'slide',
        }
    },		
    
    items: [{
    	xtype: 'epr'
    }, {
    	xtype: 'singleRuleDetails'
    }]
});
