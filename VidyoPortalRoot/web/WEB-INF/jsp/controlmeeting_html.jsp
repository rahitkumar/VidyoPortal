<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"
        %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
        %><%@ taglib prefix="spring" uri="http://www.springframework.org/tags"
        %><%@ taglib prefix="vidyo" uri="/WEB-INF/tld/ReplaceStringTagHandler.tld"
        %><!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="_csrf" content="<c:out value="${_csrf.token}"/>"/>
    <meta name="_csrf_header" content="<c:out value="${_csrf.headerName}"/>"/>
    <title><spring:message code="control.meeting"/> - <c:out value="${model.roomName}"/>@<c:out value="${model.roomHost}"/> </title>
    <link rel="stylesheet" href="themes/vidyo/controlmeeting.css?v=3.4">
    <link rel="stylesheet" href="themes/vidyo/jqueryui/css/jquery.ui.all.css">
    <link rel="shortcut icon" href="favicon.ico">
    <script type="text/javascript"  src="js/jquery.js"></script>
    <script type="text/javascript"  src="js/jquery-migrate.js"></script>
    <script type="text/javascript"  src="js/jquery-ui.js"></script>
    <script type="text/javascript"  src="js/jquery.stickysectionheaders.js"></script>
    <script type="text/javascript" src="js/noty/jquery.noty.js"></script>
    <script type="text/javascript" src="js/noty/layouts/inline.js"></script>
    <script type="text/javascript" src="js/noty/layouts/bottom.js"></script>
    <script type="text/javascript" src="js/noty/layouts/bottomLeft.js"></script>
    <script type="text/javascript" src="js/noty/themes/vidyo.js"></script>

    <script type="text/javascript">
        $(function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $(document).ajaxSend(function(e, xhr, options) {
                xhr.setRequestHeader(header, token);
            });
        });
    </script>
</head>
<body>
    <div id="content">

        <div class="row-fluid">
            <div id="header">
                <c:if test="${model.logoUrl != ''}">
                    <div class="span4"><img id="customLogo" src="<c:url value="${model.logoUrl}"/>" border="0"/></div>
                </c:if>
                <c:if test="${model.logoUrl == ''}">
                    <div id="logo" class="span4"></div>
                </c:if>
                <div id="conference" class="span4">
                    <spring:message htmlEscape="true" code="connected.to.conference"/><br/>
                    <span id="roomName"><c:out value="${model.roomDisplayName}"/></span> [<c:out value="${model.roomName}"/>]</span>
                </div>
                <div id="timer" class="span4">
                    <span id="timerImage"></span>
                    <span id="timerTime"></span>
                </div>
            </div>
        </div>

        <div id="lectureBanner">
            <div id="lectureBannerText"></div>
            <div id="lectureModeStateButtons"><div id="lectureModePresenterButton"><spring:message htmlEscape="true" code="presenter"/></div><div id="lectureModeExitButton"><spring:message htmlEscape="true" code="group"/></div></div>
        </div>

        <div id="participantsPage">
            <div class="searchBar">
                <div class="searchSpacer">
                    <div style="float: right;">
                        <a id="addParticipantButton" class="" href="#" title="<spring:message htmlEscape="true" code="add.participant"/>"></a>
                        <a id="participantEmailInviteButton" class="emailInviteButton" href="#" title="<spring:message htmlEscape="true" code="invite.via.email"/>"></a>
                        <a id="lockButton" class="unlocked" href="#" title="<spring:message htmlEscape="true" code="lock.unlock.room"/>"></a>
                    </div>
                </div>
                <input type="text" id="searchParticipants" class="search rounded" placeholder="<spring:message htmlEscape="true" code="search.participants"/>" autocapitalize="off" autocorrect="off" autocomplete="off"/>
                <div id="searchParticipantsAppend" class="searchAppend searchSearch"></div>
                <div class="searchSpacer">
                    <div style="float: left;">
                        <a id="recordButton" class="record" href="#" title="<spring:message htmlEscape="true" code="record"/>"></a><a id="pauseButton" class="" href="#" title="<spring:message htmlEscape="true" code="pause"/>"></a><a id="stopButton" class="stop" href="#" title="<spring:message htmlEscape="true" code="stop"/>"></a>
                        <a id="settingsButton" class="" href="#" title="<spring:message htmlEscape="true" code="settings"/>"></a>
                    </div>
                </div>
            </div>
            <div class="actionsBarContainer">
            <div class="actionsBar1 box rounded">
                <a id="muteVideoAllButton" class="unmutedVideo" href="#" title="<spring:message htmlEscape="true" code="disable.video.on.participants.cameras.without.allowing.participants.to.re.enable"/>"></a>
                <a id="muteAllButton" class="unmuted" href="#" title="<spring:message htmlEscape="true" code="mute.audio.on.participants.microphones.without.allowing.participants.to.re.enable"/>"></a>
            </div>
            <div class="actionsBar2 box rounded">
                <a id="silenceVideoAllButton" class="silenceVideoAll" href="#" title="<spring:message htmlEscape="true" code="disable.video.on.participants.cameras.and.allow.participants.to.re.enable"/>"></a>
                <a id="silenceAllButton" class="silenceAll" href="#" title="<spring:message htmlEscape="true" code="mute.audio.on.participants.microphones.and.allow.participants.to.re.enable"/>"></a>
                <a id="disconnectAllButton" href="#" title="<spring:message htmlEscape="true" code="disconnect.all.participants"/>"></a>
            </div>
            </div>
            <div style="float: right; margin-top: -30px;">
                <a id="alphaSortButton" class="alphaSortOff" href="#" title="<spring:message htmlEscape="true" code="sort.participants.alphabetically"/>"></a>
                <a id="numericSortButton" class="numericSortOn" href="#" title="<spring:message htmlEscape="true" code="sort.participants.by.order.of.attendance"/>"></a>
            </div>

            <div style="padding: 15px 10px 10px 10px;">
                <div id="theParticipants" style="min-height: 150px; overflow: auto; clear: both; -webkit-overflow-scrolling: touch;">
                    <ul>
                    <li id="theInvited">
                        <strong class="summaryBar" id="inviteesHeader">
                            <div style="display: inline-block; float: left;" >
                                <spring:message htmlEscape="true" code="invitees"/>: <span id="inviteesCount">0</span>
                            </div>
                        </strong>
                        <ul id="invitedList">
                        </ul>
                    </li>
                    <li id="theDisplayedParticipants">
						<strong class="summaryBar"  id="participantsHeader">
							<div style="display: inline-block; float: left;">
								<spring:message htmlEscape="true" code="participants1"/>: <span id="participantsCount">0</span> <a href="#" id="dismissAllLink"> <spring:message htmlEscape="true" code="dismiss.all.hands"/> (<span id="handsCounter">1</span>)</a>
							</div>
							<div style="display: inline-block; float: right; height: 20px;">
								<div id="recordingIndicator" class=""></div>
								<div id="recordingIndicatorText"></div>
							</div>
						</strong>
                        <ul id="participantsList">
                        </ul>
                    </li>
                    <li id="noParticipants">
                        <div style="text-align: center; padding: 40px 0px 30px 0px;"><spring:message htmlEscape="true" code="there.are.no.participants.in.the.conference"/></div>
                        <div style="display: block; width: 250px; margin: 0px auto;">
                            <a id="bigAddParticipantButton" href="#" class="bigAddPart"></a>
                            <a id="bigEmailInviteButton" href="#" class="bigEmail"></a>
                        </div>
                        <div style="text-align: center; clear: both;  padding: 30px 0px 0px 0px;"><spring:message htmlEscape="true" code="add.participants.or.invite.by.email"/></div>
                    </li>
                    </ul>
                </div>
            </div>

        </div>

        <div id="contactsPage" title="<spring:message htmlEscape="true" code="add.participant"/>">
            <div class="searchBar" >
                <input type="text" id="searchContacts" class="search rounded" placeholder="<spring:message htmlEscape="true" code="search.contacts"/>" autocomplete="off"/>
                <div id="searchContactsAppend" class="searchAppend searchSearch"></div>
                <input type="button" id="inviteButton" class="button opaque" value="<spring:message htmlEscape="true" code="invite"/>"/>
            </div>

            <div id="noContacts" class="rounded">
                <div style="width: 100%;" class="bigContact"></div>
                <div style="text-align: center;"><spring:message htmlEscape="true" code="search.for.contacts.to.invite.to.the.conference"/></div>
            </div>
            <div id="theSelectedContacts" style="max-height: 375px; overflow: auto; -webkit-overflow-scrolling: touch;">
                <ul>
					<li id="selectedContacts">
						<strong class="summaryBar" id="selectedHeader">
							<spring:message htmlEscape="true" code="selected.contacts"/>:
						</strong>
						<ul id="selectedContactsList">
						</ul>
					</li>
                </ul>
            </div>
            <div id="theContacts" style="-webkit-overflow-scrolling: touch;">
                <ul>
					<li id="dirContacts">
						<strong class="summaryBar" id="directoryHeader">
							<spring:message htmlEscape="true" code="directory"/>:
						</strong>
						<ul id="contactsDirectoryList">
						</ul>
					</li>
					<li id="extContacts">
						<strong class="summaryBar">
							<spring:message htmlEscape="true" code="external.contact"/>:
						</strong>
						<ul id="contactsExternalList">
						</ul>
					</li>
                </ul>
            </div>
        </div>

    </div>

    <div id="loginBox" title="<spring:message htmlEscape="true" code="login"/>: <c:out value="${model.roomHost}"/>">
        <div style="margin: auto;">
            <vidyo:replaceString from="\\\\'" to="'"><spring:message code="username"/></vidyo:replaceString>:<br /> <input type="text" id="username" name="username" autocomplete="off" autocapitalize="off" autocorrect="off" value=""/><br />
            <vidyo:replaceString from="\\\\'" to="'"><spring:message code="password"/></vidyo:replaceString>:<br /> <input type="password" id="password"  name="password" autocomplete="off" value=""/><br />
        </div>
    </div>

    <div id="pinBox" title="<spring:message htmlEscape="true" code="moderator.pin"/>">
        <div style="margin: auto;">
            <spring:message htmlEscape="true" code="pin"/>:<br /> <input type="number" id="pin" name="pin" autocomplete="off"/><br />
        </div>
    </div>

    <div id="waitBox" title="<spring:message htmlEscape="true" code="room.moderation.forbidden"/>">
        <div style="font-size: 0.85em; padding: 0px 0px 10px 0px;" >
            <spring:message htmlEscape="true" code="sorry.you.are.unable.to.moderate.this.room.at.this.time.as.the.room.owner.has.not.set.a.moderator.pin"/>
        </div>
    </div>

    <div id="invalidRoomBox" title="<spring:message htmlEscape="true" code="error"/>">
        <div style="font-size: 0.85em; padding: 0px 0px 10px 0px;" >
            <spring:message htmlEscape="true" code="the.link.you.have.followed.is.invalid"/>
        </div>
    </div>

    <div id="emailBox" title="<spring:message htmlEscape="true" code="email.invitation"/>">
        <div style="margin: auto;">
            <div class="optionsItem">
                <input type="radio" id="emailTypeInvite" name="emailType" value="inviteRoom" checked="checked"><label for="emailTypeInvite">&nbsp;&nbsp;<spring:message htmlEscape="true" code="send.an.invite.link"/></label><br />
            </div>

            <div class="optionsItem" id="emailShowWebcastLink">
                <input type="radio" id="emailTypeWebcast" name="emailType" value="webcast"><label for="emailTypeWebcast">&nbsp;&nbsp;<spring:message htmlEscape="true" code="send.a.webcast.link"/></label><br />
            </div>

            <div class="optionsItem" id="emailShowVoiceLink">
                <input type="radio" id="emailTypeVoice" name="emailType" value="voiceOnly"><label for="emailTypeVoice">&nbsp;&nbsp;<spring:message htmlEscape="true" code="send.a.voice.only.link"/></label><br />
            </div>
        </div>
    </div>

    <div id="startRecordingBox" title="<spring:message htmlEscape="true" code="recording.options"/>">
        <div id="startRecordingOptions" class="rounded">
            <div class="optionsItem">
                <div><spring:message htmlEscape="true" code="record"/>:</div>
                <div>
                    <div style="display:inline-block;">
                        <div style="padding-bottom: 5px;"><input type="radio" id="recordRecord" name="recordType" value="record" checked="checked"><label for="recordRecord">&nbsp;&nbsp;<spring:message htmlEscape="true" code="record"/></label></div>
                        <div style="padding-top: 5px;"><input type="radio" id="recordWebcast" name="recordType" value="webcast"><label for="recordWebcast">&nbsp;&nbsp;<spring:message htmlEscape="true" code="record"/> &amp; <spring:message htmlEscape="true" code="webcast"/></label> </div>
                    </div>
                </div>
            </div>

            <div class="optionsItem">
                <div style="padding-bottom: 5px;"><spring:message htmlEscape="true" code="record.profile"/>:</div>
                <select name="recordProfile" id="recordProfiles" style="font-size: 0.8em; padding: 4px;">

                </select>
            </div>

        </div>
    </div>

    <div id="optionsBox" title="<spring:message htmlEscape="true" code="options"/>">
        <div id="replayLibraryOptions" class="rounded">
            <div class="optionsItem">
                <span style="padding-right: 20px;">VidyoReplay:</span>
                <input type="button" id="replayLibraryButton" class="button"  style="min-height: 30px; min-width: 150px;" value="<spring:message htmlEscape="true" code="go.to.library"/>"/>
            </div>
        </div>
        <div id="moderatorOptions" class="rounded">
            <div class="optionsItem">
                <div><spring:message htmlEscape="true" code="moderator.pin"/>:</div>
                <input id="moderatorPin" type="password" name="moderatorPin"> <input type="button" id="saveModeratorPINButton" class="button"  style="min-height: 30px;" value="<spring:message htmlEscape="true" code="save"/>"/>
            </div>
        </div>
        <div id="roomOptions" class="rounded">
            <div class="optionsItem">
                <div><spring:message htmlEscape="true" code="room.link"/>:</div>
                <div id="roomLink" style="width: 300px; border: solid 1px #bbb; min-height: 24px; padding: 5px; background: white; overflow: hidden; white-space: nowrap;"></div>
                <div id="roomLinkActions">
                    <a href="#" class="new" id="createRoomLinkButton" title="<spring:message htmlEscape="true" code="create.new.room.link"/>"></a><a href="#" class="remove" id="removeRoomLinkButton" title="<spring:message htmlEscape="true" code="remove.room.link"/>"></a>
                </div>
            </div>
            <div class="optionsItem" id="roomPINAction">
                <div><spring:message htmlEscape="true" code="room.pin"/>:</div>
                <input id="roomPin" type="password" name="roomPin"> <input type="button" id="saveRoomPINButton" class="button" style="min-height: 30px;" value="<spring:message htmlEscape="true" code="save"/>"/>
            </div>
        </div>
        <div id="recordingOptions" class="rounded">
            <div class="optionsItem">
                <div><spring:message htmlEscape="true" code="webcast.link"/>:</div>
                <div id="webcastLink" style="width: 300px; border: solid 1px #bbb; min-height: 24px; padding: 5px; background: white; overflow: hidden; white-space: nowrap;"></div>
                <div id="webcastActions">
                    <a href="#" class="new" id="createWebcastLinkButton" title="<spring:message htmlEscape="true" code="create.new.webcast.link"/>"></a><a href="#" class="remove" id="removeWebcastLinkButton" title="<spring:message htmlEscape="true" code="remove.webcast.link"/>"></a>
                </div>
            </div>
            <div class="optionsItem" id="webcastPINAction">
                <div><spring:message htmlEscape="true" code="webcast.pin"/>:</div>
                <input id="webcastPin" type="password" name="webcastPin"> <input type="button" id="saveWebcastPINButton" class="button" style="min-height: 30px;" value="<spring:message htmlEscape="true" code="save"/>"/>
            </div>
        </div>
    </div>

    <script type="text/javascript">

    var authenticated = <c:out value="${model.authenticated}"/>;
    var roomLocked = false;
    var canRecordMeeting = false;
    var allMuted = false;
    var allMutedVideo = false;
    var roomID = "<c:out value="${model.roomID}"/>";
    var myRoomID = 0;
    var myEID = "";
    var roomName = "<c:out value="${model.roomName}"/>";
    var pin = "";
    var contactsDirCount = 0;
    var contactsPageOn = false;
    var joinSeconds = 0;
    var showTime = 0;
    var inviteesRegular = new Array();
    var inviteesExternal = new Array();
    var invitedRegular = new Array();
    var invitedExternal = new Array();
    var selectedParticipants = new Array();
    var hasWebcastPin = false;
    var hasModeratorPin = false;
    var recordingOn = false;
    var pauseOn = false;
    var recorderID="";
    var recorderName="";
    var webcastingOn = false;
    var participantsCount = 0;
    var sort = "joinTime"; // "name";
    var order = "DESC"; // "ASC";
    var roomAdmin = <c:out value="${model.roomAdmin}"/>;
    var roomOwner = false;
    var showDisconnectAll= false;
    var showSilenceAll = false;
    var showVideoSilenceAll = false;
    var selectedParticipantEid = null;
    var chosenRecordingProfile = "";
    var roomDialIn = "";
    var roomURL = "";
    var hasRoomPin = false;
    var lectureMode = <c:out value="${model.lectureMode}"/>;
    var waitingRoom = <c:out value="${model.waitingRoom}"/>;
    var presenterSet = false;
    var raisedHandsCount = 0;
    var showLectureModeExitConfirm = false;
    var lectureModeFeature = <c:out value="${model.lectureModeFeature}"/>;

    $.noty.defaults = {
        layout: 'bottomLeft',
        theme: 'vidyoTheme',
        type: 'alert',
        text: '',
        dismissQueue: true,
        template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
        animation: {
            open: {height: 'toggle'},
            close: {height: 'toggle'},
            easing: 'swing',
            speed: 500 // opening & closing animation speed
        },
        timeout: 5000, // delay for closing event. Set false for sticky notifications
        force: false, // adds notification to the beginning of queue when set to true
        modal: false,
        maxVisible: 5, // you can set max visible notification for dismissQueue true option
        closeWith: ['click'], // ['click', 'button', 'hover']
        callback: {
            onShow: function() {},
            afterShow: function() {},
            onClose: function() {},
            afterClose: function() {}
        },
        buttons: false // an array of buttons
    };

    $().ready( function() {

        $("body").fadeIn(300);

        $("#timer").on("click", function() {
           if (showTime == 0) {
               showTime = 1;
           } else {
               showTime = 0;
           }
           updateTimer();
        });

        $("#addParticipantButton, #bigAddParticipantButton").on("click", function() {
            showContactsPage();
        });

        $("#viewParticipantsButton").on("click", function() {
            getParticipants();
            showParticipantsPage();
        });

        $(".emailInviteButton, #bigEmailInviteButton").on("click", function() {
            if (canShowEmailChoice()) {
                $("#emailBox").dialog({
                    width:'300px',
                    //show: 'fade',
                    //hide: 'fade',
                    modal: true,
                    draggable: false,
                    closeOnEscape: true,
                    resizable: false,
                    //create: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog).hide(); },
                    buttons: [ { text: "<spring:message javaScriptEscape="true" code="cancel"/>", click: function() { $("#emailBox").dialog("close"); } } , { text: "<spring:message javaScriptEscape="true" code="ok"/>", click:
                            function() {
                                var emailType = $("input[type='radio'][name='emailType']:checked").val();
                                handleEmail(emailType);
                            }

                    } ]
                });
            } else {
                handleEmail('inviteRoom');
            }
        });


        $("#recordButton").on('click', function() {
            if (recordingOn) {
                //stopRecording();
            } else {
                showStartRecordingBox();
            }
        });


        $("#stopButton").on('click', function() {
            if (recordingOn) {
                stopRecording();
            } else {
                //startRecording();
            }
        });

        $("#pauseButton").on('click', function() {
            if (!recordingOn) {
                turnPauseOff();
                return;
            }
            if (pauseOn) {
                resumeRecording();
            } else {
                pauseRecording();
            }
        });



        $("#lockButton").on("click", function() {
           toggleRoomLock();
        });

        $("#settingsButton").on("click", function() {
            getRoomInfo().complete(function() {
                getRoomURLDetails().complete(function() {
                    showOptions();
                });
            });
        });

        $("#alphaSortButton").on("click", function () {
            $("#numericSortButton").removeClass("numericSortOn").addClass("numericSortOff");
            $("#alphaSortButton").removeClass("alphaSortOff").addClass("alphaSortOn");
            sort = "name";
            order = "ASC";
            getParticipants();
            //showMessage("Sorting participants by name.");
        });

        $("#numericSortButton").on("click", function () {
            $("#alphaSortButton").removeClass("alphaSortOn").addClass("alphaSortOff");
            $("#numericSortButton").removeClass("numericSortOff").addClass("numericSortOn");
            sort = "joinTime";
            order = "DESC";
            getParticipants();
            //showMessage("Sorting participants by join time.");
        });


        $("#muteVideoAllButton").on("click", function() {
            //if (isParticipantSelected()) {
            //    toggleMuteVideoSelectedParticipants();
            //} else {
                toggleMuteVideoAll();
           // }
        });


        $("#silenceVideoAllButton").on("click", function() {
            //if (isParticipantSelected()) {
                // do nothing
            //} else {
            if (!showVideoSilenceAll) {
                showVideoSilenceAll = true;
                confirmMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="clicking.this.button.will.locally.disable.the.video.on.all.the.participants.cameras.the.conference.participants.will.then.have.to.re.enable.their.cameras.in.order.to.be.seen.you.will.not.be.able.to.re.enable.their.cameras.remotely.are.you.sure.you.want.to.continue"/>", function () {
                    silenceVideoAll();
                    showVideoSilenceAll = false;
                }, function () {
                    showVideoSilenceAll = false;
                });
            }
            //}
        });

        $("#muteAllButton").on("click", function() {
            //if (isParticipantSelected()) {
            //    toggleMuteSelectedParticipants();
            //} else {
                toggleMuteAll();
           // }
        });


        $("#silenceAllButton").on("click", function() {
           // if (isParticipantSelected()) {
                // do nothing
           // } else {
            if (!showSilenceAll) {
                showSilenceAll = true;
                confirmMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="clicking.this.button.will.locally.mute.the.audio.on.all.the.participants.microphones.the.conference.participants.will.then.have.to.unmute.their.microphones.in.order.to.be.heard.you.will.not.be.able.to.unmute.their.microphones.remotely.are.you.sure.you.want.to.continue"/>", function () {
                    silenceAll();
                    showSilenceAll = false;
                }, function () {
                    showSilenceAll = false;
                });
            }
            //}
        });

        $("#disconnectAllButton").on("click", function() {
            //if (isParticipantSelected()) {
            //    disconnectSelectedParticipants();
            //} else {

            if (!showDisconnectAll) {
                showDisconnectAll = true;
                confirmMessage("<spring:message javaScriptEscape="true" code="disconnect.all.participants1"/>", function () {
                    disconnectAll();
                    showDisconnectAll = false;
                }, function () {
                    showDisconnectAll = false;
                });
            }
            //}
        });

        $("#createRoomLinkButton").on("click", function () {
            setRoomURL();
        });

        $("#removeRoomLinkButton").on("click", function() {
            clearRoomURL();
        });

        $("#saveRoomPINButton").on("click", function() {
            var thePIN = $("#roomPin").val().trim();
            if (thePIN == '') {
                clearRoomPIN();
            } else {
                setRoomPIN(thePIN);
            }
        });

        $("#createWebcastLinkButton").on("click", function () {
           setWebcastURL();
        });

        $("#removeWebcastLinkButton").on("click", function() {
           clearWebcastURL();
        });

        $("#saveWebcastPINButton").on("click", function() {
            var thePIN = $("#webcastPin").val().trim();
            if (thePIN == '') {
                clearWebCastPIN();
            } else {
                setWebcastPIN(thePIN);
            }
        });

        $("#saveModeratorPINButton").on("click", function() {
            var thePIN = $("#moderatorPin").val().trim();
            if (thePIN == '') {
                clearRoomModeratorPIN();
            } else {
                setRoomModeratorPIN(thePIN);
            }
        });

        $("#replayLibraryButton").on("click", function() {
           openReplayURL();
        });

        $("#searchParticipants").on("focus", function () {
            $("#searchParticipantsAppend").removeClass("searchSearch");
            $("#searchParticipantsAppend").addClass("searchClear");
        });

        $("#searchParticipantsAppend").on("click", function() {
            $("#searchParticipants").val("");
            //$("#searchParticipants").focus();
            $("#searchParticipantsAppend").removeClass("searchClear");
            $("#searchParticipantsAppend").addClass("searchSearch");
            $('#participantsList').find('li').each(
                    function(index, row) {
                        $(row).show();
                    }
            );
        });

        $("#searchContacts").on("focus", function () {
            if ($("#searchContacts").val() != '') {
                $("#searchContactsAppend").removeClass("searchSearch");
                $("#searchContactsAppend").addClass("searchClear");
            }
        });

        $("#searchContactsAppend").on("click", function() {
            $("#searchContacts").val("");
            $("#searchContacts").focus();
            $("#searchContactsAppend").removeClass("searchClear");
            $("#searchContactsAppend").addClass("searchSearch");
            if (getTotalInvitees() != 0) {
                $("#noContacts").hide();
                $("#theContacts").hide();
                $("#theSelectedContacts").show();
            } else {
                $("#noContacts").show();
                $("#theContacts").hide();
                $("#theSelectedContacts").hide();
            }
            clearContacts();
        });

        $("#searchContacts").on("keydown", function () {
            $("#searchContactsAppend").removeClass("searchSearch");
            $("#searchContactsAppend").addClass("searchClear");
        });


        $("#searchContacts").on("keyup", function () {
            if ($("#searchContacts").val() == '') {
                clearContacts();
                if (getTotalInvitees() != 0) {
                    $("#noContacts").hide();
                    $("#theContacts").hide();
                    $("#theSelectedContacts").show();
                } else {
                    $("#noContacts").show();
                    $("#theContacts").hide();
                    $("#theSelectedContacts").hide();
                }
                $("#searchContactsAppend").removeClass("searchSearch");
                $("#searchContactsAppend").addClass("searchClear");
            } else {
                searchContacts($("#searchContacts").val());
            }
        });

        $("#searchContacts").on("blur", function () {
            if ($("#searchContacts").val() == '') {
                $("#searchContactsAppend").removeClass("searchClear");
                $("#searchContactsAppend").addClass("searchSearch");
            }
        });

        $("#searchParticipants").on("keyup", function() {
            if ($("#searchParticipants").val().trim() != "" ) {
                filterParticipants($("#searchParticipants").val());
            } else {
                $('ul#participantsList li').show();
            }
        });

        $("#inviteButton").on("click", function () {
           doInvite();
        });


        $('#participantsList').on("click", 'li', function(e) {
            e.stopPropagation();
            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                selectedParticipant.removeClass("participantSel");
                // if me
                if (selectedParticipantEid == $(this).attr("endpointID")) {
                    selectedParticipantEid = null;
                    return;
                }
            }
            selectedParticipantEid = $(this).attr("endpointID");
            $(this).addClass("participantSel");
        });

        $('#participantsList').on('click', 'li a.partDisconnect', function(e) {
            e.stopPropagation(); // prevent above event from firing (parent li click)

            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                // if me
                if (selectedParticipantEid != $(this).parent().attr("endpointID")) {
                    selectedParticipant.removeClass("participantSel");
                    selectedParticipantEid = $(this).parent().attr("endpointID");
                    $(this).parent().addClass("participantSel");
                }
            } else {
                selectedParticipantEid = $(this).parent().attr("endpointID");
                $(this).parent().addClass("participantSel");
            }

            var endpointID = $(this).parent().attr("endpointID");
            var endpointType = $(this).parent().attr("endpointType");
            var id = $(this).parent().attr("id");
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/disconnectparticipant.ajax"/>',
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        selectedParticipantEid = null;
                        $("#" + escapeCSS(id)).remove();
                    } else {
                         showMessage($(data).find("msg").text());
                    }
            });
        });

        $('#participantsList').on('click', 'li a.partMute', function(e) {
            e.stopPropagation();

            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                // if me
                if (selectedParticipantEid != $(this).parent().attr("endpointID")) {
                    selectedParticipant.removeClass("participantSel");
                    selectedParticipantEid = $(this).parent().attr("endpointID");
                    $(this).parent().addClass("participantSel");
                }
            } else {
               selectedParticipantEid = $(this).parent().attr("endpointID");
                $(this).parent().addClass("participantSel");
            }

            var objID = $(this).parent().attr("entityid");
            var url = "";
            if ($(this).hasClass("mutedSmall")) {
                url = '<c:url value="/ui/unmuteparticipant.ajax"/>'
            } else {
                url = '<c:url value="/ui/muteparticipant.ajax"/>'
            }
            var endpointID = $(this).parent().attr("endpointID");
            var endpointType = $(this).parent().attr("endpointType");
            $.ajax( {
                method: 'POST',
                url : url,
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                        if ($(data).find("message").attr("success") == "true") {
                            var tmpObj = $("ul#participantsList li[entityid='"+ escapeCSS(objID) +"'][endpointID='"+ escapeCSS(endpointID) +"'] .partMute");
                            if (tmpObj.hasClass("mutedSmall")) {
                                tmpObj.removeClass("mutedSmall").addClass("unmutedSmall");
                                tmpObj.attr("title", "<spring:message javaScriptEscape="true" code="mute.audio.on.participant.s.microphone.without.allowing.participant.to.re.enable"/>");
                            } else if (tmpObj.hasClass("unmutedSmall")) {
                                tmpObj.removeClass("unmutedSmall").addClass("mutedSmall");
                                tmpObj.attr("title", "<spring:message javaScriptEscape="true" code="unmute.audio"/>");
                            }
                        } else {
                            showMessage($(data).find("msg").text());
                        }
                    });
        });

        $('#participantsList').on('click', 'li a.partPrivacy', function(e) {
            e.stopPropagation();

            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                // if me
                if (selectedParticipantEid != $(this).parent().attr("endpointID")) {
                    selectedParticipant.removeClass("participantSel");
                    selectedParticipantEid = $(this).parent().attr("endpointID");
                    $(this).parent().addClass("participantSel");
                }
            } else {
                selectedParticipantEid = $(this).parent().attr("endpointID");
                $(this).parent().addClass("participantSel");
            }

            var objID = $(this).parent().attr("entityid");
            var url = "";
            if ($(this).hasClass("privacyonSmall")) {
                url = '<c:url value="/ui/startvideo.ajax"/>'
            } else {
                url = '<c:url value="/ui/stopvideo.ajax"/>'
            }
            var endpointID = $(this).parent().attr("endpointID");
            var endpointType = $(this).parent().attr("endpointType");
            $.ajax( {
                method: 'POST',
                url : url,
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                        if ($(data).find("message").attr("success") == "true") {
                            var tmpObj = $("ul#participantsList li[entityid='"+ escapeCSS(objID) +"'][endpointID='"+ escapeCSS(endpointID) +"'] .partPrivacy");
                            if (tmpObj.hasClass("privacyonSmall")) {
                                tmpObj.removeClass("privacyonSmall").addClass("privacyoffSmall");
                                tmpObj.attr("title", "<spring:message javaScriptEscape="true" code="disable.video.on.participant.s.camera.without.allowing.participant.to.re.enable"/>");
                            } else if (tmpObj.hasClass("privacyoffSmall")) {
                                tmpObj.removeClass("privacyoffSmall").addClass("privacyonSmall");
                                tmpObj.attr("title", "<spring:message javaScriptEscape="true" code="enable.video"/>");
                            }
                        } else {
                            showMessage($(data).find("msg").text());
                        }
                    });
        });


        $('#participantsList').on('click', 'li a.partPresenter', function(e) {
            e.stopPropagation();

            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                // if me
                if (selectedParticipantEid != $(this).parent().attr("endpointID")) {
                    selectedParticipant.removeClass("participantSel");
                    selectedParticipantEid = $(this).parent().attr("endpointID");
                    $(this).parent().addClass("participantSel");
                }
            } else {
                selectedParticipantEid = $(this).parent().attr("endpointID");
                $(this).parent().addClass("participantSel");
            }

            var objID = $(this).parent().attr("entityid");
            var endpointID = $(this).parent().attr("endpointID");
            var endpointType = $(this).parent().attr("endpointType");
            if ($(this).hasClass("presenteroffSmall")) {
                handlePresenterChange(true, objID, endpointID, endpointType);
            } else {
                handlePresenterChange(false, objID, endpointID, endpointType);
            }

        });

        $('#participantsList').on('click', 'li a.dismissHandLink', function(e) {
            e.stopPropagation();

            $(this).hide();
            if (raisedHandsCount == 1) {
                $("#dismissAllLink").hide();
            } else {
                $("#handsCounter").text(raisedHandsCount-1);
            }

            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                // if me
                if (selectedParticipantEid != $(this).parent().attr("endpointID")) {
                    selectedParticipant.removeClass("participantSel");
                    selectedParticipantEid = $(this).parent().attr("endpointID");
                    $(this).parent().addClass("participantSel");
                }
            } else {
                selectedParticipantEid = $(this).parent().attr("endpointID");
                $(this).parent().addClass("participantSel");
            }

            var objID = $(this).parent().attr("entityid");
            var url = '<c:url value="/ui/dismissHand.ajax"/>';
            var endpointID = $(this).parent().attr("endpointID");
            var endpointType = $(this).parent().attr("endpointType");
            $.ajax( {
                method: 'POST',
                url : url,
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                        if ($(data).find("message").attr("success") == "true") {
                            var tmpObj = $("ul#participantsList li[entityid='"+ escapeCSS(objID) +"'][endpointID='"+ escapeCSS(endpointID) +"']");
                            tmpObj.removeClass("handRaised");
                        } else {
                            showMessage($(data).find("msg").text());
                        }
                    });
        });

        $("#dismissAllLink").on("click", function(e) {
            e.stopPropagation();
            $(this).hide();

            var url = '<c:url value="/ui/dismissAllHands.ajax"/>';
            $.ajax( {
                method: 'POST',
                url : url,
                data : { roomID: roomID, moderatorPIN: pin}
            }).success( function(data) {
                        if ($(data).find("message").attr("success") == "true") {
                            getParticipants();
                        } else {
                            showMessage($(data).find("msg").text());
                        }
                    });

        });

        $('#invitedList').on('click', 'li a.removeInvitee', function() {
            var objID = $(this).parent().attr("entityid");
            if (objID != null) {
                removeInvited(objID);
            } else {
                removeInvitedExt($(this).parent().attr("extentityid"))
            }
            fixHeaders();
        });

        $('#selectedContactsList').on('click', 'li a.removeInvitee', function() {
            var objID = $(this).parent().attr("entityid");
            if (objID != null) {
                unInviteRegularContact(objID);
            } else {
                unInviteExternalContact($(this).parent().attr("ext"))
            }
            if (getTotalInvitees() != 0) {
                $("#noContacts").hide();
                $("#theContacts").hide();
                $("#theSelectedContacts").show();
            } else {
                $("#noContacts").show();
                $("#theContacts").hide();
                $("#theSelectedContacts").hide();
            }
            if (!$("#selectedContacts").parent().hasVerticalScrollbar()) {
                $("#selectedHeader").removeAttr("style");
            }
        });

        $('#invitedList').on('click', 'li a.reInvite', function() {

            if (!roomLocked) {
                var objID = $(this).parent().attr("entityid");

                $(this).hide();
                if (objID != null) {
                    $.ajax( {
                        method: 'POST',
                        url : '<c:url value="/ui/connectparticipant.ajax"/>',
                        data : { entityID: objID, roomID: roomID, moderatorPIN: pin}
                    }).success( function(data) {
                                var $results = $(data);
                                if ($results.find("msg").text() != "") {
                                    showMessage($results.find("msg").text());
                                }
                            });
                } else {
                    objID = $(this).parent().attr("extentityid");
                    $.ajax( {
                        method: 'POST',
                        url : '<c:url value="/ui/connectparticipant.ajax"/>',
                        data : { input: objID, roomID: roomID, moderatorPIN: pin}
                    }).success( function(data) {
                                var $results = $(data);
                                if ($results.find("msg").text() != "") {
                                    showMessage($results.find("msg").text());
                                }
                            });
                }
            } else {
                showMessage("<spring:message javaScriptEscape="true" code="room.is.locked.please.unlock.room.before.adding.participants"/>");
            }

        });

        $("#contactsDirectoryList").disableSelection();
        $("#theContacts").hide();
        $("#theSelectedContacts").hide();
        $("#contactsPage").hide();
        $("#invitedList").disableSelection();
        $("#theInvited").hide();
        $("#participantsList").disableSelection();
        $("#participantsPage").hide();
        $("#pinBox").hide();
        $("#loginBox").hide();
        $("#invalidRoomBox").hide();
        $("#startRecordingBox").hide();
        $("#optionsBox").hide();
        $("#emailBox").hide();
        if (!lectureModeFeature) {
            $("#lectureBanner").hide();
        }

        $("#waitBox").dialog( {
            autoShow: false,
            modal: true,
            draggable: false,
            resizable: false,
            closeOnEscape: false,
            create: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog).hide(); }
        } );

        $("#waitBox").dialog("close");

        if (roomID != "") {
            if (!authenticated) {
                $("#loginBox").dialog({
                    width:'auto',
                    //show: 'fade',
                    //hide: 'fade',
                    modal: true,
                    draggable: false,
                    closeOnEscape: false,
                    resizable: false,
                    create: function(event, ui) {
                        $(".ui-dialog-titlebar-close", ui.dialog).hide();
                        var $this = $(this);
                        // focus first button and bind enter to it
                        $this.parent().find('.ui-dialog-buttonpane button:first').focus();
                        $this.keypress(function(e) {
                            if( e.keyCode == 13 ) {
                                $this.parent().find('.ui-dialog-buttonpane button:first').click();
                                return false;
                            }
                        });
                    },
                    buttons: [ { text: "<spring:message javaScriptEscape="true" code="ok"/>", click: function() { handleAuth(); } } ]
                });
            } else {
                doPINCheck();
            }
        } else {
            $("#invalidRoomBox").dialog({
                width:'auto',
                //show: 'fade',
                //hide: 'fade',
                modal: true,
                draggable: false,
                closeOnEscape: false,
                resizable: false,
                create: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog).hide(); }
            });
        }

        $("#optionsBox").dialog( {
            width:'auto',
            autoShow: false,
            modal: true,
            draggable: true,
            resizable: false,
            closeOnEscape: true,
            position: ['center', 50],
            buttons: [ { text: "<spring:message javaScriptEscape="true" code="close"/>", click: function() { $("#optionsBox").dialog("close"); } }]
        } );

        $("#optionsBox").dialog("close");

        $("#startRecordingBox").dialog( {
            width:'300px',
            autoShow: false,
            modal: true,
            draggable: true,
            resizable: false,
            closeOnEscape: true,
            position: ['center', 150],
            buttons: [ { text: "<spring:message javaScriptEscape="true" code="cancel"/>", click: function() { $("#startRecordingBox").dialog("close"); } } , { text: "<spring:message javaScriptEscape="true" code="record"/>", click:
                    function() {
                        $("#startRecordingBox").dialog("close");
                        startRecording();
                    }

            } ]
        } );

        $("#startRecordingBox").dialog("close");
/*
        var bheight = $(window).height() - 150;
        $("#theContacts").height(bheight);

        $(window).resize(function() {
            var bheight = $(window).height() - 150;
            $("#theContacts").height(bheight);
        });
        */

        var bheight = $(window).height() - 250;
        $("#theParticipants").height(bheight);

        $(window).resize(function() {
            var bheight = $(window).height() - 250;
            $("#theParticipants").height(bheight);
            fixHeaders();
        });

        /* ipad keyboard causes things to shift
        $(window).resize(function() {
            var dialog = $("#" + $($(".ui-dialog :visible")[2]).attr('id'));
            if (dialog.length > 0) {
                $("#" + $($(".ui-dialog :visible")[2]).attr('id')).dialog("option", "position", "center");
            } else {
                dialog = $("#" + $($(".ui-dialog :visible")[5]).attr('id'));
                if (dialog.length > 0) {
                   $("#" + $($(".ui-dialog :visible")[5]).attr('id')).dialog("option", "position", "center");
              }
            }
        });
        */

        $("body").on("click", function(e) {
            if (selectedParticipantEid != null) {
                var selectedParticipant = $("ul#participantsList li[endpointID='"+ escapeCSS(selectedParticipantEid) +"']");
                selectedParticipant.removeClass("participantSel");
                selectedParticipantEid = null;
                e.stopPropagation();
            }
        });

        $("#recordProfiles").on("change", function(e) {
            chosenRecordingProfile = this.value;
        });

        $("#lectureModePresenterButton").on("click", function(e) {
            if (!lectureMode) {
                startLectureMode();
            }
        });

        $("#lectureModeExitButton").on("click", function(e) {
            if (lectureMode) {
                stopLectureMode();
            }
        });

    }); // ready()

    $.ajaxSetup({
        cache: false
    });

    $(document).ajaxError(function(event, jqxhr, settings, exception) {
        //if (exception == "Forbidden") {
        if (jqxhr.status == 500) {
            console.log("readyState: " + jqxhr.readyState +", status: " + jqxhr.status + ", statusText: " + jqxhr.statusText )
        } else if (jqxhr.status != 0) {
            location.reload();
        }
        //}
        //showMessage("readyState: " + jqxhr.readyState +", status" + jqxhr.status + ", statusText" + jqxhr.statusText );
    });

    function canShowEmailChoice() {
        var showWebcastLink =  canRecordMeeting && ($("#webcastLink a").text() != '' || ($("#webcastLink").val() == '' && (isMyRoom() || roomAdmin)));
        if (showWebcastLink) {
            $("#emailShowWebcastLink").show();
        } else {
            $("#emailShowWebcastLink").hide();
        }
        var showVoiceOnlyLink = (roomDialIn != "");
        if (showVoiceOnlyLink) {
            $("#emailShowVoiceLink").show();
        } else {
            $("#emailShowVoiceLink").hide();
        }
        return showWebcastLink || showVoiceOnlyLink;
    }

    function handleEmail(emailType) {
        if (emailType == 'webcast' && $("#webcastLink a").text() == '') {
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/setwebcasturl.ajax"/>',
                data : { roomID: roomID, entityID: roomID}
            }).success( function(data) {
                        getWebcastDetails();
                        showEmail(emailType);
                    });
        } else {
            showEmail(emailType);
        }
    }

    function showEmail (emailType) {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/invite_content.ajax"/>',
            data : { roomid: roomID, type: emailType, moderatorPIN: pin}
        }).success( function(data) {
                    if (canShowEmailChoice()) { // no choice if no webcasting available
                        $("#emailBox").dialog("close");
                    }
                    
                    var subject = $(data).find("subject").text();
                    var content = $(data).find("content").text();
                    window.location = 'mailto:?subject=' + encodeURIComponent($.trim(subject)) + '&body=' + encodeURIComponent($.trim(content));
                });
    }

    function showContactsPage() {
        //$("#participantsPage").toggle();
        //$("#contactsPage").toggle();
        $("#contactsPage").dialog({
            open: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog).show(); },
            close: function(event, ui) { $(".ui-dialog-titlebar-close", ui.dialog).hide(); },
            width: 600,
            height: 510,
            autoShow: false,
            modal: true,
            draggable: true,
            resizable: false,
            closeOnEscape: true,
            position: ['center', 90],
            close: function(event, ui) {
                showParticipantsPage();
            }
        } );
        contactsPageOn = true;
    }

    function showParticipantsPage() {
        //$("#contactsPage").toggle();
        //$("#participantsPage").toggle();
        contactsPageOn = false;
    }

    function handleAuth() {
        var username = $("#username").val();
        var password = $("#password").val();
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/j_spring_security_check"/>',
            data : { username: username, password: password}
        }).success( function(data) {

                    var $results = $(data);

                    if ($results.find("status").text() != "200") {
                        $("#loginBox").parent().effect("shake");
                        //showMessage("Invalid credentials.");
                        return;
                    } else {
                        var role = $results.find("role").text();
                        if (role == "ROLE_ADMIN" || role == "ROLE_OPERATOR") {
                            roomAdmin = true;
                        }
                    }

                    $("#loginBox").dialog("close");
                    // check if it is my room
                   doPINCheck();
                }).error( function(data) { alert("<spring:message javaScriptEscape="true" code="error.contacting.server.please.refresh.the.page.and.try.again"/>");});
    }


    function doPINCheck() {
        getMyContacts().success( function(data) {

            getRoomInfo().success( function (data) {
                if (!isMyRoom() && hasModeratorPin && !roomAdmin) {
                    $("#pinBox").dialog({
                        width: 'auto',
                        //show: 'fade',
                        //hide: 'fade',
                        modal: true,
                        draggable: false,
                        closeOnEscape: false,
                        resizable: false,
                        create: function(event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog).hide();
                            var $this = $(this);
                            // focus first button and bind enter to it
                            $this.parent().find('.ui-dialog-buttonpane button:first').focus();
                            $this.keypress(function(e) {
                                if( e.keyCode == 13 ) {
                                    $this.parent().find('.ui-dialog-buttonpane button:first').click();
                                    return false;
                                }
                            });
                        },
                        buttons: [ { text: "<spring:message javaScriptEscape="true" code="ok"/>", click: function() { handlePIN(); } } ]
                    });

                } else if (!isMyRoom() && !hasModeratorPin && !roomAdmin) {
                        $("#waitBox").dialog({
                        });
                } else {
                    if ($("#waitBox").dialog("isOpen")) {
                        $("#waitBox").dialog("close");
                    }
                    $("#participantsPage").show();
                    initializeRoom();
                }

            });
        });
    }

    function initializeRoom() {
        getRoomInfo();
        setTimerTimestamp();
        $("#dismissAllLink").hide();
        updateRoomState();
        getParticipants();
        getRecordingProfiles();
        getWebcastDetails();
        setUpCron();
    }

    function getRoomInfo() {
        return $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/getentities.ajax"/>',
            data : { limit: 1, entityID: roomID, list: roomID }
        }).success( function(data) {
                    var $results = $(data);
                    $results.find("entity").each(function () {
                        roomOwner = $(this).find("canControlMeeting").text() == "true";
                        canRecordMeeting = $(this).find("canRecordMeeting").text() == "true";
                        if (!canRecordMeeting) {
                            $("#recordButton").hide();
                            $("#stopButton").hide();
                            $("#pauseButton").hide();
                        }
                        if (!isMyRoom()) {
                            $("#moderatorOptions").hide();
                            $("#webcastActions").hide();
                            $("#webcastPINAction").hide();
                            $("#roomLinkActions").hide();
                            $("#roomPINAction").hide();
                        }
                        if (roomAdmin) {
                            $("#settingsButton").show();
                            $("#moderatorOptions").show();
                            $("#webcastActions").show();
                            $("#webcastPINAction").show();
                            $("#roomLinkActions").show();
                            $("#roomPINAction").show();
                        }
                        roomLocked = $(this).find("isLocked").text() == "true";
                        hasWebcastPin = $(this).find("hasWebcastPin").text() == "true";
                        hasModeratorPin = $(this).find("hasModeratorPin").text() == "true";
                        if (roomLocked) {
                            $("#lockButton").removeClass("unlocked");
                            $("#lockButton").addClass("locked");
                        }
                        roomDialIn = $(this).find("dialIn").text();
                        //roomURL = $(this).find("roomURL").text();
                        //hasRoomPin = $(this).find("hasPin").text() == "true";
                    });

                });
    }

    function setUpCron() {
        setTimerTimestamp();
        setInterval(function() { updateTimer() }, 1000);
        setInterval(function() { getParticipants() }, 5000);
        setInterval(function() { showInvited()}, 5000);
        setInterval(function() { optionalContactsSearch() }, 5000);
        setInterval(function() { updateRoomState()}, 5000);
    }

    function handlePIN() {
        pin = $("#pin").val();
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/checkroommoderatorpin.ajax"/>',
            data : { PIN: pin, entityID: roomID}
        }).success( function(data) {

                    var $results = $(data);
                    if ($results.find("errors").length > 0) {
                        $("#pinBox").parent().effect("shake");
                        //showMessage("The PIN you entered is incorrect.");
                        return;
                    }

                    $("#pinBox").dialog("close");
                    $("#participantsPage").show();
                    initializeRoom();
/*
                    $.ajax( {
                        method: 'POST',
                        url : '<c:url value="/ui/jointheconference.ajax"/>',
                        data : { entityID: roomID}
                    }).success( function(data) {
                                $("#pinBox").dialog("close");
                            }).error( function(data) { alert("could not join conference--it has a PIN?"); });
*/
                }).error( function(data) { alert("Error received from portal."); });
    }


    function isMyRoom() {
        return (myRoomID == roomID) || roomOwner;
    }

    function setRoomName(name) {
        $("#roomName").html(name);
    }

    function setTenantHostname(name) {
        $("#tenantHostname").html(name);
    }

    function optionalContactsSearch() {
        if (contactsPageOn) {
                searchContacts($("#searchContacts").val());
        }
    }

    function getParticipants() {
        if (roomID != "0") {
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/getparticipants.ajax"/>',
                data : { "limit": 1200, "entityID": roomID, "moderatorPIN": pin, "sort": sort, "dir": order}
            }).success( function(data) {
                        presenterSet = false;
                        var $results = $(data);

                        setParticipantsCount($results.find("participants").attr("total"));

                        recorderID = $results.find("participants").attr("recorderID");
                        recorderName = $results.find("participants").attr("recorderName");
                        webcastingOn = $results.find("participants").attr("isWebcast") == "true";
                        if ($.isNumeric(recorderID)) {
                            turnRecordingOn();
                        } else {
                            turnRecordingOff();
                        }
                        pauseOn = $results.find("participants").attr("isPaused") == "true";
                        if (pauseOn) {
                            turnPauseOn();
                        } else {
                            turnPauseOff();
                        }

                        $("#participantsList li").remove();

                        var prevID = null;

                        allHTML = "";
                        raisedHandsCount = 0;

                        $results.find("participant").each(function () {
                            var tmpId = $(this).find("entityID").text();
                            if (tmpId == "0" && $(this).find("endpointType").text() == "V") {
                                tmpId = htmlEscape($(this).find("displayName").text());
                            }

                            if ($(this).find("presenter").text() == "true") {
                                presenterSet = true;
                            }

                            addParticipantHTML(htmlEscape($(this).find("displayName").text()),
                                    tmpId,
                                    $(this).find("endpointID").text(),
                                    $(this).find("endpointType").text(),
                                    $(this).find("video").text(),
                                    $(this).find("audio").text(),
                                    $(this).find("presenter").text(),
                                    $(this).find("handRaised").text());
                            /*
                            addParticipant($(this).find("displayName").text(),
                                    tmpId,
                                    $(this).find("endpointID").text(),
                                    $(this).find("endpointType").text(),
                                    $(this).find("video").text(),
                                    $(this).find("audio").text(), prevID );
                            */
                            prevID = tmpId;
                        });

                        $("#participantsList").html(allHTML);
                        if (raisedHandsCount > 0) {
                            $("#handsCounter").text(raisedHandsCount);
                            $("#dismissAllLink").show();
                        } else {
                            $("#dismissAllLink").hide();
                        }

/*
                        if (selectedParticipants.length > 0) {
                            for (var i = 0; i < selectedParticipants.length; i++) {
                                var newSelected = $(".participant[entityId=" + escapeCSS(selectedParticipants[i]) + "]");
                                if (newSelected.length > 0) {
                                    newSelected.addClass("participantSel");
                                } else {
                                    removeSelectedParticipant(selectedParticipants[i]); // not found, they went offline?
                                    showMessage("Selected participant is no longer in the call.")
                                }
                            }
                        }
                        */


                        if ($("#searchParticipants").val() != "" ) {
                            filterParticipants($("#searchParticipants").val());
                        }

                        fixHeaders();
                        setUpLectureModeTextAndButtons();

                    });
        }
    }

    function searchContacts(searchText) {
        if (searchText == '') {
            if (getTotalInvitees() != 0) {
                $("#noContacts").hide();
                $("#theContacts").hide();
                $("#theSelectedContacts").show();
            } else {
                $("#noContacts").show();
                $("#theContacts").hide();
                $("#theSelectedContacts").hide();
            }
            return;
        } else {
            $("#noContacts").hide();
            $("#theSelectedContacts").hide();
            $("#theContacts").show();
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/search.ajax"/>',
            data : { limit: 50, excluderooms : false, start: 0, query : searchText}
        }).success( function(data) {
                    var $results = $(data);
                    var scrollLocation = $("#theContacts ul").scrollTop();
                    clearContacts();
                    //setContactsCount($results.find("entities").attr("total"));
                        $results.find("entity").each(function () {
                            var displayName = htmlEscape($(this).find("displayName").text());
                            var status = $(this).find("memberStatus").text();
                            var entityType = $(this).find("entityType").text();

                            if (entityType != "Room") { // "Schedule Rooms" never returned
                                addContact(displayName,
                                        status,
                                    $(this).find("entityID").text(),
                                    $(this).find("isInMyContacts").text());
                            }
                        });

                    if (contactsDirCount == 0) {
                        $("#dirContacts").hide();
                        addExtContact(searchText);
                    } else {
                        $("#dirContacts").show();
                    }

                    $("#theContacts ul").scrollTop(scrollLocation);
                });
    }

    function getMyContacts() {
        return $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/contacts.ajax"/>',
            data : { limit: 200}
        }).success( function(data) {
                    var $results = $(data);

                    myRoomID = $results.find("myRoom").find("entityID").text();
                    myEID = $results.find("myRoom").find("EID").text();


                    if (contactsDirCount == 0) {
                        $("#dirContacts").hide();
                    } else {
                        $("#dirContacts").show();
                    }

                    $("#extContacts").hide();

                });
    }

    function clearContacts() {
        contactsDirCount = 0;
        $("#contactsDirectoryList li").remove();
        $("#dirContacts").hide();
        $("#contactsExternalList li").remove();
        $("#extContacts").hide();
        if (!$("#dirContacts").hasVerticalScrollbar()) {
            $("#directoryHeader").removeAttr("style");
        }
    }

    function clearParticipants() {
        setParticipantsCount(0);
    }

    function incrementDirContactsCount() {
        ++contactsDirCount;
    }

    function setParticipantsCount(amount) {
        participantsCount = amount;
        $("#participantsCount").html(amount);
        if (amount == 0) {
            $("#participantsList li").remove();
            $("#noParticipants").show();
        } else {
            $("#noParticipants").hide();
        }
        fixHeaders();
    }

    function htmlEscape (value) {
        return String(value)
            .replace(/&/g, '&amp;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
    }


    function addContact(name, status, entityID, isInMyContacts) {

        if (contactsDirCount == 0) {
            $("#contactsDirectoryList li").remove();
        }
        $("#contactsDirectoryList").append('<li class="contact" entityID="'+ entityID +'" id="contact'+ entityID + '"><span class="contactName">'+ htmlEscape(name) + '</span></li>');
        $("#contact" + entityID).addClass(getClassFromStatus(status));
        incrementDirContactsCount();

        if (isRegularInvitee(entityID)) {
            $("#contact" + entityID).addClass("contactSel");
        }
        if (status == "Online" || status == "External" || status == "") {
            $("#contact" + entityID).on("click", function () {
                if (!$(this).hasClass("contactSel")) {
                    $(this).toggleClass("contactSel");
                    //$(this).effect("transfer", {to : $("#inviteButton")}, 300);
                    inviteRegularContact(entityID);
                } else {
                    // $("#inviteButton").effect("transfer", {to : $(this)}, 300, function() {
                    //     $("#contact" + entityID).toggleClass("contactSel"); });
                    $(this).toggleClass("contactSel");
                    unInviteRegularContact(entityID);
                }
            });
        }
    }

    function getClassFromStatus(status) {
        if (status == "Online") {
            return "contactOnline";
        } else if (status == "Busy" || status == "BusyInOwnRoom") {
            return "contactBusy";
        } else if (status == "Offline") {
            return "contactOffline";
        } else if (status == "Ringing" || status == "WaitJoinConfirm") {
            return "inviteeRinging";
        } else if (status == "External") {
            return "contactExt";
        } else {
            return "contactExt";
        }
    }

    function addExtContact(searchInput) {
        $("#contactsExternalList li").remove();
        var searchEsc = $('<div/>').text(searchInput).html();  // magic div does html escaping
        var entityID = searchInput;
        $("#contactsExternalList").append('<li class="contact contactExt" ext="' + searchEsc + '">'+  searchEsc +'</li>');
        $("#extContacts").show();

        if (isExternalInvitee(entityID)) {
            $("#contactsExternalList .contact[ext='" + entityID +"']").addClass("contactSel");
        }
        $("#contactsExternalList .contact[ext='" + entityID +"']").disableSelection();
        $("#contactsExternalList .contact[ext='" + entityID +"']").on("click", function () {
            if (!$(this).hasClass("contactSel")) {
                $(this).toggleClass("contactSel");
              //  $(this).effect("transfer", {to : $("#inviteButton")}, 300);
                inviteExternalContact(entityID);
            } else {
             //   $("#inviteButton").effect("transfer", {to : $(this)}, 300, function() {
             //       $(".contact[ext='" + entityID +"']").toggleClass("contactSel"); });
                $(this).toggleClass("contactSel");
                unInviteExternalContact(entityID);
            }
        });
    }

    function isRegularInvitee(entityID) {
        var index = jQuery.inArray(entityID, inviteesRegular);
        return index >= 0;
    }

    function isExternalInvitee(str) {
        var index = jQuery.inArray(str, inviteesExternal);
        return index >= 0;
    }

    function isInvitee(str) {
        return isRegularInvitee(str) || isEternalInvitee(str);
    }

    function isRegularInviteeAlreadyInvited(entityID) {
        var index = jQuery.inArray(entityID, invitedRegular);
        return index >= 0;
    }

    function isExternalInviteeAlreadyInvited(str) {
        var index = jQuery.inArray(str, invitedExternal);
        return index >= 0;
    }

    function inviteRegularContact(entityID) {
        inviteesRegular.push(entityID);
        copyToSelectedContacts(entityID, false);
        updateInviteButton();
    }

    function unInviteRegularContact(entityID) {
        var index = jQuery.inArray(entityID, inviteesRegular);
        if (index >= 0) {
            inviteesRegular.splice(index, 1);
        }
        removeFromSelectedContacts(entityID,false);
        updateInviteButton();
    }

    function inviteExternalContact(entityID) {
        inviteesExternal.push(entityID);
        copyToSelectedContacts(entityID, true);
        updateInviteButton();
    }

    function unInviteExternalContact(entityID) {
        var index = jQuery.inArray(entityID, inviteesExternal);
        if (index >= 0) {
            inviteesExternal.splice(index, 1);
        }
        removeFromSelectedContacts(entityID, true);
        updateInviteButton();
    }

    function copyToSelectedContacts(entityID, isExternal) {
        var theID = "";
        var theParentID = "";
        if (!isExternal) {
            theID = "entityid";
            theParentID = "contactsDirectoryList";
        } else {
            theID = "ext";
            theParentID = "contactsExternalList";
        }

        var item = $("#" + theParentID + " li[" + theID + "='" + escapeCSS(entityID) + "']").clone();
        item.removeClass("contactSel");
        item.attr("id", "");
        item.append("<a class='removeInvitee' href='#'></a>");

        $("#selectedContactsList").append(item);
    }

    function removeFromSelectedContacts(entityID, isExternal) {
        var theID = "";
        if (!isExternal) {
            $("#selectedContactsList li[entityid=" + entityID + "]").remove();
        } else {
            $("#selectedContactsList li[ext='" + escapeCSS(entityID) + "']").remove();
        }
    }

    function addSelectedParticipant(entityID) {
        selectedParticipants.push(entityID);
        updateSelectedParticipantCount();
    }

    function removeSelectedParticipant(entityID) {
        var index = jQuery.inArray(entityID, selectedParticipants);
        if (index >= 0) {
            selectedParticipants.splice(index, 1);
        }
        updateSelectedParticipantCount();
    }

    function updateSelectedParticipantCount() {
        //$("#selectedParticipantsCount").html(selectedParticipants.length);
        updateActions();
    }

    function updateInviteButton() {
        var totalInvitees = getTotalInvitees();
        if (totalInvitees == 0) {
            $("#inviteButton").attr('value', '<spring:message javaScriptEscape="true" code="invite"/>');
            $("#inviteButton").removeClass("manyinvitees");
            $("#inviteButton").addClass("noinvitees");
            $("#inviteButton").addClass("opaque");
        } else {
            $("#inviteButton").attr('value', "<spring:message javaScriptEscape="true" code="invite"/> ("+ totalInvitees +")");
            $("#inviteButton").removeClass("noinvitees");
            $("#inviteButton").addClass("manyinvitees");
            $("#inviteButton").removeClass("opaque");
        }
    }


    function getTotalInvitees() {
        return inviteesRegular.length + inviteesExternal.length;
    }

    function getTotalInvited() {
        return invitedRegular.length + invitedExternal.length;
    }

    function doInvite() {
        if (getTotalInvitees() == 0) {
            return;
        }
        var foundOne = false;
        var numericInvitees = new Array();
        var stringInvitees = new Array();
        for (var i = 0; i < inviteesRegular.length; i++) {
            if (isRegularInviteeAlreadyInvited(inviteesRegular[i]) || isParticipant(inviteesRegular[i])) {
                continue;
            }
            foundOne = true;
            var index = jQuery.inArray(inviteesRegular[i], invitedRegular);
            if (index < 0) {
                invitedRegular.push(inviteesRegular[i]);
            }
            numericInvitees.push(inviteesRegular[i]);
        }
        for (var i = 0; i < inviteesExternal.length; i++) {
            //if (isExternalInviteeAlreadyInvited(inviteesExternal[i]) || isParticipant(inviteesExternal[i])) {
            //    continue;
            //}
            foundOne = true;
            //var index = jQuery.inArray(inviteesExternal[i], invitedExternal);
            //if (index < 0) {
                invitedExternal.push(inviteesExternal[i]);
            //}
            stringInvitees.push(inviteesExternal[i]);
        }

        if (!roomLocked) {
            if (foundOne) {
                $.ajax( {
                    method: 'POST',
                    url : '<c:url value="/ui/connectparticipants.ajax"/>',
                    data : { list: numericInvitees.toString(), inputlist: stringInvitees.toString(), roomID: roomID, moderatorPIN: pin}
                }).success( function(data) {
                            if ($(data).find("message").attr("success") == "false") {
                                if ($(data).find("msg").length == 1) {
                                    showMessage($(data).find("msg"));
                                } else {
                                    showMessage("<spring:message javaScriptEscape="true" code="some.users.could.not.be.invited"/>");
                                }
                            }
                        });
            }
        } else {
            showMessage("<spring:message javaScriptEscape="true" code="room.is.locked.please.unlock.room.before.adding.participants"/>");
        }

        uninviteAll();
        showInvited();
        showParticipantsPage();
        $("#contactsPage").dialog("close");
    }

    function uninviteAll() {
        for (var i = 0; i < inviteesRegular.length; i++) {
            if ($("#contact" + inviteesRegular[i]).length != 0) {
                $("#contact" + inviteesRegular[i]).removeClass("contactSel");
            }
        }
        for (var i = 0; i < inviteesExternal.length; i++) {
            if ($(".contact[ext='" + inviteesExternal[i] +"']").length != 0) {
                $(".contact[ext='" + inviteesExternal[i] +"']").removeClass("contactSel");
            }
        }

        inviteesRegular = new Array();
        inviteesExternal = new Array();
        $("#selectedContactsList li").remove();
        if ($("#searchContacts").val() == '') {
            $("#noContacts").show();
            $("#theContacts").hide();
            $("#theSelectedContacts").hide();
        }
        updateInviteButton();
    }

    function showInvited() {
        if (getTotalInvited() != 0) {
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/getentities.ajax"/>',
                data : { limit: 200, entityID: roomID, list: invitedRegular.toString()}
            }).success( function(data) {
                        var $results = $(data);

/*
                        var invitedParticipant = $("#invitedList .invitedSel");
                        var selectedId;
                        if (invitedParticipant.length == 1) {
                            selectedId = invitedParticipant.attr("id");
                        }
*/

                        clearInvited();

                        $results.find("entity").each(function () {
                            addInvitedRegular(htmlEscape($(this).find("displayName").text()),
                                    $(this).find("entityID").text(),
                                    $(this).find("memberStatus").text());
                        });

                        // handle external invitee
                        for (var i = 0; i < invitedExternal.length; i++) {
                           addInvitedExternal(invitedExternal[i], invitedExternal[i], 'External');
                        }

/*                        if (invitedParticipant.length == 1) {
                            var newSelected = $("#" + escapeCSS(selectedId));
                            if (newSelected.length > 0) {
                                newSelected.addClass("invitedSel");
                                newSelected.children("a").remove();
                                newSelected.append("<a class='removeInvitee' href='#'></a>");

                                if (!(newSelected.hasClass('inviteeRinging'))) {
                                    newSelected.append("<a class='reInvite' href='#'></a>");
                                }
                            }
                        }*/


                        $("#inviteesCount").html(getTotalInvited());
                        if (getTotalInvited() > 0) {
                            $("#theInvited").show();
                        }

                    });
        } else {
            $("#theInvited").hide();
        }
    }

    function clearInvited() {
        $("#invitedList li").remove();
    }

    function addInvitedRegular(name, entityID, memberStatus) {

        if (isParticipant(entityID)) {
            removeInvited(entityID);
        } else {

            $("#invitedList").append('<li class="invited" entityID="' + entityID +
                    '" id="invited'+ entityID +
                    '">'+ name + '</li>');


            var tmpInvited = $("#invited" + escapeCSS(entityID));
            tmpInvited.addClass(getClassFromStatus(memberStatus));

            tmpInvited.append("<a class='removeInvitee' href='#'></a>");
            if (!(tmpInvited.hasClass('inviteeRinging'))) {
                tmpInvited.append("<a class='reInvite' href='#'></a>");
            }

/*
            tmpInvited.on("click", function () {
                var amIAlreadySelected = $(this).hasClass("invitedSel");
                $("#invitedList li").removeClass("invitedSel");
                if (amIAlreadySelected) {
                    $(this).removeClass("invitedSel"); // unselect
                } else {
                    $(this).addClass("invitedSel");
                }

                var amISelectedNow = $(this).hasClass("invitedSel");
                if (amISelectedNow) {
                    $(this).children("a").remove();
                    $(this).append("<a class='removeInvitee' href='#'></a>");

                    if (!($(this).hasClass('inviteeRinging'))) {
                        $(this).append("<a class='reInvite' href='#'></a>");
                    }
                }
            });
            */
        }
    }

    function addInvitedExternal(name, entityID, memberStatus) {

        if (isParticipant(entityID)) {
            removeInvitedExt(entityID);
        } else {

            $("#invitedList").append('<li class="invited" extEntityID="' + entityID +
                    '" id="invitedExt'+ entityID +
                    '"></li>');


            var tmpInvited = $("#invitedExt" + escapeCSS(entityID));
            tmpInvited.text(name);
            tmpInvited.addClass(getClassFromStatus(memberStatus));

            tmpInvited.append("<a class='removeInvitee' href='#'></a>");
            if (!(tmpInvited.hasClass('inviteeRinging'))) {
                tmpInvited.append("<a class='reInvite' href='#'></a>");
            }

            /*
             tmpInvited.on("click", function () {
             var amIAlreadySelected = $(this).hasClass("invitedSel");
             $("#invitedList li").removeClass("invitedSel");
             if (amIAlreadySelected) {
             $(this).removeClass("invitedSel"); // unselect
             } else {
             $(this).addClass("invitedSel");
             }

             var amISelectedNow = $(this).hasClass("invitedSel");
             if (amISelectedNow) {
             $(this).children("a").remove();
             $(this).append("<a class='removeInvitee' href='#'></a>");

             if (!($(this).hasClass('inviteeRinging'))) {
             $(this).append("<a class='reInvite' href='#'></a>");
             }
             }
             });
             */
        }
    }


    function escapeCSS( str) {
        if( str)
            return str.replace(/([ #;?%&,.+*~\':"!^$[\]()=<>|\/@])/g,'\\\$1')
        else
            return str;
    }


    function isParticipant(entityID) {
       return ($("ul#participantsList li[entityid='"+ escapeCSS(entityID) +"']").length != 0);
    }

    function isInvited(entityID) {
      return ($("#invited" + escapeCSS(entityID)).length != 0);
    }

    function isInvitedExt(entityID) {
        return ($("#invitedExt" + escapeCSS(entityID)).length != 0);
    }

    function removeInvited(entityID) {
        invitedRegular = jQuery.grep(invitedRegular, function(value) {
            return value != entityID;
        });
        if ($("#invited" + escapeCSS(entityID)).length != 0) {
            $("#invited" + escapeCSS(entityID)).remove();
        }
        $("#inviteesCount").html(getTotalInvited());
        if (getTotalInvited() == 0) {
            $("#theInvited").hide();
        }
    }

    function removeInvitedExt(entityID) {
        invitedExternal = jQuery.grep(invitedExternal, function(value) {
            return value != entityID;
        });
        if ($("#invitedExt" + escapeCSS(entityID)).length != 0) {
            $("#invitedExt" + escapeCSS(entityID)).remove();
        }
        $("#inviteesCount").html(getTotalInvited());
        if (getTotalInvited() == 0) {
            $("#theInvited").hide();
        }
    }

    function addParticipant(name, entityID, endpointID, endpointType, video, audio) {

        if (isInvited(entityID)) {
            removeInvited(entityID);
        }
        if (isInvitedExt(entityID)) {
            removeInvitedExt(entityID);
        }

        if (audio == 'true') {
            muteClass ="unmutedSmall";
        } else {
            muteClass ="mutedSmall";
        }
        if (video == 'true') {
            privacyClass ="privacyoffSmall";
        } else {
            privacyClass ="privacyonSmall";
        }

        $("#participantsList").append(
                '<li entityID="' + entityID +
                '" endpointID="' + endpointID +
                '" endpointType="' + endpointType +
                //'" id="participant'+ entityID +
                '">'+ name +
                "<a class='partDisconnect' href='#'></a><a class='partMute " + muteClass + "' href='#'></a><a class='partPrivacy "+ privacyClass + "' href='#'></a>" +
                '</li>');

        /*
        tmpPart.on("click", function () {
            var amIAlreadySelected = $(this).hasClass("participantSel");
            if (amIAlreadySelected) {
                $(this).removeClass("participantSel");
                removeSelectedParticipant($(this).attr('entityID'));
            } else {
                $(".participantSel").removeClass("participantSel");
                selectedParticipants = new Array();
                $(this).addClass("participantSel");
                addSelectedParticipant($(this).attr('entityID'));
            }
        });
        */
    }

    var allHTML = "";

    function addParticipantHTML(name, entityID, endpointID, endpointType, video, audio, presenter, handRaised) {

        if (isInvited(entityID)) {
            removeInvited(entityID);
        }
        if (isInvitedExt(entityID)) {
            removeInvitedExt(entityID);
        }


        var muteClass, privacyClass, selectedClass, presenterClass, handRaisedClass;
        var audioTitle, videoTitle, presenterTitle;

        if (audio == 'true') {
            muteClass ="unmutedSmall";
            audioTitle = "<spring:message javaScriptEscape="true" code="mute.audio.on.participant.s.microphone.without.allowing.participant.to.re.enable"/>";
        } else {
            muteClass ="mutedSmall";
            audioTitle = "<spring:message javaScriptEscape="true" code="unmute.audio"/>";
        }
        if (video == 'true') {
            privacyClass ="privacyoffSmall";
            videoTitle = "<spring:message javaScriptEscape="true" code="disable.video.on.participant.s.camera.without.allowing.participant.to.re.enable"/>";
        } else {
            privacyClass ="privacyonSmall";
            videoTitle = "<spring:message javaScriptEscape="true" code="enable.video"/>";
        }
        if (presenter == 'true') {
            //name = name + " (Presenting)";
            presenterClass = "presenteronSmall";
            presenterTitle = "<spring:message javaScriptEscape="true" code="remove.presenter.rights.from.participant"/>";
        } else{
            presenterClass = "presenteroffSmall";
            presenterTitle = "<spring:message javaScriptEscape="true" code="set.participant.as.presenter"/>";
        }


        if (selectedParticipantEid != null && selectedParticipantEid == endpointID) {
            selectedClass = "participantSel";
        } else {
            selectedClass = ""
        }

        handRaisedTitle = '<spring:message htmlEscape="true" javaScriptEscape="true" code="this.participant.has.raised.their.hand.to.grant.permission.to.speak.unmute.their.microphone.alternatively.you.can.dismiss.the.request.by.clicking.dismiss.request"/>';

        if (handRaised == 'true') {
            handRaisedClass = "handRaised";
            name = '<span style="margin-left: -20px; padding-left: 20px;" title="' + handRaisedTitle + '">' + name + "</span> <a href='#' class='dismissHandLink'><spring:message htmlEscape="true" javaScriptEscape="true" code="dismiss.hand"/></a>";

            raisedHandsCount++;
        } else {
            handRaisedClass = "";
        }

        allHTML = allHTML +
                '<li entityID="' + entityID +
                        '" endpointID="' + endpointID +
                        '" endpointType="' + endpointType +
                        '" class="' + selectedClass + ' ' + handRaisedClass +
                    //'" id="participant'+ entityID +
                        '">'+ name +
                        '<a class="partDisconnect" href="#" title="<spring:message javaScriptEscape="true" code="disconnect.participant"/>"></a><a class="partMute ' + muteClass + '" title="' + audioTitle + '" href="#"></a><a class="partPrivacy '+ privacyClass + '" title="' + videoTitle + '" href="#"></a>';
        if (lectureMode){
            allHTML =  allHTML + '<a class="partPresenter ' + presenterClass + '" title="' + presenterTitle +'" href="#"></a>';

        }
        allHTML = allHTML + '</li>';

    }

    function updateParticipantUI(name, entityID, endpointID, endpointType, video, audio, prevEntityID) {

        if (isInvited(entityID)) {
            removeInvited(entityID);
        }

        if (audio == 'true') {
            muteClass ="unmutedSmall";
        } else {
            muteClass ="mutedSmall";
        }
        if (video == 'true') {
            privacyClass ="privacyoffSmall";
        } else {
            privacyClass ="privacyonSmall";
        }

        presenterClass = "presenteroffSmall";

        var searchPart = $(".participant[entityid='" + escapeCSS(entityID) + "']");
        if (searchPart.length == 0) { // not found, add it
            var nodeToAppend=$("#participantsList");
            if (prevEntityID == undefined || prevEntityID == null) {
                var prevPart = $("#" + escapeCSS(prevEntityID));
                if (prevPart.length != 0) {
                    nodeToAppend = prevPart;
                }
            }

           nodeToAppend.append(
                    '<li class="participant contactOnline " entityID="' + entityID +
                            '" endpointID="' + endpointID +
                            '" endpointType="' + endpointType +
                            '" id="participant'+ entityID +
                            '">'+ name +
                            "<a class='partDisconnect' href='#'></a><a class='partMute " + muteClass + "' href='#'></a><a class='partPrivacy "+ privacyClass + "' href='#'></a>");
            if (lectureMode){
                nodeToAppend.append("<a class='partPresenter "+ presenterClass + "' href='#'></a>");
            }
                            nodeToAppend('</li>');
        } else {
            // update it
            // TODO
        }

        var actualPrev = searchPart.prev();
        if (actualPrev.length != 0) {
            var actualPrevEntityID = actualPrev.attr("entityid");
            if (actualPrevEntityID != prevEntityID) {
                actualPrev.remove();
            }
        }


    }


    function setDateTime() {
        var d = new Date();
        var hh = d.getHours();
        var m = d.getMinutes();
        var s = d.getSeconds();
        var dd = "AM";
        var h = hh;
        if (h >= 12) {
            h = hh-12;
            dd = "PM";
        }
        if (h == 0) {
            h = 12;
        }
        m = m<10?"0"+m:m;
        s = s<10?"0"+s:s;

        $("#timerTime").html((d.getMonth() + 1) + '/' + d.getDate() + '/' + d.getFullYear() + '<br />' +
              h + ":" + m + " " + dd);
    }

    function setTimerTimestamp() {
        var h = parseInt(joinSeconds/3600) % 24;
        var m = parseInt(joinSeconds/60) % 60;
        var s = joinSeconds % 60;
        var str = (h < 10 ? "0" + h : h) + ":" + (m < 10 ? "0" + m : m) + ":" + (s  < 10 ? "0" + s : s);
        $("#timerTime").html(str);
    }

    function updateTimer() {
        joinSeconds++;
        if (showTime) {
            setDateTime();
        } else {
            setTimerTimestamp();
        }
    }

    function doCommand(cmd) {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/controlmeeting.ajax"/>',
            data : { action: cmd, roomID: roomID, moderatorPIN: pin}
        }).success( function(data) {

                }).error( function() {

                });
    }

    function toggleRoomLock() {
         var command  = "";
         if (roomLocked) {
             command = "unlock";
         } else {
             command = "lock";
         }

        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/controlmeeting.ajax"/>',
            data : { action: command, roomID: roomID, moderatorPIN: pin}
        }).success( function(data) {
            if (command == "unlock") {
                $("#lockButton").removeClass("locked");
                $("#lockButton").addClass("unlocked");
                roomLocked = false;
                showMessage("<spring:message javaScriptEscape="true" code="room.unlocked"/>");
             } else {
                $("#lockButton").removeClass("unlocked");
                $("#lockButton").addClass("locked");
                roomLocked = true;
                showMessage("<spring:message javaScriptEscape="true" code="room.is.now.locked.additional.participants.cannot.join.the.conference"/>");
            }
                }).error( function() {
                    alert("Failed to toggle room lock.");
                });
    }

    function toggleMuteSelectedParticipants() {
        var command  = "";
        var url = "";
        if ($("#muteAllButton").hasClass("muted")) {
            command = "unmute";
            url = "<c:url value="/ui/unmuteparticipant.ajax"/>";
        } else {
            command = "mute";
            url = "<c:url value="/ui/muteparticipant.ajax"/>";
        }

        for (var i = 0 ; i < selectedParticipants.length; i++) {
            var objID = selectedParticipants[i];
            var participant = $(".participant[entityID='" + escapeCSS(objID) + "']");
            var endpointID = participant.attr("endpointID");
            var endpointType = participant.attr("endpointType");
            $.ajax( {
                method: 'POST',
                url : url,
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                        if (command == "unmute") {
                            $("#muteAllButton").removeClass("muted");
                            $("#muteAllButton").addClass("unmuted");
                            for (var i = 0 ; i < selectedParticipants.length; i++) {
                                $("#participant" + escapeCSS(selectedParticipants[i]) + " .partMute").removeClass("mutedSmall").addClass("unmutedSmall");
                            }
                        } else {
                            $("#muteAllButton").removeClass("unmuted");
                            $("#muteAllButton").addClass("muted");
                            for (var i = 0 ; i < selectedParticipants.length; i++) {
                                $("#participant" + escapeCSS(selectedParticipants[i]) + " .partMute").removeClass("unmutedSmall").addClass("mutedSmall");
                            }
                        }
                    });

        }
    }


    function toggleMuteAll() {
        var command  = "";
        if (allMuted) {
            command = "unmute";
        } else {
            command = "mute";
        }

        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/controlmeeting.ajax"/>',
            data : { action: command, roomID: roomID, moderatorPIN: pin}
        }).success( function(data) {
                    if (command == "unmute") {
                        $("#muteAllButton").removeClass("muted");
                        $("#muteAllButton").addClass("unmuted");
                        $("#muteAllButton").attr("title", "<spring:message javaScriptEscape="true" code="mute.audio.on.participants.microphones.without.allowing.participants.to.re.enable"/>");
                        allMuted = false;
                        //showMessage("Force mute has been disabled. Participants may now unmute themselves.");
                    } else {
                        $("#muteAllButton").removeClass("unmuted");
                        $("#muteAllButton").addClass("muted");
                        $("#muteAllButton").attr("title", "<spring:message javaScriptEscape="true" code="unmute.audio"/>");
                        allMuted = false;
                        allMuted = true;
                        //showMessage("All participants have been force muted. Participants will not be able to unmute themselves.");
                    }
                    getParticipants();
                }).error( function() {
                    alert("Failed to toggle mute all.");
                });
    }

    function toggleMuteVideoSelectedParticipants() {
        var command  = "";
        var url = "";
        if ($("#muteVideoAllButton").hasClass("mutedVideo")) {
            command = "unmuteVideo";
            url = "<c:url value="/ui/startvideo.ajax"/>";
        } else {
            command = "muteVideo";
            url = "<c:url value="/ui/stopvideo.ajax"/>";
        }

        for (var i = 0 ; i < selectedParticipants.length; i++) {
            var objID = selectedParticipants[i];
            var participant = $(".participant[entityID='" + escapeCSS(objID) + "']");
            var endpointID = participant.attr("endpointID");
            var endpointType = participant.attr("endpointType");
            $.ajax( {
                method: 'POST',
                url : url,
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                        if (command == "unmuteVideo") {
                            $("#muteVideoAllButton").removeClass("mutedVideo");
                            $("#muteVideoAllButton").addClass("unmutedVideo");

                            for (var i = 0 ; i < selectedParticipants.length; i++) {
                                $("#participant" + escapeCSS(selectedParticipants[i]) + " .partPrivacy").removeClass("privacyonSmall").addClass("privacyoffSmall");
                            }
                        } else {
                            $("#muteVideoAllButton").removeClass("unmutedVideo");
                            $("#muteVideoAllButton").addClass("mutedVideo");
                            for (var i = 0 ; i < selectedParticipants.length; i++) {
                                $("#participant" + escapeCSS(selectedParticipants[i]) + " .partPrivacy").removeClass("privacyoffSmall").addClass("privacyonSmall");
                            }
                        }
                    });

        }
    }

    function toggleMuteVideoAll() {
        var command  = "";
        if (allMutedVideo) {
            command = "unmuteVideo";
        } else {
            command = "muteVideo";
        }

        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/controlmeeting.ajax"/>',
            data : { action: command, roomID: roomID, moderatorPIN: pin}
        }).success( function(data) {
                    if (command == "unmuteVideo") {
                        $("#muteVideoAllButton").removeClass("mutedVideo");
                        $("#muteVideoAllButton").addClass("unmutedVideo");
                        $("#muteVideoAllButton").attr("title", "<spring:message javaScriptEscape="true" code="disable.video.on.participants.cameras.without.allowing.participants.to.re.enable"/>");
                        allMutedVideo = false;
                        //showMessage("Forced privacy has been disabled. Participants may now turn on their video themselves.");
                    } else {
                        $("#muteVideoAllButton").removeClass("unmutedVideo");
                        $("#muteVideoAllButton").addClass("mutedVideo");
                        $("#muteVideoAllButton").attr("title", "<spring:message htmlEscape="true" javaScriptEscape="true" code="enable.video"/>");
                        allMutedVideo = true;
                        //showMessage("All participants have forced privacy on. Participants will not be able to turn on their video themselves.");
                    }
                    getParticipants();
                }).error( function() {
                    alert("Failed to toggle privacy all.");
                });
    }

    function disconnectSelectedParticipants() {
        for (var i = 0 ; i < selectedParticipants.length; i++) {
            var objID = selectedParticipants[i];
            var participant = $(".participant[entityID='" + escapeCSS(objID) + "']");
            var endpointID = participant.attr("endpointID");
            var endpointType = participant.attr("endpointType");
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/disconnectparticipant.ajax"/>',
                data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
            }).success( function(data) {
                        removeSelectedParticipant(objID);
                    });
        }
    }

    function disconnectAll() {
        var command  = "disconnectAll";
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/controlmeeting.ajax"/>',
            data : { action: command, roomID: roomID, moderatorPIN: pin}
        }).success( function(data) {
                  clearParticipants();
                    showMessage("<spring:message javaScriptEscape="true" code="all.participants.have.been.disconnected"/>");
                }).error( function() {
                    alert("Failed to disconnect all.");
                });
    }

    function silenceAll() {
        if (!$("#silenceAllButton").hasClass("silenceAllPress")) {
            var command  = "silence";
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/controlmeeting.ajax"/>',
                data : { action: command, roomID: roomID, moderatorPIN: pin}
            }).success( function(data) {
                        //showMessage("All participants have been silenced.");
                    }).error( function() {
                        alert("Failed to silence all.");
                    }).complete( function () {
                        $("#silenceAllButton").removeClass("silenceAll").addClass("silenceAllPress").delay(3000);
                        $("#silenceAllButton").effect("fade", resetSilenceAll);
                    });
        }
    }

    function resetSilenceAll() {
        $("#silenceAllButton").removeClass("silenceAllPress").addClass("silenceAll").effect("fade");
    }


    function silenceVideoAll() {
        if (!$("#silenceVideoAllButton").hasClass("silenceVideoAllPress")) {
            var command  = "silenceVideo";
            $.ajax( {
                method: 'POST',
                url : '<c:url value="/ui/controlmeeting.ajax"/>',
                data : { action: command, roomID: roomID, moderatorPIN: pin}
            }).success( function(data) {
                        //showMessage("All participants have been video silenced.");
                    }).error( function() {
                        alert("Failed to silence video all.");
                    }).complete( function () {
                        $("#silenceVideoAllButton").removeClass("silenceVideoAll").addClass("silenceVideoAllPress").delay(3000);
                        $("#silenceVideoAllButton").effect("fade", resetSilenceVideoAll);
                    });
        }
    }

    function resetSilenceVideoAll() {
        $("#silenceVideoAllButton").removeClass("silenceVideoAllPress").addClass("silenceVideoAll").effect("fade");
    }

    // An implementation of a case-insensitive contains pseudo
    // made for all versions of jQuery
    (function( $ ) {

        function icontains( elem, text ) {
            return (
                    elem.textContent ||
                            elem.innerText ||
                            $( elem ).text() ||
                            ""
                    ).toLowerCase().indexOf( (text || "").toLowerCase() ) > -1;
        }

        $.expr[':'].icontains = $.expr.createPseudo ?
                $.expr.createPseudo(function( text ) {
                    return function( elem ) {
                        return icontains( elem, text );
                    };
                }) :
                function( elem, i, match ) {
                    return icontains( elem, match[3] );
                };

    })( jQuery );

    function filterParticipants(inputVal) {
        if ($("#participantsCount").text() == "0") {
            return;
        }
        $("#participantsList li").hide();
        $('#participantsList li:icontains("' + inputVal + '")').show();
    }

    /*
    function filterParticipants(inputVal) {
        if ($("#participantsCount").text() == "0") {
            return;
        }
        var participantsList = $('ul#participantsList');
        participantsList.find('li').each(
                function(index, row) {
                    if(participantsList.length > 0) {
                        var regExp = new RegExp(inputVal, 'i');
                        if(regExp.test($(row).text())) {
                            $(row).show();
                        } else {
                            $(row).hide();
                        }
                    }
                });
    }
    */

    function isParticipantSelected() {
        return selectedParticipants.length != 0;
    }

    function updateActions() {
        /*
        if (!isParticipantSelected()) {
            updateActionsToEffectAllParticipants();
        } else {
            updateActionsToEffectSelectedParticipants();
        }
        */
    }

    function updateActionsToEffectAllParticipants() {
        if (allMutedVideo) {
            $("#muteVideoAllButton").removeClass("unmutedVideo");
            $("#muteVideoAllButton").addClass("mutedVideo");
        } else {
            $("#muteVideoAllButton").removeClass("mutedVideo");
            $("#muteVideoAllButton").addClass("unmutedVideo");
        }
        $("#silenceVideoAllButton").removeClass("silenceVideoAllPress").removeClass("silenceVideoAllDisabled").addClass("silenceVideoAll");
        if (allMuted) {
            $("#muteAllButton").removeClass("unmuted");
            $("#muteAllButton").addClass("muted");
        } else {
            $("#muteAllButton").removeClass("muted");
            $("#muteAllButton").addClass("unmuted");
        }
        $("#silenceAllButton").removeClass("silenceAllPress").removeClass("silenceAllDisabled").addClass("silenceAll");
    }

    function updateActionsToEffectSelectedParticipants() {
        $("#muteVideoAllButton").removeClass("mutedVideo");
        $("#muteVideoAllButton").addClass("unmutedVideo");
        $("#silenceVideoAllButton").removeClass("silenceVideoAllPress").addClass("silenceVideoAll silenceVideoAllDisabled");
        $("#muteAllButton").removeClass("muted");
        $("#muteAllButton").addClass("unmuted");
        $("#silenceAllButton").removeClass("silenceAllPress").addClass("silenceAll silenceAllDisabled");
    }

    function showOptions() {
        if (canRecordMeeting) {
            //getRecordingProfiles();
            getWebcastDetails();
            $("#replayLibraryOptions").show();
        } else {
            $("#recordingOptions").hide();
            $("#replayLibraryOptions").hide();
        }

        if (hasModeratorPin) {
            $("#moderatorPin").val("****");
        } else {
            $("#moderatorPin").val("");
        }

        if (hasWebcastPin) {
            $("#webcastPin").val("****");
        } else {
            $("#webcastPin").val("");
        }

        $("#optionsBox").dialog({
            create: function(event, ui) {
                $(".ui-dialog-titlebar-close", ui.dialog).show();
            }}
        );

        $("#roomLink").val(roomURL);
        if (hasRoomPin) {
            $("#roomPin").val("****");
        } else {
            $("#roomPin").val("");
        }


        $("#optionsBox").dialog("open");

        $("#roomLink").blur();
    }

    function showStartRecordingBox() {
        if (participantsCount == 0) {
            showMessage("<spring:message javaScriptEscape="true" code="please.invite.a.participant.before.recording"/>");
            return;
        }
        if (jQuery.isNumeric(recorderID)) {
            showMessage("<spring:message javaScriptEscape="true" code="recording.not.yet.stopped"/>");
            return;
        }
        getRecordingProfiles().success( function() {
            $("#startRecordingBox").dialog({
                        create: function(event, ui) {
                            $(".ui-dialog-titlebar-close", ui.dialog).show();
                        }}
            );

            $("#startRecordingBox").dialog("open");
        });
    }

    function getRecordingProfiles() {
        return $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/getrecordingprofiles.ajax"/>'
        }).success( function(data) {
                    var results = $(data);
                    var selected = "";

                    $("#recordProfiles").empty();

                    if (results.find("response").attr("total") == "0") {
                        $("#recordButton").hide();
                        $("#stopButton").hide();
                        $("#pauseButton").hide();
                        return;
                    }

                    results.find("profile").each(function () {
                        var prefix = $(this).find("prefix").text();
                        var displayName = htmlEscape($(this).find("displayName").text());
                        if (chosenRecordingProfile == prefix) {
                            selected = "selected='selected'";
                        } else {
                            selected = "";
                        }
                        $("#recordProfiles").append("<option value='" + prefix + "' " + selected + ">" + displayName + "</option>");
                    });
                });
    }

    function getWebcastDetails() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/getwebcastdetails.ajax"/>',
            data: {roomID: roomID}
        }).success( function(data) {
                    var $results = $(data);

                    $results.find("webcast").each(function () {
                        var webCastUrl = $(this).find("webCastUrl").text();
                        hasWebcastPin = $(this).find("hasWebcastPin").text() == "true";
                        $("#webcastLink").html("<a target='_blank' href='" + webCastUrl + "'>" + webCastUrl + "</a>");
                        if (hasWebcastPin) {
                            $("#webcastPin").val("****");
                        }
                    });
                });
    }

    function getRoomURLDetails() {
        return $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/getroomurldetails.ajax"/>',
            data: {roomID: roomID, PIN: pin}
        }).success( function(data) {
                    var $results = $(data);

                    $results.find("room").each(function () {
                        roomURL = $(this).find("roomURL").text();
                        hasRoomPin = $(this).find("hasPin").text() == "true";
                        $("#roomLink").html("<a target='_blank' href='" + roomURL + "'>" + roomURL + "</a>");
                        if (hasRoomPin) {
                            $("#roomPin").val("****");
                        }
                    });
                });
    }

    function isWebcastSelected() {
        var recordType = $("input[type='radio'][name='recordType']:checked").val();
        if (recordType == "webcast") {
            return "on";
        } else {
            return "off";
        }
    }

    function startRecording() {
        if (isWebcastSelected() == 'on' && $("#webcastLink a").text() == '' && isMyRoom()) {
            setWebcastURL();
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/recordtheconference.ajax"/>',
            data: {prefix: $("#recordProfiles").find(":selected").val(), entityID: roomID, webcast: isWebcastSelected(), moderatorPIN: pin}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        recorderName = $("#recordProfiles").find(":selected").text();
                        recordingOn = true;
                        //turnRecordingOn();
                        showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.recording.to.start"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function stopRecording() {
        if (!jQuery.isNumeric(recorderID)) {
            showMessage("<spring:message javaScriptEscape="true" code="recording.not.yet.begun"/>");
            return;
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/disconnectparticipant.ajax"/>',
            data : { roomID: roomID, endpointID: recorderID, endpointType: "R", moderatorPIN: pin}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        recordingOn = false;
                        //turnRecordingOff();
                        showMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="please.wait.for.recording.to.stop"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function turnRecordingOn() {
        recordingOn = true;
        //$("#recordButton").removeClass("record");
        $("#recordButton").addClass('opaque');
        $("#recordButton").hide();
        //$("#recordButton").addClass("stop");
        $("#stopButton").removeClass('opaque');
        $("#pauseButton").removeClass("opaque");
        $("#pauseButton").show();
        $("#recordingIndicatorText").html("(" + recorderName + ")");
        if (webcastingOn) {
            $("#recordingIndicator").addClass('webcastingIndicatorOn');
        } else {
            $("#recordingIndicator").addClass('recordingIndicatorOn');
        }
    }

    function turnRecordingOff() {
        recordingOn = false;
        recorderName = "";
        //$("#recordButton").removeClass("stop");
        //$("#recordButton").addClass("record");
        $("#recordButton").removeClass('opaque');
        if (canRecordMeeting && $("#recordProfiles option").size() != 0) {
            $("#recordButton").show();
        }
        $("#stopButton").addClass('opaque');
        $("#pauseButton").addClass("opaque");
        $("#pauseButton").hide();
        $("#recordingIndicatorText").html("");
        $("#recordingIndicator").removeClass();
    }

    function pauseRecording() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/stopvideo.ajax"/>',
            data : { roomID: roomID, endpointID: recorderID, endpointType: "R", moderatorPIN: pin}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        turnPauseOn();
                        showMessage("<spring:message javaScriptEscape="true" code="please.wait.fo.recording.to.pause"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function resumeRecording() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/startvideo.ajax"/>',
            data : { roomID: roomID, endpointID: recorderID, endpointType: "R", moderatorPIN: pin}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        turnPauseOff();
                        showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.recording.to.resume"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function turnPauseOn() {
        pauseOn = true;
        $("#pauseButton").removeClass("pause");
        $("#pauseButton").addClass("paused");
        $("#pauseButton").attr("title", "<spring:message javaScriptEscape="true" code="resume"/>");
    }

    function turnPauseOff() {
        pauseOn = false;
        $("#pauseButton").removeClass("paused");
        $("#pauseButton").addClass("pause");
        $("#pauseButton").attr("title", "<spring:message javaScriptEscape="true" code="pause"/>");
    }

    function clearRoomURL() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/clearroomurl.ajax"/>',
            data : { entityID: roomID}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        showMessage("<spring:message javaScriptEscape="true" code="room.link.removed"/>");
                        getRoomURLDetails();
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function setRoomURL() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/setroomurl.ajax"/>',
            data : { entityID: roomID}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        showMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="room.link.created"/>");
                        getRoomURLDetails();
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function setRoomPIN(thePIN) {
        if (thePIN == '****') {
            showMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="room.pin.left.unchanged"/>");
            return;
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/setroompin.ajax"/>',
            data : { entityID: roomID, PIN: thePIN}
        }).success( function(data) {
                    var $results = $(data);
                    var msg = $results.find("msg").text();
                    if (msg.length != 0) {
                        showMessage(msg);
                    } else {
                        showMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="room.pin.saved"/>");
                    }
                });
    }

    function clearRoomPIN() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/clearroompin.ajax"/>',
            data : { entityID: roomID }
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        hasModeratorPin = false;
                        showMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="room.pin.removed"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function clearWebcastURL() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/clearwebcasturl.ajax"/>',
            data : { roomID: roomID, entityID: roomID}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        showMessage("<spring:message javaScriptEscape="true" code="webcast.link.removed"/>");
                        getWebcastDetails();
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function setWebcastURL() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/setwebcasturl.ajax"/>',
            data : { roomID: roomID, entityID: roomID}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        showMessage("<spring:message javaScriptEscape="true" code="webcast.link.created"/>");
                        getWebcastDetails();
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function setWebcastPIN(thePIN) {
        if (thePIN == '****') {
            showMessage("<spring:message javaScriptEscape="true" code="webcast.pin.left.unchanged"/>");
            return;
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/setwebcastpin.ajax"/>',
            data : { roomID: roomID, PIN: thePIN}
        }).success( function(data) {
                    var $results = $(data);
                    var msg = $results.find("msg").text();
                    if (msg.length != 0) {
                        showMessage(msg);
                    } else {
                        showMessage("<spring:message javaScriptEscape="true" code="webcast.pin.saved"/>");
                    }
                });
    }

    function clearWebCastPIN() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/clearwebcastpin.ajax"/>',
            data : { roomID: roomID, entityID: roomID}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        if (!isMyRoom()) {
                            pin = "";
                        }
                        hasWebcastPin = false;
                        showMessage("<spring:message javaScriptEscape="true" code="webcast.pin.removed"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function setRoomModeratorPIN(thePIN) {
        if (thePIN == '****') {
            showMessage("<spring:message javaScriptEscape="true" code="moderator.pin.left.unchanged"/>");
            return;
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/setroommoderatorpin.ajax"/>',
            data : { roomID: roomID, entityID: roomID, PIN: thePIN}
        }).success( function(data) {
                    var $results = $(data);
                    var msg = $results.find("msg").text();
                    if (msg.length != 0) {
                       showMessage(msg);
                    } else {
                        if (!isMyRoom()) {
                            pin = thePIN;
                        }
                        hasModeratorPin = true;
                        showMessage("<spring:message javaScriptEscape="true" code="moderator.pin.saved"/>");
                    }
                });
    }

    function clearRoomModeratorPIN() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/clearroommoderatorpin.ajax"/>',
            data : { roomID: roomID, entityID: roomID}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        hasModeratorPin = false;
                        showMessage("<spring:message javaScriptEscape="true" code="moderator.pin.removed"/>");
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    function openReplayURL() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/getmyrecordurl.ajax"/>'
        }).success( function(data) {
                    window.open( $(data).find("url").text(), '_blank');
                });
    }

    function showMessage(theMessage) {
        noty({text: theMessage});
    }

    function confirmMessage(theMessage, successFunction, cancelFunction) {
        noty({
            text: theMessage,
            buttons: [
                {addClass: 'button' , text: '<spring:message javaScriptEscape="true" code="ok"/>', onClick: function($noty) {
                    $noty.close();
                    successFunction();
                }
                },
                {addClass: 'button', text: '<spring:message javaScriptEscape="true" code="cancel"/>', onClick: function($noty) {
                    $noty.close();
                    cancelFunction();
                }
                }
            ]
        });
    }

    function updateRoomState() {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/getroomstate.ajax"/>',
            data : { entityID: roomID, "moderatorPIN": pin}
        }).success( function(data) {
                    var lockedFlag, mutedFlag, videoMutedFlag, lectureModeFlag, waitingRoomFlag;
                    lockedFlag = $(data).find("locked").text();
                    mutedFlag = $(data).find("muted").text();
                    videoMutedFlag = $(data).find("videoMuted").text();
                    lectureModeFlag = $(data).find("lectureMode").text();
                    waitingRoomFlag = $(data).find("waitingRoom").text();

                    if (lockedFlag == "1") {
                        $("#lockButton").removeClass("unlocked");
                        $("#lockButton").addClass("locked");
                        roomLocked = true;
                    } else {
                        $("#lockButton").removeClass("locked");
                        $("#lockButton").addClass("unlocked");
                        roomLocked = false;
                    }

                    if (mutedFlag == "1") {
                        $("#muteAllButton").removeClass("unmuted");
                        $("#muteAllButton").addClass("muted");
                        $("#muteAllButton").attr("title", "<spring:message javaScriptEscape="true" code="unmute.audio"/>");
                        allMuted = true;
                    } else {
                        $("#muteAllButton").removeClass("muted");
                        $("#muteAllButton").addClass("unmuted");
                        $("#muteAllButton").attr("title", "<spring:message javaScriptEscape="true" code="mute.audio.on.participants.microphones.without.allowing.participants.to.re.enable"/>");
                        allMuted = false;
                    }

                    if (videoMutedFlag == "1") {
                        $("#muteVideoAllButton").removeClass("unmutedVideo");
                        $("#muteVideoAllButton").addClass("mutedVideo");
                        $("#muteVideoAllButton").attr("title", "<spring:message htmlEscape="true" javaScriptEscape="true" code="enable.video"/>");
                        allMutedVideo = true;
                    } else {
                        $("#muteVideoAllButton").removeClass("mutedVideo");
                        $("#muteVideoAllButton").addClass("unmutedVideo");
                        $("#muteVideoAllButton").attr("title", "<spring:message javaScriptEscape="true" code="disable.video.on.participants.cameras.without.allowing.participants.to.re.enable"/>");
                        allMutedVideo = false;
                    }

                    if (waitingRoomFlag == "1") {
                        waitingRoom = true;
                    } else {
                        waitingRoom = false;
                    }

                    if (lectureModeFlag == "1") {
                        lectureMode = true;
                        showLectureModeUI();
                    } else {
                        lectureMode = false;
                        hideLectureModeUI();
                    }
                });
    }

    function showLectureModeUI() {
        //$("#lectureBanner").show();
        setUpLectureModeTextAndButtons();
    }

    function setUpLectureModeTextAndButtons() {
        if (waitingRoom) {
            if (lectureMode) {
                $("#lectureBanner").addClass("lectureOn");
                $("#lectureBanner").removeClass("lectureOff");
                $("#lectureModePresenterButton").addClass("lectureButtonPresenterOn").removeClass("lectureButtonPresenterOff");
                $("#lectureModeExitButton").removeClass("lectureButtonGroupOn").addClass("lectureButtonGroupOff");
                if (presenterSet) {
                    $("#lectureBannerText").html('<spring:message javaScriptEscape="true" code="the.conference.is.in.presenter.mode"/>');
                } else {
                    $("#lectureBannerText").html('<spring:message javaScriptEscape="true" code="please.assign.a.presenter.or.click.group.to.start.the.meeting"/>');
                }
            } else {
                $("#lectureBannerText").html('<spring:message javaScriptEscape="true" code="the.conference.is.in.group.mode"/>');
                $("#lectureBanner").addClass("lectureOff");
                $("#lectureBanner").removeClass("lectureOn");
                $("#lectureModePresenterButton").removeClass("lectureButtonPresenterOn").addClass("lectureButtonPresenterOff");
                $("#lectureModeExitButton").addClass("lectureButtonGroupOn").removeClass("lectureButtonGroupOff");
            }
        } else {
            if (lectureMode) {
                $("#lectureBanner").addClass("lectureOn");
                $("#lectureBanner").removeClass("lectureOff");
                $("#lectureModePresenterButton").addClass("lectureButtonPresenterOn").removeClass("lectureButtonPresenterOff");
                $("#lectureModeExitButton").removeClass("lectureButtonGroupOn").addClass("lectureButtonGroupOff");
                if (presenterSet) {
                    $("#lectureBannerText").html('<spring:message javaScriptEscape="true" code="the.conference.is.in.presenter.mode"/>');
                } else {
                    $("#lectureBannerText").html('<spring:message javaScriptEscape="true" code="please.assign.a.presenter.or.click.group.to.exit.this.mode"/>');
                }
            } else {
                $("#lectureBannerText").html('<spring:message javaScriptEscape="true" code="the.conference.is.in.group.mode"/>');
                $("#lectureBanner").addClass("lectureOff").removeClass("lectureOn");
                $("#lectureModePresenterButton").removeClass("lectureButtonPresenterOn").addClass("lectureButtonPresenterOff");
                $("#lectureModeExitButton").addClass("lectureButtonGroupOn").removeClass("lectureButtonGroupOff");
            }

        }
    }

    function hideLectureModeUI() {
        //$("#lectureBanner").hide();
    }

    function isIOS() {
        return navigator.userAgent.match(/(iPad|iPhone|iPod)/g);
    }

    if (!isIOS()) { // touch devices don't send scroll until the end
        $('#theContacts').stickySectionHeaders({
            stickyClass      : 'sticky',
            headlineSelector : 'strong'
        });

        $('#theSelectedContacts').stickySectionHeaders({
            stickyClass      : 'sticky',
            headlineSelector : 'strong'
        });

        $('#theParticipants').stickySectionHeaders({
            stickyClass      : 'sticky',
            headlineSelector : 'strong'
        });
    }

    function fixHeaders() {
        if (!isIOS() && !$("#theParticipants ul").hasVerticalScrollbar()) {
            $("#inviteesHeader").removeAttr("style");
            $("#participantsHeader").removeAttr("style");
        }
    }

    function startLectureMode() {
        if (!lectureMode) {
            $("#optionsBox").dialog("close");
            beginLectureModeWithChecks();
        }
    }

    function beginLectureModeWithChecks() {
        if (participantsCount == 0) {
            showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.lecture.mode.to.begin"/>");
            setLectureModeState(1);
            return;
        }
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/doAllEndpointsSupportLectureMode.ajax"/>',
            data : { "roomID": roomID, "moderatorPIN": pin}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "false") {
                        confirmMessage("<spring:message javaScriptEscape="true" code="applying.lecture.mode.to.this.conference.will.disconnect.users.who.use.a.client.version.that.does.not.support.this.feature.are.you.sure.you.want.to.continue"/>", function () {
                            showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.lecture.mode.to.begin"/>");
                            setLectureModeState(1);
                        }, function () {
                            // do nothing
                        });
                    } else {
                        showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.lecture.mode.to.begin"/>");
                        setLectureModeState(1);
                    }
                }).error( function() {
                    showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.lecture.mode.to.begin"/>");
                    setLectureModeState(1);
                });
    }

    function stopLectureMode() {
        if (lectureMode && !showLectureModeExitConfirm) {
            $("#optionsBox").dialog("close");
            showLectureModeExitConfirm = true;
            confirmMessage("<spring:message htmlEscape="true" javaScriptEscape="true" code="are.you.sure.you.want.to.exit.lecture.mode"/>", function() {
                showMessage("<spring:message javaScriptEscape="true" code="please.wait.for.lecture.mode.to.end"/>");
                setLectureModeState(0);
                showLectureModeExitConfirm = false;
            }, function () {
                showLectureModeExitConfirm = false;
            });
        }
    }

    function setLectureModeState(lectureFlag) {
        $.ajax( {
            method: 'POST',
            url : '<c:url value="/ui/setlecturemodestate.ajax"/>',
            data : { "roomID": roomID, "moderatorPIN": pin, "lectureFlag": lectureFlag}
        }).success( function(data) {
                    raisedHandsCount = 0;
                }).error( function() {
                    alert("<spring:message javaScriptEscape="true" code="failed.to.toggle.lecture.mode"/>.");
                });
    }

    function handlePresenterChange(flag, objID, endpointID, endpointType) {
        var url = "";
        if (flag) { // set presenter
            url = '<c:url value="/ui/setpresenter.ajax"/>';
            ajaxPresenterUpdate(objID, roomID, endpointID, endpointType, url);
            /*
            confirmMessage("Are you sure you want to set or switch the presenter?", function () {
                    ajaxPresenterUpdate(objID, roomID, endpointID, endpointType, url);
            }, function () {
                // do nothing
            });
            */
        } else { // unset presenter
            url = '<c:url value="/ui/unsetpresenter.ajax"/>';
            ajaxPresenterUpdate(objID, roomID, endpointID, endpointType, url);
            /*
            confirmMessage("Are you sure you want to remove presenter rights?", function () {
                ajaxPresenterUpdate(objID, roomID, endpointID, endpointType, url);
            }, function () {
                // do nothing
            });
            */
        }

    }

    function ajaxPresenterUpdate(objID, roomID, endpointID, endpointType, url) {
        $.ajax( {
            method: 'POST',
            url : url,
            data : { roomID: roomID, endpointID: endpointID, endpointType: endpointType, moderatorPIN: pin}
        }).success( function(data) {
                    if ($(data).find("message").attr("success") == "true") {
                        var tmpObj = $("ul#participantsList li[entityid='"+ escapeCSS(objID) +"'][endpointID='"+ escapeCSS(endpointID) +"'] .partPresenter");
                        if (tmpObj.hasClass("presenteronSmall")) {
                            tmpObj.removeClass("presenteronSmall").addClass("presenteroffSmall");
                            tmpObj.attr("title", "<spring:message javaScriptEscape="true" code="set.participant.as.presenter"/>");
                        } else if (tmpObj.hasClass("presenteroffSmall")) {
                            tmpObj.removeClass("presenteroffSmall").addClass("presenteronSmall");
                            tmpObj.attr("title", "<spring:message javaScriptEscape="true" code="remove.presenter.rights.from.participant"/>");
                        }
                    } else {
                        showMessage($(data).find("msg").text());
                    }
                });
    }

    (function($) {
        $.fn.hasVerticalScrollbar = function() {
            return this.get(0).scrollHeight > this.height();
        }
        $.fn.hasHorizontalScrollbar = function() {
            return this.get(0).scrollWidth > this.width();
        }
    })(jQuery);

</script>
</body>
</html>
