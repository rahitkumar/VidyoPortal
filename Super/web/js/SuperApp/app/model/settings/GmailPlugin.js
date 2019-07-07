Ext.define('SuperApp.model.settings.GmailPlugin',{
    extend :'Ext.data.Model',
     
    fields :[{
        name :'gmailPluginVersion',
        mapping :'gmailPlugin > gmailPluginVersion'
    },{
        name :'gmailPluginInstalled',
        mapping :'gmailPlugin > gmailPluginInstalled',
        type :'boolean'
    },{
        name :'betaFeatureEnabled',
        mapping :'gmailPlugin > betaFeatureEnabled',
        type :'boolean'
    },{
        name :'gmailPluginDeleteLabel',
        mapping : 'gmailPlugin > gmailPluginDeleteLabel'
    },{
        name :'gmailPluginSaveMsgBoxDesc',
        mapping : 'gmailPlugin > gmailPluginSaveMsgBoxDesc'
    }]
});
