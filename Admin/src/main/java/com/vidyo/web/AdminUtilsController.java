package com.vidyo.web;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AdminUtilsController extends UtilController {

    @Override
    protected String getViewName() {
        return "admin";
    }

    @Override
    protected void complementHomeModel(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Map<String, Object> model) throws Exception {
        this.user.setLoginUser(model, response);
        model.put("userRole", user.getLoginUserRole());
        String guideLoc = system.getGuideLocation(lr.resolveLocale(request).toString(), "admin");
        model.put("guideLoc", guideLoc);
    }

    @Override
    protected String languagePropertyName() {
        return "VidyoPortalAdminLanguage";
    }
}
