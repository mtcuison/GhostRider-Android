/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GConnect.room.Entities.ETokenInfo;


@Dao
public interface DRawDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTokenInfo(ETokenInfo tokenInfo);

    @Query("DELETE FROM App_Token_Info")
    void clearTokenInfo();

    @Query("SELECT sAppToken FROM App_Token_Info")
    String getTokenInfo();

    @Query("SELECT sUserIDxx FROM Client_Profile_Info")
    String GetUserID();

    @Query("SELECT sTransNox FROM Notification_Info_Recepient ORDER BY sTransNox DESC LIMIT 1")
    String GetNotificationRcptID();
}
