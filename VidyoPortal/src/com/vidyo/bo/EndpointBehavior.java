/**
 * 
 */
package com.vidyo.bo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * EndpointBehavior is the class to repersent 
 * @author ysakurikar
 *
 */
@Entity
@Table (name="EndpointBehavior")
public class EndpointBehavior implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int endpointBehaviorID;
	
	@JoinColumn (name="tenantID", referencedColumnName="tenantID")
	@ManyToOne (fetch = FetchType.LAZY)
	private Tenant tenant;
	
	private String endpointBehaviorKey;
	private int windowSizeHeight;
	private int windowSizeWidth;
	private int windowPositionTop;
	private int windowPositionBottom;
	private int windowPositionLeft;
	private int windowPositionRight;
	private int welcomePage;
	private int beautyScreen;
	private int loginModule;
	private int publicChat;
	private int leftPanel;
	private int inCallSearch;
	private int inviteParticipants;
	private int contentSharing;
	private int shareDialogOnJoin;
	private int displayLabels;
	private int remoteContentAccess;
	private int cameraMuteControl;
	private int muteCameraOnEntry;
	private int audioMuteControl;
	private int muteAudioOnEntry;
	private int deviceSettings;
	private int pinnedParticipant;
	private int recordConference;
	private String recordingRole;
	private int exitOnUserHangup;
	private int automaticallyUpdate;
	private int lockUserName;
	private int enableAutoAnswer;
	private int participantNotification;
	private int fullScreenVideo;
	private String preIframeUrl;
	private int preIframeSize;
    private String topIframeUrl;
    private int topIframeSize;
    private String leftIframeUrl;
    private int leftIframeSize;
    private String rightIframeUrl;
    private int rightIframeSize;
    private String bottomIframeUrl;
    private int bottomIframeSize;
    private String postIframeUrl;
    private int postIframeSize;
	
	/**
	 * @return the endpointBehaviorID
	 */
	public int getEndpointBehaviorID() {
		return endpointBehaviorID;
	}
	/**
	 * @param endpointBehaviorID the endpointBehaviorID to set
	 */
	public void setEndpointBehaviorID(int endpointBehaviorID) {
		this.endpointBehaviorID = endpointBehaviorID;
	}
	/**
	 * @return the tenant
	 */
	@JsonIgnore
	public Tenant getTenant() {
		return tenant;
	}
	/**
	 * @param tenant to set
	 */
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	/**
	 * @return the endpointBehaviorKey
	 */
	public String getEndpointBehaviorKey() {
		return endpointBehaviorKey;
	}
	/**
	 * @param endpointBehaviorKey the endpointBehaviorKey to set
	 */
	public void setEndpointBehaviorKey(String endpointBehaviorKey) {
		this.endpointBehaviorKey = endpointBehaviorKey;
	}
	/**
	 * @return the welcomePage
	 */
	public int getWelcomePage() {
		return welcomePage;
	}
	/**
	 * @param welcomePage the welcomePage to set
	 */
	public void setWelcomePage(int welcomePage) {
		this.welcomePage = welcomePage;
	}
	/**
	 * @return the beautyScreen
	 */
	public int getBeautyScreen() {
		return beautyScreen;
	}
	/**
	 * @param beautyScreen the beautyScreen to set
	 */
	public void setBeautyScreen(int beautyScreen) {
		this.beautyScreen = beautyScreen;
	}
	/**
	 * @return the loginModule
	 */
	public int getLoginModule() {
		return loginModule;
	}
	/**
	 * @param loginModule the loginModule to set
	 */
	public void setLoginModule(int loginModule) {
		this.loginModule = loginModule;
	}
	/**
	 * @return the publicChat
	 */
	public int getPublicChat() {
		return publicChat;
	}
	/**
	 * @param publicChat the publicChat to set
	 */
	public void setPublicChat(int publicChat) {
		this.publicChat = publicChat;
	}
	/**
	 * @return the leftPanel
	 */
	public int getLeftPanel() {
		return leftPanel;
	}
	/**
	 * @param leftPanel the leftPanel to set
	 */
	public void setLeftPanel(int leftPanel) {
		this.leftPanel = leftPanel;
	}
	/**
	 * @return the inCallSearch
	 */
	public int getInCallSearch() {
		return inCallSearch;
	}
	/**
	 * @param inCallSearch the inCallSearch to set
	 */
	public void setInCallSearch(int inCallSearch) {
		this.inCallSearch = inCallSearch;
	}
	/**
	 * @return the inviteParticipants
	 */
	public int getInviteParticipants() {
		return inviteParticipants;
	}
	/**
	 * @param inviteParticipants the inviteParticipants to set
	 */
	public void setInviteParticipants(int inviteParticipants) {
		this.inviteParticipants = inviteParticipants;
	}
	/**
	 * @return the contentSharing
	 */
	public int getContentSharing() {
		return contentSharing;
	}
	/**
	 * @param contentSharing the contentSharing to set
	 */
	public void setContentSharing(int contentSharing) {
		this.contentSharing = contentSharing;
	}
	/**
	 * @return the shareDialogOnJoin
	 */
	public int getShareDialogOnJoin() {
		return shareDialogOnJoin;
	}
	/**
	 * @param shareDialogOnJoin the shareDialogOnJoin to set
	 */
	public void setShareDialogOnJoin(int shareDialogOnJoin) {
		this.shareDialogOnJoin = shareDialogOnJoin;
	}
	/**
	 * @return the displayLabels
	 */
	public int getDisplayLabels() {
		return displayLabels;
	}
	/**
	 * @param displayLabels the displayLabels to set
	 */
	public void setDisplayLabels(int displayLabels) {
		this.displayLabels = displayLabels;
	}
	/**
	 * @return the remoteContentAccess
	 */
	public int getRemoteContentAccess() {
		return remoteContentAccess;
	}
	/**
	 * @param remoteContentAccess the remoteContentAccess to set
	 */
	public void setRemoteContentAccess(int remoteContentAccess) {
		this.remoteContentAccess = remoteContentAccess;
	}
	/**
	 * @return the cameraMuteControl
	 */
	public int getCameraMuteControl() {
		return cameraMuteControl;
	}
	/**
	 * @param cameraMuteControl the cameraMuteControl to set
	 */
	public void setCameraMuteControl(int cameraMuteControl) {
		this.cameraMuteControl = cameraMuteControl;
	}
	/**
	 * @return the muteCameraOnEntry
	 */
	public int getMuteCameraOnEntry() {
		return muteCameraOnEntry;
	}
	/**
	 * @param muteCameraOnEntry the muteCameraOnEntry to set
	 */
	public void setMuteCameraOnEntry(int muteCameraOnEntry) {
		this.muteCameraOnEntry = muteCameraOnEntry;
	}
	/**
	 * @return the audioMuteControl
	 */
	public int getAudioMuteControl() {
		return audioMuteControl;
	}
	/**
	 * @param audioMuteControl the audioMuteControl to set
	 */
	public void setAudioMuteControl(int audioMuteControl) {
		this.audioMuteControl = audioMuteControl;
	}
	/**
	 * @return the muteAudioOnEntry
	 */
	public int getMuteAudioOnEntry() {
		return muteAudioOnEntry;
	}
	/**
	 * @param muteAudioOnEntry the muteAudioOnEntry to set
	 */
	public void setMuteAudioOnEntry(int muteAudioOnEntry) {
		this.muteAudioOnEntry = muteAudioOnEntry;
	}
	/**
	 * @return the deviceSettings
	 */
	public int getDeviceSettings() {
		return deviceSettings;
	}
	/**
	 * @param deviceSettings the deviceSettings to set
	 */
	public void setDeviceSettings(int deviceSettings) {
		this.deviceSettings = deviceSettings;
	}
	/**
	 * @return the pinnedParticipant
	 */
	public int getPinnedParticipant() {
		return pinnedParticipant;
	}
	/**
	 * @param pinnedParticipant the pinnedParticipant to set
	 */
	public void setPinnedParticipant(int pinnedParticipant) {
		this.pinnedParticipant = pinnedParticipant;
	}
	/**
	 * @return the recordConference
	 */
	public int getRecordConference() {
		return recordConference;
	}
	/**
	 * @param recordConference the recordConference to set
	 */
	public void setRecordConference(int recordConference) {
		this.recordConference = recordConference;
	}
	/**
	 * @return the recordingRole
	 */
	public String getRecordingRole() {
		return recordingRole;
	}
	/**
	 * @param recordingRole the recordingRole to set
	 */
	public void setRecordingRole(String recordingRole) {
		this.recordingRole = recordingRole;
	}
	/**
	 * @return the exitOnUserHangup
	 */
	public int getExitOnUserHangup() {
		return exitOnUserHangup;
	}
	/**
	 * @param exitOnUserHangup the exitOnUserHangup to set
	 */
	public void setExitOnUserHangup(int exitOnUserHangup) {
		this.exitOnUserHangup = exitOnUserHangup;
	}
	/**
	 * @return the automaticallyUpdate
	 */
	public int getAutomaticallyUpdate() {
		return automaticallyUpdate;
	}
	/**
	 * @param automaticallyUpdate the automaticallyUpdate to set
	 */
	public void setAutomaticallyUpdate(int automaticallyUpdate) {
		this.automaticallyUpdate = automaticallyUpdate;
	}
	/**
	 * @return the lockUserName
	 */
	public int getLockUserName() {
		return lockUserName;
	}
	/**
	 * @param lockUserName the lockUserName to set
	 */
	public void setLockUserName(int lockUserName) {
		this.lockUserName = lockUserName;
	}
	/**
	 * @return the enableAutoAnswer
	 */
	public int getEnableAutoAnswer() {
		return enableAutoAnswer;
	}
	/**
	 * @param enableAutoAnswer the enableAutoAnswer to set
	 */
	public void setEnableAutoAnswer(int enableAutoAnswer) {
		this.enableAutoAnswer = enableAutoAnswer;
	}
	/**
	 * @return the participantNotification
	 */
	public int getParticipantNotification() {
		return participantNotification;
	}
	/**
	 * @param participantNotification the participantNotification to set
	 */
	public void setParticipantNotification(int participantNotification) {
		this.participantNotification = participantNotification;
	}
	/**
	 * @return the fullScreenVideo
	 */
	public int getFullScreenVideo() {
		return fullScreenVideo;
	}
	/**
	 * @param fullScreenVideo the fullScreenVideo to set
	 */
	public void setFullScreenVideo(int fullScreenVideo) {
		this.fullScreenVideo = fullScreenVideo;
	}
	/**
	 * @return the windowSizeHeight
	 */
	public int getWindowSizeHeight() {
		return windowSizeHeight;
	}
	/**
	 * @param windowSizeHeight the windowSizeHeight to set
	 */
	public void setWindowSizeHeight(int windowSizeHeight) {
		this.windowSizeHeight = windowSizeHeight;
	}
	/**
	 * @return the windowSizeWidth
	 */
	public int getWindowSizeWidth() {
		return windowSizeWidth;
	}
	/**
	 * @param windowSizeWidth the windowSizeWidth to set
	 */
	public void setWindowSizeWidth(int windowSizeWidth) {
		this.windowSizeWidth = windowSizeWidth;
	}
	/**
	 * @return the windowPositionTop
	 */
	public int getWindowPositionTop() {
		return windowPositionTop;
	}
	/**
	 * @param windowPositionTop the windowPositionTop to set
	 */
	public void setWindowPositionTop(int windowPositionTop) {
		this.windowPositionTop = windowPositionTop;
	}
	/**
	 * @return the windowPositionBottom
	 */
	public int getWindowPositionBottom() {
		return windowPositionBottom;
	}
	/**
	 * @param windowPositionBottom the windowPositionBottom to set
	 */
	public void setWindowPositionBottom(int windowPositionBottom) {
		this.windowPositionBottom = windowPositionBottom;
	}
	/**
	 * @return the windowPositionLeft
	 */
	public int getWindowPositionLeft() {
		return windowPositionLeft;
	}
	/**
	 * @param windowPositionLeft the windowPositionLeft to set
	 */
	public void setWindowPositionLeft(int windowPositionLeft) {
		this.windowPositionLeft = windowPositionLeft;
	}
	/**
	 * @return the windowPositionRight
	 */
	public int getWindowPositionRight() {
		return windowPositionRight;
	}
	/**
	 * @param windowPositionRight the windowPositionRight to set
	 */
	public void setWindowPositionRight(int windowPositionRight) {
		this.windowPositionRight = windowPositionRight;
	}
	/**
	 * @return the preIframeUrl
	 */
	public String getPreIframeUrl() {
		return preIframeUrl;
	}
	/**
	 * @param preIframeUrl the preIframeUrl to set
	 */
	public void setPreIframeUrl(String preIframeUrl) {
		this.preIframeUrl = preIframeUrl;
	}
	/**
	 * @return the preIframeSize
	 */
	public int getPreIframeSize() {
		return preIframeSize;
	}
	/**
	 * @param preIframeSize the preIframeSize to set
	 */
	public void setPreIframeSize(int preIframeSize) {
		this.preIframeSize = preIframeSize;
	}
	/**
	 * @return the topIframeUrl
	 */
	public String getTopIframeUrl() {
		return topIframeUrl;
	}
	/**
	 * @param topIframeUrl the topIframeUrl to set
	 */
	public void setTopIframeUrl(String topIframeUrl) {
		this.topIframeUrl = topIframeUrl;
	}
	/**
	 * @return the topIframeSize
	 */
	public int getTopIframeSize() {
		return topIframeSize;
	}
	/**
	 * @param topIframeSize the topIframeSize to set
	 */
	public void setTopIframeSize(int topIframeSize) {
		this.topIframeSize = topIframeSize;
	}
	/**
	 * @return the leftIframeUrl
	 */
	public String getLeftIframeUrl() {
		return leftIframeUrl;
	}
	/**
	 * @param leftIframeUrl the leftIframeUrl to set
	 */
	public void setLeftIframeUrl(String leftIframeUrl) {
		this.leftIframeUrl = leftIframeUrl;
	}
	/**
	 * @return the leftIframeSize
	 */
	public int getLeftIframeSize() {
		return leftIframeSize;
	}
	/**
	 * @param leftIframeSize the leftIframeSize to set
	 */
	public void setLeftIframeSize(int leftIframeSize) {
		this.leftIframeSize = leftIframeSize;
	}
	/**
	 * @return the rightIframeUrl
	 */
	public String getRightIframeUrl() {
		return rightIframeUrl;
	}
	/**
	 * @param rightIframeUrl the rightIframeUrl to set
	 */
	public void setRightIframeUrl(String rightIframeUrl) {
		this.rightIframeUrl = rightIframeUrl;
	}
	/**
	 * @return the rightIframeSize
	 */
	public int getRightIframeSize() {
		return rightIframeSize;
	}
	/**
	 * @param rightIframeSize the rightIframeSize to set
	 */
	public void setRightIframeSize(int rightIframeSize) {
		this.rightIframeSize = rightIframeSize;
	}
	/**
	 * @return the bottomIframeUrl
	 */
	public String getBottomIframeUrl() {
		return bottomIframeUrl;
	}
	/**
	 * @param bottomIframeUrl the bottomIframeUrl to set
	 */
	public void setBottomIframeUrl(String bottomIframeUrl) {
		this.bottomIframeUrl = bottomIframeUrl;
	}
	/**
	 * @return the bottomIframeSize
	 */
	public int getBottomIframeSize() {
		return bottomIframeSize;
	}
	/**
	 * @param bottomIframeSize the bottomIframeSize to set
	 */
	public void setBottomIframeSize(int bottomIframeSize) {
		this.bottomIframeSize = bottomIframeSize;
	}
	/**
	 * @return the postIframeUrl
	 */
	public String getPostIframeUrl() {
		return postIframeUrl;
	}
	/**
	 * @param postIframeUrl the postIframeUrl to set
	 */
	public void setPostIframeUrl(String postIframeUrl) {
		this.postIframeUrl = postIframeUrl;
	}
	/**
	 * @return the postIframeSize
	 */
	public int getPostIframeSize() {
		return postIframeSize;
	}
	/**
	 * @param postIframeSize the postIframeSize to set
	 */
	public void setPostIframeSize(int postIframeSize) {
		this.postIframeSize = postIframeSize;
	}
	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(new String[] { "tenant"}).toString();
				
	}
	
	
		
}
