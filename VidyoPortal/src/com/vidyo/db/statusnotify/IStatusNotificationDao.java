package com.vidyo.db.statusnotify;

import com.vidyo.bo.statusnotify.StatusNotificationInfo;

public interface IStatusNotificationDao {
    public StatusNotificationInfo getInfoForStatusNotification(String GUID);
}