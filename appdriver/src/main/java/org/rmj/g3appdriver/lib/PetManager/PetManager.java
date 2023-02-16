package org.rmj.g3appdriver.lib.PetManager;

import android.app.Application;

import org.rmj.g3appdriver.lib.PetManager.Obj.EmployeeLeave;
import org.rmj.g3appdriver.lib.PetManager.Obj.EmployeeOB;
import org.rmj.g3appdriver.lib.PetManager.model.iPM;

public class PetManager {
    private static final String TAG = PetManager.class.getSimpleName();

    private final Application instance;

    public PetManager(Application instance) {
        this.instance = instance;
    }

    public enum ePetManager{
        LEAVE_APPLICATION,
        BUSINESS_TRIP_APPLICATION
    }

    public iPM GetInstance(ePetManager args){
        switch (args){
            case LEAVE_APPLICATION:
                return new EmployeeLeave(instance);
            case BUSINESS_TRIP_APPLICATION:
                return new EmployeeOB(instance);
        }
        return null;
    }
}
