package org.rmj.guanzongroup.ghostrider.epacss.ui.home;

import android.app.Application;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EItinerary;
import org.rmj.g3appdriver.dev.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.dev.Database.Repositories.RNotificationInfo;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Account.SessionManager;
import org.rmj.g3appdriver.lib.Itinerary.EmployeeItinerary;
import org.rmj.g3appdriver.lib.Notifications.Obj.BranchOpeningMonitor;

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