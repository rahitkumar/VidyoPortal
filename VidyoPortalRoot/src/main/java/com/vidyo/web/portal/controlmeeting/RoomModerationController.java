package com.vidyo.web.portal.controlmeeting;

import static org.springframework.web.bind.ServletRequestUtils.getIntParameter;
import static org.springframework.web.bind.ServletRequestUtils.getStringParameter;

import java.io.File;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vidyo.service.conference.ConferenceAppService;
import com.vidyo.service.lecturemode.request.LectureModeParticipantControlRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.Entity;
import com.vidyo.bo.EntityFilter;
import com.vidyo.bo.Invite;
import com.vidyo.bo.Member;
import com.vidyo.bo.RecorderEndpointFilter;
import com.vidyo.bo.RecorderPrefix;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.TenantConfiguration;
import com.vidyo.bo.User;
import com.vidyo.bo.conference.CallCompletionCode;
import com.vidyo.bo.email.InviteEmailContentForInviteRoom;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.bo.search.simple.RoomIdSearchResult;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.recordings.webcast.GeneralFaultException;
import com.vidyo.recordings.webcast.InvalidArgumentFaultException;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IFederationConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IReplayService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.SystemServiceImpl;
import com.vidyo.service.conference.control.ConferenceControlService;
import com.vidyo.service.conference.response.ConferenceControlResponse;
import com.vidyo.service.exceptions.EndpointNotExistException;
import com.vidyo.service.exceptions.InviteConferenceException;
import com.vidyo.service.exceptions.JoinConferenceException;
import com.vidyo.service.exceptions.LeaveConferenceException;
import com.vidyo.service.exceptions.LectureModeNotSupportedException;
import com.vidyo.service.exceptions.MuteAudioException;
import com.vidyo.service.exceptions.NoVidyoReplayException;
import com.vidyo.service.exceptions.OutOfPortsException;
import com.vidyo.service.exceptions.SilenceAudioException;
import com.vidyo.service.exceptions.StartVideoException;
import com.vidyo.service.exceptions.StopVideoException;
import com.vidyo.service.exceptions.UnmuteAudioException;
import com.vidyo.service.lecturemode.LectureModeService;
import com.vidyo.service.lecturemode.request.LectureModeControlRequest;
import com.vidyo.service.lecturemode.response.LectureModeControlResponse;
import com.vidyo.service.room.RoomControlResponse;
import com.vidyo.service.usergroup.UserGroupService;
import com.vidyo.service.conference.request.JoinConferenceRequest;
import com.vidyo.service.conference.response.JoinConferenceResponse;

@Controller
public class RoomModerationController {

    @Autowired
    private IUserService user;

    @Autowired
    private IMemberService member;

    @Autowired
    private ISystemService system;

    @Autowired
    private IRoomService roomService;

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private IReplayService replayService;

    @Autowired
    private IConferenceService conferenceService;

    @Autowired
    private ConferenceControlService conferenceControlService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private LectureModeService lectureModeService;
    
    @Autowired
    private IFederationConferenceService federationConferenceService;

    @Autowired
    private ReloadableResourceBundleMessageSource ms;

    @Autowired
    private ConferenceAppService conferenceAppService;

    private final Logger logger = LoggerFactory.getLogger(RoomModerationController.class.getName());

    @RequestMapping(value = "/ui/contacts.ajax", method = RequestMethod.POST)
    public ModelAndView getFlexContactsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        EntityFilter filter = new EntityFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();

        Entity myroom = this.member.getContact(memberID);
        myroom.setName(myroom.getName());
        model.put("myroom", myroom);

        int num = this.member.getCountContacts(memberID, filter);
        model.put("num", num);

        Control control = null;

        if(myroom.getMemberStatus() == 2 || myroom.getMemberStatus() == 9){ //Busy or BusyInOwnRoom
            control = this.conferenceService.getControlForMember(memberID, null);
        }

        if(control != null){
            model.put("control", control);
        }

        List<Entity> list = this.member.getContacts(memberID, filter);
        for(Entity entity: list){
            entity.setName(entity.getName());
        }
        model.put("list", list);

        // provide portal version
        String portalVersion = this.system.getPortalVersion();
        if (!portalVersion.equalsIgnoreCase("")) {
            model.put("portalVersion", portalVersion);
        } else {
            model.put("portalVersion", "");
        }

        String systemLanguage = this.system.getSystemLang().getLangCode();
        model.put("systemLanguage", systemLanguage);


        String logopath = ""; //no default "vidyoLogo.swf" be provided, because Bill will check if it's blank string or not
        try {
            String path = this.system.getCustomizedLogoName();
            if((path != null) && (path.length() > 0) && (new File(request.getServletContext().getRealPath(path))).exists() )
                logopath = path;
            else {
                path = this.system.getCustomizedDefaultUserPortalLogoName();
                if((path != null) && (path.length() > 0) && (new File(request.getServletContext().getRealPath(path))).exists() )
                    logopath = path;
            }

        } catch (Exception ignored) {
        }

        String joinURL = this.roomService.getRoomURL(system, request.getScheme(), request.getHeader("host"), myroom.getRoomKey());
        if(tenantService.isTenantNotAllowingGuests()){
            joinURL += "&noguest";
        }

        model.put("roomURLFormated", joinURL);
        model.put("customLogo", logopath);
        return new ModelAndView("ajax/flex_contacts_ajax", "model", model);
    }

    /**
     * This API supports the following Call Joining scenarios :<br>
     * User joining his own room/other member's room in the same tenant.<br>
     * User joining other member's room belonging to a different tenant.<br>
     * User joining an Inter-Portal-Conference.<br>
     * User joining other member's room belonging to a different tenant via IPC.<br>
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ui/jointheconference.ajax", method = RequestMethod.POST)
    public ModelAndView joinTheConference(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        if (memberID <= 0) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        }

        Locale loc = LocaleContextHolder.getLocale();
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int roomId = getIntParameter(request, "entityID", 0);
        String pin = getStringParameter(request, "pin", "");
        String input = getStringParameter(request, "input", "");

        // Handling the IPC case & the case when the user types the room name
        // with Tenant URL & scheduled room
        if (roomId == 0 && !input.equalsIgnoreCase("")) {
            String roomNameExt = null;
            // Starting 2.4, Join button will be enabled for scheduled rooms
            // also as opposed to IPC only in prior releases
            // Validate the input string, if there is only one @ symbol,
            // follow IPC else scheduled room
            if (StringUtils.countOccurrencesOf(input, "@") == 1) {
                // Parse the input string
                String[] parsedInput = input.trim().split("@");
                if (parsedInput.length != 2) {
                    FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                            "Invalid Room");
                    errors.add(fe);
                    model.put("fields", errors);
                    model.put("success", Boolean.FALSE);
                    return new ModelAndView("ajax/result_ajax", "model", model);
                }
                // Array length is 2 now, arr[0] will be the roomname or extn
                // and arr[1] will be the tenant url
                roomNameExt = parsedInput[0];
                String tenantUrl = parsedInput[1];
                JoinConferenceRequest joinConfReq = new JoinConferenceRequest();
                joinConfReq.setTenantUrl(tenantUrl);
                joinConfReq.setPin(pin);
                joinConfReq.setRoomNameExt(roomNameExt);
                joinConfReq.setJoiningMemberId(memberID);
                JoinConferenceResponse joinConferenceResponse = conferenceAppService
                        .joinInterPortalRoomConference(joinConfReq);
                if (joinConferenceResponse.getStatus() != 0) {
                    FieldError fe = new FieldError(joinConferenceResponse.getMessageId(),
                            joinConferenceResponse.getMessageId(),
                            ms.getMessage(joinConferenceResponse.getMessage(), null, "", loc));
                    errors.add(fe);
                    model.put("fields", errors);
                }

            } else {
                // The input may be scheduled room extension
                roomNameExt = input.trim();
                JoinConferenceRequest joinConferenceRequest = new JoinConferenceRequest();
                joinConferenceRequest.setRoomNameExt(input.trim());
                joinConferenceRequest.setPin(pin);
                joinConferenceRequest.setJoiningMemberId(memberID);
                JoinConferenceResponse joinConferenceResponse = conferenceAppService
                        .joinConferenceInScheduledRoom(joinConferenceRequest);
                if (joinConferenceResponse.getStatus() != 0) {
                    FieldError fe = new FieldError(joinConferenceResponse.getMessageId(),
                            joinConferenceResponse.getMessageId(),
                            ms.getMessage(joinConferenceResponse.getMessage(), null, "", loc));
                    errors.add(fe);
                    model.put("fields", errors);
                }
            }

        } else {
            // Room Id is present
            JoinConferenceRequest joinConfReq = new JoinConferenceRequest();
            joinConfReq.setCheckCrossTenant(true);
            joinConfReq.setCheckPin(true);
            joinConfReq.setRoomId(roomId);
            joinConfReq.setPin(pin);
            joinConfReq.setJoiningMemberId(memberID);
            JoinConferenceResponse joinConferenceResponse = conferenceAppService.joinConferenceInRoom(joinConfReq);
            if (joinConferenceResponse.getStatus() != 0) {
                FieldError fe = new FieldError(joinConferenceResponse.getMessageId(),
                        joinConferenceResponse.getMessageId(),
                        ms.getMessage(joinConferenceResponse.getMessage(), null, "", loc));
                errors.add(fe);
                model.put("fields", errors);
            }
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getentities.ajax", method = RequestMethod.POST)
    public ModelAndView getEntitiesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();

        String listOfentityID = getStringParameter(request,
                "list", "");

        List<Entity> list = new ArrayList<Entity>();

        List<Integer> roomIDs = new ArrayList<Integer>();
        String[] ids = listOfentityID.split(",");
        for (String id : ids) {
            try {
                roomIDs.add(Integer.parseInt(id.trim()));
            } catch (NumberFormatException nfe) {
                logger.debug("Wrong Entity Id format ->" + id);
            }

        }

        if(roomIDs.isEmpty()) {
            List<FieldError> errors = new ArrayList<FieldError>();
            FieldError fe = new FieldError("getEntities", "getEntities", "Empty room list");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            logger.error("getEntities:->"+user.getUsername()+":No rooms Ids") ;
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        list = this.roomService.getEntities(roomIDs, user, null);

        for(Entity room: list) {
            if(room.getRoomType().equalsIgnoreCase("Scheduled")) {
                // Override the name here
                room.setName(room.getName() + room.getRoomPIN());
            }
        }

        model.put("num", list.size());
        model.put("list", list);

        // provide portal version
        String portalVersion = this.system.getPortalVersion();
        if (!portalVersion.equalsIgnoreCase("")) {
            model.put("portalVersion", portalVersion);
        } else {
            model.put("portalVersion", "");
        }

        String systemLanguage = this.system.getSystemLang().getLangCode();
        model.put("systemLanguage", systemLanguage);

        Entity myroom = this.member.getContact(user.getMemberID());

        myroom.setName(myroom.getName());

        model.put("myroom", myroom);

        Control control = null;

        if (myroom.getMemberStatus() == 2 || myroom.getMemberStatus() == 9) { // Busy or BusyInOwnRoom
            control = this.conferenceService.getControlForMember(memberID, null);
        }

        if (control != null) {
            model.put("control", control);
        }

        String joinURL = this.roomService.getRoomURL(system, request.getScheme(), request.getHeader("host"), myroom.getRoomKey());
        if(tenantService.isTenantNotAllowingGuests()){
            joinURL += "&noguest";
        }
        model.put("roomURLFormated", joinURL);
        return new ModelAndView("ajax/flex_entities_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getroomstate.ajax", method = RequestMethod.POST)
    public ModelAndView getRoomState(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        int roomID = getIntParameter(request, "entityID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");
        Room theRoom = this.roomService.getRoom(roomID);
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }
        if (theRoom == null) {
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }
        if (!canMemberControlRoom(memberID, theRoom, moderatorPIN)) {
        	model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }
        TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(TenantContext.getTenantId());
        model.put("room", theRoom);
        model.put("waitingRoom", tenantConfiguration.getWaitingRoomsEnabled());
        return new ModelAndView("ajax/flex_roomstate_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getparticipants.ajax", method = RequestMethod.POST)
    public ModelAndView getParticipants(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int roomID = getIntParameter(request, "entityID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");
        ControlFilter filter = new ControlFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }
        Room room = roomService.getRoom(roomID);
        if (room == null) {
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }
        if (!canMemberControlRoom(memberID, room, moderatorPIN)) {
        	model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        Long num = this.conferenceService.getCountParticipants(roomID);
        List<Control> list = this.conferenceService.getParticipants(roomID, filter);

        List<Control> removed = new ArrayList<Control>();
        for(Control control: list){
            // TODO: do not show Federated participant in v.2.2
            if (control.getEndpointType().equalsIgnoreCase("F") || control.getEndpointType().equalsIgnoreCase("C")) {
                removed.add(control);
                num--;
            }

            if (control.getEndpointType().equalsIgnoreCase("R")) {
                removed.add(control);
                num--;
                model.put("recorderID", control.getEndpointID());
                model.put("recorderName", cleanseRecorderProfileName(control.getName()));
                model.put("isPaused", control.getVideo() == 0 ? "true" : "false");
                model.put("isWebcast", control.getWebcast() == 1 ? "true" : "false");
            }
            control.setName(control.getName());
        }
        for(Control control: removed){
            list.remove(control);
        }
        model.put("num", num);
        model.put("list", list);

        return new ModelAndView("ajax/flex_getparticipants_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getrecordingprofiles.ajax", method = RequestMethod.POST)
    public ModelAndView getRecordingProfilesAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        }

        Map<String, Object> model = new HashMap<String, Object>();

        RecorderEndpointFilter filter = new RecorderEndpointFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        List<RecorderPrefix> list = this.system.getAvailableRecorderPrefixes(filter);

        for (RecorderPrefix prefix : list) {
            prefix.setDescription(cleanseRecorderProfileName(prefix.getDescription()));
        }

        model.put("list", list);
        Long num = this.system.getCountAvailableRecorderPrefixes();
        model.put("num", num);

        return new ModelAndView("ajax/flex_recording_profiles_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getwebcastdetails.ajax", method = RequestMethod.POST)
    public ModelAndView getWebcastDetails(HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        int roomID = getIntParameter(request, "roomID", 0);
        Map<String, Object> model = new HashMap<String, Object>();
        if (roomID <= 0) {
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        Room room = this.roomService.getRoomDetailsForWebcast(roomID);

        if (room == null) {
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        try {
            this.replayService.getWebCastURLandPIN(room);
        } catch (RemoteException e) {
            logger.error("Exception while invoking ReplayService", e);
        }
        model.put("room", room);
        model.put("success", Boolean.TRUE);
        return new ModelAndView("ajax/webcast_details_ajax", "model", model);

    }

    @RequestMapping(value = "/ui/controlmeeting.ajax", method = RequestMethod.POST)
    public ModelAndView controlMeetingAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int roomID = getIntParameter(request, "roomID", 0);
        String action = getStringParameter(request, "action", "");
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        if (roomID != 0) {
            //Add the restricted rooms check.
            //apply restricted room access check
            //fetch the member from member repository
            if (!userGroupService.canMemberAccessRoom(roomID, user.getMemberID())) {
                logger.error("User cannot perform operations on a restricted room without access " + user.getMemberName() + " roon: " + roomID);
                FieldError fe = new FieldError("Restricted Room", "Cannot perform operation on Restricted Room",
                        "Cannot perform operation on  Restricted Room");
                errors.add(fe);
            } else {
                if (action.equalsIgnoreCase("disconnectAll")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.disconnectConferenceAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }

                } else if (action.equalsIgnoreCase("mute")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.muteAudioServerAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("unmute")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.unmuteAudioServerAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("muteVideo")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.muteVideoServerAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("unmuteVideo")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.unmuteVideoServerAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("lock")) {
                    RoomControlResponse ccResponse = this.roomService.lockRoom(memberID, roomID, moderatorPIN);
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("unlock")) {
                    RoomControlResponse ccResponse = this.roomService.unlockRoom(memberID, roomID, moderatorPIN);
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("silence")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.muteAudioClientAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                } else if (action.equalsIgnoreCase("silenceVideo")) {
                    ConferenceControlResponse ccResponse = conferenceControlService.muteVideoClientAll(memberID, roomID, moderatorPIN);
                    List<ConferenceControlResponse.NotInterruptibleError> errs = ccResponse.retrieveNotInterruptibleError();
                    if (errs.size() > 0) {
                        for (ConferenceControlResponse.NotInterruptibleError err : errs) {
                            FieldError fe = new FieldError(err.getMessageID(), err.getMessageID(),
                                    ms.getMessage(err.getMessage(), null, "", loc));
                            errors.add(fe);
                        }
                    }
                    int errStatus = ccResponse.getStatus();
                    if (errStatus != 0 && errStatus != ConferenceControlResponse.OK_WITH_ERRORS) {
                        FieldError fe = new FieldError(ccResponse.getMessageId(), ccResponse.getMessageId(),
                                ms.getMessage(ccResponse.getMessage(), null, "", loc));
                        errors.add(fe);
                    }
                }
            }
        } else {
            FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setroommoderatorpin.ajax", method = RequestMethod.POST)
    public ModelAndView setRoomModeratorPINAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int roomID = getIntParameter(request, "entityID", 0);
        String PIN = getStringParameter(request, "PIN", "");

        if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), PIN)) {
            String[] args = {""+system.getMinPINLengthForTenant(TenantContext.getTenantId()), ""+ SystemServiceImpl.PIN_MAX};
            FieldError fe = new FieldError("setRoomModeratorPIN", "setRoomModeratorPIN", ms.getMessage("moderator.pin.must.be.3.10.digits", args, "", loc));
            errors.add(fe);
        } else {
            Room room = this.roomService.getRoom(roomID);

            if (roomID != 0 && room != null) {
                if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
                    room.setModeratorPinSetting("enter");
                    room.setRoomModeratorPIN(PIN);
                    this.roomService.updateRoomModeratorPIN(room);
                } else {
                    FieldError fe = new FieldError("setRoomModeratorPIN", "setRoomModeratorPIN", "Unable to set moderator PIN");
                    errors.add(fe);
                }
            } else {
                FieldError fe = new FieldError("setRoomModeratorPIN", "setRoomModeratorPIN", "Unable to set moderator PIN");
                errors.add(fe);
            }
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/clearroommoderatorpin.ajax", method = RequestMethod.POST)
    public ModelAndView clearRoomModeratorPINAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int roomID = getIntParameter(request, "entityID", 0);
        Room room = this.roomService.getRoom(roomID);

        if (roomID != 0 && room != null) {
            if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
                room.setModeratorPinSetting("remove");
                this.roomService.updateRoomModeratorPIN(room);
            } else {
                FieldError fe = new FieldError("clearRoomModeratorPIN", "clearRoomModeratorPIN", "Unable to clear moderator PIN");
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("clearRoomModeratorPIN", "clearRoomModeratorPIN", "Unable to clear moderator PIN");
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/checkroommoderatorpin.ajax", method = RequestMethod.POST)
    public ModelAndView checkRoomModeratorPinAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int roomID = getIntParameter(request, "entityID", 0);
        String pin = getStringParameter(request, "PIN", "");

        if (roomID != 0) {
            Room room = this.roomService.getRoom(roomID);
            if (room != null) {
            	if(!canMemberControlRoom(this.user.getLoginUser().getMemberID(), room, pin)) {
                    FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
                    errors.add(fe);
            	} else {
                    String moderatorPIN = room.getRoomModeratorPIN() != null ? room.getRoomModeratorPIN(): "";
                    if (!moderatorPIN.equalsIgnoreCase(pin)) {
                        FieldError fe = new FieldError("pin", "pin", ms.getMessage("wrong.moderator.pin", null, "", loc));
                        errors.add(fe);
                    }
            	}
            } else {
                FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setroompin.ajax", method = RequestMethod.POST)
    public ModelAndView setRoomPINAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();
        int roomID = getIntParameter(request, "entityID", 0);
        String PIN = getStringParameter(request, "PIN", "");

        if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), PIN)) {
            String[] args = {""+system.getMinPINLengthForTenant(TenantContext.getTenantId()), ""+SystemServiceImpl.PIN_MAX};
            FieldError fe = new FieldError("setRoomPIN", "setRoomPIN", ms.getMessage("room.pin.must.be.3.10.digits", args, "", loc));
            errors.add(fe);
        } else {
            Room room = this.roomService.getRoom(roomID);

            if (roomID != 0 && room != null) {
                if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
                    // Scheduled Room PIN cannot be updated
                    if(!room.getRoomType().equalsIgnoreCase("Scheduled")) {
                        room.setPinSetting("enter");
                        room.setRoomPIN(PIN);
                        this.roomService.updateRoom(roomID, room);
                    } else {
                        FieldError fe = new FieldError("RoomPIN", "RoomPIN", ms.getMessage(
                                "user.scheduled.room.pin.cannot.update", null, "", loc));
                        errors.add(fe);
                    }

                } else {
                    FieldError fe = new FieldError("setRoomPIN", "setRoomPIN", "Unable to set moderator PIN");
                    errors.add(fe);
                }
            } else {
                FieldError fe = new FieldError("setRoomPIN", "setRoomPIN", "Unable to set moderator PIN");
                errors.add(fe);
            }
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/clearroompin.ajax", method = RequestMethod.POST)
    public ModelAndView clearRoomPINAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Locale loc = LocaleContextHolder.getLocale();
        int roomID = getIntParameter(request, "entityID", 0);
        Room room = this.roomService.getRoom(roomID);
        List<FieldError> errors = new ArrayList<FieldError>();
        if (roomID != 0 && room != null) {
            if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
                if(!room.getRoomType().equalsIgnoreCase("Scheduled")) {
                    room.setPinSetting("remove");
                    this.roomService.updateRoomPIN(room);
                    model.put("success", Boolean.TRUE);
                } else {
                    FieldError fe = new FieldError("RoomPIN", "RoomPIN", ms.getMessage(
                            "user.scheduled.room.pin.cannot.update", null, "", loc));
                    errors.add(fe);
                }
            } else {
                model.put("success", Boolean.FALSE);
            }
        } else {
            model.put("success", Boolean.FALSE);
        }
        if(errors.size() > 0) {
            model.put("fields", errors);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setroomurl.ajax", method = RequestMethod.POST)
    public ModelAndView setRoomURLAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();

        int roomID = getIntParameter(request, "entityID", 0);
        Room room = this.roomService.getRoom(roomID);

        if (roomID != 0 && room != null) {
            if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
                this.roomService.generateRoomKey(room);
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setlecturemodestate.ajax", method = RequestMethod.POST)
    public ModelAndView setLectureModeStateAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int lectureFlag = getIntParameter(request, "lectureFlag", 0);
        int roomID = getIntParameter(request, "roomID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        User user = this.user.getLoginUser();
        LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setRoomID(roomID);
        serviceRequest.setTenantID(TenantContext.getTenantId());
        serviceRequest.setModeratorPIN(moderatorPIN);

        LectureModeControlResponse serviceResponse = null;

        if (1 == lectureFlag) {
            serviceResponse = lectureModeService.startLectureMode(serviceRequest);
        } else {
            serviceResponse = lectureModeService.stopLectureMode(serviceRequest);
        }
        if (serviceResponse.getStatus() != LectureModeControlResponse.SUCCESS) {
            FieldError fe = new FieldError("setLectureModeState", "setLectureModeState",
                    ms.getMessage(serviceResponse.getMessage(), null, "", loc));
            errors.add(fe);
        }
        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setpresenter.ajax", method = RequestMethod.POST)
    public ModelAndView setPresenterAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int endpointID = getIntParameter(request, "endpointID", 0);
        String endpointType = getStringParameter(request, "endpointType", "D");
        int roomID = getIntParameter(request, "roomID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        User user = this.user.getLoginUser();
        LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setRoomID(roomID);
        serviceRequest.setTenantID(TenantContext.getTenantId());
        serviceRequest.setModeratorPIN(moderatorPIN);
        serviceRequest.setEndpointID(endpointID);
        serviceRequest.setEndpointType(endpointType);

        LectureModeControlResponse serviceResponse = lectureModeService.setPresenter(serviceRequest);

        if (serviceResponse.getStatus() != LectureModeControlResponse.SUCCESS) {
            FieldError fe = new FieldError("setPresenter", "setPresenter",
                    ms.getMessage(serviceResponse.getMessage(), null, "", loc));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/unsetpresenter.ajax", method = RequestMethod.POST)
    public ModelAndView unsetPresenterAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int endpointID = getIntParameter(request, "endpointID", 0);
        String endpointType = getStringParameter(request, "endpointType", "D");
        int roomID = getIntParameter(request, "roomID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        User user = this.user.getLoginUser();
        LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setRoomID(roomID);
        serviceRequest.setTenantID(TenantContext.getTenantId());
        serviceRequest.setModeratorPIN(moderatorPIN);
        serviceRequest.setEndpointID(endpointID);
        serviceRequest.setEndpointType(endpointType);

        LectureModeControlResponse serviceResponse = lectureModeService.removePresenter(serviceRequest);

        if (serviceResponse.getStatus() != LectureModeControlResponse.SUCCESS) {
            FieldError fe = new FieldError("setPresenter", "setPresenter",
                    ms.getMessage(serviceResponse.getMessage(), null, "", loc));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/dismissHand.ajax", method = RequestMethod.POST)
    public ModelAndView dismissHandAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int endpointID = getIntParameter(request, "endpointID", 0);
        String endpointType = getStringParameter(request, "endpointType", "D");
        int roomID = getIntParameter(request, "roomID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        User user = this.user.getLoginUser();
        LectureModeParticipantControlRequest serviceRequest = new LectureModeParticipantControlRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setRoomID(roomID);
        serviceRequest.setTenantID(TenantContext.getTenantId());
        serviceRequest.setModeratorPIN(moderatorPIN);
        serviceRequest.setEndpointID(endpointID);
        serviceRequest.setEndpointType(endpointType);

        LectureModeControlResponse serviceResponse = lectureModeService.dismissRaisedHand(serviceRequest);

        if (serviceResponse.getStatus() != LectureModeControlResponse.SUCCESS) {
            FieldError fe = new FieldError("dismissHand", "dismissHand",
                    ms.getMessage(serviceResponse.getMessage(), null, "", loc));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/dismissAllHands.ajax", method = RequestMethod.POST)
    public ModelAndView dismissAllHandsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        int roomID = getIntParameter(request, "roomID", 0);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        User user = this.user.getLoginUser();
        LectureModeControlRequest serviceRequest = new LectureModeControlRequest();
        serviceRequest.setMemberID(user.getMemberID());
        serviceRequest.setRoomID(roomID);
        serviceRequest.setTenantID(TenantContext.getTenantId());
        serviceRequest.setModeratorPIN(moderatorPIN);

        LectureModeControlResponse serviceResponse = lectureModeService.dismissAllRaisedHands(serviceRequest);

        if (serviceResponse.getStatus() != LectureModeControlResponse.SUCCESS) {
            FieldError fe = new FieldError("dismissAllHands", "dismissAllHands",
                    ms.getMessage(serviceResponse.getMessage(), null, "", loc));
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/doAllEndpointsSupportLectureMode.ajax", method = RequestMethod.POST)
    public ModelAndView doAllEndpointsSupportLectureMode(HttpServletRequest request,
                                                         HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int controllingMemberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            controllingMemberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();
        int roomID = getIntParameter(request, "roomID", 0);
        String moderatorPIN = getStringParameter(request,
                "moderatorPIN", "noModeratorPIN");


        // Invalid RoomID
        if (roomID <= 0) {
            FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                    "Invalid RoomId");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        Room roomToJoin = this.roomService.getRoomDetailsForConference(roomID);

        // Invalid Room
        if (roomToJoin == null) {
            FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                    "Invalid RoomId");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        boolean canControlRoom = canMemberControlRoom(controllingMemberID, roomToJoin, moderatorPIN);

        // Cannot Control the Room
        if (!canControlRoom) {
            FieldError fe = new FieldError("CannotControlRoom",
                    "CannotControlRoom", ms.getMessage(
                    "unable.to.control.users.in.the.conference", null,
                    "", loc));
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);

            return new ModelAndView("ajax/result_ajax", "model", model);
        }


        TenantConfiguration tenantConfiguration = this.tenantService.getTenantConfiguration(TenantContext.getTenantId());
        if (tenantConfiguration.getLectureModeStrict() == 0) {
            model.put("success", Boolean.TRUE); // if not enforced, pretend they all support lecture mode
        } else {
            if (lectureModeService.getCountEndpointsWithoutLectureModeSupportInRoom(roomID) > 0) {
                model.put("success", Boolean.FALSE);
            } else {
                model.put("success", Boolean.TRUE);
            }
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/search.ajax", method = RequestMethod.POST)
    public ModelAndView getFlexSearchAjax(HttpServletRequest request,
                                          HttpServletResponse response) throws Exception {
        Boolean excludeRooms = ServletRequestUtils.getBooleanParameter(request,
                "excluderooms", false);

        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        EntityFilter filter = new EntityFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        if ((filter != null) && filter.getQuery().isEmpty()) {
            List<FieldError> errors = new ArrayList<FieldError>();
            FieldError fe = new FieldError("Search", "Search", "Empty search string");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            logger.error("Search:->" + user.getUsername() + ":" + filter.getQuery() + ":");
            return new ModelAndView("ajax/result_ajax", "model", model);
        }


        Entity myroom = this.member.getOwnRoomDetails(memberID);
        myroom.setName(myroom.getName());
        model.put("myroom", myroom);

        Control control = null;

        // Busy or Busy in own Room
        if (myroom.getMemberStatus() == 2 || myroom.getMemberStatus() == 9) {
            control = this.conferenceService.getControlForMember(memberID, null);
        }

        if (control != null) {
            model.put("control", control);
        }

        int num = 0;
        List<RoomIdSearchResult> roomIdSearchResults = this.roomService.searchRoomIds(filter, user);
        if (!roomIdSearchResults.isEmpty() && roomIdSearchResults.get(0).getTotalCount() > 0) {
            List<Integer> roomIds = new ArrayList<Integer>(roomIdSearchResults.size());
            roomIds = roomIdSearchResults.stream().map(roomIdSeacrhResult -> roomIdSeacrhResult.getRoomId()).collect(Collectors.toList());
            List<Entity> entities = this.roomService.getEntities(roomIds, user, filter);
            model.put("list", entities);
        }
        model.put("num", num);
        String joinURL = this.roomService.getRoomURL(system, request.getScheme(), request.getHeader("host"), myroom.getRoomKey());
        if (tenantService.isTenantNotAllowingGuests()) {
            joinURL += "&noguest";
        }
        model.put("roomURLFormated", joinURL);
        return new ModelAndView("ajax/flex_entities_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/invite_content.ajax", method = RequestMethod.POST)
    public ModelAndView getInviteContentAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        EntityFilter filter = new EntityFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }
        Map<String, Object> model = new HashMap<String, Object>();
        String type = getStringParameter(request, "type", "inviteRoom");
        int roomid = getIntParameter(request, "roomid", -1);
        String moderatorPIN = getStringParameter(request, "moderatorPIN", null);
        String content = "";
        String pin = "";
        Locale loc = LocaleContextHolder.getLocale();
        if (type.equals("inviteRoom")) {

            Tenant tenant = tenantService.getTenant(user.getTenantID());

            InviteEmailContentForInviteRoom inviteEmailContentForInviteRoom = new InviteEmailContentForInviteRoom();
            inviteEmailContentForInviteRoom.setGateWayDns(tenant.getVidyoGatewayControllerDns());
            inviteEmailContentForInviteRoom.setGuestsAllowed(tenant.getGuestLogin() == 1);
            inviteEmailContentForInviteRoom.setLocale(loc);
            inviteEmailContentForInviteRoom.setTenantUrl(tenant.getTenantURL());
            inviteEmailContentForInviteRoom.setTenantDialIn(tenant.getTenantDialIn());
            inviteEmailContentForInviteRoom.setTransportName(request.getScheme());

            Room aRoom = null;
            boolean overrideScheduledRoomProperties = false;
            if (roomid == -1) {
                Entity myroom = this.member.getContact(memberID);
                aRoom = this.roomService.getRoom(myroom.getRoomID());
            } else {
                aRoom = this.roomService.getRoom(roomid);
                if (!canMemberControlRoom(memberID, aRoom, moderatorPIN)) {
                    if (!StringUtils.isEmpty(moderatorPIN)) {
                        model.put("message", ms.getMessage("wrong.moderator.pin", null, "", loc));
                    }
                    model.put("result", "failure");
                    return new ModelAndView("ajax/invite_content_ajax", "model", model);
                }
                if (aRoom.getRoomType().equalsIgnoreCase("Scheduled")) {
                    overrideScheduledRoomProperties = true;
                }
            }

            inviteEmailContentForInviteRoom.setOverrideScheduledRoomProperties(overrideScheduledRoomProperties);
            inviteEmailContentForInviteRoom.setRoom(aRoom);
            content = this.system.getTenantInvitationEmailContent();
            if (org.apache.commons.lang.StringUtils.isBlank(content)) {
                content = this.system.getSuperInvitationEmailContent();
                if (org.apache.commons.lang.StringUtils.isBlank(content)) {
                    content = this.ms.getMessage("defaultInvitationEmailContent", null, "", loc);
                }
            }

            content = this.system.constructEmailInviteContentForInviteRoom(inviteEmailContentForInviteRoom, content, false);

        } else if (type.equals("voiceOnly")) {
            String ds = getStringParameter(request, "dialstring", "");
            if ("".equals(ds)) {
                ds = this.roomService.getTenantDialIn();
            }

            content = this.system.getTenantVoiceOnlyContent();
            if (content == null || content.trim().length() == 0) {
                content = this.system.getSuperVoiceOnlyContent();
                if (content == null || content.trim().length() == 0) {
                    content = ms.getMessage("defaultInvitationVoiceOnlyContent", null, "", loc);
                }
            }

            content = content.replaceAll("\\[DIALIN_NUMBER\\]", ds != null ? ds : "");
            String extension = "";
            String extensionOnly = "";
            if (roomid != -1) {
                Room aRoom = this.roomService.getRoom(roomid);
                if (!canMemberControlRoom(memberID, aRoom, moderatorPIN)) {
                    if (!StringUtils.isEmpty(moderatorPIN)) {
                        model.put("message", ms.getMessage("we.re.sorry.but.you.are.not.authorized.to.perform.the.requested.operation", null, "", loc));
                    }
                    model.put("result", "failure");
                    return new ModelAndView("ajax/invite_content_ajax", "model", model);
                }
                extension = aRoom.getRoomExtNumber();
                extensionOnly = extension;
                if (aRoom.getRoomPinned() == 0) {
                    ds += "x" + extension;
                } else {
                    pin = aRoom.getRoomPIN();
                    ds += "x" + extension + "*" + pin;
                    if (extension != null) {
                        extension = extension.concat("*").concat(pin);
                    }
                }
            }

            content = content.replaceAll("\\[PIN_ONLY\\]", pin);

            content = content.replaceAll("\\[EXTENSION\\]", extension);
            content = content.replaceAll("\\[EXTENSION_ONLY\\]", extensionOnly);
            content = content.replaceAll("\\[DIALSTRING\\]", ds);
        } else if (type.equals("webcast")) {
            String link = getStringParameter(request, "link", "");

            content = this.system.getTenantWebcastContent();
            if (content == null || content.trim().length() == 0) {
                content = this.system.getSuperWebcastContent();
                if (content == null || content.trim().length() == 0) {
                    content = ms.getMessage("defaultInvitationWebcastContent", null, "", loc);
                }
            }
            Room aRoom = this.roomService.getRoom(roomid);
            if (!canMemberControlRoom(memberID, aRoom, moderatorPIN)) {
                if (!StringUtils.isEmpty(moderatorPIN)) {
                    model.put("message", ms.getMessage("wrong.moderator.pin", null, "", loc));
                }
                model.put("result", "failure");
                return new ModelAndView("ajax/invite_content_ajax", "model", model);
            }
            try {
                this.replayService.getWebCastURLandPIN(aRoom);
            } catch (RemoteException e) {
                logger.error("Exception while invoking ReplayService", e);
            }

            if (link.length() == 0) {
                link = aRoom.getWebCastURL();
            }
            if (aRoom.getWebCastPinned() == 1) {
                pin = aRoom.getWebCastPIN();
                link += " (Webcast Pin: " + pin + ")";
                logger.debug("Webcast has PIN: " + aRoom.getWebCastPIN());
            }

            content = content.replaceAll("\\[WEBCASTURL\\]", link != null ? link : "");
            content = content.replaceAll("\\[PIN_ONLY\\]", pin);
        }
        model.put("content", content);
        logger.debug("Invite Content=" + content);

        String subject = this.system.constructEmailInviteSubjectForInviteRoom(loc);
        model.put("subject", subject);
        logger.debug("Invite Subject=" + subject);
        model.put("result", "success");
        return new ModelAndView("ajax/invite_content_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getroomurldetails.ajax", method = RequestMethod.POST)
    public ModelAndView getRoomURLDetails(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();

        int roomID = getIntParameter(request, "roomID", 0);
        String pin = getStringParameter(request, "PIN", "noModeratorPin");
        Room room = this.roomService.getRoom(roomID);

        if (room == null) {
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        // Cross tenant check
        int tenantId = TenantContext.getTenantId();
        if (room.getTenantID() != tenantId) {
            logger.error("getRoomURLDetails - Trying to access Unauthorized room - {}", roomID);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        if (roomID != 0 && room != null) {
            if (canMemberControlRoom(memberID, room, pin)) {
                //ensureRoomKeyExists(room);
                StringBuilder roomURL = new StringBuilder();

                String joinURL = this.roomService.getRoomURL(system, request.getScheme(), request.getHeader("host"), room.getRoomKey());

                roomURL.append(joinURL);
                if (tenantService.isTenantNotAllowingGuests()) {
                    roomURL.append("&noguest");
                }
                room.setRoomURL(roomURL.toString());

                model.put("room", room);
                model.put("success", Boolean.TRUE);
                return new ModelAndView("ajax/room_url_details_ajax", "model", model);
            } else {
                model.put("success", Boolean.FALSE);
            }
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/clearroomurl.ajax", method = RequestMethod.POST)
    public ModelAndView clearRoomURLAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();

        int roomID = getIntParameter(request, "entityID", 0);
        Room room = roomService.getRoom(roomID);

        if (roomID != 0 && room != null) {
            if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
                roomService.removeRoomKey(room);
                model.put("success", Boolean.TRUE);
            } else {
                model.put("success", Boolean.FALSE);
            }
        } else {
            model.put("success", Boolean.FALSE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/connectparticipant.ajax", method = RequestMethod.POST)
    public ModelAndView connectParticipantAjax(HttpServletRequest request,
                                               HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int controllingMemberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            controllingMemberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();
        int roomID = getIntParameter(request, "roomID", 0);
        int entityID = getIntParameter(request, "entityID",
                0);
        String input = getStringParameter(request, "input",
                "");
        String moderatorPIN = getStringParameter(request,
                "moderatorPIN", "noModeratorPIN");

        // Check EntityId and Input Params

        if (entityID <= 0 && input.equals("")) {
            FieldError fe = new FieldError("Invalid Input", "Invalid Input",
                    "Invalid Input");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        // Invalid RoomID
        if (roomID <= 0) {
            FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                    "Invalid RoomId");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        Room roomToJoin = this.roomService.getRoomDetailsForConference(roomID);

        // Invalid Room
        if (roomToJoin == null) {
            FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                    "Invalid RoomId");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        // Room Valid but locked
        if (roomToJoin.getRoomLocked() == 1) {
            FieldError fe = new FieldError("lock", "lock", ms.getMessage(
                    "room.is.locked", null, "", loc));
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);

            return new ModelAndView("ajax/result_ajax", "model", model);
        }
        
        boolean canControlRoom = canMemberControlRoom(controllingMemberID, roomToJoin, moderatorPIN);

        // Cannot Control the Room
        if (!canControlRoom) {
            FieldError fe = new FieldError("CannotControlRoom",
                    "CannotControlRoom", ms.getMessage(
                    "unable.to.control.users.in.the.conference", null,
                    "", loc));
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);

            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        if (entityID != 0) {
            Member invitee = null;
            // If the invitee is invalid return error
            invitee = this.member.getInviteeDetails(entityID);

            if (invitee == null) {
                FieldError fe = new FieldError("Invalid Invitee",
                        "Invalid Invitee", "Invalid Invitee");
                errors.add(fe);
                model.put("fields", errors);
                model.put("success", Boolean.FALSE);

                return new ModelAndView("ajax/result_ajax", "model", model);
            }
            //Get the Inviter Details
            Member inviter = this.member.getInviterDetails(controllingMemberID);

            try {
                if(invitee.getRoleName().equalsIgnoreCase("Legacy")) {
                    conferenceService.inviteLegacyToConference(roomToJoin, inviter, invitee);
                } else {
                    //apply restricted room access check
                    //fetch the member from member repository
                    if (!userGroupService.canMemberAccessRoom(roomID, invitee.getMemberID())) {
                        logger.error("User cannot access the restricted room " + " memberId :" + invitee.getMemberID() + " roomId " + roomID);
                        FieldError fe = new FieldError("Restricted Room", "Cannot access Restricted Room",
                                "Cannot access Restricted Room");
                        errors.add(fe);
                        model.put("fields", errors);
                        model.put("success", Boolean.FALSE);
                        return new ModelAndView("ajax/result_ajax", "model", model);

                    }
                    this.conferenceService.inviteParticipantToConference(roomToJoin, inviter, invitee, "D");
                }
            } catch (OutOfPortsException e) {
                FieldError fe = new FieldError("OutOfPorts", "OutOfPorts",
                        e.getMessage());
                errors.add(fe);
            } catch (EndpointNotExistException e) {
                FieldError fe = new FieldError("EndpointNotExist",
                        "EndpointNotExist",
                        "Unable to invite the specified user");
                errors.add(fe);
            } catch (InviteConferenceException e) {
                FieldError fe = new FieldError("InviteConference",
                        "InviteConference",
                        "Unable to invite the specified user");
                errors.add(fe);
            } catch (LectureModeNotSupportedException e) {
                FieldError fe = new FieldError("InviteConference",
                        "InviteConference",
                        ms.getMessage("user.does.not.support.lecture.mode", null, "", loc));
                errors.add(fe);
            }
        } else if (!input.equals("")) {
            //Legacy Device
			/*Invite invite = new Invite();
			invite.setFromMemberID(controllingMemberID);
			invite.setFromRoomID(roomID);
			invite.setSearch(input);
			invite.setToMemberID(0);
			invite.setToEndpointID(0);*/

            //Get the Inviter Details
            Member inviter = member.getInviterDetails(controllingMemberID);
            //Create Legacy Details
            Member invitee = new Member();
            invitee.setMemberName(input);
            invitee.setRoomExtNumber(input);
            invitee.setRoleName("Legacy");
            invitee.setTenantID(roomToJoin.getTenantID());
            try {
                conferenceService.inviteLegacyToConference(roomToJoin, inviter, invitee);
                //this.conferenceService.inviteToConference(invite);
            } catch (OutOfPortsException e) {
                FieldError fe = new FieldError("OutOfPorts", "OutOfPorts",
                        e.getMessage());
                errors.add(fe);
            } catch (EndpointNotExistException e) {
                FieldError fe = new FieldError("EndpointNotExist",
                        "EndpointNotExist",
                        "Unable to invite the specified user");
                errors.add(fe);
            } catch (InviteConferenceException e) {
                FieldError fe = new FieldError("InviteConference",
                        "InviteConference",
                        "Unable to invite the specified user");
                errors.add(fe);
            }
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/connectparticipants.ajax", method = RequestMethod.POST)
    public ModelAndView connectParticipantsAjax(HttpServletRequest request,
                                                HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int controllingMemberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            controllingMemberID = user.getMemberID();
        }

        int roomID = getIntParameter(request, "roomID", 0);
        String listOfentityID = getStringParameter(request,
                "list", "");
        String listOfinput = getStringParameter(request,
                "inputlist", "");
        String moderatorPIN = getStringParameter(request,
                "moderatorPIN", "noModeratorPIN");

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        Locale loc = LocaleContextHolder.getLocale();

        // Check EntityId and Input Params

        if (listOfentityID.equals("") && listOfinput.equals("")) {
            FieldError fe = new FieldError("Invalid Input", "Invalid Input",
                    "Invalid Input");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        if (roomID <= 0) {
            FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                    "Invalid RoomId");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        Room roomToJoin = this.roomService.getRoomDetailsForConference(roomID);

        // Invalid Room
        if (roomToJoin == null) {
            FieldError fe = new FieldError("Invalid Room", "Invalid Room",
                    "Invalid RoomId");
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        // Room Valid but locked
        if (roomToJoin.getRoomLocked() == 1) {
            FieldError fe = new FieldError("lock", "lock", ms.getMessage(
                    "room.is.locked", null, "", loc));
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        boolean canControlRoom = canMemberControlRoom(controllingMemberID,
                roomToJoin, moderatorPIN);

        // Cannot Control the Room
        if (!canControlRoom) {
            FieldError fe = new FieldError("CannotControlRoom",
                    "CannotControlRoom", ms.getMessage(
                    "unable.to.control.users.in.the.conference", null,
                    "", loc));
            errors.add(fe);
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);

            return new ModelAndView("ajax/result_ajax", "model", model);
        }

        // Get the Inviter Details
        Member inviter = this.member.getInviterDetails(controllingMemberID);

        if (!listOfentityID.equalsIgnoreCase("")) {
            String[] entityIds = listOfentityID.split(",");
            for (String entityId : entityIds) {
                Member invitee = this.member.getInviteeDetails(Integer
                        .parseInt(entityId));
                if (invitee == null) {
                    logger.warn("Invalid Invitee -" + entityId);
                    FieldError fe = new FieldError("InviteConference",
                            "InviteConference", "Unable to invite the user -"
                            + entityId);
                    errors.add(fe);
                    continue;
                }
                // If the invitee is valid, invite the user to conf
                try {
                    if (invitee.getRoleName().equalsIgnoreCase("Legacy")) {
                        conferenceService.inviteLegacyToConference(roomToJoin, inviter, invitee);
                    } else {
                        //apply restricted room access check
                        //fetch the member from member repository
                        if (!userGroupService.canMemberAccessRoom(roomID, invitee.getMemberID())) {
                            logger.error("User cannot access the restricted room " + " memberId :" + invitee.getMemberID() + " roomId " + roomID);
                            FieldError fe = new FieldError("Restricted Room", "Cannot access Restricted Room",
                                    "Cannot access Restricted Room");
                            errors.add(fe);
                            model.put("fields", errors);
                            model.put("success", Boolean.FALSE);
                            return new ModelAndView("ajax/result_ajax", "model", model);
                        }
                        this.conferenceService.inviteParticipantToConference(roomToJoin, inviter, invitee, "D");
                    }
                } catch (OutOfPortsException e) {
                    FieldError fe = new FieldError("OutOfPorts", "OutOfPorts",
                            e.getMessage());
                    errors.add(fe);
                } catch (EndpointNotExistException e) {
                    FieldError fe = new FieldError("EndpointNotExist",
                            "EndpointNotExist",
                            "Unable to invite the specified user");
                    errors.add(fe);
                } catch (InviteConferenceException e) {
                    FieldError fe = new FieldError("InviteConference",
                            "InviteConference",
                            "Unable to invite the specified user");
                    errors.add(fe);
                } catch (LectureModeNotSupportedException e) {
                    FieldError fe = new FieldError("InviteConference",
                            "InviteConference",
                            ms.getMessage("user.does.not.support.lecture.mode", null, "", loc));
                    errors.add(fe);
                }
            }

        }

        if (!listOfinput.equalsIgnoreCase("")) {
            String[] inputVals = listOfinput.split(",");
            for (String inputVal : inputVals) {
                // Create Legacy Details
                Member invitee = new Member();
                invitee.setMemberName(inputVal);
                invitee.setRoomExtNumber(inputVal);
                invitee.setRoleName("Legacy");
                invitee.setTenantID(roomToJoin.getTenantID());
                try {
                    conferenceService.inviteLegacyToConference(roomToJoin, inviter,
                            invitee);
                    // this.conferenceService.inviteToConference(invite);
                } catch (OutOfPortsException e) {
                    FieldError fe = new FieldError("OutOfPorts", "OutOfPorts",
                            e.getMessage());
                    errors.add(fe);
                } catch (EndpointNotExistException e) {
                    FieldError fe = new FieldError("EndpointNotExist",
                            "EndpointNotExist",
                            "Unable to invite the specified user");
                    errors.add(fe);
                } catch (InviteConferenceException e) {
                    FieldError fe = new FieldError("InviteConference",
                            "InviteConference",
                            "Unable to invite the specified user");
                    errors.add(fe);
                }
            }
        }
        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setwebcastpin.ajax", method = RequestMethod.POST)
    public ModelAndView setWebCastPINAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        Locale loc = LocaleContextHolder.getLocale();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        String pin = getStringParameter(request, "PIN", "");
        int roomID = getIntParameter(request, "roomID", 0);
        Room room = roomService.getRoom(roomID);
        if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), pin)) {
            String[] args = {""+system.getMinPINLengthForTenant(TenantContext.getTenantId()), ""+SystemServiceImpl.PIN_MAX};
            FieldError fe = new FieldError("setWebcastPIN", "setWebcastPIN", ms.getMessage("webcast.pin.must.be.3.10.digits", args, "", loc));
            errors.add(fe);
        } else if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
            try {
                replayService.updateWebCastPIN(room, pin);
            } catch ( RemoteException
                    | NoVidyoReplayException
                    | GeneralFaultException
                    | InvalidArgumentFaultException  e) {
                FieldError fe = new FieldError("Replay", "Replay", e.getMessage());
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("Replay", "Replay", "");
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/clearwebcastpin.ajax", method = RequestMethod.POST)
    public ModelAndView clearWebCastPINAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int roomID = getIntParameter(request, "roomID", 0);
        Room room = roomService.getRoom(roomID);

        if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
            try {
                replayService.updateWebCastPIN(room, "");
            } catch (RemoteException | NoVidyoReplayException
                    | InvalidArgumentFaultException | GeneralFaultException e) {
                FieldError fe = new FieldError("Replay", "Replay", e.getMessage());
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("Replay", "Replay", "");
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/setwebcasturl.ajax", method = RequestMethod.POST)
    public ModelAndView setWebCastURLAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int roomID = getIntParameter(request, "roomID", 0);
        Room room = roomService.getRoom(roomID);

        if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
            try {
                replayService.generateWebCastURL(room);
            } catch (RemoteException | NoVidyoReplayException | GeneralFaultException
                    | InvalidArgumentFaultException e) {
                FieldError fe = new FieldError("Replay", "Replay", e.getMessage());
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("Replay", "Replay", "");
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/clearwebcasturl.ajax", method = RequestMethod.POST)
    public ModelAndView clearWebCastURLAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int roomID = getIntParameter(request, "roomID", 0);
        Room room = roomService.getRoom(roomID);

        if (canMemberControlRoom(memberID, room, "noModeratorPIN")) {
            try {
                replayService.deleteWebCastURL(room);
            } catch (RemoteException | NoVidyoReplayException | InvalidArgumentFaultException
                    | GeneralFaultException e) {
                FieldError fe = new FieldError("Replay", "Replay", e.getMessage());
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("Replay", "Replay", "");
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/recordtheconference.ajax", method = RequestMethod.POST)
    public ModelAndView recordTheConference(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        int memberID = 0;
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        } else {
            memberID = user.getMemberID();
        }

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int roomID = getIntParameter(request, "entityID", 0);
        String prefix = getStringParameter(request, "prefix", "");
        String webcast = getStringParameter(request, "webcast", "off");
        String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

        Room room = roomService.getRoom(roomID);

        if (canMemberControlRoom(memberID, room, moderatorPIN)) {
            try {
                conferenceService.recordTheConference(memberID, roomID, prefix, webcast.equalsIgnoreCase("on")?1:0);
            } catch (OutOfPortsException e) {
                FieldError fe = new FieldError("OutOfPorts", "OutOfPorts", e.getMessage());
                errors.add(fe);
            } catch (JoinConferenceException e) {
                FieldError fe = new FieldError("JoinConference", "JoinConference", e.getMessage());
                errors.add(fe);
            }
        } else {
            FieldError fe = new FieldError("Record", "Record", "");
            errors.add(fe);
        }

        if (errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }

        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/ui/getmyrecordurl.ajax", method = RequestMethod.POST)
    public ModelAndView getMyRecordURL(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = this.user.getLoginUser();
        if (user == null) {
            response.sendRedirect("flex.html?login_error=2");
            return null;
        }
        Map<String, Object> model = new HashMap<String, Object>();
        String url = tenantService.getTenantReplayURL(TenantContext.getTenantId());
        if (!url.equalsIgnoreCase("") && user != null) {
            String BAK = this.user.generateBAKforMember(user.getMemberID(), MemberBAKType.VidyoReplayLibrary);
            url += "/replay/recordingUI.html?token=" + URLEncoder.encode(BAK, "UTF-8");
        }
        model.put("url", url);
        return new ModelAndView("ajax/flex_record_url_ajax", "model", model);
    }
    
    @RequestMapping(value = "/ui/muteparticipant.ajax", method = RequestMethod.POST)
    public ModelAndView muteParticipantAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();
		int memberID = 0;
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			memberID = user.getMemberID();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		int endpointID = getIntParameter(request, "endpointID", 0);
		String endpointType = getStringParameter(request, "endpointType", "D");
		int roomID = getIntParameter(request, "roomID", 0);
		String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

		if (roomID != 0) {
			Room room = roomService.getRoom(roomID);
	        if (room != null) {
				if (canMemberControlRoom(memberID, room, moderatorPIN)) {
					if (endpointID != 0) {
						if (endpointType.equalsIgnoreCase("F")) {
							try {
								try {
									federationConferenceService.sendMuteAudioForParticipantInFederation(endpointID, false); // try http
								} catch (RemoteException e) {
									federationConferenceService.sendMuteAudioForParticipantInFederation(endpointID, true); // try https
								}
							} catch (MuteAudioException e) {
								FieldError fe = new FieldError("MuteAudioForParticipant", "MuteAudioForParticipant", e.getMessage());
								errors.add(fe);
							}
						} else {
							String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
							if (!endpointGUID.equalsIgnoreCase("")) {
								try {
									conferenceService.muteAudio(endpointGUID);
								} catch (Exception e) {
									FieldError fe = new FieldError("MuteAudioForParticipant", "MuteAudioForParticipant", e.getMessage());
									errors.add(fe);
								}
							}
						}
					}
				} else {
					FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
					errors.add(fe);
				}
	        } else {
		        FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
		        errors.add(fe);
	        }
		} else {
			FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/ui/unmuteparticipant.ajax", method = RequestMethod.POST)
	public ModelAndView unmuteParticipantAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();
		int memberID = 0;
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			memberID = user.getMemberID();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		int endpointID = getIntParameter(request, "endpointID", 0);
		String endpointType = getStringParameter(request, "endpointType", "D");
		int roomID = getIntParameter(request, "roomID", 0);
		String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

		if (roomID != 0) {
			Room room = roomService.getRoom(roomID);
	        if (room != null) {
				if (canMemberControlRoom(memberID, room, moderatorPIN)) {
					if (endpointID != 0) {
						if (endpointType.equalsIgnoreCase("F")) {
							try {
								try {
									federationConferenceService.sendUnmuteAudioForParticipantInFederation(endpointID, false); // try http
								} catch (RemoteException e) {
									federationConferenceService.sendUnmuteAudioForParticipantInFederation(endpointID, true); // try https
								}
							} catch (UnmuteAudioException e) {
								FieldError fe = new FieldError("UnmuteAudioForParticipant", "UnmuteAudioForParticipant", e.getMessage());
								errors.add(fe);
							}
						} else {
							String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
							if (!endpointGUID.equalsIgnoreCase("")) {
								try {
									conferenceService.unmuteAudio(endpointGUID);
								} catch (Exception e) {
									FieldError fe = new FieldError("UnmuteAudioForParticipant", "UnmuteAudioForParticipant", e.getMessage());
									errors.add(fe);
								}
							}
						}
					}
				} else {
					FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
					errors.add(fe);
				}
	        } else {
		        FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
		        errors.add(fe);
	        }
		} else {
			FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/ui/startvideo.ajax", method = RequestMethod.POST)
	public ModelAndView startVideoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();
		int memberID = 0;
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			memberID = user.getMemberID();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		int endpointID = getIntParameter(request, "endpointID", 0);
		String endpointType = getStringParameter(request, "endpointType", "D");
		int roomID = getIntParameter(request, "roomID", 0);
		String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

		if (roomID != 0) {
			Room room = roomService.getRoom(roomID);
	        if (room != null) {
				if (canMemberControlRoom(memberID, room, moderatorPIN)) {
					if (endpointID != 0) {
						if (endpointType.equalsIgnoreCase("F")) {
							try {
								try {
									federationConferenceService.sendStartVideoForParticipantInFederation(endpointID, false); // try http
								} catch (RemoteException e) {
									federationConferenceService.sendStartVideoForParticipantInFederation(endpointID, true); // try https
								}
							} catch (StartVideoException e) {
								FieldError fe = new FieldError("StartVideoForParticipant", "StartVideoForParticipant", e.getMessage());
								errors.add(fe);
							}
						} else {
							String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
							if (!endpointGUID.equalsIgnoreCase("")) {
								try {
									if(endpointType.equalsIgnoreCase("R")) {
										conferenceService.resumeRecording(endpointGUID);
									} else {
										conferenceService.startVideo(endpointGUID);
									}
								} catch (Exception e) {
									FieldError fe = new FieldError("StartVideoForParticipant", "StartVideoForParticipant", e.getMessage());
									errors.add(fe);
								}
							}
						}
					}
				} else {
					FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
					errors.add(fe);
				}
	        } else {
		        FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
		        errors.add(fe);
	        }
		} else {
			FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/ui/stopvideo.ajax", method = RequestMethod.POST)
	public ModelAndView stopVideoAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();
		int memberID = 0;
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			memberID = user.getMemberID();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		int endpointID = getIntParameter(request, "endpointID", 0);
		String endpointType = getStringParameter(request, "endpointType", "D");
		int roomID = getIntParameter(request, "roomID", 0);
		String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

		if (roomID != 0) {
			Room room = roomService.getRoom(roomID);
	        if (room != null) {
				if (canMemberControlRoom(memberID, room, moderatorPIN)) {
					if (endpointID != 0) {
						if (endpointType.equalsIgnoreCase("F")) {
							try {
								try {
									federationConferenceService.sendStopVideoForParticipantInFederation(endpointID, false); // try http
								} catch (RemoteException e) {
									federationConferenceService.sendStopVideoForParticipantInFederation(endpointID, true); // try https
								}
							} catch (StopVideoException e) {
								FieldError fe = new FieldError("StopVideoForParticipant", "StopVideoForParticipant", e.getMessage());
								errors.add(fe);
							}
						} else {
							String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
							if (!endpointGUID.equalsIgnoreCase("")) {
								try {
									if(endpointType.equalsIgnoreCase("R")) {
										conferenceService.pauseRecording(endpointGUID);
									} else {
										conferenceService.stopVideo(endpointGUID);
									}
								} catch (Exception e) {
									FieldError fe = new FieldError("StopVideoForParticipant", "StopVideoForParticipant", e.getMessage());
									errors.add(fe);
								}
							}
						}
					}
				} else {
					FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
					errors.add(fe);
				}
	        } else {
		        FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
		        errors.add(fe);
	        }
		} else {
			FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    @RequestMapping(value = "/ui/silenceparticipant.ajax", method = RequestMethod.POST)
	public ModelAndView silenceParticipantAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();
		int memberID = 0;
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			memberID = user.getMemberID();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		int endpointID = getIntParameter(request, "endpointID", 0);
		String endpointType = getStringParameter(request, "endpointType", "D");
		int roomID = getIntParameter(request, "roomID", 0);
		String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

		if (roomID != 0) {
			Room room = roomService.getRoom(roomID);
	        if (room != null) {
				if (canMemberControlRoom(memberID, room, moderatorPIN)) {
					if (endpointID != 0) {
						if (endpointType.equalsIgnoreCase("F")) {
							try {
								try {
									federationConferenceService.sendSilenceAudioForParticipantInFederation(endpointID, false); // try http
								} catch (RemoteException e) {
									federationConferenceService.sendSilenceAudioForParticipantInFederation(endpointID, true); // try https
								}
							} catch (SilenceAudioException e) {
								FieldError fe = new FieldError("SilenceAudioForParticipant", "SilenceAudioForParticipant", e.getMessage());
								errors.add(fe);
							}
						} else {
							String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
							if (!endpointGUID.equalsIgnoreCase("")) {
								try {
									conferenceService.silenceAudio(endpointGUID);
								} catch (Exception e) {
									FieldError fe = new FieldError("SilenceAudioForParticipant", "SilenceAudioForParticipant", e.getMessage());
									errors.add(fe);
								}
							}
						}
					}
				} else {
					FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
					errors.add(fe);
				}
	        } else {
		        FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
		        errors.add(fe);
	        }
		} else {
			FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

    /*
     * inviteParticipantToRoomAjax:-
     * This method is similar as connectParticipantAjax with following difference
     * A VidyoRoom or VidyoPanorama type of User can invite another online user to a conference room he/she have already joined
     * not necessary to his or her own room, it can be his or her own room or any other room he have joined.
     * HttpServletRequest has only one input parameter the "invitedMemberID", Id of the invited online user
     * The conference roomID is retrieved by the loggedin User's MemberID, (this joined room can be different than his own room)
     *
     * ALMOST DEAD CODE: used only by Flex VUI code
      */
    @RequestMapping(value = "/ui/inviteparticipant.ajax", method = RequestMethod.POST)
    public ModelAndView inviteParticipantToRoomAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user =  null;
        Member caller = null;
        String callerRole = null;
        Entity invitedMemberEntity = null;
        int roomID =  0;
        int memberID = 0;    // Caller
        int invitedMemberID = 0;    // Invited Member's ( roomID, memberID) came in as input parameter
        Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();

        // Load the logged in user, who is the inviter/caller to conference
        user = this.user.getLoginUser();
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			memberID = user.getMemberID();
		}
		Locale loc = LocaleContextHolder.getLocale();
        caller = this.member.getMember(memberID);

        //get roomID/memberID of called or invited member, came in as input parameter
        invitedMemberID = getIntParameter(request, "invitedMemberID", 0);

        // Validation 1: Caller role needs to be VidyoRoom  or VidyoPanorama
        if(caller != null)  {
                callerRole = caller.getRoleName();
        }
        else {
             FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user");
			 errors.add(fe);
        }
        if(null != callerRole){
            if(callerRole.equals("VidyoRoom") || callerRole.equals("VidyoPanorama")) {
                 // Validation 2: Caller needs to be busy in a conference room to be able to invite others to that room he joined not necessaryly his own room
                roomID =  this.member.getJoinedConferenceRoomId(memberID);
                // If roomID non Zero returned that means the caller (member) has already joined this room (so he is busy no need to chek it's status)
                if(roomID > 0) {
                    Member invitedMember =   this.member.getMemberByRoom(invitedMemberID) ;
                    invitedMemberEntity = this.member.getContact(invitedMember.getMemberID()) ;
                    if(null != invitedMemberEntity)  {
                        int intCalledMemberStatus = invitedMemberEntity.getMemberStatus();  //0 = offline, 1= online, 2= busy,.. 9 = busy in own room ...
                        String roomType = invitedMemberEntity.getRoomType();
                        // Validation 3: Invited member's status needs to be online  or it's a Legacy Device
                        if(intCalledMemberStatus==1 || roomType.equals("Legacy") )  {
                            // Finally Invite the user
                            Invite invite = new Invite();
                            invite.setFromMemberID(caller.getMemberID());
                            invite.setFromRoomID(roomID);
                            invite.setToMemberID(invitedMemberEntity.getMemberID());
                            invite.setToEndpointID(invitedMemberEntity.getEndpointID());
                            // All 3 validations successfull Invite the Member now
                            try {
                                conferenceService.inviteToConference(invite);
                            } catch (OutOfPortsException e) {
                                FieldError fe = new FieldError("OutOfPorts", "OutOfPorts", e.getMessage());
                                errors.add(fe);
                            } catch (EndpointNotExistException e) {
                                FieldError fe = new FieldError("EndpointNotExist", "EndpointNotExist", "Unable to invite the specified user");
                                errors.add(fe);
                            } catch (InviteConferenceException e) {
                                FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user");
                                errors.add(fe);
                            }  catch (LectureModeNotSupportedException e) {
                                FieldError fe = new FieldError("InviteConference", "InviteConference", ms.getMessage("user.does.not.support.lecture.mode", null, "", loc));
                                errors.add(fe);
                            }
                        }
                        else  {
                             FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user, the user is not online");
                             errors.add(fe);
                        }

                    }
                    else  {
                      FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user, the user is not valid");
                      errors.add(fe);
                }
                }
                else  {
                      FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user, you need join a conference room");
                      errors.add(fe);
                }
            }
            else {
                 FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user, you need to login as VidyoRoom or VidyoPanorama");
                 errors.add(fe);
            }
        }
        else {
                 FieldError fe = new FieldError("InviteConference", "InviteConference", "Unable to invite the specified user, you need to login as VidyoRoom or VidyoPanorama");
                 errors.add(fe);
        }
		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
    
    @RequestMapping(value = "/ui/disconnectparticipant.ajax", method = RequestMethod.POST)
	public ModelAndView disconnectParticipantAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User user = this.user.getLoginUser();
		int controllingMemberID = 0;
		if (user == null) {
			response.sendRedirect("flex.html?login_error=2");
			return null;
		} else {
			controllingMemberID = user.getMemberID();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		List<FieldError> errors = new ArrayList<FieldError>();
		Locale loc = LocaleContextHolder.getLocale();

		int endpointID = getIntParameter(request, "endpointID", 0);
		String endpointType = getStringParameter(request, "endpointType", "D");
		int roomID = getIntParameter(request, "roomID", 0);
		String moderatorPIN = getStringParameter(request, "moderatorPIN", "noModeratorPIN");

		if (roomID != 0) {
			//Room room = this.room.getRoom(roomID);
			Room roomToDisconnect = roomService.getRoomDetailsForConference(roomID);
			if (roomToDisconnect != null) {
				if (canMemberControlRoom(controllingMemberID, roomToDisconnect, moderatorPIN)) {
					if (endpointID != 0) {
						if (endpointType.equalsIgnoreCase("F")) {
							try {
								try {
									federationConferenceService.sendDisconnectParticipantFromFederation(endpointID, roomID, false); // try http
								} catch (RemoteException e) {
									federationConferenceService.sendDisconnectParticipantFromFederation(endpointID, roomID, true); // try https
								}
							} catch (LeaveConferenceException e) {
								FieldError fe = new FieldError("LeaveConference", "LeaveConference", e.getMessage());
								errors.add(fe);
							}
						} else {
							String endpointGUID = conferenceService.getGUIDForEndpointID(endpointID, endpointType);
							if (!endpointGUID.equalsIgnoreCase("")) {
								try {
									conferenceService.leaveTheConference(endpointGUID, roomID, CallCompletionCode.BY_SOMEONE_ELSE);
								} catch (LeaveConferenceException e) {
									FieldError fe = new FieldError("LeaveConference", "LeaveConference", e.getMessage());
									errors.add(fe);
								}
							}
						}
					}
				} else {
					FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
					errors.add(fe);
				}
			} else {
				FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
				errors.add(fe);
			}
		} else {
			FieldError fe = new FieldError("CannotControlRoom", "CannotControlRoom", ms.getMessage("unable.to.control.users.in.the.conference", null, "", loc));
			errors.add(fe);
		}

		if (errors.size() != 0) {
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
		} else {
			model.put("success", Boolean.TRUE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
    
    protected boolean canMemberControlRoom(int memberID, Room room, String moderatorPIN) {
        boolean rc = false;
        //Step 1 Check the TenantContext TenantId and the Room's tenantId
        if(TenantContext.getTenantId() != room.getTenantID()) {
			logger.error("Trying to access Unauthorized room - {}", room.getRoomName() + " roomId " + room.getRoomID()
					+ " by Tenant Id " + TenantContext.getTenantId());
        	return rc;
        }
        // step 2 - user is an owner of room
        if (room.getMemberID() == memberID) {
            rc = true;
        } else {
            // step 2 - user has an admin or operator role
            String userRole = this.user.getLoginUserRole();
            if (userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_OPERATOR")) {
                rc = true;
            } else {
                // step 3 - user knows moderator PIN and he is a participant
                String roomModeratorPIN = room.getRoomModeratorPIN() != null ? room.getRoomModeratorPIN(): "";
                if ("".equals(roomModeratorPIN)) {
                    rc = false;
                } else if (roomModeratorPIN.equalsIgnoreCase(moderatorPIN)) {
                    rc = true;
                }
            }
        }
        return rc;
    }

    /**
     * Checks if the Member can control the room to which he is inviting the
     * Users. Returns true if the Member is Admin/Operator or if
     * Member's input
     * moderator pin matches the room's Moderator pin
     *
     * @param controllingMemberID
     * @param roomID
     * @param roomOwnerID
     * @return
     */
    /*private boolean canMemberControlRoom(int controllingMemberID, int roomID,
                                         int roomOwnerID, String roomModeratorPin, String inputModeratorPin) {
        boolean canControl = false;

        //Step 1 Check the TenantContext TenantId and the Room's tenantId
        if(TenantContext.getTenantId() != room.getTenantID()) {
        	return canControl;
        }
        if (controllingMemberID == roomOwnerID) {
            canControl = true;
        } else {
            String userRole = this.user.getLoginUserRole();
            if ((userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_OPERATOR")) ||
                    (roomModeratorPin != null && !"".equals(roomModeratorPin) && roomModeratorPin.equalsIgnoreCase(inputModeratorPin))) {
                canControl = true;
            }
        }
        return canControl;
    }*/

    // remove quotes for display purposes (VP-5533), e.g. "HD" -> HD
    private String cleanseRecorderProfileName(String actualRecorderName) {
        if (actualRecorderName != null &&
                actualRecorderName.length() >= 2 &&
                actualRecorderName.charAt(0) == '"' &&
                actualRecorderName.charAt(actualRecorderName.length()-1) == '"' ) {
            return actualRecorderName.substring(1,actualRecorderName.length()-1);
        }
        return actualRecorderName;
    }

}
