Ext.define('AdminApp.view.groups.AddGroups', {
    extend : 'Ext.window.Window',
    alias : 'widget.addgroupsview',
    border : false,
    width : 600,
    minWidth : 600,
    modal : true,
    constrain : true,
    closeAction : 'hide',
    closable : true,
    reference : 'addGroupWin',
    resizeable : false,
    bind : {
         title : '{title}'
    },
    initComponent : function() {
        var addGroupPanel,
        allowVideoReplay;
        var me = this;
        allowVideoReplay = {
            xtype : 'fieldcontainer',
            margin : 10,
            reference : 'allowVideoReplay',
            layout : 'hbox',
            width : 550,
            items : [{
                xtype : 'checkbox',
                name : 'allowRecordingFlag',
                // reference : 'allowRecordingFlag',
                bind : {
                    hidden : '{hasReplay}'
                },
                fieldLabel : l10n('allow-vidyoreplay'),
                labelWidth : 160,
                boxLabel : l10n('enabled')
            }]
        };

        this.items = [{
        	xtype : 'form',
        	reference : 'addGroupForm',

            layout : {
                type : 'vbox',
                align : 'center'
            },
           bodyStyle: 'background:#F6F6F6;',
            errorReader : {
                type : 'xml',
                record : 'field',
                model : 'AdminApp.model.Field',
                success : '@success'
            },
            reader : {
                type : 'xml',
                totalRecords : 'results',
                record : 'row',
                id : 'groupID',
                model : 'AdminApp.model.groups.AddGroupModel'
            },
            items : [
            	{
                    xtype : 'hidden',
                    name : csrfFormParamName,
                    value : csrfToken
                }, {
                    xtype : 'hiddenfield',
                    reference : 'groupId',
                    name : 'groupID',
                    bind : {
                        value : '{groupIdVal}'
                    }
                }, {
                    xtype : 'textfield',
                    labelWidth : 160,
                    width : 400,
                    name : 'groupName',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('group-name'),
                    allowBlank : false,
                    maxLength : 80,
                    count : 1,
                    validateOnChange : true,
                    validFlag : true,
                    validator : function() {
                     return this.validFlag;
                    },                    
                    msgTarget : 'under',
                    listeners : {
                        change : 'remoteValidateGroupName'
                    },
                    reference : 'groupNameText'
                }, {
                    xtype : 'textarea',
                    labelWidth : 160,
                    width : 400,
                    name : 'groupDescription',
                    fieldLabel : l10n('description'),
                    maxLength : 65535,
                    msgTarget : 'under'
                }, {
                    xtype : 'numberfield',
                    labelWidth : 160,
                    width : 400,
                    name : 'roomMaxUsers',
                    fieldLabel : '<span class="red-label">*</span>' + l10n('max-number-of-participants'),
                    hideTrigger : true,
                    maxValue : 2147483647,
                    minValue : 2,
                    value : 10,
                    msgTarget : 'under',
                    allowBlank : false,
                    reference : 'maxparticipants'
                }, {
                    xtype : 'numberfield',
                    name : 'userMaxBandWidthIn',
                    labelWidth : 160,
                    width : 400,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('max-receive-bandwidth-per-user-kbps'),
                    hideTrigger : true,
                    maxValue : 100000,
                    minValue : 0,
                    value : 100000,
                    msgTarget : 'under',
                    allowBlank : false,
                    reference : 'maxreceivebandwidth'
                }, {
                    xtype : 'numberfield',
                    name : 'userMaxBandWidthOut',
                    labelWidth : 160,
                    width : 400,
                    fieldLabel : '<span class="red-label">*</span>' + l10n('max-transmit-bandwidth-per-user-kbps'),
                    hideTrigger : true,
                    maxValue : 100000,
                    minValue : 0,
                    value : 100000,
                    msgTarget : 'under',
                    allowBlank : false,
                    reference : 'maxtransmitbandwidth'
                }, {
                    xtype : 'checkbox',
                    name : 'allowRecordingFlag',
                    reference : 'allowRecordingFlag',
                    bind : {
                        hidden : '{hasReplay}'
                    },
                    fieldLabel : l10n('allow-vidyoreplay'),
                    labelWidth : 160,
                    width : 400,
                    boxLabel : l10n('enabled')
                }],
	           buttonAlign : 'center',
                buttons : [{
                     xtype : 'button',
	                text : l10n('save'),
	                name : 'saveGroups',
	                itemId : 'saveGroupBtn',
	                formBind : true,
	                handler : 'saveGroup',
	                disabled : true
	            }, {
                     xtype : 'button',
	                text : l10n('close'),
	                handler : 'closeAddGroup'
	            }]
        }];
        this.callParent(arguments);
    }
});
