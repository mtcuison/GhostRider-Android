/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;

public class VMApplicationList extends AndroidViewModel {
    private static final String TAG = VMApplicationList.class.getSimpleName();
    private final Application instance;

    private final MutableLiveData<String> sCredInvxx = new MutableLiveData<>();


    private final REmployee poEmploye;
    public VMApplicationList(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmploye = new REmployee(application);
    }

    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public void setEmployeeID(String empID){
        this.sCredInvxx.setValue(empID);
    }
}