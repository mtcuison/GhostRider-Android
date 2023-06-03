/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GCircle.room.Entities.EFileCode;

import java.util.List;

@Dao
    public interface DCreditApplicationDocuments {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ECreditApplicationDocuments documentsInfo);

    @Update
    void update(ECreditApplicationDocuments documentsInfo);

    @Query("SELECT * FROM Credit_Online_Application_Documents WHERE sTransNox=:args")
    ECreditApplicationDocuments GetCreditAppDocs(String args);

    @Query("UPDATE Credit_Online_Application_Documents " +
            "SET sFileLoct = (SELECT sFileLoct FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode=:sFileCD), " +
            "sImageNme = (SELECT sImageNme FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode=:sFileCD) " +
            "WHERE sTransNox =:TransNox " +
            "AND sFileCode =:sFileCD")
    void updateDocumentsInfo(String TransNox, String sFileCD);

    @Query("UPDATE Credit_Online_Application_Documents " +
            "SET sFileLoct = (SELECT sFileLoct FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode = Credit_Online_Application_Documents.sFileCode), " +
            "sImageNme = (SELECT sImageNme FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode = Credit_Online_Application_Documents.sFileCode) " +
            "WHERE sTransNox =:TransNox ")
    void updateDocumentsInfos(String TransNox);

    @Query("SELECT " +
            "a.sTransNox, " +
            "a.sFileCode, " +
            "a.nEntryNox, " +
            "b.sImageNme, " +
            "b.sFileLoct, " +
            "(SELECT sBriefDsc FROM EDocSys_File WHERE sFileCode = a.sFileCode) AS sBriefDsc, " +
            "b.cSendStat " +
            "FROM Credit_Online_Application_Documents a " +
            "LEFT JOIN image_information b " +
            "ON a.sFileCode = b.sFileCode " +
            "AND a.sTransNox = b.sSourceNo " +
            "WHERE a.sTransNox =:TransNox " +
            "AND b.sSourceNo =:TransNox " +
            "GROUP BY a.sFileCode " +
            "ORDER BY a.nEntryNox ASC")
    LiveData<List<ApplicationDocument>> getDocument(String TransNox);

    @Query("INSERT INTO Credit_Online_Application_Documents (sTransNox, sFileCode, nEntryNox) " +
            "SELECT a.sTransNox, b.sFileCode, b.nEntryNox FROM Credit_Online_Application_List a LEFT JOIN EDocSys_File b " +
            "WHERE a.sTransNox =:TransNox AND b.sFileCode !='0021' AND b.sFileCode !='0020' ")
        void insertDocumentByTransNox(String TransNox);

    @Query("SELECT * FROM Credit_Online_Application_Documents WHERE sTransNox =:TransNox")
    List<ECreditApplicationDocuments> getDuplicateTransNox(String TransNox);

    @Query("SELECT a.sTransNox, " +
            "a.sFileCode, " +
            "b.sBriefDsc, " +
            "a.nEntryNox, " +
            "a.sImageNme, " +
            "a.sFileLoct, " +
            "a.cSendStat " +
            "FROM Credit_Online_Application_Documents a " +
            "LEFT JOIN EDocSys_File b " +
            "ON a.sFileCode = b.sFileCode " +
            "WHERE a.sTransNox =:TransNox " +
            "ORDER BY a.nEntryNox ASC")
    LiveData<List<ApplicationDocument>> GetCreditAppDocuments(String TransNox);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT a.sTransNox, a.sFileCode, a.nEntryNox, a.sImageNme, a.sFileLoct FROM Credit_Online_Application_Documents a LEFT JOIN Image_Information b ON a.sFileCode = b.sFileCode AND a.sTransNox = b.sSourceNo WHERE b.cSendStat != 1")
    LiveData<List<ApplicationDocument>> getDocumentDetailForPosting();

    @Query("SELECT * FROM EDocSys_File WHERE  sFileCode !='0021' AND sFileCode !='0020' ")
    LiveData<List<EFileCode>> getDocumentInfoByFile();

    @Query("SELECT * FROM Credit_Online_Application_Documents WHERE cSendStat != '1' GROUP BY sTransNox")
    List<ECreditApplicationDocuments> getUnsentApplicationDocumentss();

    @Query("UPDATE Credit_Online_Application_Documents " +
            "SET cSendStat = '1' " +
            "WHERE sTransNox =:TransNox " +
            "AND sFileCode =:fileCode ")
    void updateDocumentsInfoByFile(String TransNox, String fileCode);

    class ApplicationDocument{
        public String sTransNox;
        public String sFileCode;
        public int nEntryNox;
        public String sImageNme;
        public String sFileLoct;
        public String sBriefDsc;
        public String cSendStat;
    }
}
