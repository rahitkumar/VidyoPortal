Ext.define('AdminApp.view.settings.customization.InviteText', {
    extend : 'Ext.form.Panel',
    width : '100%',
    alias : 'widget.invitetext',
    reference : 'invitetext',
    requires : ['AdminApp.view.settings.customization.CustomizationViewModel', 'AdminApp.view.settings.customization.CustomizationController'],
    viewModel : {
        type : 'CustomizationViewModel'
    },
    height:800,
    controller : 'CustomizationController',

    layout : {
        // layout-specific configs go here
        type : 'accordion',
        titleCollapse : true,
        animate : true,
        activeOnTop : false
    },
    title : {
        text : '<span class="header-title">' + l10n('invite-text') + '</span>',
        textAlign : 'center'
    },
    initComponent : function() {
        var me = this,
            LINK_KEYWORD = "[ROOMLINK]",
            VIDYOROOMLINK_KEYWORD = "[VIDYOROOMLINK]",
            EXT_ONLY_KEYWORD = "[EXTENSION_ONLY]",
            LEGACY_URI_KEYWORD = "[LEGACY_URI]",
            DIALSTRING_KEYWORD = "[DIALSTRING]",
            DIALIN_NUMBER_KEYWORD = "[DIALIN_NUMBER]",
            INTERNATIONAL_DIALIN_KEYWORD = "[INTERNATIONAL_DIALIN]",
            WEBCASTURL_KEYWORD = "[WEBCASTURL]",
            WEBCASTPIN_KEYWORD = "[WEBCASTPIN]",
            PIN_ONLY_KEYWORD = "[PIN_ONLY]",
            AVATAR_KEYWORD = "[AVATAR]",
            USER_TITLE_KEYWORD = "[USER_TITLE]",
            USER_DISPLAYNAME_KEYWORD = "[USER_DISPLAYNAME]",
            ROOMNAME_KEYWORD = "[ROOMNAME]",
            TENANT_LOGO_KEYWORD = "[TENANT_LOGO]";

        Ext.form.field.TextArea.override({
            insertAtCursor : function(v) {
                if (Ext.isIE) {
                    this.el.focus();
                    var sel = document.selection.createRange();
                    sel.text = v;
                    sel.moveEnd('character', v.length);
                    sel.moveStart('character', v.length);
                } else {
                    var textareaDOM = this.el.down('textarea').dom;
                    var startPos = textareaDOM.selectionStart;
                    var endPos = textareaDOM.selectionEnd;
                    textareaDOM.value = textareaDOM.value.substring(0, startPos) + v + textareaDOM.value.substring(endPos, textareaDOM.value.length);
                    textareaDOM.focus();
                    textareaDOM.setSelectionRange(endPos + v.length, endPos + v.length);
                }
            }
        });

        me.items = [{
            title : l10n('email-content-text'),
            tools : [{
                //cls:"x-btn-icon",
                type : 'dialin_number_icon',
                tooltip : '<b>' + l10n('insert-dialin-number') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContent]').insertAtCursor(DIALIN_NUMBER_KEYWORD);
                    }
                }

            }, {
                type : 'pin_only_icon',
                tooltip : '<b>' + l10n('insert-pin-only') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContent]').insertAtCursor(PIN_ONLY_KEYWORD);
                    }
                }
            }, {
                type : 'extension_only_icon',
                tooltip : '<b>' + l10n('insert-extension-only') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContent]').insertAtCursor(EXT_ONLY_KEYWORD);
                    }
                }
            }, {
                type : 'room_link_icon',
                tooltip : '<b>' + l10n('room-link') + '</b><br/>' + l10n('required'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContent]').insertAtCursor(LINK_KEYWORD);
                    }
                }
            }, {
                type : 'sip_link_icon',
                tooltip : '<b>' + l10n('insert-sip-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContent]').insertAtCursor(LEGACY_URI_KEYWORD);
                    }
                }
            }],
            items : [{
                xtype : 'textareafield',
                name : 'invitationEmailContent',
                width : '100%',
                height : '100%',
                grow : true,
                maxLength : 1300,
                bind : {
                    value : '{invitationEmailContent}'
                }
            }]
        }, {
            title : l10n('email-content-html'),
            tools : [{
                //cls:"x-btn-icon",
                type : 'dialin_number_icon',
                tooltip : '<b>' + l10n('insert-dialin-number') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(DIALIN_NUMBER_KEYWORD);
                    }
                }

            }, {
                type : 'pin_only_icon',
                tooltip : '<b>' + l10n('insert-pin-only') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(PIN_ONLY_KEYWORD);
                    }
                }
            }, {
                type : 'extension_only_icon',
                tooltip : '<b>' + l10n('insert-extension-only') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(EXT_ONLY_KEYWORD);
                    }
                }
            }, {
                type : 'room_link_icon',
                tooltip : '<b>' + l10n('room-link') + '</b><br/>' + l10n('required'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(LINK_KEYWORD);
                    }
                }
            }, {
                type : 'sip_link_icon',
                tooltip : '<b>' + l10n('insert-sip-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(LEGACY_URI_KEYWORD);
                    }
                }
            }, {
                type : 'avatar_icon',
                tooltip : '<b>' + l10n('avatar-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(AVATAR_KEYWORD);
                    }
                }
            }, {
                type : 'user_title_icon',
                tooltip : '<b>' + l10n('user-title-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(USER_TITLE_KEYWORD);
                    }
                }
            }, {
                type : 'user_displayname_icon',
                tooltip : '<b>' + l10n('user-displayname-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(USER_DISPLAYNAME_KEYWORD);
                    }
                }
            }, {
                type : 'room_name_icon',
                tooltip : '<b>' + l10n('room-name-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(ROOMNAME_KEYWORD);
                    }
                }
            }, {
                type : 'tenant_logo_icon',
                tooltip : '<b>' + l10n('tenant-logo-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(TENANT_LOGO_KEYWORD);
                    }
                }
            }, {
                type : 'international_dialin_icon',
                tooltip : '<b>' + l10n('international-dialin-link') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=invitationEmailContentHtml]').insertAtCursor(INTERNATIONAL_DIALIN_KEYWORD);
                    }
                }
            }],
            items : [{
                xtype : 'textareafield',
                name : 'invitationEmailContentHtml',
                grow : true,
             	enableFont: false,
             	enableLinks: false,
                fieldLabel: '',
                width: '100%',
                growMin: 300,
                height : 400,
                bind : {
                    value : '{invitationEmailContentHtml}'
                }
            }]
        }, {
            title : l10n('voice-only'),
            tools : [{
                type : 'dialin_number_icon',
                tooltip : '<b>' + l10n('insert-dialin-number') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=voiceOnlyContent]').insertAtCursor(DIALIN_NUMBER_KEYWORD);
                    }
                }

            }, {
                type : 'pin_only_icon',
                tooltip : '<b>' + l10n('insert-pin-only') + '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=voiceOnlyContent]').insertAtCursor(PIN_ONLY_KEYWORD);
                    }
                }
            }, {
                type : 'extension_only_icon',
                tooltip : '<b>' + l10n('insert-extension-only')+ '</b><br/>' + l10n('optional'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=voiceOnlyContent]').insertAtCursor(EXT_ONLY_KEYWORD);
                    }
                }
            }, {
                type : 'meeting_link_icon',
                tooltip : '<b>' + l10n('insert-dial-string') + '</b><br/>' + l10n('required'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=voiceOnlyContent]').insertAtCursor(DIALSTRING_KEYWORD);
                    }
                }
            }],
            items : [{
                xtype : 'textareafield',
                name : 'voiceOnlyContent',
                height : '100%',
                width : '100%',
                grow : true,
                maxLength : 1300,
                bind : {
                    value : '{voiceOnlyContent}'
                }
            }]
        }, {
            title : l10n('webcast'),
            tools : [{
                type : 'webcast_icon',
                tooltip : '<b>' + l10n('webcast-url') + '</b><br/>' + l10n('required'),
                listeners : {
                    click : function(v) {
                        me.down('textareafield[name=webcastContent]').insertAtCursor(WEBCASTURL_KEYWORD);
                    }
                }

            }],
            items : [{
                xtype : 'textareafield',
                name : 'webcastContent',
                width : '100%',
                height : '100%',
                grow : true,
                maxLength : 1300,
                bind : {
                    value : '{webcastContent}'
                }
            }]
        }, {
            title : l10n('email-subject'),
            items : [{
                xtype : 'textfield',
                width : '100%',
                hideLabel : true,
               
                maxLength : 1300,
                name : 'invitationEmailSubject',
                bind : {
                    value : '{invitationEmailSubject}'
                }
            }]
        },{
                    title : l10n('dial-in-numbers'),
                           
                        name : 'Dial-In Numbers',
                          
    
                    layout : {
                        type : 'fit',
                        align : 'stretch'
                    },
                    items : [{
                        xtype : 'grid',
                        title: l10n('dial-in'),
                        reference:'dialInGrid',
                        gridDirtyFlag:false,
                        bind: {
        		        store: '{DialInStore}'
        	            },
      
                        plugins: {
      	        ptype: 'rowediting',
      	        clicksToEdit: 2
		    },

               listeners: {
                   	 edit: function(editor, context, eOpts){
                     var comboStr= Ext.getStore('dialInComboStore');

                     //fix for reset the combobox value when you update the grid
                     if(context.colIdx==0 || context.record.get('countryID')==0){
                           var comboRec =comboStr.findRecord('countryID',context.record.get('name'));       
                           context.record.set('phoneCode',  comboRec.get('phoneCode'));
                           context.record.set('name',  comboRec.get('name'));
                           context.record.set('countryID',  comboRec.get('countryID'));
                     }else{                   
                          var comboRec =comboStr.findRecord('countryID',context.record.get('countryID')); 
                          if(comboRec){ 
                            context.record.set('phoneCode',  comboRec.get('phoneCode'));
                            context.record.set('name',  comboRec.get('name'));
                            context.record.set('countryID',  comboRec.get('countryID'));
                          }
                      }
            
                   },
					
                   //????????? I need that just for 'Add new record' button handler
                   canceledit : function ( editor, context, eOpts ){
                      var grid = context.grid;
                       var store = grid.getStore();
                       var record = grid.getSelectionModel().getSelection()[0];
                       if(!record.get('countryID') ){
                            store.remove(record);
                       }
                      }
                   },
             
    
         columns: [{
                header: l10n('country'),
                menuDisabled: true,
                dataIndex: 'name',
                minWidth: 250,
      
       /* renderer : function(value, mataData, record, rowIndex, colIndex, store, view) {
            console.log(value + mataData +record + rowIndex +colIndex + store+view );

                   if(Ext.isNumeric(value)){
                    var comboStore =  view.getStore('DialInComboStore');
                    var country = comboStore.findRecord('countryId',value);
                    return country.get('countryName');
                   }else{
                       return value;
                   }
        },*/
                editor: {
                    xtype: 'combo',
                    queryMode: 'local',
                    valueField: 'countryID',
                    autoSizeColumn: true,
                    allowBlank: false,
                    forceSelection:false,
                    displayField: 'name',
                    minChars: 2,
                    typeAhead: true,
                    bind: {
                        store: '{DialInComboStore}'
                    }
                }
            }, {
                header: l10n('country-code'),
                dataIndex: 'phoneCode',
                menuDisabled: true,
                width: 130,
                align: 'right',
                editor: {
                    xtype: 'numberfield',
                    disabled:true,
                    allowDecimal:false,
                     hideTrigger: true,
                }
            }, {
                header: l10n('dial-in-number'),
                dataIndex: 'dialInNumber',
                menuDisabled: true,
                width: 130,
                align: 'right',
                editor: {
                    xtype: 'textfield',
                    allowBlank: false,                   
                 
                     hideTrigger: true,
                }
            }, {
                header: l10n('dial-label'),
                dataIndex: 'dialInLabel',
                menuDisabled: true,
                width: 130,
                align: 'right',
                editor: {
                    xtype: 'textfield',
                    allowBlank: true,                
                     hideTrigger: true,
                }
            }],
        selModel: {
                type: 'cellmodel'
            },
            tbar: [{
                text: l10n('add'),
                iconCls: 'icon-add',
                handler: 'onAddClick'
            },{
                text: l10n('remove'),
                iconCls: 'icon-remove',       
                handler: 'onRemoveClick'
            }]}]
                   }],

        me.buttons = ['->', {
            text : l10n('save'),
            listeners : {
                click : 'onClickSaveInviteText'
            }
        }, {
            text : l10n('default'),
            type : 'default',
            listeners : {
                click : 'onClickSaveInviteText'
            }
        }, '->'];

        me.callParent(arguments);
    }
}); 