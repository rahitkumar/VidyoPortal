/**
 * VidyoPortalUCClientsServiceSkeletonInterface.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.0  Built on : May 17, 2011 (04:19:43 IST)
 */
package com.vidyo.portal.ucclients;

/**
 * VidyoPortalUCClientsServiceSkeletonInterface java skeleton interface for the
 * axisService
 */
public interface VidyoPortalUCClientsServiceSkeletonInterface {

	/**
	 * Auto generated method signature
	 * 
	 * @param getUserStatusRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.GetUserStatusResponse getUserStatus(
			com.vidyo.portal.ucclients.GetUserStatusRequest getUserStatusRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException;

	/**
	 * Auto generated method signature
	 * 
	 * @param createMyRoomURLRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.CreateMyRoomURLResponse createMyRoomURL(
			com.vidyo.portal.ucclients.CreateMyRoomURLRequest createMyRoomURLRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException;

	/**
	 * Auto generated method signature
	 * 
	 * @param startMyConferenceRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws RoomDisabledFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.StartMyConferenceResponse startMyConference(
			com.vidyo.portal.ucclients.StartMyConferenceRequest startMyConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, RoomDisabledFaultException,
			SeatLicenseExpiredFaultException;

	/**
	 * Auto generated method signature get Browser Access Key
	 * 
	 * @param browserAccessKeyRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.BrowserAccessKeyResponse getBrowserAccessKey(
			com.vidyo.portal.ucclients.BrowserAccessKeyRequest browserAccessKeyRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException;

	/**
	 * Auto generated method signature
	 * 
	 * @param joinConferenceRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.JoinConferenceResponse joinConference(
			com.vidyo.portal.ucclients.JoinConferenceRequest joinConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException;

	/**
	 * Auto generated method signature
	 * 
	 * @param getUserDataRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.GetUserDataResponse getUserData(
			com.vidyo.portal.ucclients.GetUserDataRequest getUserDataRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException;

	/**
	 * Auto generated method signature
	 * 
	 * @param inviteToConferenceRequest
	 * @throws NotLicensedFaultException
	 *             :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 * @throws SeatLicenseExpiredFaultException
	 *             :
	 */

	public com.vidyo.portal.ucclients.InviteToConferenceResponse inviteToConference(
			com.vidyo.portal.ucclients.InviteToConferenceRequest inviteToConferenceRequest)
			throws NotLicensedFaultException, InvalidArgumentFaultException,
			GeneralFaultException, SeatLicenseExpiredFaultException;

}
