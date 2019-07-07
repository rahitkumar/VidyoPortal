Ext.define('AdminApp.model.settings.DialNo',{
    extend :'Ext.data.Model',
 fields:[{name: 'countryID', type: 'int'},
        			{name: 'name', type: 'string'},
                    {name: 'phoneCode', type: 'string'},
                     {name: 'flagFileName', type: 'string'},
                     {name: 'dialInLabel', type: 'string'},
                     {name: 'dialInNumber', type: 'string'}
                    ],
    
});
