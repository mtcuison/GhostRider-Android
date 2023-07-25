package org.rmj.g3appdriver.GConnect.room.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "App_Event_Info")
public class EEvents {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;

    @ColumnInfo(name = "dEvntFrom")
    private String EvntFrom;

    @ColumnInfo(name = "dEvntThru")
    private String EvntThru;

    @ColumnInfo(name = "sEventTle")
    private String EventTle;

    @ColumnInfo(name = "sAddressx")
    private String Addressx;

    @ColumnInfo(name = "sEventURL")
    private String EventURL;

    @ColumnInfo(name = "sImageURL")
    private String ImageURL;

    @ColumnInfo(name = "cNotified")
    private String Notified;

    @ColumnInfo(name = "dModified")
    private String Modified;

    @ColumnInfo(name = "sDirectoryFolder")
    private String DirectoryFolder;

    @ColumnInfo(name = "sImagePath")
    private String ImagePath;

    public EEvents() {
    }
    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getEvntFrom() {
        return EvntFrom;
    }

    public void setEvntFrom(String evntFrom) {
        EvntFrom = evntFrom;
    }

    public String getEvntThru() {
        return EvntThru;
    }

    public void setEvntThru(String evntThru) {
        EvntThru = evntThru;
    }

    public String getEventTle() {
        return EventTle;
    }

    public void setEventTle(String eventTle) {
        EventTle = eventTle;
    }

    public String getAddressx() {
        return Addressx;
    }

    public void setAddressx(String addressx) {
        Addressx = addressx;
    }

    public String getEventURL() {
        return EventURL;
    }

    public void setEventURL(String eventURL) {
        EventURL = eventURL;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getNotified() {
        return Notified;
    }

    public void setNotified(String notified) {
        Notified = notified;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getDirectoryFolder() {
        return DirectoryFolder;
    }

    public void setDirectoryFolder(String directoryFolder) {
        DirectoryFolder = directoryFolder;
    }
    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
