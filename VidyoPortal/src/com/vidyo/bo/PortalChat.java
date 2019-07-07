package com.vidyo.bo;

public class PortalChat {
	
	private boolean isChatAvailable;
	private boolean isDefaultPublicChatEnabled;
	private boolean isDefaultPrivateChatEnabled;
	
	public boolean isChatAvailable() {
		return isChatAvailable;
	}
	
	public void setChatAvailable(boolean isChatAvailable) {
		this.isChatAvailable = isChatAvailable;
	}
	
	public boolean isDefaultPublicChatEnabled() {
		return isDefaultPublicChatEnabled;
	}
	
	public void setDefaultPublicChatEnabled(boolean isDefaultPublicChatEnabled) {
		this.isDefaultPublicChatEnabled = isDefaultPublicChatEnabled;
	}

	public boolean isDefaultPrivateChatEnabled() {
		return isDefaultPrivateChatEnabled;
	}

	public void setDefaultPrivateChatEnabled(boolean isDefaultPrivateChatEnabled) {
		this.isDefaultPrivateChatEnabled = isDefaultPrivateChatEnabled;
	}

}
