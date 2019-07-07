/**
 * @class AuthenticationViewModel
 */
Ext.define('AdminApp.view.settings.auth.AuthenticationViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.AuthenticationViewModel',

    data : {
        wsflag : false,
        wsurl : '',
        wsusername : '',
        wspassword : '',
        restFlag:false,
        restUrl:'',
        ldapflag : false,
        ldapurl : '',
        ldapusername : '',
        ldappassword : '',
        ldapbase : '',
        ldapfilter : '',
        ldapscope : '',
        enableAdminAPI : false,
        ldapmappingflag : false,
        samlflag:false,
        samlIdpMetadata : '',
        samlSecurityProfile : '',
        samlSSLProfile : '',
        samlSignMetadata : '',
        windowTitle: '',
        rowIndex:'',
        webServerRestartNeeded:false,
        pkiCertReviewPending:false
       // minPINLength : 6
    },
    stores : {
        authenticationStore : {
            fields : ['cacflag','userNameExtractfrom','ocspcheck','ocsprespondercheck','ocspresponder','ocspnonce','cacldapflag','pkiCertReviewPending','webServerRestartNeeded','isSSLEnabled','wsflag', 'wsurl', 'wsusername', 'wspassword', 'restFlag','restUrl','ldapflag', 'ldapurl', 'ldapusername', 'ldappassword', 'ldapbase', 'ldapfilter', 'ldapscope', 'enableAdminAPI','ldapmappingflag', 'samlflag','samlmappingflag', 'samlIdpMetadata', 'samlSecurityProfile', 'samlSSLProfile', 'samlSignMetadata', 'samlSpEntityId','idpAttributeForUsername'],
            proxy : {
                type : 'ajax',
                url : 'authentication.ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        roleStore : {
            model : 'AdminApp.model.settings.Roles',
            proxy : {
                type : 'ajax',
                url : 'roles.ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            autoLoad: false
        },
        toRolesStore : {
            model : 'AdminApp.model.settings.Roles',
            proxy : {
                type : 'ajax',
                url : 'toroles.ajax',
                actionMethods : {
                    create : "POST",
                    read : "GET",
                    update : "POST",
                    destroy : "POST"
                },

                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            autoLoad: false
        },
        ldapMappingStore : {
            fields : [{
                name : 'mappingID',
                type : 'int'
            }, {
                name : 'tenantID',
                type : 'int'
            }, {
                name : 'vidyoAttributeName',
                type : 'string'
            }, {
                name : 'vidyoAttributeDisplayName',
                type : 'string'
            }, {
                name : 'ldapAttributeName',
                type : 'string'
            }, {
                name : 'defaultAttributeValue',
                type : 'string'
            }, {
                name : 'attrValueMapping',
                type : 'string'
            }, {
                name : 'qtipAttrValueMapping',
                type : 'string'
            }],
            proxy : {
                type : 'ajax',
                url : 'ldapmapping.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            }
        },
        samlMappingStore : {
            fields : [{
                name : 'mappingID',
                type : 'int'
            }, {
                name : 'tenantID',
                type : 'int'
            }, {
                name : 'vidyoAttributeName',
                type : 'string'
            }, {
                name : 'vidyoAttributeDisplayName',
                type : 'string'
            }, {
            	name : 'idpAttributeName',
                type : 'string'
            }, {
                name : 'ldapAttributeName',
                type : 'string'
            }, {
                name : 'defaultAttributeValue',
                type : 'string'
            }, {
                name : 'attrValueMapping',
                type : 'string'
            }, {
                name : 'qtipAttrValueMapping',
                type : 'string'
            }],
            proxy : {
                type : 'ajax',
                url : 'samlmapping.ajax',
                method : 'GET',
                reader : {
                    type : 'xml',
                    totalRecords : 'results',
                    record : 'row',
                    rootProperty : 'dataset'
                }
            },
            remoteSort: true
        },
        authTypeStore:{
        	fields: [{name: 'type', type: 'string'},
        			{name: 'name', type: 'string'}],
            proxy:{
        		type:'ajax',
        		url:'authenticationParam.ajax',
                     method:'POST',
        		  reader : {
	                 type : 'xml',	               
	     			 record: 'row',
	                 rootProperty : 'dataset'
	             }
        	},
        	pageSize:0,
            autoLoad:false
        },
        groupds:{
        	fields:[{name: 'groupID', type: 'int'},
        			{name: 'groupName', type: 'string'}],
        	proxy:{
        		type:'ajax',
        		url:'groups.ajax',
        		reader:{
        			type:'xml',
        			totalRecords: 'results',
        			record: 'row',
        			id: 'groupID'
        		}
        	},
        	pageSize:0,
            autoLoad:true
        },
        roleds:{
        	fields:[{name: 'roleID', type: 'int'},
        			{name: 'roleName', type: 'string'}],
        	proxy:{
        		type:'ajax',
        		url:'roles.ajax',
        		reader:{
        			type:'xml',
        			totalRecords: 'results',
        			record: 'row',
        			id: 'roleID'
        		}
        	},
        	pageSize:0,
            autoLoad:true
        },
        samlroleds:{
        	fields:[{name: 'roleID', type: 'int'},
        			{name: 'roleName', type: 'string'}],
        	proxy:{
        		type:'ajax',
        		url:'samlroles.ajax',
        		reader:{
        			type:'xml',
        			totalRecords: 'results',
        			record: 'row',
        			id: 'roleID'
        		}
        	},
        	pageSize:0,
            autoLoad:true
        },
        proxyds: {
        	fields:[{name: 'proxyID', type: 'int'},
        			{name: 'proxyName', type: 'string'}],
        	proxy:{
        		type:'ajax',
        		url:'proxies.ajax',
        		reader:{
        			type:'xml',
        			totalRecords: 'results',
        			record: 'row',
        			id: 'proxyID'
        		}
        	},
        	pageSize:0,
            autoLoad:true
        },
        locationTagds:{
        	fields:[{name: 'locationID', type: 'int'},
        			{name: 'locationTag', type: 'string'}],
        	proxy:{
        		type:'ajax',
        		url:'locationtags.ajax',
        		reader:{
        			type:'xml',
        			totalRecords: 'results',
        			record: 'row',
        			id: 'locationID'
        		}
        	},
        	pageSize:0,
            autoLoad:true
        },
        samlAttrMappingGridStore: {
        	autoLoad: false,
        	fields: [{name: 'valueID', type: 'int'},
				{name: 'mappingID', type: 'int'},
				{name: 'vidyoValueName', type: 'string'},
				{name: 'idpValueName', type: 'string'}
			],
        	proxy: {
        		type: 'ajax',
        		url: 'samlvaluemapping.ajax',
        		method : 'GET',
	            reader : {
	                type : 'xml',
	                totalRecords : 'results',
	                record : 'row',
	                rootProperty : 'dataset'
	            }
        	}
        },
        ldapAtrributGridStore: {
        	fields:[
					{name: 'valueID', type: 'int'},
					{name: 'mappingID', type: 'int'},
					{name: 'vidyoValueName', type: 'string'},
					{name: 'ldapValueName', type: 'string'}
            ],
        	proxy :{
        		 type : 'ajax',
	             url : 'ldapvaluemapping.ajax',
	             method:'POST',
	             reader : {
	                 type : 'xml',
	                 totalRecords: 'results',
	     			 record: 'row',
	                 rootProperty : 'dataset'
	             }
        	},
        	listeners:{
        		load:'ldapAttrionLoad'
        	} 
         },
         
          certificateStore: {
        
         
        	fields:[
					{name: 'certificateName', type: 'string'},
                    {name: 'notBefore', type: 'string'},
                    {name: 'notAfter', type: 'string'},
                    {name: 'serialNo', type: 'string'}
					
            ],
        	proxy :{
        		 type : 'ajax',
	             url : 'certificateextracter.ajax',
	             method:'POST',
                 extraParams: {
                    stageFlag: 'true'
                },
	             reader : {
	                 type : 'xml',
	                 totalRecords: 'results',
	     			 record: 'row',
	                 rootProperty : 'dataset'
	             }
        	},
             listeners:{
                 load:'afterCertficateStgLoad'
             },
             autoLoad:true,
         
         },
         certificateStorePERM: {
      
         
        	fields:[
					{name: 'certificateName', type: 'string'},
                    {name: 'notBefore', type: 'string'},
                    {name: 'notAfter', type: 'string'},
                    {name: 'serialNo', type: 'string'}
					
            ],
        	proxy :{
        		 type : 'ajax',
	             url : 'certificateextracter.ajax',
	             method:'POST',
                 extraParams: {
                    stageFlag: 'false'
                },
	             reader : {
	                 type : 'xml',
	                 totalRecords: 'results',
	     			 record: 'row',
	                 rootProperty : 'dataset'
	             }
        	},
             autoLoad:true,
             listeners:{
                 load:'afterCertficatePermLoad'
             }
         
         }
          
       
    }
});
