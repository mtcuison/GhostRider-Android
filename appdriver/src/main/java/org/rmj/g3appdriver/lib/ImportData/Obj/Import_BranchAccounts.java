package org.rmj.g3appdriver.lib.ImportData.Obj;

import android.app.Application;

import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RRemittanceAccount;
import org.rmj.g3appdriver.lib.ImportData.model.ImportDataCallback;
import org.rmj.g3appdriver.lib.ImportData.model.ImportInstance;

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
