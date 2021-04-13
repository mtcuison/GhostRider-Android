package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

@Dao
public interface DCIEvaluation {

    @Insert
    void insert(ECIEvaluation eciEvaluation);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(ECIEvaluation eciEvaluation);

    @Delete
    void delete(ECIEvaluation eciEvaluation);

    @Insert
    void insertNewCiApplication(ECIEvaluation eciEvaluation);

    @Query("SELECT * FROM CI_Evaluation WHERE sTransNox =:TransNox")
    LiveData<ECIEvaluation> getCIInfoOfTransNox(String TransNox);

    @Query("UPDATE CI_Evaluation SET " +
            "sLandMark =:LandMark, " +
            "cOwnershp =:Ownershp, " +
            "cOwnOther =:OwnOther, " +
            "cHouseTyp =:HouseTyp, " +
            "cGaragexx =:Garagexx, " +
            "nLatitude =:Latitude, " +
            "nLongitud =:Longitud " +
            "WHERE sTransNox =:TransNox ")
    void updateCIInfo(String TransNox, String LandMark, String Ownershp, String OwnOther, String HouseTyp, String Garagexx,String Latitude, String Longitud);
}
