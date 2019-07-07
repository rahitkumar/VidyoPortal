/**
 * 
 */
package com.vidyo.portal.batch.jobs.ipccleanup.tasklet;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

import com.vidyo.bo.ExternalLink;
import com.vidyo.service.IFederationConferenceService;

/**
 * This job checks the ExternalLinks table for entries with status 1
 * (disconnected) for more than 5 minutes and gets the conference name of that
 * entry. It sends delete external link message to VidyoManager and deletes that
 * particular entry from ExternalLinks table, if there are no other External
 * Links with that conferenceName and if there are no entries in Conferences
 * table with the same ConferenceName, it sends a delete conference message to
 * VidyoManager
 * 
 * 
 * @author Ganesh
 * 
 */
public class IpcCleanupTasklet implements Tasklet {

	/**
	 * 
	 */
	protected static final Logger logger = Logger
			.getLogger(IpcCleanupTasklet.class);

	/**
	 * Inactive time interval
	 */
	private int timeLapsed;

	/**
	 * 
	 */
	private int status;

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 
	 */
	private IFederationConferenceService federationConferenceService;

	/**
	 * @param federationConferenceService
	 *            the federationConferenceService to set
	 */
	public void setFederationConferenceService(
			IFederationConferenceService federationConferenceService) {
		this.federationConferenceService = federationConferenceService;
	}

	/**
	 * @param timeLapsed
	 *            the timeLapsed to set
	 */
	public void setTimeLapsed(int timeLapsed) {
		this.timeLapsed = timeLapsed;
	}

	/**
	 * 
	 */
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1)
			throws Exception {
		// Step 1 - Get the ExternalLinks disconnected for more than 5 minutes
		List<ExternalLink> externalLinks = getDisconnectedExternalLinks(status,
				timeLapsed);
		logger.info("No.of External Links in disconnected status - "
				+ externalLinks.size());
		if (externalLinks.isEmpty()) {
			return RepeatStatus.FINISHED;
		}
		// If there are disconnected links process them one by one

		for (ExternalLink externalLink : externalLinks) {
			// Delete the externallink from VidyoManager for all records
			int status = -1;
			try {
				status = federationConferenceService
						.removeExternalLink(externalLink);
			} catch (Exception e) {
				logger.error("External Link not removed from the VidyoManager, this should not happen - " + externalLink);
			}

			// If deleted successfully, delete the entry from table
			if (status != 0) {
				logger.warn("External Link not removed from the VidyoManager, this should not happen - "
						+ externalLink);
			}

			logger.warn("IPC link is in disconnected state from host - "
					+ externalLink.getFromTenantHost()
					+ " for more than 5 minutes for conference "
					+ externalLink.getToConferenceName()
					+ ". Forcefully deleting it from the system");
			logger.warn("ExternalLink details - " + externalLink);

			// Delete the entry from table in any case
			federationConferenceService.deleteFederation(externalLink);

			// Check if anymore entry exists in ExternalLink table with same
			// toConferenceName
			int count = federationConferenceService
					.getExtLinksCountByToConfName(externalLink
							.getToConferenceName());
			if (logger.isDebugEnabled()) {
				logger.debug("Count of ExternalLinks with ConferenceName - "
						+ externalLink.getToConferenceName() + " is - " + count);
			}
			// if there are no external links, check the conference table or
			// else proceed with the next link
			if (count == 0) {
				Long participantsCount = federationConferenceService
						.getCountParticipants(externalLink
								.getToConferenceName());
				if (logger.isDebugEnabled()) {
					logger.debug("Count of Participants with ConferenceName - "
							+ externalLink.getToConferenceName() + " is - "
							+ participantsCount);
				}
				if (participantsCount == 0) {
					// If there are no participants in the conference,
					// delete the conf in VidyoManager
					try {
						status = federationConferenceService
								.deleteConfInVidyoManager(externalLink
										.getToConferenceName());
					} catch (Exception e) {
						logger.error("Conference not deleted in VidyoManager - "
								+ externalLink);
					}
					
					if(status == 0) {
						logger.warn("Conference - "
								+ externalLink.getToConferenceName()
								+ " is forcefully deleted from the VidyoManager");						
					}
					// It is possible that while the conference is getting
					// deleted, a user might join the same conference and get
					// call disconnected by operator
					if (status != 0) {
						logger.error("Conference not deleted in VidyoManager - "
								+ externalLink);
					}
				}
			}

		}

		return RepeatStatus.FINISHED;
	}

	/**
	 * Returns the list of ExternalLinks which are in disconnected state for
	 * last 5 minutes
	 * 
	 * @param minutes
	 * @return
	 */
	protected List<ExternalLink> getDisconnectedExternalLinks(int status,
			int timeLapsed) {
		return federationConferenceService.getDisconnectedExternalLinks(status,
				timeLapsed);
	}

}
