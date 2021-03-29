package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcBrand;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.ReviewAppDetail;
import org.rmj.guanzongroup.onlinecreditapplication.Data.GoCasBuilder;

import java.util.ArrayList;
import java.util.List;

public class VMReviewLoanApp extends AndroidViewModel {
    private static final String TAG = VMReviewLoanApp.class.getSimpleName();

    private final RCreditApplicant poCreditApp;
    private String TransNox;

    private final RMcModel poModel;
    private final RMcBrand poBrand;
    private final RBarangay poBarangay;
    private final RTown poTown;

    private ECreditApplicantInfo poInfo = new ECreditApplicantInfo();
    private GOCASApplication poGOCas = new GOCASApplication();

    private MutableLiveData<List<ReviewAppDetail>> plAppDetail = new MutableLiveData<>();

    public VMReviewLoanApp(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplicant(application);
        this.plAppDetail.setValue(new ArrayList<>());
        this.poModel = new RMcModel(application);
        this.poBrand = new RMcBrand(application);
        this.poBarangay = new RBarangay(application);
        this.poTown = new RTown(application);
    }

    public void setTransNox(String transNox){
        this.TransNox = transNox;
    }

    public LiveData<ECreditApplicantInfo> getApplicantInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox);
    }

    public void setCreditAppInfo(ECreditApplicantInfo foInfo){
        this.poInfo = foInfo;
    }

    public LiveData<List<ReviewAppDetail>> getAppDetail(){
        List<ReviewAppDetail> loListDetl = new ArrayList<>();
        try {
            GOCASApplication loGOCas = new GOCASApplication();
            loGOCas.setData(new GoCasBuilder(poInfo).getConstructedDetailedInfo());
            loListDetl.add(new ReviewAppDetail(true, "Purchase Info", "", ""));
            loListDetl.add(new ReviewAppDetail(false, "", "Branch", loGOCas.PurchaseInfo().getBrandName()));
            loListDetl.add(new ReviewAppDetail(false, "", "Unit Applied", loGOCas.PurchaseInfo().getBrandName()));
            loListDetl.add(new ReviewAppDetail(false, "", "Model", loGOCas.PurchaseInfo().getBrandName()));
            loListDetl.add(new ReviewAppDetail(false, "", "Downpayment", loGOCas.PurchaseInfo().getBrandName()));
            loListDetl.add(new ReviewAppDetail(false, "", "Branch", loGOCas.PurchaseInfo().getBrandName()));
            loListDetl.add(new ReviewAppDetail(false, "", "Branch", loGOCas.PurchaseInfo().getBrandName()));
            loListDetl.add(new ReviewAppDetail(false, "", "Branch", loGOCas.PurchaseInfo().getBrandName()));
        } catch (Exception e){
            e.printStackTrace();
        }

        return plAppDetail;
    }

}