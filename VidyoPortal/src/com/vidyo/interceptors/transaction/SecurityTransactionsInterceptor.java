/**
 * 
 */
package com.vidyo.interceptors.transaction;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ITenantService;
import com.vidyo.service.transaction.TransactionService;

/**
 * @author ganesh
 *
 */
public class SecurityTransactionsInterceptor extends TransactionInterceptor {

	protected static final Logger logger = LoggerFactory
			.getLogger(SecurityTransactionsInterceptor.class);

	/**
	 * 
	 */
	private TransactionService transactionService;

	/**
	 * 
	 */
	private ITenantService tenantService;

	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		boolean exception = false;
		Object retVal = null;
		Exception ex = null;
		try {
			retVal = methodInvocation.proceed();
		} catch (Exception e) {
			exception = true;
			ex = e;
			logger.error("Error while performing operation "
					+ methodInvocation.getMethod().getName() + " Error - "
					+ e.getMessage());
		}
		switch (methodInvocation.getMethod().getName()) {
		case "generateCSR":
			handleGenerateCSR(methodInvocation, retVal);
			break;
		case "importRootCACert":
			handleRootCAImport(methodInvocation, retVal);
			break;
		case "importP7bCertificateBundle":
			handleCertBundleUpload(methodInvocation, retVal);
			break;
		case "importPfxCertificateBundle":
			handleCertBundleUpload(methodInvocation, retVal);
			break;
		case "importVidyoSecurityBundle":
			handleVidyoBundleUpload(methodInvocation, retVal);
			break;
		case "exportVidyoSecurityBundle":
			handleExportVidyoBundle(methodInvocation, retVal);
			break;
		case "setUseDefaultRootCerts":
			handleConfigureClientCACerts(methodInvocation, retVal);
			break;
		case "resetSecuritySettings":
			handleResetSecuritySettings(methodInvocation, retVal);
			break;
		case "setOCSPConfigProperties":
			handleOcspSettings(methodInvocation, retVal);
			break;
		default:
			break;
		}

		// Throw the exception, dont suppress it.
		if (exception) {
			throw ex;
		}
		return retVal;
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleGenerateCSR(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Generate CSR");
		transactionHistory.setTransactionParams("Generate CSR");
		if (retVal == null || !(retVal instanceof String)
				|| !((String) retVal).equalsIgnoreCase("OK")) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleRootCAImport(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Import Client CA Cert");
		StringBuilder transactionParams = new StringBuilder();
		for (Object obj : methodInvocation.getArguments()) {
			if (obj instanceof Boolean) {
				if ((Boolean) (obj)) {
					transactionParams.append("Append to Existing Cert" + ";");
				} else {
					transactionParams.append("Replacing Existing Cert" + ";");
				}
			} else {
				transactionParams.append(obj.toString() + ";");
			}

		}
		transactionHistory.setTransactionParams(transactionParams.toString());
		if (retVal == null || !(retVal instanceof Boolean)
				|| !((Boolean) retVal)) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleCertBundleUpload(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Import Certificate Bundle");
		StringBuilder transactionParams = new StringBuilder();
		// Ignore the password which is the second param
		if (methodInvocation.getArguments() != null
				&& methodInvocation.getArguments().length >= 1) {
			transactionParams.append(methodInvocation.getArguments()[0]);
		}
		transactionHistory.setTransactionParams(transactionParams.toString());
		if (retVal == null || !(retVal instanceof Boolean)
				|| !((Boolean) retVal)) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleExportVidyoBundle(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Export Security Bundle");
		StringBuilder transactionParams = new StringBuilder();
		if (retVal == null || !(retVal instanceof String)
				|| ((String) retVal).equalsIgnoreCase("")) {
			transactionHistory.setTransactionResult("FAILURE");
			transactionParams.append("No File Name Generated");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
			transactionParams.append(retVal);
		}
		transactionHistory.setTransactionParams(transactionParams.toString());
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleVidyoBundleUpload(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Import Certificate Bundle");
		StringBuilder transactionParams = new StringBuilder();
		for (Object obj : methodInvocation.getArguments()) {
			transactionParams.append(obj.toString() + ";");
		}
		transactionHistory.setTransactionParams(transactionParams.toString());
		if (retVal == null || !(retVal instanceof String)
				|| !((String) retVal).equalsIgnoreCase("")) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleConfigureClientCACerts(
			MethodInvocation methodInvocation, Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Configure Client CA Certs");
		StringBuilder transactionParams = new StringBuilder();
		transactionParams.append("Use Default Root Certs:");
		for (Object obj : methodInvocation.getArguments()) {
			transactionParams.append(obj.toString() + ";");
		}
		transactionHistory.setTransactionParams(transactionParams.toString());
		if (retVal == null || !(retVal instanceof Boolean)
				|| !((Boolean) retVal)) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleResetSecuritySettings(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Reset Security Settings");
		StringBuilder transactionParams = new StringBuilder();
		transactionParams.append("Reset Security Settings");
		transactionHistory.setTransactionParams(transactionParams.toString());
		if (retVal == null || !(retVal instanceof Boolean)
				|| !((Boolean) retVal)) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * 
	 * @param methodInvocation
	 * @param retVal
	 */
	private void handleOcspSettings(MethodInvocation methodInvocation,
			Object retVal) {
		TransactionHistory transactionHistory = new TransactionHistory();
		transactionHistory.setTransactionName("Save OCSP Settings");
		StringBuilder transactionParams = new StringBuilder();
		int i = 0;
		for (Object obj : methodInvocation.getArguments()) {
			if (obj instanceof Boolean) {
				if ((Boolean) (obj)) {
					if (i == 0) {
						transactionParams.append("OCSP Enabled:" + obj + ";");
					}
					if (i == 1) {
						transactionParams.append("Overirde OCSP Responder:"
								+ obj + ";");
					}
				}
			} else {
				transactionParams.append(obj.toString() + ";");
			}
			i++;
		}
		transactionHistory.setTransactionParams(transactionParams.toString());
		if (retVal == null || !(retVal instanceof Integer)
				|| ((Integer) retVal).intValue() != 0) {
			transactionHistory.setTransactionResult("FAILURE");
		} else {
			transactionHistory.setTransactionResult("SUCCESS");
		}
		try {
			transactionHistory.setTenantName(tenantService.getTenant(
					TenantContext.getTenantId()).getTenantName());
		} catch (Exception e) {
			logger.error("No Tenant with Tenant Id "
					+ TenantContext.getTenantId());
		}
		transactionService
				.addTransactionHistoryWithUserLookup(transactionHistory);
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

}
