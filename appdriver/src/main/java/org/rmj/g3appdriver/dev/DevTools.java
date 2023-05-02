package org.rmj.g3appdriver.dev;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DevTool;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.gCircle.EmployeeMaster;
import org.rmj.g3appdriver.lib.Account.gCircle.EmployeeSession;

public class DevTools {
    private static final String TAG = DevTools.class.getSimpleName();

    private final Application instance;

    private final DevTool poDao;
    private final EmployeeSession poSession;
    private final AppConfigPreference poConfig;
    private final EmployeeMaster poUser;

    private String message;

    public DevTools(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GCircleDB.getInstance(instance).devTool();
        this.poSession = new EmployeeSession(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poUser = new EmployeeMaster(instance);
    }

    public String getMessage() {
        return message;
    }

    public LiveData<EEmployeeInfo> GetUserInfo(){
        return poDao.GetUserInfo();
    }

    public boolean Update(EEmployeeInfo args){
        try{
            poDao.Update(args);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean SetDefault(){
        try{
            EEmployeeInfo loDetail = poDao.GetUser();
            loDetail.setPositnID(poSession.getPositionID());
            loDetail.setEmpLevID(Integer.parseInt(poSession.getEmployeeLevel()));
            loDetail.setDeptIDxx(poSession.getDeptID());
            poDao.Update(loDetail);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean GetTestStatus(){
        return poConfig.getTestStatus();
    }

    public boolean SetTestStatus(boolean isTest){
        try {
            poConfig.setTestCase(isTest);
            poUser.LogoutUserSession();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
