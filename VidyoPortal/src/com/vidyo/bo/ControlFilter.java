package com.vidyo.bo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vidyo.framework.dao.BaseQueryFilter;

public class ControlFilter extends BaseQueryFilter{
	
	/**
	 * 
	 */
	public static List<String> sortList = new ArrayList<String>();

	/**
	 * 
	 */
	static {
		sortList.add("name");
		sortList.add("conferenceName");
		sortList.add("ext");
		sortList = Collections.unmodifiableList(sortList);
	}
	
	/**
	 * 
	 */
	public static List<String> controlFilterDirs = new ArrayList<String>();

	/**
	 * 
	 */
	static {
		controlFilterDirs.add("ASC");
		controlFilterDirs.add("DESC");
		controlFilterDirs = Collections.unmodifiableList(controlFilterDirs);
	}

	
    String name = "";
    String ext = "";

    String sort = "name";

    int limit = 25;
    int start = 0;
    
    public Group group = new Group();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }
    
    
	public String getProperty() {
		return this.group.getProperty();
	}
	public void setProperty(String property) {
		this.group.setProperty(property);
	}
	public String getDirection() {
		return this.group.getDirection();
	}
	public void setDirection(String direction) {
		this.group.setDirection(direction);
	} 

	class Group {
    	String property;
    	String direction;
		public String getProperty() {
			return property;
		}
		public void setProperty(String property) {
			this.property = property;
		}
		public String getDirection() {
			return direction;
		}
		public void setDirection(String direction) {
			this.direction = direction;
		}
    	
    	
    }
}