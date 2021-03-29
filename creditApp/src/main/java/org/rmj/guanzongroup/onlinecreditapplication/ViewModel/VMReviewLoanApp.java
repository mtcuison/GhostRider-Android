package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.ReviewAppDetail;
import org.rmj.guanzongroup.onlinecreditapplication.Data.GoCasBuilder;
import org.rmj.guanzongroup.onlinecreditapplication.Data.UploadCreditApp;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;

import java.util.ArrayList;
import java.util.List;

public class VMReviewLoanApp extends AndroidViewModel {
    private static final String TAG = VMReviewLoanApp.class.getSimpleName();

    private final Application instance;
    private final RCreditApplicant poCreditApp;
    private final RBranchLoanApplication poLoan;
    private final RImageInfo poImage;
    private String TransNox;

    private ECreditApplicantInfo poInfo = new ECreditApplicantInfo();

    private final MutableLiveData<List<ReviewAppDetail>> plAppDetail = new MutableLiveData<>();

    public interface OnFetchReviewListener{
        void OnDetailLoad(List<ReviewAppDetail> detailList);
    }

    public VMReviewLoanApp(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCreditApp = new RCreditApplicant(application);
        this.plAppDetail.setValue(new ArrayList<>());
        this.poLoan = new RBranchLoanApplication(application);
        this.poImage = new RImageInfo(application);
    }

    public void setTransNox(String transNox){
        this.TransNox = transNox;
    }

    public LiveData<ECreditApplicantInfo> getApplicantInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox);
    }

    public void setCreditAppInfo(ECreditApplicantInfo foInfo){
        this.poInfo = foInfo;
        new FetchReviewDetail(instance, plAppDetail::setValue).execute(poInfo);
    }

    public LiveData<List<ReviewAppDetail>> getAppDetail(){
        return plAppDetail;
    }

    public void saveImageFile(EImageInfo foImage){
        poImage.insertImageInfo(foImage);
    }

    public void SaveCreditOnlineApplication(UploadCreditApp.OnUploadLoanApplication listener){
        ECreditApplication loCreditApp = new ECreditApplication();
        GoCasBuilder loModel = new GoCasBuilder(poInfo);
        GOCASApplication loGOCas = new GOCASApplication();
        loGOCas.setData(loModel.getConstructedDetailedInfo());
        loCreditApp.setTransNox(poCreditApp.getGOCasNextCode());
        loCreditApp.setBranchCd(poInfo.getBranchCd());
        loCreditApp.setClientNm(poInfo.getClientNm());
        loCreditApp.setUnitAppl(poInfo.getAppliedx());
        loCreditApp.setSourceCD("APP");
        loCreditApp.setDetlInfo(loModel.getConstructedDetailedInfo());
        loCreditApp.setDownPaym(poInfo.getDownPaym());
        loCreditApp.setCreatedx(poInfo.getCreatedx());
        loCreditApp.setTransact(poInfo.getTransact());
        loCreditApp.setTimeStmp(AppConstants.DATE_MODIFIED);
        loCreditApp.setSendStat("0");

        EBranchLoanApplication loLoan = new EBranchLoanApplication();
        loLoan.setTransNox(poLoan.getGOCasNextCode());
        loLoan.setBranchCD(loCreditApp.getBranchCd());
        loLoan.setTransact(loCreditApp.getTransact());
        loLoan.setCompnyNm(loCreditApp.getBranchCd());
        loLoan.setDownPaym(String.valueOf(loGOCas.PurchaseInfo().getDownPayment()));
        loLoan.setAcctTerm(String.valueOf(loGOCas.PurchaseInfo().getAccountTerm()));
        loLoan.setCreatedX(loCreditApp.getCreatedx());
        loLoan.setTranStat("0");
        loLoan.setTimeStmp(AppConstants.DATE_MODIFIED);
        new UploadCreditApp(instance).UploadLoanApplication(loCreditApp, loLoan, listener);
    }

    private static class FetchReviewDetail extends AsyncTask<ECreditApplicantInfo, Void, List<ReviewAppDetail>>{
        private final Application instance;
        private final RMcModel poModel;
        private final RBarangay poBarangay;
        private final RTown poTown;
        private final RCountry poCountry;
        private final OnFetchReviewListener mListener;

        public FetchReviewDetail(Application application, OnFetchReviewListener listener) {
            this.instance = application;
            this.poModel = new RMcModel(instance);
            this.poBarangay = new RBarangay(instance);
            this.poTown = new RTown(instance);
            this.poCountry = new RCountry(instance);
            this.mListener = listener;
        }

        @Override
        protected List<ReviewAppDetail> doInBackground(ECreditApplicantInfo... eCreditApplicantInfos) {
            ECreditApplicantInfo poInfo = eCreditApplicantInfos[0];
            List<ReviewAppDetail> loListDetl = new ArrayList<>();
            try {
                GOCASApplication loGOCas = new GOCASApplication();
                loGOCas.setData(new GoCasBuilder(poInfo).getConstructedDetailedInfo());
                loListDetl.add(new ReviewAppDetail(true, "Purchase Info", "", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "Branch", loGOCas.PurchaseInfo().getPreferedBranch()));
                loListDetl.add(new ReviewAppDetail(false, "", "Unit Applied", loGOCas.PurchaseInfo().getBrandName()));

                EMcModel loModel = poModel.getModelInfo(loGOCas.PurchaseInfo().getModelID());
                String lsModelNme = loModel.getModelNme();
                loListDetl.add(new ReviewAppDetail(false, "", "Model", lsModelNme));

                String lsDownpayment = String.valueOf(loGOCas.PurchaseInfo().getDownPayment());
                loListDetl.add(new ReviewAppDetail(false, "", "Downpayment", FormatUIText.getCurrencyUIFormat(lsDownpayment)));

                String lsTerm = String.valueOf(loGOCas.PurchaseInfo().getAccountTerm());
                loListDetl.add(new ReviewAppDetail(false, "", "Installment Term", lsTerm + " Months"));

                String lsAmort = String.valueOf(loGOCas.PurchaseInfo().getMonthlyAmortization());
                loListDetl.add(new ReviewAppDetail(false, "", "Monthly Amortization", FormatUIText.getCurrencyUIFormat(lsAmort)));

                loListDetl.add(new ReviewAppDetail(true, "Personal Info", "", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "Full Name", loGOCas.ApplicantInfo().getClientName()));

                String lsBirthDate = FormatUIText.getBirthDateUIFormat(loGOCas.ApplicantInfo().getBirthdate());
                loListDetl.add(new ReviewAppDetail(false, "", "Birth Date", lsBirthDate));

                DTownInfo.TownProvinceName loBPlace = poTown.getTownProvinceName(loGOCas.ApplicantInfo().getBirthPlace());
                String lsBirthPlace = loBPlace.sTownName + ", " + loBPlace.sProvName;
                loListDetl.add(new ReviewAppDetail(false, "", "Birth Place", lsBirthPlace));
                loListDetl.add(new ReviewAppDetail(false, "", "Gender", parseGender(loGOCas.ApplicantInfo().getGender())));
                loListDetl.add(new ReviewAppDetail(false, "", "Civil Status", parseCivilStatus(loGOCas.ApplicantInfo().getCivilStatus())));
                loListDetl.add(new ReviewAppDetail(false, "", "Maiden Name", loGOCas.ApplicantInfo().getMaidenName()));

                ECountryInfo loCountry = poCountry.getCountryInfo(loGOCas.ApplicantInfo().getCitizenship());
                String lsCitizen = loCountry.getNational();
                loListDetl.add(new ReviewAppDetail(false, "", "Citizenship", lsCitizen));

                for(int x = 0; x < loGOCas.ApplicantInfo().getMobileNoQty(); x++) {
                    loListDetl.add(new ReviewAppDetail(false, "", "Mobile No",
                            loGOCas.ApplicantInfo().getMobileNo(x) + ", " + parseSimType(loGOCas.ApplicantInfo().IsMobilePostpaid(x))));
                }

                loListDetl.add(new ReviewAppDetail(false, "", "Landline", loGOCas.ApplicantInfo().getPhoneNo(0)));
                loListDetl.add(new ReviewAppDetail(false, "", "Email", loGOCas.ApplicantInfo().getEmailAddress(0)));
                loListDetl.add(new ReviewAppDetail(false, "", "Facebook", loGOCas.ApplicantInfo().getFBAccount()));
                loListDetl.add(new ReviewAppDetail(false, "", "Viber", loGOCas.ApplicantInfo().getViberAccount()));


                loListDetl.add(new ReviewAppDetail(true, "Present Residence Info", "", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "Landmark", loGOCas.ResidenceInfo().PresentAddress().getLandMark()));
                loListDetl.add(new ReviewAppDetail(false, "", "House No", loGOCas.ResidenceInfo().PresentAddress().getHouseNo()));
                loListDetl.add(new ReviewAppDetail(false, "", "Sitio/Lot No.", loGOCas.ResidenceInfo().PresentAddress().getAddress1()));
                loListDetl.add(new ReviewAppDetail(false, "", "Street", loGOCas.ResidenceInfo().PresentAddress().getAddress2()));

                DBarangayInfo.BrgyTownProvNames loPresent = poBarangay.getAddressInfo(loGOCas.ResidenceInfo().PresentAddress().getBarangay());
                loListDetl.add(new ReviewAppDetail(false, "", "Barangay", loPresent.sBrgyName));
                loListDetl.add(new ReviewAppDetail(false, "", "Town", loPresent.sTownName));
                loListDetl.add(new ReviewAppDetail(false, "", "Province", loPresent.sProvName));


                loListDetl.add(new ReviewAppDetail(false, "", "House Ownership", parseHouseOwn(loGOCas.ResidenceInfo().getOwnership())));
                if(loGOCas.ResidenceInfo().getOwnership().equalsIgnoreCase("1")){
                    loListDetl.add(new ReviewAppDetail(false, "", "HouseHolds", parseHouseHold(loGOCas.ResidenceInfo().getRentedResidenceInfo())));

                    String lsExp = String.valueOf(loGOCas.ResidenceInfo().getRentExpenses());
                    loListDetl.add(new ReviewAppDetail(false, "", "Monthly Rent", lsExp));
                    //loListDetl.add(new ReviewAppDetail(false, "", "Length of Stay", loGOCas.ResidenceInfo().getRentNoYears()));
                } else if(loGOCas.ResidenceInfo().getOwnership().equalsIgnoreCase("2")){
                    loListDetl.add(new ReviewAppDetail(false, "", "House Owner", loGOCas.ResidenceInfo().getCareTakerRelation()));
                }
                loListDetl.add(new ReviewAppDetail(false, "", "House Hold", parseHouseHold(loGOCas.ResidenceInfo().getOwnedResidenceInfo())));
                loListDetl.add(new ReviewAppDetail(false, "", "House Type", parseHouseType(loGOCas.ResidenceInfo().getHouseType())));
                loListDetl.add(new ReviewAppDetail(false, "", "Has Garage", parseGarage(loGOCas.ResidenceInfo().hasGarage())));

                /*
                  Permanent Residence Info
                 */
                loListDetl.add(new ReviewAppDetail(true, "Permanent Residence Info", "", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "Landmark", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "House No", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "Sitio/Lot No.", ""));
                loListDetl.add(new ReviewAppDetail(false, "", "Street", ""));

                DBarangayInfo.BrgyTownProvNames loPermanent = poBarangay.getAddressInfo(loGOCas.ResidenceInfo().PermanentAddress().getBarangay());
                loListDetl.add(new ReviewAppDetail(false, "", "Barangay", loPermanent.sBrgyName));
                loListDetl.add(new ReviewAppDetail(false, "", "Town", loPermanent.sTownName));
                loListDetl.add(new ReviewAppDetail(false, "", "Province", loPermanent.sProvName));


                loListDetl.add(new ReviewAppDetail(true, "Means Info", "", ""));
                String lsPrimary = loGOCas.MeansInfo().getIncomeSource();
                loListDetl.add(new ReviewAppDetail(true, "", "Primary Income", parseMeansPrimary(lsPrimary)));


                loListDetl.add(new ReviewAppDetail(true, "Employment Info", "", ""));

                String lsMSector = loGOCas.MeansInfo().EmployedInfo().getEmploymentSector();
                loListDetl.add(new ReviewAppDetail(true, "", "Employment Sector", parseSector(lsMSector)));
                loListDetl.add(new ReviewAppDetail(true, "Employment Info", "", ""));
                loListDetl.add(new ReviewAppDetail(true, "Employment Info", "", ""));

                loListDetl.add(new ReviewAppDetail(true));
            } catch (Exception e){
                e.printStackTrace();
            }
            return loListDetl;
        }

        @Override
        protected void onPostExecute(List<ReviewAppDetail> reviewAppDetails) {
            super.onPostExecute(reviewAppDetails);
            mListener.OnDetailLoad(reviewAppDetails);
        }

        String parseGender(String value){
            if(value.equalsIgnoreCase("0")){
                return "Male";
            } else if(value.equalsIgnoreCase("1")){
                return "Female";
            } else {
                return "LGBT";
            }
        }

        String parseCivilStatus(String value){
            return CreditAppConstants.CIVIL_STATUS[Integer.parseInt(value)];
        }

        String parseSimType(String value){
            return CreditAppConstants.MOBILE_NO_TYPE[Integer.parseInt(value)];
        }

        String parseHouseOwn(String value){
            if(value.equalsIgnoreCase("0")){
                return "Owned";
            } else if(value.equalsIgnoreCase("1")){
                return "Rent";
            } else {
                return "Care-Taker";
            }
        }

        String parseHouseHold(String value){
            return CreditAppConstants.HOUSEHOLDS[Integer.parseInt(value)];
        }

        String parseHouseType(String value){
            return CreditAppConstants.HOUSE_TYPE[Integer.parseInt(value)];
        }

        String parseGarage(String value){
            if(value.equalsIgnoreCase("0")){
                return "Yes";
            } else {
                return "No";
            }
        }

        String parseMeansPrimary(String value){
            switch (value){
                case "0":
                    return "Employment";
                case "1":
                    return "Self Employed";
                case "2":
                    return "Finance";
            }
            return "Pensioner";
        }

        String parseSector(String value){
            switch (value){
                case "0":
                    return "Goverment";
                case "1":
                    return "Private";
            }
            return "OFW";
        }
    }
}