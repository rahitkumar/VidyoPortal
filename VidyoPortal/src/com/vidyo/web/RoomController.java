package com.vidyo.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vidyo.bo.endpoints.Endpoint;
import com.vidyo.bo.endpoints.EndpointFilter;
import com.vidyo.bo.memberbak.MemberBAKType;
import com.vidyo.bo.usergroup.UserGroup;
import com.vidyo.db.repository.member.MemberRepository;
import com.vidyo.db.repository.room.RoomRepository;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.user.AccessKeyResponse;
import com.vidyo.utils.ValidationUtils;
import com.vidyo.utils.room.RoomUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.owasp.esapi.ESAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.util.HtmlUtils;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.bo.Group;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.RoomFilter;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IReplayService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.service.SystemServiceImpl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.text.MessageFormat;
import java.util.*;

@Controller
public class RoomController {

	private static final Logger logger = LoggerFactory.getLogger(RoomController.class);

	@Autowired
    private IRoomService service;

	@Autowired
    private IUserService user;

	@Autowired
	@Qualifier("conferenceService")
	private IConferenceService conference;

	@Autowired
    private ISystemService system;

	@Autowired
    private CookieLocaleResolver lr;

	@Autowired
    private ReloadableResourceBundleMessageSource ms;

	@Autowired
    private IReplayService replay;

	@Autowired
    private ITenantService tenantService;

    @Autowired
    private IGroupService groupService;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private RoomRepository roomRepository;

	@Autowired
    private EndpointService endpointService;

	@RequestMapping(value = "/rooms.html", method = RequestMethod.GET)
	public ModelAndView getRoomsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
		this.user.setLoginUser(model, response);

        model.put("nav", "rooms");

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

        try {
            String logopath = this.system.getCustomizedLogoName();
            if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
                model.put("customizedLogoPath", logopath);
            }
        } catch (Exception ignored) {}

		return new ModelAndView("admin/rooms_html", "model", model);
    }

	@RequestMapping(value = "/generateroomkey.ajax", method = RequestMethod.GET)
	  public ModelAndView generateRoomKeyAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

	        if (!request.isRequestedSessionIdValid()) {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	            return null;
	        } else {
	            int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);

	            Map<String, Object> model = new HashMap<String, Object>();
	            Room room = service.getRoom(roomID);

	            roomID = service.generateRoomKey(room);

	            if (roomID > 0) {
	                model.put("success", Boolean.TRUE);
	            } else {
	                model.put("success", Boolean.FALSE);

	            }

	            if(tenantService.isTenantNotAllowingGuests()){
	    			model.put("noguest", "&noguest");
	    		}
	            else{
	            	model.put("noguest", "");
	            }

	            return new ModelAndView("ajax/result_ajax", "model", model);
	        }
	    }

	  @RequestMapping(value = "/removeroomkey.ajax", method = RequestMethod.POST)
	  public ModelAndView removeRoomKeyAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

	        if (!request.isRequestedSessionIdValid()) {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	            return null;
	        } else {
	            int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);

	            Map<String, Object> model = new HashMap<String, Object>();
	            Room room = service.getRoom(roomID);

	            roomID = service.removeRoomKey(room);

	            if (roomID > 0) {
	                model.put("success", Boolean.TRUE);
	            } else {
	                model.put("success", Boolean.FALSE);

	            }

	            return new ModelAndView("ajax/result_ajax", "model", model);
	        }
	    }

	@RequestMapping(value = "/rooms.ajax", method = RequestMethod.GET)
    public ModelAndView getRoomsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    Map<String, Object> model = new HashMap<String, Object>();

        RoomFilter filter = new RoomFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Long num = this.service.getCountRooms(filter);
        model.put("num", num);
        List<Room> list = this.service.getRooms(filter);

        list = this.service.getRoomStatusAndNumOfParticipants(list);

        model.put("list", list);

        return new ModelAndView("ajax/rooms_ajax", "model", model);
    }

	@RequestMapping(value = "/room.html", method = RequestMethod.GET)
	public ModelAndView getRoomHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "rooms");

		this.user.setLoginUser(model, response);

		int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		try {
			String logopath = this.system.getCustomizedLogoName();
			if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
				model.put("customizedLogoPath", logopath);
			}
		} catch (Exception ignored) {
		}

		model.put("roomID", roomID);
		model.put("tenantPrefix", this.service.getTenantPrefix());
		int tenantId = TenantContext.getTenantId();

		if (roomID != 0) {
			Room room;
			try {
				room = this.service.getRoom(roomID);
			} catch (Exception e) {
				logger.error("Trying to access non-existent room -{}", roomID);
				return new ModelAndView("admin/rooms_html", "model", model);
			}

			// Check if room's tenantId and tenantHolder's tenantId are same
			if (room == null || tenantId != room.getTenantID()) {
				logger.error("Trying to access unauthorized room -{}", roomID);
				return new ModelAndView("admin/rooms_html", "model", model);
			}

			if (room.getRoomKey() != null && !room.getRoomKey().equalsIgnoreCase("")) {
				String joinURL = service.getRoomURL(system, request.getScheme(), request.getHeader("host"), room.getRoomKey());
				room.setRoomURL(joinURL);
				if (tenantService.isTenantNotAllowingGuests()) {
					room.setRoomURL(room.getRoomURL() + "&noguest");
				}
			}
			model.put("roomName", ESAPI.encoder().encodeForJavaScript(room.getRoomName()));
			model.put("roomKey", room.getRoomKey());
			model.put("roomExtNumber", room.getRoomExtNumber());

			boolean roomTypePersonal = false;
			if (room.getRoomTypeID() == 1 || room.getRoomType().equalsIgnoreCase("Personal")) {
				roomTypePersonal = true;
			}
			model.put("roomTypePersonal", roomTypePersonal);

			if (room.getRoomPIN() != null) {
				model.put("roomPIN", "yes");
			}
			if (room.getRoomModeratorPIN() != null) {
				model.put("roomModeratorPIN", "yes");
			}
			model.put("importedUsed", room.getImportedUsed());
		} else {
			model.put("roomName", "");
			model.put("importedUsed", "0");
		}

		return new ModelAndView("admin/room_html", "model", model);
	}

	@RequestMapping(value = "/room.ajax", method = RequestMethod.GET)
	public ModelAndView getRoomAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);
		model.put("roomID", roomID);

		Room room = null;
		if (roomID == 0) {
			room = new Room();
			room.setTenantPrefix(this.service.getTenantPrefix());
			model.put("room", room);
			return new ModelAndView("ajax/room_ajax", "model", model);
		}

		try {
			room = this.service.getRoom(roomID);
		} catch (Exception e) {
			logger.error("Trying to access non-existent room - {}", roomID);
		}
		int tenantId = TenantContext.getTenantId();

		if (room == null || room.getTenantID() != tenantId) {
			room = new Room();
			room.setTenantPrefix(this.service.getTenantPrefix());
			model.put("room", room);
			logger.error("Trying to access unauthorized room - {}", roomID);
			return new ModelAndView("ajax/room_ajax", "model", model);
		}

		if (room.getRoomKey() != null && !room.getRoomKey().equalsIgnoreCase("")) {
			String joinURL = service.getRoomURL(system, request.getScheme(), request.getHeader("host"), room.getRoomKey());
			room.setRoomURL(joinURL);
			if (tenantService.isTenantNotAllowingGuests()) {
				room.setRoomURL(room.getRoomURL() + "&noguest");
			}
		}
		String stripTenantPrefix = room.getRoomExtNumber().replaceFirst(room.getTenantPrefix(), "");
		room.setRoomExtNumber(stripTenantPrefix);
		room.setDisplayName(room.getDisplayName());

		model.put("roomExtNumber", room.getRoomExtNumber());
		model.put("tenantPrefix", this.service.getTenantPrefix());
		if (roomID != 0) {
			model.put("roomName", room.getRoomName());
			model.put("importedUsed", room.getImportedUsed());boolean roomTypePersonal = false;
			if (room.getRoomTypeID() == 1 || room.getRoomType().equalsIgnoreCase("Personal")) {
				roomTypePersonal = true;
			}
			model.put("roomTypePersonal", roomTypePersonal);
			if (room.getRoomPIN() != null) {
				model.put("roomPIN", "yes");
				model.put("roomPinValue", "yes");
			}
			if (room.getRoomModeratorPIN() != null) {
				model.put("roomModeratorPIN", "yes");
				model.put("roomModeratorPinValue", "yes");
			}
		} else {
			model.put("roomName", "");
			model.put("importedUsed", "0");
		}

		model.put("room", room);

		return new ModelAndView("ajax/room_ajax", "model", model);
	}

    private void checkIsRoomForTenant(int tenantID, Room room) {
    	logger.debug("Check is Room for tenant = " + tenantID + " and roomID = " + room.getRoomID());

    	if (tenantID != room.getTenantID()) {
    		logger.error("Room with id = " + room.getRoomID() + "is not for tenant = " + tenantID);
    		throw new DataAccessException("Room is incorrect") {};
    	}
    }

	@RequestMapping(value = "/saveroom.ajax", method = RequestMethod.POST)
    public ModelAndView saveRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {
			Map<String, Object> model = new HashMap<String, Object>();

	    int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);
	    String displayName = ServletRequestUtils.getStringParameter(request, "rmDisplayName", null);

        Room room = new Room();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(room);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();

        Locale loc = LocaleContextHolder.getLocale();

        if (roomID != 0) { // not insert
        	Room currentRoom = this.service.getRoom(roomID);
        	try {
        		checkIsRoomForTenant(TenantContext.getTenantId(), currentRoom);
        	} catch (DataAccessException dae) {
        		FieldError fe = new FieldError("UpdateRoom", "UpdateRoom", MessageFormat.format(ms.getMessage("invalid.room", null, "", loc), room.getRoomName()));
        		results.addError(fe);
        	}
        }

        // validations
        if(displayName == null) {
       	 	FieldError fe = new FieldError("rmDisplayName", "rmDisplayName", MessageFormat.format(ms.getMessage("invalid.display.name", null, "", loc), room.getRoomName()));
            results.addError(fe);
		} else {
			displayName = displayName.trim();
		}
		int displayNameCount = service.getDisplayNameCount(displayName, roomID, TenantContext.getTenantId());
		if (displayNameCount > 0){
			FieldError fe = new FieldError("rmDisplayName", "rmDisplayName", MessageFormat.format(ms.getMessage("invalid.display.name", null, "", loc), room.getRoomName()));
            results.addError(fe);
		}

       room.setDisplayName(displayName);

       if (this.service.isRoomExistForRoomName(room.getRoomName(), roomID)) {
            FieldError fe = new FieldError("roomName", "roomName", MessageFormat.format(ms.getMessage("duplicate.room.name", null, "", loc), room.getRoomName()));
            results.addError(fe);
        }
       	//only for add a new room
        if (roomID==0 && this.service.getTenantCreatePublicRoomRemainCount( TenantContext.getTenantId())<=0) {
            FieldError fe = new FieldError("roomName", "roomName", MessageFormat.format(ms.getMessage("exceeded.allowed.room.number", null, "", loc), room.getRoomName()));
            results.addError(fe);
        }
        if (this.service.isRoomExistForRoomExtNumber(this.service.getTenantPrefix() + room.getRoomExtNumber(), roomID)) {
            FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", MessageFormat.format(ms.getMessage("duplicate.room.number", null, "", loc), room.getRoomExtNumber()));
            results.addError(fe);
        }
		if ((this.service.getTenantPrefix() + room.getRoomExtNumber()).length() > 64) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", "Extension (with tenant prefix) must be 64 characters or less.");
			results.addError(fe);
		}
		
		if(!NumberUtils.isDigits(room.getRoomExtNumber())) {
			FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", MessageFormat
					.format(ms.getMessage("import.invalid.ext.number", null, "", loc), 
							HtmlUtils.htmlEscape(room.getRoomExtNumber())));
			results.addError(fe);
		}
		
		int extExists = RoomUtils.checkIfPrefixExistsInScheduleRoom(system, this.service.getTenantPrefix() +room.getRoomExtNumber());
        if (extExists > 0) {
        	FieldError fe = new FieldError("roomExtNumber", "roomExtNumber", "Extension matches Schedule Room prefix. Please choose a different extension and try again.");
			results.addError(fe);
        }
        if (room.getRoomName().length() > 80 ) {
            FieldError fe = new FieldError("AddMember", "roomName", MessageFormat.format(ms.getMessage("invalid.roomname.length", null, "", loc), room.getRoomName()));
            results.addError(fe);
        }

        if (!room.getRoomName().matches(ValidationUtils.USERNAME_REGEX)) {
            FieldError fe = new FieldError("AddMember", "roomName", 
            		MessageFormat.format(ms.getMessage("invalid.roomname.match", null, "", loc)
            				.replace("&quot;", "\""), HtmlUtils.htmlEscape(room.getRoomName())));
            results.addError(fe);
        }

        // Return if there are validation errors
		if (results.hasErrors()) {
			List fields = results.getFieldErrors();
			model.put("success", Boolean.FALSE);
			model.put("fields", fields);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		if (room.getPinSetting() != null) {
			if (room.getPinSetting().equalsIgnoreCase("enter")) {
				if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), room.getRoomPIN())) {
					String[] args = {""+system.getMinPINLengthForTenant(TenantContext.getTenantId()), ""+SystemServiceImpl.PIN_MAX};
					FieldError fe = new FieldError("UpdateRoom", "UpdateRoom", ms.getMessage("room.pin.must.be.3.10.digits", args, "", loc));
					results.addError(fe);
					model.put("success", Boolean.FALSE);
					model.put("fields", results.getFieldErrors());
					return new ModelAndView("ajax/result_ajax", "model", model);
				}
			}
		}

		if (room.getModeratorPinSetting() != null) {
			if (room.getModeratorPinSetting().equalsIgnoreCase("enter")) {
				if (!system.isPINLengthAcceptable(TenantContext.getTenantId(), room.getRoomModeratorPIN())) {
					String[] args = {""+system.getMinPINLengthForTenant(TenantContext.getTenantId()), ""+SystemServiceImpl.PIN_MAX};
					FieldError fe = new FieldError("UpdateRoom", "UpdateRoom", ms.getMessage("moderator.pin.must.be.3.10.digits", args, "", loc));results.addError(fe);
					model.put("success", Boolean.FALSE);
					model.put("fields", results.getFieldErrors());
					return new ModelAndView("ajax/result_ajax", "model", model);
				}
			}
		}
		// Validate the GroupId, if the groupId doesn't exist for Tenant, replace it with default group
		Group group = null;
		try {
			group = groupService.getGroup(room.getGroupID());
		} catch (Exception e) {
			logger.error("Invalid GroupId {}", room.getGroupID());
		}
		
		if(group == null || (group.getTenantID() != TenantContext.getTenantId())) {
			Group defaultGroup = groupService.getDefaultGroup();
			room.setGroupID(defaultGroup.getGroupID());
		}

		if (roomID == 0) {
			room.setRoomExtNumber(this.service.getTenantPrefix() + room.getRoomExtNumber());
			room.setRoomMuted(0);
			roomID = this.service.insertRoom(room);
			model.put("success", roomID > 0 ? Boolean.TRUE : Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int tenantId = TenantContext.getTenantId();

		Room existingRoom = null;
		try {
			existingRoom = this.service.getRoom(roomID);
		} catch (Exception e) {
			logger.error("Trying to access non-existent room -{}", roomID);
		}

		if (room == null || tenantId != existingRoom.getTenantID()) {
            FieldError fe = new FieldError("UpdateRoom", "UpdateRoom", MessageFormat.format(ms.getMessage("invalid.room", null, "", loc), room.getRoomName()));
            results.addError(fe);
            model.put("success", Boolean.FALSE);
            model.put("fields", results.getFieldErrors());
            logger.error("Trying to update unauthorized room -{}", roomID);
            return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int updateCount = 0;
		
		// If room key is present and the key is not alphanumeric, return error
        if (StringUtils.isNotBlank(room.getRoomKey()) && !StringUtils.isAlphanumeric(room.getRoomKey())) {
            FieldError fe = new FieldError("UpdateRoom", "UpdateRoom", MessageFormat.format(ms.getMessage("invalid.room", null, "", loc), room.getRoomName()));
            results.addError(fe);
            model.put("success", Boolean.FALSE);
            model.put("fields", results.getFieldErrors());
            logger.error("Trying to update room with invalid room key {}", room.getRoomKey());
            return new ModelAndView("ajax/result_ajax", "model", model);          
        }
		room.setRoomExtNumber(this.service.getTenantPrefix() + room.getRoomExtNumber());
		
		updateCount = this.service.updateRoom(roomID, room);
		model.put("success", updateCount > 0 ? Boolean.TRUE : Boolean.FALSE);

		return new ModelAndView("ajax/result_ajax", "model", model);

    }

	@RequestMapping(value = "/deleteroom.ajax", method = RequestMethod.POST)
	public ModelAndView deleteRoom(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		Locale loc = LocaleContextHolder.getLocale();

		int roomId = ServletRequestUtils.getIntParameter(request, "roomID", 0);
		Room room = null;
		try {
			room = this.service.getRoom(roomId);
		} catch (Exception e) {
			logger.error("Trying to delete non-existent Room -{}", roomId);
		}

		int tenantId = TenantContext.getTenantId();

		// Cross Tenant Check
		if (room == null || room.getTenantID() != tenantId) {
			FieldError fe = new FieldError("DeleteRoom", "DeleteRoom", MessageFormat.format(
					ms.getMessage("invalid.room", null, "", loc), ""));
			List<FieldError> errors = new ArrayList<FieldError>();
			errors.add(fe);
			model.put("success", Boolean.FALSE);
			model.put("fields", errors);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int deleteCount = this.service.deleteRoom(roomId);

		if (deleteCount > 0) {
			model.put("success", Boolean.TRUE);
		} else {
			model.put("success", Boolean.FALSE);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/roomcontrol.html", method = RequestMethod.GET)
    public String getAdminControlHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {

	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("nav", "rooms");

	    int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);
        Room room = null;
		try {
			room = this.service.getRoom(roomID);
		} catch (Exception e1) {
			logger.error("Admin Control Meeting - Trying to access non-existent room - {}", roomID);
			return "redirect:login.html";
		}

		int tenantId = TenantContext.getTenantId();

		// Cross tenant check
		if(room == null || room.getTenantID() != tenantId) {
			logger.error("Admin Control Meeting - Trying to access Unauthorized room - {}", roomID);
			return "redirect:login.html";
		}

		String moderatorKey = room.getRoomModeratorKey();
		if (moderatorKey == null) {
			service.generateModeratorKey(room);
			moderatorKey = room.getRoomModeratorKey();
		}
		StringBuilder path = new StringBuilder("redirect:");

		Tenant tenant = tenantService.getTenant(room.getTenantID());

		path.append(request.getScheme()).append("://").append(tenant.getTenantURL());

		path.append("/controlmeeting.html?key=");
		path.append(moderatorKey);

		// bak
		User userAdmin = user.getLoginUser();
		AccessKeyResponse accessKeyResponse = user.getOnetimeAccessUri(userAdmin.getTenantID(), userAdmin.getMemberID(), MemberBAKType.MorderatorURL);

		if (accessKeyResponse.getStatus() == AccessKeyResponse.INVALID_TENANT) {
			logger.error("Admin Control Meeting  - Invalid tenant");
			return "redirect:login.html";
		}

		if (accessKeyResponse.getStatus() != AccessKeyResponse.SUCCESS) {
			logger.error("Admin Control Meeting  - Internal Server Error");
			return "redirect:login.html";
		}

		// add restricted room check if the tenant belongs to the same group as the room belongs
		// get the groups the tenant belongs to
		// get the user groups the room belongs to
		// check if there is a common group between the room and the tenant

		Member member = memberRepository.findByMemberIDAndTenantID(userAdmin.getMemberID(), userAdmin.getTenantID());

		Room roomWithUserGroups = roomRepository.findOne(room.getRoomID());

		if (CollectionUtils.isNotEmpty(roomWithUserGroups.getUserGroups())) {

			Set<Integer> userGroupsAttachedToRoom = new HashSet<>();
			for (UserGroup userGroup : roomWithUserGroups.getUserGroups()) {
				userGroupsAttachedToRoom.add(userGroup.getUserGroupId());
			}

			Set<Integer> userGroupsAttachedToMember = new HashSet<>();
			for (UserGroup userGroup : member.getUserGroups()) {
				userGroupsAttachedToMember.add(userGroup.getUserGroupId());
			}
			Collection commonUserGroups = CollectionUtils.intersection(userGroupsAttachedToRoom,
					userGroupsAttachedToMember);
			if (CollectionUtils.isEmpty(commonUserGroups)) {
				logger.error(String.format("Member, %d, and Room, %d, have no user groups in common", member.getMemberID(),
						room.getRoomID()));

				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
		}
		path.append("&bak=").append(URLEncoder.encode(accessKeyResponse.getAccessKey(), "UTF-8"));
		path.append("&lang=").append(lr.resolveLocale(request).toString());

		return path.toString();
    }

	@RequestMapping(value = "/roomcontrol.ajax", method = RequestMethod.GET)
	public ModelAndView getAdminControlAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);

		ControlFilter filter = new ControlFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			filter = null;
		}

		Room room = null;
		try {
			room = this.service.getRoom(roomID);
		} catch (Exception e) {
			logger.error("Control Meeting - Trying to access non-existent room - {}", roomID);
		}

		int tenantId = TenantContext.getTenantId();

		// Cross tenant check
		if (room == null || room.getTenantID() != tenantId) {
			logger.error("Control Meeting - Trying to access Unauthorized room - {}", roomID);
			return new ModelAndView("ajax/control_ajax", "model", model);
		}

		model.put("roomStatus", room.getRoomStatus());

		Long num = this.conference.getCountControl(roomID);
		List<Control> list = this.conference.getControl(roomID, filter);
		List<Control> removed = new ArrayList<Control>();
		for (Control control : list) {
			control.setName(control.getName());

			if (control.getEndpointType().equalsIgnoreCase("R")) {
				removed.add(control);
				num--;
				model.put("recorderID", control.getEndpointID());
				model.put("recorderName", control.getName());
				model.put("isPaused", control.getVideo() == 0 ? "true" : "false");
				model.put("isWebcast", control.getWebcast() == 1 ? "true" : "false");
			}
		}
		for (Control control : removed) {
			list.remove(control);
		}

		model.put("num", num);
		model.put("list", list);

		return new ModelAndView("ajax/control_ajax", "model", model);
	}

	@RequestMapping(value = "/roomdetails.ajax", method = RequestMethod.GET)
    public ModelAndView getAdminRoomDetailsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    Map<String, Object> model = new HashMap<String, Object>();

        int roomID = ServletRequestUtils.getIntParameter(request, "roomID", 0);

		Room room = null;
		try {
			room = this.service.getRoom(roomID);
		} catch (Exception e) {
			logger.error("Control Meeting - Trying to access non-existent room - {}", roomID);
		}

		int tenantId = TenantContext.getTenantId();

		// Cross tenant check
		if (room == null || room.getTenantID() != tenantId) {
			logger.error("Control Meeting - Trying to access Unauthorized room - {}", roomID);
			// Forward to rooms page
			return new ModelAndView("ajax/rooms_html", "model", model);
		}

        if (room.getRoomKey() != null && !room.getRoomKey().equalsIgnoreCase("")) {
            String joinURL = service.getRoomURL(system, request.getScheme(), request.getHeader("host"), room.getRoomKey());
			room.setRoomURL(joinURL);
            if(tenantService.isTenantNotAllowingGuests()){
        		room.setRoomURL(room.getRoomURL() + "&noguest");
        	}
        }

        if (room.getAllowRecording() == 1) {
			try {
				this.replay.getWebCastURLandPIN(room);
			} catch (RemoteException e) {
				logger.error("Exception while invoking ReplayService", e);
			}
        }

        model.put("room", room);
        //TODO - Why Member and Group are put in model? they are never used

        /*Member member = this.member.getMember(room.getMemberID());
        model.put("member", member);
        Group group = this.group.getGroup(room.getGroupID());
        model.put("group", group);*/

        return new ModelAndView("ajax/room_details_ajax", "model", model);
    }

	@RequestMapping(value = "/checkroomname.ajax", method = RequestMethod.POST)
    public ModelAndView checkRoomNameAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
    	String roomName = ServletRequestUtils.getStringParameter(request, "roomname", null);

		if(roomName == null) {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		boolean roomNameExists = service.isRoomExistForRoomName(roomName, 0);
		model.put("success", roomNameExists);

		return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/checkdisplayname.ajax", method = RequestMethod.POST)
    public ModelAndView checkDisplayNameAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
    	String displayName = ServletRequestUtils.getStringParameter(request, "displayName", null);
    	int roomId = ServletRequestUtils.getIntParameter(request, "roomId", 0);

		if(displayName == null) {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int displayNameCount = service.getDisplayNameCount(displayName, roomId, TenantContext.getTenantId());
		if (displayNameCount > 0){
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		model.put("success", Boolean.TRUE);
		return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/checkroomextn.ajax", method = RequestMethod.POST)
    public ModelAndView checkRoomExtAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> model = new HashMap<String, Object>();

		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		String tenantPrefix = StringUtils.trimToEmpty(tenant.getTenantPrefix());
		String roomExt = tenantPrefix + ServletRequestUtils.getStringParameter(request, "roomext", null);

		if(roomExt == null) {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		boolean extExists = service.isRoomExistForRoomExtNumber(roomExt, 0);
		model.put("success", extExists);

		return new ModelAndView("ajax/result_ajax", "model", model);
    }

	@RequestMapping(value = "/vrooms.html", method = RequestMethod.GET)
	public ModelAndView getVidyoRoomsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("nav", "vrooms");

		this.user.setLoginUser(model, response);

		String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
		model.put("guideLoc", guideLoc);

		return new ModelAndView("admin/vidyorooms_html", "model", model);
	}

	@RequestMapping(value = "/vrooms.ajax", method = RequestMethod.GET)
	public ModelAndView getVidyoRoomsAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		EndpointFilter endpointFilter = new EndpointFilter();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(endpointFilter);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();
		if (results.hasErrors()) {
			endpointFilter = null;
		}
		if(endpointFilter != null) {
			final String jsonSort = endpointFilter.getSort();
	        if(jsonSort != null) {
	            try {
					final ObjectNode[] nodes = new ObjectMapper().readValue(jsonSort, ObjectNode[].class);
					for(ObjectNode node : nodes) {
						if(node.get("property").asText().equals("memberName") || node.get("property").asText().equals("ipAddress") || node.get("property").asText().equals("status")) {
							endpointFilter.setSort(node.get("property").asText());
						}
						if(node.get("direction") != null){
							endpointFilter.setDir(node.get("direction").asText());
						}
					}
				} catch (Exception e) {
					logger.warn("Not a valid JSON format for Endpoint Filter - VidyoRooms page - possible SQL Injection");
					endpointFilter.setSort("memberName");
					endpointFilter.setDir("ASC");
				}
	        }
		}
		List<Endpoint> vidyoRoomEndpoints = endpointService.getVidyoRooms(endpointFilter);
		if (vidyoRoomEndpoints != null && !vidyoRoomEndpoints.isEmpty()) {
			for (Endpoint endpoint : vidyoRoomEndpoints) {
				endpoint.setMemberName(endpoint.getMemberName());
			}
		}

		int total = endpointService.getVidyoRoomsCount(endpointFilter);

		model.put("num", total);
		model.put("vrooms", vidyoRoomEndpoints);

		return new ModelAndView("ajax/vidyorooms_ajax", "model", model);
	}

	@RequestMapping(value = "/getroomidbyext.ajax", method = RequestMethod.POST)
	public ModelAndView getRoomIdByExtAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		String roomExt = ServletRequestUtils.getStringParameter(request, "roomext", null);

		String pin = ServletRequestUtils.getStringParameter(request, "pin", null);

		if(roomExt == null) {
			model.put("roomId", 0);
			return new ModelAndView("ajax/room_id_ajax", "model", model);
		}

		// Cross tenant check
		int tenantId = 0;
		String loginUserRole = user.getLoginUserRole();
		if(!loginUserRole.equalsIgnoreCase("ROLE_SUPER")) {
			tenantId = TenantContext.getTenantId();
		}

		int roomId = service.getRoomIdByExt(tenantId, roomExt, pin);

		model.put("roomId", roomId);

		return new ModelAndView("ajax/room_id_ajax", "model", model);
	}

}
