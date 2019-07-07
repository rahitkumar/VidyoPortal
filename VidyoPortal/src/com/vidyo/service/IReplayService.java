package com.vidyo.service;

import com.vidyo.bo.*;
import com.vidyo.recordings.webcast.GeneralFaultException;
import com.vidyo.recordings.webcast.InvalidArgumentFaultException;
import com.vidyo.service.exceptions.NoVidyoReplayException;

import java.rmi.RemoteException;

public interface IReplayService {
    void getWebCastURLandPIN(Entity entity) throws GeneralFaultException, InvalidArgumentFaultException, RemoteException;
    void getWebCastURLandPIN(Room room) throws GeneralFaultException, InvalidArgumentFaultException, RemoteException;
    void generateWebCastURL(Room room) throws RemoteException, NoVidyoReplayException, GeneralFaultException, InvalidArgumentFaultException;
    void deleteWebCastURL(Room room) throws RemoteException, NoVidyoReplayException, GeneralFaultException, InvalidArgumentFaultException;
    void updateWebCastPIN(Room room, String pin) throws RemoteException, NoVidyoReplayException, GeneralFaultException, InvalidArgumentFaultException;
}