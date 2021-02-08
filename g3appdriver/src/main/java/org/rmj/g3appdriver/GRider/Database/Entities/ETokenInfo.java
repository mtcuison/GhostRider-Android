package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "App_Token_Info")
public class ETokenInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTokenInf")
    private String TokenInf;

    public ETokenInfo() {
    }

    @NonNull
    public String getTokenInf() {
        return TokenInf;
    }

    public void setTokenInf(@NonNull String tokenInf) {
        TokenInf = tokenInf;
    }
}
