package org.rmj.g3appdriver.GCircle.Apps.CreditApp.Obj;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.MobileNo;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Personal;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.lib.Etc.Country;
import org.rmj.g3appdriver.lib.Etc.Town;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class PersonalInfo implements CreditApp {
    private static final String TAG = PersonalInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final Town poTown;
    private final Country poCountry;

    private Personal poDetail;

    private String message;

    public PersonalInfo(Application instance){
        this.poDao = GGC_GCircleDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new Town(instance);
        this.poCountry = new Country(instance);
    }

    @Override
    public LiveData<ECreditApplicantInfo> GetApplication(String args) {
        return poDao.GetApplicantInfo(args);
    }

    @Override
    public Object Parse(ECreditApplicantInfo args) {
        try{
            Personal loDetail = new Personal();
            if(args.getApplInfo() != null){

                String lsDetail = args.getApplInfo();


                if(lsDetail == null){
                    message = "No personal info detail has been set yet.";
                    return null;
                }

                if(lsDetail.trim().isEmpty()){
                    message = "No personal info detail has been set yet.";
                    return null;
                }

                GOCASApplication gocas = new GOCASApplication();
                JSONParser loJson = new JSONParser();
                JSONObject joDetail = (JSONObject) loJson.parse(lsDetail);
                gocas.ApplicantInfo().setData(joDetail);

                loDetail.setLastName(gocas.ApplicantInfo().getLastName());
                loDetail.setFrstName(gocas.ApplicantInfo().getFirstName());
                loDetail.setMiddName(gocas.ApplicantInfo().getMiddleName());
                loDetail.setSuffix(gocas.ApplicantInfo().getSuffixName());
                loDetail.setNickName(gocas.ApplicantInfo().getNickName());
                loDetail.setBrthDate(gocas.ApplicantInfo().getBirthdate());

                loDetail.setCitizenx(gocas.ApplicantInfo().getCitizenship());

                // Get the UI preview of Citizenship
                String lsCtzen = poDao.GetCitizenship(loDetail.getCitizenx());

                loDetail.setCtznShip(lsCtzen);

                loDetail.setCvlStats(gocas.ApplicantInfo().getCivilStatus());
                loDetail.setGender(gocas.ApplicantInfo().getGender());
                loDetail.setMotherNm(gocas.ApplicantInfo().getMaidenName());

                loDetail.setBrthPlce(gocas.ApplicantInfo().getBirthPlace());

                //Get Town, Province names and set to model class to be displayed on UI.
                String lsBrthPlc = poDao.GetBirthPlace(loDetail.getBrthPlce());

                loDetail.setBirthPlc(lsBrthPlc);
//
//            for(int x = 0; x < gocas.ApplicantInfo().getMobileNoQty(); x++) {
//                loDetail.setMobileNo(
//                        gocas.ApplicantInfo().getMobileNo(x),
//                        gocas.ApplicantInfo().IsMobilePostpaid(x),
//                        gocas.ApplicantInfo().getPostPaidYears(x));
//            }

                for(int x = 0; x < gocas.ApplicantInfo().getMobileNoQty(); x++) {
//                JSONObject mobile = (JSONObject) loMobile.get(x);
//                Log.d(TAG, "Postpaid Year: " + );
//                long lnPostYr = (long) mobile.get("nPostYear");
                    MobileNo mobileNo = new MobileNo();
                    if(x == 0) {
                        mobileNo.setMobileNo(gocas.ApplicantInfo().getMobileNo(x));
                        mobileNo.setIsPostPd((gocas.ApplicantInfo().IsMobilePostpaid(x)));
                        mobileNo.setPostYear(gocas.ApplicantInfo().getPostPaidYears(x));
                        loDetail.setMobileNo1(mobileNo);
                    }
                    if(x == 1) {
                        mobileNo.setMobileNo(gocas.ApplicantInfo().getMobileNo(x));
                        mobileNo.setIsPostPd((gocas.ApplicantInfo().IsMobilePostpaid(x)));
                        mobileNo.setPostYear(gocas.ApplicantInfo().getPostPaidYears(x));
                        loDetail.setMobileNo2(mobileNo);
                    }
                    if(x == 2) {
                        mobileNo.setMobileNo(gocas.ApplicantInfo().getMobileNo(x));
                        mobileNo.setIsPostPd((gocas.ApplicantInfo().IsMobilePostpaid(x)));
                        mobileNo.setPostYear(gocas.ApplicantInfo().getPostPaidYears(x));
                        loDetail.setMobileNo3(mobileNo);
                    }
                }

                loDetail.setEmailAdd(gocas.ApplicantInfo().getEmailAddress(0));
                loDetail.setFbAccntx(gocas.ApplicantInfo().getFBAccount());
                loDetail.setPhoneNox(gocas.ApplicantInfo().getPhoneNo(0));
                loDetail.setVbrAccnt(gocas.ApplicantInfo().getViberAccount());
                poDetail = loDetail;
            }
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        Personal loDetail = (Personal) args;

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
    public String Save(Object args) {
        try{
            Personal loDetail = (Personal) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return null;
            }

            GOCASApplication gocas = new GOCASApplication();

            gocas.ApplicantInfo().setLastName(loDetail.getLastName());
            gocas.ApplicantInfo().setFirstName(loDetail.getFrstName());
            gocas.ApplicantInfo().setMiddleName(loDetail.getMiddName());
            gocas.ApplicantInfo().setSuffixName(loDetail.getSuffix());
            gocas.ApplicantInfo().setNickName(loDetail.getNickName());
            gocas.ApplicantInfo().setBirthdate(loDetail.getBrthDate());
            gocas.ApplicantInfo().setBirthPlace(loDetail.getBrthPlce());
            gocas.ApplicantInfo().setCitizenship(loDetail.getCitizenx());
            gocas.ApplicantInfo().setCivilStatus(loDetail.getCvlStats());
            gocas.ApplicantInfo().setGender(loDetail.getGender());
            gocas.ApplicantInfo().setMaidenName(loDetail.getMotherNm());
            gocas.ApplicantInfo().setMobileNoQty(loDetail.getMobileNoQty());

            int lnMobQty = 1;
            gocas.ApplicantInfo().setMobileNoQty(lnMobQty);
            gocas.ApplicantInfo().setMobileNo(0, loDetail.getMobileNo1().getMobileNo());
            gocas.ApplicantInfo().IsMobilePostpaid(0, loDetail.getMobileNo1().getIsPostPd());
            gocas.ApplicantInfo().setPostPaidYears(0, loDetail.getMobileNo1().getPostYear());

            if(loDetail.getMobileNo2() != null){
                lnMobQty++;
            }

            if(loDetail.getMobileNo3() != null){
                lnMobQty++;
            }
            gocas.ApplicantInfo().setMobileNoQty(lnMobQty);

            if(loDetail.getMobileNo2() != null){
                gocas.ApplicantInfo().setMobileNo(1, loDetail.getMobileNo2().getMobileNo());
                gocas.ApplicantInfo().IsMobilePostpaid(1, loDetail.getMobileNo2().getIsPostPd());
                gocas.ApplicantInfo().setPostPaidYears(1, loDetail.getMobileNo2().getPostYear());
            }

            if(loDetail.getMobileNo3() != null){
                gocas.ApplicantInfo().setMobileNo(2, loDetail.getMobileNo3().getMobileNo());
                gocas.ApplicantInfo().IsMobilePostpaid(2, loDetail.getMobileNo3().getIsPostPd());
                gocas.ApplicantInfo().setPostPaidYears(2, loDetail.getMobileNo3().getPostYear());
            }

            gocas.ApplicantInfo().setEmailAddQty(1);
            gocas.ApplicantInfo().setEmailAddress(0, loDetail.getEmailAdd());
            gocas.ApplicantInfo().setPhoneNoQty(1);
            gocas.ApplicantInfo().setPhoneNo(0, loDetail.getPhoneNox());
            gocas.ApplicantInfo().setFBAccount(loDetail.getFbAccntx());
            gocas.ApplicantInfo().setViberAccount(loDetail.getVbrAccnt());

            loApp.setClientNm(gocas.ApplicantInfo().getClientName());
            loApp.setBranchCd(gocas.PurchaseInfo().getPreferedBranch());
            loApp.setApplInfo(gocas.ApplicantInfo().toJSONString());
            loApp.setIsSpouse(loDetail.getCvlStats());
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
