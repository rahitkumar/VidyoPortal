Ext.define('SuperApp.view.settings.CDRExportPurge', {
	 extend: 'Ext.panel.Panel',
   width:'400',
   floating:true,
   draggable:true,
   modal:true,
   autoDestroy:true,
   border:true,
   title:l10n('cdr-export-purge'),
   defaultType: 'textfield',
   tools:[{
  	 tooltipType:'qtip',
       type: 'close',
       callback: function() {
    	   
       }}],
       layout: {
           type: 'vbox',
           align: 'left'
       },
   items:[

  {
      xtype:'fieldset',
       margin:'10 10 10 10',
      title: l10n('tenant'), // title or checkboxToggle creates fieldset header
      columnWidth: 1,
      width:'90%',
      collapsible:false,
      collapsed: false, // fieldset initially collapsed
      layout:'anchor',
      items :[{
          xtype: 'panel',
          anchor: '100%',
          title: '',
          frame: false,
          border:false,
          items: [
        {
            xtype      : 'fieldcontainer',
            fieldLabel : '',
            defaultType: 'radiofield',
            defaults: {
                flex: 1
            },
            layout: 'vbox',
            items: [
                {
                    boxLabel  : l10n('one-tenant'),
                    name      : 'tenant',
                    inputValue: 'one',
                    id        : 'radio1'
                }, {
                    boxLabel  : l10n('all-tenants'),
                    name      : 'tenant',
                    inputValue: 'all',
                    id        : 'radio2'
                }
            ]
        },
        {
            xtype:'combobox'
        }
    ]
          
      }]
  },{
	  xtype:'fieldset',
      margin:'10 0 10 10',
     title: l10n('period-of-time'), // title or checkboxToggle creates fieldset header
     columnWidth: 1,
     width:'90%',
     collapsible:false,
     collapsed: false, // fieldset initially collapsed
     layout:'anchor',
     items :[{
         xtype: 'panel',
         anchor: '100%',
         title: '',
         frame: false,
         border:false,
         items:[{
        xtype: 'datefield',
        anchor: '100%',
        fieldLabel: l10n('date-from'),
        name: 'from_date',
        maxValue: new Date(),  // limited to the current date or prior
              value: new Date()
    }, {
        xtype: 'datefield',
        anchor: '100%',
        fieldLabel: l10n('date-to'),
        name: 'to_date',
        value: new Date()  // defaults to today
    }]
         
     }]
  }
  
  ],
    buttonAlign:'center',
    buttons:[{
        text:l10n('export')
    },{
        text:l10n('purge')
    }]
});