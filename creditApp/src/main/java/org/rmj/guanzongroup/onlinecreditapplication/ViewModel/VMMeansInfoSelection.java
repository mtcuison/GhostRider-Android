package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

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

public class VMMeansInfoSelection extends AndroidViewModel {
    private static final String TAG = VMMeansInfoSelection.class.getSimpleName();
    private final RCreditApplicant poCreditA;
    private final GOCASApplication poGOCasxx;
    private ECreditApplicantInfo poInfo;
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

    public void setGOCasDetailInfo(ECreditApplicantInfo DetailInfo){
        try{
            poInfo = DetailInfo;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SaveMeansInfo(MeansInfo infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isMeansInfoValid()){
                poInfo.setAppMeans(infoModel.getMeansInfo());
                poCreditA.updateGOCasData(poInfo);
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
         } else if(loJson.getString("pensionx").equalsIgnoreCase("1")){
             return "6";
         }
        return foJSon;
    }

    public static class MeansInfo{
        private String Employed;
        private String sEmplyed;
        private String Financex;
        private String Pensionx;
        private String Primaryx;

        private String message;

        private final List<String> meansInfo;

        public MeansInfo(){
            meansInfo = new ArrayList<>();
            meansInfo.clear();
        }

        public String getMessage(){
            return message;
        }

        public void setEmployed(String employed) {
            Employed = employed;
            if(employed.equalsIgnoreCase("1")) {
                meansInfo.add("employed");
            }
        }

        public void setSelfEmployed(String sEmplyed) {
            this.sEmplyed = sEmplyed;
            if(sEmplyed.equalsIgnoreCase("1")) {
                meansInfo.add("sEmplyed");
            }
        }

        public void setFinance(String financex) {
            Financex = financex;
            if(financex.equalsIgnoreCase("1")) {
                meansInfo.add("financer");
            }
        }

        public void setPension(String pensionx) {
            Pensionx = pensionx;
            if(pensionx.equalsIgnoreCase("1")) {
                meansInfo.add("pensionx");
            }
        }

        public void setPrimaryx(String primaryx) {
            if(primaryx.equalsIgnoreCase("Employment")){
                Primaryx = "0";
            } else if(primaryx.equalsIgnoreCase("Self-Employed")){
                Primaryx = "1";
            } else if(primaryx.equalsIgnoreCase("Financier")){
                Primaryx = "2";
            } else {
                Primaryx = "3";
            }
        }

        public String[] getSourcesOfIncome(){
            String[] income = new String[meansInfo.size()];
            for(int x = 0; x < meansInfo.size(); x++){
                if(meansInfo.get(x).equalsIgnoreCase("employed")){
                    income[x] = "Employment";
                } else if(meansInfo.get(x).equalsIgnoreCase("sEmplyed")){
                    income[x] = "Self-Employed";
                } else if(meansInfo.get(x).equalsIgnoreCase("financer")){
                    income[x] = "Financier";
                } else if(meansInfo.get(x).equalsIgnoreCase("pensionx")){
                    income[x] = "Pension";
                }
            }
            return income;
        }

        public boolean isMeansInfoValid(){
            if(Employed.equalsIgnoreCase("0") &&
                    sEmplyed.equalsIgnoreCase("0") &&
                    Financex.equalsIgnoreCase("0") &&
                    Pensionx.equalsIgnoreCase("0")){
                message = "Please select at least one source of income";
                return false;
            } else if (meansInfo.size() > 1 && Primaryx == null){
                message = "means_info";
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

                if(meansInfo.size() > 1){
                    loJson.put("primay", Primaryx);
                } else if (meansInfo.size() == 1){
                    if(meansInfo.get(0).equalsIgnoreCase("employed")){
                        loJson.put("primay", "0");
                    } else if(meansInfo.get(0).equalsIgnoreCase("sEmplyed")){
                        loJson.put("primay", "1");
                    } else if(meansInfo.get(0).equalsIgnoreCase("financer")){
                        loJson.put("primay", "2");
                    } else if(meansInfo.get(0).equalsIgnoreCase("pensionx")){
                        loJson.put("primay", "3");
                    }
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
            return loJson.toString();
        }
    }
}