package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

public class VMSelfieLogin extends AndroidViewModel {
    private static final String TAG = VMSelfieLogin.class.getSimpleName();

    private final RImageInfo poImage;
    private final REmployee poUser;

    public VMSelfieLogin(@NonNull Application application) {
        super(application);
        this.poImage = new RImageInfo(application);
        this.poUser = new REmployee(application);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public void insertImageInfo(EImageInfo loImage){
        poImage.insertImageInfo(loImage);
    }
}