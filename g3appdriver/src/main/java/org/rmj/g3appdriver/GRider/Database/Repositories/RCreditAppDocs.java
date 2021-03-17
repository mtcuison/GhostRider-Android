package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditAppDocs;

public class RCreditAppDocs {

    private final DCreditAppDocs docsDao;

    public RCreditAppDocs(Application application){
        this.docsDao = AppDatabase.getInstance(application).CreditAppDocsDao();
    }
}
