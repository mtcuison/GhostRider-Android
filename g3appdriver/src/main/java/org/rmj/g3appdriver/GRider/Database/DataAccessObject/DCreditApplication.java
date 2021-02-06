package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

@Dao
public interface DCreditApplication {

    @Insert
    void insert(ECreditApplication creditApplication);

    @Update
    void update(ECreditApplication creditApplication);

    @Delete
    void delete(ECreditApplication creditApplication);

    @Query("SELECT * FROM Credit_Online_Application")
    LiveData<List<ECreditApplication>> getAllCreditApplication();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<ECreditApplication> creditApplications);

    @Query("Select a.sGOCASNox, " +
            "a.sTransNox, " +
            "b.sBranchNm, " +
            "a.dCreatedx, " +
            "a.sDetlInfo, " +
            "a.sClientNm, " +
            "a.cWithCIxx, " +
            "a.cSendStat, " +
            "a.cTranStat, " +
            "a.dReceived, " +
            "a.dVerified " +
            "From Credit_Online_Application a " +
            "Left Join Branch_Info b " +
            "ON a.sBranchCd = b.sBranchCd " +
            "WHERE cTranStat != 4 " +
            "AND sCreatedx = (SELECT sUserIDxx From User_Info_Master) " +
            "ORDER BY a.dCreatedx DESC")
    LiveData<List<ApplicationLog>> getApplicationHistory();

    class ApplicationLog{
        public String sGOCASNox;
        public String sTransNox;
        public String sBranchNm;
        public String dCreatedx;
        public String sDetlInfo;
        public String sClientNm;
        public String cWithCIxx;
        public String cSendStat;
        public String cTranStat;
        public String dReceived;
        public String dVerified;

    }
}
