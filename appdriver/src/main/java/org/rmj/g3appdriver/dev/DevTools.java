package org.rmj.g3appdriver.dev;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DevTool;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.etc.SessionManager;

public class DevTools {
    private static final String TAG = DevTools.class.getSimpleName();

    private final Application instance;

    private final DevTool poDao;
    private final SessionManager poSession;

    private String message;

    public DevTools(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).devTool();
        this.poSession = new SessionManager(instance);
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



            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }
}
