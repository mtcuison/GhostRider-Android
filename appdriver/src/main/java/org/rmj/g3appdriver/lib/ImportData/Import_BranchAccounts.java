package org.rmj.g3appdriver.lib.ImportData;

import android.app.Application;

import org.rmj.g3appdriver.dev.Database.Repositories.RRemittanceAccount;

public class Import_BranchAccounts implements ImportInstance{

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
