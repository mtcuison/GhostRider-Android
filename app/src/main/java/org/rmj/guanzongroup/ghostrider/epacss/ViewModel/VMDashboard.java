/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;

import java.util.List;

public class VMDashboard extends AndroidViewModel {

    private final SessionManager poSession;
    private final EmployeeMaster poEmploye;
    private final SelfieLog poLog;
    private MutableLiveData<String> psEmailxx = new MutableLiveData<>();
    private MutableLiveData<String> psUserNme = new MutableLiveData<>();
    private MutableLiveData<String> psBranchx = new MutableLiveData<>();
    private MutableLiveData<String> psDeptNme = new MutableLiveData<>();
    private MutableLiveData<String> psMobleNo = new MutableLiveData<>();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public VMDashboard(@NonNull Application application) {
        super(application);
        poSession = new SessionManager(application);
        poEmploye = new EmployeeMaster(application);
        psMobleNo.setValue(new Telephony(application).getMobilNumbers());
        poLog = new SelfieLog(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
    }

    public LiveData<String> getMobileNo() {
        return psMobleNo;
    }

    public LiveData<List<ESelfieLog>> getCurrentLogTimeIfExist(){
        return poLog.getCurrentLogTimeIfExist(new AppConstants().CURRENT_DATE);
    }
}