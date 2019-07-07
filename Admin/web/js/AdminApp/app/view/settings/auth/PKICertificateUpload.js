Ext.define('AdminApp.view.settings.auth.PKICertificateUpload', {
    extend: 'Ext.window.Window',
    xtype: 'pkicertificate-upload',
  
    width: 600,
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    

    modal: true,
   bind: {
        title: '{title}'
    },
      initComponent: function() {
        var me = this;
me.items=[{
        xtype: 'form',
        reference:'crtuploadform',
        layout: 'anchor',
      bodyPadding: 10,
     errorReader: {
                type: 'xml',
                record: 'field',
                model: 'AdminApp.model.Field',
                success: '@success'
            },

            reader: {
                type: 'xml',
                success: '@success'
            },

        defaults: {
            anchor: '100%'
        },
   

    items: [{
        xtype: 'filefield',
        hideLabel: true,
        reference: 'certificatefilefield',
        name:'certificatefilefield',
        emptyText : 'Select a pem chain file.',
        allowBlank:false,
    }],
    buttons: [
        {

            text: 'Upload',
            handler: 'uploadCACCertificate',
            actionType:me.actionType,
            title:me.title,
            formBind:true,
        }, {

            text: 'cancel',
            handler: function () { this.up('window').close(); }


        }
    ]
}];
  me.callParent(arguments);
      }
});