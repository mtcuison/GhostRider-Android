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
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

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
        poCreditA = new RCreditApplicant(application);
        poGOCasxx = new GOCASApplication();
        psMeansIf.setValue(new ArrayList<>());
        poJsonMnx.setValue(new JSONObject());
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

    public LiveData<Integer> getMeansInfoPage(){
        setUpMeansInfoPages();
        return pnPageIDx;
    }

    public void addMeansInfo(String MeansInfo){
        try {
            psMeansIf.getValue().add(MeansInfo);
            Log.e(TAG, MeansInfo + " has been added to list");
            Log.e(TAG, psMeansIf.getValue().get(0) + " is index 0");
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
    }

    public void removeMeansInfo(String MeansInfo){
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
        }
    }

    private void setUpMeansInfoPages(){
        try {
            if(psMeansIf.getValue().size() > 0) {
                poJsonMnx.getValue().put("means", psMeansIf.getValue().get(0));
                pnPageIDx.setValue(getNextPage());
                for (int x = 0; x < psMeansIf.getValue().size(); x++) {
                    if (psMeansIf.getValue().get(x).equalsIgnoreCase("employed")) {
                        poJsonMnx.getValue().put("employed", "");
                    }
                    if (psMeansIf.getValue().get(x).equalsIgnoreCase("self-employed")) {
                        poJsonMnx.getValue().put("sEmployd", "");
                    }
                    if (psMeansIf.getValue().get(x).equalsIgnoreCase("finance")) {
                        poJsonMnx.getValue().put("finance", "");
                    }
                    if (psMeansIf.getValue().get(x).equalsIgnoreCase("pension")) {
                        poJsonMnx.getValue().put("pension", "");
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getNextPage(){
        if(psMeansIf.getValue().size() > 0){
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
}