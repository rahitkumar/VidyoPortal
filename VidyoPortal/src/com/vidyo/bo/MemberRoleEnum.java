package com.vidyo.bo;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum MemberRoleEnum {
	ADMIN			(1, "Admin"),
	OPERATOR		(2, "Operator"),
	NORMAL			(3, "Normal"),
	VIDYO_ROOM		(4, "VidyoRoom"),
	SUPER			(5, "Super"),
	LEGACY			(6, "Legacy"),
	EXECUTIVE		(7, "Executive"),
	VIDYO_PANORAMA	(8, "VidyoPanorama");
	
	private static final Map<Integer, MemberRoleEnum> lookup = new HashMap<Integer, MemberRoleEnum>();

    static {
        for(MemberRoleEnum memberRoleEnum : EnumSet.allOf(MemberRoleEnum.class))
             lookup.put(memberRoleEnum.getMemberRoleID(), memberRoleEnum);
    }

	private int memberRoleID;
	private String memberRole;
	
	private MemberRoleEnum(int memberRoleID, String memberRole) {
		this.memberRoleID = memberRoleID;
		this.memberRole = memberRole;
	}
	
	public int getMemberRoleID() {
		return memberRoleID;
	}
	
	public String getMemberRole() {
		return memberRole;
	}
	
	public static MemberRoleEnum get(int memberRoleID) {
		return lookup.get(memberRoleID);
	}

}
