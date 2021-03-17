package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Credit_Online_Application_Documents")
public class ECreditApplicationDocuments {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sDocTransNox")
    private String DocTransNox;
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sFileCode")
    private String FileCode;
    @ColumnInfo(name = "nEntryNox")
    private int EntryNox;
    @ColumnInfo(name = "sImageNme")
    private String ImageNme;
    @ColumnInfo(name = "sFileLoc")
    private String FileLoc;
    public ECreditApplicationDocuments() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    @NonNull
    public String getDocTransNox() {
        return DocTransNox;
    }

    public void setDocTransNox(@NonNull String transNox) {
        DocTransNox = transNox;
    }

    public String getFileCode() {
        return FileCode;
    }

    public void setFileCode(String fileCode) {
        FileCode = fileCode;
    }

    public int getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(int entryNox) {
        EntryNox = entryNox;
    }

    public String getImageNme() {
        return ImageNme;
    }
    public void setImageNme(String imageNme) {
        ImageNme = imageNme;
    }

    public String getFileLoc() {
        return FileLoc;
    }
    public void setFileLoc(String fileLoc) {
        FileLoc = fileLoc;
    }
}
