package org.rmj.g3appdriver.GCircle.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.GCircle.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.GCircle.ImportData.model.ImportInstance;
import org.rmj.g3appdriver.GCircle.room.Repositories.RRemittanceAccount;

public class Import_BranchAccounts implements ImportInstance {

    private final RRemittanceAccount poSys;

    public Import_BranchAccounts(Application instance) {
        this.poSys = new RRemittanceAccount(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        if(!poSys.ImportRemittanceAccounts()){
            callback.OnFailedImportData(poSys.getMessage());
        } else {
            callback.OnSuccessImportData();
        }
    }
}
