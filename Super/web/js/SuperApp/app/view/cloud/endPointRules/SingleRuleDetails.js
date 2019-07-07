Ext.define('SuperApp.view.cloud.endPointRules.SingleRuleDetails', {
    extend : 'Ext.form.Panel',

    xtype : 'singleRuleDetails',

    requires : ['SuperApp.view.cloud.endPointRules.RuleInfoForm'],

    reference : 'singleRuleDetails',

    deletedRuleSets : [],
 cls : 'pools-grid',
    border : 1,

    initComponent : function() {
        var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor : 1,
            errorSummary : false,
            autoCancel : false
        });
        var me = this;

        var items = [{
            xtype : 'toolbar',
            cls : 'white-header-footer',
            border : 1,
            items : [{
                xtype : 'button',
                iconCls : 'back-button-icon',
                icon : 'js/SuperApp/resources/images/control_rewind.png',
                handler : 'backToRules',
                tooltip : 'Back To Endpoint Rules'
            }, {
                xtype : 'button',
                text : l10n('save-rule'),
                iconCls : 'x-fa fa-edit',
                formBind:true,
                handler : 'saveRuleDetails'
            }]
        }, {
                xtype : 'hidden',
                name : 'id'
            }, {
                xtype : 'hidden',
                name : 'ruleOrder'
            }, {
                xtype : 'textfield',
                margin : 10,
                fieldLabel : l10n('rule-name'),
                name : 'ruleName',
                maxLength:128,
                vtype: 'nohtml',
                msgTarget : 'under',
                allowBlank:false
            }, {
                xtype : 'grid',
                selModel : Ext.create('Ext.selection.CheckboxModel', {
                    showHeaderCheckbox : true,
                    mode : 'SIMPLE',
                    checkOnly : true
                }),
            listeners : {
                edit : 'onRuleOrderEdit'
            },
                plugins : [rowEditing],
                bind : {
                    store : '{ruleSetStore}'
                },
                margin : 10,
                flex : 1,
                reference : 'ruleSetGrid',
                columns : [{
                    dataIndex : 'ruleSetOrder',
                    hidden : true,
                    text : l10n('order'),
                    flex : 1,
                    editor : {
                        xtype : 'numberfield',
                        allowBlank : false,
                        maxLength:9,
                        regex:/^[1-9][0-9]*$/
                    }
                }, {
                    text : l10n('ruleset'),
                    flex : 3,
                    renderer : function(value, mataData, record, rowIndex, colIndex, store, view) {
                        var returnString = "";
                        if (record.get('privateIP')) {
                            returnString += l10n('local-ip') + " : " + record.get('privateIP') + "/" + record.get('privateIpCIDR');
                        }

                        if (record.get('publicIP')) {
                            if (returnString != "") {
                                returnString += "; ";
                            }
                            returnString += l10n('external-ip') + " : " + record.get('publicIP') + "/" + record.get('publicIpCIDR');
                        }

                        if (record.get('locationTagID')) {
                            returnString += l10n('user-location-tag') + " : " + Ext.util.Format.htmlEncode(me.lookupViewModel().storeInfo.ruleLocations.findRecord('locationID', record.get('locationTagID')).get('locationTag'));
                        }

                        if (record.get('endpointID')) {
                            returnString += l10n('endpoint-id') + " : " + record.get('endpointID');
                        }

                        return '<span>' + returnString + '</span><span><img id="disclosure-image-rule-detail-' + record.get("id") + '" style="float:right; cursor:pointer;" data-disclosure=true src="js/SuperApp/resources/images/edit-icon.png"/></span>';
                    }
                }]
            }, {
                xtype : 'toolbar',
                cls : 'white-header-footer',
            border : 1,
                items : [{
                    xtype : 'button',
                    text : l10n('add-ruleset'),
                    handler : 'addRuleset'
                }, {
                    xtype : 'button',
                    text : l10n('edit-ruleset'),
                    handler : 'editRuleset'
                }, {
                    xtype : 'button',
                    text : l10n('delete-ruleset'),
                    handler : 'deleteRuleset'
                }]
            }, {
                xtype : 'combo',
                margin : 10,
                editable : false,
                fieldLabel : l10n('priority-list'),
                displayField : 'priorityListName',
                valueField : 'id',
                queryMode : 'local',
                bind : {
                    store : '{freePriorityListsStore}'
                },
                name : 'priorityList'
        }];

        this.items = items;

        this.callParent();
    }
}); 