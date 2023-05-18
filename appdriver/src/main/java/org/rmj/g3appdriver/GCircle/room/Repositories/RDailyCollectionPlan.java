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

package org.rmj.g3appdriver.GCircle.room.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.EClientUpdate;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.List;

public class RDailyCollectionPlan {
    private static final String TAG = "DB_CollectionDetail";
    private final DDCPCollectionDetail detailDao;
    private final DDCPCollectionMaster masterDao;
    private final Application application;

    public interface OnClientAccNoxInserted{
        void OnInsert(String message);
    }

    public RDailyCollectionPlan(Application application){
        this.application = application;
        GGC_GCircleDB GGCGriderDB = GGC_GCircleDB.getInstance(application);
        detailDao = GGCGriderDB.DcpDetailDao();
        masterDao = GGCGriderDB.DcpMasterDao();
    }

    public void AddCollectionAccount(EDCPCollectionDetail foDetail){
        detailDao.insert(foDetail);
    }

    public void insertCollectionDetail(EDCPCollectionDetail collectionDetail, OnClientAccNoxInserted listener){
        new InsertCollectionDetailTask(listener).execute(collectionDetail);
    }

    public void updateEntryMaster(int nEntryNox){
        masterDao.updateEntryMaster(nEntryNox);
    }

    public void updateSentPostedDCPMaster(String TransNox){
        masterDao.updateSentPostedDCPMaster(TransNox, AppConstants.DATE_MODIFIED());
    }

    public List<EDCPCollectionMaster> getCollectionMasterIfExist(String TransNox){
        return masterDao.getCollectionMasterIfExist(TransNox);
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailList(){
        return detailDao.getCollectionDetailList();
    }

    public List<EDCPCollectionDetail> getUnsentPaidCollection() throws Exception{
        return detailDao.getUnsentPaidCollection();
    }

    public EDCPCollectionDetail CheckIFAccountExist(String AccNmbr){
        return detailDao.CheckIFAccountExist(AppConstants.CURRENT_DATE(), AccNmbr);
    }

    public LiveData<List<EDCPCollectionMaster>> getCollectioMasterList(){
        return masterDao.getCollectionMasterList();
    }

    public LiveData<EClientUpdate> getClientUpdateInfo(String AccountNox){
        return detailDao.getClient_Update_Info(AccountNox);
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return masterDao.getCollectionMaster();
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(String TransNox, int EntryNox){
        return detailDao.getCollectionDetail(TransNox, EntryNox);
    }

    public void updateCollectionDetailInfo(EDCPCollectionDetail collectionDetail){
        detailDao.update(collectionDetail);
    }

    public void updateCNADetail(String AccNmbr, int EntryNo, String Remarks){
        detailDao.UpdateCNADetails(AccNmbr, EntryNo, Remarks, AppConstants.DATE_MODIFIED());
    }

    public EDCPCollectionMaster CheckIfHasCollection(){
        return masterDao.CheckIfHasCollection();
    }

    public List<EDCPCollectionDetail> checkDCPPAYTransaction(){
        return detailDao.checkDCPPAYTransaction();
    }

    public void updateCollectionDetail(int EntryNox, String RemCode, String Remarks){
        detailDao.updateCollectionDetailInfo(EntryNox, RemCode, Remarks, AppConstants.DATE_MODIFIED());
    }

    public void updateNotVisitedCollections(String Remarks, String TransNox){
        detailDao.updateNotVisitedCollections(Remarks, TransNox, AppConstants.DATE_MODIFIED());
    }

    public void updateCollectionDetailStatus(String TransNox, int EntryNox){
        detailDao.updateCollectionDetailStatus(TransNox, EntryNox, AppConstants.DATE_MODIFIED());
    }

    public List<EDCPCollectionDetail> CheckCollectionDetailNoRemCode(String TransNox){
        return detailDao.CheckCollectionDetailNoRemCode(TransNox);
    }

    public void updateCollectionDetailStatusWithRemarks(String TransNox, int EntryNox, String Remarks){
        detailDao.updateCollectionDetailStatusWithRemarks(TransNox, EntryNox, AppConstants.DATE_MODIFIED(), Remarks);
    }

    public int getAccountNoCount(String TransNox){
        return detailDao.getAccountNoCount(TransNox);
    }

    public int getUnsentCollectionDetail(String TransNox){
        return detailDao.getUnsentCollectionDetail(TransNox);
    }

    public String getMasterSendStatus(String TransNox){
        return detailDao.getMasterSendStatus(TransNox);
    }

    public String getUnpostedDcpMaster(){
        return detailDao.getUnpostedDcpMaster();
    }

    public void updateCollectionDetailImage(String AccntNox){
        detailDao.updateCustomerDetailImage(AccntNox);
        Log.e(TAG, "updateCustomerDetailImage");
    }

    public LiveData<EDCPCollectionDetail> getCollectionLastEntry(){
        return detailDao.getCollectionLastEntry(AppConstants.CURRENT_DATE());
    }

    public String getCurrentDateTransNox(){
        return detailDao.getCurrentDateTransNox(AppConstants.CURRENT_DATE());
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailForDate(String dTransact){
        return detailDao.getCollectionDetailForDate(dTransact);
    }

    public LiveData<String> getCollectedTotalCheckPayment(String dTransact){
        return detailDao.getCollectedCheckTotalPayment(dTransact);
    }

    public LiveData<String> getCollectedTotalPayment(String dTransact){
        return detailDao.getCollectedTotalPayment(dTransact);
    }

    public LiveData<String> getCollectedTotal(String dTransact){
        return detailDao.getCollectedTotal(dTransact);
    }

    public LiveData<String> getTotalRemittedPayment(String dTransact){
        return detailDao.getTotalRemittedPayment(dTransact);
    }

    public LiveData<EDCPCollectionDetail> getDuplicateSerialEntry(String SerialNo){
        return detailDao.getDuplicateSerialEntry(SerialNo);
    }

    public LiveData<List<DDCPCollectionDetail.CollectionDetail>> getCollectionDetailForPosting(){
        return detailDao.getCollectionDetailForPosting();
    }

    public LiveData<EDCPCollectionDetail> getPostedCollectionDetail(String TransNox, String Acctnox, String RemCode) {
        return detailDao.getPostedCollectionDetail(TransNox, Acctnox, RemCode);
    }

    public EDCPCollectionDetail checkCollectionImport(String sTransNox, int nEntryNox) {
        return detailDao.checkCollectionImport(sTransNox,nEntryNox);
    }

    public LiveData<DDCPCollectionDetail.Location_Data_Trigger> getDCP_COH_StatusForTracking(){
        return detailDao.getDCP_COH_StatusForTracking(AppConstants.CURRENT_DATE());
    }

    public DDCPCollectionDetail.DCP_Posting_Validation_Data getValidationData(){
        return detailDao.getValidationData(AppConstants.CURRENT_DATE());
    }

    public Integer getDCPStatus(){
        return detailDao.getDCPStatus(AppConstants.CURRENT_DATE());
    }

    public List<EDCPCollectionDetail> checkCollectionRemarksCode(){
        return detailDao.checkCollectionRemarksCode();
    }

    public EDCPCollectionMaster getLastCollectionMaster(){
        return masterDao.getLastCollectionMaster();
    }

    private class InsertCollectionDetailTask extends AsyncTask<EDCPCollectionDetail, Void, String>{
        private OnClientAccNoxInserted mListener;

        public InsertCollectionDetailTask(OnClientAccNoxInserted mListener) {
            this.mListener = mListener;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... edcpCollectionDetails) {
            edcpCollectionDetails[0].setTransNox(getCurrentDateTransNox());
            if(!edcpCollectionDetails[0].getAcctNmbr().isEmpty()) {
                if (detailDao.getClientDuplicateAccNox(edcpCollectionDetails[0].getAcctNmbr()) == null) {
                    edcpCollectionDetails[0].setTransNox(getCurrentDateTransNox());
                    detailDao.insert(edcpCollectionDetails[0]);
                    return "New customer has been added to collection list.";
                } else {
                    return "Customer already existed in current collection list";
                }
            } else if(!edcpCollectionDetails[0].getSerialNo().isEmpty()){
                if (detailDao.getClientDuplicateSerialNox(edcpCollectionDetails[0].getSerialNo()) == null) {
                    detailDao.insert(edcpCollectionDetails[0]);
                    return "New customer has been added to collection list.";
                } else {
                    return "Customer already existed in current collection list";
                }
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mListener.OnInsert(s);
        }
    }

    public LiveData<List<EDCPCollectionDetail>> getDCPDetailForPosting(){
        return detailDao.getDCPDetailForPosting(AppConstants.CURRENT_DATE());
    }

    public List<EDCPCollectionDetail> getCheckPostedCollectionDetail(String TransNox){
        return detailDao.getCheckPostedCollectionDetail(TransNox);
    }

    public EDCPCollectionDetail getCollectionDetail(String TransNox, String Account){
        return detailDao.getCollectionDetail(TransNox, Account);
    }

    public List<EDCPCollectionDetail> getLRDCPCollectionForPosting(){
        return detailDao.getLRDCPCollectionForPosting();
    }

    public List<EDCPCollectionDetail> getDetailCollection(String TransNox){
        return detailDao.getDetailCollection(TransNox);
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailLog() {
        return detailDao.getCollectionDetailLog();
    }
}
