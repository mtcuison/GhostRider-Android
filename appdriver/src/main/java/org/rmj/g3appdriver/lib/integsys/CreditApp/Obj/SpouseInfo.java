package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;
import android.util.Log;

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
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientSpouseInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.MobileNo;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class SpouseInfo implements CreditApp {
    private static final String TAG = SpouseInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RCountry poCountry;

    private ClientSpouseInfo poDetail;

    private String message;

    public SpouseInfo(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poCountry = new RCountry(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            ClientSpouseInfo loDetail = new ClientSpouseInfo();
            if (args.getSpousexx() != null){

                String lsDetail = args.getSpousexx();
                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.SpouseInfo().PersonalInfo().setData(joDetail);

                loDetail.setLastName(gocas.SpouseInfo().PersonalInfo().getLastName());
                loDetail.setFrstName(gocas.SpouseInfo().PersonalInfo().getFirstName());
                loDetail.setMiddName(gocas.SpouseInfo().PersonalInfo().getMiddleName());
                loDetail.setSuffix(gocas.SpouseInfo().PersonalInfo().getSuffixName());
                loDetail.setNickName(gocas.SpouseInfo().PersonalInfo().getNickName());
                loDetail.setBrthDate(gocas.SpouseInfo().PersonalInfo().getBirthdate());
                loDetail.setBrthPlce(gocas.SpouseInfo().PersonalInfo().getBirthPlace());

                loDetail.setCitizenx(gocas.SpouseInfo().PersonalInfo().getCitizenship());

                String lsCtzen = poDao.GetCitizenship(loDetail.getCitizenx());

                loDetail.setCtznShip(lsCtzen);

//            loDetail.setCvlStats(gocas.SpouseInfo().PersonalInfo().getCivilStatus());

                loDetail.setBrthPlce(gocas.SpouseInfo().PersonalInfo().getBirthPlace());

                //Get Town, Province names and set to model class to be displayed on UI.
                String lsBrthPlc = poDao.GetBirthPlace(loDetail.getBrthPlce());

                loDetail.setBirthPlc(lsBrthPlc);

                for(int x = 0; x < gocas.SpouseInfo().PersonalInfo().getMobileNoQty(); x++) {
//                loDetail.setMobileNo(
//                        gocas.SpouseInfo().PersonalInfo().getMobileNo(x),
//                        gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x),
//                        gocas.SpouseInfo().PersonalInfo().getPostPaidYears(x));
                    MobileNo mobileNo = new MobileNo();
                    if(x == 0) {

                        mobileNo.setMobileNo(gocas.SpouseInfo().PersonalInfo().getMobileNo(x));
                        mobileNo.setIsPostPd((gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x)));
                        mobileNo.setPostYear(gocas.SpouseInfo().PersonalInfo().getPostPaidYears(x));
                        loDetail.setMobileNo1(mobileNo);
                    }
                    if(x == 1) {
                        mobileNo.setMobileNo(gocas.SpouseInfo().PersonalInfo().getMobileNo(x));
                        mobileNo.setIsPostPd((gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x)));
                        mobileNo.setPostYear(gocas.SpouseInfo().PersonalInfo().getPostPaidYears(x));
                        loDetail.setMobileNo2(mobileNo);
                    }
                    if(x == 2) {
                        mobileNo.setMobileNo(gocas.SpouseInfo().PersonalInfo().getMobileNo(x));
                        mobileNo.setIsPostPd((gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x)));
                        mobileNo.setPostYear(gocas.SpouseInfo().PersonalInfo().getPostPaidYears(x));
                        loDetail.setMobileNo3(mobileNo);
                    }
                }

                loDetail.setEmailAdd(gocas.SpouseInfo().PersonalInfo().getEmailAddress(0));
                loDetail.setFbAccntx(gocas.SpouseInfo().PersonalInfo().getFBAccount());
                loDetail.setPhoneNox(gocas.SpouseInfo().PersonalInfo().getPhoneNo(0));
                loDetail.setVbrAccnt(gocas.SpouseInfo().PersonalInfo().getViberAccount());

                poDetail = loDetail;
            }
            return loDetail;
        } catch (NullPointerException e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        ClientSpouseInfo loDetail = (ClientSpouseInfo) args;

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
            ClientSpouseInfo loDetail = (ClientSpouseInfo) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.SpouseInfo().PersonalInfo().setLastName(loDetail.getLastName());
            gocas.SpouseInfo().PersonalInfo().setFirstName(loDetail.getFrstName());
            gocas.SpouseInfo().PersonalInfo().setMiddleName(loDetail.getMiddName());
            gocas.SpouseInfo().PersonalInfo().setSuffixName(loDetail.getSuffix());
            gocas.SpouseInfo().PersonalInfo().setNickName(loDetail.getNickName());
            gocas.SpouseInfo().PersonalInfo().setBirthdate(loDetail.getBrthDate());
            gocas.SpouseInfo().PersonalInfo().setBirthPlace(loDetail.getBrthPlce());
            gocas.SpouseInfo().PersonalInfo().setCitizenship(loDetail.getCitizenx());
//            gocas.SpouseInfo().PersonalInfo().setCivilStatus(loDetail.getCvlStats());
//            gocas.SpouseInfo().PersonalInfo().setMobileNoQty(loDetail.getMobileNoQty());

            if(loDetail.getMobileNo2() != null){
                gocas.SpouseInfo().PersonalInfo().setMobileNoQty(2);
                gocas.SpouseInfo().PersonalInfo().setMobileNo(1, loDetail.getMobileNo1().getMobileNo());
                gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(1, loDetail.getMobileNo1().getIsPostPd());
                gocas.SpouseInfo().PersonalInfo().setPostPaidYears(1, loDetail.getMobileNo1().getPostYear());
            }

            if(loDetail.getMobileNo3() != null){
                gocas.SpouseInfo().PersonalInfo().setMobileNoQty(3);
                gocas.SpouseInfo().PersonalInfo().setMobileNo(2, loDetail.getMobileNo1().getMobileNo());
                gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(2, loDetail.getMobileNo1().getIsPostPd());
                gocas.SpouseInfo().PersonalInfo().setPostPaidYears(2, loDetail.getMobileNo1().getPostYear());
            }

//            for(int x = 0; x < loDetail.getMobileNoQty(); x++){
//                gocas.SpouseInfo().PersonalInfo().setMobileNo(x, loDetail.getMobileNo(x));
//                gocas.SpouseInfo().PersonalInfo().setPostPaidYears(x, loDetail.getPostYear(x));
//                if(loDetail.getPostPaid(x) != null){
//                    gocas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x, loDetail.getPostPaid(x));
//                }
//            }

            gocas.SpouseInfo().PersonalInfo().setEmailAddQty(1);
            gocas.SpouseInfo().PersonalInfo().setEmailAddress(0, loDetail.getEmailAdd());
            gocas.SpouseInfo().PersonalInfo().setPhoneNoQty(1);
            gocas.SpouseInfo().PersonalInfo().setPhoneNo(0, loDetail.getPhoneNox());
            gocas.SpouseInfo().PersonalInfo().setFBAccount(loDetail.getFbAccntx());
            gocas.SpouseInfo().PersonalInfo().setViberAccount(loDetail.getVbrAccnt());

            loApp.setSpousexx(gocas.SpouseInfo().PersonalInfo().toJSONString());
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
        return poCountry.getAllCountryInfo();
    }

    @Override
    public LiveData<List<EOccupationInfo>> GetOccupations() {
        return null;
    }
}
