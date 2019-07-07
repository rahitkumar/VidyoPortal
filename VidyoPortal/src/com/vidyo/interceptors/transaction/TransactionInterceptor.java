package com.vidyo.interceptors.transaction;

import java.util.Calendar;
import java.util.Map;

import com.vidyo.framework.context.TenantContext;

import com.vidyo.bo.authentication.Authentication;
import com.vidyo.bo.authentication.AuthenticationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import com.vidyo.bo.EndpointBehavior;
import com.vidyo.bo.EndpointUpload;
import com.vidyo.bo.Group;
import com.vidyo.bo.Member;
import com.vidyo.bo.Room;
import com.vidyo.bo.Service;
import com.vidyo.bo.Tenant;
import com.vidyo.bo.User;
import com.vidyo.bo.transaction.TransactionHistory;
import com.vidyo.service.IGroupService;
import com.vidyo.service.IMemberService;
import com.vidyo.service.IRoomService;
import com.vidyo.service.IServiceService;
import com.vidyo.service.ITenantService;
import com.vidyo.service.IUserService;
import com.vidyo.framework.security.authentication.VidyoUserDetails;
import com.vidyo.service.group.request.DeleteGroupRequest;
import com.vidyo.service.transaction.TransactionService;

public class TransactionInterceptor implements MethodInterceptor {

	/**
	 * Logger
	 */
	/** Logger for this class and subclasses */
	protected final static Logger logger = LoggerFactory.getLogger(TransactionInterceptor.class.getName());

	/**
	 * 
	 */
	private TransactionService transactionService;

	/**
	 * 
	 */
	private IUserService userService;

	/**
	 * 
	 */
	private IMemberService memberService;

	/**
	 * 
	 */
	private IGroupService groupService;

	/**
	 * 
	 */
	private Map<String, String> methodNameTransactionMap;

	/**
	 * 
	 */
	private ITenantService tenantService;

	/**
	 * 
	 */
	private IRoomService roomService;

	/**
	 * 
	 */
	private IServiceService serviceService;

	/**
	 * @param groupService
	 *            the groupService to set
	 */
	public void setGroupService(IGroupService groupService) {
		this.groupService = groupService;
	}

	/**
	 * @param memberService
	 *            the memberService to set
	 */
	public void setMemberService(IMemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @param transactionService
	 *            the transactionService to set
	 */
	public void setTransactionService(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	/**
	 * @param methodNameTransactionMap
	 *            the methodNameTransactionMap to set
	 */
	public void setMethodNameTransactionMap(
			Map<String, String> methodNameTransactionMap) {
		this.methodNameTransactionMap = methodNameTransactionMap;
	}

	/**
	 * @param tenantService
	 *            the tenantService to set
	 */
	public void setTenantService(ITenantService tenantService) {
		this.tenantService = tenantService;
	}

	/**
	 * @param roomService
	 *            the roomService to set
	 */
	public void setRoomService(IRoomService roomService) {
		this.roomService = roomService;
	}

	/**
	 * @param serviceService
	 *            the serviceService to set
	 */
	public void setServiceService(IServiceService serviceService) {
		this.serviceService = serviceService;
	}
	
	/**
	 * 
	 */
	@Override
	public Object invoke(MethodInvocation methodInvocation) throws Throwable {
		logger.debug("Entering invoke() of TransactionInterceptor");
		Object retVal = null;
		boolean operSuccess = true;
		String transactionParams = null;

		if (methodInvocation.getMethod().getName().contains("delete")) {
			transactionParams = createTransactionParams(methodInvocation
					.getMethod().getName(), methodInvocation.getArguments(),
					retVal);
		}

		if (methodInvocation.getMethod().getName()
				.equalsIgnoreCase("upgradeSystem")) {
			handleSystemShutdown(methodInvocation.getMethod().getName(), methodInvocation.getArguments());
		}

		try {
			retVal = methodInvocation.proceed();
		} catch (Exception ex) {
			operSuccess = false;
			logger.error("Exception while doing operation -"
					+ methodInvocation.getMethod().getName(), ex);
		}

		TransactionHistory transactionHistory = new TransactionHistory();

		if (!methodInvocation.getMethod().getName().contains("delete")) {
			transactionParams = createTransactionParams(methodInvocation
					.getMethod().getName(), methodInvocation.getArguments(),
					retVal);
		}
		User user = userService.getLoginUser();
		if (user != null && user.getUsername() != null) {
			transactionHistory.setUserID(user.getUsername());
		} else {
			transactionHistory.setUserID("self");
		}
		transactionHistory.setTransactionResult("SUCCESS");
		if (!operSuccess) {
			transactionHistory.setTransactionResult("FAILURE");
		}

		String result = determineSuccessOrFailure(methodInvocation.getMethod()
				.getName(), retVal, transactionHistory.getTransactionResult());
		transactionHistory.setTransactionResult(result);
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication()
						.getDetails() instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) SecurityContextHolder
					.getContext().getAuthentication().getDetails();
			transactionHistory.setSourceIP(webAuthenticationDetails
					.getRemoteAddress());
		} else if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().getDetails() instanceof VidyoUserDetails) {

			VidyoUserDetails vidyoUserDetails = (VidyoUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
			transactionHistory.setSourceIP(vidyoUserDetails.getSourceIP());
		}
		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		transactionHistory.setTenantName(tenant != null ? tenant
				.getTenantName() : "Unknown");
		// Handle Enable/Disable NE Configuration Special case
		if (methodInvocation.getMethod().getName()
				.equalsIgnoreCase("enableNEConfiguration")) {
			for (Object obj : methodInvocation.getArguments()) {
				if (obj instanceof Boolean) {
					if ((Boolean) obj) {
						transactionHistory
								.setTransactionName("Component Enabled");
					} else {
						transactionHistory
								.setTransactionName("Component Disabled");
					}

				}
			}
		} else {
			transactionHistory
					.setTransactionName(methodNameTransactionMap
							.get(methodInvocation.getMethod().getName()) == null ? methodInvocation
							.getMethod().getName() : methodNameTransactionMap
							.get(methodInvocation.getMethod().getName()));
		}
		transactionHistory.setTransactionTime(Calendar.getInstance().getTime());
		if (transactionParams.length() == 0) {
			transactionParams = methodInvocation.getMethod().getName();
		}
		transactionHistory.setTransactionParams(transactionParams);
		transactionService.addTransactionHistory(transactionHistory);
		logger.debug("Exiting invoke() of TransactionInterceptor");
		return retVal;
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private String createTransactionParams(String methodName, Object[] args,
			Object retVal) {

		if (methodName.contains("Member") || methodName.contains("Legacy")) {
			return createMemberTransactionParams(methodName, args, retVal);
		} else if (methodName.contains("Room")) {
			return createRoomTransactionParams(methodName, args, retVal);
		} else if (methodName.contains("Group")) {
			return createGroupTransactionParams(methodName, args, retVal);
		} else if (methodName.contains("EndpointBehavior")){
			return createEndpointBehaviorParams(methodName, args, retVal);
		} else if (methodName.contains("Endpoint")) {
			return createEndpointUploadParams(methodName, args, retVal);
		} else if (methodName.contains("saveAuthenticationConfig")) {
			return createSaveLdapTransactionParams(methodName, args, retVal);
		} else if (methodName.contains("Tenant")) {
			return createTenantTransactionParams(methodName, args, retVal);
		} else if (methodName.contains("enableHttpHttps")
				|| methodName.contains("disableHttpHttps")) {
			return createPortTransactionParams(methodName, args, retVal);
		} else if (methodName.contains("VG")) {
			return createVGTransactionParams(methodName, args, retVal);
		} else {
			return createDefaultTransactionParams(methodName, args, retVal);
		}
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	protected String createMemberTransactionParams(String methodName,
			Object[] args, Object retVal) {
		if (methodName.equalsIgnoreCase("insertMember")
				|| methodName.equalsIgnoreCase("insertSuper")
				|| methodName.equalsIgnoreCase("updateSuper")
				|| methodName.equalsIgnoreCase("updateMember")
				|| methodName.equalsIgnoreCase("addLegacy")
				|| methodName.equalsIgnoreCase("updateLegacy")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof Member) {
					Group group = null;
					String locationTag = "Unknown";
					try {
						if(((Member) obj).getLocationID() > 0) {
							locationTag = memberService
									.getLocationTag(((Member) obj).getLocationID());
						}
						if(((Member) obj).getGroupID() > 0) {
							group = groupService.getGroup(((Member) obj)
									.getGroupID());
						}
					} catch (Exception e) {
						logger.error("Error while getting Group/Location Tag ->"
								+ e.getMessage());
					}

					String groupName = "Unknown";
					if (group != null) {
						groupName = group.getGroupName();
					}
					if (locationTag != null) {
						((Member) obj).setLocationTag(locationTag);
					}
					((Member) obj).setGroupName(groupName);
					transactionParams.append(new ReflectionToStringBuilder(obj,
							ToStringStyle.SHORT_PREFIX_STYLE)
							.setExcludeFieldNames(
									new String[] { "memberID", "roleID",
											"tenantID", "groupID", "langID",
											"profileID", "password", "active",
											"allowedToParticipate",
											"memberCreated", "roomID",
											"roomTypeID", "endpointGUID",
											"defaultEndpointGUID",
											"tenantPrefix", "proxyID",
											"dialIn", "locationID", "modeID",
											"roomType", "roomName", "location",
											"endpointId", "importedUsed",
											"bakCreationTime",
											"loginFailureCount" }).toString());

				}
			}
			return transactionParams.toString();
		}

		if (methodName.equalsIgnoreCase("deleteMember")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof Integer) {
					Member member = memberService.getMember((Integer) obj);
					if (member == null) {
						logger.error("Member Not Found for memberId = " + obj);
					}
					transactionParams.append("MemberID=" + obj);
					if (member != null) {
						transactionParams.append(";MemberName="
								+ member.getMemberName());
					}
				}

			}
			return transactionParams.toString();
		}

		return "Default";

	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private String createRoomTransactionParams(String methodName,
			Object[] args, Object retVal) {
		if (methodName.equalsIgnoreCase("insertRoom")
				|| methodName.equalsIgnoreCase("updateRoom")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof Room) {
					Group group = null;;
					try {
						group = groupService.getGroup(((Room) obj)
								.getGroupID());
					} catch (Exception e) {
						
					}
					String groupName = "Unknown";
					if(group != null) {
						groupName = group.getGroupName(); 
					}
					((Room) obj).setGroupName(groupName);
					String memberName = "Unknown";
					Member member = memberService.getMember(((Room) obj).getMemberID());
					if(member != null) {
						memberName = member.getMemberName();
					}
					((Room) obj).setOwnerName(memberName);
					transactionParams.append(new ReflectionToStringBuilder(obj,
							ToStringStyle.SHORT_PREFIX_STYLE)
							.setExcludeFieldNames(
									new String[] { "roomID", "roomTypeID",
											"memberID", "groupID", "roomMuted",
											"tenantID", "tenantPrefix",
											"tenantName", "displayName",
											"numParticipants", "dialIn",
											"allowRecording", "webCastURL",
											"webCastPIN", "webCastPinned",
											"allowWebcasting", "roomType" })
							.toString());

				}
			}
			return transactionParams.toString();
		}

		if (methodName.equalsIgnoreCase("deleteRoom")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof Integer) {
					transactionParams.append("RoomID = " + obj);
					Room room = null;
					try {
						room = roomService.getRoom((Integer) obj);
					} catch (Exception e) {
						logger.error("Room Not Found", obj);
					}
					if (room != null) {
						transactionParams.append(";RoomName="
								+ room.getRoomName());
					}
				}
			}
			return transactionParams.toString();
		}

		return methodName;

	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @param retVal
	 * @return
	 */
	private String createGroupTransactionParams(String methodName,
			Object[] args, Object retVal) {
		if (methodName.equalsIgnoreCase("insertGroup")
				|| methodName.equalsIgnoreCase("updateGroup")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof Group) {
					transactionParams
							.append(new ReflectionToStringBuilder(obj,
									ToStringStyle.SHORT_PREFIX_STYLE)
									.setExcludeFieldNames(new String[] {
											"tenantID", "routerID",
											"secondaryRouterID", "defaultFlag",
											"routerName", "routerActive",
											"secondaryRouterName",
											"secondaryRouterActive",
											"allowRecording" }));

				}
			}
			return transactionParams.toString();
		}

		if (methodName.equalsIgnoreCase("deleteGroup")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof DeleteGroupRequest) {
					transactionParams.append("GroupID = " + ((DeleteGroupRequest) obj).getGroupId());
					Group group = null;
					try {
						group = groupService.getGroup(((DeleteGroupRequest) obj).getGroupId());
					} catch (Exception e) {
						logger.error("Group Not Found", obj);
					}
					if (group != null) {
						transactionParams.append(";GroupName="
								+ group.getGroupName());
					}
				}
			}
			return transactionParams.toString();
		}

		return methodName + "-Error";
	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @param retVal
	 * @return
	 */
	private String createEndpointUploadParams(String methodName, Object[] args,
			Object retVal) {
		if (methodName.equalsIgnoreCase("insertEndpointUpload")
				|| methodName.equalsIgnoreCase("insertEndpointUploadBySuper")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof EndpointUpload) {
					transactionParams
							.append(new ReflectionToStringBuilder(obj,
									ToStringStyle.SHORT_PREFIX_STYLE)
									.setExcludeFieldNames(new String[] {
											"tenantID", "routerID",
											"secondaryRouterID", "defaultFlag",
											"routerName", "routerActive",
											"secondaryRouterName",
											"secondaryRouterActive",
											"allowRecording" }));

				}
			}
			return transactionParams.toString();
		}
		return methodName;
	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @param retVal
	 * @return
	 */
	private String createEndpointBehaviorParams(String methodName,
			Object[] args, Object retVal) {
		StringBuffer transactionParams = new StringBuffer();
		for (Object obj : args) {
			if (obj instanceof String) {
				transactionParams.append("EndpointBehaviorKey="+obj + ";");
			} else if (obj instanceof Integer) {
				transactionParams.append("tenantID="+obj + ";");
			} else if (obj instanceof EndpointBehavior) {
				transactionParams.append(obj.toString() + ";");
			} else {
				transactionParams.append(new ReflectionToStringBuilder(obj,
						ToStringStyle.SHORT_PREFIX_STYLE)
						.setExcludeFieldNames(new String[] { "password" }));
			}
		}
		return transactionParams.toString();
	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @param retVal
	 * @return
	 */
	private String createDefaultTransactionParams(String methodName,
			Object[] args, Object retVal) {
		StringBuffer transactionParams = new StringBuffer();
		for (Object obj : args) {
			if (obj instanceof String) {
				transactionParams.append(obj + ";");
			} else {
				transactionParams.append(new ReflectionToStringBuilder(obj,
						ToStringStyle.SHORT_PREFIX_STYLE)
						.setExcludeFieldNames(new String[] { "password" }));
			}
		}
		return transactionParams.toString();
	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @param retVal
	 * @return
	 */
	private String createSaveLdapTransactionParams(String methodName,
			Object[] args, Object retVal) {
		StringBuffer transactionParams = new StringBuffer();
		for (Object obj : args) {
			if (obj instanceof AuthenticationConfig
					&& ((AuthenticationConfig) obj).isLdapflag()) {
				transactionParams.append(new ReflectionToStringBuilder(obj,
						ToStringStyle.SHORT_PREFIX_STYLE)
						.setExcludeFieldNames(new String[] { "password",
								"wspassword", "ldappassword" }));
			} else if (obj instanceof Authentication
					&& !((AuthenticationConfig) obj).isLdapflag()
					&& ((AuthenticationConfig) obj).getAuthFor() != null) {
				String authFor = ((AuthenticationConfig) obj)
						.getAuthFor();
				transactionParams.append("Roles=" + authFor);
			}
		}
		return transactionParams.toString();
	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private String createTenantTransactionParams(String methodName,
			Object[] args, Object retVal) {
		if (methodName.equalsIgnoreCase("insertTenant")
				|| methodName.equalsIgnoreCase("updateTenant")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof Tenant) {
					transactionParams
							.append(new ReflectionToStringBuilder(obj,
									ToStringStyle.SHORT_PREFIX_STYLE)
									.setExcludeFieldNames(
											new String[] { "memberID",
													"roleID", "tenantID",
													"groupID", "langID",
													"profileID", "password",
													"active",
													"allowedToParticipate",
													"memberCreated", "roomID",
													"roomTypeID",
													"endpointGUID",
													"defaultEndpointGUID",
													"tenantPrefix", "proxyID",
													"dialIn", "locationID",
													"modeID", "roomType",
													"roomName", "location" })
									.toString());

				}
			}
			return transactionParams.toString();
		}

		if (methodName.equalsIgnoreCase("deleteTenant")) {
			StringBuffer transactionParams = new StringBuffer();

			for (Object obj : args) {
				transactionParams.append("TenantID = " + obj);
				Tenant tenant = null;
				try {
					tenant = tenantService.getTenant((Integer) obj);
				} catch (Exception e) {
					logger.error("Tenant Not Found", obj);
				}
				if (tenant != null) {
					transactionParams.append(";TenantName="
							+ tenant.getTenantName());
				}
			}
			return transactionParams.toString();
		}

		return methodName;

	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @param retVal
	 * @return
	 */
	private String createPortTransactionParams(String methodName,
			Object[] args, Object retVal) {
		StringBuffer transactionParams = new StringBuffer();
		for (Object obj : args) {
			transactionParams.append(new ReflectionToStringBuilder(obj,
					ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
					new String[] { "memberID", "roleID", "tenantID", "groupID",
							"langID", "profileID", "password", "active",
							"allowedToParticipate", "memberCreated", "roomID",
							"roomTypeID", "endpointGUID",
							"defaultEndpointGUID", "tenantPrefix", "proxyID",
							"dialIn", "locationID", "modeID", "roomType",
							"roomName", "location" }).toString());

		}
		return transactionParams.toString();
	}

	/**
	 * 
	 * @param methodName
	 * @param retVal
	 * @param currValue
	 * @return
	 */
	private String determineSuccessOrFailure(String methodName, Object retVal,
			String currValue) {
		if ((methodName.equalsIgnoreCase("enableHttpHttps")
				|| methodName.equalsIgnoreCase("disableHttpHttps") || methodName
				.equalsIgnoreCase("applyCSR"))
				&& retVal instanceof String
				&& !(((String) retVal).equalsIgnoreCase("true") || ((String) retVal)
						.equalsIgnoreCase("success"))) {
			return "FAILURE";
		} else if ((methodName.equalsIgnoreCase("clearCSR")
				|| methodName.equalsIgnoreCase("backupDatabase")
				|| methodName.equals("uploadDatabase")
				|| methodName.equals("downloadDatabase")
				|| methodName.equalsIgnoreCase("activateNetworkConfig")
				|| methodName.equalsIgnoreCase("enableNEConfiguration")
				|| methodName.equalsIgnoreCase("setSystemLicense")
				|| methodName.equalsIgnoreCase("setVMLicense")
				|| methodName.equalsIgnoreCase("deleteEndpointBehavior")
				|| methodName.equalsIgnoreCase("deleteEndpointBehaviorByTenantID")
				|| methodName.equalsIgnoreCase("restoreDatabaseWithBackupFile") || methodName
				.equalsIgnoreCase("generateCSR"))
				&& retVal instanceof Boolean
				&& !((Boolean) retVal)) {
			if (logger.isDebugEnabled()) {
				logger.debug("retVal ->" + retVal + " methdoName->"
						+ methodName);
			}
			return "FAILURE";
		}
		return currValue;
	}

	/**
	 * 
	 * @param methodName
	 * @param args
	 * @return
	 */
	public void handleSystemShutdown(String methodName, Object[] args) {
		TransactionHistory transactionHistory = new TransactionHistory();

		User user = userService.getLoginUser();
		transactionHistory.setUserID(user.getUsername());
		transactionHistory.setTransactionResult("SUCCESS");
		if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication()
						.getDetails() instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) SecurityContextHolder
					.getContext().getAuthentication().getDetails();
			transactionHistory.setSourceIP(webAuthenticationDetails
					.getRemoteAddress());
		} else if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication()
				.getDetails() instanceof VidyoUserDetails) {
			
			VidyoUserDetails vidyoUserDetails = (VidyoUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
			transactionHistory.setSourceIP(vidyoUserDetails.getSourceIP());
		}
		Tenant tenant = tenantService.getTenant(TenantContext.getTenantId());
		transactionHistory.setTenantName(tenant != null ? tenant
				.getTenantName() : "Unknown");
		transactionHistory.setTransactionName(methodNameTransactionMap
				.get(methodName) == null ? methodName
				: methodNameTransactionMap.get(methodName));
		transactionHistory.setTransactionTime(Calendar.getInstance().getTime());
		if (methodName.equalsIgnoreCase("shutdownSystem")) {
			transactionHistory.setTransactionParams("System Shutdown");
		} else if (methodName.equalsIgnoreCase("rebootSystem")) {
			transactionHistory.setTransactionParams("System Reboot");
		} else if (methodName.equalsIgnoreCase("restartWebServer")) {
			transactionHistory.setTransactionParams("Restart Webserver");
		} else if (methodName.equalsIgnoreCase("upgradeSystem")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				if (obj instanceof String) {
					transactionParams.append(obj + ";");
				} else {
					transactionParams.append(ToStringBuilder
							.reflectionToString(obj,
									ToStringStyle.SHORT_PREFIX_STYLE));
				}
			}
			transactionHistory.setTransactionParams(transactionParams
					.toString());
		} else {
			transactionHistory.setTransactionParams("System Reboot/Shutdown");
		}
		transactionService.addTransactionHistory(transactionHistory);

	}

	/**
	 * 
	 * @param args
	 * @return
	 */
	private String createVGTransactionParams(String methodName, Object[] args,
			Object retVal) {

		if (methodName.equalsIgnoreCase("deleteVG")) {
			StringBuffer transactionParams = new StringBuffer();

			for (Object obj : args) {
				transactionParams.append("GatewayID = " + obj);
				Service service = null;
				if (obj instanceof Integer) {
					try {
						service = serviceService.getVG((Integer) obj);
					} catch (Exception e) {
						logger.error("Gateway Not Found", obj);
					}
					if (service != null) {
						transactionParams.append(";GatewayName="
								+ service.getServiceName());
					}

				}
			}
			return transactionParams.toString();
		}

		if (methodName.equalsIgnoreCase("insertVG")
				|| methodName.equalsIgnoreCase("updateVG")) {
			StringBuffer transactionParams = new StringBuffer();
			for (Object obj : args) {
				transactionParams.append(new ReflectionToStringBuilder(obj,
						ToStringStyle.SHORT_PREFIX_STYLE).setExcludeFieldNames(
						new String[] { "password" }).toString());

			}
			return transactionParams.toString();
		}

		return methodName;

	}

}
