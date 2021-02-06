package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;

public class VMFinance extends AndroidViewModel {
    private static final String TAG = VMFinance.class.getSimpleName();
    private RCountry poCountry;

    public VMFinance(@NonNull Application application) {
        super(application);
        poCountry = new RCountry(application);
    }

    public LiveData<String[]> getCountryNameList(){
        return poCountry.getAllCountryNames();
    }
}