package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;

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
