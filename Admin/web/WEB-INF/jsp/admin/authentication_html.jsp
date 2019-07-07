<%@ include file="header_html.jsp" %>

	<div id="maincontent">

		<div id="content-panel">&nbsp;</div>

	</div>

<div id="saml-win" class="x-hidden">
    <div class="x-window-header">View SAML Service Provider (SP) Metadata XML</div>
    <div id="saml-panel">
        <div id="saml-spmetadata">

        </div>
    </div>
</div>


<%@ include file="footer_html.jsp" %>

<script type='text/javascript' src='<c:url value="/js/FieldOverride.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/DDView.js"/>'></script>
<script type="text/javascript" src='<c:url value="/js/Multiselect.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/RowActions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ComboLoadValue.js"/>'></script>

<script type="text/javascript">


var samlViewerWin;


Ext.override(Ext.form.RadioGroup, {
	setValue: function(id, value){
		if(this.rendered){
			if(arguments.length > 1){
				var f = this.getBox(id);
				if(f){
					f.setValue(value);
					if(f.checked){
						this.items.each(function(item){
							if (item !== f){
								item.setValue(false);
							}
						}, this);
					}
				}
			}else{
				this.setValueForItem(id);
			}
		}else{
			this.values = arguments;
		}
		return this;
	},

	setValueForItem: function(val){
		this.items.each(function(item){
			if(item.inputValue == val){
				item.setValue(true);
			}
		}, this);
	}
});

Ext.ux.SimpleColumn = function(){
    function  reconfigItems(items,clayouts,fieldset){
        var result = [] , index = 0;
        for(var i=0 ; i < clayouts.length ; i++){
            var	crow = clayouts[i],
                    columnCnt = {
                        layout : 'column',
                        border : false
                    },
                    rowItems = [];
            for(var j=0 , l = crow.length; j < l ; j++){
                rowItems.push({
                    columnWidth : crow[j],
                    layout : 'form',
                    border : false,
                    items  : items[ index++ ]
                });
            }
            result.push(Ext.apply(columnCnt,{ items : rowItems }));
        }
        if(fieldset){
            result = [{
                xtype : 'fieldset',
                title : '',
                autoHeight : true,
                items  : result
            }]
        }
        return result;
    }

    return {
        init : function(cmp){
            var	clayouts = cmp.clayouts ,
                    items = cmp.initialConfig.items ,
                    fieldset = cmp.fieldset ;

            if(!clayouts){
                return ;
            }
            items = Ext.isArray(items) ? items : [items];
            cmp.items.clear();
            cmp.add.apply(cmp,reconfigItems(items,clayouts,fieldset));
        }
    }
}();

Ext.onReady(function(){
	Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';
	Ext.QuickTips.init();
	Ext.form.Field.prototype.msgTarget = 'under';

	var msg = function(title, msg, callback){
		Ext.Msg.show({
			title: title,
			msg: msg,
			minWidth: 200,
			modal: true,
			icon: Ext.Msg.INFO,
			buttons: Ext.Msg.OK,
			fn: callback
		});
	};

	var localmenu = new Ext.Panel({
		collapsible: false,
		autoHeight: true,
		border: false,
		frame: false,
		autoLoad: {url: '<c:url value="menu_content.html?settings=1&authentication=1"/>'}
	});

	var roleRec = Ext.data.Record.create([
		{name: 'roleID', type: 'string'},
		{name: 'roleName', type: 'string'}
	]);

	var fromStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: '<c:url value="fromroles.ajax"/>'
		}),
		reader: new Ext.data.XmlReader({
			totalRecords: 'results',
			record: 'row',
			id: 'roleID'
		}, roleRec),
		remoteSort: true
	});

	fromStore.setDefaultSort('roleID', 'ASC');
	fromStore.load();

	var toStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: '<c:url value="toroles.ajax"/>'
		}),
		reader: new Ext.data.XmlReader({
			totalRecords: 'results',
			record: 'row',
			id: 'roleID'
		}, roleRec),
		remoteSort: true
	});

	toStore.setDefaultSort('roleID', 'ASC');
	toStore.load();

	var auth = new Ext.data.Record.create([
		{name: 'wsflag', type: 'boolean'},
		{name: 'wsurl', type: 'string'},
		{name: 'wsusername', type: 'string'},
		{name: 'wspassword', type: 'string'},
		{name: 'ldapflag', type: 'boolean'},
		{name: 'ldapurl', type: 'string'},
		{name: 'ldapusername', type: 'string'},
		{name: 'ldappassword', type: 'string'},
		{name: 'ldapbase', type: 'string'},
		{name: 'ldapfilter', type: 'string'},
		{name: 'ldapscope', type: 'string'},
		{name: 'enableAdminAPI', type: 'boolean'},
		{name: 'ldapmappingflag', type: 'boolean'},
        {name: 'samlflag', type: 'boolean'},
        {name: 'samlIdpMetadata', type: 'string'},
        {name: 'samlSpEntityId', type: 'string'},
        {name: 'samlSecurityProfile', type: 'string'},
        {name: 'samlSSLProfile', type: 'string'},
        {name: 'samlSignMetadata', type: 'string'},
        {name: 'samlmappingflag', type: 'int'},
        {name: 'idpAttributeForUsername', type: 'string'}
	]);

	var authReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row'
	}, auth);

	var ldapinputwin;
	var wsinputwin;

	var ldapmappingwin;
	var attrmappingwin;
	var ldaptestmappingwin;

	var tanantLdapMappingRecord = Ext.data.Record.create([
		{name: 'mappingID', type: 'int'},
		{name: 'tenantID', type: 'int'},
		{name: 'vidyoAttributeName', type: 'string'},
		{name: 'vidyoAttributeDisplayName', type: 'string'},
		{name: 'ldapAttributeName', type: 'string'},
		{name: 'defaultAttributeValue', type: 'string'},
		{name: 'attrValueMapping', type: 'string'},
		{name: 'qtipAttrValueMapping', type: 'string'}
	]);

	var tanantLdapMappingStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: '<c:url value="ldapmapping.ajax"/>'
		}),
		reader: new Ext.data.XmlReader({
			totalRecords: 'results',
			record: 'row'
		}, tanantLdapMappingRecord),
		remoteSort: true
	});

	var attrValueMapping = new Ext.ux.grid.RowActions({
		header: '<spring:message code="value.mapping"/>',
		autoWidth: false,
		width: 70,
		actions: [{
			iconIndex: 'attrValueMapping',
			qtipIndex: 'qtipAttrValueMapping'
		}]
	});

	attrValueMapping.on({
		action:function(grid, record, action, row, col) {
			switch(action){
				case 'icon-value-map':
					attrmappingwin = new Ext.Window({
						title: '<spring:message code="attribute.values.mapping"/>',
						closable: true,
						closeAction: 'hide',
						resizable: false,
						width: 400,
						autoHeight: true,
						border: true,
						frame: true,
						modal: true,
						items: [
							attributeValueMappingGrid
						],
						listeners: {
							show: function(el,type) {
								attributeValueMappingStore.load({params:{mappingID:record.data.mappingID}});
								attributeValueMappingGrid.getView().refresh();
							},
							hide: function(el,type) {
								//Ext.getCmp('save').focus(true,100);
							}
						}
					});
					attrmappingwin.show();
				break;
			}
		}
	});

	var role = new Ext.data.Record.create([
		{name: 'roleID', type: 'int'},
		{name: 'roleName', type: 'string'}
	]);

	var roleReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row',
		id: 'roleID'
	}, role);

	var roleds = new Ext.data.Store({
		url: '<c:url value="roles.ajax"/>',
		reader: roleReader,
		remoteSort: false
	});

	roleds.setDefaultSort('roleID', 'ASC');

	var roleEditor = new Ext.form.ComboBox({
		store: roleds,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'roleName',
		displayField: 'roleName',
		editable: false,
		listeners: {
			beforerender: function(c){
				roleds.load();
			}
		},
		plugins: new ComboLoadValue()
	});
	
	var samlroleds = new Ext.data.Store({
		url: '<c:url value="samlroles.ajax"/>',
		reader: roleReader,
		remoteSort: false
	});

	samlroleds.setDefaultSort('roleID', 'ASC');

	var samlRoleEditor = new Ext.form.ComboBox({
		store: samlroleds,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'roleName',
		displayField: 'roleName',
		editable: false,
		listeners: {
			beforerender: function(c){
				samlroleds.load();
			}
		},
		plugins: new ComboLoadValue()
	});

	var group = new Ext.data.Record.create([
		{name: 'groupID', type: 'int'},
		{name: 'groupName', type: 'string'}
	]);

	var groupReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row',
		id: 'groupID'
	}, group);

	var groupds = new Ext.data.Store({
		url: '<c:url value="groups.ajax"/>',
		reader: groupReader,
		remoteSort: false
	});

	groupds.setDefaultSort('groupName', 'ASC');

	var groupEditor = new Ext.form.ComboBox({
		store: groupds,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'groupName',
		displayField: 'groupName',
		editable: false,
		listeners: {
			beforeshow: function(c){
				groupds.load();
			}
		},
		plugins: new ComboLoadValue()
	});

	var proxy = new Ext.data.Record.create([
		{name: 'proxyID', type: 'int'},
		{name: 'proxyName', type: 'string'}
	]);

	var proxyReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row',
		id: 'proxyID'
	}, proxy);

	var proxyds = new Ext.data.Store({
		url: '<c:url value="proxies.ajax"/>',
		reader: proxyReader,
		remoteSort: false
	});

	proxyds.setDefaultSort('proxyName', 'ASC');

	var proxyEditor = new Ext.form.ComboBox({
		store: proxyds,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'proxyName',
		displayField: 'proxyName',
		editable: false,
		listeners: {
			beforeshow: function(c){
				proxyds.load();
			}
		},
		plugins: new ComboLoadValue()
	});

	var locationTag = new Ext.data.Record.create([
		{name: 'locationID', type: 'int'},
		{name: 'locationTag', type: 'string'}
	]);

	var locationTagReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row',
		id: 'locationID'
	}, locationTag);

	var locationTagds = new Ext.data.Store({
		url: '<c:url value="locationtags.ajax"/>',
		reader: locationTagReader,
		remoteSort: false
	});

	locationTagds.setDefaultSort('locationID', 'ASC');

	var locationTagEditor = new Ext.form.ComboBox({
		store: locationTagds,
		mode: 'local',
		triggerAction: 'all',
		valueField: 'locationTag',
		displayField: 'locationTag',
		editable: false,
		listeners: {
			beforeshow: function(c){
				locationTagds.load();
			}
		},
		plugins: new ComboLoadValue()
	});

	function renderReadOnlyColor(value, metaData, record, rowIndex, colIndex, store){
		if (record.data.vidyoAttributeName == "UserName" || record.data.vidyoAttributeName == "Extension") {
			metaData.css = 'red-row';
			return value;
		} else {
			return value;
		}
	}

	var tanantLdapMappingColumnModel = new Ext.grid.ColumnModel({
		columns: [{
				id:'mappingID',
				header: 'ID',
				dataIndex: 'mappingID',
				hidden: true
			},{
				id:'tenantID',
				header: "tenant",
				dataIndex: 'tenantID',
				hidden: true
			},{
				header: 'attribute',
				dataIndex: 'vidyoAttributeName',
				hidden: true
			},{
				header: '<spring:message code="portal.attribute.name"/>',
				dataIndex: 'vidyoAttributeDisplayName',
				sortable: false,
				menuDisabled: true
			},{
				header: '<spring:message code="ldap.attribute.name"/>',
				dataIndex: 'ldapAttributeName',
				sortable: false,
				menuDisabled: true,
				editable: true
			},{
				header: '<spring:message code="default.value"/>',
				dataIndex: 'defaultAttributeValue',
				sortable: false,
				menuDisabled: true,
				editable: true,
				renderer: renderReadOnlyColor
			},
				attrValueMapping
		],
		editors: {
			'text': new Ext.grid.GridEditor(
					new Ext.form.TextField({
						allowBlank: true,
						style: 'font-size: 12px; padding-top: 1px; padding-left: 4px;'
					})),
			'group': new Ext.grid.GridEditor(groupEditor),
			'role': new Ext.grid.GridEditor(roleEditor),
			'proxy': new Ext.grid.GridEditor(proxyEditor),
			'locationtag': new Ext.grid.GridEditor(locationTagEditor)
		},
		getCellEditor: function(colIndex, rowIndex) {
			var field = this.getDataIndex(colIndex);
			if (field == 'ldapAttributeName') {
				return this.editors['text'];
			}
			if (field == 'defaultAttributeValue') {
				var rec = tanantLdapMappingStore.getAt(rowIndex);
				if (rec.data['vidyoAttributeName'] == 'Group') {
					return this.editors['group'];
				} else if (rec.data['vidyoAttributeName'] == 'UserType') {
					return this.editors['role'];
				} else if (rec.data['vidyoAttributeName'] == 'Proxy') {
					return this.editors['proxy'];
				} else if (rec.data['vidyoAttributeName'] == 'LocationTag') {
					return this.editors['locationtag'];
				} else {
					return this.editors['text'];
				}
			}
			return Ext.grid.ColumnModel.prototype.getCellEditor.call(this, colIndex, rowIndex);
		}
	});

	var tanantLdapMappingGrid = new Ext.grid.EditorGridPanel({
		store: tanantLdapMappingStore,
		cm: tanantLdapMappingColumnModel,
		plugins: [attrValueMapping],
		height: 400,
		border: true,
		frame: true,
		autoScroll: true,
		clicksToEdit: 2,
		viewConfig: {
			forceFit: true
		},
		buttonAlign: 'center',
		buttons: [{
			text: '<spring:message code="save"/>',
			handler: function(){
				var rec = tanantLdapMappingStore.getModifiedRecords();
				var modifiedRecordCount = rec.length;
				var errorMsg = '<spring:message code="save.failed"/>';
				var successCount = 0;
				var responseCount = 0;
				for (var r = 0; r < rec.length; r++){
					Ext.Ajax.request({
						url: '<c:url value="saveldapmapping.ajax"/>',
						waitMsg: '<spring:message code="saving"/>',
						params: {
							mappingID: rec[r].get('mappingID'),
							ldapAttributeName: rec[r].get('ldapAttributeName'),
							defaultAttributeValue: rec[r].get('defaultAttributeValue')
						},
						success: function (response, opts) {
							responseCount++;
							
							var errors = '';
							var resp = response.responseXML;
							var result = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
							
							if (result == 'false') {
								var errorsNode = Ext.DomQuery.select('message/errors/field', response.responseXML);
								for (var i = 0; i < errorsNode.length; i++) {
									errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>'; 
								}
								errorMsg += '<br>' + errors;
							} else {
								successCount++;
							}
							if(responseCount == modifiedRecordCount) {
								if(responseCount == successCount) {
									tanantLdapMappingStore.commitChanges();
									ldapmappingwin.hide();
								} else {
									msg('<spring:message code="message"/>', errorMsg, function(){});
								}
							}
						},
						failure:function(response, opts) {
							responseCount++;
							
							var errors = '';
							var resp = response.responseXML;
							var result = Ext.DomQuery.selectValue('message/@success', response.responseXML).trim();
							
							if (result == 'false') {
								var errorsNode = Ext.DomQuery.select('message/errors/field', response.responseXML);
								for (var i = 0; i < errorsNode.length; i++) {
									errors += Ext.DomQuery.selectValue('id', errorsNode[i]) + ' - ' + Ext.DomQuery.selectValue('msg', errorsNode[i]) + '<br>'; 
								}
								errorMsg += '<br>' + errors;
							}
							
							if(responseCount == modifiedRecordCount) {
								msg('<spring:message code="message"/>', errorMsg, function(){});
							}
						}
					});
				}
			}
		},{
			text: '<spring:message code="cancel"/>',
			handler: function(){
				ldapmappingwin.hide();
			}
		}]
	});

	tanantLdapMappingGrid.getColumnModel().isCellEditable = function(colIndex, rowIndex){
		var fieldName = tanantLdapMappingGrid.getColumnModel().getDataIndex(colIndex);
		if (fieldName == 'defaultAttributeValue' && (rowIndex == 0 || rowIndex == 4)) {
			return false; // UserName and Extension are not editable
		}
		return Ext.grid.ColumnModel.prototype.isCellEditable.call(this, colIndex, rowIndex);
	}

	var attributeValueMappingRecord = Ext.data.Record.create([
		{name: 'valueID', type: 'int'},
		{name: 'mappingID', type: 'int'},
		{name: 'vidyoValueName', type: 'string'},
		{name: 'ldapValueName', type: 'string'}
	]);

	var attributeValueMappingStore = new Ext.data.Store({
		proxy: new Ext.data.HttpProxy({
			url: '<c:url value="ldapvaluemapping.ajax"/>'
		}),
		reader: new Ext.data.XmlReader({
			totalRecords: 'results',
			record: 'row'
		}, attributeValueMappingRecord),
		remoteSort: true
	});

	var attributeValueMappingColumnModel = new Ext.grid.ColumnModel(
		[{
			id: 'valueID',
			header: 'ID',
			dataIndex: 'valueID',
			hidden: true
		},{
			id: 'mappingID',
			header: 'mappingID',
			dataIndex: 'mappingID',
			hidden: true
		},{
			header: '<spring:message code="portal.attribute.value"/>',
			dataIndex: 'vidyoValueName',
			sortable: false,
			menuDisabled: true
		},{
			header: '<spring:message code="ldap.attribute.value"/>',
			dataIndex: 'ldapValueName',
			sortable: false,
			menuDisabled: true,
			editor: new Ext.form.TextField({
				allowBlank: true,
				style: 'font-size: 12px; padding-top: 1px; padding-left: 4px;'
			})
		}
	]);

	var dublicateAttrBtn = new Ext.Button({
		text: '<spring:message code="dublicate"/>',
		iconCls: 'icon-add',
		disabled: true,
		handler : function(){
			var cell = attributeValueMappingGrid.getSelectionModel().getSelectedCell();
			var record = attributeValueMappingGrid.getStore().getAt(cell[0]); //rowIndex
			var p = new attributeValueMappingRecord({
				valueID: '0',
				mappingID: record.get('mappingID'),
				vidyoValueName: record.get('vidyoValueName'),
				ldapValueName: ''
			});
			attributeValueMappingGrid.stopEditing();
			attributeValueMappingStore.insert(cell[0]+1, p);
			attributeValueMappingGrid.startEditing(cell[0]+1, 1);
		}
	});

	var removeAttrBtn = new Ext.Button({
		text: '<spring:message code="remove"/>',
		disabled: true,
		iconCls: 'icon-remove',
		handler : function(){
			var cell = attributeValueMappingGrid.getSelectionModel().getSelectedCell();
			var record = attributeValueMappingGrid.getStore().getAt(cell[0]); //rowIndex
			if (record.get('valueID') != '0') {
				Ext.Ajax.request({
					url: '<c:url value="removeldapvaluemapping.ajax"/>',
					waitMsg: '<spring:message code="saving"/>',
					params: {
						valueID: record.get('valueID')
					},
					success: function (form, action) {
						attributeValueMappingGrid.stopEditing();
						attributeValueMappingStore.removeAt(cell[0]);
						attributeValueMappingGrid.startEditing(cell[0], 1);
					},
					failure:function(form, action) {
						var errorMsg = '<spring:message code="save.failed"/>';
						var errors = '';
						if (!action.result.success) {
							for (var i in action.result.errors) {
								errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
							}
						}
						msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
					}
				});
			} else {
				attributeValueMappingGrid.stopEditing();
				attributeValueMappingStore.removeAt(cell[0]);
				attributeValueMappingGrid.startEditing(cell[0], 1);
			}
		}
	});

	var attributeValueMappingGridSM = new Ext.grid.CellSelectionModel({
		listeners: {
			cellselect : {
				fn: function(selModel, rowIndex, colIndex) {
					var fieldName = attributeValueMappingGrid.getColumnModel().getDataIndex(colIndex);
					if (fieldName == 'vidyoValueName') {
						dublicateAttrBtn.enable();
						removeAttrBtn.enable();
					} else {
						dublicateAttrBtn.disable();
						removeAttrBtn.disable();
					}
				},
				scope: this
			}
		}
	});

	var attributeValueMappingGrid = new Ext.grid.EditorGridPanel({
		store: attributeValueMappingStore,
		cm: attributeValueMappingColumnModel,
		sm: attributeValueMappingGridSM,
		height: 300,
		border: true,
		frame: true,
		clicksToEdit: 2,
		autoScroll: true,
		viewConfig: {
			forceFit: true
		},
		buttonAlign: 'center',
		tbar: [
			dublicateAttrBtn,
			removeAttrBtn
		],
		buttons: [{
			text: '<spring:message code="save"/>',
			handler: function(){
				var rec = attributeValueMappingStore.getModifiedRecords();
				for (var r = 0; r < rec.length; r++){
					Ext.Ajax.request({
						url: '<c:url value="saveldapvaluemapping.ajax"/>',
						waitMsg: '<spring:message code="saving"/>',
						params: {
							valueID: rec[r].get('valueID'),
							mappingID: rec[r].get('mappingID'),
							vidyoValueName: rec[r].get('vidyoValueName'),
							ldapValueName: rec[r].get('ldapValueName')
						},
						success: function (form, action) {
						},
						failure:function(form, action) {
							var errorMsg = '<spring:message code="save.failed"/>';
							var errors = '';
							if (!action.result.success) {
								for (var i in action.result.errors) {
									errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
								}
							}
							msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
						}
					});
				}
				attributeValueMappingStore.commitChanges();
				attrmappingwin.hide();
			}
		},{
			text: '<spring:message code="cancel"/>',
			handler: function(){
				attrmappingwin.hide();
			}
		}]
	});

    /* SAML */

    var samlmappingwin;
    var samlAttrmappingwin;

    var samlAttributeValueMappingRecord = Ext.data.Record.create([
        {name: 'valueID', type: 'int'},
        {name: 'mappingID', type: 'int'},
        {name: 'vidyoValueName', type: 'string'},
        {name: 'idpValueName', type: 'string'}
    ]);


    var samlAttributeValueMappingStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<c:url value="samlvaluemapping.ajax"/>'
        }),
        reader: new Ext.data.XmlReader({
            totalRecords: 'results',
            record: 'row'
        }, samlAttributeValueMappingRecord),
        remoteSort: true
    });

    var samlAttributeValueMappingColumnModel = new Ext.grid.ColumnModel(
            [{
                id: 'valueID',
                header: 'ID',
                dataIndex: 'valueID',
                hidden: true
            },{
                id: 'mappingID',
                header: 'mappingID',
                dataIndex: 'mappingID',
                hidden: true
            },{
                header: '<spring:message code="portal.attribute.value"/>',
                dataIndex: 'vidyoValueName',
                sortable: false,
                menuDisabled: true
            },{
                header: '<spring:message code="idp.attribute.value"/>',
                dataIndex: 'idpValueName',
                sortable: false,
                menuDisabled: true,
                editor: new Ext.form.TextField({
                    allowBlank: true,
                    style: 'font-size: 12px; padding-top: 1px; padding-left: 4px;'
                })
            }
            ]);

    var samlAttributeValueMappingGridSM = new Ext.grid.CellSelectionModel({
        listeners: {
            cellselect : {
                fn: function(selModel, rowIndex, colIndex) {
                    var fieldName = samlAttributeValueMappingGrid.getColumnModel().getDataIndex(colIndex);
                    if (fieldName == 'vidyoValueName') {
                        samlDublicateAttrBtn.enable();
                        samlRemoveAttrBtn.enable();
                    } else {
                        samlDublicateAttrBtn.disable();
                        samlRemoveAttrBtn.disable();
                    }
                },
                scope: this
            }
        }
    });

    var samlDublicateAttrBtn = new Ext.Button({
        text: '<spring:message code="dublicate"/>',
        iconCls: 'icon-add',
        disabled: true,
        handler : function(){
            var cell = samlAttributeValueMappingGrid.getSelectionModel().getSelectedCell();
            var record = samlAttributeValueMappingGrid.getStore().getAt(cell[0]); //rowIndex
            var p = new samlAttributeValueMappingRecord({
                valueID: '0',
                mappingID: record.get('mappingID'),
                vidyoValueName: record.get('vidyoValueName'),
                idpValueName: ''
            });
            samlAttributeValueMappingGrid.stopEditing();
            samlAttributeValueMappingStore.insert(cell[0]+1, p);
            samlAttributeValueMappingGrid.startEditing(cell[0]+1, 1);
        }
    });

    var samlRemoveAttrBtn = new Ext.Button({
        text: '<spring:message code="remove"/>',
        disabled: true,
        iconCls: 'icon-remove',
        handler : function(){
            var cell = samlAttributeValueMappingGrid.getSelectionModel().getSelectedCell();
            var record = samlAttributeValueMappingGrid.getStore().getAt(cell[0]); //rowIndex
            if (record.get('valueID') != '0') {
                Ext.Ajax.request({
                    url: '<c:url value="removesamlvaluemapping.ajax"/>',
                    waitMsg: '<spring:message code="saving"/>',
                    params: {
                        valueID: record.get('valueID')
                    },
                    success: function (form, action) {
                        samlAttributeValueMappingGrid.stopEditing();
                        samlAttributeValueMappingStore.removeAt(cell[0]);
                        samlAttributeValueMappingGrid.startEditing(cell[0], 1);
                    },
                    failure:function(form, action) {
                        var errorMsg = '<spring:message code="save.failed"/>';
                        var errors = '';
                        if (!action.result.success) {
                            for (var i in action.result.errors) {
                                errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
                            }
                        }
                        msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
                    }
                });
            } else {
                samlAttributeValueMappingGrid.stopEditing();
                samlAttributeValueMappingStore.removeAt(cell[0]);
                samlAttributeValueMappingGrid.startEditing(cell[0], 1);
            }
        }
    });

    var samlAttributeValueMappingGrid = new Ext.grid.EditorGridPanel({
        store: samlAttributeValueMappingStore,
        cm: samlAttributeValueMappingColumnModel,
        sm: samlAttributeValueMappingGridSM,
        height: 300,
        border: true,
        frame: true,
        clicksToEdit: 2,
        autoScroll: true,
        viewConfig: {
            forceFit: true
        },
        buttonAlign: 'center',
        tbar: [
            samlDublicateAttrBtn,
            samlRemoveAttrBtn
        ],
        buttons: [{
            text: '<spring:message code="save"/>',
            handler: function(){
                var rec = samlAttributeValueMappingStore.getModifiedRecords();
                for (var r = 0; r < rec.length; r++){
                    Ext.Ajax.request({
                        url: '<c:url value="savesamlvaluemapping.ajax"/>',
                        waitMsg: '<spring:message code="saving"/>',
                        params: {
                            valueID: rec[r].get('valueID'),
                            mappingID: rec[r].get('mappingID'),
                            vidyoValueName: rec[r].get('vidyoValueName'),
                            idpValueName: rec[r].get('idpValueName')
                        },
                        success: function (form, action) {
                        },
                        failure:function(form, action) {
                            var errorMsg = '<spring:message code="save.failed"/>';
                            var errors = '';
                            if (!action.result.success) {
                                for (var i in action.result.errors) {
                                    errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
                                }
                            }
                            msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
                        }
                    });
                }
                samlAttributeValueMappingStore.commitChanges();
                samlAttrmappingwin.hide();
            }
        },{
            text: '<spring:message code="cancel"/>',
            handler: function(){
                samlAttrmappingwin.hide();
            }
        }]
    });

    var samlAttrValueMapping = new Ext.ux.grid.RowActions({
        header: '<spring:message code="value.mapping"/>',
        autoWidth: false,
        width: 70,
        actions: [{
            iconIndex: 'attrValueMapping',
            qtipIndex: 'qtipAttrValueMapping'
        }]
    });

    samlAttrValueMapping.on({
        action:function(grid, record, action, row, col) {
            switch(action){
                case 'icon-value-map':
                    samlAttrmappingwin = new Ext.Window({
                        title: '<spring:message code="attribute.values.mapping"/>',
                        closable: true,
                        closeAction: 'hide',
                        resizable: false,
                        width: 400,
                        autoHeight: true,
                        border: true,
                        frame: true,
                        modal: true,
                        items: [
                            samlAttributeValueMappingGrid
                        ],
                        listeners: {
                            show: function(el,type) {
                                samlAttributeValueMappingStore.load({params:{mappingID:record.data.mappingID}});
                                samlAttributeValueMappingGrid.getView().refresh();
                            },
                            hide: function(el,type) {
                                //Ext.getCmp('save').focus(true,100);
                            }
                        }
                    });
                    samlAttrmappingwin.show();
                    break;
            }
        }
    });

    var tanantSamlMappingRecord = Ext.data.Record.create([
        {name: 'mappingID', type: 'int'},
        {name: 'tenantID', type: 'int'},
        {name: 'vidyoAttributeName', type: 'string'},
        {name: 'vidyoAttributeDisplayName', type: 'string'},
        {name: 'idpAttributeName', type: 'string'},
        {name: 'defaultAttributeValue', type: 'string'},
        {name: 'attrValueMapping', type: 'string'},
        {name: 'qtipAttrValueMapping', type: 'string'}
    ]);

    var tanantSamlMappingStore = new Ext.data.Store({
        proxy: new Ext.data.HttpProxy({
            url: '<c:url value="samlmapping.ajax"/>'
        }),
        reader: new Ext.data.XmlReader({
            totalRecords: 'results',
            record: 'row'
        }, tanantSamlMappingRecord),
        remoteSort: true
    });

    tanantSamlMappingStore.load();

    var tanantSamlMappingColumnModel = new Ext.grid.ColumnModel({
        columns: [{
            id:'mappingID',
            header: 'ID',
            dataIndex: 'mappingID',
            hidden: true
        },{
            id:'tenantID',
            header: "tenant",
            dataIndex: 'tenantID',
            hidden: true
        },{
            header: 'attribute',
            dataIndex: 'vidyoAttributeName',
            hidden: true
        },{
            header: '<spring:message code="portal.attribute.name"/>',
            dataIndex: 'vidyoAttributeDisplayName',
            sortable: false,
            menuDisabled: true
        },{
            header: '<spring:message code="saml.idp.attribute.name"/>',
            dataIndex: 'idpAttributeName',
            sortable: false,
            menuDisabled: true,
            editable: true
        },{
            header: '<spring:message code="default.value"/>',
            dataIndex: 'defaultAttributeValue',
            sortable: false,
            menuDisabled: true,
            editable: true,
            renderer: renderReadOnlyColor
        },
            samlAttrValueMapping
        ],
        editors: {
            'text': new Ext.grid.GridEditor(
                    new Ext.form.TextField({
                        allowBlank: true,
                        style: 'font-size: 12px; padding-top: 1px; padding-left: 4px;'
                    })),
            'group': new Ext.grid.GridEditor(groupEditor),
            'role': new Ext.grid.GridEditor(samlRoleEditor),
            'proxy': new Ext.grid.GridEditor(proxyEditor),
            'locationtag': new Ext.grid.GridEditor(locationTagEditor)
        },
        getCellEditor: function(colIndex, rowIndex) {
            var field = this.getDataIndex(colIndex);
            if (field == 'idpAttributeName') {
                return this.editors['text'];
            }
            if (field == 'defaultAttributeValue') {
                var rec = tanantSamlMappingStore.getAt(rowIndex);
                if (rec.data['vidyoAttributeName'] == 'Group') {
                    return this.editors['group'];
                } if (rec.data['vidyoAttributeName'] == 'UserType') {
					return this.editors['role'];
				} else if (rec.data['vidyoAttributeName'] == 'Proxy') {
					return this.editors['proxy'];
				} else if (rec.data['vidyoAttributeName'] == 'LocationTag') {
					return this.editors['locationtag'];
				} else {
                    return this.editors['text'];
                }
            }
            return Ext.grid.ColumnModel.prototype.getCellEditor.call(this, colIndex, rowIndex);
        }
    });

    var tanantSamlMappingGrid = new Ext.grid.EditorGridPanel({
        store: tanantSamlMappingStore,
        cm: tanantSamlMappingColumnModel,
        plugins: [samlAttrValueMapping],
        height: 400,
        border: true,
        frame: true,
        autoScroll: true,
        clicksToEdit: 2,
        viewConfig: {
            forceFit: true
        },
        buttonAlign: 'center',
        buttons: [{
            text: '<spring:message code="save"/>',
            handler: function(){
                var rec = tanantSamlMappingStore.getModifiedRecords();
                for (var r = 0; r < rec.length; r++){
                    Ext.Ajax.request({
                        url: '<c:url value="savesamlmapping.ajax"/>',
                        waitMsg: '<spring:message code="saving"/>',
                        params: {
                            mappingID: rec[r].get('mappingID'),
                            idpAttributeName: rec[r].get('idpAttributeName'),
                            defaultAttributeValue: rec[r].get('defaultAttributeValue')
                        },
                        success: function (form, action) {

                        },
                        failure:function(form, action) {
                            var errorMsg = '<spring:message code="save.failed"/>';
                            var errors = '';
                            if (!action.result.success) {
                                for (var i in action.result.errors) {
                                    errors += action.result.errors[i].id + ' - ' + action.result.errors[i].msg + '<br>';
                                }
                            }
                            msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
                        }
                    });
                }
                tanantSamlMappingStore.commitChanges();
                samlmappingwin.hide();
            }
        },{
            text: '<spring:message code="cancel"/>',
            handler: function(){
                samlmappingwin.hide();
            }
        }]
    });

    tanantSamlMappingGrid.getColumnModel().isCellEditable = function(colIndex, rowIndex){
        var fieldName = tanantSamlMappingGrid.getColumnModel().getDataIndex(colIndex);
        var rec = tanantSamlMappingStore.getAt(rowIndex);
        var vidyoAttrName = rec.data['vidyoAttributeName'];
        if (fieldName == 'defaultAttributeValue' && (vidyoAttrName == 'UserName' || vidyoAttrName == 'Extension')) {
            return false; // UserName and Extension are not editable
        }
        return Ext.grid.ColumnModel.prototype.isCellEditable.call(this, colIndex, rowIndex);
    }
    
    function changeSaveButtonStatusForSaml() {
        var samlIdpMetadataVal = Ext.getCmp('samlIdpMetadata').getValue();
        var idpAttributeForUsernameVal = Ext.getCmp('idpAttributeForUsername').getValue();
        var samlProvisionTypeVal = Ext.getCmp('samlProvisionType').getValue();
        var samlSpEntityId = Ext.getCmp('samlSpEntityId').getValue();
        if(samlIdpMetadataVal == null || samlIdpMetadataVal=='' || samlIdpMetadataVal == undefined ||
                samlSpEntityId == null || samlSpEntityId=='' || samlSpEntityId == undefined ||
                (idpAttributeForUsernameVal == null || idpAttributeForUsernameVal == '' || idpAttributeForUsernameVal == undefined) && 
                samlProvisionTypeVal == 0) {
            Ext.getCmp('save').disable();
        } else {
            Ext.getCmp('save').enable();
        }
    }

	var authForm = new Ext.form.FormPanel({
		title: '<spring:message code="authentication"/>',
		id: 'authForm',
		tools: [{
			id:'help',
			qtip: '<spring:message code="help1"/>',
			handler: function(event, toolEl, panel){
				var url = '<c:out value="${model.guideLoc}"/>#Admin_SettingsSetUpAuthentication';
				var wname = 'VidyoPortalHelp';
				var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
				window.open(url, wname, wfeatures);
			}
		}],
		autoWidth: true,
		autoHeight: true,
		border: true,
		frame: true,
		labelAlign: 'side',
		labelWidth: 200,
		reader: authReader,
		errorReader: new Ext.data.XmlReader({
			success: '@success',
			record : 'field'
			},[
			'id', 'msg'
			]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[{
            xtype: 'combo',
            fieldLabel: 'Authentication Type',
            id: 'authType',
            value: 'NORMAL',
            name: 'authType',
            allowBlank: false,
            editable: false,
            triggerAction: 'all',
            typeAhead: false,
            mode: 'local',
            width:120,
            listWidth: 120,
          <c:if test="${model.enableAdminAPI}">
            store: [['NORMAL', 'Local'], ['LDAP', 'LDAP'], ['WS', 'Web Service'], ['SAML', 'SAML' ]],
          </c:if>
          <c:if test="${not model.enableAdminAPI}">
            store: [['NORMAL', 'Local'], ['LDAP', 'LDAP'], ['SAML', 'SAML']],
          </c:if>
            readOnly: true,
            listeners: {
                'select': function() {
                    if (this.getValue() == "NORMAL") {
                        Ext.getCmp('ws').hide();
                        Ext.getCmp('ldap').hide();
                        Ext.getCmp('authForSelector').hide();
                        Ext.getCmp('saml').hide();
                        Ext.getCmp('save').enable();
                        var items = Ext.getCmp('ldap').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('ws').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                               items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('saml').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        Ext.getCmp('wsflag').setValue('false');
                        Ext.getCmp('ldapflag').setValue('false');
                        Ext.getCmp('samlflag').setValue('false');
                    } else if (this.getValue() == "LDAP") {
                        Ext.getCmp('ws').hide();
                        Ext.getCmp('ldap').show();
                        Ext.getCmp('authForSelector').show();
                        Ext.getCmp('saml').hide();
                        Ext.getCmp('save').disable();

                        var items = Ext.getCmp('ldap').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].enable();
                            }
                        }
                        items = Ext.getCmp('ws').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('saml').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        Ext.getCmp('wsflag').setValue('false');
                        Ext.getCmp('ldapflag').setValue('true');
                        Ext.getCmp('samlflag').setValue('false');
                    } else if (this.getValue() == "WS") {
                        Ext.getCmp('ws').show();
                        Ext.getCmp('ldap').hide();
                        Ext.getCmp('authForSelector').show();
                        Ext.getCmp('save').disable();
                        Ext.getCmp('saml').hide();
                        var items = Ext.getCmp('ldap').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('ws').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].enable();
                            }
                        }
                        items = Ext.getCmp('saml').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        Ext.getCmp('wsflag').setValue('true');
                        Ext.getCmp('ldapflag').setValue('false');
                        Ext.getCmp('samlflag').setValue('false');
                    } else if (this.getValue() == 'SAML') {
                        Ext.getCmp('ws').hide();
                        Ext.getCmp('ldap').hide();
                        Ext.getCmp('authForSelector').hide();
                        Ext.getCmp('saml').show();
                        
                        var items = Ext.getCmp('ldap').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('ws').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('saml').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].enable();
                            }
                        }
                        Ext.getCmp('wsflag').setValue('false');
                        Ext.getCmp('ldapflag').setValue('false');
                        Ext.getCmp('samlflag').setValue('true');
                        if (this.getForm().getValues()['samlSecurityProfile'] == undefined) {
                            this.getForm().findField("samlSecurityProfile").items.items[0].setValue(false);
                            this.getForm().findField("samlSecurityProfile").items.items[1].setValue(true);
                        }
                        if (this.getForm().getValues()['samlSSLProfile'] == undefined) {
                            this.getForm().findField("samlSSLProfile").items.items[0].setValue(false);
                            this.getForm().findField("samlSSLProfile").items.items[1].setValue(true);
                        }
                        if (this.getForm().getValues()['samlSignMetadata'] == undefined) {
                            this.getForm().findField("samlSignMetadata").items.items[0].setValue(false);
                            this.getForm().findField("samlSignMetadata").items.items[1].setValue(true);
                        }
                        
                        Ext.getCmp('samlProvisionType').fireEvent('select', Ext.getCmp('samlProvisionType'));
                    }
                }
            }
        },{
			xtype: 'fieldset',
			id: 'ldap',
			hideBorders: true,
			title: '<spring:message code="authentication.using.ldap"/>',
			autoHeight: true,
			collapsed: false,
			tabIndex: 5,
			defaults: {
				labelStyle: 'font-weight:bold;text-align:right;'
			},
			listeners: {
				collapse: function(p) {
					p.items.each(function(i) {
						if (i instanceof Ext.form.Field) {
							i.disable();
						}
					}, this);
					Ext.getCmp('wsflag').setValue('true');
					Ext.getCmp('save').enable();
				},
				expand: function(p) {
					p.items.each(function(i) {
						if (i instanceof Ext.form.Field) {
							i.enable();
						}
					}, this);
					Ext.getCmp('ldapflag').setValue('true');
					Ext.getCmp('ws').collapse();
					Ext.getCmp('save').disable();
				}
			},
			items:[{
				xtype: 'hidden',
				name: 'ldapflag',
				id: 'ldapflag'
			},{
				xtype: 'hidden',
				name: 'ldapmappingflag',
				id: 'ldapmappingflag'
			},{
				xtype: 'textfield',
				name: 'ldapurl',
				id: 'ldapurl',
				fieldLabel: '<span class="red">*</span><spring:message code="url"/>',
				allowBlank: false,
				tabIndex: 6,
				width: 300
			},{
				xtype: 'textfield',
				name: 'ldapusername',
				id: 'ldapusername',
				fieldLabel: '<span class="red">*</span><spring:message code="bind.dn.or.username"/>',
				allowBlank: false,
				tabIndex: 7,
				width: 300
			},{
				xtype: 'textfield',
				name: 'ldappassword',
				id: 'ldappassword',
				fieldLabel: '<span class="red">*</span><spring:message code="bind.password"/>',
				inputType: 'password',
				allowBlank: false,
				tabIndex: 8,
				width: 300
			},{
				xtype: 'textfield',
				name: 'ldapbase',
				id: 'ldapbase',
				fieldLabel: '<spring:message code="search.base"/>',
				allowBlank: true,
				tabIndex: 9,
				width: 300
			},{
				xtype: 'textfield',
				name: 'ldapfilter',
				id: 'ldapfilter',
				fieldLabel: '<span class="red">*</span><spring:message code="filter.template"/>',
				helpText: '<spring:message code="use.lt.gt.for.username.substitution"/>',
				allowBlank: false,
				tabIndex: 10,
				width: 300
			},{
				xtype: 'radiogroup',
				id: 'scopegroup',
				fieldLabel: '<span class="red">*</span><spring:message code="scope"/>',
				labelStyle: 'font-weight:bold;text-align:right;padding-top:7px;',
				columns: [100, 100, 100],
				vertical: false,
				name: 'ldapscope',
				items: [
					{tabIndex: 11, name: 'ldapscope', inputValue: 0, boxLabel: '<spring:message code="object"/>'},
					{tabIndex: 12, name: 'ldapscope', inputValue: 1, boxLabel: '<spring:message code="one.level"/>'},
					{tabIndex: 13, name: 'ldapscope', inputValue: 2, boxLabel: '<spring:message code="subtree"/>'}
				]
			},{
                xtype: 'button',
                style: 'padding-left: 205px; padding-bottom: 7px;',
                text: '<spring:message code="connection.test"/>',
                tooltip: '<spring:message code="check.connection.to.ldap.server.and.user.authentication.before.saving"/>',
                tabIndex: 17,
                handler: function(){
                    authForm.getForm().submit({
                        url: '<c:url value="testldapauthentication.ajax"/>',
                        waitMsg: '<spring:message code="connection.testing"/>',
                        success: function (form, action) {
                            if (!ldapinputwin) {
                                ldapinputwin = new Ext.Window({
                                    title: '<spring:message code="test.ldap.user.authentication"/>',
                                    closable: true,
                                    closeAction: 'hide',
                                    resizable: true,
                                    width: 300,
                                    autoHeight: true,
                                    border: true,
                                    frame: true,
                                    modal: true,
                                    items: [
                                        ldapTestForm
                                    ],
                                    listeners: {
                                        show: function(el,type) {
                                            Ext.getCmp('ldaptestusername').focus(true,100);
                                        },
                                        hide: function(el,type) {
                                            Ext.getCmp('save').focus(true,100);
                                        }
                                    }
                                });
                            }
                            ldapinputwin.show();
                        },
                        failure:function(form, action) {
                            var errorMsg = '';
                            var errors = '';
                            if ((action.result != null) && (!action.result.success)) {
                                for(var i=0; i < action.result.errors.length; i++){
                                    errors += action.result.errors[i].msg + '<br>';
                                }
                            }
                            if(errors != ''){
                                msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){Ext.getCmp('save').disable();});
                            }
                            else{
                                msg('<spring:message code="message"/>', '<spring:message code="timeout"/>', function(){Ext.getCmp('save').disable();});
                            }
                        }
                    });
                }
            },{
				xtype: 'fieldset',
				id: 'ldapmapping',
				title: '<spring:message code="ldap.attributes.mapping"/>',
				autoHeight: true,
				checkboxToggle: true,
				collapsed: true,
                clayouts: [[.5, .5]] , // config column layout with 2 cols
                plugins: [Ext.ux.SimpleColumn],
				listeners: {
					collapse: function(p) {
						p.items.each(function(i) {
							if (i instanceof Ext.form.Field) {
								i.disable();
							}
						}, this);
						Ext.getCmp('ldapmappingflag').setValue('false');
					},
					expand: function(p) {
						p.items.each(function(i) {
							if (i instanceof Ext.form.Field) {
								i.enable();
							}
						}, this);
						Ext.getCmp('ldapmappingflag').setValue('true');
					}
				},
                items: [{
                    xtype: 'button',
                    text: '<spring:message code="edit.attributes.mapping"/>',
                    tooltip: '<spring:message code="click.to.open.ldap.attributes.mapping.dialog.box"/>',
                    id: 'ldapmappingbtn',
                    name: 'ldapmappingbtn',
                    tabIndex: 16,
                    handler: function(){
                        authForm.getForm().submit({
                            url: '<c:url value="testldapauthentication.ajax"/>',
                            waitMsg: '<spring:message code="connection.testing"/>',
                            success: function (form, action) {
                                if (!ldapmappingwin) {
                                    ldapmappingwin = new Ext.Window({
                                        title: '<spring:message code="ldap.attributes.mapping"/>',
                                        closable: true,
                                        closeAction: 'hide',
                                        resizable: false,
                                        width: 600,
                                        autoHeight: true,
                                        border: true,
                                        frame: true,
                                        modal: true,
                                        items: [
                                            tanantLdapMappingGrid
                                        ],
                                        listeners: {
                                            show: function(el,type) {
                                                //Ext.getCmp('ldaptestusername').focus(true,100);
                                            	tanantLdapMappingStore.load();

                                            },
                                            hide: function(el,type) {
                                                //Ext.getCmp('save').focus(true,100);
                                            }
                                        }
                                    });
                                }
                                ldapmappingwin.show();
                            },
                            failure:function(form, action) {
                                var errorMsg = '';
                                var errors = '';
                                if ((action.result != null) && (!action.result.success)) {
                                    for(var i=0; i < action.result.errors.length; i++){
                                        errors += action.result.errors[i].msg + '<br>';
                                    }
                                }
                                if(errors != ''){
                                    msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){Ext.getCmp('save').disable();});
                                }
                                else{
                                    msg('<spring:message code="message"/>', '<spring:message code="timeout"/>', function(){Ext.getCmp('save').disable();});
                                }
                            }
                        });
                    }
                },{
                    xtype: 'button',
                    style: 'padding-left: 10px;',
                    text: '<spring:message code="test.attributes.mapping"/>',
                    tooltip: '<spring:message code="click.to.test.ldap.attributes.mapping.for.user"/>',
                    id: 'ldaptestmappingbtn',
                    name: 'ldaptestmappingbtn',
                    tabIndex: 16,
                    handler: function(){
                        authForm.getForm().submit({
                            url: '<c:url value="testldapauthentication.ajax"/>',
                            waitMsg: '<spring:message code="connection.testing"/>',
                            success: function (form, action) {
                                if (!ldaptestmappingwin) {
                                    ldaptestmappingwin = new Ext.Window({
                                        title: '<spring:message code="test.ldap.attributes.mapping"/>',
                                        closable: true,
                                        closeAction: 'hide',
                                        resizable: false,
                                        width: 300,
                                        autoHeight: true,
                                        border: true,
                                        frame: true,
                                        modal: true,
                                        items: [
                                            ldapUserMappingTestForm
                                        ],
                                        listeners: {
                                            show: function(el,type) {
                                                Ext.getCmp('ldaptestusernamemapping').focus(true,100);

                                            },
                                            hide: function(el,type) {
                                                Ext.getCmp('save').focus(true,100);
                                            }
                                        }
                                    });
                                }
                                ldaptestmappingwin.show();
                            },
                            failure:function(form, action) {
                                var errorMsg = '';
                                var errors = '';
                                if ((action.result != null) && (!action.result.success)) {
                                    for(var i=0; i < action.result.errors.length; i++){
                                        errors += action.result.errors[i].msg + '<br>';
                                    }
                                }
                                if(errors != ''){
                                    msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){Ext.getCmp('save').disable();});
                                }
                                else{
                                    msg('<spring:message code="message"/>', '<spring:message code="timeout"/>', function(){Ext.getCmp('save').disable();});
                                }
                            }
                        });
                    }
                }]
            }]
		},{
			xtype: 'fieldset',
			id: 'ws',
			hideBorders: true,
			title: '<spring:message code="authentication.using.web.service"/>',
			autoHeight: true,
			tabIndex: 1,
			defaults: {
				labelStyle: 'font-weight:bold;text-align:right;'
			},
			listeners: {
				collapse: function(p) {
					p.items.each(function(i) {
						if (i instanceof Ext.form.Field) {
							i.disable();
						}
					}, this);
					Ext.getCmp('ldapflag').setValue('true');
					Ext.getCmp('save').enable();
				},
				expand: function(p) {
					p.items.each(function(i) {
						if (i instanceof Ext.form.Field) {
							i.enable();
						}
					}, this);
					Ext.getCmp('wsflag').setValue('true');
					Ext.getCmp('ldap').collapse();
					Ext.getCmp('save').disable();
				}
			},
			items:[{
				xtype: 'hidden',
				name: 'wsflag',
				id: 'wsflag'
			},{
				xtype: 'hidden',
				name: 'enableAdminAPI',
				id: 'enableAdminAPI'
			},{
				xtype: 'textfield',
				name: 'wsurl',
				id: 'wsurl',
				fieldLabel: '<span class="red">*</span><spring:message code="url"/>',
				allowBlank: false,
				tabIndex: 2,
				width: 300
			},{
				xtype: 'textfield',
				name: 'wsusername',
				id: 'wsusername',
				fieldLabel: '<span class="red">*</span><spring:message code="user.name"/>',
				allowBlank: false,
				tabIndex: 3,
				width: 300
			},{
				xtype: 'textfield',
				name: 'wspassword',
				id: 'wspassword',
				fieldLabel: '<span class="red">*</span><spring:message code="password"/>',
				allowBlank: false,
				inputType: 'password',
				tabIndex: 4,
				width: 300
			},{
				xtype: 'button',
                style: 'padding-left: 205px; padding-bottom: 7px;',
                text: '<spring:message code="connection.test"/>',
				tooltip: '<spring:message code="check.connection.to.ws.server.and.user.authentication.before.saving"/>',
				tabIndex: 14,
				handler: function(){
					if (!wsinputwin) {
						wsinputwin = new Ext.Window({
							title: '<spring:message code="test.ws.user.authentication"/>',
							closable: true,
							closeAction: 'hide',
							resizable: false,
							width: 300,
							autoHeight: true,
							border: true,
							frame: true,
							modal: true,
							items: [
								wsTestForm
							],
							listeners: {
								show: function(el,type) {
									Ext.getCmp('wstestusername').focus(true,100);
								},
								hide: function(el,type) {
									Ext.getCmp('save').focus(true,100);
								}
							}
						});
					}
					wsinputwin.show();
				}
			}]
		},{
            xtype: 'fieldset',
            id: 'saml',
            hideBorders: true,
            title: 'SAML',
            autoHeight: true,
            tabIndex: 1,
            defaults: {
                labelStyle: 'font-weight:bold;text-align:right;'
            },
            items:[{
                xtype: 'hidden',
                name: 'samlflag',
                id: 'samlflag'
            },{
				xtype: 'hidden',
				name: 'samlmappingflag',
				id: 'samlmappingflag'
			},{
                xtype: 'textarea',
                name: 'samlIdpMetadata',
                id: 'samlIdpMetadata',
                fieldLabel: '<spring:message code="identity.provider.idp.metadata.xml"/>',
                allowBlank: false,
                tabIndex: 2,
                width: 400,
                height: 150,
                enableKeyEvents: true,
                listeners: {
                    'keyup': function() {
                        changeSaveButtonStatusForSaml();
                    },
                    'change': function() {
                        changeSaveButtonStatusForSaml();
                    }
                }
            },{
                xtype: 'textfield',
                name: 'samlSpEntityId',
                id: 'samlSpEntityId',
                tabIndex: 3,
                fieldLabel: '<spring:message code="entity.id"/>',
                allowBlank: false,
                enableKeyEvents: true,
                listeners: {
                    'keyup': function() {
                        changeSaveButtonStatusForSaml();
                    },
                    'change': function() {
                        changeSaveButtonStatusForSaml();
                    }
                }
    		},{
                xtype: 'radiogroup',
                fieldLabel: '<spring:message code="security.profile"/>',
                columns: [100, 100],
                vertical: true,
                name: 'samlSecurityProfile',
                tabIndex: 4,
                items: [
                    {boxLabel: 'MetaIOP', name: 'samlSecurityProfile', inputValue: "METAIOP"},
                    {boxLabel: 'PKIX', name: 'samlSecurityProfile', inputValue: "PKIX"}
                ]
            },{
                xtype: 'radiogroup',
                fieldLabel: '<spring:message code="ssl.tls.profile"/>',
                columns: [100, 100],
                vertical: true,
                name: 'samlSSLProfile',
                tabIndex: 5,
                items: [
                    {boxLabel: 'MetaIOP', name: 'samlSSLProfile', inputValue: "METAIOP"},
                    {boxLabel: 'PKIX', name: 'samlSSLProfile', inputValue: "PKIX"}
                ]
            },{
                xtype: 'radiogroup',
                fieldLabel: '<spring:message code="sign.metadata"/>',
                columns: [100, 100],
                vertical: true,
                name: 'samlSignMetadata',
                tabIndex: 6,
                items: [
                    {boxLabel: '<spring:message code="yes"/>', name: 'samlSignMetadata', inputValue: "YES"},
                    {boxLabel: '<spring:message code="no"/>', name: 'samlSignMetadata', inputValue: "NO"}
                ]
            },{
                xtype: 'combo',
                fieldLabel: '<spring:message code="saml.provision.type"/>',
                id: 'samlProvisionType',
                value: 1,
                name: 'samlProvisionType',
                tabIndex: 7,
                allowBlank: false,
                editable: false,
                triggerAction: 'all',
                typeAhead: false,
                mode: 'local',
                width:120,
                listWidth: 120,
                store: [['0', '<spring:message code="local"/>'], ['1', 'SAML' ]],
                readOnly: true,
                listeners: {
                    'select': function() {
                        if (this.getValue() == 0) {
                            Ext.getCmp('samlmappingbtn').hide();
                            
                            Ext.getCmp('idpAttributeForUsername').show();
                            Ext.getCmp('idpAttributeForUsername').enable();
                            Ext.getCmp('idpAttributeForUsername').getEl().up('.x-form-item').setDisplayed(true);
                            
                            var samlIdpMetadataVal = Ext.getCmp('samlIdpMetadata').getValue();
                            var idpAttributeForUsernameVal = Ext.getCmp('idpAttributeForUsername').getValue();
                            var samlSpEntityId = Ext.getCmp('samlSpEntityId').getValue();
                            if(samlIdpMetadataVal == null || samlIdpMetadataVal=='' || samlIdpMetadataVal == undefined ||
                            		samlSpEntityId == null || samlSpEntityId=='' || samlSpEntityId == undefined ||
                            		idpAttributeForUsernameVal == null || idpAttributeForUsernameVal == '' || idpAttributeForUsernameVal == undefined) {
                                Ext.getCmp('save').disable();
                            } else {
                                Ext.getCmp('save').enable();
                            }
                        } else if (this.getValue() == 1) {
                            Ext.getCmp('samlmappingbtn').show();
                            
                            Ext.getCmp('idpAttributeForUsername').hide();
                            Ext.getCmp('idpAttributeForUsername').disable();
                            Ext.getCmp('idpAttributeForUsername').getEl().up('.x-form-item').setDisplayed(false);
                            
                            var samlIdpMetadataVal = Ext.getCmp('samlIdpMetadata').getValue();
                            var idpAttributeForUsernameVal = Ext.getCmp('idpAttributeForUsername').getValue();
                            var samlSpEntityId = Ext.getCmp('samlSpEntityId').getValue();
                            if(samlIdpMetadataVal == null || samlIdpMetadataVal=='' || samlIdpMetadataVal == undefined ||
                            		samlSpEntityId == null || samlSpEntityId=='' || samlSpEntityId == undefined) {
                                Ext.getCmp('save').disable();
                            } else {
                                Ext.getCmp('save').enable();
                            }
                        }
                        Ext.getCmp('samlmappingflag').setValue(this.getValue());
                    }
                }
            },{
                xtype: 'button',
                text: '<spring:message code="edit.idp.attribute.mapping"/>',
                tooltip: '<spring:message code="click.to.open.idp.attribute.mappings"/>',
                style: 'padding-left: 205px; padding-bottom: 7px;',
                id: 'samlmappingbtn',
                name: 'samlmappingbtn',
                tabIndex: 8,
                handler: function(){
                            if (!samlmappingwin) {
                                samlmappingwin = new Ext.Window({
                                    title: '<spring:message code="saml.idp.attribute.mapping"/>',
                                    closable: true,
                                    closeAction: 'hide',
                                    resizable: false,
                                    width: 600,
                                    autoHeight: true,
                                    border: true,
                                    frame: true,
                                    modal: true,
                                    items: [
                                        tanantSamlMappingGrid
                                    ]
                                });
                            }
                            tanantSamlMappingStore.load();
                            samlmappingwin.show();
                        }

            },{
                xtype: 'textfield',
                name: 'idpAttributeForUsername',
                id: 'idpAttributeForUsername',
                tabIndex: 9,
                fieldLabel: '<spring:message code="idp.attribute.for.username"/>',
                enableKeyEvents: true,
                listeners: {
                    'keyup': function() {
                        var samlIdpMetadataVal = Ext.getCmp('samlIdpMetadata').getValue();
                        var idpAttributeForUsernameVal = this.getValue();
                        var samlSpEntityId = Ext.getCmp('samlSpEntityId').getValue();
                        if(samlIdpMetadataVal == null || samlIdpMetadataVal=='' || samlIdpMetadataVal == undefined ||
                                samlSpEntityId == null || samlSpEntityId=='' || samlSpEntityId == undefined ||
                                (idpAttributeForUsernameVal == null || idpAttributeForUsernameVal == '' || idpAttributeForUsernameVal == undefined)) {
                            Ext.getCmp('save').disable();
                        } else {
                            Ext.getCmp('save').enable();
                        }
                    }
                }
    		},{
                xtype: 'button',
                id: 'samlSPMetadata',
                style: 'padding-left: 205px; padding-bottom: 7px;',
                fieldLabel: '<spring:message code="service.provider.sp.metadata.xml"/>',
                text: '<spring:message code="view.service.provider.sp.metadata.xml"/>',
                handler: function() {
                    if(!samlViewerWin){
                        samlViewerWin = new Ext.Window({
                            applyTo     : 'saml-win',
                            layout      : 'fit',
                            width       : 500,
                            height      : 400,
                            closeAction :'hide',
                            plain       : true,
                            autoScroll  : true,
                            items       : new Ext.Panel({
                                applyTo        : 'saml-panel',
                                deferredRender : false,
                                border         : false
                            }),
                            buttons: [{
                                text     : '<spring:message code="close"/>',
                                handler  : function(){
                                    samlViewerWin.hide();
                                }
                            }]
                        });
                    }
                        Ext.Ajax.request({
                            url: '<c:url value="authentication_saml_view.ajax"/>&samlSecurityProfile=' + authForm.getForm().getValues()['samlSecurityProfile'] +
                                    "&samlSSLProfile=" + authForm.getForm().getValues()['samlSSLProfile']  + "&samlSignMetadata=" + authForm.getForm().getValues()['samlSignMetadata'] +
                                    "&samlmappingflag=" + authForm.getForm().getValues()['samlmappingflag'] + "&idpAttributeForUsername=" + authForm.getForm().getValues()['idpAttributeForUsername'] +
                                    "&samlSpEntityId=" + authForm.getForm().getValues()['samlSpEntityId'],
                            success: function(d) { Ext.get("saml-spmetadata").update(d.responseText);
                                 },
                            failure: function() { Ext.get("saml-spmetadata").update('<b style="color: red;"><spring:message code="error.generating.service.provider.metadata"/></b>');
                                }
                        });
                        samlViewerWin.show();

                },
                tabIndex: 9
            }]
        },
            {
			xtype:'fieldset',
			title: '<spring:message code="use.selected.authentication.for.selected.user.types"/>',
            id: 'authForSelector',
			autoHeight: true,
			defaults: {
				hideLabel: true
			},
			tabIndex: 15,
			items: [{
				xtype: 'itemselector',
				width: 640,
				msWidth: 310,
				msHeight: 210,
				border: true,
				name: 'authFor',
				drawUpIcon: false,
				drawDownIcon: false,
				drawLeftIcon: true,
				drawRightIcon: true,
				drawTopIcon: false,
				drawBotIcon: false,
				dataFields: ['roleID', 'roleName'],
				valueField: 'roleID',
				displayField: 'roleName',
				fromLegend: '<spring:message code="available.types"/>',
				fromStore: fromStore,
				toLegend: '<spring:message code="selected.types"/>',
				toStore: toStore
			}]
		}],
		buttons: [{
			text: '<spring:message code="save"/>',
			id: 'save',
			handler: function(){
				authForm.getForm().submit({
					url: '<c:url value="saveauthentication.ajax"/>',
					waitMsg: '<spring:message code="saving"/>',
					success: function (form, action) {
                        msg('<spring:message code="message"/>', '<spring:message code="saved"/>', function(){ window.location = '<c:url value="authentication.html"/>' });
					},
					failure:function(form, action) {
						var errorMsg = '<spring:message code="save.failed"/>';
						var errors = '';
						if ((action.result != null) && (!action.result.success)) {
							for(var i=0; i < action.result.errors.length; i++){
								errors += action.result.errors[i].msg + '<br>';
							}
						}
						msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
					}
				});
			},
			tabIndex: 16
		},{
			text: '<spring:message code="cancel"/>',
			handler: function(){
				window.location = '<c:url value="settings.html"/>';
			},
			tabIndex: 17
		}]
	});

	var ldapTestForm = new Ext.form.FormPanel({
		autoWidth: true,
		bodyStyle: 'padding: 10px;',
		border: true,
		frame: true,
		labelAlign: 'side',
		labelWidth: 100,
		errorReader: new Ext.data.XmlReader({
			record : 'field',
			success: '@success'
			},[
			'id', 'msg'
			]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[{
			xtype: 'textfield',
			name: 'username',
			id: 'ldaptestusername',
			fieldLabel: '<spring:message code="user.name"/>',
			allowBlank: false,
			tabIndex: 18,
			listeners: {
				keyup: function(el,type) {
					if (el.getValue() != '' && Ext.getCmp('ldaptestuserpassword').getValue() != '') {
						Ext.getCmp('ldaptestuser').enable();
					} else {
						Ext.getCmp('ldaptestuser').disable();
					}
				}
			}
		},{
			xtype: 'textfield',
			name: 'password',
			id: 'ldaptestuserpassword',
			inputType: 'password',
			fieldLabel: '<spring:message code="password"/>',
			allowBlank: false,
			tabIndex: 19,
			listeners: {
				keyup: function(el,type) {
					if (el.getValue() != '' && Ext.getCmp('ldaptestusername').getValue() != '') {
						Ext.getCmp('ldaptestuser').enable();
					} else {
						Ext.getCmp('ldaptestuser').disable();
					}
				}
			}
		}],
		buttons: [{
			text: '<spring:message code="submit"/>',
			id: 'ldaptestuser',
			disabled: true,
			tabIndex: 20,
			handler: function(){
				ldapTestForm.getForm().submit({
					url: '<c:url value="testldapuserauthentication.ajax"/>',
					params: authForm.getForm().getValues(),
					success: function (form, action) {
						msg('<spring:message code="message"/>', "<spring:message code="test.passed"/>", function(){
							Ext.getCmp('save').enable();
							ldapinputwin.hide();
						});
					},
					failure: function(form, action) {
						msg('<spring:message code="message"/>', "<spring:message code="test.failer"/>", function(){
							Ext.getCmp('save').disable();
						});
					}
				});
			}
		}]
	});

	var wsTestForm = new Ext.form.FormPanel({
		autoWidth: true,
		bodyStyle: 'padding: 10px;',
		border: true,
		frame: true,
		labelAlign: 'side',
		labelWidth: 100,
		errorReader: new Ext.data.XmlReader({
			record : 'field',
			success: '@success'
			},[
				'id', 'msg'
			]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[{
			xtype: 'textfield',
			name: 'username',
			id: 'wstestusername',
			fieldLabel: '<spring:message code="user.name"/>',
			allowBlank: false,
			tabIndex: 21,
			listeners: {
				keyup: function(el,type) {
					if (el.getValue() != '' && Ext.getCmp('wstestuserpassword').getValue() != '') {
						Ext.getCmp('wstestuser').enable();
					} else {
						Ext.getCmp('wstestuser').disable();
					}
				}
			}
		},{
			xtype: 'textfield',
			name: 'password',
			id: 'wstestuserpassword',
			inputType: 'password',
			fieldLabel: '<spring:message code="password"/>',
			allowBlank: false,
			tabIndex: 22,
			listeners: {
				keyup: function(el,type) {
					if (el.getValue() != '' && Ext.getCmp('wstestusername').getValue() != '') {
						Ext.getCmp('wstestuser').enable();
					} else {
						Ext.getCmp('wstestuser').disable();
					}
				}
			}
		}],
		buttons: [{
			text: '<spring:message code="submit"/>',
			id: 'wstestuser',
			disabled: true,
			tabIndex: 23,
			handler: function(){
				wsTestForm.getForm().submit({
					url: '<c:url value="testwsuserauthentication.ajax"/>',
					params: authForm.getForm().getValues(),
					success: function (form, action) {
						msg('<spring:message code="message"/>', "<spring:message code="test.passed"/>", function(){
							Ext.getCmp('save').enable();
							wsinputwin.hide();
						});
					},
					failure: function(form, action) {
						msg('<spring:message code="message"/>', "<spring:message code="test.failer"/>", function(){
							Ext.getCmp('save').disable();
						});
					}
				});
			}
		}]
	});

	var ldapUserMappingTestForm = new Ext.form.FormPanel({
		autoWidth: true,
		bodyStyle: 'padding: 10px;',
		border: true,
		frame: true,
		labelAlign: 'side',
		labelWidth: 100,
		errorReader: new Ext.data.XmlReader({
				record : 'field',
				success: '@success'
			},[
				'id', 'msg'
			]
		),
		defaults: {
			labelStyle: 'font-weight:bold;text-align:right;'
		},
		items:[{
			xtype: 'textfield',
			name: 'username',
			id: 'ldaptestusernamemapping',
			fieldLabel: '<spring:message code="user.name"/>',
			allowBlank: false,
			tabIndex: 18,
			listeners: {
				keyup: function(el,type) {
					if (el.getValue() != '' && Ext.getCmp('ldaptestuserpasswordmapping').getValue() != '') {
						Ext.getCmp('ldaptestusermapping').enable();
					} else {
						Ext.getCmp('ldaptestusermapping').disable();
					}
				}
			}
		},{
			xtype: 'textfield',
			name: 'password',
			id: 'ldaptestuserpasswordmapping',
			inputType: 'password',
			fieldLabel: '<spring:message code="password"/>',
			allowBlank: false,
			tabIndex: 19,
			listeners: {
				keyup: function(el,type) {
					if (el.getValue() != '' && Ext.getCmp('ldaptestusernamemapping').getValue() != '') {
						Ext.getCmp('ldaptestusermapping').enable();
					} else {
						Ext.getCmp('ldaptestusermapping').disable();
					}
				}
			}
		}],
		buttons: [{
			text: '<spring:message code="submit"/>',
			id: 'ldaptestusermapping',
			disabled: true,
			tabIndex: 20,
			handler: function(){
				ldapUserMappingTestForm.getForm().submit({
					url: '<c:url value="testldapusermapping.ajax"/>',
					params: authForm.getForm().getValues(),
					success: function (form, action) {
                        var results = '';
                        if ((action.result != null) && (action.result.success)) {
                            results += '<table cellspacing="0" cellpadding="0" border="0"><tbody>';
                            for(var i=0; i < action.result.errors.length; i++){
                                results += '<tr><td align="right">'+action.result.errors[i].id + '</td><td>&nbsp;:&nbsp;</td><td><b>' + action.result.errors[i].msg + '</b></td></tr>';
                            }
                            results += '</tbody></table>';
                        }
                        Ext.Msg.show({
                            title: 'LDAP Attributes Mapping results',
                            msg: results,
                            minWidth: 400,
                            modal: true,
                            icon: Ext.Msg.INFO,
                            buttons: Ext.Msg.OK,
                            fn: function(){
                                Ext.getCmp('save').enable();
                                ldaptestmappingwin.hide();
                            }
                        });
					},
					failure: function(form, action) {
						msg('<spring:message code="message"/>', "<spring:message code="test.failer"/>", function(){
							Ext.getCmp('save').disable();
						});
					}
				});
			}
		}]
	});

	authForm.getForm().load({url: '<c:url value="authentication.ajax"/>', waitMsg: '<spring:message code="loading"/>'});
	authForm.on({
		actioncomplete: function(form, action){
			if(action.type == 'load'){
				var wsflag = form.findField('wsflag');
				var ldapflag = form.findField('ldapflag');
				var ldapmappingflag = form.findField('ldapmappingflag');
                var samlflag = form.findField('samlflag');
                var samlmappingflag = form.findField('samlmappingflag');
                if(samlmappingflag == 'undefined') {
                    samlmappingflag = 1;
                }
				if (wsflag.getValue() == 'false' && ldapflag.getValue() == 'false' && samlflag.getValue() == 'false') {
                    Ext.getCmp('authType').setValue("NORMAL");
					Ext.getCmp('ws').hide();
					Ext.getCmp('ldap').hide();
                    Ext.getCmp('authForSelector').hide();
                    Ext.getCmp('saml').hide();
				} else {
					if (wsflag.getValue() == 'true') {
                        Ext.getCmp('authType').setValue("WS");
						Ext.getCmp('ws').show();
						Ext.getCmp('ldap').hide();
                        Ext.getCmp('saml').hide();
                        var items = Ext.getCmp('ldap').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('saml').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        Ext.getCmp('save').disable();
					}
					if (ldapflag.getValue() == 'true') {
                        Ext.getCmp('authType').setValue("LDAP");
						Ext.getCmp('ldap').show();
						Ext.getCmp('ws').hide();
                        Ext.getCmp('saml').hide();
						if (ldapmappingflag.getValue() == 'true') {
							Ext.getCmp('ldapmapping').expand();
						} else {
							Ext.getCmp('ldapmapping').collapse();
						}
                        var items = Ext.getCmp('ldap').items;
                        items = Ext.getCmp('ws').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('saml').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        Ext.getCmp('save').disable();
					}
                    if (samlflag.getValue() == 'true') {
                        Ext.getCmp('authType').setValue("SAML");
                        Ext.getCmp('ws').hide();
                        Ext.getCmp('ldap').hide();
                        Ext.getCmp('saml').show();
                        Ext.getCmp('authForSelector').hide();
                        
                        Ext.getCmp('samlProvisionType').setValue(samlmappingflag.getValue());
                        Ext.getCmp('samlProvisionType').fireEvent('select', Ext.getCmp('samlProvisionType'));
                        
                        var items = Ext.getCmp('ldap').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                        items = Ext.getCmp('ws').items;
                        for (i=0; i<items.getCount(); ++i) {
                            if (items.items[i] instanceof Ext.form.Field) {
                                items.items[i].disable();
                            }
                        }
                    }
				}
				var enableAdminAPI = form.findField('enableAdminAPI');
				if(enableAdminAPI.getValue() == 'false') {
					Ext.getCmp('ws').hide();
				}
			}
		}
	});

	new Ext.Panel({
		renderTo: 'content-panel',
		border: false,
		items:[{
			layout: 'column',
			border: false,
			items: [{
				columnWidth:.25,
				baseCls:'x-plain',
				bodyStyle:'padding:5px 5px 5px 5px',
				id: 'local',
				items:[
					localmenu
				]
			},{
				columnWidth:.75,
				baseCls:'x-plain',
				bodyStyle:'padding:5px 5px 5px 5px',
				items:[
					authForm
				]
			}]
		}]
	});

});

</script>