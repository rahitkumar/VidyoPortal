package com.vidyo.service.conference.control;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.bo.Control;
import com.vidyo.bo.Room;
import com.vidyo.bo.User;
import com.vidyo.service.ConferenceServiceImpl;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IFederationConferenceService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.conference.response.ConferenceControlResponse;
import com.vidyo.service.exceptions.DisconnectAllException;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.exceptions.MuteAudioException;
import com.vidyo.service.exceptions.SilenceAudioException;
import com.vidyo.service.exceptions.StartVideoException;
import com.vidyo.service.exceptions.StopVideoException;
import com.vidyo.service.exceptions.UnmuteAudioException;
import com.vidyo.service.utils.UtilService;

@Service
public class ConferenceControlServiceImpl extends ConferenceServiceImpl implements ConferenceControlService {

    private final Logger logger = LoggerFactory.getLogger(ConferenceControlServiceImpl.class.getName());

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IConferenceService conferenceService;
    @Autowired
    private IFederationConferenceService federationConferenceService;
    @Autowired
    private UtilService utilService;

    @Override
    public ConferenceControlResponse disconnectConferenceAll(int loggedMemberID, int disconnectRoomID,
            String moderatorPIN) {
        if (logger.isDebugEnabled())
            logger.debug("Entering disconnectConferenceAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room disconnectRoom = null;
        try {
            disconnectRoom = roomService.getRoom(disconnectRoomID);
        } catch (Exception e) {
            logger.error("Invalid disconnectRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidDisconnectRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting disconnectConferenceAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (disconnectRoom == null) {
            logger.error("Invalid disconnectRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidDisconnectRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting disconnectConferenceAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, disconnectRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(disconnectRoomID, null);
            for (Control control : list) {
                if (control.getEndpointType().equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService.sendDisconnectParticipantFromFederation(control.getEndpointID(),
                                    disconnectRoomID, false); // try http
                        } catch (RemoteException e) {
                            federationConferenceService.sendDisconnectParticipantFromFederation(control.getEndpointID(),
                                    disconnectRoomID, true); // try https
                        }
                    } catch (LeaveConferenceException e) {
                        logger.error("LeaveConference", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("LeaveConference", "leave.conference.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.leave.conference.with.remote.vidyoconferencing.system");
                    }
                }
            }
            try {
                conferenceService.disconnectAll(disconnectRoom);
            } catch (DisconnectAllException e) {
                logger.error("DisconnectAllFailed", e.getMessage());

                response.setStatus(ConferenceControlResponse.DISCONNECT_ALL_FAILED);
                response.setMessageId("DisconnectAllFailed");
                response.setMessage("problem.disconnecting.all.from.conference");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting disconnectConferenceAll() of ConferenceControlServiceImpl");

        return response;
    }

    @Override
    public ConferenceControlResponse muteAudioServerAll(int loggedMemberID, int muteAudioRoomID, String moderatorPIN) {
        if (logger.isDebugEnabled())
            logger.debug("Entering muteAudioServerAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room muteAudioRoom = null;
        try {
            muteAudioRoom = roomService.getRoom(muteAudioRoomID);
        } catch (Exception e) {
            logger.error("Invalid muteAudioRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteAudioServerAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (muteAudioRoom == null) {
            logger.error("Invalid muteAudioRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteAudioServerAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, muteAudioRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(muteAudioRoomID, null);
            for (Control control : list) {
                if (control.getPresenter() == 1) {
                    continue;
                }
                if (control.getEndpointType().equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService.sendMuteAudioForParticipantInFederation(control.getEndpointID(),
                                    false); // try http
                        } catch (RemoteException e) {
                            federationConferenceService.sendMuteAudioForParticipantInFederation(control.getEndpointID(),
                                    true); // try https
                        }
                    } catch (MuteAudioException e) {
                        logger.error("MuteAudioForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("MuteAudioForParticipant", "mute.audio.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.mute.audio.with.remote.vidyoconferencing.system");
                    }
                } else {
                    try {
                        conferenceService.muteAudio(control.getEndpointGUID());
                    } catch (Exception e) {
                        logger.error("MuteAudioForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("MuteAudioForParticipant", "mute.audio.failed");
                    }
                }
            }
            try {
                roomService.muteRoom(muteAudioRoomID); // Mute All Control
                                                        // meeting feature a.k.a
                                                        // lecture mode audio
            } catch (Exception e) {
                logger.error("Problem on muteRoom", e.getMessage());

                response.setStatus(ConferenceControlResponse.MUTE_AUDIO_SERVER_ALL_FAILED);
                response.setMessageId("MuteAudiServerAllFailed");
                response.setMessage("problem.mute.audio.all.in.conference");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting muteAudioServerAll() of ConferenceControlServiceImpl");

        return response;
    }

    @Override
    public ConferenceControlResponse unmuteAudioServerAll(int loggedMemberID, int unmuteAudioRoomID,
            String moderatorPIN) {
        if (logger.isDebugEnabled())
            logger.debug("Entering unmuteAudioServerAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room unmuteAudioRoom = null;
        try {
            unmuteAudioRoom = roomService.getRoom(unmuteAudioRoomID);
        } catch (Exception e) {
            logger.error("Invalid unmuteAudioRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidUnmuteRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting unmuteAudioServerAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (unmuteAudioRoom == null) {
            logger.error("Invalid unmuteAudioRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidUnmuteRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting unmuteAudioServerAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, unmuteAudioRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(unmuteAudioRoomID, null);
            for (Control control : list) {
                if (control.getEndpointType().equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService
                                    .sendUnmuteAudioForParticipantInFederation(control.getEndpointID(), false); // try
                                                                                                                // http
                        } catch (RemoteException e) {
                            federationConferenceService
                                    .sendUnmuteAudioForParticipantInFederation(control.getEndpointID(), true); // try
                                                                                                                // https
                        }
                    } catch (UnmuteAudioException e) {
                        logger.error("UnmuteAudioForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("UnmuteAudioForParticipant", "unmute.audio.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.unmute.audio.with.remote.vidyoconferencing.system");
                    }
                } else {
                    try {
                        conferenceService.unmuteAudio(control.getEndpointGUID());
                    } catch (Exception e) {
                        logger.error("UnmuteAudioForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("UnmuteAudioForParticipant", "unmute.audio.failed");
                    }
                }
            }

            try {
                roomService.unmuteRoom(unmuteAudioRoomID); // Unmute All Control
                                                            // meeting feature
                                                            // a.k.a lecture
                                                            // mode audio
            } catch (Exception e) {
                logger.error("Problem on unmuteRoom", e.getMessage());

                response.setStatus(ConferenceControlResponse.UNMUTE_AUDIO_SERVER_ALL_FAILED);
                response.setMessageId("UnmuteAudiServerAllFailed");
                response.setMessage("problem.unmute.audio.all.in.conference");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting unmuteAudioServerAll() of ConferenceControlServiceImpl");

        return response;
    }

    @Override
    public ConferenceControlResponse muteAudioClientAll(int loggedMemberID, int muteAudioRoomID, String moderatorPIN) {
        if (logger.isDebugEnabled())
            logger.debug("Entering muteAudioClientAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room muteAudioRoom = null;
        try {
            muteAudioRoom = roomService.getRoom(muteAudioRoomID);
        } catch (Exception e) {
            logger.error("Invalid muteAudioRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteAudioClientAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (muteAudioRoom == null) {
            logger.error("Invalid muteAudioRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteAudioClientAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, muteAudioRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(muteAudioRoomID, null);
            for (Control control : list) {
                if (control.getPresenter() == 1) {
                    continue;
                }
                if (control.getEndpointType().equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService
                                    .sendSilenceAudioForParticipantInFederation(control.getEndpointID(), false); // try
                                                                                                                    // http
                        } catch (RemoteException e) {
                            federationConferenceService
                                    .sendSilenceAudioForParticipantInFederation(control.getEndpointID(), true); // try
                                                                                                                // https
                        }
                    } catch (SilenceAudioException e) {
                        logger.error("SilenceAudioForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("SilenceAudioForParticipant", "silence.audio.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.silence.audio.with.remote.vidyoconferencing.system");
                    }
                } else {
                    try {
                        conferenceService.silenceAudio(control.getEndpointGUID());
                    } catch (Exception e) {
                        logger.error("SilenceAudioForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("SilenceAudioForParticipant", "silence.audio.failed");
                    }
                }
            }
            try {
                roomService.silenceRoom(muteAudioRoomID); // Silence All Control
                                                            // meeting feature
                                                            // a.k.a lecture
                                                            // mode audio
            } catch (Exception e) {
                logger.error("Problem on silenceRoom", e.getMessage());

                response.setStatus(ConferenceControlResponse.MUTE_AUDIO_CLIENT_ALL_FAILED);
                response.setMessageId("MuteAudiServerAllFailed");
                response.setMessage("problem.silence.audio.all.in.conference");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting muteAudioClientAll() of ConferenceControlServiceImpl");

        return response;
    }

    @Override
    public ConferenceControlResponse muteVideoServerAll(int loggedMemberID, int muteVideoRoomID, String moderatorPIN) {

        if (logger.isDebugEnabled())
            logger.debug("Entering muteVideoServerAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room muteVideoRoom = null;
        try {
            muteVideoRoom = roomService.getRoom(muteVideoRoomID);
        } catch (Exception e) {
            logger.error("Invalid muteVideoRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteVideoRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteVideoServerAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (muteVideoRoom == null) {
            logger.error("Invalid muteVideoRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteVideoRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteVideoServerAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, muteVideoRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(muteVideoRoomID, null);
            for (Control control : list) {
                if (control.getPresenter() == 1) {
                    continue;
                }
                int endpointID = control.getEndpointID();
                String endpointType = control.getEndpointType();
                if (endpointType.equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService.sendStopVideoForParticipantInFederation(endpointID, false); // try
                                                                                                                    // http
                        } catch (RemoteException e) {
                            federationConferenceService.sendStopVideoForParticipantInFederation(endpointID, true); // try
                                                                                                                    // https
                        }
                    } catch (StopVideoException e) {
                        logger.error("StopVideoForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("StopVideoForParticipant", "mute.video.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.mute.vodeo.with.remote.vidyoconferencing.system");
                    }
                } else {
                    String endpointGUID = control.getEndpointGUID();
                    if (!endpointGUID.equalsIgnoreCase("")) {
                        try {
                            if (endpointType.equalsIgnoreCase("R")) {
                                // do nothing, see VPTL-113
                            } else {
                                conferenceService.stopVideo(endpointGUID);
                            }
                        } catch (Exception e) {
                            logger.error("StopVideoForParticipant", e.getMessage());

                            response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                            response.addNotInterruptibleError("StopVideoForParticipant", "mute.video.failed");
                        }
                    }
                }
            }

            try {
                roomService.muteRoomVideo(muteVideoRoomID); // Mute All Control
                                                            // meeting feature
                                                            // a.k.a lecture
                                                            // mode audio
            } catch (Exception e) {
                logger.error("Problem on muteRoomVideo", e.getMessage());

                response.setStatus(ConferenceControlResponse.MUTE_VIDEO_SERVER_ALL_FAILED);
                response.setMessageId("MuteVideoServerAllFailed");
                response.setMessage("Failed to mute video.");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting muteVideoServerAll() of ConferenceControlServiceImpl");

        return response;
    }

    @Override
    public ConferenceControlResponse unmuteVideoServerAll(int loggedMemberID, int unmuteVideoRoomID,
            String moderatorPIN) {

        if (logger.isDebugEnabled())
            logger.debug("Entering unmuteVideoServerAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room unmuteVideoRoom = null;
        try {
            unmuteVideoRoom = roomService.getRoom(unmuteVideoRoomID);
        } catch (Exception e) {
            logger.error("Invalid unmuteVideoRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidUnmuteVideoRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting unmuteVideoServerAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (unmuteVideoRoom == null) {
            logger.error("Invalid unmuteVideoRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidUnmuteVideoRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting unmuteVideoServerAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, unmuteVideoRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(unmuteVideoRoomID, null);
            for (Control control : list) {
                int endpointID = control.getEndpointID();
                String endpointType = control.getEndpointType();
                if (endpointType.equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService.sendStartVideoForParticipantInFederation(endpointID, false); // try
                                                                                                                        // http
                        } catch (RemoteException e) {
                            federationConferenceService.sendStartVideoForParticipantInFederation(endpointID, true); // try
                                                                                                                    // https
                        }
                    } catch (StartVideoException e) {
                        logger.error("StartVideoForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("StartVideoForParticipant", "unmute.video.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.unmute.vodeo.with.remote.vidyoconferencing.system");
                    }
                } else {
                    String endpointGUID = control.getEndpointGUID();
                    if (!endpointGUID.equalsIgnoreCase("")) {
                        try {
                            if (endpointType.equalsIgnoreCase("R")) {
                                // do nothing, see VPTL-113
                            } else {
                                conferenceService.startVideo(endpointGUID);
                            }
                        } catch (Exception e) {
                            logger.error("StartVideoForParticipant", e.getMessage());

                            response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                            response.addNotInterruptibleError("StartVideoForParticipant", "ummute.video.failed");
                        }
                    }
                }
            }

            try {
                roomService.unmuteRoomVideo(unmuteVideoRoomID); // Unmute All
                                                                // Control
                                                                // meeting
                                                                // feature a.k.a
                                                                // lecture mode
                                                                // audio
            } catch (Exception e) {
                logger.error("Problem on unmute video Room", e.getMessage());

                response.setStatus(ConferenceControlResponse.UNMUTE_VIDEO_SERVER_ALL_FAILED);
                response.setMessageId("UnmuteVideoServerAllFailed");
                response.setMessage("Failed to unmute video.");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting unmuteVideoServerAll() of ConferenceControlServiceImpl");

        return response;
    }

    @Override
    public ConferenceControlResponse muteVideoClientAll(int loggedMemberID, int muteVideoRoomID, String moderatorPIN) {

        if (logger.isDebugEnabled())
            logger.debug("Entering muteVideoClientAll() of ConferenceControlServiceImpl");

        ConferenceControlResponse response = new ConferenceControlResponse();

        Room muteVideoRoom = null;
        try {
            muteVideoRoom = roomService.getRoom(muteVideoRoomID);
        } catch (Exception e) {
            logger.error("Invalid muteVideoClientRoom", e.getMessage());

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteVideoClientRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteVideoClientAll() of ConferenceControlServiceImpl");

            return response;
        }
        if (muteVideoRoom == null) {
            logger.error("Invalid muteVideoClientRoom");

            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            response.setMessageId("InvalidMuteVideoClientRoom");
            response.setMessage("invalid.room");

            if (logger.isDebugEnabled())
                logger.debug("Exiting muteVideoClientAll() of ConferenceControlServiceImpl");

            return response;
        }

        if (utilService.canMemberControlRoom(loggedMemberID, muteVideoRoom, moderatorPIN)) {
            List<Control> list = conferenceService.getParticipants(muteVideoRoomID, null);
            for (Control control : list) {
                if (control.getPresenter() == 1) {
                    continue;
                }
                if (control.getEndpointType().equalsIgnoreCase("F")) {
                    try {
                        try {
                            federationConferenceService.sendStopVideoForParticipantInFederation(control.getEndpointID(),
                                    false); // try http
                        } catch (RemoteException e) {
                            federationConferenceService.sendStopVideoForParticipantInFederation(control.getEndpointID(),
                                    true); // try https
                        }
                    } catch (StopVideoException e) {
                        logger.error("SilenceVideoForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("SilenceVideoForParticipant", "silence.video.failed");
                    } catch (RemoteException e) {
                        logger.error("RemoteException", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("RemoteException",
                                "failed.to.silence.vodeo.with.remote.vidyoconferencing.system");
                    }
                } else {
                    try {
                        if (!control.getEndpointType().equalsIgnoreCase("R")) {
                            conferenceService.silenceVideo(control.getEndpointGUID());
                        }
                    } catch (Exception e) {
                        logger.error("SilenceVideoForParticipant", e.getMessage());

                        response.setStatus(ConferenceControlResponse.OK_WITH_ERRORS);
                        response.addNotInterruptibleError("StartVideoForParticipant", "silence.video.failed");
                    }
                }
            }

            try {
                roomService.silenceRoomVideo(muteVideoRoomID); // Mute All
                                                                // Control
                                                                // meeting
                                                                // feature a.k.a
                                                                // lecture mode
                                                                // audio
            } catch (Exception e) {
                logger.error("Problem on silenceRoomVideo", e.getMessage());

                response.setStatus(ConferenceControlResponse.MUTE_VIDEO_CLIENT_ALL_FAILED);
                response.setMessageId("MuteVideoClientAllFailed");
                response.setMessage("Failed to silence video.");
            }
        } else {
            logger.error("Unable to control users in the conference");

            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            response.setMessageId("CannotControlRoom");
            response.setMessage("unable.to.control.users.in.the.conference");
        }

        if (logger.isDebugEnabled())
            logger.debug("Exiting muteVideoClientAll() of ConferenceControlServiceImpl");

        return response;
    }

    /**
     * Silences [Hard Silence] the speaker of the participants in a conference.
     * Overloaded to handle single endpoint as well as all endpoints in the
     * conference.
     *
     * @param controllingUser
     * @param roomId
     * @param participantId
     * @param moderatorPIN
     * @return
     */
    @Override
    public ConferenceControlResponse silenceSpeakerServer(User controllingUser, int roomId, int participantId,
            String moderatorPIN, int silence) {
        ConferenceControlResponse response = new ConferenceControlResponse();
        // Validate Room
        Room room = roomService.getRoomDetailsForConference(roomId);
        if (room == null) {
            response.setStatus(ConferenceControlResponse.INVALID_ROOM);
            return response;
        }

        // Validate if conference can be controlled
        boolean canControl = canControlConference(controllingUser, room, moderatorPIN);
        if (!canControl) {
            response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ROOM);
            return response;
        }

        String guid = null;
        List<String> guids = new ArrayList<>();
        // If participantId/EndpointId is less than or equal to zero, do not
        // perform this check
        // If the endpoint is not in this room, it can't be controlled
        if (participantId > 0) {
            if (!isEndpointIDinRoomID(participantId, roomId)) {
                response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ENDPOINT);
                return response;
            }
            // Retrieve the GUID from the conferences table if the EndpointId is
            // in the Room
            guid = getGUIDForEndpointIdInConf(participantId, roomId);
            if (guid == null) {
                response.setStatus(ConferenceControlResponse.CANNOT_CONTROL_ENDPOINT);
                return response;
            }
            guids.add(guid);
        }

        /*
         * If particpantId is -1, the silencing of speakers has to be done for
         * all the endpoints in the conference and the room - overloaded
         */
        if (participantId == -1) {
            guids.addAll(getLectureModeDao().getEndpointGUIDsInConference(roomId));
            // Silence/UnSilence Room if the participant id is -1
            roomService.silenceSpeakerForRoomServer(roomId, silence ^ 1); // reverse one and zero for Room
        }

        // Send the VCAP with Silence Speaker message for GUIDs
        silenceSpeaker(guids, silence, roomId, (participantId == -1));
        return response;
    }

}
