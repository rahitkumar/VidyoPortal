Ext.define('AdminApp.view.users.AddUsers', {
    extend: 'Ext.window.Window',
    alias: 'widget.addusersview',
    border: false,
    width: 600,
    minWidth: 600,
    modal: true,
    bind: {
        title: '<span class="header-title">{title}</span>'
    },
    constrain: true,
    // better to use destroy instead of hide ,but because view model, we are getting js error after opening couple of windows
    closeAction: 'hide',
    closable: true,
    reference: 'addUsersWin',
    resizeable: false,
    initComponent: function() {
        var passwordFields,
            changePasswordFieldset;
        var me = this;
        var roleID = 3;

        Ext.apply(Ext.form.VTypes, {
            password: function(val, field) {
                if (field.initialPassField) {
                    var pwd = me.down('textfield[itemId=' + field.initialPassField + ']');
                    return (val == pwd.getValue());
                }
                return true;
            },
            passwordText: l10n('password-not-match'),

            passwordForEdit: function(val, field) {
                if (field.initialPassField) {
                    var pwd = me.query('textfield[itemId=' + field.initialPassField + ']')[1];
                    return (val == pwd.getValue());
                }
                return true;
            },
            passwordForEditText: l10n('password-not-match'),

            roomExtNumber: function(val, field) {
                var tenantPrefix = me.down('textfield[name=tenantPrefix]').getValue();
                var currRoomExt = '';
                var memberID = me.down('textfield[name=memberID]').getValue();
                if (memberID != 0) {
                    currRoomExt = me.down('textfield[name=currRoomExt]').getValue();
                }
                if (tenantPrefix != "") {
                    val = tenantPrefix + val;
                }
                var re = /^[0-9]+$/;
                return re.test(val);
            },
            /*email: function (val,field){
                //var re = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,})+$/;
            	var re = /^\w+([.-]\w+)*@\w+([.-]\w+)*\.\w{2,}$/;
            	var re = /^\w+([.!#$%&'*+-/=?^_`{|}~,]\w+)*@\w+([.-]\w+)*\.\w{2,}$/;
                return re.test(val);
            },*/
            roomExtNumberText: l10n('the-format-is-wrong-must-only-contain-numeric-values')

        });

        passwordFields = {
            xtype: 'container',
            layout: {
                type: 'vbox',
                align: 'center'
            },
            width: 600,
            border: true,
            margin: '0 0 10 10',
            reference: 'passwordFieldContainer',
            items: [{
                xtype: 'textfield',
                fieldLabel: '<span class="red-label">*</span>' + l10n('password'),
                name: 'password1',
                inputType: 'password',
                allowBlank: false,
                reference: 'password1',
                itemId: 'password1',
                width: 430,
                labelWidth: 160,
                margin: '0 0 10 10',
                tabIndex: 4,
                maskRe: /[^ ]/
            }, {
                xtype: 'textfield',
                fieldLabel: '<span class="red-label">*</span>' + l10n('verify-password'),
                name: 'password2',
                reference: 'password2',
                inputType: 'password',
                itemId: 'password2',
                allowBlank: false,
                tabIndex: 5,
                width: 430,
                labelWidth: 160,
                margin: '0 0 10 10',
                vtype: 'password',
                initialPassField: 'password1',
                maskRe: /[^ ]/
            }]
        };

        changePasswordFieldset = {
            xtype: 'container',
            layout: {
                type: 'vbox',
                align: 'center'
            },
            bind: {
                        disabled: '{isLdapEnabled}',
                         hidden: '{isLdapEnabled}'
                    },
            border: true,
            reference: 'changepasswordFieldContainer',
            items: [{
                xtype: 'fieldset',

                reference: 'changepasswordFieldset',

                checkboxToggle: true,
                title: l10n('change-password'),
                autoHeight: true,
                collapsed: true,
                width: 500,
                margin: '0 0 10 10',


                listeners: {
                    collapse: 'changePassCollapse',
                    expand: 'changePassExpand',
                    render: 'changePassRender'
                },
                items: [{
                    xtype: 'textfield',
                    name: 'password1',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('password'),
                    inputType: 'password',
                    allowBlank: false,
                    reference: 'password1Edit',
                    itemId: 'password1',
                    width: 450,
                    labelWidth: 180,
                    margin: '0 0 10 10',
                    tabIndex: 4,

                    maskRe: /[^ ]/
                }, {
                    xtype: 'textfield',
                    name: 'password2',
                    reference: 'password2Edit',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('verify-password'),
                    inputType: 'password',
                    allowBlank: false,
                    width: 450,
                    labelWidth: 180,
                    margin: '0 0 10 10',
                    vtype: 'passwordForEdit',
                    tabIndex: 5,
                    initialPassField: 'password1',
                    maskRe: /[^ ]/
                }]
            }]
        };

        userAdditionalInfo = {
            xtype: 'fieldset',
            title: l10n('additional-information'),
            autoHeight: true,
            collapsed: true,
            width: 500,
            checkboxToggle: true,
            bodyStyle: 'background:#fff;',
            //  margin: '0 0 10 10',
            items: [

                {
                    xtype: 'textfield',
                    name: 'phone1',
                    fieldLabel: l10n('phone-number-1'),
                    labelWidth: 180,
                    margin: '0 0 10 10',
                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                      tabIndex: 18,
                    maxLength: 64,

                }, {
                    xtype: 'textfield',
                    name: 'phone2',
                    fieldLabel:l10n('phone-number-2'),
                    labelWidth: 180,
                    margin: '0 0 10 10',
                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    maxLength: 64,
                    tabIndex: 19,

                }, {
                    xtype: 'textfield',
                    name: 'phone3',
                    margin: '0 0 10 10',
                    fieldLabel: l10n('phone-number-3'),
                    labelWidth: 180,

                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    maxLength: 64,
                    tabIndex: 20

                }, {
                    xtype: 'textfield',
                    name: 'department',
                    fieldLabel: l10n('department'),
                    labelWidth: 180,
                    margin: '0 0 10 10',
                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    maxLength: 128,
                    tabIndex: 21,

                },

                {
                    xtype: 'textfield',
                    name: 'title',
                    margin: '0 0 10 10',
                    fieldLabel: l10n('user-title'),
                    labelWidth: 180,
                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    maxLength: 128,
                    tabIndex: 22,

                }, {
                    xtype: 'textfield',
                    name: 'location',
                    margin: '0 0 10 10',
                    fieldLabel: l10n('location'),
                    labelWidth: 180,
                    maxLength: 128,

                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    tabIndex: 22,

                }, {
                    xtype: 'textfield',
                    name: l10n('instantMessager-id'),
                    margin: '0 0 10 10',
                    fieldLabel: l10n('user-IM'),
                    labelWidth: 180,

                    width: 450,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    maxLength: 128,
                    tabIndex: 23

                }, {
                    xtype: 'textarea',
                    name: 'description',
                    fieldLabel: l10n('description'),
                    width: 450,
                    labelWidth: 180,
                    margin: '0 0 10 10',
                    maxLength: 65535,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    tabIndex: 24
                }
            ]
        };
        userPrimaryPanel = {
            xtype: 'container',
            margin: "10 0 0 0",
            items: [{
                    xtype: 'hidden',
                    name: csrfFormParamName,
                    value: csrfToken
                }, {
                    xtype: 'hidden',
                    name: 'thumbnailImageFileName',
                    reference: 'thumbnailImageFileName',
                    bind: {
                        value: '{thumbnailImageFileName}'
                    }
                }, {
                    xtype: 'textfield',
                    hidden: true,
                    name: 'memberID',
                    reference: 'memberId',
                    bind: {
                        value: '{memberIdVal}'
                    }
                }, {
                    xtype: 'hidden',
                    name: 'roomTypeID',
                    value: 1
                }, {
                    xtype: 'hidden',
                    name: 'roomID',
                    reference: 'roomId',
                    bind: {
                        value: '{roomIdVal}',
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled '
                }, {
                    xtype: 'textfield',
                    hidden: true,
                    disabled: true,
                    name: 'currRoomExt',
                    reference: 'currRoomExt',
                    bind: {
                        value: '{currRoomExt}',
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled '
                }, {
                    xtype: 'combo',
                    hiddenName: 'roleID',
                    name: 'roleID',
                    reference: 'roleId',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('user-type'),
                    bind: {
                        store: '{UserTypeStore}',
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled ',
                    tabIndex: 1,
                    valueField: 'roleID',
                    displayField: 'roleName',
                    typeAhead: false,
                    loadingText: l10n('searching'),
                    triggerAction: 'all',
                    emptyText: l10n('select-member-type'),
                    resizable: false,
                    allowBlank: false,
                    editable: false,
                    minChars: 1,
                    queryMode: 'local',
                    labelWidth: 180,
                    width: 400,
                    margin: 4,
                    value: 3,
                    listeners: {
                        change: 'userTypeComboChange',
                        select: 'userTypeComboSelect'
                    }
                }, {
                    xtype: 'textfield',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('user-name'),
                    name: 'username',
                    reference: 'deviceName',
                    labelWidth: 180,
                    maxLength: 80,
                    width: 400,
                    allowBlank: false,
                    tabIndex: 2,
                    margin: 4,
                    // vtype: 'username',
                    validateOnChange: false,
                    validator: me.remoteValidate,
                    listeners: {
                        change: 'remoteValidateMemberName'
                    },
                    bind: {
                        readOnly: '{userNameReadOnly}',
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled '
                }, {
                    xtype: 'textfield',
                    fieldLabel: '<span class="red-label">*</span>' + l10n('display-name'),
                    name: 'memberName',
                    reference: 'displayName',

                    labelWidth: 180,
                    width: 400,
                    maxLength: 80,
                    tabIndex: 3,
                    allowBlank: false,
                    margin: 4,
                    bind: {
                        disabled: '{isImportedUsed}'
                    },
                    disabledCls: 'user-edit-disabled '
                }


            ]
        };
        imageBlankPanel = {

            xtype: 'panel',
            width: '145',

            border: false,
            margin: "5 0 5 5",
            bind: {
                hidden: '{isUsrImgEnbledAdmin}'
            },

            items: [{

                text: '',
                width: 124,
                // margin: '40 30 20 10'

            }]
        };

        imagePanel = {
            xtype: 'form',
            reference: 'imageForm',
            bind: {
                hidden: '{!isUsrImgEnbledAdmin}'
            },
            margin: "5 0 5 5",
            height: '194',
            width: '145',
            border: false,
            layout: {
                type: 'vbox'
            },
            errorReader: {
                type: 'xml',
                record: 'field',
                model: 'AdminApp.model.Field',
                success: '@success'
            },
            reader: {
                type: 'xml',
                record: 'field',

                success: '@success'
            },
            cls: 'thumbnailcss',

            items: [{
                    xtype: 'hidden',
                    name: csrfFormParamName,
                    value: csrfToken
                }, {
                    xtype: 'container',
                    items: {
                        xtype: 'image',

                        width: 120,
                        height: 120,
                        //this will be moved to server.

                        reference: 'thumbnailImage',
                        name: 'thumbnailImage',


                    }
                }, {
                    xtype: 'panel',
                    layout: {
                        type: 'hbox'
                    },

                    items: [{
                        xtype: 'filefield',
                        name: 'thumbnailImagePreview',
                        reference:'thumbnailImagePreview',
                        buttonOnly: true,
                        margin: "0 0 0 2",
                        //regex: (/.(gif|jpg|jpeg|png)$/i),
                        // regexText: 'Invalid Image type. Allowed types are jpg,jpeg and png',
                        width: 30,
                        buttonText: '',
                        buttonConfig: {
                            iconCls: 'x-fa fa-camera',
                            //text: 'Upload Photo'
                        },

                        listeners: {
                            change: 'userImageUpload',
                            afterRender:'imageUploadAfterRender'
                        }
                    }, {
                        xtype: 'button',
                         margin: "0 0 1 5",
                         reference:'thumbnailImageDelete',
                        iconCls: 'x-fa fa-times',
                        tooltip:'Delete Photo',
                         listeners: {
                            click: 'imageDelete',
                        }, bind: {
                           // disabled: '{isImportedUsed}',
                            hidden:'{hideImageDelete}'
                        },

                    }]

                }

            ]


        };


        this.items = [{
            xtype: 'form',

            reference: 'addUsersForm',
            layout: {
                type: 'vbox'
            },
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
                id: 'memberID',
                model: 'AdminApp.model.users.AddUsersModel'
            },



            items: [{

                xtype: 'panel',
                border: false,
                layout: {
                    type: 'hbox',
                    align: 'top'
                },
                items: [imageBlankPanel, imagePanel, userPrimaryPanel]
            }, {
                xtype: 'panel',
                width: '100%',
                bodyStyle: 'marginLeft:10px',
                border: false,
                items: [{
                        xtype: 'checkbox',
                        name: 'userImageAllowed',
                        reference: 'disableUserImageUpld',
                        cls: 'override-for-tooltip',
                        uncheckedValue: 1,
                        inputValue:0,
                        checked:false,
                        fieldLabel: l10n('disable-user-image-upload'),
                        hideLabel: true,
                        boxLabel: l10n('disable-user-image-upload'),
                        listeners: {
                        //   change: 'disableUserImgUpldWarning',
                            /**
                             * @method afterRender
                             * @inheritdoc
                             * @return {void}
                             */
                            afterRender: 'displayToolTip'

                        },
                        labelSeparator: '',
                        disabledCls: 'user-edit-disabled ',
                        bind: {
                            disabled: '{!isUsrImgUpldEnbledAdm}',
                            hidden: '{!isUsrImgEnbledAdmin}'
                        },
                    },

                    passwordFields, changePasswordFieldset, {
                        xtype: 'container',
                        border: true,
                        layout: {
                            type: 'vbox',
                            align: 'center'
                        },
                        items: [{
                            xtype: 'textfield',
                            fieldLabel: '<span class="red-label">*</span>' + l10n('e-mail-address'),
                            name: 'emailAddress',
                            reference: 'emailId',

                            vtype: 'email',
                            labelWidth: 180,
                            maxLength: 254,
                            width: 450,
                            tabIndex: 6,
                            allowBlank: false,
                            bind: {
                                disabled: '{isImportedUsed}'
                            },
                            disabledCls: 'user-edit-disabled '
                        }, {
                            xtype: 'panel',
                            // bodyStyle: 'background:#F6F6F6;',
                            reference: 'extension',
                            border: false,
                            layout: {
                                type: 'hbox',
                                align: 'center'
                            },

                            style: {
                                padding: '2px',
                                marginBottom: '10px',
                            },
                            width: 500,

                            items: [{
                                xtype: 'label',
                                // columnWidth: .428,
                                style: {
                                    marginLeft: '139px',
                                    marginTop: '5px',
                                },
                                labelWidth: 100,
                                html: '<span class="red-label">*</span>' + l10n('extension') + ":",
                                bind: {
                                    disabled: '{isImportedUsed}'
                                },
                                disabledCls: 'user-edit-disabled '
                            }, {
                                xtype: 'textfield',
                                labelSeparator: '',
                                //columnWidth: .22,
                                width: 65,
                                disabled: true,
                                name: 'tenantPrefix',
                                reference: 'tenantPrefix',
                                bind: {
                                    value: '{tenantPrefix}'
                                }
                            }, {
                                xtype: 'textfield',
                                labelSeparator: '',
                                // columnWidth: .26,
                                hideLabel: true,
                                width: 200,
                                allowBlank: false,
                                reference: 'roomExtNumber',
                                name: 'roomExtNumber',
                                vtype: 'roomExtNumber',
                                maxLength: 64,
                                validateOnChange: false,
                                validator: me.remoteValidate,
                                validateOnBlur: true,
                                tabIndex: 8,
                                maskRe: /[0-9]/,
                                listeners: {
                                    change: 'remoteValidateExt'
                                },
                                bind: {
                                    disabled: '{isImportedUsed}'
                                },
                                disabledCls: 'user-edit-disabled '
                            }]
                        }, {
                            xtype: 'combo',
                            hiddenName: 'groupID',
                            name: 'groupID',

                            fieldLabel: '<span class="red-label">*</span>' + l10n('group'),
                            labelWidth: 180,

                            tabIndex: 9,
                            bind: {
                                store: '{groupComboStore}',
                                disabled: '{isImportedUsed}'
                            },
                            disabledCls: 'user-edit-disabled ',
                            tpl: Ext.create('Ext.XTemplate',
                                '<ul class="x-list-plain"><tpl for=".">',
                                '<li role="option" class="x-boundlist-item">{groupName:htmlEncode}</li>',
                                '</tpl></ul>'
                            ),
                            valueField: 'groupID',
                            displayField: 'groupName',
                            typeAhead: false,
                            loadingText: l10n('searching'),
                            triggerAction: 'all',
                            emptyText: l10n('select-a-group'),
                            allowBlank: false,
                            editable: false,
                            resizable: false,
                            margin: '0 0 10 10',
                            minChars: 1,
                            queryMode: 'local',
                            value: 1,

                            width: 450
                        }, {
                            xtype: 'combo',
                            hiddenName: 'proxyID',
                            name: 'proxyID',
                            tabIndex: 10,
                            fieldLabel: '<span class="red-label">*</span>' + l10n('proxy'),
                            labelWidth: 180,
                            bind: {
                                store: '{proxyComboStore}',
                                disabled: '{isImportedUsed}'
                            },
                            disabledCls: 'user-edit-disabled ',
                            valueField: 'proxyID',
                            displayField: 'proxyName',
                            typeAhead: false,
                            loadingText: l10n('searching'),
                            triggerAction: 'all',
                            emptyText: l10n('select-a-proxy'),
                            allowBlank: false,

                            editable: false,
                            resizable: false,
                            queryMode: 'local',
                            minChars: 1,
                            value: 0,
                            width: 450,
                            margin: '0 0 10 10'
                        }, {
                            xtype: 'combo',
                            hiddenName: 'locationID',
                            name: 'locationID',
                            tabIndex: 11,
                            fieldLabel: '<span class="red-label">*</span>' + l10n('location-tag'),
                            labelWidth: 180,
                            bind: {
                                store: '{locationTagComboStore}',
                                disabled: '{isImportedUsed}'
                            },
                            disabledCls: 'user-edit-disabled ',
                            tpl: Ext.create('Ext.XTemplate',
                                '<ul class="x-list-plain"><tpl for=".">',
                                '<li role="option" class="x-boundlist-item">{locationTag:htmlEncode}</li>',
                                '</tpl></ul>'
                            ),
                            valueField: 'locationID',
                            displayField: 'locationTag',
                            typeAhead: false,
                            loadingText: l10n('searching'),
                            triggerAction: 'all',
                            emptyText: l10n('select-a-location-tag'),
                            allowBlank: false,
                            queryMode: 'local',
                            editable: false,
                            resizable: false,
                            minChars: 1,
                            value: 1,
                            width: 450,
                            margin: '0 0 10 10'
                        }, {
                            xtype: 'combo',
                            hiddenName: 'langID',
                            name: 'langID',
                            tabIndex: 12,
                            fieldLabel: '<span class="red-label">*</span>' + l10n('language-preference'),
                            labelWidth: 180,
                            bind: {
                                store: '{langPreferenceStore}',
                                disabled: '{isImportedUsed}'
                            },
                            disabledCls: 'user-edit-disabled ',
                            valueField: 'langID',
                            displayField: 'langName',
                            typeAhead: false,
                            loadingText: l10n('searching'),
                            triggerAction: 'all',
                            emptyText: l10n('select-a-language'),
                            allowBlank: false,
                            editable: false,
                            resizable: false,
                            minChars: 1,
                            queryMode: 'local',
                            value: 1,
                            width: 450,
                            margin: '0 0 10 10'
                        }, {
                            xtype: 'checkbox',
                            name: 'allowedToParticipateHtml',
                            reference: 'allowedToParticipateHtml',
                            checked: true,
                            fieldLabel: l10n('allowed-to-participate'),
                            labelWidth: 180,
                            tabIndex: 13,
                            boxLabel: l10n('enabled'),
                            listeners: {
                                change: 'allowToParticipateCheck'
                            },
                            labelSeparator: '',
                            width: 450,
                            margin: '0 0 10 10',
                            bind: {
                                disabled: '{isImportedUsed}'
                            },
                            disabledCls: 'user-edit-disabled '
                        }, {
                            xtype: 'checkbox',
                            name: 'enable',
                            reference: 'enable',
                            checked: true,
                            fieldLabel: l10n('status'),
                            boxLabel: l10n('enabled'),
                            width: 450,
                            tabIndex: 14,
                            labelWidth: 180,
                            margin: '0 0 10 10'
                        }/*, {
                            xtype: 'checkbox',
                            name: 'neoRoomPermanentPairingDeviceUser',
                            reference: 'neoRoomPermanentPairingDeviceUser',
                            checked: false,
                            fieldLabel: l10n('neoroom-permanentpairingdevice-user'),
                            boxLabel: l10n('enabled'),
                            width: 450,
                            tabIndex: 14,
                            labelWidth: 180,
                            margin: '0 0 10 10'
                        }*/, userAdditionalInfo]
                    }
                ]
            }],

            buttonAlign: 'center',
            buttons: [{
                xtype: 'button',
                text: l10n('save'),
                name: 'saveUsers',
                tabIndex: 16,
                formBind: true,
                disabled: true,
                handler: 'saveUser'
            }, {
                xtype: 'button',
                text: l10n('close'),
                tabIndex: 17,
                handler: 'closeAddUser'
            }]
        }];
        this.callParent();
    },

    listeners: {
        actioncomplete: 'onActionComplete'
    },

    remoteValidate: function() {
        if (this.textValid) {
            return true;
        } else {
            return this.textInvalid;
        }
    }
});