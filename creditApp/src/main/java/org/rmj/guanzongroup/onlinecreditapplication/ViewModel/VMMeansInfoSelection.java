package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMMeansInfoSelection extends AndroidViewModel {
    private static final String TAG = VMMeansInfoSelection.class.getSimpleName();
    private final RCreditApplicant poCreditA;
    private final GOCASApplication poGOCasxx;
    private final MutableLiveData<Integer> pnPageIDx = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<List<String>> psMeansIf = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poJsonMnx = new MutableLiveData<>();

    public VMMeansInfoSelection(@NonNull Application application) {
        super(application);
        this.poCreditA = new RCreditApplicant(application);
        this.poGOCasxx = new GOCASApplication();
        this.psMeansIf.setValue(new ArrayList<>());
        this.poJsonMnx.setValue(new JSONObject());
    }

    public void setTransNox(String TransNox){
        this.psTransNo.setValue(TransNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicantInfo(String TransNox){
        return poCreditA.getCreditApplicantInfoLiveData(TransNox);
    }

    public void setGOCasDetailInfo(String DetailInfo){
        try{
            poGOCasxx.setData(DetailInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean addMeansInfo(String MeansInfo){
        try {
            Objects.requireNonNull(psMeansIf.getValue()).add(MeansInfo);
            Log.e(TAG, MeansInfo + " has been added to list");
            Log.e(TAG, psMeansIf.getValue().get(0) + " is index 0");
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean removeMeansInfo(String MeansInfo){
        try {
            for (int x = 0; x < psMeansIf.getValue().size(); x++) {
                if (MeansInfo.equalsIgnoreCase(psMeansIf.getValue().get(x))) {
                    psMeansIf.getValue().remove(x);
                }
            }
            Log.e(TAG, MeansInfo + " has been remove to list");
            Log.e(TAG, psMeansIf.getValue().get(0) + " is index 0");
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private int getNextPage(){
        if(Objects.requireNonNull(psMeansIf.getValue()).size() > 0){
            switch (psMeansIf.getValue().get(0)){
                case "employed":
                    return 3;
                case "self-employed":
                    return 4;
                case "finance":
                    return 5;
                case "pension":
                    return 6;
            }
        }
        return 2;
    }

    public void SaveMeansInfo(MeansInfo infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isMeansInfoValid()){
                poGOCasxx.MeansInfo().setIncomeSource("1");
                ECreditApplicantInfo applicantInfo = new ECreditApplicantInfo();
                applicantInfo.setTransNox(Objects.requireNonNull(psTransNo.getValue()));
                applicantInfo.setDetlInfo(poGOCasxx.toJSONString());
                applicantInfo.setClientNm(poGOCasxx.ApplicantInfo().getClientName());
                applicantInfo.setAppMeans(infoModel.getMeansInfo());
                poCreditA.updateGOCasData(applicantInfo);
                callBack.onSaveSuccessResult(getNextPage(infoModel.getMeansInfo()));
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }

    private String getNextPage(String foJSon) throws Exception{
        JSONObject loJson = new JSONObject(foJSon);
         if(loJson.getString("employed").equalsIgnoreCase("1")){
             return "3";
         } else if(loJson.getString("sEmplyed").equalsIgnoreCase("1")){
             return "4";
         } else if(loJson.getString("financer").equalsIgnoreCase("1")){
             return "5";
         } else {
             return "6";
         }
    }

    public static class MeansInfo{
        private String Employed = "";
        private String sEmplyed = "";
        private String Financex = "";
        private String Pensionx = "";

        private String message;

        public MeansInfo(){

        }

        public String getMessage(){
            return message;
        }

        public void setEmployed(String employed) {
            Employed = employed;
        }

        public void setSelfEmployed(String sEmplyed) {
            this.sEmplyed = sEmplyed;
        }

        public void setFinance(String financex) {
            Financex = financex;
        }

        public void setPension(String pensionx) {
            Pensionx = pensionx;
        }

        public boolean isMeansInfoValid(){
            if(Employed.isEmpty() && sEmplyed.isEmpty() && Financex.isEmpty() && Pensionx.isEmpty()){
                message = "Please select at least one source of income";
                return false;
            }
            return true;
        }

        public String getMeansInfo(){
            JSONObject loJson = new JSONObject();
            try{
                loJson.put("employed", Employed);
                loJson.put("sEmplyed", sEmplyed);
                loJson.put("financer", Financex);
                loJson.put("pensionx", Pensionx);
            } catch (JSONException e){
                e.printStackTrace();
            }
            return loJson.toString();
        }
    }
}