package com.vidyo.service;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vidyo.bo.Entity;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.recordings.webcast.CreateWebcastURLRequest;
import com.vidyo.recordings.webcast.CreateWebcastURLResponse;
import com.vidyo.recordings.webcast.DeleteWebcastURLRequest;
import com.vidyo.recordings.webcast.DeleteWebcastURLResponse;
import com.vidyo.recordings.webcast.GeneralFaultException;
import com.vidyo.recordings.webcast.GetWebcastURLRequest;
import com.vidyo.recordings.webcast.GetWebcastURLResponse;
import com.vidyo.recordings.webcast.InvalidArgumentFaultException;
import com.vidyo.recordings.webcast.RecordingWebcastServiceStub;
import com.vidyo.recordings.webcast.UpdateWebcastPINRequest;
import com.vidyo.recordings.webcast.UpdateWebcastPINResponse;
import com.vidyo.service.exceptions.NoVidyoReplayException;

@Service
public class ReplayServiceImpl implements IReplayService {

    /** Logger for this class and subclasses */
    private final Logger logger = LoggerFactory.getLogger(ReplayServiceImpl.class.getName());

    @Autowired
    private ITenantService tenantService;

    @Autowired
    private ISystemService system;

    @Autowired
    private IRoomService roomService;

    public void getWebCastURLandPIN(Entity entity)
            throws GeneralFaultException, InvalidArgumentFaultException, RemoteException
    {
        RecordingWebcastServiceStub replay = null;

        try {
            replay = this.system.getRecordingWebcastServiceStubWithAUTH(entity.getTenantID());

            GetWebcastURLRequest req = new GetWebcastURLRequest();
            req.setUserName(entity.getUsername());
            req.setTenant(entity.getTenantName());
            logger.info("room.getRoomType()->"+ entity.getRoomType() + "memberid->" + entity.getMemberID() + "pin"+ entity.getRoomPIN());

            String roomName = entity.getRoomName();
            if (entity.getRoomType().equalsIgnoreCase("Scheduled")) {
                // Override the room name/extn/displayname with ScheduledRoom extension
            	entity = roomService.overrideScheduledRoomProperties(entity);
                roomName = entity.getRoomName();
                if(StringUtils.isNotBlank(entity.getRoomPIN())) {
                	roomName += entity.getRoomPIN();
                }
            }
            req.setRoomName(roomName);
            logger.info("Room name ->" + roomName);
            GetWebcastURLResponse resp = replay.getWebcastURL(req);
            String webCastURL = resp.getUrl();
            String webCastPIN = resp.getPin();

            entity.setWebCastURL(webCastURL);
            entity.setWebCastPIN(webCastPIN);
            if (webCastPIN.equalsIgnoreCase("")) {
                entity.setWebCastPinned(0);
            } else {
                entity.setWebCastPinned(1);
            }
            entity.setAllowWebcasting(1);
        } catch (NoVidyoReplayException e) {
	        logger.error(e.getMessage());
	        throw new RemoteException();
	    } catch (Exception e) {
            logger.error(e.getMessage());
            entity.setAllowWebcasting(0);
	        if (e instanceof AxisFault) {
		        AxisFault fault = (AxisFault) e;
		        if (fault.toString().contains("401") || fault.toString().contains("Unauthorized")) {
			        throw new RemoteException();
		        }
		        if (fault.toString().contains("302") || fault.toString().contains("Transport")) {
			        throw new RemoteException();
		        }
	        }
        } finally {
            if (replay != null) {
                try {
                    replay.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void getWebCastURLandPIN(Room room)
            throws GeneralFaultException, InvalidArgumentFaultException, RemoteException
    {
        RecordingWebcastServiceStub replay = null;

        try {
            replay = this.system.getRecordingWebcastServiceStubWithAUTH(room.getTenantID());

            GetWebcastURLRequest req = new GetWebcastURLRequest();
            req.setUserName(room.getOwnerName());
            req.setTenant(room.getTenantName());

            logger.info("room.getRoomType()->"+ room.getRoomType() + "memberid->" + room.getMemberID() + "pin"+ room.getRoomPIN());

            String roomName = room.getRoomName();
            if (room.getRoomType().equalsIgnoreCase("Scheduled")) {
                // Override the room name/extn/displayname with ScheduledRoom extension
                room = roomService.overrideScheduledRoomProperties(room);
                roomName = room.getRoomName();
                if(StringUtils.isNotBlank(room.getRoomPIN())) {
                	roomName += room.getRoomPIN();
                }
            }

            req.setRoomName(roomName);
            logger.info("Room name ->" + roomName);

            GetWebcastURLResponse resp = replay.getWebcastURL(req);
            String webCastURL = resp.getUrl();
            String webCastPIN = resp.getPin();

            room.setWebCastURL(webCastURL);
            room.setWebCastPIN(webCastPIN);
            if (webCastPIN.equalsIgnoreCase("")) {
                room.setWebCastPinned(0);
            } else {
                room.setWebCastPinned(1);
            }
            room.setAllowWebcasting(1);
        } catch (Exception e) {
			logger.error(e.getMessage());
			room.setAllowWebcasting(0);
			if (e instanceof AxisFault) {
				AxisFault fault = (AxisFault) e;
				if (fault.toString().contains("401") || fault.toString().contains("Unauthorized")) {
					room.setReplayErrorCode(401);
				}
				if (fault.toString().contains("302") || fault.toString().contains("Transport")) {
					throw new RemoteException();
				}
			}
        } finally {
            if (replay != null) {
                try {
                    replay.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void generateWebCastURL(Room room)
            throws RemoteException, NoVidyoReplayException, GeneralFaultException, InvalidArgumentFaultException
    {
        RecordingWebcastServiceStub replay = null;
        try {
            replay = this.system.getRecordingWebcastServiceStubWithAUTH(room.getTenantID());

            CreateWebcastURLRequest req = new CreateWebcastURLRequest();
            req.setUserName(room.getOwnerName());
            req.setTenant(room.getTenantName());
            logger.info("room.getRoomType()->"+ room.getRoomType() + "memberid->" + room.getMemberID() + "pin"+ room.getRoomPIN());

            String roomName = room.getRoomName();
            if (room.getRoomType().equalsIgnoreCase("Scheduled")) {
                // Override the room name/extn/displayname with ScheduledRoom extension
                room = roomService.overrideScheduledRoomProperties(room);
                roomName = room.getRoomName();
                if(StringUtils.isNotBlank(room.getRoomPIN())) {
                	roomName += room.getRoomPIN();
                }
            }
            logger.info("Room name ->" + roomName);
            req.setRoomName(roomName);
            CreateWebcastURLResponse resp = replay.createWebcastURL(req);
            String stOK = resp.getStatus().getValue();
        } finally {
            if (replay != null) {
                try {
                    replay.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void deleteWebCastURL(Room room)
            throws RemoteException, NoVidyoReplayException, GeneralFaultException, InvalidArgumentFaultException
    {
        RecordingWebcastServiceStub replay = null;
        try {
            replay = this.system.getRecordingWebcastServiceStubWithAUTH(room.getTenantID());

            DeleteWebcastURLRequest req = new DeleteWebcastURLRequest();
            req.setUserName(room.getOwnerName());
            req.setTenant(room.getTenantName());
            logger.info("room.getRoomType()->"+ room.getRoomType() + "memberid->" + room.getMemberID() + "pin"+ room.getRoomPIN());

            String roomName = room.getRoomName();
            if (room.getRoomType().equalsIgnoreCase("Scheduled")) {
                // Override the room name/extn/displayname with ScheduledRoom extension
                room = roomService.overrideScheduledRoomProperties(room);
                roomName = room.getRoomName();
                if(StringUtils.isNotBlank(room.getRoomPIN())) {
                	roomName += room.getRoomPIN();
                }
            }

            req.setRoomName(roomName);
            logger.info("Room name ->" + roomName);
            DeleteWebcastURLResponse resp = replay.deleteWebcastURL(req);
            String stOK = resp.getStatus().getValue();
        } finally {
            if (replay != null) {
                try {
                    replay.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

    public void updateWebCastPIN(Room room, String pin)
            throws RemoteException, NoVidyoReplayException, GeneralFaultException, InvalidArgumentFaultException
    {
        Tenant userTenant = this.tenantService.getTenant(TenantContext.getTenantId());
        RecordingWebcastServiceStub replay = null;
        try {

            replay = this.system.getRecordingWebcastServiceStubWithAUTH(room.getTenantID());

            UpdateWebcastPINRequest req = new UpdateWebcastPINRequest();
            req.setUserName(room.getOwnerName());
            req.setTenant(room.getTenantName());
            logger.info("room.getRoomType()->"+ room.getRoomType() + "memberid->" + room.getMemberID() + "pin"+ room.getRoomPIN());

            String roomName = room.getRoomName();
            if (room.getRoomType().equalsIgnoreCase("Scheduled")) {
                // Override the room name/extn/displayname with ScheduledRoom extension
                room = roomService.overrideScheduledRoomProperties(room);
                roomName = room.getRoomName();
                if(StringUtils.isNotBlank(room.getRoomPIN())) {
                	roomName += room.getRoomPIN();
                }
            }
            logger.info("Room name ->" + roomName);

            req.setRoomName(roomName);
            req.setPin(pin);

            UpdateWebcastPINResponse resp = replay.updateWebcastPIN(req);
            String stOK = resp.getStatus().getValue();
        } finally {
            if (replay != null) {
                try {
                    replay.cleanup();
                } catch (AxisFault af) {
                    // ignore
                }
            }
        }
    }

	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}
}