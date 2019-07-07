/**
 * 
 */
Ext.define('SuperApp.view.settings.superaccounts.AddSuperAcct', {
	extend : 'Ext.window.Window',
    alias : 'widget.addsuperview',
    border : false,
    width : 700,
    modal: true,
    frame: true,
    constrain: true,
    closeAction: 'destroy',
    closable: true,
    reference:'addSuperUserWin',
    resizeable:false,
    title : {
        text : '<span class="header-title">' + l10n('add-super-account-new-user') + '</span>',
        textAlign : 'center'
    },
    initComponent : function() {
    	var me = this;
        Ext.apply(Ext.form.VTypes, {
            passwordMatch : function(val, field) {
                if (field.initialPassField) {
                    var pwd = me.down('textfield[itemId=' + field.initialPassField + ']');
                    return (val == pwd.getValue());
                }
                return true;
            },
            passwordMatchText : l10n('password-not-match'),

            oldPasswordMatch : function(val, field) {
                var oldpassword = me.down('textfield[name=oldpassword]').getValue(),
                    params = {
                    field : 'oldpassword',
                    value : oldpassword
                };
                Ext.Ajax.request({
                    url : "validatesuperpassword.ajax",
                    params : params,
                    success : function(response, request) {
                        var passwordMatch = Ext.JSON.decode(response.responseText);
                        if (!passwordMatch['valid']) {
                            field.markInvalid(passwordMatch["reason"]);
                            me.down('button[name=savesuperaccounts]').setDisabled(true);
                        }
                    }
                });
                return true;
            },
            oldPasswordMatchText : l10n('old-password-do-not-match')
        });
        me.items = [
                 {
                 xtype : 'form',
                 width : '100%',
                 errorReader : {
                     type : 'xml',
                     record : 'field',
                     model : 'SuperApp.model.settings.Field',
                     success : '@success'
                 },
                 reader : {
                     type : 'xml',
                     totalRecords : 'results',
                     record : 'row',
                     id : 'memberID',
                     model : 'SuperApp.model.settings.SuperAccounts'
                 },
                 border : false,
                 reference : 'addSuperAccountForm',
                 items : [{
                     xtype : 'hiddenfield',
                     name : 'memberID',
                     value : 0
                 },{
                     xtype : 'fieldset',
                     padding : 5,
                     width : '100%',
                     layout : {
                     	type : 'vbox',
                     	align : 'center',
                     	pack: 'center'
                     },
                     items : [{
                         xtype : 'textfield',
                         fieldLabel : '<span class="red-label">*</span><span>' + l10n('SuperAccount-user-name') + '<span>',
                         name : 'username',
                         maxLength : 80,
     	                 width: 500,
     	                 labelWidth: 175,
                         enforceMaxLength : true,
                         validateOnChange : false,
                         labelAlign : 'right',
                         validator : function() {
                        	 return this.validFlag;
                         },
                         listeners : {
                             change : 'remoteValidateUserName'
                         },
                         allowBlank : false,
                         bind : {
                             readOnly : '{usernameReadOnly}'
                         },
                         msgTarget : 'under'
                     },{
                         xtype : 'textfield',
                         fieldLabel : '<span class="red-label">*</span><span>' + l10n('SuperAccount-password') + '</span>',
                         name : 'password1',
                         itemId : 'password1',
                         inputType : 'password',
                         allowBlank : false,
     	                 width: 500,
     	                 labelWidth: 175,
     	                 labelAlign : 'right',
                         maskRe : /[^ ]/,
                         msgTarget : 'under'
                     }, {
                         xtype : 'textfield',
                         fieldLabel : '<span class="red-label">*</span><span>' + l10n('SuperAccount-verify-password') + '</span>',
                         inputType : 'password',
                         name : 'password2',
                         itemId : 'password2',
                         msgTarget : 'under',
                         vtype : 'passwordMatch',
                         initialPassField : 'password1',
                         allowBlank : false,
     	                 width: 500,
     	                 labelWidth: 175,
     	                 labelAlign : 'right',
                         maskRe : /[^ ]/,
                         msgTarget : 'under'
                     }, {
                         xtype : 'textfield',
                         fieldLabel : '<span class="red-label">*</span><span>' + l10n('SuperAccount-full-name') + '</span>',
                         name : 'memberName',
                         maxLength : 80,
                         allowBlank : false,
     	                 width: 500,
     	                 labelWidth: 175,
     	                 labelAlign : 'right',
                         msgTarget : 'under'
                     }, {
                         xtype : 'textfield',
                         fieldLabel : '<span class="red-label">*</span><span>' + l10n('SuperAccount-email-address') + '</span>',
                         vtype : 'email',
                         name : 'emailAddress',
                         allowBlank : false,
     	                 width: 500,
     	                 labelWidth: 175,
     	                 labelAlign : 'right',
                         msgTarget : 'under'
                     }, {
                         xtype : 'combobox',
                         fieldLabel : '<span class="red-label">*</span><span>' + l10n('language-preference') + '</span>',
                         labelAlign : 'right',
                         labelWidth: 175,
                         width: 500,
                         name : 'langID',
                         reference : 'cGuidelocation',
                         bind : {
                             store : '{saLocation}'
                         },
                         emptyText : l10n('select-a-language'),
                         valueField : 'langID',
                         displayField : 'langName',
                         triggerAction : 'all',
                         allowBlank : true,
                         editable : true,
                         selectOnFocus : true,
                         resizable : false,
                         minChars : 1,
                         allowBlank : false
                     }, {
                         xtype : 'textareafield',
                         fieldLabel : l10n('description'),
                         maxLength : 65534,
                         name : 'description',
                         labelAlign : 'right',
     	                 width: 500,
     	                 labelWidth: 175,
                         msgTarget : 'under'
                     }, {
                         xtype : 'checkbox',
                         fieldLabel : l10n('enable'),
                         name : 'enable',
                         value : true,
                         labelAlign : 'right',
                         labelWidth: 175
                     }]
                 }],
                 buttonAlign : 'center',
                 buttons : [
                    {
                     text : l10n('save'),
                     name : 'savesuperaccounts',
                     reference : 'savesuperaccounts',
                     formBind : true,
                     handler :'onSaveSuperAccount'
                 }, {
                     text : l10n('cancel'),
                     handler : 'cancelAddSuperAcct'
                 }]
             }];
        this.callParent();
    }
	
});
