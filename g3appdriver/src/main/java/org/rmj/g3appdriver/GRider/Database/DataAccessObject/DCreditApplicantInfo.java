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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;

import java.util.List;

@Dao
public interface DCreditApplicantInfo {

    @Insert
    void insert(ECreditApplicantInfo creditApplicantInfo);

    @Update
    void update(ECreditApplicantInfo creditApplicantInfo);

    @Delete
    void delete(ECreditApplicantInfo creditApplicantInfo);

    @Query("SELECT * FROM Credit_Applicant_Info WHERE sTransNox = :TransNox")
    LiveData<ECreditApplicantInfo> getCreditApplicantInfo(String TransNox);

    @Query("SELECT * FROM Credit_Applicant_Info")
    LiveData<List<ECreditApplicantInfo>> getCreditApplicantList();

    @Query("SELECT sAppMeans FROM Credit_Applicant_Info WHERE sTransNox = :TransNox")
    LiveData<String> getAppMeansInfo(String TransNox);

    @Query("SELECT * FROM Credit_Applicant_Info WHERE sTransNox =:TransNox")
    ECreditApplicantInfo getCurrentCreditApplicantInfo(String TransNox);

    @Query("DELETE FROM Credit_Applicant_Info")
    void deleteAllCreditApp();
}
