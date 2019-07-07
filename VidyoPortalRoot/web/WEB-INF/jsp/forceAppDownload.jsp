<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div style="text-align: center; color: #5d5d5d;">
    <div id="choiceContainer1" style="background-color: rgb(246, 246, 246); margin: 0px 0px 30px;">
        <div style="font-size: 24px; padding: 50px 50px 20px 50px;"><spring:message code="download.installable" htmlEscape="true"/></div>
        <div style="margin-bottom: 5%;">
            <span style="display:block;font-weight: bold;"><spring:message code="technical.limitations.to.connect.to.webrtc" htmlEscape="true"/></span>
            <span style="display:block;font-weight: bold;"><spring:message code="download.to.install" htmlEscape="true"/></span>
        </div>
        <div style="text-align: center;font-weight: 300; width: 100%; float: left;">

            <div style="overflow: hidden;">
                <div style="width: 350px; margin: auto;">
                    <img src="/themes/vidyo/i/guest/download.svg" style="cursor: pointer" onclick="javascript:doDownload()">
                    <div style="margin-top: 8%; margin: auto auto; padding:40px; width: 200px;">
                        <spring:message code="after.downloading.the.installer.open.it.to.install.the.app" htmlEscape="true"/>
                    </div>
                    <div style="overflow: hidden;  margin-bottom: 20px; margin-left: auto; margin-right: auto; ">
                        <div style="text-align: left; padding: 20px 10px;">
                            <div style="padding: 10px;"></div>
                            <a href="javascript:doDownload()" style="font-size: 18px; text-align: center; text-decoration: none; background-color: #83C36D; display: block; padding: 14px 0px 14px 0px; color: white; font-weight: normal; width: 200px; margin: auto; border-radius: 5px; "><spring:message code="download" htmlEscape="true"/></a>
                        </div>


                    </div>
                </div>
            </div>
        </div>
        <div style="clear: both; padding: 20px 0px;"></div>
    </div>
    <div id="downloadContainerLegal" style="padding-top: 30px; font-size: 14px; color: #888888; clear: both;">
        <spring:message code="by.downloading.or.using.our.products.you.agree.to.our" htmlEscape="true"/> <a target="_blank" style="color: #6a6a6a;" href="/terms_content.html"><spring:message code="end.user.license.agreement" htmlEscape="true"/></a> &amp; <a target="_blank" style="color: #6a6a6a;" href="http://www.vidyo.com/privacy-policy/"><spring:message code="privacy.policy" htmlEscape="true"/></a>.
    </div>
    <img id="downArrow"  src="/themes/vidyo/i/guest/arrow.svg" style="display: none; position: absolute; bottom: 0px; left: 50px;  transform: rotate(30deg);"/>
	<div id="row-help1">
        <div id="helpText">
           <vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="need.help"/></vidyo:replaceString> <a href="<c:url value='/contact.html'/>" style="font-size: 14px;font-weight: 300;color:#7EACDF;text-decoration: inherit;" target="_blank"><vidyo:replaceString from="\\\\'" to="'"><spring:message htmlEscape="false" code="contact.support"/></vidyo:replaceString></a>
        </div>
    </div>
    <div id="copyright">&copy;2008-2019 Vidyo</div>    
</div>