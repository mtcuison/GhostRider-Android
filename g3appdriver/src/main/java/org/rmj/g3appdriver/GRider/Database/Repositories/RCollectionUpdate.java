package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;

import java.util.List;

public class RCollectionUpdate {
    private static final String TAG = "DB_Collection";
    private Application application;
    private final DAddressRequest addressDao;
    private final DMobileRequest mobileDao;

    public RCollectionUpdate(Application application){
        this.application = application;
        AppDatabase db = AppDatabase.getInstance(application);
        addressDao = db.AddressRequestDao();
        mobileDao = db.MobileRequestDao();
    }

    public void insertUpdateAddress(List<EAddressUpdate> addressUpdate){
        new UpdateAddressTask(addressDao).execute(addressUpdate);
    }

    public void insertUpdateMobile(List<EMobileUpdate> mobileUpdate){
        new UpdateMobileTask(mobileDao).execute(mobileUpdate);
    }

    private static class UpdateAddressTask extends AsyncTask<List<EAddressUpdate>, Void, String>{
        private final DAddressRequest addressDao;

        UpdateAddressTask(DAddressRequest addressDao){
            this.addressDao = addressDao;
        }

        @Override
        protected String doInBackground(List<EAddressUpdate>... eAddressUpdates) {
            addressDao.insert(eAddressUpdates[0]);
            return "";
        }
    }

    private static class UpdateMobileTask extends AsyncTask<List<EMobileUpdate>, Void, String>{
        private final DMobileRequest mobileDao;

        UpdateMobileTask(DMobileRequest mobileDao){
            this.mobileDao = mobileDao;
        }

        @SafeVarargs
        @Override
        protected final String doInBackground(List<EMobileUpdate>... eMobileUpdates) {
            mobileDao.insert(eMobileUpdates[0]);
            return "";
        }
    }

    private String getGeneratedTransNox(String fsTableName) throws Exception {
        return "";
    }
}
