package com.vidyo.superapp.components.bo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Entity
@Table(name = "vidyo_recorder_config")
public class VidyoRecorder implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
    @Column(name = "USERNAME")
    String userName;
    
    @JsonSerialize(using=StringSerializer.class)
    @Column(name = "PASSWORD")
    String password;
    
    @OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMP_ID")
	Component components;

    @OneToMany(mappedBy = "vidyoRecorder",fetch = FetchType.EAGER)
	Set<RecorderEndpoints> recorderEndpoints = new HashSet<RecorderEndpoints>();
    
    @JsonProperty("compType")
    @Transient
    private ComponentType compType;
    
    /**
    * 
    * @return
    * The compType
    */
    @JsonProperty("compType")
    public ComponentType getCompType() {
    return compType;
    }

    /**
    * 
    * @param compType
    * The compType
    */
    @JsonProperty("compType")
    public void setCompType(ComponentType compType) {
    this.compType = compType;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Component getComponents() {
		return components;
	}

	public void setComponents(Component components) {
		this.components = components;
	}
	
	public Set<RecorderEndpoints> getRecorderEndpoints() {
		return recorderEndpoints;
	}
	
	public void setRecorderEndpoints(Set<RecorderEndpoints> recorderEndpoints) {
		this.recorderEndpoints = recorderEndpoints;
	}
}
