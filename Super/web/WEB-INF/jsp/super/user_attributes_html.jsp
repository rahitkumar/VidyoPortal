<?xml version="1.0" encoding="UTF-8"?>
<%@ include file="../ajax/include_ajax.jsp" %>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>

<dataset>
    <results>1</results>
    <row>
        <enableUserImage><c:out value="${model.enableUserImage}"/></enableUserImage>
        <enableUserImageUpload><c:out value="${model.enableUserImageUpload}"/></enableUserImageUpload>
         <maxImageSize><c:out value="${model.maxImageSize}"/></maxImageSize>
        
       
    </row>
</dataset>