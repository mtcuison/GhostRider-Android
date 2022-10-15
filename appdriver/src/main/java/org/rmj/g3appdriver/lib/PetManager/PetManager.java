package org.rmj.g3appdriver.lib.PetManager;

import android.app.Application;

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

    public iPetManager GetInstance(ePetManager args){
        switch (args){
            case LEAVE_APPLICATION:
                return new EmployeeLeave(instance);
            case BUSINESS_TRIP_APPLICATION:
                return new EmployeeOB(instance);
        }
        return null;
    }
}
