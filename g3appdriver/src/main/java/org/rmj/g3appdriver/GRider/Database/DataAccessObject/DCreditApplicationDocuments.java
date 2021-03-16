package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;

import java.util.List;

@Dao
public interface DCreditApplicationDocuments {

    @Insert
    void insert(ECreditApplicationDocuments documentsInfo);

    @Update
    void update(ECreditApplicationDocuments documentsInfo);
//
//    @Query("UPDATE Credit_Application_Documents " +
//            "SET sTransNox =:TransNox, " +
//            "cSendStat = '1', " +
//            "dSendDate =:DateModifield " +
//            "WHERE sTransNox =:oldTransNox")
//    void updateImageInfo(String TransNox, String DateModifield, String oldTransNox);

    @Query("SELECT * FROM Credit_Online_Application_Documents " +
            "WHERE sTransNox = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    LiveData<List<ECreditApplicationDocuments>> getDocumentInfo();

    @Query("SELECT * FROM Credit_Online_Application_Documents " +
            "WHERE sTransNox = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    LiveData<List<ECreditApplicationDocuments>> getDocumentInfoList();


    @Query("SELECT * FROM Credit_Online_Application_Documents a\n" +
            "LEFT JOIN Image_Information b " +
            "ON a.sTransNox = b.sSourceNo " +
            "WHERE a.sTransNox =:TransNox ")
    LiveData<List<ECreditApplicationDocuments>> getDocument(String TransNox);

}
