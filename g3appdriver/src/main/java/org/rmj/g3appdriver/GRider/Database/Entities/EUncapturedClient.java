package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Uncaptured_Client_Image")
public class EUncapturedClient {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sClientNm")
    private String ClientNm;
    @ColumnInfo(name = "sSourceNo")
    private String SourceNo;

    public EUncapturedClient() {
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public void setSourceNo(String sourceNo) {
        SourceNo = sourceNo;
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public String getSourceNo() {
        return SourceNo;
    }
}
