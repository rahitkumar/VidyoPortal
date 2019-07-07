package com.vidyo.service.lecturemode.request;

import com.vidyo.bo.EntityFilter;

public class LectureModeParticipantsRequest extends LectureModeControlRequest {
	private EntityFilter entityFilter;

	public EntityFilter getEntityFilter() {
		return entityFilter;
	}

	public void setEntityFilter(EntityFilter entityFilter) {
		this.entityFilter = entityFilter;
	}
}
