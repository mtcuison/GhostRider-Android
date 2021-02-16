package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
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

    public LiveData<List<EAddressUpdate>> getAddressList(){
        return addressDao.getAddressRequestList();
    }

    public LiveData<List<DAddressRequest.CustomerAddressInfo>> getAddressNames() {
        return addressDao.getAddressNames();
    }

    public LiveData<List<EMobileUpdate>> getMobileList(){
        return mobileDao.getMobileRequestList();
    }

    public void insertUpdateAddress(EAddressUpdate addressUpdate){
        new UpdateAddressTask(addressDao).execute(addressUpdate);
    }

    public void deleteAddress(String TransNox){
        new DeleteAddressTask(addressDao).execute(TransNox);
    }

    public void insertUpdateMobile(EMobileUpdate mobileUpdate){
        new UpdateMobileTask(mobileDao).execute(mobileUpdate);
    }

    public void deleteMobile(String TransNox){
        new DeleteMobileTask(mobileDao).execute(TransNox);
    }

    private static class UpdateAddressTask extends AsyncTask<EAddressUpdate, Void, String>{
        private final DAddressRequest addressDao;

        UpdateAddressTask(DAddressRequest addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected String doInBackground(EAddressUpdate... eAddressUpdates) {
            addressDao.insert(eAddressUpdates[0]);
            return "";
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

    private static class UpdateMobileTask extends AsyncTask<EMobileUpdate, Void, String>{
        private final DMobileRequest mobileDao;

        UpdateMobileTask(DMobileRequest mobileDao){
            this.mobileDao = mobileDao;
        }

        @SafeVarargs
        @Override
        protected final String doInBackground(EMobileUpdate... eMobileUpdates) {
            mobileDao.insert(eMobileUpdates[0]);
            return "";
        }
    }


    public static class DeleteMobileTask extends AsyncTask<String, Void, String>{
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
}
