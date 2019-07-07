package com.vidyo.superapp.components.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;

@Entity
@Table(name = "router_media_addr_map")
public class RouterMediaAddrMap  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	int id;
	
	@Column(name = "LOCAL_IP")
	@JsonSerialize(using=StringSerializer.class)
    String localIP;
    
	@Column(name = "REMOTE_IP")
    String remoteIP;
	
    @ManyToOne
    @JoinColumn(name = "ROUTER_CONFIG_ID",referencedColumnName = "ID")
    @JsonIgnore
    VidyoRouter vidyoRouter;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLocalIP() {
		return localIP;
	}
	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	public String getRemoteIP() {
		return remoteIP;
	}
	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}
	public VidyoRouter getVidyoRouter() {
		return vidyoRouter;
	}
	public void setVidyoRouter(VidyoRouter vidyoRouter) {
		this.vidyoRouter = vidyoRouter;
	}
}
