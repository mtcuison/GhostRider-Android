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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ECreditApplicationDocuments documentsInfo);

    @Update
    void update(ECreditApplicationDocuments documentsInfo);


    @Query("UPDATE Credit_Online_Application_Documents " +
            "SET sFileLoc =:sFilePath , " +
            "sImageNme =:sImgName " +
            "WHERE sTransNox =:TransNox " +
            "AND sFileCode =:sFileCD")
    void updateDocumentsInfo(String TransNox, String sFileCD, String sImgName, String sFilePath);


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

@Query("SELECT a.sTransNox, a.sFileCode, a.nEntryNox, a.sImageNme, a.sFileLoc FROM Credit_Online_Application_Documents a LEFT JOIN Credit_Online_Application_Documents b ON a.sFileCode = b.sFileCode AND a.nEntryNox = b.nEntryNox WHERE a.sTransNox =:TransNox ORDER BY a.nEntryNox ASC;")
LiveData<List<ApplicationDocument>> getDocument(String TransNox);

    class ApplicationDocument{
        public String sTransNox;
        public String sFileCode;
        public int nEntryNox;
        public String sFileLoc;
        public String sImageNme;

    }
}
