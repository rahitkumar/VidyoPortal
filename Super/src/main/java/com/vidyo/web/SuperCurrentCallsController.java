package com.vidyo.web;

import com.vidyo.bo.Control;
import com.vidyo.bo.ControlFilter;
import com.vidyo.service.ISystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class SuperCurrentCallsController extends AbstractCallsController {

    @Autowired
    public SuperCurrentCallsController(ISystemService system) {
        super(system);
    }

    @Override
    protected Long getCurrentCallsCount() {
        return system.getCountCurrentCalls();
    }

    @Override
    protected List<Control> getCurrentCalls(ControlFilter filter) {
        return this.system.getCurrentCalls(filter);
    }
}
