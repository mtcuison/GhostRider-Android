package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RCountry;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientFinancier;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class FinancierInfo implements CreditApp {
    private static final String TAG = FinancierInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RCountry poCountry;

    private ClientFinancier poDetail;

    private String message;

    public FinancierInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poCountry = new RCountry(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getResidnce();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.MeansInfo().FinancerInfo().setData(joDetail);

            ClientFinancier loDetail = new ClientFinancier();
            loDetail.setFinancierRelation(gocas.MeansInfo().FinancerInfo().getSource());
            loDetail.setFinancierRelation(gocas.MeansInfo().FinancerInfo().getFinancerName());
            loDetail.setEmail(gocas.MeansInfo().FinancerInfo().getEmailAddress());
            loDetail.setFacebook(gocas.MeansInfo().FinancerInfo().getFBAccount());
            loDetail.setMobileNo(gocas.MeansInfo().FinancerInfo().getMobileNo());
            loDetail.setRangeOfIncome(String.valueOf(gocas.MeansInfo().FinancerInfo().getAmount()));

            String lsCountry = gocas.MeansInfo().FinancerInfo().getCountry();
            loDetail.setCountry(lsCountry);

            String lsCName = poCountry.getCountryInfo(lsCountry).getCntryNme();
            loDetail.setCountryName(lsCName);

            poDetail = loDetail;
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        ClientFinancier loDetail = (ClientFinancier) args;

        if(poDetail == null){

            if(!loDetail.isDataValid()){
                message = loDetail.getMessage();
                return 0;
            }

        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.

//            if(!poDetail.isEqual(loDetail)){
//                return 2;
//            } else {
//                return 1;
//            }
        }

        return 1;
    }

    @Override
    public boolean Save(Object args) {
        try{
            ClientFinancier loDetail = (ClientFinancier) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
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

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
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
