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
            "SET sFileLoc = (SELECT sFileLoct FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode=:sFileCD), " +
            "sImageNme = (SELECT sImageNme FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode=:sFileCD) " +
            "WHERE sTransNox =:TransNox " +
            "AND sFileCode =:sFileCD")
    void updateDocumentsInfo(String TransNox, String sFileCD);

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

    @Query("SELECT a.sTransNox, a.sFileCode, a.nEntryNox, b.sImageNme, b.sFileLoct FROM Credit_Online_Application_Documents a LEFT JOIN image_information b ON a.sFileCode = b.sFileCode AND a.sTransNox = b.sSourceNo WHERE a.sTransNox =:TransNox  AND b.sSourceNo =:TransNox  GROUP BY a.sFileCode  ORDER BY a.nEntryNox ASC")
    LiveData<List<ApplicationDocument>> getDocument(String TransNox);

    @Query("INSERT INTO Credit_Online_Application_Documents (sTransNox, sFileCode, nEntryNox) " +
            "SELECT a.sTransNox, b.sFileCode, b.nEntryNox FROM Credit_Online_Application_List a LEFT JOIN EDocSys_File b " +
            "WHERE a.sTransNox =:TransNox AND b.sFileCode !='0021' AND b.sFileCode !='0020' ")
        void insertDocumentByTransNox(String TransNox);

    @Query("SELECT * FROM Credit_Online_Application_Documents WHERE sTransNox =:TransNox")
    List<ECreditApplicationDocuments> getDuplicateTransNox(String TransNox);

    @Query("SELECT a.sTransNox, a.sFileCode, b.sBriefDsc, a.nEntryNox, a.sImageNme, a.sFileLoc " +
            "FROM Credit_Online_Application_Documents a LEFT JOIN EDocSys_File b " +
            "ON a.sFileCode = b.sFileCode WHERE a.sTransNox =:TransNox " +
            "ORDER BY a.nEntryNox ASC")
    LiveData<List<ApplicationDocument>> getDocumentInfo(String TransNox);

    class ApplicationDocument{
        public String sTransNox;
        public String sFileCode;
        public int nEntryNox;
        public String sImageNme;
        public String sFileLoc;
        public String sBriefDsc;
    }
}
