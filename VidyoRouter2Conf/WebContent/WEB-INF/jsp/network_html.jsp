<%@ include file="header_html.jsp" %>

	<div id="maincontent">

		<div id="content-panel">&nbsp;</div>

    </div>
<script type='text/javascript' src='<c:url value="/js/VTypes.js"/>'></script>
<script type="text/javascript">

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
		autoLoad: {url: '<c:url value="menu_content.html?settings=1&network=1"/>'}
	});

	var network = Ext.data.Record.create([
		{name: 'ipAddress', type: 'string'},
		{name: 'subnetMask', type: 'string'},
		{name: 'gateway', type: 'string'},
		{name: 'dns1', type: 'string'},
		{name: 'dns2', type: 'string'},
		{name: 'MACAddress', type: 'string'},
		{name: 'SystemID', type: 'string'},
		{name: 'ipAddress2', type: 'string'},
		{name: 'subnetMask2', type: 'string'},
		{name: 'fqdn1', type: 'string'},
		{name: 'fqdn2', type: 'string'}
	]);

	var networkReader = new Ext.data.XmlReader({
		totalRecords: 'results',
		record: 'row'
	}, network);

	var networkForm = new Ext.form.FormPanel({
		title: '<spring:message code="platform.network.settings"/>',
		autoWidth: true,
		autoHeight: true,
		border: true,
		frame: true,
		labelAlign: 'side',
		labelWidth: 200,
		reader: networkReader,
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
		items: [{
			xtype: 'textfield',
			name: 'ipAddress',
			fieldLabel: '<spring:message code="ip.address"/> (eth0)',
			tabIndex: 1,
			width: 300,
			readOnly: true
		},{
			xtype: 'textfield',
			name: 'fqdn1',
			fieldLabel: 'FQDN (eth0)',
			tabIndex: 2,
			width: 300,
			readOnly: true
		},{
			xtype: 'textfield',
			name: 'subnetMask',
			fieldLabel: '<spring:message code="subnet.mask"/>',
			tabIndex: 3,
			width: 300,
			readOnly: true
		},{
			xtype: 'textfield',
			name: 'gateway',
			fieldLabel: '<spring:message code="default.gateway"/>',
			tabIndex: 4,
			width: 300,
			readOnly: true
		},{
			xtype: 'textfield',
			name: 'dns1',
			fieldLabel: '<spring:message code="dns.server.1"/>',
			tabIndex: 5,
			width: 300,
			readOnly: true
		},{
			xtype: 'textfield',
			name: 'dns2',
			fieldLabel: '<spring:message code="dns.server.2"/>',
			tabIndex: 6,
			width: 300,
			readOnly: true
		},{
			xtype: 'textfield',
			name: 'MACAddress',
			fieldLabel: '<spring:message code="mac.address"/>',
			selectOnFocus: true,
			allowBlank: false,
			tabIndex: 7,
			width: 300
		},{
			xtype: 'textfield',
			name: 'ipAddress2',
			id: 'ipAddress2',
			fieldLabel: '<spring:message code="ip.address"/> 2 (eth0:0)',
			tabIndex: 8,
			width: 300,
			readOnly: true,
			hideLabel: true,
			hidden: true
		},{
			xtype: 'textfield',
			name: 'fqdn2',
			id: 'fqdn2',
			fieldLabel: 'FQDN 2 (eth0:0)',
			tabIndex: 9,
			width: 300,
			hideLabel: true,
			hidden: true,
			readOnly: true
		}]
	});

	// trigger the data store load
	networkForm.getForm().load({
		url: '<c:url value="network.ajax"/>',
		waitMsg: '<spring:message code="loading"/>',
		success: function (f, a) {
			if(Ext.getCmp('ipAddress2').getValue() != ""){
				Ext.getCmp('ipAddress2').show();
				Ext.getCmp('fqdn2').show();
				Ext.getCmp('ipAddress2').getEl().up('.x-form-item').removeClass('x-hide-label');
				Ext.getCmp('fqdn2').getEl().up('.x-form-item').removeClass('x-hide-label');
			}
		}
	});

	new Ext.Panel({
		renderTo: 'content-panel',
		border: false,
		items: [{
			layout: 'column',
			border: false,
			items: [{
				columnWidth: .25,
				baseCls: 'x-plain',
				bodyStyle: 'padding:5px 5px 5px 5px',
				id: 'local',
				items:[
					localmenu
				]
			},{
				columnWidth: .75,
				baseCls: 'x-plain',
				bodyStyle: 'padding:5px 5px 5px 5px',
				items:[
					networkForm
				]
			}]
		}]
	});

});

</script>

<%@ include file="footer_html.jsp" %>