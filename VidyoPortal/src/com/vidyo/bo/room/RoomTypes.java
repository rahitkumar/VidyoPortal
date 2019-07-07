/**
 * 
 */
package com.vidyo.bo.room;

/**
 * @author ganesh
 * 
 */
public enum RoomTypes {

	PERSONAL(1, "Personal"), PUBLIC(2, "Public"), LEGACY(3, "Legacy"), SCHEDULED(
			4, "Scheduled");

	private int id;

	private String type;

	private RoomTypes(int id, String type) {
		this.id = id;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}
}
