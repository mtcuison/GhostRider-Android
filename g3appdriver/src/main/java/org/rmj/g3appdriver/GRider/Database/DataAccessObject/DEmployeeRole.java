/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 9/2/21, 11:06 AM
 * project file last modified : 9/2/21, 11:06 AM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;

import java.util.List;

@Dao
public interface DEmployeeRole {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertEmployeeRole(EEmployeeRole role);

    @Query("DELETE FROM xxxAOEmpRole WHERE sProdctID =:Product AND sUserIDxx =:UserID AND sObjectNm=:Object")
    void DeleteEmployeeRole(String Product, String UserID, String Object);

    @Query("SELECT * FROM xxxAOEmpRole " +
            "WHERE sUserIDxx = (" +
            "SELECT sUserIDxx FROM User_Info_Master) " +
            "AND cRecdStat = '1' AND sParentxx = ''")
    LiveData<List<EEmployeeRole>> getEmployeeRoles();

    @Query("SELECT * FROM xxxAOEmpRole " +
            "WHERE sUserIDxx = (" +
            "SELECT sUserIDxx FROM User_Info_Master) " +
            "AND cRecdStat = '1' AND sParentxx != ''")
    LiveData<List<EEmployeeRole>> getChildRoles();
}
