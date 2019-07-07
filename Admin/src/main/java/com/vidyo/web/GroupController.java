package com.vidyo.web;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vidyo.framework.context.TenantContext;

import org.apache.commons.lang.StringUtils;
import org.owasp.esapi.ESAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import com.vidyo.bo.Group;
import com.vidyo.bo.GroupFilter;
import com.vidyo.bo.Router;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IRouterService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import com.vidyo.service.group.request.DeleteGroupRequest;
import com.vidyo.service.group.response.UpdateGroupResponse;
import com.vidyo.utils.ValidationUtils;

@Controller
public class GroupController {

	@Autowired
    private IGroupService service;

	@Autowired
    private IUserService user;

	@Autowired
	private IRouterService router;

	@Autowired
	private ISystemService system;

	@Autowired
	private CookieLocaleResolver lr;

	@Autowired
	private ReloadableResourceBundleMessageSource ms;

	@RequestMapping(value = "/groups.html", method = RequestMethod.GET)
    public ModelAndView getGroupsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "groups");

        this.user.setLoginUser(model, response);

	    String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
	    model.put("guideLoc", guideLoc);

        try {
            String logopath = this.system.getCustomizedLogoName();
            if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
                model.put("customizedLogoPath", logopath);
            }
        } catch (Exception ignored) {}

        return new ModelAndView("admin/groups_html", "model", model);
    }

	@RequestMapping(value = "/groups.ajax", method = RequestMethod.GET)
    public ModelAndView getGroupsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    Map<String, Object> model = new HashMap<String, Object>();

        GroupFilter filter = new GroupFilter();
        filter.setLimit(5000);  // LIMIT is hard coded to 25 in BaseQueryFilter.getLimit(), so initializing to high number to get all groups.
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }
        
        // JSON format for filtering - new UI 
		if(filter != null) {
	        final String jsonFilter = filter.getFilter();
	        if(jsonFilter != null && jsonFilter.contains("property")) {
	            final ObjectNode[] nodes = new ObjectMapper().readValue(jsonFilter, ObjectNode[].class);
	            for(ObjectNode node : nodes) {
	            	if(node.get("property").asText().equals("groupName") && !StringUtils.isEmpty(node.get("value").asText())) {
	            		filter.setGroupName(node.get("value").asText());
	            	}
	            }
	        }
		}
		
        Long num = this.service.getCountGroups(filter);
        model.put("num", num);
        List<Group> list = this.service.getGroups(filter);

        for (Group group: list) {
            group.setGroupName(group.getGroupName());
        }

        model.put("list", list);

        return new ModelAndView("ajax/groups_ajax", "model", model);
    }

	@RequestMapping(value = "/group.html", method = RequestMethod.GET)
    public ModelAndView getGroupHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    Map<String, Object> model = new HashMap<String, Object>();
	    model.put("nav", "groups");

	    this.user.setLoginUser(model, response);

        int groupID = ServletRequestUtils.getIntParameter(request, "groupID", 0);

	    String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
	    model.put("guideLoc", guideLoc);
	    
	    int tenantId = TenantContext.getTenantId();

        try {
            String logopath = this.system.getCustomizedLogoName();
            if (new File(request.getServletContext().getRealPath(logopath)).exists()) {
                model.put("customizedLogoPath", logopath);
            }
        } catch (Exception ignored) {}

	    model.put("groupID", groupID);

        if (groupID != 0) {
            Group group;
            try {
            	group = this.service.getGroup(groupID);	
            } catch(Exception e) {
	            return new ModelAndView("admin/groups_html", "model", model);
            }
            
	        // Cross tenant check
            if (group == null || group.getTenantID() != tenantId) {
            	return new ModelAndView("admin/groups_html", "model", model);
            }

            model.put("groupName", ESAPI.encoder().encodeForJavaScript(group.getGroupName()));
        } else {
            model.put("groupName", "");
        }

        if (this.service.hasReplayComponent()) {
            model.put("hasReplay", "1");
        } else {
            model.put("hasReplay", "0");
        }

        return new ModelAndView("admin/group_html", "model", model);
    }

	@RequestMapping(value = "/group.ajax", method = RequestMethod.GET)
    public ModelAndView getGroupAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    Map<String, Object> model = new HashMap<String, Object>();

	    int groupID = ServletRequestUtils.getIntParameter(request, "groupID", 0);
        model.put("groupID", groupID);
        
        int tenantId = TenantContext.getTenantId();

        Group group = null;
        
        if (groupID == 0) {
            group = new Group();
			group.setGroupName("");
        } else {
            group = this.service.getGroup(groupID);
	        if (tenantId != group.getTenantID()) {
		        group = new Group();
		        group.setGroupName("");
	        } else {
	            group.setGroupName(group.getGroupName());
				group.setGroupDescription(group.getGroupDescription());
	        }
        }
        if (this.service.hasReplayComponent()) {
            model.put("hasReplay", "1");
        } else {
            model.put("hasReplay", "0");
        }
        model.put("group", group);
        model.put("groupName", group.getGroupName());

        return new ModelAndView("ajax/group_ajax", "model", model);
    }

	@RequestMapping(value = "/routers.ajax", method = RequestMethod.GET)
    public ModelAndView getRoutersAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        Long num = this.router.getCountRouters();
        model.put("num", num);
        List<Router> list = this.router.getRouters();
        model.put("list", list);

        return new ModelAndView("ajax/routers_ajax", "model", model);
    }

	@RequestMapping(value = "/savegroup.ajax", method = RequestMethod.POST)
	public ModelAndView saveGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		int groupID = ServletRequestUtils.getIntParameter(request, "groupID", 0);
        Locale loc = LocaleContextHolder.getLocale();

		Group group = new Group();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(group);
		binder.bind(request);
		BindingResult results = binder.getBindingResult();

		if (results.hasErrors()) {
			List fields = results.getFieldErrors();
			model.put("success", Boolean.FALSE);
			model.put("fields", fields);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}
		
		if (!StringUtils.isNotEmpty(group.getGroupName()) || !group.getGroupName().matches(ValidationUtils.GROUP_NAME_REGEX)) {
			model.put("success", Boolean.FALSE);
			FieldError fe = new FieldError("AddGroup", "name", MessageFormat.format(
					ms.getMessage("invalid.groupname.match.message", null, "", loc), ""));
			List<FieldError> errors = new ArrayList<FieldError>();
			errors.add(fe);
			model.put("fields", errors);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}
		
		if (groupID == 0) {
			group.setDefaultFlag(0);
			groupID = this.service.insertGroup(group);
			model.put("success", groupID > 0 ? Boolean.TRUE : Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int tenantId = TenantContext.getTenantId();

		Group existingGroup = this.service.getGroup(groupID);

		if (existingGroup == null || existingGroup.getTenantID() != tenantId) {
			model.put("success", Boolean.FALSE);
			FieldError fe = new FieldError("UpdateGroup", "name", MessageFormat.format(
					ms.getMessage("invalid.group", null, "", loc), ""));
			List<FieldError> errors = new ArrayList<FieldError>();
			errors.add(fe);
			model.put("fields", errors);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int updateCount = this.service.updateGroup(groupID, group);

		model.put("success", updateCount > 0 ? Boolean.TRUE : Boolean.FALSE);
		return new ModelAndView("ajax/result_ajax", "model", model);

	}

	/**
	 * Deletes a specific group of the Tenant. Deletion will happen only if
	 * there is a default group present.
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deletegroup.ajax", method = RequestMethod.POST)
	public ModelAndView deleteGroup(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		int groupId = ServletRequestUtils.getIntParameter(request, "groupID", 0);
        Locale loc = LocaleContextHolder.getLocale();

		if (groupId <= 0) {
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		int tenantId = TenantContext.getTenantId();
		
		DeleteGroupRequest deleteGroupRequest = new DeleteGroupRequest();
		deleteGroupRequest.setTenantId(tenantId);
		deleteGroupRequest.setGroupId(groupId);
		
		UpdateGroupResponse updateGroupResponse = service.deleteGroup(deleteGroupRequest);

		// Deletion failed - return model with error messages
		if (updateGroupResponse.getStatus() != UpdateGroupResponse.SUCCESS) {
			FieldError fe = new FieldError("DeleteGroup", "name", MessageFormat.format(
					ms.getMessage(updateGroupResponse.getMessage(), null, "", loc), ""));
			List<FieldError> errors = new ArrayList<FieldError>();
			errors.add(fe);
			model.put("fields", errors);
			model.put("success", Boolean.FALSE);
			return new ModelAndView("ajax/result_ajax", "model", model);
		}

		// Group deleted successfully
		model.put("success", Boolean.TRUE);

		return new ModelAndView("ajax/result_ajax", "model", model);
	}

	@RequestMapping(value = "/checkgrpname.ajax", method = RequestMethod.POST)
	public ModelAndView checkGroupNameAvailabilityAjax(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();

		String groupName = ServletRequestUtils.getStringParameter(request, "groupname", null);
		int groupId = ServletRequestUtils.getIntParameter(request, "groupId", 0);
		if (groupName == null) {
			model.put("success", Boolean.FALSE);
		} else {
			boolean groupExists = service.isGroupExistForGroupName(groupName, groupId);
			model.put("success", groupExists);
		}

		return new ModelAndView("ajax/result_ajax", "model", model);
	}
}
