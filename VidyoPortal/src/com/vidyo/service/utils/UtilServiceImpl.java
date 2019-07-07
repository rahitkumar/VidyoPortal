package com.vidyo.service.utils;

import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.service.IConferenceService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IUserService;

public class UtilServiceImpl implements UtilService {

	private IConferenceService conferenceService;
	private IMemberService memberService;
	private IUserService userService;
	
	public void setConferenceService(IConferenceService conferenceService) {
		this.conferenceService = conferenceService;
	}

	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean canMemberControlRoom(int memberID, Room checkingRoom, String moderatorPIN) {
		boolean rc = false;
		// step 1 - user is an owner of room
		if (checkingRoom.getMemberID() == memberID) {
			rc = true;
		} else {
			Member curMember = memberService.getMember(memberID);
			int roomTenantID = checkingRoom.getTenantID();
			
			// step 2 - user has an admin or operator role and has the same tenant as checking room
			String userRole = userService.getLoginUserRole();
			if ((userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_OPERATOR")) && 
					curMember != null && roomTenantID == curMember.getTenantID()) {
				rc = true;
			} else {
				// step 3 - user knows moderator PIN and he is a participant
				String roomModeratorPIN = checkingRoom.getRoomModeratorPIN() != null ? checkingRoom.getRoomModeratorPIN(): "";
				if ("".equals(roomModeratorPIN)) {
                    rc = false;
                } else if (roomModeratorPIN.equalsIgnoreCase(moderatorPIN)) {
					rc = true;
				}
			}
		}
		return rc;
	}

	public boolean canMemberControlRoom(int memberID, int memberTenantID, Room checkingRoom, String moderatorPIN) {
		boolean rc = false;
		// step 1 - user is an owner of room
		if (checkingRoom.getMemberID() == memberID) {
			rc = true;
		} else {
			int roomTenantID = checkingRoom.getTenantID();

			// step 2 - user has an admin or operator role and has the same tenant as checking room
			String userRole = userService.getLoginUserRole();
			if ((userRole.equalsIgnoreCase("ROLE_SUPER") || userRole.equalsIgnoreCase("ROLE_ADMIN") || userRole.equalsIgnoreCase("ROLE_OPERATOR")) &&
					roomTenantID == memberTenantID) {
				rc = true;
			} else {
				// step 3 - user knows moderator PIN and he is a participant
				String roomModeratorPIN = checkingRoom.getRoomModeratorPIN() != null ? checkingRoom.getRoomModeratorPIN(): "";
				if ("".equals(roomModeratorPIN)) {
					rc = false;
				} else if (roomModeratorPIN.equalsIgnoreCase(moderatorPIN)) {
					rc = true;
				}
			}
		}
		return rc;
	}

}
