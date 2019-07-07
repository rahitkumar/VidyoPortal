<%@ include file="header_html.jsp" %>

    <div id="maincontent">
        <div id="content-panel">

			<div id="welcomemsg" style="padding-top: 420px;">&nbsp;</div>

        </div>

	</div>

<script type='text/javascript' src='<c:url value="/js/FieldOverride.js"/>'></script>

<script type="text/javascript">

    Ext.onReady(function(){

         var userName = '<c:out value="${model.showLoginBanner}"/>';
        Ext.BLANK_IMAGE_URL = '<c:url value="/js/resources/images/default/s.gif"/>';

        Ext.QuickTips.init();

        /************************************************************
        * Display the result in page
        * the column model has information about grid columns
        * dataIndex maps the column to the specific data field in
        * the data store (created below)
        ************************************************************/
        <security:authorize access="!hasRole('ROLE_AUDIT')">
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
            {name: 'ID', type: 'string'},
            {name: 'transactionName', type: 'string'},
            {name: 'transactionResult', type: 'string'},
            {name: 'sourceIP', type: 'string'},
            {name: 'transactionTime', type: 'string'}
        ]);

        var loginHistoryReader = new Ext.data.XmlReader({
                totalRecords: "results",
                record: "row"
        }, loginHistory);

        var ds = new Ext.data.Store({
            url: '<c:url value="loginhistory.ajax"/>',
            params: {id:'<c:out value="${requestScope.USER_NAME}" />'},
            reader: loginHistoryReader,
            remoteSort: true
        });

        ds.setDefaultSort('tenantName', 'ASC');
        // trigger the data store load
        ds.load();

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
                cls: 'x-panel-footer',
                    children: [
                        { tag: 'div', cls: 'login_footer_messg', html: "${model.expiryStatement}"}
                        ]
            },
            items: [{
                xtype: 'panel',
                bodyStyle: 'padding: 10px;',
               html:'<c:if test="${not empty model.banner}"><c:out value="${model.banner}" escapeXml="false"/></c:if>'
            },{
                xtype:'fieldset',
                autoHeight: true,
                title: 'Your Last 5 login attempts',
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
                text: 'Continue',
                handler: function(){
                    window.location='<c:url value="maintenance.html"/>';
                }
            }]
        });

        welcomeBannerWin.show();
   });

</script>

<%@ include file="footer_html.jsp" %>