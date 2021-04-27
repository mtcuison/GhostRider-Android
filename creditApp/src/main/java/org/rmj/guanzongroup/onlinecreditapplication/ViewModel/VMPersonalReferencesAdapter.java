package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;

public class VMPersonalReferencesAdapter extends AndroidViewModel {
    private final RTown poTown;

    public VMPersonalReferencesAdapter(@NonNull Application application) {
        super(application);
            this.poTown = new RTown(application);
    }

    public LiveData<DTownInfo.BrgyTownProvinceInfo> getTownProvinceInfo(String fsID) {
        return poTown.getTownProvinceInfo(fsID);
    }

}
