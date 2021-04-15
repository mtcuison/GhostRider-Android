package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.EvaluationHistoryInfoModel;

import java.util.ArrayList;
import java.util.List;

public class VMEvaluationHistoryInfo extends AndroidViewModel {
    private static final String TAG = VMEvaluationHistoryInfo.class.getSimpleName();
    private final Application instance;
    private final RCIEvaluation poInvestx;
    private ECIEvaluation poCredtEv;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();

    public VMEvaluationHistoryInfo(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poInvestx = new RCIEvaluation(application);
    }

    public void setCreditEvaluationObject(ECIEvaluation foCredtEv) {
        this.poCredtEv = foCredtEv;
        setTransNo(this.poCredtEv.getTransNox());
    }

    public void onFetchCreditEvaluationDetail(OnFetchCustomerEvaluationInfo fmListenr) {
        new CustomerEvaluationDetailTask(this.instance, fmListenr).execute(this.poCredtEv);
    }

    private void setTransNo(String fsTransNo) {
        this.psTransNo.setValue(fsTransNo);
    }

    private static class CustomerEvaluationDetailTask extends AsyncTask<ECIEvaluation, Void, List<EvaluationHistoryInfoModel>> {
        private final Application instance;
        private final OnFetchCustomerEvaluationInfo pmListener;

        public CustomerEvaluationDetailTask(Application fsInstance, OnFetchCustomerEvaluationInfo fmListener) {
            this.instance = fsInstance;
            this.pmListener = fmListener;
        }

        @Override
        protected List<EvaluationHistoryInfoModel> doInBackground(ECIEvaluation... eciEvaluations) {
            ECIEvaluation loDetail = eciEvaluations[0];
            List<EvaluationHistoryInfoModel> loListDetl = new ArrayList<>();
            try {
                // Headers
                loListDetl.add(new EvaluationHistoryInfoModel(true, "Residence Information", "", ""));

                loListDetl.add(new EvaluationHistoryInfoModel(true, "Barangay Record & Neighbor's Information", "", ""));

                loListDetl.add(new EvaluationHistoryInfoModel(true, "Disbursement Information", "", ""));

                loListDetl.add(new EvaluationHistoryInfoModel(true, "Character Traits", "", ""));

                // Infos
                loListDetl.add(new EvaluationHistoryInfoModel(false, "", "Landmark", loDetail.getLandMark()));
                loListDetl.add(new EvaluationHistoryInfoModel(false, "", "House Ownership", loDetail.getOwnershp()));
                loListDetl.add(new EvaluationHistoryInfoModel(false ,"", "Households", loDetail.getOwnOther()));
                loListDetl.add(new EvaluationHistoryInfoModel(false, "", "Type of House", loDetail.getHouseTyp()));
                loListDetl.add(new EvaluationHistoryInfoModel(false, "", "Has Garage?", loDetail.getGaragexx()));

            } catch(Exception e) {
                e.printStackTrace();
            }
            return loListDetl;
        }

        @Override
        protected void onPostExecute(List<EvaluationHistoryInfoModel> evaluationInfo) {
            super.onPostExecute(evaluationInfo);
            pmListener.onDisplayList(evaluationInfo);
        }
    }

    public interface OnFetchCustomerEvaluationInfo {
        void onDisplayList(List<EvaluationHistoryInfoModel> flDetails);
    }

}
