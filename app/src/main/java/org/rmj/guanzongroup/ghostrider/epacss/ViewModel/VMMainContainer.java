package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;

public class VMMainContainer extends AndroidViewModel {

    private final REmployee poUser;

    public VMMainContainer(@NonNull Application application) {
        super(application);
        this.poUser = new REmployee(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poUser.getEmployeeInfo();
    }
}