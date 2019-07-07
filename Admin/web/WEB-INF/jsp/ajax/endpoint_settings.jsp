<?xml version="1.0" encoding="UTF-8"?>
<%@ page contentType="text/xml" %>
<%@ page pageEncoding="UTF-8" %>
<%@ include file="../ajax/include_ajax.jsp" %>
<dataset>
    <result>
        1
    </result>
    <row>
        <endpointSettings>
            <dscp>
                <video>${model.dscpVideo}</video>
                <audio>${model.dscpAudio}</audio>
                <content>${model.dscpContent}</content>
                <signaling>${model.dscpSignaling}</signaling>
            </dscp>
            <mediaPortRange>
                <minPort>${model.minMediaPort}</minPort>
                <maxPort>${model.maxMediaPort}</maxPort>
            </mediaPortRange>
            <vidyoProxy>
                <alwaysUseVidyoProxy>${model.alwaysUseVidyoProxy}</alwaysUseVidyoProxy>
            </vidyoProxy>
        </endpointSettings>
    </row>
</dataset>