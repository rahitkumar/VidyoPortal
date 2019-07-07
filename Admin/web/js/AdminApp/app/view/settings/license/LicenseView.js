/**
 * @class LicenseView
 */
Ext.define('AdminApp.view.settings.license.LicenseView',{
    extend :'Ext.grid.Panel',
    alias : 'widget.licenseview',
    
    requires :[
        'AdminApp.view.settings.license.LicenseViewModel',
        'AdminApp.view.settings.license.LicenseController'
    ],
    xtype: 'array-grid',
    reference : 'licensegrid',
    width:'100%',
    frame:false,
         border:false,
   height:800,
    viewModel :{
        type :'LicenseViewModel'
    },
     title : {
        text : '<span class="header-title">' + l10n('SuperSystemLicense-license') + '</span>',
        textAlign : 'center'
    },
    controller : 'LicenseController',
    
    bind : {
                store : '{licenseStore}'
            },
    listeners:{
                render:'getLicenseData'
   },
    initComponent : function() {
        var me = this;
        
        me.columns  = [{
                dataIndex : 'feature',
                text : 'Feature',
              width:'49%',
                style : {
                    'font-size' : '12px' 
                }
            },{
                dataIndex : 'license',
                text : l10n('SuperSystemLicense-license'),
                border :false,
               width:'50%',
                 style : {
                    'font-size' : '12px' 
                }
            }],
          
        
        me.callParent(arguments);
    }
});
