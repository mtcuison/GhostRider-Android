/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeRole;
import org.rmj.g3appdriver.lib.Panalo.obj.ILOVEMYJOB;
import org.rmj.g3appdriver.utils.Task.OnDoBackgroundTaskListener;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataSyncService;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboard.Fragment_AHDashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboard.Fragment_BHDashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboard.Fragment_Dashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboard.Fragment_Eng_Dashboard;

import java.util.List;

public class VMMainActivity extends AndroidViewModel {
    private static final String TAG = "GRider Main Activity";
    private final Application app;
    private final DataSyncService poNetRecvr;
    private final EmployeeMaster poUser;
    private final ILOVEMYJOB poPanalo;
    private String message;

    public VMMainActivity(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poNetRecvr = new DataSyncService(app);
        this.poUser = new EmployeeMaster(app);
        this.poPanalo = new ILOVEMYJOB(app);
    }

    public DataSyncService getInternetReceiver() {
        return poNetRecvr;
    }

    public LiveData<List<EEmployeeRole>> getEmployeeRole() {
        return poUser.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles() {
        return poUser.getChildRoles();
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo() {
        return poUser.GetEmployeeInfo();
    }

    public Fragment GetUserFragments(EEmployeeInfo args) {
        Fragment userLevel;
        switch (args.getEmpLevID()) {

            case 3:
                userLevel = new Fragment_BHDashboard();
                break;
            case 4:
                userLevel = new Fragment_AHDashboard();
                break;
            default:
                switch (args.getDeptIDxx()) {
                    case "032":
                        userLevel = new Fragment_Eng_Dashboard();
                        break;
                    default:
                        userLevel = new Fragment_Dashboard();
                        break;
                }
        }
        return userLevel;
    }

    public void ResetRaffleStatus() {
//        new ResetPanaloStatusTask().execute();
        TaskExecutor.Execute(null, new OnDoBackgroundTaskListener() {
            @Override
            public Object DoInBackground(Object args) {
                if (!poPanalo.ResetRaffleStatus()) {
                    message = poPanalo.getMessage();
                    return false;
                }
                return true;
            }

            @Override
            public void OnPostExecute(Object object) {

            }
        });
    }
}
//    private class ResetPanaloStatusTask extends AsyncTask<Void, Void, Boolean>{
//
//        private String message;
//
//        @Override
//        protected Boolean doInBackground(Void... voids) {
//            if(!poPanalo.ResetRaffleStatus()){
//                message = poPanalo.getMessage();
//                return false;
//            }
//            return true;
//        }
//    }
//}
