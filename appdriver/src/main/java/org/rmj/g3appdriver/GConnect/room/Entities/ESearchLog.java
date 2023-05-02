package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mkt_Search_log")
public class ESearchLog {

    @PrimaryKey
    @ColumnInfo(name = "nEntryNox")
    private int EntryNox;
    @ColumnInfo(name = "sSearchxx")
    private String Searchxx;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ESearchLog() {
    }

    public int getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(int entryNox) {
        EntryNox = entryNox;
    }

    public String getSearchxx() {
        return Searchxx;
    }

    public void setSearchxx(String searchxx) {
        Searchxx = searchxx;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
