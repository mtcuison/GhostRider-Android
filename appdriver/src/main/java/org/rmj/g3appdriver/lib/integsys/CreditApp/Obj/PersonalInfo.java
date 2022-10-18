package org.rmj.g3appdriver.lib.integsys.CreditApp.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientInfo;
import org.rmj.gocas.base.GOCASApplication;

public class PersonalInfo implements CreditApp {
    private static final String TAG = PersonalInfo.class.getSimpleName();

    private final DCreditApplication poDao;

    private String message;

    public PersonalInfo(Application instance){
        this.poDao = GGC_GriderDB.getInstance(instance).CreditApplicationDao();
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
            loClient.setBrthDate(loGocas.ApplicantInfo().getBirthdate());
            loClient.setCitizenx(loGocas.ApplicantInfo().getCitizenship());
            loClient.setCvlStats(loGocas.ApplicantInfo().getCivilStatus());
            loClient.setGender(loGocas.ApplicantInfo().getGender());
            loClient.setMotherNm(loGocas.ApplicantInfo().getMaidenName());

            for(int x = 0; x < loGocas.ApplicantInfo().getMobileNoQty(); x++) {
                loClient.setMobileNo(
                        loGocas.ApplicantInfo().getMobileNo(x),
                        loGocas.ApplicantInfo().IsMobilePostpaid(x),
                        loGocas.ApplicantInfo().getPostPaidYears(x));
            }
            loClient.setEmailAdd(loGocas.ApplicantInfo().getEmailAddress(0));
            loClient.setFbAccntx(loGocas.ApplicantInfo().getFBAccount());
            loClient.setPhoneNox(loGocas.ApplicantInfo().getPhoneNo(0));

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
