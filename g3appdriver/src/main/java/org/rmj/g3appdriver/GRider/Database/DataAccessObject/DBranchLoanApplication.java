package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;

import java.util.List;
@Dao
public interface DBranchLoanApplication {
    @Insert
    void insert(EBranchLoanApplication branchLoanApplication);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EBranchLoanApplication> branchLoanApplication);

    @Update
    void update(EBranchLoanApplication branchLoanApplication);

    @Delete
    void delete(EBranchLoanApplication branchLoanApplication);

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE sBranchCD = (SELECT sBranchCD FROM User_Info_Master) ")
    LiveData<List<EBranchLoanApplication>> getAllBranchCreditApplication();

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE cTranStat !=4 AND " +
            "sBranchCD = (SELECT sBranchCD FROM User_Info_Master)")
    LiveData<List<EBranchLoanApplication>> getAllCICreditApplication();

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE cTranStat != 4 AND " +
            "sCredInvx  = (SELECT sEmployID FROM User_Info_Master)")
    LiveData<List<EBranchLoanApplication>> getAllCICreditApplicationLog();

    @Insert
    void insertNewApplication(EBranchLoanApplication loanApplication);
}
