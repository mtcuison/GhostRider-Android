package org.rmj.g3appdriver.lib.Notifications.pojo;

import org.rmj.g3appdriver.etc.FormatUIText;

public class NotificationItemList {
    private String MessageID;
    private String Name;
    private String Title;
    private String Message;
    private String DateTime;
    private String Receipt;
    private String Status;
    private String Type;

    public NotificationItemList() {
    }

    public String getMessageID() {
        return MessageID;
    }

    public void setMessageID(String messageID) {
        MessageID = messageID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public String getReceipt() {
        return Receipt;
    }

    public void setReceipt(String receipt) {
        Receipt = receipt;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getDateTime() {
        return FormatUIText.getAbbreviationOfMonthAndDayFormat(DateTime);
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
