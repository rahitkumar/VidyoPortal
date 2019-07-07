package com.vidyo.service;

import java.util.Map;

public interface PortalService {

	String getEncodedPortalFeatures();

	Map<PortalFeaturesEnum, Boolean> getPortalFeatures();

	enum PortalFeaturesEnum {

		Guest("G"),
		IPCoutbound("IPCO"),
		IPCinbound("IPCI"),
		ModeratorURL("Mod"),
		TlsTunneling("TLS"),
		LoginBanner("LB"),
		WelcomeBanner("WB"),
		EndpointPrivateChat("PC"),
		EndpointPublicChat("PubC"),
		CDR2_1("CDR"),
		EndpointDetails("EP"),
		HTMLChangePswd("CP"),
		RouterParticipantInformation("RPI"),
		BindAck("BA"),
		NeoDirectCall("NDC"),
		CreatePublicRoom("CPR"),
		OpusAudio("OA"),
		SDK220("220"),
		TestCall("TC"),
		TytoCare("TCR"),
		PersonalRoom("PR"),
		Tiles16("T16"),
		ScheduledRoom2("SR2"),
		ScheduledRoom("SR"),
		ThumbnailPhoto("TP"),
		ModeratedConference("MC");

		private String featureAbbreviatedName;

		private PortalFeaturesEnum(String featureAbbreviatedName) {
			this.featureAbbreviatedName = featureAbbreviatedName;
		}

		public String getFeatureAbbreviatedName() {
			return featureAbbreviatedName;
		}
	}

}
