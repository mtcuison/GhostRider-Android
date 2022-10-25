package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RCountry;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientInfo;
import org.rmj.gocas.base.GOCASApplication;

import java.util.List;

public class PersonalInfo implements CreditApp {
    private static final String TAG = PersonalInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RCountry poCountry;

    private ClientInfo loLClient;

    private String message;

    public PersonalInfo(Application instance){
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
            String lsDetail = args.getApplInfo();
            GOCASApplication gocas = new GOCASApplication();
            gocas.setData(lsDetail);

            ClientInfo loDetail = new ClientInfo();
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

            for(int x = 0; x < gocas.ApplicantInfo().getMobileNoQty(); x++) {
                loDetail.setMobileNo(
                        gocas.ApplicantInfo().getMobileNo(x),
                        gocas.ApplicantInfo().IsMobilePostpaid(x),
                        gocas.ApplicantInfo().getPostPaidYears(x));
            }

            loDetail.setEmailAdd(gocas.ApplicantInfo().getEmailAddress(0));
            loDetail.setFbAccntx(gocas.ApplicantInfo().getFBAccount());
            loDetail.setPhoneNox(gocas.ApplicantInfo().getPhoneNo(0));
            loDetail.setVbrAccnt(gocas.ApplicantInfo().getViberAccount());

            loLClient = loDetail;
            return loDetail;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        ClientInfo loClient = (ClientInfo) args;

        if(loLClient == null){

            if(!loClient.isDataValid()){
                message = loClient.getMessage();
                return 0;
            }

        } else {

            //TODO: if all information inside each old object and new object is not the same,
            // return 2 to indicate validation needs confirmation from user to update the
            // previous information being save.

            if(!loLClient.isEqual(loClient)){
                return 2;
            } else {
                return 1;
            }
        }

        return 1;
    }

    @Override
    public boolean Save(Object args) {
        try{
            ClientInfo loDetail = (ClientInfo) args;

            ECreditApplicantInfo loApp = poDao.GetApplicantDetails(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
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

            for(int x = 0; x < loDetail.getMobileNoQty(); x++){
                gocas.ApplicantInfo().setMobileNo(x, loDetail.getMobileNo(x));
                gocas.ApplicantInfo().setPostPaidYears(x, loDetail.getPostYear(x));
                if(loDetail.getPostPaid(x) != null){
                    gocas.ApplicantInfo().IsMobilePostpaid(x, loDetail.getPostPaid(x));
                }
            }

            gocas.ApplicantInfo().setEmailAddQty(1);
            gocas.ApplicantInfo().setEmailAddress(0, loDetail.getEmailAdd());
            gocas.ApplicantInfo().setPhoneNoQty(1);
            gocas.ApplicantInfo().setPhoneNo(0, loDetail.getPhoneNox());
            gocas.ApplicantInfo().setFBAccount(loDetail.getFbAccntx());
            gocas.ApplicantInfo().setViberAccount(loDetail.getVbrAccnt());

            loApp.setApplInfo(gocas.toJSONString());
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
