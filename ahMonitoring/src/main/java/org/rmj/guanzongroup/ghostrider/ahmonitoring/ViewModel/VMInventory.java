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

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;

import java.util.ArrayList;
import java.util.List;


public class VMInventory extends AndroidViewModel {

    private final MutableLiveData<List<RandomItem>> psRandom = new MutableLiveData<>();
    private final RBranch poBranch;
    public VMInventory(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        List<RandomItem> randomItems = new ArrayList<>();
        randomItems.add(new RandomItem("1000438", "N0842736853", "Tmx 155 Cam Follower"));
        randomItems.add(new RandomItem("1005049", "N0740345581", "Tmx 125 Alpha Side Cover"));
        randomItems.add(new RandomItem("1007564", "N0722848986", "Honda Beat Headlight Fairing"));
        randomItems.add(new RandomItem("1007572", "N0722848989", "Honda Beat Grab Bar"));
        randomItems.add(new RandomItem("1007580", "N0722848998", "Honda Click Exhaust"));
        psRandom.setValue(randomItems);
    }
    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }
    public LiveData<List<RandomItem>> getRandomItemList() {
        return psRandom;
    }
}