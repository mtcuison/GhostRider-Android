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

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;

import java.util.List;

public class RApprovalCode {
    private final DApprovalCode approvalDao;

    public RApprovalCode(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        approvalDao = GGCGriderDB.ApprovalDao();
    }

    public void insert(ECodeApproval codeApproval){
        approvalDao.insert(codeApproval);
    }

    public void update(ECodeApproval codeApproval){
        approvalDao.update(codeApproval);
    }

    public void insertBulkData(List<ESCA_Request> requestList){
        approvalDao.insertBulkData(requestList);
    }

    public LiveData<ECodeApproval> getCodeApprovalInfo(){
        return approvalDao.getCodeApprovalEntry();
    }

    public LiveData<List<ESCA_Request>> getAuthReferenceList(){
        return approvalDao.getSCA_AuthReference();
    }

    public LiveData<List<ESCA_Request>> getAuthNameList(){
        return approvalDao.getSCA_AuthName();
    }

    public LiveData<List<ESCA_Request>> getAuthorizedFeatures(SupportSQLiteQuery sqLiteQuery){
        return approvalDao.getAuthorizedFeatures(sqLiteQuery);
    }

    public LiveData<String> getApprovalDesc(String appCode){
        return approvalDao.getApprovalDesc(appCode);
    }
}
