    <div id="container">
        <div class="row-header">
            <div id="header">
                <c:if test="${model.logoUrl != ''}">
                    <img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/>
                </c:if>
                <c:if test="${model.logoUrl == ''}">
                    <div id="logo"></div>
                </c:if>
            </div>
        </div>
        
        <div id="row-title">
            <c:if test="${model.isInfoCheck}">
                <div id="infoIconCheck"></div>
            </c:if>
            <c:if test="${!model.isInfoCheck}">
                <div id="infoIconExclamation"></div>
            </c:if>
            <div id="infoMsg"><c:out escapeXml="false" value="${model.infoMsg}"/></div>
        </div>
        
        <div id="row-main">
            <div id="closeTab">
                <div id="closeTabMsg">
                    <spring:message javaScriptEscape="true" code="please.close.this.tab"/>
                </div>
                <div id="closeTabEmptySpace"></div>
            </div>
        </div>
        
        <div id="row-help">
            <div id="helpText">
            <vidyo:replaceString from="\\\\'" to="'"><spring:message javaScriptEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="color:#8888CC" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message javaScriptEscape="false" code="contact.support"/></vidyo:replaceString></a>
            </div>
        </div>
    </div>