/**
 * 
 */
package com.vidyo.bo.conference;

import java.io.Serializable;

/**
 * @author Ganesh
 *
 */
public class Conference implements Serializable {

	private static final long serialVersionUID = 1L;

	private String conferenceName;
	private String uniqueCallId;
    private int conferenceCreated = 1; // assume true as that was the original assumption in the code

	public String getConferenceName() {
		return conferenceName;
	}

	public void setConferenceName(String conferenceName) {
		this.conferenceName = conferenceName;
	}

	public String getUniqueCallId() {
		return uniqueCallId;
	}

	public void setUniqueCallId(String uniqueCallId) {
		this.uniqueCallId = uniqueCallId;
	}

    public int getConferenceCreated() {
        return conferenceCreated;
    }

    public void setConferenceCreated(int conferenceCreated) {
        this.conferenceCreated = conferenceCreated;
    }

    // helper method
    public boolean isConferenceCreatedInVidyoManager() {
        return this.getConferenceCreated() == 1;
    }
}
