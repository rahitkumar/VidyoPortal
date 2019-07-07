/**
 * 
 */
package com.vidyo.service.license.response;

import com.vidyo.framework.service.BaseServiceResponse;


/**
 * @author Ganesh
 *
 */
public class LicenseResponse extends BaseServiceResponse{
	
	/**
	 * Status code for invalid license file whose signature cannot be verified
	 */
	public static final int INVALID_LICENSE_FILE = 16001;
	
	/**
	 * 
	 */
	private boolean consumesLine;

	/**
	 * @return the consumesLine
	 */
	public boolean isConsumesLine() {
		return consumesLine;
	}

	/**
	 * @param consumesLine the consumesLine to set
	 */
	public void setConsumesLine(boolean consumesLine) {
		this.consumesLine = consumesLine;
	}
	
}
