package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SupportSQLiteQuery;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Entities.ECodeApproval;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;

import java.util.List;

public class RApprovalCode {
    private final DApprovalCode approvalDao;

    public RApprovalCode(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        approvalDao = appDatabase.ApprovalDao();
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
}
