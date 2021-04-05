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

    public EUncapturedClient(@NonNull String transNox, String clientNm, String sourceNo) {
        TransNox = transNox;
        ClientNm = clientNm;
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
