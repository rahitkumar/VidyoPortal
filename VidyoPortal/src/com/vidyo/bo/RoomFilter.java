package com.vidyo.bo;

import java.util.ArrayList;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class RoomFilter extends BaseQueryFilter{
	
	private static List<String> sortList = new ArrayList<String>();
	
	static {
		sortList.add("roomID");
		sortList.add("roomName");
		sortList.add("displayName");
		sortList.add("roomExtNumber");
		sortList.add("roomType");
	}
	
    private String query = "";
    private String roomName = "";
    private String ext = "";
    private String type = "All";
    private String roomStatus="All";
    private String sort = "displayName";
    private String displayName = "";

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(String roomStatus) {
		this.roomStatus = roomStatus;
	}


    public String getSort() {
    	if(sort == null || !sortList.contains(sort)) {
    		return "displayName";
    	}
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
    
    @Override
	public int getLimit() {
		if(limit <= 0) {
			return 25;
		}
		
		return limit;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

    
}
