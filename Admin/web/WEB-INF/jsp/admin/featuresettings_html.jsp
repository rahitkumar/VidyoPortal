<%@ include file="header_html.jsp" %>

<div id="maincontent">

    <div id="content-panel">&nbsp;</div>

</div>

<%@ include file="footer_html.jsp" %>

<script type='text/javascript' src='<c:url value="/js/ComboLoadValue.js"/>'></script>

<script type="text/javascript">

    Ext.onReady(function(){
        Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';
        Ext.QuickTips.init();
        Ext.form.Field.prototype.msgTarget = 'under';

        var msg = function(title, msg, callback){
            Ext.Msg.show({
                title: title,
                msg: msg,
                minWidth: 200,
                modal: true,
                icon: Ext.Msg.INFO,
                buttons: Ext.Msg.OK,
                fn: callback
            });
        };

        var localmenu = new Ext.Panel({
            collapsible: false,
            autoHeight: true,
            border: false,
            frame: false,
            autoLoad: {url: '<c:url value="menu_content.html?settings=1&featuresettings=1"/>'}
        });


        /* VidyoWeb START */

        var vidyoWebForm = new Ext.form.FormPanel({
            autoWidth: true,
            autoHeight: true,
            border: true,
            frame: true,
            labelAlign: 'side',
            labelWidth: 230,
            errorReader: new Ext.data.XmlReader({
                        record : 'field',
                        success: '@success'
                    },[
                'id', 'msg'
            ]
            ),
            defaults: {
                labelStyle: 'margin:0 0 0 -11px;font-weight:bold;text-align:right;'
            },
            items:[{
                xtype: 'textfield',
                fieldLabel: 'Version',
                readOnly: true,
                width: 300,
                value: '<c:out value="${model.vidyoWebVersion}"/>'
            },{
                xtype: 'radiogroup',
                id: 'vidyoWebEnabledGroup',
                name: 'vidyoWebEnabledGroup',
                hidden: false,
                fieldLabel: '<spring:message javaScriptEscape="true" code="vidyoweb.admin.enable"/>',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message code="enabled"/>', id:'vidyoWebGroupEnable', name:'vidyoWebEnabledGroup', inputValue: 'enabled', checked:false},
                    {boxLabel: '<spring:message code="disabled"/>', id:'vidyoWebGroupDisable',  name:'vidyoWebEnabledGroup', inputValue: 'disabled', checked:false}
                ],
                listeners: {
                    change: function(radiogroup, radio) {
                        if (radio.inputValue == 'enabled') {
                            Ext.getCmp("zincServer").enable();
                            Ext.getCmp("zincGroupEnable").enable();
                            Ext.getCmp("zincGroupDisable").enable();
                        } else {
                            Ext.getCmp("zincServer").disable();
                            Ext.getCmp("zincGroupEnable").setValue(false);
                            Ext.getCmp("zincGroupEnable").disable();
                            Ext.getCmp("zincGroupDisable").setValue(true);
                            Ext.getCmp("zincGroupDisable").disable();
                        }
                    }
                }
            },{
                xtype: 'textfield',
                id: 'zincServer',
                fieldLabel: '<spring:message javaScriptEscape="true" code="zinc.server"/>',
                maxLength: 255,
                width: 300,
                value: '<c:out value="${model.zincServer}"/>'
            },{
                xtype: 'radiogroup',
                id: 'zincEnabledGroup',
                name: 'zincEnabledGroup',
                hidden: false,
                fieldLabel: '<spring:message javaScriptEscape="true" code="enable.zinc.server.for.guests"/>',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message code="enabled"/>', id:'zincGroupEnable', name:'zincEnabledGroup', inputValue: 'enabled', checked:false},
                    {boxLabel: '<spring:message code="disabled"/>', id:'zincGroupDisable',  name:'zincEnabledGroup', inputValue: 'disabled', checked:false}
                ]
            }],
            buttons: [{
            	id: "saveButton",
                text: '<spring:message code="save"/>',
                handler: function() {

                            vidyoWebForm.getForm().submit({
                                url: '<c:url value="savevidyoweb.ajax"/>',
                                waitMsg: '<spring:message code="saving"/>',
                                success: function (form, action) {
                                    window.location = '<c:url value="featuresettings.html"/>';
                                },
                                failure: function(form, action) {
                                    var errorMsg = '<spring:message code="save.failed"/>';
                                    var errors = '';
                                    if ((action.result != null) && (!action.result.success)) {
                                        for(var i=0; i < action.result.errors.length; i++){
                                            errors += action.result.errors[i].msg + '<br>';
                                        }
                                    }
                                    msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
                                }
                            });
                }
            },{
                id: "cancelButton",
                text: '<spring:message code="cancel"/>',
                handler: function(){
                    window.location = '<c:url value="featuresettings.html"/>';
                }
            }]
        });

        /* VidyoWeb END */


        /* Chat BEGIN */

        var chatForm = new Ext.form.FormPanel({
            autoWidth: true,
            autoHeight: true,
            border: true,
            frame: true,
            labelAlign: 'side',
            labelWidth: 260,
            errorReader: new Ext.data.XmlReader({
                        record : 'field',
                        success: '@success'
                    },[
                'id', 'msg'
            ]
            ),
            defaults: {
                labelStyle: 'margin:0 0 0 -11px;font-weight:bold;text-align:right;'
            },
            items:[{
                xtype: 'radiogroup',
                id: 'publicChatEnabledGroup',
                name: 'publicChatEnabledGroup',
                hidden: false,
                fieldLabel: '<spring:message javaScriptEscape="true" code="public.chat"/>',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message code="enabled"/>', id:'publicChatGroupEnable', name:'publicChatEnabledGroup', inputValue: 'enabled', checked:false},
                    {boxLabel: '<spring:message code="disabled"/>', id:'publicChatGroupDisable',  name:'publicChatEnabledGroup', inputValue: 'disabled', checked:false}
                ]
            },{
                xtype: 'radiogroup',
                id: 'privateChatEnabledGroup',
                name: 'privateChatEnabledGroup',
                hidden: false,
                fieldLabel: '<spring:message javaScriptEscape="true" code="private.chat"/>',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message code="enabled"/>', id:'privateChatGroupEnable', name:'privateChatEnabledGroup', inputValue: 'enabled', checked:false},
                    {boxLabel: '<spring:message code="disabled"/>', id:'privateChatGroupDisable',  name:'privateChatEnabledGroup', inputValue: 'disabled', checked:false}
                ]
            },{
                xtype: 'label',
                id: 'chatFeatureDisabled',
                name: 'chatFeatureDisabled',
                hidden: true,
                html: '<b><span style="color:red"><spring:message javaScriptEscape="true" code="chat.feature.is.turned.off"/></span></b>'
            }],
            buttons: [{
                id: "saveChatButton",
                text: '<spring:message code="save"/>',
                handler: function(){
                	chatForm.getForm().submit({
                        url: '<c:url value="savechatadmin.ajax"/>',
                        waitMsg: '<spring:message code="saving"/>',
                        success: function (form, action) {
                            msg('<spring:message code="message"/>', '<spring:message code="saved"/>', function(){});
                        },
                        failure: function(form, action) {
                            var errorMsg = '<spring:message code="save.failed"/>';
                            var errors = '';
                            if ((action.result != null) && (!action.result.success)) {
                            	for(var i=0; i < action.result.errors.length; i++){
                                    errors += action.result.errors[i].msg + '<br>';
                                }
                            }
                            msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
                        }
                    });
                }
            },{
                id: "cancelChatButton",
                text: '<spring:message code="cancel"/>',
                handler: function(){
                    window.location = '<c:url value="featuresettings.html"/>';
                }
            }]
        });

        /* Chat END */

        /* Room Attributes BEGIN */

        var roomAttributesForm = new Ext.form.FormPanel({
            autoWidth: true,
            autoHeight: true,
            border: true,
            frame: true,
            labelAlign: 'side',
            labelWidth: 20,
            errorReader: new Ext.data.XmlReader({
                        record : 'field',
                        success: '@success'
                    },[
                'id', 'msg'
            ]
            ),
            items:[{
                xtype: 'checkboxgroup',
                id: 'waitingRoomDefault',
                name: 'waitingRoomDefault',
                hidden: false,
                hideLabel: false,
                labelSeparator: '',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message javaScriptEscape="true" code="make.the.waiting.room.mode.the.default.state.for.all.the.rooms.on.your.tenant"/>', id:'waitingRoomDefaultState', name:'waitingRoomsEnabled', inputValue: 'enabled', checked:false},
                ],
                listeners: {
                    change: function(checkbox, newVal, oldVal) {
                        if (newVal[0] != undefined && (newVal[0]).checked) {
                            Ext.getCmp("waitingRoomStateGroup").enable();
                            Ext.getCmp("waitingRoomStateOwnerJoin").setValue(true);
                            Ext.getCmp("waitingRoomStatePresenterSelect").setValue(false);
                        } else {
                            Ext.getCmp("waitingRoomStateGroup").disable();
                            Ext.getCmp("waitingRoomStateOwnerJoin").setValue(false);
                            Ext.getCmp("waitingRoomStatePresenterSelect").setValue(false);
                        }
                    }
                }
            },{
                xtype: 'radiogroup',
                id: 'waitingRoomStateGroup',
                name: 'waitingRoomStateGroup',
                hidden: false,
                hideLabel: false,
                labelSeparator: '',
                style: 'padding-left: 50px',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message javaScriptEscape="true" code="automatically.start.the.meeting.when.the.owner.joins"/>', id:'waitingRoomStateOwnerJoin', name:'waitUntilOwnerJoins', inputValue: 'enabled', checked:false},
                    {boxLabel: '<spring:message javaScriptEscape="true" code="stay.in.waiting.room.mode.until.a.presenter.is.selected"/>', id:'waitingRoomStatePresenterSelect',  name:'waitUntilOwnerJoins', inputValue: 'disabled', checked:false}
                ]
            },{
                xtype: 'checkboxgroup',
                id: 'supportedEndpoints',
                name: 'supportedEndpoint',
                hidden: false,
                hideLabel: false,
                labelSeparator: '',
                columns: 1,
                items:[
                    {boxLabel: '<spring:message javaScriptEscape="true" code="enforce.presenter.and.waiting.room.modes.for.supported.endpoints.only"/>', id:'supportedEndpointsOnly', name:'lectureModeStrict', inputValue: 'enabled', checked:false},
                ]
            }],
            buttons: [{
                id: "saveRoomAttributesButton",
                text: '<spring:message code="save"/>',
                handler: function(){
                    Ext.MessageBox.confirm('<spring:message javaScriptEscape="true" code="save.room.attributes"/>', '<spring:message javaScriptEscape="true" code="all.current.calls.will.be.disconnected.continue"/>', function(btn){
                        if(btn === 'yes'){
                            roomAttributesForm.getForm().submit({
                                url: '<c:url value="saveRoomAttributes.ajax"/>',
                                waitMsg: '<spring:message code="saving"/>',
                                success: function (form, action) {
                                    msg('<spring:message code="message"/>', '<spring:message code="saved"/>', function(){});
                                },
                                failure: function(form, action) {
                                    var errorMsg = '<spring:message code="save.failed"/>';
                                    var errors = '';
                                    if ((action.result != null) && (!action.result.success)) {
                                        for(var i=0; i < action.result.errors.length; i++){
                                            errors += action.result.errors[i].msg + '<br>';
                                        }
                                    }
                                    msg('<spring:message code="message"/>', errorMsg += '<br>' + errors, function(){});
                                }
                            });
                        }
                        else {
                            // do nothing
                        }
                    });
                }
            },{
                id: "cancelRoomAttributesSaveButton",
                text: '<spring:message code="cancel"/>',
                handler: function(){
                    window.location = '<c:url value="featuresettings.html"/>';
                }
            }]
        });

        /* Room Attributes END */

        var tabs = new Ext.TabPanel({
            activeTab: 0,
            plain: false,
            autoWidth: true,
            autoHeight: true,
            border: false,
            frame: true,
            autoScroll: true,
            enableTabScroll: true,
            deferredRender: false,
            layoutOnTabChange: true,
            defaults: {
                autoHeight: true
            },
            items:[
                {
                    title:'<spring:message javaScriptEscape="true" code="vidyoweb"/>',
                    items:[
                        vidyoWebForm
                    ]
                },{
                    title:'<spring:message javaScriptEscape="true" code="chat"/>',
                    items:[
                        chatForm
                    ]
                },{
                    title:'<spring:message javaScriptEscape="true" code="room.attributes"/>',
                    items:[
                        roomAttributesForm
                    ]
                }
            ]
        });

        var featureSettingsPanel = new Ext.Panel({
            title: '<spring:message code="admin.featuresettings.label"/>',
            tools: [{
                id:'help',
                qtip: '<spring:message code="help1"/>',
                handler: function(event, toolEl, panel){
                    var url = '<c:out value="${model.guideLoc}"/>#';
                    var wname = 'VidyoPortalHelp';
                    var wfeatures = 'menubar=no,resizable=yes,scrollbars=yes,status=yes,location=no';
                    window.open(url, wname, wfeatures);
                }
            }],
            frame: true,
            border: false,
            items: [
                tabs
            ]
        });

        /*
         * Feature Settings Top Level UI:
         * -Left: Local Menu
         * -Right: Feature Settings Tabbed Panels
         */
        new Ext.Panel({
            renderTo: 'content-panel',
            border: false,

            items:[{
                layout: 'column',
                border: false,
                items: [{
                    columnWidth:.19,
                    baseCls:'x-plain',
                    bodyStyle:'padding:5px 5px 5px 5px',
                    id: 'local',
                    items:[
                        localmenu
                    ]
                },{
                    columnWidth:.81,
                    baseCls:'x-plain',
                    bodyStyle:'padding:5px 5px 5px 5px',
                    items:[
                        featureSettingsPanel
                    ]
                }]
            }]
        });
        
        ////////// VidyoWeb /////////////////
        if (!<c:out value="${model.available}"/>) {
            Ext.getCmp('vidyoWebGroupDisable').setValue(true);
            vidyoWebForm.getForm().items.each(function(itm){itm.setDisabled(true)});
            Ext.getCmp("saveButton").disable();
            Ext.getCmp("cancelButton").disable();
        } else {
            if (<c:out value="${model.zincEnabled}"/>) {
                Ext.getCmp("zincGroupEnable").setValue(true);
                Ext.getCmp("zincGroupDisable").setValue(false);
            } else {
                Ext.getCmp("zincGroupEnable").setValue(false);
                Ext.getCmp("zincGroupDisable").setValue(true);
            }
            if (<c:out value="${model.enabled}"/>) {
                Ext.getCmp('vidyoWebGroupEnable').setValue(true);
            } else {
                Ext.getCmp('vidyoWebGroupDisable').setValue(true);
                Ext.getCmp("zincServer").disable();
                Ext.getCmp("zincGroupEnable").setValue(false);
                Ext.getCmp("zincGroupEnable").disable();
                Ext.getCmp("zincGroupDisable").setValue(true);
                Ext.getCmp("zincGroupDisable").disable();
            }
        }
        
        ////////// End of VidyoWeb /////////////////
        
        //////////Chat /////////////////
        if (!<c:out value="${model.chatAvailable}"/>) {
            Ext.getCmp("privateChatGroupDisable").setValue(true);
            Ext.getCmp("publicChatGroupDisable").setValue(true);
            Ext.getCmp('chatFeatureDisabled').setVisible(true);
            chatForm.getForm().items.each(function(itm){itm.setDisabled(true)});
            Ext.getCmp("saveChatButton").disable();
            Ext.getCmp("cancelChatButton").disable();
        } else {
            if(<c:out value="${model.publicChatEnabled}"/>) {
                Ext.getCmp("publicChatGroupEnable").setValue(true);
            } else {
                Ext.getCmp("publicChatGroupDisable").setValue(true);
            }
            if(<c:out value="${model.privateChatEnabled}"/>) {
                Ext.getCmp("privateChatGroupEnable").setValue(true);
            } else {
                Ext.getCmp("privateChatGroupDisable").setValue(true);
            }
        }
        ////////// End of Chat //////////


        ///// ROOM ATTRIBUTES START //////
        if (<c:out value="${model.waitingRoomsEnabled}"/>) {
            Ext.getCmp("waitingRoomDefaultState").setValue(true);

            if (<c:out value="${model.waitUntilOwnerJoins}"/>) {
                Ext.getCmp("waitingRoomStateOwnerJoin").setValue(true);
                Ext.getCmp("waitingRoomStatePresenterSelect").setValue(false);
            } else {
                Ext.getCmp("waitingRoomStatePresenterSelect").setValue(true);
                Ext.getCmp("waitingRoomStateOwnerJoin").setValue(false);
            }
        } else {
            Ext.getCmp("waitingRoomDefaultState").setValue(false);
            Ext.getCmp("waitingRoomStateGroup").disable();
        }


        if (<c:out value="${model.lectureModeStrict}"/>) {
            Ext.getCmp("supportedEndpointsOnly").setValue(true);
        } else {
            Ext.getCmp("supportedEndpointsOnly").setValue(false);
        }

        /// ROOM ATTRIBUTES END //////
    });

</script>
