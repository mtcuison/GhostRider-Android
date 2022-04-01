package org.rmj.g3appdriver.lib.BullsEye;

import android.app.Application;

public class RandomStockInventoryManager {
    private static final String TAG = RandomStockInventoryManager.class.getSimpleName();

    private final Application instance;

    private String message;

    public RandomStockInventoryManager(Application application) {
        this.instance = application;
    }

    public boolean RequestInventoryItems() {
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
        }
        return false;
    }
}
