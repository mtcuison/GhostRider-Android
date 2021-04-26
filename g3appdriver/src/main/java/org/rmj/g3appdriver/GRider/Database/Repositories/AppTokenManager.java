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

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.annotation.SuppressLint;
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

    @SuppressLint("StaticFieldLeak")
    private class InsertTokenTask extends AsyncTask<ETokenInfo, Void, String>{

        @Override
        protected String doInBackground(ETokenInfo... eTokenInfos) {
            if(dao.getTokenInfo() != null) {
                if(dao.getTokenInfo().equalsIgnoreCase("temp_token")) {
                    dao.clearTokenInfo();
                    dao.insertTokenInfo(eTokenInfos[0]);
                }
            } else {
                dao.insertTokenInfo(eTokenInfos[0]);
            }
            return "";
        }
    }
}
