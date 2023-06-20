package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Financier;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Country;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class FinancierInfo implements CreditApp {
    private static final String TAG = FinancierInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Country poCountry;

    private Financier poDetail;

    private String message;

    public FinancierInfo(Application instance) {
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poCountry = new Country(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Financier loDetail = new Financier();
            if(args.getFinancex() != null){
                String lsDetail = args.getFinancex();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.MeansInfo().FinancerInfo().setData(joDetail);

                loDetail.setFinancierRelation(gocas.MeansInfo().FinancerInfo().getSource());
                loDetail.setFinancierName(gocas.MeansInfo().FinancerInfo().getFinancerName());
                loDetail.setRangeOfIncome(gocas.MeansInfo().FinancerInfo().getAmount());
                loDetail.setEmail(gocas.MeansInfo().FinancerInfo().getEmailAddress());
                loDetail.setFacebook(gocas.MeansInfo().FinancerInfo().getFBAccount());
                loDetail.setMobileNo(gocas.MeansInfo().FinancerInfo().getMobileNo());

                String lsCountry = gocas.MeansInfo().FinancerInfo().getCountry();
                loDetail.setCountry(lsCountry);
                if(!lsCountry.isEmpty()){
                    String lsCName = poCountry.getCountryInfo(lsCountry).getCntryNme();
                    loDetail.setCountryName(lsCName);

                }
                poDetail = loDetail;
            }
            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        Financier loDetail = (Financier) args;
        if(poDetail == null){
            if(loDetail.isPrimary()){
                if(!loDetail.isDataValid()){
                    message = loDetail.getMessage();
                    return 0;
                }
            }else{
                return 1;
            }
        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.
            if(loDetail.isPrimary()){
                if(!loDetail.isDataValid()){
                    message = loDetail.getMessage();
                    return 0;
                }
            }else{
                return 1;
            }
//            if(!poDetail.isEqual(loDetail)){
//                return 2;
//            } else {
//                return 1;
//            }
        }

        return 1;
    }

    @Override
    public String Save(Object args) {
        try{
            Financier loDetail = (Financier) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();
            gocas.MeansInfo().FinancerInfo().setSource(loDetail.getFinancierRelation());
            gocas.MeansInfo().FinancerInfo().setFinancerName(loDetail.getFinancierName());
            gocas.MeansInfo().FinancerInfo().setAmount(loDetail.getRangeOfIncome());
            gocas.MeansInfo().FinancerInfo().setCountry(loDetail.getCountry());
            gocas.MeansInfo().FinancerInfo().setMobileNo(loDetail.getMobileNo());
            gocas.MeansInfo().FinancerInfo().setFBAccount(loDetail.getFacebook());
            gocas.MeansInfo().FinancerInfo().setEmailAddress(loDetail.getEmail());
            loApp.setFinancex(gocas.MeansInfo().FinancerInfo().toJSONString());
            poDao.Update(loApp);

            return loDetail.getTransNox();
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LiveData<List<EBarangayInfo>> GetBarangayList(String args) {
        return null;
    }

    @Override
    public LiveData<List<DTownInfo.TownProvinceInfo>> GetTownProvinceList() {
        return null;
    }

    @Override
    public LiveData<List<ECountryInfo>> GetCountryList() {
        return poCountry.getAllCountryInfo();
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }
}
