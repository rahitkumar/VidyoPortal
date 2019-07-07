Ext.define('SuperApp.model.components.MainModel', {
    extend: 'Ext.data.Model',
fields:["id","status", "name",{name:'comptypename', mapping:'compType.name'},"compID", "localIP","clusterIP","mgmtUrl", "configVersion", "compSoftwareVersion", "alarm", "runningVersion"]
});
