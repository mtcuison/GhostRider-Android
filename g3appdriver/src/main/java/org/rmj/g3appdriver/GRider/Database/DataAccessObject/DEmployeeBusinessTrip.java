/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 8/13/21 10:32 AM
 * project file last modified : 8/13/21 10:32 AM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;

@Dao
public interface DEmployeeBusinessTrip {

    @Insert
    void insert(EEmployeeBusinessTrip obLeave);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(EEmployeeBusinessTrip obLeave);

    @Delete
    void delete(EEmployeeBusinessTrip obLeave);

    @Insert
    void insertNewOBLeave(EEmployeeBusinessTrip obLeave);

}
