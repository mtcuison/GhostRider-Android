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
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.MobileNo;
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
                MobileNo mobileNo = new MobileNo();
                if(x == 0) {
                    mobileNo.setMobileNo((String) mobile.get("sMobileNo"));
                    mobileNo.setIsPostPd((int) mobile.get("cPostPaid"));
                    mobileNo.setPostYear((int)lnPostYr);
                    loDetail.setMobileNo1(mobileNo);
                }
                if(x == 1) {
                    mobileNo.setMobileNo((String) mobile.get("sMobileNo"));
                    mobileNo.setIsPostPd((int) mobile.get("cPostPaid"));
                    mobileNo.setPostYear((int)lnPostYr);
                    loDetail.setMobileNo2(mobileNo);
                }
                if(x == 2) {
                    mobileNo.setMobileNo((String) mobile.get("sMobileNo"));
                    mobileNo.setIsPostPd((int) mobile.get("cPostPaid"));
                    mobileNo.setPostYear((int)lnPostYr);
                    loDetail.setMobileNo3(mobileNo);
                }
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

            gocas.CoMakerInfo().setMobileNoQty(1);
            gocas.CoMakerInfo().setMobileNo(0, loDetail.getMobileNo1().getMobileNo());
            gocas.CoMakerInfo().IsMobilePostpaid(0, loDetail.getMobileNo1().getIsPostPd());
            gocas.CoMakerInfo().setPostPaidYears(0, loDetail.getMobileNo1().getPostYear());

            if(loDetail.getMobileNo2() != null){
                gocas.CoMakerInfo().setMobileNoQty(2);
                gocas.CoMakerInfo().setMobileNo(1, loDetail.getMobileNo1().getMobileNo());
                gocas.CoMakerInfo().IsMobilePostpaid(1, loDetail.getMobileNo1().getIsPostPd());
                gocas.CoMakerInfo().setPostPaidYears(1, loDetail.getMobileNo1().getPostYear());
            }

            if(loDetail.getMobileNo3() != null){
                gocas.CoMakerInfo().setMobileNoQty(3);
                gocas.CoMakerInfo().setMobileNo(2, loDetail.getMobileNo1().getMobileNo());
                gocas.CoMakerInfo().IsMobilePostpaid(2, loDetail.getMobileNo1().getIsPostPd());
                gocas.CoMakerInfo().setPostPaidYears(2, loDetail.getMobileNo1().getPostYear());
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
