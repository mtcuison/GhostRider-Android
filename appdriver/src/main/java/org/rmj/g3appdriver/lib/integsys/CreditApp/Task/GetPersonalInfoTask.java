package org.rmj.g3appdriver.lib.integsys.CreditApp.Task;

import android.os.AsyncTask;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplication;
import org.rmj.gocas.base.GOCASApplication;

public class GetPersonalInfoTask extends AsyncTask<ECreditApplication, Void, String> {

    private final DCreditApplication poDao;
    private final OnRetrievePersonaInfo listener;
    private final GOCASApplication gocas = new GOCASApplication();

    private String BPlace, Citizen;

    public GetPersonalInfoTask(DCreditApplication foDao, OnRetrievePersonaInfo listener) {
        this.poDao = foDao;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(ECreditApplication... loApp) {
        try{
            ECreditApplication loDetail = loApp[0];
            gocas.setData(loDetail.getDetlInfo());

            String lsBPlace = gocas.ApplicantInfo().getBirthPlace();
            String lsCtizen = gocas.ApplicantInfo().getCitizenship();

            BPlace = poDao.GetBirthPlace(lsBPlace);
            Citizen = poDao.GetCitizenship(lsCtizen);

        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        listener.LastName(gocas.ApplicantInfo().getLastName());
        listener.FirstName(gocas.ApplicantInfo().getFirstName());
        listener.MiddleName(gocas.ApplicantInfo().getMiddleName());
        listener.Suffix(gocas.ApplicantInfo().getSuffixName());
        listener.BirthDate(gocas.ApplicantInfo().getBirthdate());
        listener.BirthPlace(gocas.ApplicantInfo().getBirthPlace(), BPlace);
        listener.Gender(gocas.ApplicantInfo().getGender());
        listener.CivilStatus(gocas.ApplicantInfo().getCivilStatus());
        listener.Citizenship(gocas.ApplicantInfo().getCitizenship(), Citizen);
        listener.MaidenName(gocas.ApplicantInfo().getMaidenName());
        String[] mobileno = new String[gocas.ApplicantInfo().getMobileNoQty()],
                simtype = new String[gocas.ApplicantInfo().getMobileNoQty()];
        int[] planyear = new int[gocas.ApplicantInfo().getMobileNoQty()];
        for(int x = 0; x < gocas.ApplicantInfo().getMobileNoQty(); x++){
            mobileno[x] = gocas.ApplicantInfo().getMobileNo(x);
            simtype[x] = gocas.ApplicantInfo().IsMobilePostpaid(x);
            planyear[x] = gocas.ApplicantInfo().getPostPaidYears(x);
        }
        listener.MobileNo(mobileno, simtype, planyear);

        String[] tellno = new String[gocas.ApplicantInfo().getPhoneNoQty()];
        for(int x = 0; x < gocas.ApplicantInfo().getPhoneNoQty(); x++){
            tellno[x] = gocas.ApplicantInfo().getPhoneNo(x);
        }
        String[] email = new String[gocas.ApplicantInfo().getPhoneNoQty()];
        for(int x = 0; x < gocas.ApplicantInfo().getPhoneNoQty(); x++){
            email[x] = gocas.ApplicantInfo().getPhoneNo(x);
        }
        listener.TelephoneNo(tellno);
        listener.EmailAdd(email);
        listener.FacebookAcc(gocas.ApplicantInfo().getFBAccount());
        listener.ViberAcc(gocas.ApplicantInfo().getViberAccount());
    }
}
