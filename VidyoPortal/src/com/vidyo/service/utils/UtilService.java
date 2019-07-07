package com.vidyo.service.utils;

import com.vidyo.bo.Room;

public interface UtilService {
	
	public boolean canMemberControlRoom(int memberID, Room checkingRoom, String moderatorPIN);
	public boolean canMemberControlRoom(int memberID, int memberTenantID, Room checkingRoom, String moderatorPIN);
}
