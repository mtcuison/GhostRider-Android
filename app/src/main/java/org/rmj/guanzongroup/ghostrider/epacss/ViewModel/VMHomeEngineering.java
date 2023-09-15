package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Itinerary.Obj.EmployeeItinerary;

import java.util.List;

public class VMHomeEngineering extends AndroidViewModel {

    private final EmployeeMaster poEmploye;
    private final EmployeeItinerary poItinerary;

    public VMHomeEngineering(@NonNull Application application) {
        super(application);
        this.poEmploye = new EmployeeMaster(application);
        this.poItinerary = new EmployeeItinerary(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmploye.GetEmployeeInfo();
    }

    public LiveData<List<EItinerary>> GetItineraries(){
        return poItinerary.GetItineraryListForCurrentDay();
    }

}