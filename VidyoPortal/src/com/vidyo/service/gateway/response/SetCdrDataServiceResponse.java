package com.vidyo.service.gateway.response;

import com.vidyo.framework.service.BaseServiceResponse;

public class SetCdrDataServiceResponse extends BaseGatewayServiceResponse {

	private int rowsUpdated;

	public int getRowsUpdated() {
		return rowsUpdated;
	}

	public void setRowsUpdated(int rowsUpdated) {
		this.rowsUpdated = rowsUpdated;
	}

}
