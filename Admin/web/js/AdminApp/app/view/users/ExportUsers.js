Ext.define('AdminApp.view.users.ExportUsers', {
    extend: 'Ext.window.Window',
    border: false,
    width: 600,
    minWidth: 600,
    modal: true,
    constrain: true,
    closeAction: 'hide',
    closable: true,
    reference: 'exportUsersWin',
    alias: 'widget.exportusersview',
    resizeable: false,
    title: l10n('export-users'),

    initComponent: function() {
        this.items = [{
            xtype: 'form',
            url: 'exportmembers.ajax',
            standardSubmit: true,
            layout: {
                type: 'vbox',
                align: 'center'
            },
            reference: 'exportUsersForm',
            errorReader: {
                type: 'xml',
                record: 'field',
                model: 'AdminApp.model.Field',
                success: '@success'
            },
            border: false,
            width: '100%',
            items: [{
                xtype: 'fieldset',
                title: l10n('output-file-format'),
                collapsible: false,
                width: '100%',
                margin: 5,
                layout: {
                    type: 'vbox',
                    align: 'stretch'
                },
                items: [{
                    xtype: 'hidden',
                    name: csrfFormParamName,
                    value: csrfToken
                }, {
                    xtype: 'hidden',
                    reference: 'formatvalue',
                    value: 'csv'
                }, {
                    xtype: 'radiogroup',
                    vertical: true,
                    columns: 1,
                    items: [{
                        boxLabel: ".csv" + '<div class="red">' + l10n('user-s-passwords-not-included') + '</div>',
                        name: 'fileformat',
                        inputValue: 'csv',
                        checked: true,
                        listeners: {
                            focus: 'csvFormatSelect'
                        }
                    }, {
                        boxLabel: ".veb" + '<div class="red">' + "The .veb file will be password protected, so it contains hashed passwords of exported user accounts" + '</div>',
                        name: 'fileformat',
                        inputValue: 'veb',
                        listeners: {
                            focus: 'vebFormatSelect'
                        }
                    }]
                }, {
                    xtype: 'panel',
                    reference: 'vebPasswordPanel',
                    hidden: true,
                    bodyStyle: 'background:#F6F6F6;',
                    layout: {
                        type: 'vbox',
                        align: 'left'
                    },
                    fieldDefaults: {
                        labelWidth: 200,
                        anchor: '100%'
                    },
                    items: [{
                        itemId: "password",
                        labelWidth: 200,
                        xtype: 'textfield',
                        inputType: 'password',
                        reference: 'vebPassword',
                        fieldLabel: l10n('password'),
                        name: 'password',
                        emptyText: l10n('password'),
                        validateOnBlur: true,
                        validator: function() {
                            var p1 = this.getValue();
                            var p2 = this.up('form').getForm().findField("password2").getValue();
                            if (p1 == p2) {
                                return true;
                            } else {
                                return l10n("password-not-match");
                            }
                        }
                    }, {
                        itemId: "password2",
                        xtype: 'textfield',
                        labelWidth: 200,
                        inputType: 'password',
                        fieldLabel: l10n('confirm-password'),
                        name: 'password2',
                        emptyText: l10n('confirm-password'),
                        validateOnBlur: true,
                        validator: function() {
                            var p1 = this.getValue();
                            var p2 = this.up('form').getForm().findField("password").getValue();
                            if (p1 == p2) {
                                this.up('form').getForm().findField("password").validate();
                                return true;
                            } else {
                                return l10n("password-not-match");
                            }
                        }
                    }]
                }]

            }],
            buttonAlign: 'center',
            buttons: [{
                xtype: 'button',
                text: l10n('export'),
                handler: 'exportUsers',
                formBind: true,
                disabled: true
            }, {
                xtype: 'button',
                text: l10n('close'),
                handler: 'exportMembersClose'
            }]

        }];
        this.callParent();
    }
});