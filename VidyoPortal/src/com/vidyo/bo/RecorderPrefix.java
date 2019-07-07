package com.vidyo.bo;

import java.io.Serializable;

public class RecorderPrefix implements Serializable {

	String prefix;
	String description;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
