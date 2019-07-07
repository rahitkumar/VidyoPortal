/**
 * @class QualityService
 */
Ext.define('AdminApp.view.settings.qos.QualityService', {
    extend: 'Ext.form.Panel',
    alias: 'widget.qualityservice',

    reference: 'qualityservice',
    requires: [
        'AdminApp.view.settings.qos.QualityServiceViewModel',
        'AdminApp.view.settings.qos.QualityServiceController'
    ],

    border: false,
    height: '100%',
    viewModel: {
        type: 'QualityServiceViewModel'
    },
    title: {
        text: '<span class="header-title">' + l10n('endpoint-network-settings') + '</span>',
        textAlign: 'center'
    },

    controller: 'QualityServiceController',


    buttonAlign: 'center',
    trackResetOnLoad: true,
    initComponent: function() {
        var me = this,
            rec = me.fieldRec;

        var proxyConfigState = Ext.create('Ext.data.Store', {
            fields: ['abbr', 'name'],
            data : [
                {"abbr":"0", "name": l10n('auto-detect-recommended') },
                {"abbr":"1", "name": l10n('always') }
            ]
        });

        me.items = [{
            xtype: 'fieldset',
            width: '90%',
            bodyStyle: 'padding: 10px',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left'
            },
            title: l10n('admin-dscpconfig-leftmenu-label'),
            items: [
                {
                    xtype: 'panel',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    bodyStyle: 'background-color: #f6f6f6',
                    defaults: {
                        border: 0,
                        margin: 5,
                        padding: 0
                    },
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: l10n('admin-dscpconfig-label-video'),
                        name: 'mediavideo',
                        labelWidth: 250,
                        maxValue: 63,
                        minValue: 0,
                        labelValue: 'hexVideoDSCP',
                        allowExponential: false,
                        allowBlank: false,
                        listeners: {
                            change: 'onChangeDSCPValue'
                        }
                    }, {
                        xtype: 'label',
                        width: 50,
                        bind: {
                            html: '{hexVideoDSCP}'
                        }

                    }]
                },
                {
                    xtype: 'panel',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    bodyStyle: 'background-color: #f6f6f6',
                    defaults: {
                        border: 0,
                        margin: 5,
                        padding: 0
                    },
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: l10n('admin-dscpconfig-label-audio'),
                        name: 'mediaaudio',
                        labelValue: 'hexAudioDSCP',
                        labelWidth: 250,
                        allowExponential: false,
                        allowBlank: false,
                        maxValue: 63,
                        minValue: 0,
                        listeners: {
                            change: 'onChangeDSCPValue'
                        }
                    },
                        {
                            xtype: 'label',
                            width: 50,
                            bind: {
                                html: '{hexAudioDSCP}'
                            }

                        }]
                },
                {
                    xtype: 'panel',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    bodyStyle: 'background-color: #f6f6f6',
                    defaults: {
                        border: 0,
                        margin: 5,
                        padding: 0
                    },
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: l10n('admin-dscpconfig-label-content'),
                        name: 'mediadata',
                        labelWidth: 250,
                        labelValue: 'hexContentDSCP',
                        allowExponential: false,
                        allowBlank: false,
                        maxValue: 63,
                        minValue: 0,
                        listeners: {
                            change: 'onChangeDSCPValue'
                        }
                    },
                        {
                            xtype: 'label',
                            width: 50,
                            bind: {
                                html: '{hexContentDSCP}'
                            }

                        }]
                },
                {
                    xtype: 'panel',
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    bodyStyle: 'background-color: #f6f6f6',
                    defaults: {
                        border: 0,
                        margin: 5,
                        padding: 0
                    },
                    items: [{
                        xtype: 'numberfield',
                        fieldLabel: l10n('admin-dscpconfig-label-signaling'),
                        name: 'signaling',
                        maxValue: 63,
                        minValue: 0,
                        labelWidth: 250,
                        labelValue: 'hexSignalDSCP',
                        allowExponential: false,
                        allowBlank: false,
                        listeners: {
                            change: 'onChangeDSCPValue'
                        }
                    },
                        {
                            xtype: 'label',
                            width: 50,
                            bind: {
                                html: '{hexSignalDSCP}'
                            }

                        }]
                }
            ]
        }, {
            xtype: 'fieldset',
            width: '90%',

            bodyStyle: 'padding: 10px',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left'
            },
            title: l10n('media-port-range') ,

            items: [
                {
                    xtype: 'numberfield',
                    fieldLabel: l10n('minimum-port'),
                    name: 'minMediaPort',
                    labelWidth: 250,
                    allowExponential: false,
                    allowDecimals: false,
                    allowBlank: false,
                    maxValue: 65535,
                    minValue: 1025
                }, {
                    xtype: 'numberfield',
                    fieldLabel: l10n('maximum-port'),
                    name: 'maxMediaPort',
                    maxValue: 65535,
                    minValue: 1025,
                    labelWidth: 250,
                    allowExponential: false,
                    allowDecimals: false,
                    allowBlank: false
                }
            ]
        }, {
            xtype: 'fieldset',
            width: '90%',
            hidden:true,
            bodyStyle: 'padding: 10px',
            layout: {
                type: 'vbox',
                align: 'center',
                pack: 'left'
            },
            title: "VidyoProxy" ,

            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: l10n('use-vidyoproxy'),
                    name: "alwaysUseVidyoProxy",
                    displayField: 'name',
                    valueField: 'abbr',
                    width: 400,
                    store: proxyConfigState,
                    allowBlank: false,
                    autoSelect: true,
                    forceSelection: true,
                    editable: false,
                    queryMode: 'local'
                }
            ]
        }];

        me.buttons = [{
            text: l10n('save'),
            id:'endpointSettings-save-button',
            formBind: true,
            listeners: {
                click: 'onclickSaveQualityService'
            }
        }, {
            text: l10n('cancel'),
            handler:  function() {
                this.up('form').getForm().reset();
            }
        }];
        me.callParent(arguments);
    },
    listeners: {
        dirtychange: function(form,isDirty) {
            //bug in ext js when you combine formbind and dirtycheck so that we manually checking dirty and validation check
            if(isDirty && form.isValid()){
                Ext.getCmp('endpointSettings-save-button').enable();
            }else{
                Ext.getCmp('endpointSettings-save-button').disable();
            }
        },

        afterRender: function() {
            //bug in ext js.. if you enable formbind=true, it enables the button when you load page.
            //without a delay, you cant never disable it. this is acceptable as per the ext js doc.
            setTimeout(function() {
                Ext.getCmp('endpointSettings-save-button').disable();
            }, 100);

        }
    }

});