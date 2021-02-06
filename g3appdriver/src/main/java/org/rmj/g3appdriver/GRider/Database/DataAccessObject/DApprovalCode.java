package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;

import java.util.List;

@Dao
public interface DApprovalCode {

    @Insert
    void insert(ECodeApproval codeApproval);

    @Update
    void update(ECodeApproval codeApproval);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<ESCA_Request> requestList);

    @Query("SELECT * FROM System_Code_Approval ORDER BY dTransact DESC LIMIT 1")
    LiveData<ECodeApproval> getCodeApprovalEntry();

    @Query("SELECT * FROM xxxSCA_Request " +
            "WHERE cSCATypex = '1' " +
            "AND cRecdStat = '1' ORDER BY sSCATitle")
    LiveData<List<ESCA_Request>> getSCA_AuthReference();

    @Query("SELECT * FROM xxxSCA_Request " +
            "WHERE cSCATypex = '2' " +
            "AND cRecdStat = '1' ORDER BY sSCATitle")
    LiveData<List<ESCA_Request>> getSCA_AuthName();

    @RawQuery(observedEntities = ESCA_Request.class)
    LiveData<List<ESCA_Request>> getAuthorizedFeatures(SupportSQLiteQuery sqLiteQuery);
}
