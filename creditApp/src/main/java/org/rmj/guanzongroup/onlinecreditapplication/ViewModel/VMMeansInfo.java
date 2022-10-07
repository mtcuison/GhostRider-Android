/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Repositories.RCreditApplicant;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_EmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_Finance;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PensionInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SelfEmployedInfo;

import java.util.ArrayList;
import java.util.List;

public class VMMeansInfo extends AndroidViewModel {
    private final RCreditApplicant poApplcnt;
    private final MutableLiveData<String> psTranNo = new MutableLiveData<>();
    private final MutableLiveData<String> psPrmMns = new MutableLiveData<>();

    public VMMeansInfo(@NonNull Application application) {
        super(application);
        poApplcnt = new RCreditApplicant(application);
    }

    public void setTransNox(String transNox){
        this.psTranNo.setValue(transNox);
    }

    public LiveData<String> getMeansInfo(){
        return poApplcnt.getAppMeansInfo(psTranNo.getValue());
    }

    public LiveData<List<Fragment>> getMeansInfoPages(String MeansInfos){
        MutableLiveData<List<Fragment>> loMeansPg = new MutableLiveData<>();
        List<Fragment> liFragmnt = new ArrayList<>();
        try {
            JSONObject loJson = new JSONObject(MeansInfos);
            psPrmMns.setValue(loJson.getString("means"));
            String lsEmployd = loJson.getString("employed");
            String lsSEmplyd = loJson.getString("sEmployd");
            String lsFinance = loJson.getString("finance");
            String lsPension = loJson.getString("pension");
            if(lsEmployd.equalsIgnoreCase("1")){
                liFragmnt.add(new Fragment_EmploymentInfo());
            }
            if(lsSEmplyd.equalsIgnoreCase("1")){
                liFragmnt.add(new Fragment_SelfEmployedInfo());
            }
            if(lsFinance.equalsIgnoreCase("1")){
                liFragmnt.add(new Fragment_Finance());
            }
            if(lsPension.equalsIgnoreCase("1")){
                liFragmnt.add(new Fragment_PensionInfo());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loMeansPg.setValue(liFragmnt);
        return loMeansPg;
    }
}