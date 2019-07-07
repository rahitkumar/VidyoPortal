Ext.define('SuperApp.model.settings.UserAttribute',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'enableUserImage',
        type :'boolean'
    },{
        name :'enableUserImageUpload',
        type :'boolean'
    },
    {
        name :'maxImageSize',
        type :'int'
    }
   ]
    
});
