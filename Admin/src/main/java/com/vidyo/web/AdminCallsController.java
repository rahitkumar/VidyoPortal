package com.vidyo.web;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.service.ISystemService;
import com.vidyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdminCallsController extends AbstractCallsController {

    private final CookieLocaleResolver lr;

    private final IUserService user;

    @Autowired
    public AdminCallsController(ISystemService system, CookieLocaleResolver lr, IUserService user) {
        super(system);
        this.lr = lr;
        this.user= user;

    }

    @RequestMapping(value = "/currentcalls.html", method = RequestMethod.GET)
    public ModelAndView getAdminCurrentCallsHtml(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<String, Object>();
        model.put("nav", "rooms");
        user.setLoginUser(model, response);
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
        return new ModelAndView("admin/current_calls_html", "model", model);
    }

    @Override
    protected Long getCurrentCallsCount() {
        return this.system.getCountCurrentTenantCalls();
    }

    @Override
    protected List<Control> getCurrentCalls(ControlFilter filter) {
        return this.system.getCurrentTenantCalls(filter);
    }
}
