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
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.dev.Database.Repositories.RImageInfo;

public class VMLogPromiseToPay extends AndroidViewModel {
    private RDailyCollectionPlan poCollect;
    private RImageInfo poImage;
    private RBranch poBranch;

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psAcctNox = new MutableLiveData<>();
    private final MutableLiveData<String> psRemCodex = new MutableLiveData<>();

    public VMLogPromiseToPay(@NonNull Application application) {
        super(application);
        this.poCollect = new RDailyCollectionPlan(application);
        this.poImage = new RImageInfo(application);
        this.poBranch = new RBranch(application);
    }

    public void setParameters(String TransNox, String Acctnox, String RemCodex) {
        this.psTransNox.setValue(TransNox);
        this.psAcctNox.setValue(Acctnox);
        this.psRemCodex.setValue(RemCodex);
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return poImage.getImageLocation(sDtlSrcNo, sImageNme);
    }

    public LiveData<EDCPCollectionDetail> getPostedCollectionDetail() {
        return poCollect.getPostedCollectionDetail(psTransNox.getValue(), psAcctNox.getValue(), psRemCodex.getValue());
    }

    public LiveData<String> getBranchName(String BranchCde) {
        return poBranch.getBranchName(BranchCde);
    }


}
