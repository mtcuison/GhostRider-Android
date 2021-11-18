package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryDetail;

public class VMInventoryEntry extends AndroidViewModel {
    private static final String TAG = VMInventoryEntry.class.getSimpleName();

    private final Application instance;
    private final RInventoryDetail poDetail;

    public VMInventoryEntry(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDetail = new RInventoryDetail(instance);
    }

}
