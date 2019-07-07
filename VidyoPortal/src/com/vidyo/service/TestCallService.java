package com.vidyo.service;

import com.vidyo.bo.Member;
import com.vidyo.model.CallDetails;
import com.vidyo.service.exceptions.ScheduledRoomCreationException;
import com.vidyo.service.exceptions.TestCallException;
import com.vidyo.service.room.ScheduledRoomResponse;

public interface TestCallService {

    enum PortConfig{
        SYSTEM,
        TENANT
    }

    public ScheduledRoomResponse createScheduledRoomForTestCall(Member member) throws ScheduledRoomCreationException;
    
    public ScheduledRoomResponse createScheduledRoomForTestCallOneAttempt(Member member, String externalRoomId) throws ScheduledRoomCreationException;

    public void testCall(CallDetails callDetails, String webRTCURL, PortConfig portConfig) throws TestCallException;
}
