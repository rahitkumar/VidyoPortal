package com.vidyo.bo.member;

import java.util.Date;

public class MemberMini {

    private long memberID;
    private String name;
    private String email;
    private boolean enabled;
    private int status;
    private String description;
    private String type;
    private boolean inContacts;
	private String phone1;
    private String phone2;
    private String phone3;
    private String department;
    private String title;
    private String instantMessagerID;
    private Date thumbnailUpdateTime;

    public long getMemberID() {
        return memberID;
    }

    public void setMemberID(long memberID) {
        this.memberID = memberID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isInContacts() {
        return inContacts;
    }

    public void setInContacts(boolean inContacts) {
        this.inContacts = inContacts;
    }

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getInstantMessagerID() {
		return instantMessagerID;
	}

	public void setInstantMessagerID(String instantMessagerID) {
		this.instantMessagerID = instantMessagerID;
	}

	public Date getThumbnailUpdateTime() {
		return thumbnailUpdateTime;
	}

	public void setThumbnailUpdateTime(Date thumbnailUpdateTime) {
		this.thumbnailUpdateTime = thumbnailUpdateTime;
	}
    
    
}
