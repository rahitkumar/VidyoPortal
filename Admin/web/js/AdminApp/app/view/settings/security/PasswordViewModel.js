Ext.define('AdminApp.view.settings.security.PasswordViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.PasswordViewModel',
    
    data : {
        minPINLength : 6,
        sessionExpPeriod:0
    }
});