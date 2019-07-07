package com.vidyo.service;

import com.vidyo.bo.Language;
import com.vidyo.bo.Network;

import java.util.HashMap;
import java.util.List;

public interface INetworkService {
    public Network getNetworkSettings();
    public HashMap<String, String> getSystemNetworkData();
}