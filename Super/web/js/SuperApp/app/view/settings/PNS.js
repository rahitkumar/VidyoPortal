Ext.define('SuperApp.view.settings.PNS', {
	extend : 'Ext.form.Panel',
	xtype : 'pnsform',
	alias : 'widget.pnsform',
	layout : {
		type : 'vbox',
		align : 'center',
		pack : 'center'
	},
	width : '100%',
	height : '100%',
	border : true,
	bodyStyle: 'background:#F6F6F6',
	bodyPadding : 10,
	title : {
		text : '<span class="header-title">'
				+ l10n('platform-network-settings') + '</span>',
		textAlign : 'center'
	},
	items : [ {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('ip-address-eth0'),
		name : 'ipAddress'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('fqdn-eth0'),
		name : 'fqdn1'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('subnet-mask'),
		name : 'subnetMask'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('default-gateway'),
		name : 'gateway'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('dns-server-1'),
		name : 'dns1'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('dns-server-2'),
		name : 'dns2'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('mac-address'),
		name : 'MACAddress'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('system-id'),
		name : 'SystemID'
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('fqdn-2-etho0-0'),
		name : 'fqdn2',
		hidden : true
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('ip-address-2-eth0-0'),
		name : 'ipAddress2',
		hidden : true
	}, {
		xtype : 'textfield',
		readOnly : true,
		width : 600,
		labelWidth : 150,
		fieldLabel : l10n('system-time-zone'),
		name : 'SystemTimeZone'
	} ]
});
