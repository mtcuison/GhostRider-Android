package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;

public class VMPaidTransaction extends AndroidViewModel {
    private static final String TAG = VMPaidTransaction.class.getSimpleName();
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psEntryNox = new MutableLiveData<>();
    private final MutableLiveData<Double> pnAmount = new MutableLiveData<>();
    private final MutableLiveData<Double> pnDsCntx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnOthers = new MutableLiveData<>();
    private final MutableLiveData<Double> pnTotalx = new MutableLiveData<>();

    public VMPaidTransaction(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.pnDsCntx.setValue((double) 0);
        this.pnOthers.setValue((double) 0);
    }

    public void setParameter(String TransNox, String EntryNox){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(psTransNox.getValue(), psEntryNox.getValue());
    }

    public void setCurrentCollectionDetail(EDCPCollectionDetail detail){
        this.poDcpDetail.setValue(detail);
    }

    public LiveData<EBranchInfo> getUserBranchEmployee(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<ArrayAdapter<String>> getPaymentType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.PAYMENT_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void setAmount(Double fnAmount){
        this.pnAmount.setValue(fnAmount);
        calculateTotal();
    }

    public void setDiscount(Double fnDiscount){
        this.pnDsCntx.setValue(fnDiscount);
        calculateTotal();
    }

    public void setOthers(Double fnOthers){
        this.pnOthers.setValue(fnOthers);
        calculateTotal();
    }

    public LiveData<Double> getTotalAmount(){
        return pnTotalx;
    }

    private void calculateTotal(){
        double lnAmount = pnAmount.getValue();
        double lnDscntx = pnDsCntx.getValue();
        double lnOthers = pnOthers.getValue();
        double lnTotal = lnAmount + lnOthers - lnDscntx;
        pnTotalx.setValue(lnTotal);
    }

    public void savePaidInfo(PaidTransactionModel infoModel, ViewModelCallback callback){
        try{
            if(!infoModel.isDataValid()){
                callback.OnFailedResult(infoModel.getMessage());
            } else {
                EDCPCollectionDetail detail = poDcpDetail.getValue();
                detail.setTransNox(psTransNox.getValue());
                detail.setEntryNox(psEntryNox.getValue());
                detail.setRemCodex(infoModel.getRemarksCode());
                detail.setTranType(infoModel.getPayment());
                detail.setPRNoxxxx(infoModel.getPrNoxxx());
                detail.setTranAmtx(infoModel.getAmountx());
                detail.setDiscount(infoModel.getDscount());
                detail.setOthersxx(infoModel.getOthersx());
                detail.setTranTotl(infoModel.getTotAmnt());
                detail.setRemarksx(infoModel.getRemarks());
                detail.setSendStat("0");
                detail.setModified(AppConstants.DATE_MODIFIED);
                poDcp.updateCollectionDetailInfo(detail);
                callback.OnSuccessResult(new String[]{"Dcp Save!"});
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
        }
    }
}