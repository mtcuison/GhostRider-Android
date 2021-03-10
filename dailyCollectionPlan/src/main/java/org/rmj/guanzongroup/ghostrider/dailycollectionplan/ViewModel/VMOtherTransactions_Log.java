package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

public class VMOtherTransactions_Log extends AndroidViewModel {
    private static final String TAG = VMOtherTransactions_Log.class.getSimpleName();
    private RImageInfo poImage;

    public VMOtherTransactions_Log(@NonNull Application application) {
        super(application);
        this.poImage = new RImageInfo(application);
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return poImage.getImageLocation(sDtlSrcNo, sImageNme);
    }
}
