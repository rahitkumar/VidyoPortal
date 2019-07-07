Ext.define('SuperApp.view.main.WelcomeWindow', { 
    extend : 'Ext.window.Window',
    xtype : 'welcomeWindow',
    closeAction: 'hide',
    modal: true,
    frame: true,
    title: l10n('welcome') + ' ' + superusername,
    animCollapse: true,
    width: 580,
    height: 485,
    closable: true,
    shadowOffset: 30,
    layout: 'fit',
    constrain: true,
    resizable: false,
    items: [{
        xtype: 'panel',
        itemId: 'welcomebannerpnl',
        cls: 'login-form',
        maxWidth: 700,
        closable: false,
        
        
        scrollable: true,
        modal: true,
        layout: {
            type: 'vbox',
            align: 'stretch'
        },
        items: [{
            xtype: 'fieldset',
            margin: 5,
            layout: 'anchor',
            scrollable: 'vertical',
            height: 50,
            items: [{
                xtype: 'component',

                padding: 5,
                html: welcomeBanner
            }]
        }, {
            xtype: 'grid',
            title: l10n('your-last-5-login-attempts'),
            columns: [{
                dataIndex: 'transactionResult',
                header: 'Result',
                flex: 1
            }, {
                dataIndex: 'sourceIP',
                header: 'Source Address',
                flex: 1
            }, {
                dataIndex: 'transactionTime',
                header: 'Time',
                flex: 1
            }],
            bind: {
                store: '{loginHistoryStore}'
            }

        }, {
            xtype: 'label',
            text: '',
            id: 'passwordExpiryLabel',
            margin: '12 5 0 170',
            cls: 'bold-label-style',
            style: {
                'font-weight': 'bold',
                'background-color': '#FFFFFF',
                'color': '#FF0000'
            }
        }],
        buttons: [{
            text: 'Continue',
            handler: function(btn) {
             
               this.up('window').close();
            }
        }]
    }]
});
