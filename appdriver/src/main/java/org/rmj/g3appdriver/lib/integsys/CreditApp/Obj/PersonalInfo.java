package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.dev.Database.Repositories.RCountry;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientInfo;
import org.rmj.gocas.base.GOCASApplication;

public class PersonalInfo implements CreditApp {
    private static final String TAG = PersonalInfo.class.getSimpleName();

    private final DCreditApplication poDao;
    private final RTown poTown;
    private final RCountry poCountry;

    private String message;

    public PersonalInfo(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
        this.poTown = new RTown(instance);
        this.poCountry = new RCountry(instance);
    }

    @Override
    public LiveData<ECreditApplication> GetApplication(String args) {
        return poDao.GetCurrentApplication(args);
    }

    @Override
    public Object Parse(ECreditApplication args) {
        try{
            String lsDetail = args.getDetlInfo();
            GOCASApplication loGocas = new GOCASApplication();
            loGocas.setData(lsDetail);

            ClientInfo loClient = new ClientInfo();
            loClient.setLastName(loGocas.ApplicantInfo().getLastName());
            loClient.setFrstName(loGocas.ApplicantInfo().getFirstName());
            loClient.setMiddName(loGocas.ApplicantInfo().getMiddleName());
            loClient.setSuffix(loGocas.ApplicantInfo().getSuffixName());
            loClient.setNickName(loGocas.ApplicantInfo().getNickName());
            loClient.setBrthDate(loGocas.ApplicantInfo().getBirthdate());

            loClient.setCitizenx(loGocas.ApplicantInfo().getCitizenship());

            // Get the UI preview of Citizenship
            ECountryInfo loCtzen = poCountry.getCountryInfo(loClient.getCitizenx());

            loClient.setCtznShip(loCtzen.getNational());

            loClient.setCvlStats(loGocas.ApplicantInfo().getCivilStatus());
            loClient.setGender(loGocas.ApplicantInfo().getGender());
            loClient.setMotherNm(loGocas.ApplicantInfo().getMaidenName());

            loClient.setBrthPlce(loGocas.ApplicantInfo().getBirthPlace());

            //Get Town, Province names and set to model class to be displayed on UI.
            DTownInfo.TownProvinceName loBrthPlc = poTown.getTownProvinceName(loClient.getBirthPlc());

            loClient.setBirthPlc(loBrthPlc.sTownName + ", " + loBrthPlc.sProvName);

            for(int x = 0; x < loGocas.ApplicantInfo().getMobileNoQty(); x++) {
                loClient.setMobileNo(
                        loGocas.ApplicantInfo().getMobileNo(x),
                        loGocas.ApplicantInfo().IsMobilePostpaid(x),
                        loGocas.ApplicantInfo().getPostPaidYears(x));
            }
            loClient.setEmailAdd(loGocas.ApplicantInfo().getEmailAddress(0));
            loClient.setFbAccntx(loGocas.ApplicantInfo().getFBAccount());
            loClient.setPhoneNox(loGocas.ApplicantInfo().getPhoneNo(0));
            loClient.setVbrAccnt(loGocas.ApplicantInfo().getViberAccount());

            return loClient;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return null;
        }
    }

    @Override
    public int Validate(Object args) {
        return 0;
    }

    @Override
    public boolean Save(Object args) {
        try{
            ClientInfo loDetail = (ClientInfo) args;

            ECreditApplication loApp = poDao.GetApplication(loDetail.getTransNox());

            if(loApp == null){
                message = "Unable to find record for update. Please restart credit app and try again.";
                return false;
            }

            String lsDetlInfo = loApp.getDetlInfo();

            GOCASApplication gocas = new GOCASApplication();
            gocas.setData(lsDetlInfo);

            gocas.ApplicantInfo().setLastName(loDetail.getLastName());
            gocas.ApplicantInfo().setFirstName(loDetail.getFrstName());
            gocas.ApplicantInfo().setMiddleName(loDetail.getMiddName());
            gocas.ApplicantInfo().setSuffixName(loDetail.getSuffix());
            gocas.ApplicantInfo().setNickName(loDetail.getNickName());
            gocas.ApplicantInfo().setBirthdate(loDetail.getBrthDate());
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

            loApp.setDetlInfo(gocas.toJSONString());
            poDao.update(loApp);
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

}
