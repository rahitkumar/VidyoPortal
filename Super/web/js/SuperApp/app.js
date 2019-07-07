/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by
 * Sencha Cmd when upgrading.
 */

function poolSelectionInGUI(pool) {
    SuperApp.getApplication().getController('CommonController').showPoolDetails(pool);
}

function goBackToPoolsInGrid() {
    SuperApp.getApplication().getController('CommonController').goBackToPoolsInGrid();
}

function savePoolCoordinates(newX, newY) {
    SuperApp.getApplication().getController('CommonController').savePoolCoordinates(newX, newY);
}
function toastPopUp(title, message){
this.toastPopUp(title, message, '');
}
function toastPopUp(title, message, alertType) {
    var toastcls = 'toastcls';
    if (alertType && alertType == 'warning') {
        toastcls = 'toastwarningcls';
    }
    var toast = Ext.toast({
        html: message,
        cls: toastcls,
        title: title,
        width: 400,
        autoClose: true,
        closable: true,
        align: 't'
    });
    toast.show();
}

function logoutSuper() {
    var form = Ext.create('Ext.form.Panel', {
        url: 'logout.html',
        method: 'POST',
        standardSubmit: true,
        items: [{
            xtype: 'hidden',
            name: csrfFormParamName,
            value: csrfToken
        }]
    });

    form.getForm().submit();
}

Ext.Loader.setPath('Ext.ux', 'js/SuperApp/ext-js/packages/ux/classic/src');
var initialHistoryToken;
var isFiveOThree = false;

Ext.application({
    name: 'SuperApp',
    appFolder: appFolder,
    extend: 'SuperApp.Application',

    autoCreateViewport: false,

    controllers: ['CommonController'],

    launch: function () {
    	var lang = sessionStorage.getItem('lang');
    	if (lang == 'undefined'){
    		lang = 'en';
    	}
    	var extJSLangURL = 'js/resources/locale/locale-'+ lang + '.js';
    	Ext.Loader.loadScript(extJSLangURL);

        //document.body.removeChild(document.getElementById('loading-ind-div'));
        showWelcomeBanner == "true" ? true : false;

        Ext.override(Ext.window.MessageBox, {
            buttonText: {
                ok: l10n('ok'),
                yes: l10n('yes'),
                no: l10n('no'),
                cancel: l10n('cancel')
            }
        });

        Ext.History.init();

        initialHistoryToken = Ext.History.getToken();

        Ext.apply(Ext.form.VTypes, {
            'IPv4orIPv6': function (value) {
                var ipv6 = /^((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)(\.(25[0-5]|2[0-4]\d|1\d\d|[1-9]?\d)){3}))|:)))(%.+)?$/;

                var ipv4 = /^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?))$/;
                if (ipv6.test(value) == false && ipv4.test(value) == false) {
                    return false;
                }
                return true;
            },
            'IPv4orIPv6Text': l10n('invalid-ipaddress')
        });

        Ext.apply(Ext.form.VTypes, {
            email: function (val,field){
                var re = /^\w+([.!#$%&'*+-/=?^_`{|}~,]\w+)*@\w+([.-]\w+)*\.\w{2,}$/;
                return re.test(val);
            }
        });

        Ext.apply(Ext.form.VTypes, {
            'URLValidate': function (val, field) {
                var test = /[~!`@#$%\^&*\(\)\[\]\{\}\|]$/i;
                if (val.match(test) != null) {
                    field.markInvalid();
                    return false;
                }
                field.clearInvalid();
                return true;
            },
            'URLValidateText': l10n('not-aproper-url')
        });
        Ext.apply(Ext.form.VTypes, {
            'FQDNValidate': function (val) {
                return SuperApp.utils.RegExValidator.validateFQDN(val);

            },
            'FQDNValidateText': l10n('validation-not-a-valid-FQDN')
        });

        Ext.apply(Ext.form.VTypes, {
            'URLRegExValidate': function (val) {
                return SuperApp.utils.RegExValidator.validateURL(val);

            },
            'URLRegExValidateText': l10n('not-aproper-url')
        });

        Ext.override(Ext.form.ComboBox, {
            checkChangeEvents: Ext.isIE ? ['change', 'propertychange', 'keyup'] : ['change', 'input', 'textInput', 'keyup', 'dragdrop']
        });

        Ext.override(Ext.grid.Panel, {
            emptyText: 'No records to display',
            minHeight: this.minHeight ? this.minHeight : 250
        });

        Ext.override(Ext.selection.CheckboxModel, {
            getHeaderConfig: function () {
                var me = this,
                    showCheck = me.showHeaderCheckbox !== false;

                return {
                    isCheckerHd: showCheck,
                    clickTargetName: 'el',
                    width: me.headerWidth,
                    sortable: false,
                    draggable: false,
                    resizable: true,
                    hideable: false,
                    menuDisabled: true,
                    dataIndex: '',
                    tdCls: me.tdCls,
                    cls: showCheck ? Ext.baseCSSPrefix + 'column-header-checkbox ' : '',
                    defaultRenderer: me.renderer.bind(me),
                    editRenderer: me.editRenderer || me.renderEmpty,
                    locked: me.hasLockedHeader()
                };
            }
        });

        /* if (showWelcomeBanner == "true" && window.location.hash == "") {
             var loginHistoryStore = Ext.create('Ext.data.Store', {
                 proxy : {
                     type : 'ajax',
                     url : 'loginhistory.ajax',
                     reader : {
                         type : 'xml',
                         record : 'row',
                         rootProperty : 'dataset'
                     }
                 },
                 fields : ['transactionResult', 'sourceIP', 'transactionTime']
             });

             loginHistoryStore.on('load', function(store, records, success, eOpts) {
                 var historyGrid = Ext.create('Ext.grid.Panel', {
                     columns : [{
                         dataIndex : 'transactionResult',
                         header : 'Result',
                         flex : 1
                     }, {
                         dataIndex : 'sourceIP',
                         header : 'Source Address',
                         flex : 1
                     }, {
                         dataIndex : 'transactionTime',
                         header : 'Time',
                         flex : 1
                     }],
                     store : store
                 });

                 Ext.create('Ext.window.Window', {
                     width : 700,
                     height : 400,
                     closable : false,
                     title : welcomeBannerTitle,
                     autoScroll : true,
                     modal : true,
                     scroll : 'vertical',
                     layout : {
                         type : 'vbox',
                         align : 'stretch'
                     },
                     items : [{
                         xtype : 'fieldset',
                         margin : 5,
                         layout : 'fit',
                         height : 50,
                         items : [{
                             xtype : 'component',
                             padding : 5,
                             html : welcomeBanner
                         }]
                     }, {
                         xtype : 'fieldset',
                         margin : 5,
                         flex : 1,
                         title : 'Your last 5 login attempts',
                         layout : 'fit',
                         items : [historyGrid]
                     }],
                     buttons : [{
                         text : 'Continue',
                         handler : function(btn) {
                             Ext.create('SuperApp.view.main.Main', {
                                 renderTo : Ext.getBody()
                             });
                             btn.up('window').close();
                         }
                     }]
                 }).show();
             });

             loginHistoryStore.load();

         } else {*/
        Ext.create('SuperApp.view.main.Main', {
            renderTo: Ext.get('super-app')
        });
        //}

        Ext.Ajax._defaultHeaders = {
            'X-CSRF-TOKEN': csrfToken
        };

        Ext.override(Ext.data.Connection, {
            onComplete: function (request, xdrResult) {
                var me = this,
                    options = request.options,
                    xhr,
                    result,
                    success,
                    response;

                try {
                    xhr = request.xhr;
                    result = me.parseStatus(xhr.status);
                    //patch for IE
                    if (Ext.isIE && xhr.status == 200 && !xhr.responseXML) {
                        if (xhr.responseText.search('T3MZzeBOLmeyUIO0') != -1) {
                            logoutSuper();
                        }
                    }
                    if (result.success) {
                        // This is quite difficult to reproduce, however if we abort a request just before
                        // it returns from the server, occasionally the status will be returned correctly
                        // but the request is still yet to be complete.
                        result.success = xhr.readyState === 4;
                    }
                } catch (e) {
                    // in some browsers we can't access the status if the readyState is not 4, so the request has failed
                    result = {
                        success: false,
                        isException: false
                    };

                }
                success = me.isXdr ? xdrResult : result.success;
                if (request.xhr.responseURL) {
                    var resURL = request.xhr.responseURL.toString().split('/');
                    if (resURL[resURL.length - 1].split('?')[0] === "login.html") {
                        //window.location = "login.html?lang=en";
                        logoutSuper();
                        return;
                    }
                }

                if (xhr.status === 200) {
                    if (success) {
                        response = me.createResponse(request);
                        me.fireEvent('requestcomplete', me, response, options);
                        Ext.callback(options.success, options.scope, [response, options]);
                    } else {
                        if (result.isException || request.aborted || request.timedout) {
                            response = me.createException(request);
                        } else {
                            response = me.createResponse(request);
                        }
                        me.fireEvent('requestexception', me, response, options);
                        Ext.callback(options.failure, options.scope, [response, options]);
                    }
                    Ext.callback(options.callback, options.scope, [options, success, response]);
                    delete me.requests[request.id];
                    return response;
                }
                if (xhr.status === 302) {
                    window.location = "login.html?lang=en";
                } else if (xhr.status == 0 || xhr.status == 503) {
                    if (options.url.split('?')[0] == "serverstartedtime.ajax") {
                        isFiveOThree = true;
                    }
                } else {
                    response = me.createException(request);
                    Ext.Msg.alert('Failure', 'Internal Server Error');
                    me.fireEvent('requestexception', me, response, options);
                    Ext.callback(options.failure, options.scope, [response, options]);
                    delete me.requests[request.id];
                    return response;
                }
            },

            /**
             * Creates the response object
             * @private
             * @param {Object} request
             */
            createResponse: function (request) {
                var me = this,
                    xhr = request.xhr,
                    isXdr = me.getIsXdr(),
                    headers = {},
                    lines = isXdr ? [] : xhr.getAllResponseHeaders().replace(/\r\n/g, '\n').split('\n'),
                    count = lines.length,
                    line,
                    index,
                    key,
                    response,
                    byteArray;

                while (count--) {
                    line = lines[count];
                    index = line.indexOf(':');
                    if (index >= 0) {
                        key = line.substr(0, index).toLowerCase();
                        if (line.charAt(index + 1) == ' ') {
                            ++index;
                        }
                        headers[key] = line.substr(index + 1);
                    }
                }

                request.xhr = null;
                delete request.xhr;

                response = {
                    request: request,
                    requestId: request.id,
                    status: xhr.status,
                    statusText: xhr.statusText,
                    getResponseHeader: function (header) {
                        return headers[header.toLowerCase()];
                    },
                    getAllResponseHeaders: function () {
                        return headers;
                    }
                };

                if (isXdr) {
                    me.processXdrResponse(response, xhr);
                }

                if (request.binary) {
                    response.responseBytes = me.getByteArray(xhr);
                } else {
                    // an error is thrown when trying to access responseText or responseXML
                    // on an xhr object with responseType of 'arraybuffer', so only attempt
                    // to set these properties in the response if we're not dealing with
                    // binary data
                    response.responseText = xhr.responseText;
                    response.responseXML = xhr.responseXML;
                }

                // If we don't explicitly tear down the xhr reference, IE6/IE7 will hold this in the closure of the
                // functions created with getResponseHeader/getAllResponseHeaders
                xhr = null;
                return response;
            }
        });
        Ext.Ajax.on('requestexception', function (conn, response, options) {
            if (response.status == 302) {
                //window.location.assign("ErrorPage.jsp");
                logoutSuper();
            }
        });
        Ext.Ajax.on('requestcomplete', function (connection, obj1, options, eOpts) {

            try {
            	if (typeof(obj1.request) != 'undefined' && (obj1.request != null)) {
                	var xhr = obj1.request.xhr;
                	if (xhr.responseText.search('T3MZzeBOLmeyUIO0') != -1) {
                    	logoutSuper();
                	}
				}
            }

            catch (e) {
                console.log(e);

            }
        });
    }
    //-------------------------------------------------------------------------
    // Most customizations should be made to SuperApp.Application. If you need to
    // customize this file, doing so below this section reduces the likelihood
    // of merge conflicts when upgrading to new versions of Sencha Cmd.
    //-------------------------------------------------------------------------
});
