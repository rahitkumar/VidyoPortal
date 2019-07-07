package com.vidyo.utils.room;

import com.vidyo.bo.Configuration;
import com.vidyo.bo.Room;
import com.vidyo.bo.Tenant;
import com.vidyo.framework.context.TenantContext;
import com.vidyo.service.ISystemService;
import com.vidyo.service.ITenantService;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

public class RoomUtils {

	protected static final Logger logger = LoggerFactory.getLogger(RoomUtils.class.getName());
	
	/**
	 * Validates if the extension starts with the tenant prefix if the prefix is not empty.
	 * 
	 * @param extension
	 *            the room extension
	 * @param tenantPrefix
	 *            the Tenant Prefix
	 * @return <code>true</code> if the extension starts with prefix or if the prefix is empty; <code>false</code>
	 *         otherwise
	 */
	public static boolean isValidExtension(String extension, String tenantPrefix) {
		return (tenantPrefix == null) || extension.startsWith(tenantPrefix);
	}
	
	/**
	 * Validates if the extension starts with the tenant prefix. Returns <code>true</code> if the prefix is
	 * <code>null</code> or empty.
	 * 
	 * @param tenantService
	 * @param extension
	 * @return
	 */
	public static boolean isValidExtensionByPrefix(ITenantService tenantService, String extension) {
		Tenant tenant = null;
		// If prefix is available, check the Tenant setting
		MessageContext messageContext = MessageContext.getCurrentMessageContext();
		// Getting HttpServletRequest from Message Context
		Object requestProperty = messageContext.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		if (requestProperty != null && requestProperty instanceof HttpServletRequest) {
			Integer tenantId  = TenantContext.getTenantId();
			if (tenantId != null) {
				tenant = tenantService.getTenant(tenantId);
			}
		}

		// Check if the extension starts with Tenant prefix
		String prefix = null;
		if (tenant != null) {
			prefix = tenant.getTenantPrefix();
		}
		boolean isValid = isValidExtension(extension, prefix);
		return isValid;
	}
	
    public static String buildSearchQuery(String... inputQuery) {
		String queryInput = "";
    	for(String searchTerm : inputQuery) {
    		if(!searchTerm.trim().isEmpty()) {
        		String searchTermTrimmed = searchTerm.trim().replaceAll("\\b\\s{2,}\\b", " ");
        		char[] chArr = searchTermTrimmed.toCharArray();
        		//Special character filter: any character that is not a letter, number, hyphen or underscore becomes a space (note that this must account for all languages)
        		for(int i = 0; i <  chArr.length; i++) {
        			if(!(Character.isLetter(Character.codePointAt(chArr, i)) || Character.isDigit(Character.codePointAt(chArr, i)) || 
        					chArr[i] == '_' || (""+chArr[i]).matches("\\p{M}"))) {
        				chArr[i] = ' ';
        			} 
        		}
        		String charProcessedQuery = String.valueOf(chArr);
        		// Adding changes for not allowing space in first word
        		int i=0;
        		for(String queryInputStr: charProcessedQuery.split(" ")) {
        			if(!StringUtils.isEmpty(queryInputStr)) {
            			if (i == 0){
            				// Execute this only for first word
            				queryInput = "+"+queryInputStr+"*";
            				i++;
            				continue;
            			}
        				queryInput += " +" + queryInputStr + "*";	
        			}			
        		}
        		// Commenting out as it is causing full text search slowness issue.
        		// Handle "hyphen" as part of the word and enclose it with double quotes
        		/*if(queryInput.contains("-") || queryInput.contains(".")) {
        			queryInput = '"' + queryInput + '"';
        		}*/
    		}
    	}
		return queryInput;
    }
    
    /**
     * Matches the extension's first 3 digits in schedule room prefix to confirm the possible clash. 
     * @param prefixToCheck
     * @param systemService
     * 
     * @return
     * 0 : Invalid input string
     * 1 : Prefix exists and should not be used
     * -1: Prefix does not exists and extension can be created.
     */
    public static int checkIfPrefixExistsInScheduleRoom(ISystemService systemService, String prefixToCheck){
    	Configuration scheduledRoomconfig = systemService.getConfiguration("SCHEDULED_ROOM_PREFIX");
    	if (prefixToCheck == null 
    			|| prefixToCheck.isEmpty()){
    		return 0;
    	}
    	if (scheduledRoomconfig != null ) {
    			if (prefixToCheck.length() >= scheduledRoomconfig.getConfigurationValue().length()
    			&& scheduledRoomconfig.getConfigurationValue().equals(prefixToCheck.substring(0, 
    					scheduledRoomconfig.getConfigurationValue().length()))) {
    				return 1;
    			} else if (scheduledRoomconfig
    					.getConfigurationValue().length() >= prefixToCheck.length()
    	    			&& prefixToCheck.equals(scheduledRoomconfig
    	    					.getConfigurationValue().substring(0, prefixToCheck.length()))) {
    				return 1;
    			}
    	}
    	return -1;
    }

    /**
     * Validate the given extension as a valid alpha numeric value whose length should between 1 - 64 characters.
     * @param extension
     * @return
     */
    public static boolean isNotValidAlphaNumericExtension(String extension) {
    	return StringUtils.isBlank(extension) || !extension.matches("[A-Za-z0-9]+") || extension.length() > 64;
    }
    
    /**
     * Validate the given extension as a valid numeric value whose length should between 1 - 64 characters.
     * @param extension
     * @return
     */
    public static boolean isNotValidNumericExtension(String extension) {
    	return StringUtils.isBlank(extension) || !extension.matches("[0-9]+") || extension.length() > 64;
    }
    
    public static boolean canMemberAccessRoom(int memberID, String userRole, Room room, String moderatorPIN) {
        boolean result = false;
        
        //Step 1 Check the TenantContext TenantId and the Room's tenantId
        if(TenantContext.getTenantId() != room.getTenantID()) {
			logger.error("Trying to access Unauthorized room - {}", room.getRoomName() + " roomId " + room.getRoomID()
					+ " by Tenant Id " + TenantContext.getTenantId());
        	return result;
        }
        
        // step 2 - user is an owner of room
        if (room.getMemberID() == memberID) {
            result = true;
        } else {
            // step 2 - user has an admin or operator role
            if ("ROLE_ADMIN".equalsIgnoreCase(userRole) || "ROLE_OPERATOR".equalsIgnoreCase(userRole)) {
                result = true;
            } else {
                // step 3 - user knows moderator PIN and he is a participant
                String roomModeratorPIN = room.getRoomModeratorPIN() != null ? room.getRoomModeratorPIN(): "";
                if ("".equals(roomModeratorPIN)) {
                    result = false;
                } else if (roomModeratorPIN.equalsIgnoreCase(moderatorPIN)) {
                    result = true;
                }
            }
        }
        
        return result;
    }
}
