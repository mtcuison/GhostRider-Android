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

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EClientUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RImageInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RTown;

public class VMLogClientDetail extends AndroidViewModel {
    private static final String TAG = VMLogClientDetail.class.getSimpleName();
    private RImageInfo poImage;
    private RDailyCollectionPlan poCollect;
    private RTown poTown;

    public VMLogClientDetail(@NonNull Application application){
        super(application);
        this.poImage = new RImageInfo(application);
        this.poCollect = new RDailyCollectionPlan(application);
        this.poTown = new RTown(application);
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return poImage.getImageLocation(sDtlSrcNo, sImageNme);
    }

    public LiveData<EClientUpdate> getClientUpdateInfo(String AccountNox){
        return poCollect.getClientUpdateInfo(AccountNox);
    }

    public LiveData<DTownInfo.BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String fsID){
        return poTown.getBrgyTownProvinceInfo(fsID);
    }

    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID){
        return poTown.getTownProvinceInfo(fsID);
    }
}
