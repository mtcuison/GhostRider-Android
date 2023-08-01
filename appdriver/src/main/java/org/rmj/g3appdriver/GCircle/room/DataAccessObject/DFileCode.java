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
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GCircle.room.Entities.EFileCode;

import java.util.List;

@Dao
public interface DFileCode {

    @Insert
    void insert(EFileCode foVal);

    @Update
    void update(EFileCode foVal);

    @Update
    void update(ECreditApplicationDocuments documentsInfo);

    @Query("SELECT * FROM EDocSys_File WHERE sFileCode =:fsVal")
    EFileCode GetFileCode(String fsVal);

    @Query("SELECT * FROM EDocSys_File ORDER BY dTimeStmp DESC LIMIT 1")
    EFileCode GetLatestFileCode();

    @Query("SELECT * FROM EDocSys_File WHERE sFileCode != '0020' AND sFileCode != '0021'")
    LiveData<List<EFileCode>> selectFileCodeList();

    @Query("SELECT MAX(dTimeStmp) AS TimeStamp FROM EDocSys_File")
    String getLatestDataTime();

    @Query("SELECT dLstUpdte FROM EDocSys_File ORDER BY dLstUpdte DESC LIMIT 1")
    String getLastUpdate();

    @Query("SELECT * FROM Credit_Online_Application_Documents WHERE sTransNox =:TransNox")
    List<ECreditApplicationDocuments> GetDocumentsList(String TransNox);

    @Query("SELECT * FROM Credit_Online_Application_Documents WHERE sTransNox =:TransNox AND sFileCode =:FileCode")
    ECreditApplicationDocuments GetDocument(String TransNox, String FileCode);

    @Query("INSERT INTO Credit_Online_Application_Documents (sTransNox, sFileCode, nEntryNox) " +
            "SELECT a.sTransNox, b.sFileCode, b.nEntryNox FROM Credit_Online_Application_List a LEFT JOIN EDocSys_File b " +
            "WHERE a.sTransNox =:TransNox AND b.sFileCode !='0021' AND b.sFileCode !='0020' ")
    void InitializeDocumentsList(String TransNox);

    @Query("UPDATE Credit_Online_Application_Documents " +
            "SET sFileLoct = (SELECT sFileLoct FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode = Credit_Online_Application_Documents.sFileCode), " +
            "sImageNme = (SELECT sImageNme FROM Image_Information WHERE sSourceNo =:TransNox AND sFileCode = Credit_Online_Application_Documents.sFileCode) " +
            "WHERE sTransNox =:TransNox ")
    void UpdateDocumentsList(String TransNox);

    @Query("UPDATE Credit_Online_Application_Documents " +
            "SET cSendStat = '1' " +
            "WHERE sTransNox =:TransNox " +
            "AND sFileCode =:fileCode ")
    void UpdateCheckedFile(String TransNox, String fileCode);


    @Query("SELECT a.sTransNox, a.sFileCode, b.sBriefDsc, a.nEntryNox, a.sImageNme, a.sFileLoct, a.cSendStat " +
            "FROM Credit_Online_Application_Documents a LEFT JOIN EDocSys_File b " +
            "ON a.sFileCode = b.sFileCode WHERE a.sTransNox =:TransNox " +
            "ORDER BY a.nEntryNox ASC")
    LiveData<List<ApplicationDocument>> GetCreditAppDocuments(String TransNox);

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
