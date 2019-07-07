package com.vidyo.superapp.components.bo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;

@Entity
@Table(name = "RecorderEndpoints")
public class RecorderEndpoints implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int endpointID;
    
    @JsonSerialize(using=StringSerializer.class)
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String recID;
    
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String description;
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
    String endpointGUID;
    int status;
    String prefix;
    Timestamp updateTime;
    int webcast = 0;
    int entityID;
	long sequenceNum;

	@ManyToOne
	@JoinColumn(name = "serviceID", referencedColumnName = "COMP_ID", insertable = false, updatable = false)
	@JsonIgnore
	private VidyoRecorder vidyoRecorder;

    public int getEndpointID() {
        return endpointID;
    }

    public void setEndpointID(int endpointID) {
        this.endpointID = endpointID;
    }

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndpointGUID() {
        return endpointGUID;
    }

    public void setEndpointGUID(String endpointGUID) {
        this.endpointGUID = endpointGUID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public int getWebcast() {
        return webcast;
    }

    public void setWebcast(int webcast) {
        this.webcast = webcast;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

	public long getSequenceNum() {
		return sequenceNum;
	}

	public void setSequenceNum(long sequenceNum) {
		this.sequenceNum = sequenceNum;
	}

	public VidyoRecorder getVidyoRecorder() {
		return vidyoRecorder;
	}

	public void setVidyoRecorder(VidyoRecorder vidyoRecorder) {
		this.vidyoRecorder = vidyoRecorder;
	}
	
}