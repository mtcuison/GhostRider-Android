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

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ECIEvaluation;

@Dao
public interface DCIEvaluation {

    @Insert
    void insert(ECIEvaluation eciEvaluation);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ECIEvaluation eciEvaluation);

    @Insert
    void insertNewCiApplication(ECIEvaluation eciEvaluation);

    @Query("UPDATE Credit_Online_Application_List_CI SET " +
            "sLandMark =:LandMark, " +
            "cOwnershp =:Ownershp, " +
            "cOwnOther =:OwnOther, " +
            "cHouseTyp =:HouseTyp, " +
            "cGaragexx =:Garagexx, " +
            "nLatitude =:Latitude, " +
            "nLongitud =:Longitud " +
            "WHERE sTransNox =:TransNox ")
    void updateCIInfo(String TransNox, String LandMark, String Ownershp, String OwnOther, String HouseTyp, String Garagexx,String Latitude, String Longitud);

    @Query("SELECT * FROM Credit_Online_Application_List_CI WHERE sTransNox =:TransNox")
    LiveData<ECIEvaluation> getCIInfoOfTransNox(String TransNox);

    @Query("SELECT * FROM Credit_Online_Application_List_CI " +
            "WHERE sTransNox = :fsTransNo " +
            "AND sCredInvx = (SELECT sEmployID FROM User_Info_Master) " +
            "AND (cTranStat = 1 OR cTranStat = 3)")
    LiveData<ECIEvaluation> getAllDoneCiInfo(String fsTransNo);
}
