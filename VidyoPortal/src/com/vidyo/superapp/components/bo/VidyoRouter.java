package com.vidyo.superapp.components.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NumberSerializers.IntegerSerializer;
import com.vidyo.framework.beanserializer.WhiteSpaceRemovalDeserializer;
import com.vidyo.superapp.routerpools.bo.RouterPoolMap;

@Entity
@Table(name = "vidyo_router_config")
public class VidyoRouter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private int id;

	@Column(name = "SCIP_FQDN")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	private String scipFqdn;

	@Column(name = "SCIP_PORT")
	private int scipPort;

	@Column(name = "MEDIA_PORT_START")
	private int mediaPortStart;

	@Column(name = "MEDIA_PORT_END")
	private int mediaPortEnd;

	@Column(name = "STUN_FQDN_IP")
	@JsonDeserialize(using=WhiteSpaceRemovalDeserializer.class)
	private String stunFqdn;

	@Column(name = "STUN_PORT")
	private int stunPort;

	@Column(name = "DSCP_VIDEO")
	private int dscpVidyo;

	@Column(name = "AUDIO_DSCP")
	private int audioDscp;

	@Column(name = "CONTENT_DSCP")
	private int contentDscp;

	@Column(name = "SIGNALING_DSCP")
	private int signalingDscp;

	@Column(name = "PROXY_ENABLED")
	private int proxyEnabled;

	@Column(name = "PROXY_USE_TLS")
	private int proxyUseTls;

	@OneToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "COMP_ID")
	private Component components;

	@OneToMany(mappedBy = "vidyoRouter", fetch = FetchType.EAGER)
	private List<RouterMediaAddrMap> routerMediaAddrMap = new ArrayList<RouterMediaAddrMap>();

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "vidyoRouter")
	@JsonIgnore
	private Set<RouterPoolMap> routerPoolMap = new HashSet<RouterPoolMap>();

	@Transient
	private boolean routerPoolPresent = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getScipFqdn() {
		return scipFqdn;
	}

	public void setScipFqdn(String scipFqdn) {
		this.scipFqdn = scipFqdn;
	}

	public int getScipPort() {
		return scipPort;
	}

	public void setScipPort(int scipPort) {
		this.scipPort = scipPort;
	}

	public int getMediaPortStart() {
		return mediaPortStart;
	}

	public void setMediaPortStart(int mediaPortStart) {
		this.mediaPortStart = mediaPortStart;
	}

	public int getMediaPortEnd() {
		return mediaPortEnd;
	}

	public void setMediaPortEnd(int mediaPortEnd) {
		this.mediaPortEnd = mediaPortEnd;
	}

	public String getStunFqdn() {
		return stunFqdn;
	}

	public void setStunFqdn(String stunFqdn) {
		this.stunFqdn = stunFqdn;
	}

	public int getStunPort() {
		return stunPort;
	}

	public void setStunPort(int stunPort) {
		this.stunPort = stunPort;
	}

	public int getDscpVidyo() {
		return dscpVidyo;
	}

	public void setDscpVidyo(int dscpVidyo) {
		this.dscpVidyo = dscpVidyo;
	}

	public int getAudioDscp() {
		return audioDscp;
	}

	public void setAudioDscp(int audioDscp) {
		this.audioDscp = audioDscp;
	}

	public int getContentDscp() {
		return contentDscp;
	}

	public void setContentDscp(int contentDscp) {
		this.contentDscp = contentDscp;
	}

	public int getSingnalingDscp() {
		return signalingDscp;
	}

	public void setSingnalingDscp(int singnalingDscp) {
		this.signalingDscp = singnalingDscp;
	}

	public Component getComponents() {
		return components;
	}

	public void setComponents(Component components) {
		this.components = components;
	}

	public List<RouterMediaAddrMap> getRouterMediaAddrMap() {
		return routerMediaAddrMap;
	}

	public void setRouterMediaAddrMap(List<RouterMediaAddrMap> routerMediaAddrMap) {
		this.routerMediaAddrMap = routerMediaAddrMap;
	}

	public Set<RouterPoolMap> getRouterPoolMap() {
		return routerPoolMap;
	}

	public void setRouterPoolMap(Set<RouterPoolMap> routerPoolMap) {
		this.routerPoolMap = routerPoolMap;
	}

	/**
	 * @return the proxyEnabled
	 */
	public int getProxyEnabled() {
		return proxyEnabled;
	}

	/**
	 * @param proxyEnabled the proxyEnabled to set
	 */
	public void setProxyEnabled(int proxyEnabled) {
		this.proxyEnabled = proxyEnabled;
	}

	/**
	 * @return the proxyUseTls
	 */
	public int getProxyUseTls() {
		return proxyUseTls;
	}

	/**
	 * @param proxyUseTls the proxyUseTls to set
	 */
	public void setProxyUseTls(int proxyUseTls) {
		this.proxyUseTls = proxyUseTls;
	}

	public boolean isRouterPoolPresent() {
		return routerPoolPresent;
	}

	public void setRouterPoolPresent(boolean routerPoolPresent) {
		this.routerPoolPresent = routerPoolPresent;
	}


}