package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.CoMaker;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class CoMakerInfo implements CreditApp {
    private static final String TAG = CoMakerInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;

    private CoMaker poDetail;

    private String message;

    public CoMakerInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            String lsDetail = args.getComakerx();
            GOCASApplication gocas = new GOCASApplication();
            JSONParser loJson = new JSONParser();
            JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
            gocas.CoMakerInfo().setData(joDetail);

            CoMaker loDetail = new CoMaker();
            loDetail.setLastName(gocas.CoMakerInfo().getLastName());
            loDetail.setFrstName(gocas.CoMakerInfo().getFirstName());
            loDetail.setMiddName(gocas.CoMakerInfo().getMiddleName());
            loDetail.setSuffix(gocas.CoMakerInfo().getSuffixName());
            loDetail.setNickName(gocas.CoMakerInfo().getNickName());
            loDetail.setBrthDate(gocas.CoMakerInfo().getBirthdate());
            loDetail.setBrthPlce(gocas.CoMakerInfo().getBirthPlace());
            loDetail.setIncomexx(gocas.CoMakerInfo().getIncomeSource());
            loDetail.setRelation(gocas.CoMakerInfo().getRelation());

            JSONArray loMobile = (JSONArray) joDetail.get("mobile_number");

            for(int x = 0; x < loMobile.size(); x++) {
                JSONObject mobile = (JSONObject) loMobile.get(x);
//                Log.d(TAG, "Postpaid Year: " + );
                long lnPostYr = (long) mobile.get("nPostYear");
                loDetail.setMobileNo(
                        (String) mobile.get("sMobileNo"),
                        (String) mobile.get("cPostPaid"),
                        (int) lnPostYr);
            }

//            for(int x = 0; x < gocas.CoMakerInfo().getMobileNoQty(); x++) {
//                loDetail.setMobileNo(
//                        gocas.CoMakerInfo().getMobileNo(x),
//                        gocas.CoMakerInfo().IsMobilePostpaid(x),
//                        gocas.CoMakerInfo().getPostPaidYears(x));
//            }

            loDetail.setFbAccntx(gocas.CoMakerInfo().getFBAccount());
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

        CoMaker loDetail = (CoMaker) args;

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
            CoMaker loDetail = (CoMaker) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();
            gocas.CoMakerInfo().setLastName(loDetail.getLastName());
            gocas.CoMakerInfo().setFirstName(loDetail.getFrstName());
            gocas.CoMakerInfo().setMiddleName(loDetail.getMiddName());
            gocas.CoMakerInfo().setSuffixName(loDetail.getSuffix());
            gocas.CoMakerInfo().setNickName(loDetail.getNickName());
            gocas.CoMakerInfo().setBirthdate(loDetail.getBrthDate());
            gocas.CoMakerInfo().setBirthPlace(loDetail.getBrthPlce());
            gocas.CoMakerInfo().setIncomeSource(loDetail.getIncomexx());
            gocas.CoMakerInfo().setRelation(loDetail.getRelation());
            for (int x = 0; x < loDetail.getMobileNoQty(); x++) {
                gocas.CoMakerInfo().setMobileNoQty(x + 1);
                gocas.CoMakerInfo().setMobileNo(x, loDetail.getMobileNo(x));
                gocas.CoMakerInfo().IsMobilePostpaid(x, loDetail.getPostPaid(x));
                gocas.CoMakerInfo().setPostPaidYears(x, loDetail.getPostYear(x));
            }
            gocas.CoMakerInfo().setFBAccount(loDetail.getFbAccntx());
            loApp.setComakerx(gocas.CoMakerInfo().toJSONString());

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
        return poTown.getTownProvinceInfo();
    }

    @Override
    public LiveData<List<ECountryInfo>> GetCountryList() {
        return null;
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }
}
