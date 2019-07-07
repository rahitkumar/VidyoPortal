package com.vidyo.service.lecturemode.response;

import com.vidyo.bo.Entity;
import com.vidyo.service.conference.response.ConferenceControlResponse;

import java.util.ArrayList;
import java.util.List;

public class LectureModeParticipantsResponse extends ConferenceControlResponse {
	public static final int SEARCH_EXCEEDS_LIMIT = 8001;

	private boolean lectureMode = false;
	private int recorderID = 0;
	private String recorderName = null;
	private boolean webcast = false;
	private boolean paused = false;


	private int total = 0;
	private List<Entity> entities = new ArrayList<Entity>();

	public void setLectureMode(boolean flag) {
		this.lectureMode = flag;
	}

	public void setRecorderID(int endpointID) {
		this.recorderID = endpointID;
	}

	public void setRecorderName(String name) {
		this.recorderName = name;
	}

	public void setWebcast(boolean flag) {
		this.webcast = flag;
	}

	public void setPaused(boolean flag) {
		this.paused = flag;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public boolean isLectureMode() {
		return lectureMode;
	}

	public int getRecorderID() {
		return recorderID;
	}

	public String getRecorderName() {
		return recorderName;
	}

	public boolean isWebcast() {
		return webcast;
	}

	public boolean isPaused() {
		return paused;
	}

	public int getTotal() {
		return total;
	}

	public List<Entity> getEntities() {
		return entities;
	}

}
