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
