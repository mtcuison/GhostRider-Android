/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/17/21 11:01 AM
 * project file last modified : 5/17/21 11:01 AM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;

public class VMCIHistoryInfo extends AndroidViewModel {
    private static final String TAG = VMApplicationList.class.getSimpleName();
    private final Application instance;
    private final MutableLiveData<String> sCredInvxx = new MutableLiveData<>();
    private final REmployee poEmploye;
    private final RCIEvaluation poCI;
    public VMCIHistoryInfo(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmploye = new REmployee(application);
        this.poCI = new RCIEvaluation(application);
    }

    public LiveData<EEmployeeInfo> getEmplopyeInfo(){
        return this.poEmploye.getEmployeeInfo();
    }
    public void setEmployeeID(String empID){
        this.sCredInvxx.setValue(empID);
    }
    public LiveData<ECIEvaluation> getCIByTransNox(String transNox){
        return poCI.getAllCIApplication(transNox);
    }
    public String getAnswer(String fsAnswer) {
        if(fsAnswer.equalsIgnoreCase("0")) {
            return "No";
        }
        return "Yes";
    }

    String getVibes(String fsFeedBck) {
        if(fsFeedBck.equalsIgnoreCase("0")) {
            return "Negative Feedback";
        }
        return "Positive Feedback";
    }

    public String parseHouseOwn(String fsOwnrshp) {
        if(fsOwnrshp.equalsIgnoreCase("0")) {
            return "Owned";
        } else if(fsOwnrshp.equalsIgnoreCase("1")) {
            return "Rent";
        } else if(fsOwnrshp.equalsIgnoreCase("2")) {
            return "Care-Taker";
        }
        return null;
    }

    public String parseHouseHold(String sHousehld) {
        if(sHousehld.equalsIgnoreCase("0")) {
            return "Living With Family";
        } else if(sHousehld.equalsIgnoreCase("1")) {
            return "Living With Family(Parents & Siblings)";
        } else if(sHousehld.equalsIgnoreCase("2")) {
            return "Living With Relatives";
        }
        return null;
    }

    public String parseHouseType(String fsHouseTp) {
        if(fsHouseTp.equalsIgnoreCase("0")) {
            return "Concrete";
        } else if(fsHouseTp.equalsIgnoreCase("1")) {
            return "Combination(Wood & Concrete)";
        } else if(fsHouseTp.equalsIgnoreCase("2")) {
            return "Wood/Nipa";
        }
        return null;
    }

    String parseAmtToString(String fsAmount) {
        return "₱" + Double.parseDouble(fsAmount);
    }

}
