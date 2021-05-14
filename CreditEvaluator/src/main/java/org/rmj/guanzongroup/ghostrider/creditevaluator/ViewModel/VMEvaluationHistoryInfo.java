/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

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

    public VMEvaluationHistoryInfo(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poInvestx = new RCIEvaluation(application);
    }

    public LiveData<ECIEvaluation> getAllDoneCiInfo(String fsTransNo) {
        return poInvestx.getAllDoneCiInfo(fsTransNo);
    }

    public void onFetchCreditEvaluationDetail(ECIEvaluation foCiDetl, OnFetchCustomerEvaluationInfo fmListenr) {
        new CustomerEvaluationDetailTask(this.instance, fmListenr).execute(foCiDetl);
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
