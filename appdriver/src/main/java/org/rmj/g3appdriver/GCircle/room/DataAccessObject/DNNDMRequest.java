/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/17/21 9:20 AM
 * project file last modified : 5/17/21 9:20 AM
 */

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.g3appdriver.GCircle.room.Entities.ENNDMRequest;

@Dao
public interface DNNDMRequest {

    @Insert
    void insert(ENNDMRequest enndmRequest);
}
