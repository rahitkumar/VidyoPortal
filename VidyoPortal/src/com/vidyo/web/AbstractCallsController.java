package com.vidyo.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.service.ISystemService;

public abstract class AbstractCallsController {

    protected final ISystemService system;

    public AbstractCallsController(ISystemService system) {
        this.system = system;
    }

    @RequestMapping(value = "/currentcalls.ajax", method = RequestMethod.GET)
    public ModelAndView getSuperCurrentCallsAjax(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ControlFilter filter = new ControlFilter();
        ServletRequestDataBinder binder = new ServletRequestDataBinder(filter);
        binder.bind(request);
        BindingResult results = binder.getBindingResult();
        if (results.hasErrors()) {
            filter = null;
        }

        Map<String, Object> model = new HashMap<String, Object>();
        Long num = getCurrentCallsCount();
        model.put("num", num);
        List<Control> list = getCurrentCalls(filter);
        model.put("list", list);

        return new ModelAndView("ajax/current_calls_ajax", "model", model);
    }

    protected abstract Long getCurrentCallsCount();

    protected abstract List<Control> getCurrentCalls(ControlFilter filter);
}
