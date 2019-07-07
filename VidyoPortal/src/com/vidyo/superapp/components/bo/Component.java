package com.vidyo.superapp.components.bo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "components")
public class Component implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
	@Column(name = "COMP_ID")
	@JsonSerialize(using=StringSerializer.class)
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	String compID;
	
	@Column(name = "NAME")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String name;
	
	@Column(name = "LOCAL_IP")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String localIP;
	
	@Column(name = "CLUSTER_IP")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String clusterIP;
	
	@Column(name = "MGMT_URL")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	String mgmtUrl;
	
	@Column(name = "RUNNING_VERSION")
    int runningVersion;
	
	@Column(name = "CONFIG_VERSION")
    int configVersion;
	
	@Column(name = "ALARM")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String alarm;
	
	@Column(name = "COMP_SOFTWARE_VERSION")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String compSoftwareVersion;
	
	@Column(name = "STATUS")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String status;
	
	@Column(name = "HEARTBEAT_TIME")
    Date heartbeatTime;
	
	@Column(name = "CREATION_TIME")
	Date created;
	
	@Column(name = "UPDATE_TIME")
	Date updated;
	
	@Transient
	String compStatus;
	
	@Transient
	int statusSorter=-1;

	@ManyToOne(optional = false)
	@JoinColumn(name = "COMP_TYPE_ID", nullable = false)
	ComponentType compType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCompID() {
		return compID;
	}
	public void setCompID(String compID) {
		this.compID = compID;
	}
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getLocalIP() {
		return localIP;
	}
	
	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	public String getClusterIP() {
		return clusterIP;
	}

	public void setClusterIP(String clusterIP) {
		this.clusterIP = clusterIP;
	}
	public String getMgmtUrl() {
		return mgmtUrl;
	}

	public void setMgmtUrl(String mgmtUrl) {
		this.mgmtUrl = mgmtUrl;
	}
	public int getRunningVersion() {
		return runningVersion;
	}
	public void setRunningVersion(int runningVersion) {
		this.runningVersion = runningVersion;
	}
	public int getConfigVersion() {
		return configVersion;
	}
	public void setConfigVersion(int configVersion) {
		this.configVersion = configVersion;
	}
	public String getAlarm() {
		return alarm;
	}

	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public String getCompSoftwareVersion() {
		return compSoftwareVersion;
	}
	public void setCompSoftwareVersion(String compSoftwareVersion) {
		this.compSoftwareVersion = compSoftwareVersion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public Date getHeartbeatTime() {
		return heartbeatTime;
	}
	public void setHeartbeatTime(Date heartbeatTime) {
		this.heartbeatTime = heartbeatTime;
	}
	public String getCompStatus() {
		return compStatus;
	}
	public void setCompStatus(String compStatus) {
		this.compStatus = compStatus;
	}
	public ComponentType getCompType() {
		return compType;
	}
	public void setCompType(ComponentType compType) {
		this.compType = compType;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	
	@JsonGetter
	public int getStatusSorter(){
		if (this.status != null && this.compStatus != null) {
			if (this.status.equalsIgnoreCase("ACTIVE")) {
	            if (this.compStatus.equalsIgnoreCase("UP")) {
	            	statusSorter = 8;
	            } else if (this.compStatus.equalsIgnoreCase("DOWN")) {
	            	statusSorter = 7;
	            }
	        } else if (this.status.equalsIgnoreCase("INACTIVE")) {
	        	statusSorter = 6;
	        } else {
	            if (this.compStatus.equalsIgnoreCase("UP")) {
	            	statusSorter = 5;
	            } else if (this.compStatus.equalsIgnoreCase("DOWN")) {
	            	statusSorter = 4;
	            }
	        }
		}
        return statusSorter;
	}
}
