package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;

import java.util.List;

@Dao
public interface DBranchLoanApplication {

    @Insert
    void insertBranchApplications(List<EBranchLoanApplication> applications);

    @Query("SELECT * FROM Credit_Online_Application_List")
    LiveData<List<EBranchLoanApplication>> getBranchLoanApplications();
}
