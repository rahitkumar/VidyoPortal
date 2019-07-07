<%@ include file="header_html.jsp" %>

    <div id="maincontent">
        <div id="content-panel">

			<div id="welcomemsg" style="padding-top: 420px;">&nbsp;</div>

        </div>

	</div>

<%@ include file="footer_html.jsp" %>

<script type='text/javascript' src='<c:url value="/js/FieldOverride.js"/>'></script>

<script type="text/javascript">

    Ext.onReady(function(){

        Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

        Ext.QuickTips.init();

        /************************************************************
        * Display the result in page
        * the column model has information about grid columns
        * dataIndex maps the column to the specific data field in
        * the data store (created below)
        ************************************************************/
        <security:authorize ifNotGranted="ROLE_AUDIT">
            document.getElementById("nav").style.visibility="hidden" ;
        </security:authorize>

        var cm = new Ext.grid.ColumnModel([
            {
                header: 'Result',
                dataIndex: 'transactionResult',
                width: 115
            },{
                header: 'Source Address',
                dataIndex: 'sourceIP',
                width: 100
            },{
                header: 'Time',
                dataIndex: 'transactionTime',
                width: 130
            }
        ]);

        var loginHistory = Ext.data.Record.create([
                    {name: 'transactionResult', type: 'string'},
                    {name: 'sourceIP', type: 'string'},
                    {name: 'transactionTime', type: 'string'}
                ]);

                var loginHistoryReader = new Ext.data.ArrayReader({
                    }, loginHistory);

                var ds = new Ext.data.Store({
                    reader: loginHistoryReader,
                    remoteSort: true
                });

        ds.loadData([
                        <c:forEach items="${model.loginHistories}" var="loginHistory">
                            ['<c:out value="${loginHistory.transactionResult}" />',
                            '<c:out value="${loginHistory.sourceIP}" />',
                            '<fmt:formatDate value="${loginHistory.transactionTime}" pattern="MM/dd/yyyy HH:mm:ss a" />'],
                        </c:forEach>
                         ]);

        var htmlpanel = new Ext.Panel({
                        autoWidth: true,
            			autoHeight: true,
                        id: 'usgMsgForm',
            			bodyStyle: 'padding: 10px;',
            			border: true,
            			frame: true,
            			labelWidth: 0,
            			html:'<c:out value="${model.welcomeBanner}" escapeXml="false"/>'
        });

        var welcomeBannerPanel = new Ext.form.FormPanel({
            autoWidth: true,
            autoHeight: true,
            //bodyStyle: 'padding: 5px;',
            border: false,
            frame: true,
            labelWidth: 0,
            footer: true,
            footerCfg: {
                tag: 'div',
                cls: 'x-panel-footer'
                <c:if test="${not empty model.passwdExpDate}">
                    ,
                    children: [
                        { tag: 'div', cls: 'login_footer_messg', 'html': String.format('<spring:message code="your.password.will.expire.on"/>', '<fmt:formatDate value="${model.passwdExpDate}" pattern="MM/dd/yyyy HH:mm:ss a" />')}
                        ]
                </c:if>
            },
            items: [htmlpanel,{
                xtype:'fieldset',
                autoHeight: true,
                title: '<spring:message code="your.last.5.login.attempts"/>',
                items: [{
                    xtype: 'grid',
                    store: ds,
                    cm: cm,
                    autoHeight: true,
                    border: false,
                    frame: true,
                    loadMask: true,
                    viewConfig: {
                        forceFit: true
                    }
                }]
            }]
        });

        var welcomeBannerWin = new Ext.Window({
            title: 'Welcome <c:out value="${requestScope.USER_NAME}" />',
            closable: false,
            resizable: true,
            width: 730,
            height: 400,
            renderTo: 'welcomemsg',
            border: false,
            frame: true,
            items: [
                welcomeBannerPanel
            ],
            buttonAlign: 'center',
            buttons:  [{
                text: '<spring:message code="continue"/>',
                handler: function(){
                    window.location='<c:url value="${model.savedReqUrl}"/>';
                }
            }]
        });

        welcomeBannerWin.show();
    });

</script>