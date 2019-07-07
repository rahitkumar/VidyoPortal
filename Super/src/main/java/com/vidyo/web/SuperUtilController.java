package com.vidyo.web;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class SuperUtilController extends UtilController {

    @Override
    protected String getViewName() {
        return "super";
    }

    @Override
    protected void complementHomeModel(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        // do nothing - this template is only used in admin app
    }

    @Override
    protected String languagePropertyName() {
        return "VidyoPortalSuperLanguage";
    }
}
