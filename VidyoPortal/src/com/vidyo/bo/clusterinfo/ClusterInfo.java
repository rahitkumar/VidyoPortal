/**
 * 
 */
package com.vidyo.bo.clusterinfo;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class ClusterInfo implements Serializable{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private String peerStatus;
	
	/**
	 * 
	 */
	private int lastPingOK;
	
	/**
	 * 
	 */
	private int negotiateCounter;
	
	/**
	 * 
	 */
	private String myRole;
	
	/**
	 * 
	 */
	private int peerLastHeartbeat;
	
	/**
	 * 
	 */
	private String peerAction;
	
	/**
	 * 
	 */
	private int peerHeartbeatCounter;
	
	/**
	 * 
	 */
	private String myLastRole;
	
	/**
	 * 
	 */
	private int syncDuration;
	
	/**
	 * 
	 */
	private String peerRole;
	
	/**
	 * 
	 */
	private int lastHBWatcherHB;
	
	/**
	 * 
	 */
	private String myIP;
	
	/**
	 * 
	 */
	private long peerUptime;
	
	/**
	 * 
	 */
	private int lastACKFromPeer;
	
	/**
	 * 
	 */
	private String peerBackupChecksum;
	
	/**
	 * 
	 */
	private Long lastDBSync;
	
	/**
	 * 
	 */
	private String myLastAction;
	
	/**
	 * 
	 */
	private String clusterIP;
	
	/**
	 * 
	 */
	private int lastClientHB;
	
	/**
	 * 
	 */
	private long mySeqno;
	
	/**
	 * 
	 */
	private int backupDuration;
	
	/**
	 * 
	 */
	private int lastServerHB;
	
	/**
	 * 
	 */
	private String peerIP;
	
	/**
	 * 
	 */
	private long expectedSerialNumber;
	
	/**
	 * 
	 */
	private String myState;
	
	/**
	 * 
	 */
	private String myBackupChecksum;
	
	/**
	 * 
	 */
	private String myStatus;
	
	/**
	 * 
	 */
	private int peerSeqno;
	
	/**
	 * 
	 */
	private int lastPingerHB;
	
	/**
	 * 
	 */
	private boolean preferred;
	
	private String dBSyncStatus;


	
	/**
	 * @return the peerStatus
	 */
	public String getPeerStatus() {
		return peerStatus;
	}

	/**
	 * @param peerStatus the peerStatus to set
	 */
	public void setPeerStatus(String peerStatus) {
		this.peerStatus = peerStatus;
	}

	/**
	 * @return the lastPingOK
	 */
	public int getLastPingOK() {
		return lastPingOK;
	}

	/**
	 * @param lastPingOK the lastPingOK to set
	 */
	public void setLastPingOK(int lastPingOK) {
		this.lastPingOK = lastPingOK;
	}

	/**
	 * @return the negotiateCounter
	 */
	public int getNegotiateCounter() {
		return negotiateCounter;
	}

	/**
	 * @param negotiateCounter the negotiateCounter to set
	 */
	public void setNegotiateCounter(int negotiateCounter) {
		this.negotiateCounter = negotiateCounter;
	}

	/**
	 * @return the myRole
	 */
	public String getMyRole() {
		return myRole;
	}

	/**
	 * @param myRole the myRole to set
	 */
	public void setMyRole(String myRole) {
		this.myRole = myRole;
	}

	/**
	 * @return the peerLastHeartbeat
	 */
	public int getPeerLastHeartbeat() {
		return peerLastHeartbeat;
	}

	/**
	 * @param peerLastHeartbeat the peerLastHeartbeat to set
	 */
	public void setPeerLastHeartbeat(int peerLastHeartbeat) {
		this.peerLastHeartbeat = peerLastHeartbeat;
	}

	/**
	 * @return the peerAction
	 */
	public String getPeerAction() {
		return peerAction;
	}

	/**
	 * @param peerAction the peerAction to set
	 */
	public void setPeerAction(String peerAction) {
		this.peerAction = peerAction;
	}

	/**
	 * @return the peerHeartbeatCounter
	 */
	public int getPeerHeartbeatCounter() {
		return peerHeartbeatCounter;
	}

	/**
	 * @param peerHeartbeatCounter the peerHeartbeatCounter to set
	 */
	public void setPeerHeartbeatCounter(int peerHeartbeatCounter) {
		this.peerHeartbeatCounter = peerHeartbeatCounter;
	}

	/**
	 * @return the myLastRole
	 */
	public String getMyLastRole() {
		return myLastRole;
	}

	/**
	 * @param myLastRole the myLastRole to set
	 */
	public void setMyLastRole(String myLastRole) {
		this.myLastRole = myLastRole;
	}

	/**
	 * @return the syncDuration
	 */
	public int getSyncDuration() {
		return syncDuration;
	}

	/**
	 * @param syncDuration the syncDuration to set
	 */
	public void setSyncDuration(int syncDuration) {
		this.syncDuration = syncDuration;
	}

	/**
	 * @return the peerRole
	 */
	public String getPeerRole() {
		return peerRole;
	}

	/**
	 * @param peerRole the peerRole to set
	 */
	public void setPeerRole(String peerRole) {
		this.peerRole = peerRole;
	}

	/**
	 * @return the lastHBWatcherHB
	 */
	public int getLastHBWatcherHB() {
		return lastHBWatcherHB;
	}

	/**
	 * @param lastHBWatcherHB the lastHBWatcherHB to set
	 */
	public void setLastHBWatcherHB(int lastHBWatcherHB) {
		this.lastHBWatcherHB = lastHBWatcherHB;
	}

	/**
	 * @return the myIP
	 */
	public String getMyIP() {
		return myIP;
	}

	/**
	 * @param myIP the myIP to set
	 */
	public void setMyIP(String myIP) {
		this.myIP = myIP;
	}

	/**
	 * @return the peerUptime
	 */
	public long getPeerUptime() {
		return peerUptime;
	}

	/**
	 * @param peerUptime the peerUptime to set
	 */
	public void setPeerUptime(long peerUptime) {
		this.peerUptime = peerUptime;
	}

	/**
	 * @return the lastACKFromPeer
	 */
	public int getLastACKFromPeer() {
		return lastACKFromPeer;
	}

	/**
	 * @param lastACKFromPeer the lastACKFromPeer to set
	 */
	public void setLastACKFromPeer(int lastACKFromPeer) {
		this.lastACKFromPeer = lastACKFromPeer;
	}

	/**
	 * @return the peerBackupChecksum
	 */
	public String getPeerBackupChecksum() {
		return peerBackupChecksum;
	}

	/**
	 * @param peerBackupChecksum the peerBackupChecksum to set
	 */
	public void setPeerBackupChecksum(String peerBackupChecksum) {
		this.peerBackupChecksum = peerBackupChecksum;
	}

	/**
	 * @return the lastDBSync
	 */
	public Long getLastDBSync() {
		return lastDBSync;
	}

	/**
	 * @param lastDBSync the lastDBSync to set
	 */
	public void setLastDBSync(Long lastDBSync) {
		this.lastDBSync = lastDBSync;
	}

	/**
	 * @return the myLastAction
	 */
	public String getMyLastAction() {
		return myLastAction;
	}

	/**
	 * @param myLastAction the myLastAction to set
	 */
	public void setMyLastAction(String myLastAction) {
		this.myLastAction = myLastAction;
	}

	/**
	 * @return the clusterIP
	 */
	public String getClusterIP() {
		return clusterIP;
	}

	/**
	 * @param clusterIP the clusterIP to set
	 */
	public void setClusterIP(String clusterIP) {
		this.clusterIP = clusterIP;
	}

	/**
	 * @return the lastClientHB
	 */
	public int getLastClientHB() {
		return lastClientHB;
	}

	/**
	 * @param lastClientHB the lastClientHB to set
	 */
	public void setLastClientHB(int lastClientHB) {
		this.lastClientHB = lastClientHB;
	}

	/**
	 * @return the mySeqno
	 */
	public long getMySeqno() {
		return mySeqno;
	}

	/**
	 * @param mySeqno the mySeqno to set
	 */
	public void setMySeqno(long mySeqno) {
		this.mySeqno = mySeqno;
	}

	/**
	 * @return the backupDuration
	 */
	public int getBackupDuration() {
		return backupDuration;
	}

	/**
	 * @param backupDuration the backupDuration to set
	 */
	public void setBackupDuration(int backupDuration) {
		this.backupDuration = backupDuration;
	}

	/**
	 * @return the lastServerHB
	 */
	public int getLastServerHB() {
		return lastServerHB;
	}

	/**
	 * @param lastServerHB the lastServerHB to set
	 */
	public void setLastServerHB(int lastServerHB) {
		this.lastServerHB = lastServerHB;
	}

	/**
	 * @return the peerIP
	 */
	public String getPeerIP() {
		return peerIP;
	}

	/**
	 * @param peerIP the peerIP to set
	 */
	public void setPeerIP(String peerIP) {
		this.peerIP = peerIP;
	}

	/**
	 * @return the expectedSerialNumber
	 */
	public long getExpectedSerialNumber() {
		return expectedSerialNumber;
	}

	/**
	 * @param expectedSerialNumber the expectedSerialNumber to set
	 */
	public void setExpectedSerialNumber(long expectedSerialNumber) {
		this.expectedSerialNumber = expectedSerialNumber;
	}

	/**
	 * @return the myState
	 */
	public String getMyState() {
		return myState;
	}

	/**
	 * @param myState the myState to set
	 */
	public void setMyState(String myState) {
		this.myState = myState;
	}

	/**
	 * @return the myBackupChecksum
	 */
	public String getMyBackupChecksum() {
		return myBackupChecksum;
	}

	/**
	 * @param myBackupChecksum the myBackupChecksum to set
	 */
	public void setMyBackupChecksum(String myBackupChecksum) {
		this.myBackupChecksum = myBackupChecksum;
	}

	/**
	 * @return the myStatus
	 */
	public String getMyStatus() {
		return myStatus;
	}

	/**
	 * @param myStatus the myStatus to set
	 */
	public void setMyStatus(String myStatus) {
		this.myStatus = myStatus;
	}

	/**
	 * @return the peerSeqno
	 */
	public int getPeerSeqno() {
		return peerSeqno;
	}

	/**
	 * @param peerSeqno the peerSeqno to set
	 */
	public void setPeerSeqno(int peerSeqno) {
		this.peerSeqno = peerSeqno;
	}

	/**
	 * @return the lastPingerHB
	 */
	public int getLastPingerHB() {
		return lastPingerHB;
	}

	/**
	 * @param lastPingerHB the lastPingerHB to set
	 */
	public void setLastPingerHB(int lastPingerHB) {
		this.lastPingerHB = lastPingerHB;
	}

	/**
	 * @return the preferred
	 */
	public boolean isPreferred() {
		return preferred;
	}

	/**
	 * @param preferred the preferred to set
	 */
	public void setPreferred(boolean preferred) {
		this.preferred = preferred;
	}
	
	@Override
	public String toString() {
		return "ClusterInfo [peerStatus=" + peerStatus + ", lastPingOK=" + lastPingOK + ", negotiateCounter="
				+ negotiateCounter + ", myRole=" + myRole + ", peerLastHeartbeat=" + peerLastHeartbeat + ", peerAction="
				+ peerAction + ", peerHeartbeatCounter=" + peerHeartbeatCounter + ", myLastRole=" + myLastRole
				+ ", syncDuration=" + syncDuration + ", peerRole=" + peerRole + ", lastHBWatcherHB=" + lastHBWatcherHB
				+ ", myIP=" + myIP + ", peerUptime=" + peerUptime + ", lastACKFromPeer=" + lastACKFromPeer
				+ ", peerBackupChecksum=" + peerBackupChecksum + ", lastDBSync=" + lastDBSync + ", myLastAction="
				+ myLastAction + ", clusterIP=" + clusterIP + ", lastClientHB=" + lastClientHB + ", mySeqno=" + mySeqno
				+ ", backupDuration=" + backupDuration + ", lastServerHB=" + lastServerHB + ", peerIP=" + peerIP
				+ ", expectedSerialNumber=" + expectedSerialNumber + ", myState=" + myState + ", myBackupChecksum="
				+ myBackupChecksum + ", myStatus=" + myStatus + ", peerSeqno=" + peerSeqno + ", lastPingerHB="
				+ lastPingerHB + ", preferred=" + preferred + ", dbSyncStatus=" + dBSyncStatus + "]";
	}

	public String getdBSyncStatus() {
		return dBSyncStatus;
	}

	public void setdBSyncStatus(String dBSyncStatus) {
		this.dBSyncStatus = dBSyncStatus;
	}
}
