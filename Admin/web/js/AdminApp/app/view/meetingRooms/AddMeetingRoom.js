Ext.define('AdminApp.view.meetingRooms.AddMeetingRoom', {
    extend: 'Ext.window.Window',
    alias: 'widget.addmeetingroomview',
    border: false,
    width: 760,
    minWidth: 760,
    modal: true,
    constrain: true,
    closeAction: 'hide',
    closable: true,
    reference: 'addMeetingRoomsWin',
    resizeable: false,
    bind: {
        title: '{title}'
    },
    initComponent: function() {
        var me = this;
        Ext.apply(Ext.form.VTypes, {
            roomExtNumber: function(val, field) {
                var tenantPrefix = me.down('textfield[name=tenantPrefix]').getValue();
                if (tenantPrefix != "") {
                    val = tenantPrefix + val;
                }
                var re = /^[0-9]+$/;
                if (val.length > 64) {
                    return false;
                }
                return re.test(val);
            },
            roomExtNumberText: l10n('the-format-is-wrong-must-only-contain-numeric-values-and-allowed-limit'),
            roomPIN: function(val, field) {
                var re = /^[0-9]+$/;
                return re.test(val);
            },
            roomPINText: l10n('the-format-is-wrong-must-only-contain-numeric-values')
        });

        this.items = [{
            xtype: 'form',
            layout: {
                type: 'vbox'
            },
            reference: 'addMeetingRoomForm',
            errorReader: {
                type: 'xml',
                record: 'field',
                model: 'AdminApp.model.Field',
                success: '@success'
            },

            reader: {
                type: 'xml',
                totalRecords: 'results',
                record: 'row',
                id: 'roomID',
                model: 'AdminApp.model.meetingRooms.AddMeetingRoomModel'
            },
            border: false,
            items: [{
                xtype: 'fieldset',
                border: 1,
                padding: 0,
                width: '100%',
                margin: 5,
                layout: {
                    type: 'vbox',
                    align: 'center'
                },
                items: [{
                    xtype: 'hidden',
                    name: csrfFormParamName,
                    value: csrfToken
                }, {
                    xtype: 'hidden',
                    name: 'roomID',
                    reference: 'roomID',
                    value: 0
                }, {
                    xtype: 'hidden',
                    name: 'roomTypeID',
                    reference: 'roomTypeID',
                    value: 2
                }, {
                    xtype: 'hidden',
                    name: 'roomKey'
                }, {
                    xtype: 'textfield',
                    name: 'currRoomExt',
                    hidden: true,
                    disabled: true,
                    bind: {
                        value: '{currRoomExt}'
                    }
                }, {
                    xtype: 'textfield',
                    name: 'currRoomName',
                    hidden: true,
                    disabled: true,
                    bind: {
                        value: '{currRoomName}'
                    }
                }, {
                    xtype: 'textfield',
                    reference: 'roomDisplayName',
                    name: 'rmDisplayName',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('room-display-name'),
                    allowBlank: false,
                    bind: {
                        readOnly: '{roomNameReadOnly}'
                    },
                    tabIndex: 1,
                    validateOnChange: false,
                    validator: me.remoteValidate,
                    labelWidth: 100,
                    width: 300,
                    margin: 10,
                    maxLength: 80,
                    listeners: {
                        change: 'remoteValidateDisplayName'
                    }
                }, {
                    xtype: 'textfield',
                    reference: 'roomName',
                    name: 'roomName',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('room-name'),
                    allowBlank: false,
                    bind: {
                        readOnly: '{roomNameReadOnly}'
                    },
                    tabIndex: 1,
                    validateOnChange: false,
                    validator: me.remoteValidate,
                    labelWidth: 100,
                    width: 300,
                    margin: 10,
                    maxLength: 80,
                    listeners: {
                        change: 'remoteValidateRoomName'
                    }
                }, {
                    xtype: 'textfield',
                    reference: 'displayName',
                    name: 'displayName',
                    fieldLabel: l10n('room-owner'),
                    resizable: false,
                    readOnly: true,
                    minChars: 1,
                    tabIndex: 2,
      
                    labelWidth: 100,
                    width: 300,
                    margin: '0 0 10 10',
                    hidden: true,
                    disabled: true
                }, {
                    xtype: 'textfield',
                    name: 'memberID',
                    reference: 'memberID',
                    hidden: true,
                    disabled: true
                }, {
                    xtype: 'combo',
                    hiddenName: 'memberID',
                    reference: 'ownerID',
                    name: 'ownerID',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('room-owner'),
                    bind: {
                        store: '{roomOwnerComboStore}'
                    },
                    valueField: 'memberID',
                    displayField: 'memberName',
                    loadingText: l10n('searching'),
                    triggerAction: 'all',
                    //queryMode : 'local',
                    emptyText: l10n('select-a-member'),
                    allowBlank: false,
                    selectOnFocus: true,
                    editable: true,
                    forceSelection: true,
                    resizable: false,
                    typeAhead: true,
                    minChars: 1,
                    tabIndex: 2,
                    labelWidth: 100,
                    margin: '0 0 10 10',
                    hidden: true,
                    disabled: true,
                
                    width: 300,
                    queryMode: 'remote',
                    enableKeyEvents: true,
                    listeners: {
                        select: 'selectedValidRoomOwner',
                        beforerender: 'roomOwnerComboLoad'
                    },
                    listConfig: {
                        getInnerTpl: function(displayField) {
                            return '{[Ext.String.htmlEncode(values.' + displayField + ')]}';
                        }
                    },
                }, {
                    xtype: 'fieldset',
                    name: 'username',
                    reference: 'extension',
                    layout: {
                        type: 'hbox',
                        align: 'center'
                    },
                    border: 0,
                    items: [{
                        xtype: 'displayfield',
                        fieldLabel: '<span class="red-label">*</span>' + l10n('extension'),
                        labelWidth: 60,
                        margin: '0 5 0 38'
                    }, {
                        xtype: 'textfield',
                        labelSeparator: '',
                        width: 80,
                        disabled: true,
                     
                        name: 'tenantPrefix',
                        reference: 'tenantPrefix',
                        bind: {
                            value: '{tenantPrefixVal}'
                        }
                    }, {
                        xtype: 'textfield',
                        hideTrigger: true,
                        enforceMaxLength: true,
                        labelSeparator: '',
                        hideLabel: true,
                        width: 115,
                        allowBlank: false,
                      
                        reference: 'roomExtNumber',
                        name: 'roomExtNumber',
                        vtype: 'roomExtNumber',
                        validateOnChange: false,
                        validator: me.remoteValidate,
                        tabIndex: 3,
                        allowExponential: false,
                        listeners: {
                            change: 'remoteValidateExt'
                        }
                    }]
                }, {
                    xtype: 'combo',
                    hiddenName: 'groupID',
                    reference: 'roomGroupID',
                    name: 'groupID',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('group'),
                    bind: {
                        store: '{groupComboStore}'
                    },
                    valueField: 'groupID',
                    displayField: 'groupName',
                    tpl: Ext.create('Ext.XTemplate',
                        '<ul class="x-list-plain"><tpl for=".">',
                        '<li role="option" class="x-boundlist-item">{groupName:htmlEncode}</li>',
                        '</tpl></ul>'
                    ),
                    loadingText: l10n('searching'),
                    triggerAction: 'all',
                    emptyText: l10n('select-a-group'),
                    allowBlank: false,
                    typeAhead: false,
                    selectOnFocus: true,
                    editable: true,
                    resizable: false,
                    minChars: 1,
                    tabIndex: 4,
                    labelWidth: 100,
                   
                    margin: '0 0 10 10',
                    queryMode: 'remote',
                    width: 300,
                    listeners: {
                        beforerender: 'groupComboLoad'
                    }
                }, {
                    xtype: 'textarea',
                    reference: 'roomDescription',
                    name: 'roomDescription',
                    fieldLabel: l10n('description'),
                    tabIndex: 5,
                    labelWidth: 100,
                    
                    margin: '0 0 10 10',
                    maxLength: 65535,
                    width: 300
                }, {
                    xtype: 'checkboxgroup',
                    reference: 'roomStatus',
                    labelWidth: 98,
                    width: 300,
                    fieldLabel: l10n('room-status'),
                    vertical: true,
                    margin: 0,
                    padding: 0,
                    bodyPadding: 0,
                    items: [{
                        xtype: 'checkbox',
                        name: 'enabled',
                        reference: 'enabled',
                        checked: true,
                        margin: '0 10 0 0',
                        boxLabel: l10n('enabled')
                    }, {
                        margin: '0 12 0 5',
                        xtype: 'checkbox',
                        name: 'locked',
                        reference: 'locked',
                        boxLabel: l10n('locked')
                    }]
                }, {
                    xtype: 'fieldset',
                    bind: {
                        title: '{roomPinTitle}'
                    },
                    layout: 'hbox',
                    margin: 5,
                    width: '100%',
                    autoHeight: true,
                    items: [{
                        xtype: 'radiogroup',
                        margin: '0 0 10 10',
                        vertical: true,
                        columns: 1,
                        items: [{
                            boxLabel: l10n('enter-new-pin'),
                            width: 160,
                            name: 'pinSetting',
                            reference: 'enterNewPin',
                            inputValue: 'enter',
                            listeners: {
                                focus: 'pinSelect'
                            }
                        }, {
                            boxLabel: l10n('leave-pin-alone'),
                            width: 160,
                            name: 'pinSetting',
                            reference: 'leavePin',
                            checked: true,
                            inputValue: 'leave',
                            listeners: {
                                focus: 'pinSelect'
                            }
                        }, {
                            boxLabel: l10n('remove-pin'),
                            width: 160,
                            name: 'pinSetting',
                            reference: 'removePin',
                            hidden: true,
                            disabled: true,
                            inputValue: 'remove',
                            listeners: {
                                focus: 'pinSelect'
                            }
                        }]
                    }, {
                        xtype: 'textfield',
                        hideTrigger: true,
                        vtype: 'roomPIN',
                        maskRe: /[0-9*]/,
                        validateOnBlur: true,
                        msgTarget: 'qtip',
                        inputType: 'password',
                        disabled: true,
                        reference: 'roomPIN',
                        name: 'roomPIN',
                        allowBlank: false,
                        
                        allowExponential: false,
                        allowNegative: false
                    }]
                }, {
                    xtype: 'fieldset',
                    title: l10n('room-moderator-pin'),
                    bind: {
                        title: '{roomModPinTitle}'
                    },
                    layout: 'hbox',
                    margin: 5,
                    width: '100%',
                    autoHeight: true,
                    items: [{
                        xtype: 'radiogroup',
                        margin: '0 0 10 10',
                        vertical: true,
                        columns: 1,
                        items: [{
                            boxLabel: l10n('enter-new-pin'),
                            width: 160,
                            name: 'moderatorPinSetting',
                            reference: 'enterNewModPin',
                            inputValue: 'enter',
                            listeners: {
                                focus: 'moderatorPinSelect'
                            }
                        }, {
                            boxLabel: l10n('leave-pin-alone'),
                            width: 160,
                            name: 'moderatorPinSetting',
                            reference: 'leaveModPin',
                            checked: true,
                            inputValue: 'leave',
                            listeners: {
                                focus: 'moderatorPinSelect'
                            }
                        }, {
                            boxLabel: l10n('remove-pin'),
                            width: 160,
                            name: 'moderatorPinSetting',
                            reference: 'removeModPin',
                            hidden: true,
                            disabled: true,
                            inputValue: 'remove',
                            listeners: {
                                focus: 'moderatorPinSelect'
                            }
                        }]
                    }, {
                        xtype: 'textfield',
                        hideTrigger: true,
                        vtype: 'roomPIN',
                        maskRe: /[0-9]/,
                        validateOnBlur: true,
                        msgTarget: 'qtip',
                        inputType: 'password',
                        disabled: true,
                        reference: 'roomModeratorPIN',
                        name: 'roomModeratorPIN',
                        allowBlank: false,
                        
                        allowExponential: false,
                        allowNegative: false

                    }]
                }, {
                    xtype: 'fieldset',
                    title: l10n('room-url'),
                    autoHeight: true,
                    hidden: true,
                    disabled: true,
                    width: '100%',
                    reference: 'roomUrlFieldset',
                    defaults:{
                        margin: 1
                
                    },
                    layout: {
                        type: 'hbox',
                        align: 'center'
                    },
                    items: [{
                        xtype: 'textfield',
                        reference: 'roomURL',
                        name: 'roomURL',
                        width: '88%',
                        hideLabel: true,
                        readOnly: true,
                         tabIndex: 1,
                        growMax: true,
      
                        helpText: l10n('click-button-to-generate-or-remove-public-url')
                    }, {
                         xtype : 'toolbar',
                         style:{
                            'background':'#F6F6F6',
                        },
                        border : false,
                        items : [{
                            xtype : 'button',
                       		reference: 'generateURL',
                       		iconCls:'x-fa fa-refresh',
                            tooltip: l10n('generate-new-room-url'),
                        handler: 'generateURL'
                        }, {
                        xtype: 'button',
                        text: '',
                        reference: 'removeURL',
                       
                        iconCls: 'x-fa fa-ban',
                        tooltip: l10n('remove-room-url'),
                        handler: 'removeURL'
                    }]
                    }]
                }]
            }],
            buttonAlign: 'center',
            buttons: [{
                xtype: 'button',
                text: l10n('save'),
                reference: 'saveMeetingRoom',
                tabIndex: 16,
                formBind: true,
                disabled: true,
                handler: 'saveMeetingroom'
            }, {
                xtype: 'button',
                text: l10n('close'),
                tabIndex: 17,
                handler: 'closeAddMeetingRoom'
            }]

        }];


        this.callParent();
    },

    listeners: {
        actioncomplete: 'addMeetingRoomFormLoad'
    },

    remoteValidate: function() {
        if (this.textValid) {
            return true;
        } else {
            return this.textInvalid;
        }
    }
});