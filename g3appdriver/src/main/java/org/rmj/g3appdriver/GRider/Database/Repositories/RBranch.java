package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;

import java.util.List;

public class RBranch {
    private final DBranchInfo branchInfoDao;
    private final LiveData<List<EBranchInfo>> allMcBranchInfo;
    private final LiveData<List<EBranchInfo>> allBranchInfo;
    private final LiveData<String[]> allMcBranchNames;
    private final LiveData<String[]> allBranchNames;

    public RBranch(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        branchInfoDao = appDatabase.BranchDao();
        allMcBranchNames = branchInfoDao.getMCBranchNames();
        allMcBranchInfo = branchInfoDao.getAllMcBranchInfo();
        allBranchNames = branchInfoDao.getAllBranchNames();
        allBranchInfo = branchInfoDao.getAllBranchInfo();
    }

    public void insertBulkData(List<EBranchInfo> branchInfoList){
        branchInfoDao.insertBulkData(branchInfoList);
    }

    public LiveData<List<EBranchInfo>> getAllMcBranchInfo() {
        return allMcBranchInfo;
    }

    public LiveData<String[]> getAllMcBranchNames(){
        return allMcBranchNames;
    }

    public LiveData<String[]> getAllBranchNames(){
        return allBranchNames;
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return allBranchInfo;
    }

    public String getPromoDivision(){
        return branchInfoDao.getPromoDivision();
    }
}
