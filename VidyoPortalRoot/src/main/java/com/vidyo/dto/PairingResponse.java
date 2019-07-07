package com.vidyo.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class PairingResponse   {

  private  String pairingCode;

  private String applicationServerUrl;

  @JsonCreator
  public PairingResponse(@JsonProperty("pairingCode") String pairingCode,
                         @JsonProperty("applicationServerUrl") String applicationServerUrl) {
    this.pairingCode = pairingCode;
    this.applicationServerUrl = applicationServerUrl;
  }

  @JsonProperty
  public String getPairingCode() {
    return pairingCode;
  }

  @JsonProperty
  public String getApplicationServerUrl() {
    return applicationServerUrl;
  }
}

