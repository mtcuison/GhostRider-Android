package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ETokenInfo;

@Dao
public interface DToken {

    @Insert
    void SaveToken(ETokenInfo foVal);

    @Update
    void UpdateToken(ETokenInfo foVal);

    @Query("SELECT * FROM App_Token_Info WHERE sTokenInf =:fsVal")
    ETokenInfo GetFirebaseToken(String fsVal);

    @Query("SELECT COUNT (*) FROM App_Token_Info")
    int GetTokenRowsForID();

    @Query("SELECT * FROM App_Token_Info WHERE sTokenTpe = '1'")
    ETokenInfo GetClientToken();

    @Query("SELECT * FROM App_Token_Info WHERE sTokenTpe = '2'")
    ETokenInfo GetAccessToken();

    @Query("SELECT sClientID FROM User_Info_Master")
    String GetClientID();

    @Query("SELECT sUserIDxx FROM User_Info_Master")
    String GetUserID();
}
