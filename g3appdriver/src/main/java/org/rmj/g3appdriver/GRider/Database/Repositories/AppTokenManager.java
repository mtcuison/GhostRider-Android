package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GRider.Database.Entities.ETokenInfo;


public class AppTokenManager {

    private final DRawDao dao;

    public AppTokenManager(Application instance){
        GGC_GriderDB db = GGC_GriderDB.getInstance(instance);
        this.dao = db.RawDao();
    }

    public void setTokenInfo(ETokenInfo tokenInfo){
        new InsertTokenTask().execute(tokenInfo);
    }

    private class InsertTokenTask extends AsyncTask<ETokenInfo, Void, String>{

        @Override
        protected String doInBackground(ETokenInfo... eTokenInfos) {
            dao.clearTokenInfo();
            dao.insertTokenInfo(eTokenInfos[0]);
            return "";
        }
    }
}
