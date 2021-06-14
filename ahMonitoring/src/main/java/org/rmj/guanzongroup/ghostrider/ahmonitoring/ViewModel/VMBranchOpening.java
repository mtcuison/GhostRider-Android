/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchOpeningMonitor;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMBranchOpening extends AndroidViewModel {

    private final Application instance;
    private final RBranchOpeningMonitor poOpening;
    private final SessionManager poSession;
    private final REmployee poUser;

    private final MutableLiveData<String> psDateSelected = new MutableLiveData<>();

    public VMBranchOpening(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSession = new SessionManager(instance);
        this.poOpening = new RBranchOpeningMonitor(instance);
        psDateSelected.setValue(AppConstants.CURRENT_DATE);
        this.poUser = new REmployee(instance);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public void setDateSelected(String lsDate){
        try {
            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat lsFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String date = lsFormatter.format(Objects.requireNonNull(loDate));
            psDateSelected.setValue(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public LiveData<String> getDateSelected(){
        return psDateSelected;
    }

    public LiveData<List<EBranchOpenMonitor>> getBranchOpeningsForDate(String dTransact){
        return poOpening.getBranchOpeningForDate(dTransact);
    }
}