/**
 * VidyoLicenseServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */
package com.vidyo.ws.license;

import java.io.IOException;
import java.util.Map;

import javax.activation.DataHandler;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.axis2.context.MessageContext;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.User;
import com.vidyo.framework.security.utils.VidyoHash;
import com.vidyo.service.EndpointUploadService;
import com.vidyo.service.ISystemService;
import com.vidyo.service.LicensingService;
import com.vidyo.service.IUserService;
import com.vidyo.service.endpoints.EndpointService;
import com.vidyo.service.endpoints.response.UpdateEndpointResponse;
import com.vidyo.service.license.response.LicenseResponse;

/**
 * VidyoLicenseServiceSkeleton java skeleton for the axisService
 */
public class VidyoLicenseServiceSkeleton implements VidyoLicenseServiceSkeletonInterface {

	/** Logger for this class and subclasses */
	protected final Logger logger = LoggerFactory.getLogger(VidyoLicenseServiceSkeleton.class.getName());

	private EndpointUploadService endpoint;
	private String upload_path;
	private IUserService user;

	private EndpointService endpointService;

	private LicensingService licensingService;
	
	private ISystemService system;

	public void setEndpoint(EndpointUploadService endpoint) {
		this.endpoint = endpoint;
	}

	/**
	 * @param licensingService
	 *            the licensingService to set
	 */
	public void setLicensingService(LicensingService licensingService) {
		this.licensingService = licensingService;
	}

	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path.trim();
	}

	public void setUser(IUserService user) {
		this.user = user;
	}

	public void setSystem(ISystemService system) {
		this.system = system;
	}

	/**
	 * @param endpointService
	 *            the endpointService to set
	 */
	public void setEndpointService(EndpointService endpointService) {
		this.endpointService = endpointService;
	}

	/**
	 * Auto generated method signature License Registration during installation process
	 * 
	 * @param registerLicenseRequest
	 *            :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 */
	public com.vidyo.ws.license.RegisterLicenseResponse registerLicense(
			com.vidyo.ws.license.RegisterLicenseRequest registerLicenseRequest) throws InvalidArgumentFaultException,
			GeneralFaultException {
		RegisterLicenseResponse resp = new RegisterLicenseResponse();
		User user = this.user.getLoginUser();

		String EID = registerLicenseRequest.getEID().getEID_type0();
		String ipAddress = registerLicenseRequest.getIPaddress();
		String hostname = registerLicenseRequest.getHostname();
		
		if (EID == null || EID.trim().isEmpty()) {
			InvalidArgumentFault argumentFault = new InvalidArgumentFault();
			argumentFault.setErrorMessage("Invalid EID");
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException();
			invalidArgumentFaultException.setFaultMessage(argumentFault);
			logger.error("Invalid EID request for Register License ipaddress {}, hostname {}", ipAddress, hostname);
			throw invalidArgumentFaultException;
		}

		// Check if the EID is already registered before checking the available license count
		if (this.user.isUserLicenseRegistered(EID)) {
			String LAC = VidyoHash.getRegisterLicenseResponse(user.getUsername(), EID);
			resp.setLicenseActivationCertificate(LAC);
			resp.setOutOfLicenses(false);
			return resp;
		}

		// If EID doesn't exist, check for number of licenses left
		int lic = this.user.getLicensedNumberOfInstallLeft();

		if (lic > 0) {
			this.user.registerLicenseForUser(user, EID, ipAddress, hostname);

			String LAC = VidyoHash.getRegisterLicenseResponse(user.getUsername(), EID);
			resp.setLicenseActivationCertificate(LAC);
			resp.setOutOfLicenses(false);

			// Checkpoint : Remaining balance of installs == 25, 15, 5, a
			// notification email will be sent out
			this.user.checkRemainingInstallsBalance(--lic);
		} else {
			resp.setLicenseActivationCertificate("");
			resp.setOutOfLicenses(true);
		}

		return resp;
	}

	/**
	 * Auto generated method signature Get the current active client version
	 * 
	 * @param clientVersionRequest
	 *            :
	 * @throws InvalidArgumentFaultException
	 *             :
	 * @throws GeneralFaultException
	 *             :
	 */
	public com.vidyo.ws.license.ClientVersionResponse getClientVersion(
			com.vidyo.ws.license.ClientVersionRequest clientVersionRequest) throws InvalidArgumentFaultException,
			GeneralFaultException {
		ClientVersionResponse resp = new ClientVersionResponse();

		MessageContext context = MessageContext.getCurrentMessageContext();

		String type = clientVersionRequest.getClientType().getValue();

		Map<String,String> clientVerMap = this.endpoint.getClientVersion(type, this.upload_path, context.getTo().getAddress());
		resp.setCurrentTag(clientVerMap.get("CurrentTag"));
		resp.setInstallerURI(clientVerMap.get("InstallerURI"));

		return resp;
	}

	@Override
	public UpdateLineConsumptionResponse updateLineConsumptionForEndpoint(
			UpdateLineConsumptionRequest updateLineConsumptionRequest) throws EndpointNotBoundFaultException,
			InvalidArgumentFaultException, EndpointOfflineFaultException, GeneralFaultException {

		User user = this.user.getLoginUser();

		// Not able to get the logged in User
		if (user == null) {
			GeneralFault generalFault = new GeneralFault();
			generalFault.setErrorMessage("User not logged in");
			GeneralFaultException generalFaultException = new GeneralFaultException();
			generalFaultException.setFaultMessage(generalFault);
			logger.error("Not able to get the logged in user");
			throw generalFaultException;
		}


		DataHandler dh = updateLineConsumptionRequest.getAttachmentRequest().getBinaryData();
		String data = null;
		try {
			data = IOUtils.toString(dh.getInputStream());
		} catch (IOException e) {
			logger.error("Error while converting stream to string", e);
		}

		UpdateLineConsumptionResponse updateLineConsumptionResponse = new UpdateLineConsumptionResponse();

		if (data == null) {
			// return error
			updateLineConsumptionResponse.setUpdated(false);
			return updateLineConsumptionResponse;
		}

		// Validate the license file
		// TODO - dummy implementation till the utility is available
		LicenseResponse licenseResponse = licensingService.validateLicenseFileSignature(data);

		if (licenseResponse.getStatus() != LicenseResponse.SUCCESS) {
			InvalidArgumentFault argumentFault = new InvalidArgumentFault();
			argumentFault.setErrorMessage("Invalid License File");
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException();
			invalidArgumentFaultException.setFaultMessage(argumentFault);
			throw invalidArgumentFaultException;
		}

		// License file signature has been validated and verified
		UpdateEndpointResponse updateEndpointResponse = endpointService.updateLineConsumption(
				updateLineConsumptionRequest.getEID().getEID_type0(), user.getMemberID(),
				licenseResponse.isConsumesLine());

		if (updateEndpointResponse.getStatus() == UpdateEndpointResponse.ENDPOINT_NOT_BOUND) {
			EndpointNotBoundFault endpointNotBoundFault = new EndpointNotBoundFault();
			endpointNotBoundFault.setErrorMessage(updateEndpointResponse.getMessageId());
			EndpointNotBoundFaultException notBoundFaultException = new EndpointNotBoundFaultException();
			notBoundFaultException.setFaultMessage(endpointNotBoundFault);
			throw notBoundFaultException;
		}

		if (updateEndpointResponse.getStatus() == UpdateEndpointResponse.ENDPOINT_OFFLINE) {
			EndpointOfflineFault endpointOfflineFault = new EndpointOfflineFault();
			endpointOfflineFault.setErrorMessage(updateEndpointResponse.getMessageId());
			EndpointOfflineFaultException endpointOfflineFaultException = new EndpointOfflineFaultException();
			endpointOfflineFaultException.setFaultMessage(endpointOfflineFault);
			throw endpointOfflineFaultException;
		}

		if (updateEndpointResponse.getStatus() == UpdateEndpointResponse.UPDATE_LINE_CONSUMPTION_FAILED) {
			InvalidArgumentFault argumentFault = new InvalidArgumentFault();
			argumentFault.setErrorMessage(updateEndpointResponse.getMessageId());
			InvalidArgumentFaultException invalidArgumentFaultException = new InvalidArgumentFaultException();
			invalidArgumentFaultException.setFaultMessage(argumentFault);
			throw invalidArgumentFaultException;
		}

		updateLineConsumptionResponse.setUpdated(true);

		return updateLineConsumptionResponse;
	}

}