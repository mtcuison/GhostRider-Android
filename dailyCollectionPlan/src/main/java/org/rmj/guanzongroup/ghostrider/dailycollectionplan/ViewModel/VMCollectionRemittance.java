/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRemittanceAccount;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;

public class VMCollectionRemittance extends AndroidViewModel {

    private final LRDcp poSys;

    private final EmployeeMaster poUser;
    private final RBranch poBranch;
    private final ConnectionUtil poConn;

    private String dTransact;
    private double nTotRCash = 0;
    private double nTotRChck = 0;
    private double nTotCashx = 0;
    private double nTotCheck = 0;

    private String psCltCashx = "0.0", psCltCheck = "0.0";
    private final MutableLiveData<String> psCashOHnd = new MutableLiveData<>();
    private final MutableLiveData<String> psCheckOHx = new MutableLiveData<>();

    public interface OnRemitCollectionCallback{
        void OnRemit();
        void OnSuccess();
        void OnFailed(String message);
    }

    public interface OnInitializeBranchAccountCallback{
        void OnDownload();
        void OnSuccessDownload();
        void OnFailedDownload(String message);
    }

    public VMCollectionRemittance(@NonNull Application application) {
        super(application);
        this.poSys = new LRDcp(application);
        this.poUser = new EmployeeMaster(application);
        this.poBranch = new RBranch(application);
        this.poConn = new ConnectionUtil(application);
    }



    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<String> GetTotalCollection(String fsVal){
        return poSys.GetTotalCollection()
    }
}
