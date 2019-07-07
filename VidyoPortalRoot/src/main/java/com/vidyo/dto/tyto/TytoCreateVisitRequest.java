package com.vidyo.dto.tyto;

public class TytoCreateVisitRequest {

    private final String identifier;

    private final String clinicianIdentifier;

    private final String stationIdentifier;

    private final String clinicianRemoteAddress;

    private final String stationRemoteAddress;

    public TytoCreateVisitRequest(String identifier,
                                  String clinitianIdentifier,
                                  String stationIdentifier,
                                  String clinicianRemoteAddress,
                                  String stationRemoteAddress) {
        this.identifier = identifier;
        this.clinicianIdentifier = clinitianIdentifier;
        this.stationIdentifier = stationIdentifier;
        this.clinicianRemoteAddress = clinicianRemoteAddress;
        this.stationRemoteAddress = stationRemoteAddress;
    }

    public String getIdentifier() {return identifier; }

    public String getClinicianIdentifier() {return clinicianIdentifier;}

    public String getStationIdentifier() {return stationIdentifier;}

    public String getStationRemoteAddress() {return stationRemoteAddress;}

    public String getClinicianRemoteAddress() {
        return clinicianRemoteAddress;
    }

    @Override
    public String toString() {
        return "TytoCreateVisitRequest{" +
                "identifier='" + identifier + '\'' +
                ", clinicianIdentifier='" + clinicianIdentifier + '\'' +
                ", stationIdentifier='" + stationIdentifier + '\'' +
                ", clinicianRemoteAddress='" + clinicianRemoteAddress + '\'' +
                ", stationRemoteAddress='" + stationRemoteAddress + '\'' +
                '}';
    }
}
