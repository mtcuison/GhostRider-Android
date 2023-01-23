package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeLoan;

@Dao
public interface DEmployeeLoan {

    @Insert
    void Save(EEmployeeLoan loan);

    @Query("DELETE FROM Employee_Loan WHERE sTransNox =:args")
    void Delete(String args);

    @Query("SELECT COUNT(*) FROM Employee_Loan")
    int GetLoanCountForID();

    @Query("SELECT * FROM Employee_Loan WHERE sTransNox =:args")
    EEmployeeLoan GetLoanDetail(String args);
}
