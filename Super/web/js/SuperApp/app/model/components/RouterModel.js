Ext.define('SuperApp.model.components.RouterModel', {
    extend: 'Ext.data.Model',
    fields: ['id',
             {name:'compID', mapping:'components.compID'},
            {name:'name', mapping:'components.name', convert: function(v){return Ext.util.Format.htmlDecode(v);}},
             {name:'mgmtUrl', mapping:'components.mgmtUrl', convert: function(v){return Ext.util.Format.htmlDecode(v);}},
             {name:'routerpkID', mapping:'id'},
             {name:'componentpkId', mapping:'components.id'},
            {name:'scipFqdn',convert: function(v){return Ext.util.Format.htmlDecode(v);}},
            {name:'stunFqdn',convert: function(v){return Ext.util.Format.htmlDecode(v);}},
            'scipPort','mediaPortStart','mediaPortEnd','dscpVidyo','audioDscp','contentDscp','singnalingDscp','routerPoolPresent'],
	 proxy: {
	     type: 'ajax',
	     url: 'getvidyorouter.ajax?',
	     reader: {
	     	type: 'json',
	     	rootProperty: 'items'
	     }
	 }         
});
