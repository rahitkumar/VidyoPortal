Ext.define('SuperApp.model.settings.VidyoMobile',{
    extend :'Ext.data.Model',
     
    fields :[{
        name : 'mobileLogin',
        mapping : 'vidyomobile > mobileLogin',
        type : 'int'
    },{
        name :'vidyoMobileTitle',
        mapping :'vidyomobile > vidyoMobileTitle'
    },{
        name :'vidyoMobileSaveConfirmBoxTitle',
        mapping :'vidyomobile > vidyoMobileSaveConfirmBoxTitle'
    },{
        name :'vidyoMobileSaveConfirmBoxDesc1',
        mapping :'vidyomobile > vidyoMobileSaveConfirmBoxDesc1'
    },{
        name :'vidyoMobileSaveConfirmBoxDesc2',
        mapping :'vidyomobile > vidyoMobileSaveConfirmBoxDesc2'
    }]
});
