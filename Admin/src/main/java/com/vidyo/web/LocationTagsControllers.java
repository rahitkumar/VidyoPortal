package com.vidyo.web;

import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LocationTagsControllers {

    private static final Logger logger = LoggerFactory.getLogger(LocationTagsControllers.class);

    private final IUserService user;
    private final ISystemService system;
    private final CookieLocaleResolver lr;

    @Autowired
    public LocationTagsControllers(IUserService user, ISystemService system, CookieLocaleResolver lr) {
        this.user = user;
        this.system = system;
        this.lr = lr;
    }

    @RequestMapping(value = "/managelocationtag.html", method = RequestMethod.GET)
    public ModelAndView getManageLocationTagHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("admin/managelocationtag_html", "model", model);
    }

    @RequestMapping(value = "/managelocationtag.ajax", method = RequestMethod.GET)
    public ModelAndView getManageLocationTagAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();

        int locationID = this.system.getManageLocationTag();

        model.put("locationID", locationID);

        return new ModelAndView("ajax/managelocationtag_ajax", "model", model);
    }

    @RequestMapping(value = "/savedefaultlocationtagid.ajax", method= RequestMethod.POST)
    public ModelAndView saveDefaultLocationTag(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int locationID = ServletRequestUtils.getIntParameter(request, "locationID", 1);

        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();
        if ( this.system.saveTenantDefaultLocationTagID(locationID) <= 0 ) {
            FieldError fe = new FieldError("SaveDefaultLocationTag", "Save Default LocationTag", "Failed to save default location tag");
            errors.add(fe);
        }
        if(errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }

    @RequestMapping(value = "/grouplocationtags.html", method = RequestMethod.GET)
    public ModelAndView getGroupLocationTagsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "settings");
        this.user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("admin/managegrouplocationtags_html", "model", model);
    }

    @RequestMapping(value = "/assignlocationtogroups.ajax", method = RequestMethod.POST)
    public ModelAndView assignLocationToGroupsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        List<FieldError> errors = new ArrayList<FieldError>();

        int locID = ServletRequestUtils.getIntParameter(request, "locationID", -1);
        String groupIdsStr = ServletRequestUtils.getStringParameter(request, "groupIDs");
        this.system.assignLocationToGroups(locID, groupIdsStr);
        if(errors.size() != 0) {
            model.put("fields", errors);
            model.put("success", Boolean.FALSE);
        } else {
            model.put("success", Boolean.TRUE);
        }
        return new ModelAndView("ajax/result_ajax", "model", model);
    }
}
