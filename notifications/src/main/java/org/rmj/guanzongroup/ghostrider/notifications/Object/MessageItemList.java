/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Object;

public class MessageItemList {

    private final String Name;
    private final String Message;
    private final String DateTime;
    private final String Status;

    public MessageItemList(String name, String message, String dateTime, String status) {
        Name = name;
        Message = message;
        DateTime = dateTime;
        Status = status;
    }

    public String getName() {
        return Name;
    }

    public String getMessage() {
        return Message;
    }

    public String getDateTime() {
        return DateTime;
    }

    public String getStatus() {
        return Status;
    }
}
