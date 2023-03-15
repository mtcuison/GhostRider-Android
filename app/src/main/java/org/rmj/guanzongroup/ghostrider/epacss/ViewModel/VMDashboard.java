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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Notifications.Obj.Payslip;
import org.rmj.g3appdriver.lib.SelfieLog.SelfieLog;

import java.util.List;

public class VMDashboard extends AndroidViewModel {

    private final EmployeeMaster poEmploye;
    private final SelfieLog poLog;
    private final AppConfigPreference poConfig;
    private MutableLiveData<String> psMobleNo = new MutableLiveData<>();

    private final Payslip poPaySlip;

    public VMDashboard(@NonNull Application application) {
        super(application);
        this.poEmploye = new EmployeeMaster(application);
        this.psMobleNo.setValue(new Telephony(application).getMobilNumbers());
        this.poLog = new SelfieLog(application);
        this.poConfig = AppConfigPreference.getInstance(application);
        this.poPaySlip = new Payslip(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
    }

    public LiveData<String> getMobileNo() {
        return psMobleNo;
    }

    public LiveData<List<ESelfieLog>> getCurrentLogTimeIfExist(){
        return poLog.getCurrentLogTimeIfExist(AppConstants.CURRENT_DATE);
    }

    public LiveData<Integer> GetUnreadPayslipCount(){
        return poPaySlip.GetUnreadPayslipCount();
    }
}