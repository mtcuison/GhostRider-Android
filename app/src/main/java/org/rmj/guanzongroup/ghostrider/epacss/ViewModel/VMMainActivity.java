package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.guanzongroup.ghostrider.epacss.Service.InternetStatusReciever;

import java.util.List;

public class VMMainActivity extends AndroidViewModel {

    private final Application app;
    private final InternetStatusReciever poNetRecvr;

    private final RImageInfo poImage;
    private final RLogSelfie poSelfie;
    private final RDailyCollectionPlan poDcp;
    private final RCreditApplication poCreditApp;

    public VMMainActivity(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poNetRecvr = new InternetStatusReciever(app);
        this.poImage = new RImageInfo(app);
        this.poSelfie = new RLogSelfie(app);
        this.poDcp = new RDailyCollectionPlan(app);
        this.poCreditApp = new RCreditApplication(app);
    }

    public InternetStatusReciever getInternetReceiver(){
        return poNetRecvr;
    }
}
