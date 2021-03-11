package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;

import java.util.List;

public class RCollectionUpdate {
    private static final String TAG = "DB_Collection";
    private final Application application;
    private final DAddressRequest addressDao;
    private final DMobileRequest mobileDao;

    public RCollectionUpdate(Application application){
        this.application = application;
        AppDatabase db = AppDatabase.getInstance(application);
        addressDao = db.AddressRequestDao();
        mobileDao = db.MobileRequestDao();
    }

    public LiveData<List<DMobileRequest.CNAMobileInfo>> getCNA_MobileDataList(String sClientID) {
        return mobileDao.getCNAMobileDataList(sClientID);
    }

    public LiveData<List<DAddressRequest.CNA_AddressInfo>> getCNA_AddressDataList(String sClientID) {
        return addressDao.getCNA_AddressDataList(sClientID);
    }

    public LiveData<List<EAddressUpdate>> getAddressList(){
        return addressDao.getAddressRequestList();
    }

    public LiveData<List<DAddressRequest.CustomerAddressInfo>> getAddressNames() {
        return addressDao.getAddressNames();
    }

    public LiveData<List<EMobileUpdate>> getMobileList(){
        return mobileDao.getMobileRequestList();
    }

    public LiveData<List<EMobileUpdate>> getMobileListForClient(String ClientID){
        return mobileDao.getMobileRequestListForClient(ClientID);
    }

    public void insertUpdateAddress(List<EAddressUpdate> addressUpdate){
        new UpdateAddressTask(addressDao).execute(addressUpdate);
    }

    public void deleteAddress(String TransNox){
        new DeleteAddressTask(addressDao).execute(TransNox);
    }

    public void insertUpdateMobile(List<EMobileUpdate> mobileUpdate){
        new UpdateMobileTask(mobileDao).execute(mobileUpdate);
    }

    public void deleteMobile(String TransNox){
        new DeleteMobileTask(mobileDao).execute(TransNox);
    }

    public void updateAddressStatus(String TransNox, String oldTransNox){
        addressDao.updateAddressStatus(TransNox, oldTransNox, AppConstants.DATE_MODIFIED);
    }

    public void updateMobileStatus(String TransNox, String oldTransNox) {
        mobileDao.updateMobileStatus(TransNox, oldTransNox, AppConstants.DATE_MODIFIED);
    }

    private class UpdateAddressTask extends AsyncTask<List<EAddressUpdate>, Void, String>{
        private final DAddressRequest addressDao;

        UpdateAddressTask(DAddressRequest addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected String doInBackground(List<EAddressUpdate>... lists) {
            List<EAddressUpdate> updateList = lists[0];
            for(int x = 0; x < updateList.size(); x++){
                updateList.get(x).setTransNox(getNextAddressCode());
                addressDao.insert(updateList.get(x));
            }
            return null;
        }
    }

    public static class DeleteAddressTask extends AsyncTask<String, Void, String>{
        private final DAddressRequest addressDao;

        DeleteAddressTask(DAddressRequest addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected String doInBackground(String... transNox) {
            addressDao.deleteAddressInfo(transNox[0]);
            return "";
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateMobileTask extends AsyncTask<List<EMobileUpdate>, Void, String>{
        private final DMobileRequest mobileDao;

        UpdateMobileTask(DMobileRequest mobileDao){
            this.mobileDao = mobileDao;
        }

        @Override
        protected String doInBackground(List<EMobileUpdate>... lists) {
            List<EMobileUpdate> updateList = lists[0];
            for(int x = 0; x < updateList.size(); x++){
                updateList.get(x).setTransNox(getNextMobileCode());
                mobileDao.insert(updateList.get(x));
            }
            return null;
        }
    }


    public class DeleteMobileTask extends AsyncTask<String, Void, String>{
        private final DMobileRequest mobileDao;

        DeleteMobileTask(DMobileRequest mobileDao){
            this.mobileDao = mobileDao;
        }

        @Override
        protected String doInBackground(String... transNox) {
            mobileDao.deleteMobileInfo(transNox[0]);
            return "";
        }
    }

    public String getNextMobileCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Mobile_Update_Request", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }

    public String getNextAddressCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(application);
            lsNextCode = MiscUtil.getNextCode("Address_Update_Request", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }

}
