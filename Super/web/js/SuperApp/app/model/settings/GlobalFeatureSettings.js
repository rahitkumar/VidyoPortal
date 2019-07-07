Ext.define('SuperApp.model.settings.GlobalFeatureSettings', {
    extend : 'Ext.data.Model',

    fields : [{
        name : 'vidyoWebVersion',
        mapping : 'vidyoweb > vidyoWebVersion'
    }, {
        name : 'vidyoWebAvailable',
        mapping : 'vidyoweb > vidyoWebAvailable',
        type : 'boolean'
    }, {
        name :'vidyoWebAvailableTitle',
        mapping :'vidyoweb > vidyoWebAvailableTitle'
    },{
        name :'vidyoWebAvailableDesc',
        mapping :'vidyoweb > vidyoWebAvailableDesc'
    },{
        name :'vidyoWebEnabledTitle',
        mapping :'vidyoweb > vidyoWebEnabledTitle'
    },{
        name :'vidyoWebEnabledDesc',
        mapping :'vidyoweb > vidyoWebEnabledDesc'
    },{
        name : 'vidyoWebEnabled',
        mapping : 'vidyoweb > vidyoWebEnabled',
        type : 'boolean'
    }, {
        name : 'mobileLogin',
        mapping : 'vidyomobile > MobileLogin',
        type : 'int'
    }, {
        name : 'tiles16Available',
        mapping : 'VidyoDesktop > tiles16Available',
        type : 'boolean'
    }, {
        name : 'showDisabledRoomsEnabled',
        mapping : 'searchOptions > showDisabledRoomsEnabled'
    }, {
        name : 'superManaged',
        mapping : 'ipc > superManaged'
    }, {
        name : 'adminManaged',
        mapping : 'ipc > adminManaged'
    }, {
        name : 'guideLoc',
        mapping : 'ipc > guideLoc'
    }, {
        name : 'tlsProxyEnabled',
        mapping : 'ipc > tlsProxyEnabled'
    }, {
        name : 'gmailPluginVersion',
        mapping : 'gmailPlugin > gmailPluginVersion'
    }, {
        name : 'gmailPluginInstalled',
        mapping : 'gmailPlugin > gmailPluginInstalled'
    }, {
        name : 'betaFeatureEnabled',
        mapping : 'gmailPlugin > betaFeatureEnabled'
    }, {
        name : 'tlsProxyFeatureEnabled',
        mapping : 'gmailPlugin > tlsProxyFeatureEnabled'
    }, {
        name : 'chatAvailable',
        mapping : 'chat > chatAvailable'
    }, {
        name : 'dafaultPublicChatEnabled',
        mapping : 'chat > dafaultPublicChatEnabled'
    }, {
        name : 'defaultPrivateChatEnabled',
        mapping : 'chat > defaultPrivateChatEnabled'
    }]
});
