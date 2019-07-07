/**
 * @class SamlAttrMapping
 */
Ext.define('AdminApp.model.settings.SamlAttrMapping', {
    extend : 'Ext.data.Model',
    
    fields : [{name: 'valueID', type: 'int'},
              {name: 'mappingID', type: 'int'},
              {name: 'vidyoValueName', type: 'string'},
              {name: 'idpValueName', type: 'string'}]
});
