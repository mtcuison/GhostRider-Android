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
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.EClientUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;

import java.util.List;

@Dao
public interface DDCPCollectionDetail {

    @Insert
    void insert(EDCPCollectionDetail collectionDetail);

    @Update
    void update(EDCPCollectionDetail collectionDetail);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox=:TransNox AND nEntryNox=:EntryNo AND sAcctNmbr=:AcctNox")
    EDCPCollectionDetail GetCollectionDetail(String TransNox, String EntryNo, String AcctNox);

    @Query("SELECT sAcctNmbr FROM LR_DCP_Collection_Detail " +
            "WHERE sAcctNmbr =:AccountNox " +
            "AND sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dReferDte DESC LIMIT 1)")
    String getClientDuplicateAccNox(String AccountNox);

    @Query("SELECT sSerialNo FROM LR_DCP_Collection_Detail " +
            "WHERE sSerialNo =:SerialNox " +
            "AND sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dReferDte DESC LIMIT 1)")
    String getClientDuplicateSerialNox(String SerialNox);

    @Query("UPDATE LR_DCP_Collection_Detail SET " +
            "sRemCodex = 'CNA', " +
            "sRemarksx =:Remarks, " +
            "cTranStat = '1'," +
            "dModified =:DateTime " +
            "WHERE sTransNox =(SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat IS NULL) " +
            "AND sAcctNmbr =:AccNmbr " +
            "AND nEntryNox =:EntryNo")
    void UpdateCNADetails(String AccNmbr, int EntryNo, String Remarks, String DateTime);

    /**
     *
     * @param EntryNox specific entry number of collection detail
     * @param RemCode remarks code base on the customer's transaction.
     * @param Remarks required for all remarks code except PAY
     * @param DateModified current date time of update.
     */
    @Query("UPDATE LR_DCP_Collection_Detail " +
            "SET sRemCodex =:RemCode, " +
            "sRemarksx =:Remarks, " +
            "cSendStat = '0', " +
            "cTranStat = '1', " +
            "sImageNme = (SELECT a.sImageNme FROM Image_Information a LEFT JOIN LR_DCP_Collection_Detail b  ON a.sSourceNo = b.sTransNox AND a.sDtlSrcNo = sAcctNmbr WHERE a.sSourceNo = b.sTransNox AND b.nEntryNox =:EntryNox), " +
            "nLongitud = (SELECT a.nLongitud FROM Image_Information a LEFT JOIN LR_DCP_Collection_Detail b  ON a.sDtlSrcNo = sAcctNmbr WHERE a.sSourceNo = b.sTransNox), " +
            "nLatitude = (SELECT a.nLatitude FROM Image_Information a LEFT JOIN LR_DCP_Collection_Detail b  ON a.sDtlSrcNo = sAcctNmbr WHERE a.sSourceNo = b.sTransNox), " +
            "dModified =:DateModified " +
            "WHERE sTransNox = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master ORDER BY dReferDte DESC LIMIT 1) " +
            "AND nEntryNox =:EntryNox")
    void updateCollectionDetailInfo(int EntryNox, String RemCode, String Remarks, String DateModified);

    /**
     *
     * @param TransNox transaction number of master detail
     * @param EntryNox specific entry number of collection detail
     * @param DateEntry current date time of update.
     */
    @Query("UPDATE LR_DCP_Collection_Detail " +
            "SET cSendStat='1', " +
            "cTranstat = '2', " +
            "dSendDate =:DateEntry, " +
            "dModified =:DateEntry " +
            "WHERE sTransNox =:TransNox " +
            "AND nEntryNox =:EntryNox")
    void updateCollectionDetailStatus(String TransNox, int EntryNox, String DateEntry);

    /**
     *
     * @param TransNox transaction number of master detail
     * @param EntryNox specific entry number of collection detail
     * @param DateEntry current date time of update.
     * @param Remarks if collection posted with untag remarks code. Input remarks
     */
    @Query("UPDATE LR_DCP_Collection_Detail " +
            "SET cSendStat='1', " +
            "sRemCodex = 'NV', " +
            "sRemarksx =:Remarks, " +
            "cTranstat = '2', " +
            "dModified=:DateEntry " +
            "WHERE sTransNox =:TransNox " +
            "AND nEntryNox =:EntryNox")
    void updateCollectionDetailStatusWithRemarks(String TransNox, int EntryNox, String DateEntry, String Remarks);

    @Query("SELECT COUNT(*) FROM LR_DCP_Collection_Detail WHERE sTransNox =:TransNox AND sAcctNmbr != ''")
    int getAccountNoCount(String TransNox);

    @Query("SELECT COUNT(*) FROM LR_DCP_Collection_Detail WHERE cSendStat = '0' AND sTransNox =:TransNox")
    int getUnsentCollectionDetail(String TransNox);

    @Query("SELECT cSendStat FROM LR_DCP_Collection_Master WHERE sTransNox =:TransNox")
    String getMasterSendStatus(String TransNox);

    @Query("SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat IS NULL ORDER BY dReferDte DESC LIMIT 1")
    String getUnpostedDcpMaster();

    @Delete
    void delete(EDCPCollectionDetail collectionDetail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EDCPCollectionDetail> collectionDetails);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE cTranStat != '2' ORDER BY dModified ASC")
    LiveData<List<EDCPCollectionDetail>> getCollectionDetailList();

    @Query("SELECT * FROM Client_Update_Request WHERE sDtlSrcNo = :AccountNox")
    LiveData<EClientUpdate> getClient_Update_Info(String AccountNox);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE cSendStat = 0")
    LiveData<List<EDCPCollectionDetail>> getCollectionDetailLog();

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = :TransNox " +
            "AND nEntryNox = :EntryNox")
    LiveData<EDCPCollectionDetail> getCollectionDetail(String TransNox, int EntryNox);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dReferDte) " +
            "ORDER BY nEntryNox DESC LIMIT 1")
    LiveData<EDCPCollectionDetail> getCollectionLastEntry(String dReferDte);

    @Query("SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dReferDte")
    String getCurrentDateTransNox(String dReferDte);

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master ORDER BY dTransact DESC LIMIT 1) " +
            "AND sSerialNo =:SerialNo")
    LiveData<EDCPCollectionDetail> getDuplicateSerialEntry(String SerialNo);

    @Query("UPDATE LR_DCP_Collection_Detail " +
            "SET sImageNme= (SELECT a.sTransNox FROM Image_Information a " +
            "LEFT JOIN LR_DCP_Collection_Detail b  " +
            "ON a.sSourceNo = b.sTransNox AND a.sDtlSrcNo = sAcctNmbr " +
            "WHERE a.sSourceNo = b.sTransNox) " +
            "WHERE sAcctNmbr =:AccountNo " +
            "AND sTransNox = (SELECT sTransNox " +
            "FROM LR_DCP_Collection_Master " +
            "ORDER BY dTransact DESC LIMIT 1)")
    void updateCustomerDetailImage(String AccountNo);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sCheckNox <> '' AND  sCheckDte <> '' AND sCheckAct <> '' " +
            "AND sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    LiveData<String> getCollectedCheckTotalPayment(String dTransact);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sCheckNox == '' AND  sCheckDte == '' AND sCheckAct == '' " +
            "AND sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    LiveData<String> getCollectedTotalPayment(String dTransact);

    @Query("SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    LiveData<String> getCollectedTotal(String dTransact);

    @Query("SELECT COUNT(*) FROM LR_DCP_Collection_Detail WHERE cTranStat != '2'")
    LiveData<Integer> getCurrentDCPCount();

    @Query("SELECT SUM(nAmountxx) FROM LR_DCP_Remittance " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    LiveData<String> getTotalRemittedPayment(String dTransact);

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM " +
            "LR_DCP_Collection_Master WHERE dReferDte =:dTransact) " +
            "AND cTranStat = '2'")
    LiveData<List<EDCPCollectionDetail>> getCollectionDetailForDate(String dTransact);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox = (" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    LiveData<List<EDCPCollectionDetail>> getUnpostedCollectionDetail(String dTransact);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE cSendStat <> '1' AND sRemCodex == 'PAY'")
    List<EDCPCollectionDetail> getUnsentPaidCollection();

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox =:TransNox ORDER BY nEntryNox DESC")
    List<EDCPCollectionDetail> getDetailCollection(String TransNox);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox =:TransNox AND sRemCodex = '' ORDER BY nEntryNox DESC")
    List<EDCPCollectionDetail> CheckCollectionDetailNoRemCode(String TransNox);

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:ReferDte) " +
            "AND sAcctNmbr =:AccNmbr")
    EDCPCollectionDetail CheckIFAccountExist(String ReferDte, String AccNmbr);

    @Query("SELECT (SELECT COUNT(cTranStat) FROM LR_DCP_Collection_Detail " +
            "WHERE cTranStat <> '2' AND sTransNox = " +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact)) - " +
            "(SELECT COUNT(cTranStat) FROM LR_DCP_Collection_Detail " +
            "WHERE cTranStat = 2 AND sTransNox = " +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master " +
            "WHERE dReferDte =:dTransact))")
    Integer getDCPStatus(String dTransact);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox=(" +
            "SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat IS NULL) " +
            "AND sRemCodex = 'PAY'")
    List<EDCPCollectionDetail> checkDCPPAYTransaction();

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox = " +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cSendStat IS NULL) " +
            "AND sRemCodex <> ''")
    List<EDCPCollectionDetail> checkCollectionRemarksCode();

    @Query("SELECT a.sTransNox, " +
            "a.nEntryNox, " +
            "a.sAcctNmbr, " +
            "a.sRemCodex, " +
            "a.dModified, " +
            "a.xFullName, " +
            "a.sPRNoxxxx, " +
            "a.nTranAmtx, " +
            "a.nDiscount, " +
            "a.dPromised, " +
            "a.nOthersxx, " +
            "a.sRemarksx, " +
            "a.cTranType, " +
            "a.nTranTotl, " +
            "a.cApntUnit, " +
            "a.sBranchCd, " +
            "b.sTransNox AS sImageIDx, " +
            "b.sFileCode, " +
            "b.sSourceCD, " +
            "b.sImageNme, " +
            "b.sMD5Hashx, " +
            "b.sFileLoct, " +
            "b.nLongitud, " +
            "b.nLatitude, " +
            "c.sLastName, " +
            "c.sFrstName, " +
            "c.sMiddName, " +
            "c.sSuffixNm, " +
            "c.sHouseNox, " +
            "c.sAddressx, " +
            "c.sTownIDxx, " +
            "c.cGenderxx, " +
            "c.cCivlStat, " +
            "c.dBirthDte, " +
            "c.dBirthPlc, " +
            "c.sLandline, " +
            "c.sMobileNo, " +
            "c.sEmailAdd, " +
            "d.cReqstCDe AS saReqstCde, " +
            "d.cAddrssTp AS saAddrsTp, " +
            "d.sHouseNox AS saHouseNox, " +
            "d.sAddressx AS saAddress, " +
            "d.sTownIDxx AS saTownIDxx, " +
            "d.sBrgyIDxx AS saBrgyIDxx, " +
            "d.cPrimaryx AS saPrimaryx, " +
            "d.nLatitude AS saLatitude, " +
            "d.nLongitud AS saLongitude, " +
            "d.sRemarksx AS saRemarksx," +
            "e.cReqstCDe AS smReqstCde, " +
            "e.sMobileNo AS smContactNox, " +
            "e.cPrimaryx AS smPrimaryx, " +
            "e.sRemarksx AS smRemarksx " +
            "FROM LR_DCP_Collection_Detail a " +
            "LEFT JOIN Image_Information b " +
            "ON a.sTransNox = b.sSourceNo " +
            "AND a.sAcctNmbr = b.sDtlSrcNo " +
            "LEFT JOIN Client_Update_Request c " +
            "ON a.sTransNox = c.sSourceNo " +
            "AND a.sAcctNmbr = c.sDtlSrcNo " +
            "LEFT JOIN Address_Update_Request d " +
            "ON a.sClientID = d.sClientID " +
            "LEFT JOIN MOBILE_UPDATE_REQUEST e " +
            "ON a.sClientID = e.sClientID " +
            "WHERE a.cSendStat IS NULL")
    LiveData<List<CollectionDetail>> getCollectionDetailForPosting();

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact)")
    LiveData<List<EDCPCollectionDetail>> getDCPDetailForPosting(String dTransact);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox =:TransNox")
    List<EDCPCollectionDetail> getCheckPostedCollectionDetail(String TransNox);

    @Query("SELECT * FROM LR_DCP_Collection_Detail " +
            "WHERE sTransNox = :TransNox " +
            "AND sAcctNmbr = :Acctnox " +
            "AND sRemCodex = :RemCode " +
            "AND cTranStat = 2")
    LiveData<EDCPCollectionDetail> getPostedCollectionDetail(String TransNox, String Acctnox, String RemCode);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox = :sTransNox AND nEntryNox = :nEntryNox")
    EDCPCollectionDetail checkCollectionImport(String sTransNox, int nEntryNox);

    @Query("SELECT (SELECT COUNT(*) FROM LR_DCP_Collection_Master WHERE dReferDte ==:dTransact) AS CollectionMaster, " +
            "(SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail WHERE sTransNox == " +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master WHERE cTranStat == '1') AND sRemCodex == 'PAY') - " +
            "(SELECT SUM(nAmountxx) FROM LR_DCP_Remittance WHERE dTransact == " +
            "(SELECT dReferDte FROM LR_DCP_Collection_Master WHERE cTranStat == '1' AND cSendStat == '0')) AS Cash_On_Hand, " +
            "(SELECT COUNT(*) FROM LR_DCP_Collection_Master WHERE dReferDte ==:dTransact AND cTranStat == '2' AND cSendStat == '1') AS PostedCollection")
    LiveData<Location_Data_Trigger> getDCP_COH_StatusForTracking(String dTransact);

    @Query("SELECT (SELECT COUNT(*) FROM LR_DCP_Collection_Detail WHERE sTransNox = " +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) AND sRemCodex = 'PAY') AS Paid_Collection, " +
            "(SELECT SUM(nTranTotl) FROM LR_DCP_Collection_Detail WHERE sTransNox = " +
            "(SELECT sTransNox FROM LR_DCP_Collection_Master WHERE dReferDte =:dTransact) AND sRemCodex = 'PAY') - " +
            "(SELECT SUM(nAmountxx) FROM LR_DCP_Remittance WHERE dTransact =:dTransact) AS Remitted_Collection")
    DCP_Posting_Validation_Data getValidationData(String dTransact);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE sTransNox =:TransNox AND sAcctNmbr=:Account")
    EDCPCollectionDetail getCollectionDetail(String TransNox, String Account);

    @Query("SELECT * FROM LR_DCP_Collection_Detail WHERE cSendStat <> '1'")
    List<EDCPCollectionDetail> getLRDCPCollectionForPosting();

    @Query("UPDATE LR_DCP_Collection_Detail SET sRemCodex = 'NV', " +
            "sRemarksx =:Remarks, " +
            "dModified =:dModfied " +
            "WHERE sTransNox =:TransNox " +
            "AND sRemCodex = ''")
    void updateNotVisitedCollections(String Remarks, String TransNox, String dModfied);

    class CollectionDetail{
        public String sTransNox;
        public int nEntryNox;
        public String sAcctNmbr;
        public String sRemCodex;
        public String dModified;

        public String xFullName;

        //PAY
        public String sPRNoxxxx;
        public String nTranAmtx;
        public String nDiscount;
        public String nOthersxx;
        public String sRemarksx;
        public String cTranType;
        public String nTranTotl;

        //PTP
        public String cApntUnit;
        public String sBranchCd;
        public String dPromised;

        //ImageInfo
        public String sImageIDx;
        public String sFileCode;
        public String sSourceCD;
        public String sImageNme;
        public String sMD5Hashx;
        public String sFileLoct;
        public String nLongitud;
        public String nLatitude;

        //LoanUnit || Transferred/Assumed || False Ownership
        public String sLastName;
        public String sFrstName;
        public String sMiddName;
        public String sSuffixNm;
        public String sHouseNox;
        public String sAddressx;
        public String sTownIDxx;
        public String cGenderxx;
        public String cCivlStat;
        public String dBirthDte;
        public String dBirthPlc;
        public String sLandline;
        public String sMobileNo;
        public String sEmailAdd;

        //Customer Not Around || Mobile &  Address
        public String smReqstCde;
         public String smPrimaryx;
        public String smContactNox;
        public String smRemarksx;

        public String saReqstCde;
        public String saAddrsTp;
        public String saHouseNox;
        public String saAddress;
        public String saTownIDxx;
        public String saBrgyIDxx;
        public String saPrimaryx;
        public String saLongitude;
        public String saLatitude;
        public String saRemarksx;
    }

    class Location_Data_Trigger{
        public int CollectionMaster;
        public String Cash_On_Hand;
        public int PostedCollection;
    }

    class DCP_Posting_Validation_Data{
        public int Paid_Collection;
        public String Remitted_Collection;
    }

}
