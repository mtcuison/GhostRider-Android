package org.rmj.guanzongroup.ghostrider.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.lib.Panalo.data.entity.ERaffleStatus;
import org.rmj.g3appdriver.lib.Panalo.obj.ILOVEMYJOB;

public class VMPanaloContainer extends AndroidViewModel {
    private static final String TAG = VMPanaloContainer.class.getSimpleName();

    private final ILOVEMYJOB poPanalo;

    public VMPanaloContainer(@NonNull Application application) {
        super(application);
        this.poPanalo = new ILOVEMYJOB(application);
    }

    public LiveData<ERaffleStatus> GetRaffleStatus(){
        return poPanalo.GetRaffleStatus();
    }
}
