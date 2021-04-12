package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpousePensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.Objects;

public class VMSpousePensionInfo extends AndroidViewModel {
    private static final String TAG = VMSpousePensionInfo.class.getSimpleName();
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private ECreditApplicantInfo poInfo;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<String> psPensionSec = new MutableLiveData<>();

    public VMSpousePensionInfo(@NonNull Application application) {
        super(application);
        this.poGoCas = new GOCASApplication();
        this.poCreditApp = new RCreditApplicant(application);
    }

    public boolean setTransNox(String transNox) {
        this.psTransNo.setValue(transNox);
        if(!this.psTransNo.getValue().equalsIgnoreCase(transNox)) {
            return false;
        }
        return true;
    }

    public boolean setPensionSec(String fsPensionSec) {
        this.psPensionSec.setValue(fsPensionSec);
        if(!this.psPensionSec.getValue().equalsIgnoreCase(fsPensionSec)) {
            return false;
        }
        return true;
    }

    public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNo.getValue());
    }

    public boolean setDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo = fsDetailInfo;
            poGoCas.setData(poInfo.getDetlInfo());
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean Save(SpousePensionInfoModel infoModel, ViewModelCallBack callBack) {
        try{
            infoModel.setsPensionSector(psPensionSec.getValue());
            if(infoModel.isPensionDataValid()) {
                poGoCas.SpouseMeansInfo().PensionerInfo().setSource(infoModel.getsPensionSector());
                poGoCas.SpouseMeansInfo().PensionerInfo().setAmount(infoModel.getsPensionAmt());
                poGoCas.SpouseMeansInfo().PensionerInfo().setYearRetired(infoModel.getsRetirementYr());
                poGoCas.SpouseMeansInfo().setOtherIncomeNature(infoModel.getsOtherSrc());
                poGoCas.SpouseMeansInfo().setOtherIncomeAmount(infoModel.getsOtherSrcIncx());

                poInfo.setSpsPensn(poGoCas.SpouseMeansInfo().toJSONString());
                poCreditApp.updateGOCasData(poInfo);

                Log.e(TAG, poGoCas.SpouseMeansInfo().toJSONString());
                Log.e(TAG, "GOCAS Full JSON String : " + poGoCas.toJSONString());
                callBack.onSaveSuccessResult("Success");
                return true;
            }
            else {
                callBack.onFailedResult(infoModel.getsMgs());
                return false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
            return false;
        }
    }

}