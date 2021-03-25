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

    @Query("SELECT * FROM Credit_Applicant_Info WHERE sTransNox = '210000000022'")
    ECreditApplicantInfo getCreditApplicant();
}
