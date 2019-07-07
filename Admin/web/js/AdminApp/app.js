/*
 * This file is generated and updated by Sencha Cmd. You can edit this file as
 * needed for your application, but these edits will have to be merged by
 * Sencha Cmd when upgrading.
 */

function logoutAdmin() {
    var logout = Ext.create('Ext.form.Panel', {
        url : 'logout.html',
        method : 'POST',
        standardSubmit : true,
        items : [{
            xtype : 'hidden',
            name : csrfFormParamName,
            value : csrfToken
        }]
    });

    logout.getForm().submit();
}

var initialHistoryToken;

Ext.Loader.setPath('Ext.ux', 'js/AdminApp/ext-js/packages/ux/classic/src');

Ext.application({
    name : 'AdminApp',
    appFolder : appFolder,
    extend : 'AdminApp.Application',

    autoCreateViewport : false,

    launch : function() {
    	var lang = sessionStorage.getItem('lang');
    	if (lang == 'undefined'){
    		lang = 'en';
    	}
    	var extJSLangURL = 'js/resources/locale/locale-'+ lang + '.js';
    	Ext.Loader.loadScript(extJSLangURL);
        Ext.override(Ext.layout.container.Box, {
            roundFlex : function(width) {
                return Math.round(width);
            }
        });

        Ext.apply(Ext.form.VTypes, {
            'URLRegExValidate': function (val) {
                return AdminApp.utils.RegExValidator.validateURL(val);
               
            },
            'URLRegExValidateText': l10n('not-aproper-url')
        });

        Ext.apply(Ext.form.VTypes, {
            email: function (val,field){
                var re = /^\w+([.!#$%&'*+-/=?^_`{|}~,]\w+)*@\w+([.-]\w+)*\.\w{2,}$/;
                return re.test(val);
            }
        });
        
        Ext.override(Ext.form.ComboBox, {
            checkChangeEvents : Ext.isIE ? ['change', 'propertychange', 'keyup'] : ['change', 'input', 'textInput', 'keyup', 'dragdrop']
        });

        Ext.override(Ext.selection.CheckboxModel, {
            onHeaderClick : function(headerCt, header, e) {
                if (!this.showHeaderCheckbox) {
                    return false;
                }
                if (header === this.column) {
                    e.stopEvent();
                    var me = this,
                        isChecked = header.el.hasCls(Ext.baseCSSPrefix + 'grid-hd-checker-on');

                    if (isChecked) {
                        me.deselectAll();
                    } else {
                        me.selectAll();
                    }
                }
            },
            getHeaderConfig : function() {
                var me = this,
                    showCheck = me.showHeaderCheckbox !== false;

                return {
                    isCheckerHd : showCheck,
                    clickTargetName : 'el',
                    width : me.headerWidth,
                    sortable : false,
                    draggable : false,
                    resizable : true,
                    hideable : false,
                    menuDisabled : true,
                    dataIndex : '',
                    tdCls : me.tdCls,
                    cls : showCheck ? Ext.baseCSSPrefix + 'column-header-checkbox ' : '',
                    defaultRenderer : me.renderer.bind(me),
                    editRenderer : me.editRenderer || me.renderEmpty,
                    locked : me.hasLockedHeader()
                };
            }
        });

        Ext.History.init();

        initialHistoryToken = Ext.History.getToken();

        Ext.create('AdminApp.view.main.Main', {
            renderTo : Ext.getBody()
        });

        Ext.Ajax._defaultHeaders = {
            'X-CSRF-TOKEN' : csrfToken
        };

        Ext.override(Ext.data.Connection, {
            onComplete : function(request, xdrResult) {
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
                            logoutAdmin();
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
                        success : false,
                        isException : false
                    };

                }
                success = me.isXdr ? xdrResult : result.success;
                if (request.xhr.responseURL) {
                    var resURL = request.xhr.responseURL.toString().split('/');
                    if (resURL[resURL.length -1 ].split('?')[0] === "login.html") {
                        window.location = "login.html?lang=en";
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
                } else {
                    response = me.createException(request);
                    Ext.Msg.alert('Failure', 'Internal Server Error');
                    me.fireEvent('requestexception', me, response, options);
                    Ext.callback(options.failure, options.scope, [response, options]);
                    delete me.requests[request.id];
                    return response;
                }
            }
        });
           Ext.Ajax.on('requestcomplete', function(connection, obj1, options, eOpts) {

            try{
            	if (typeof(obj1.request) != 'undefined' && (obj1.request != null)) {
                	var xhr = obj1.request.xhr;
                 	//coming from login_html.jsp
                 	if (xhr.responseText.search('T3MZzeBOLmeyUIO0') != -1) {
                            logoutAdmin();
                	}
            	}
            }
              
           catch (e) {
                    console.log(e);

                }
             });
    }
    //-------------------------------------------------------------------------
    // Most customizations should be made to AdminApp.Application. If you need to
    // customize this file, doing so below this section reduces the likelihood
    // of merge conflicts when upgrading to new versions of Sencha Cmd.
    //-------------------------------------------------------------------------

});

//Undo sencha's logic 
//Needed for top nav buttons to not open links in new tabs/windows when clicked in IE11 EXTJS-13775
//Firefox 52 is getting detected now as ALWAYS having pointer events
//chromeOS causing issues too
//unit tests failing
if (Ext.isIE || Ext.isEdge || (Ext.firefoxVersion >= 52) || Ext.os.is.ChromeOS || window.inUnitTest) {
	//sorry windows mobile phones...
	var eventMap = Ext.dom.Element.prototype.eventMap;
	eventMap.click = 'click';
	eventMap.dblclick = 'dblclick';
}