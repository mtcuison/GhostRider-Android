package org.rmj.g3appdriver.GCircle.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.GCircle.ImportData.model.ImportInstance;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcModel;

public class Import_MCCashPrice implements ImportInstance {
    public static final String TAG = Import_MCCashPrice.class.getSimpleName();
    private final RMcModel poSys;

    public Import_MCCashPrice(Application instance){
        this.poSys = new RMcModel(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportCashPrices()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
