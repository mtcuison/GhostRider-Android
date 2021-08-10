/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;

import java.util.Objects;

public class VMIncompleteTransaction extends AndroidViewModel {
    private static final String TAG = VMIncompleteTransaction.class.getSimpleName();
    private final RDailyCollectionPlan poDcp;
    private final RImageInfo poImage;

    //TODO: Temporary initialization...
    private String ImgTransNox;

    private final MutableLiveData<EDCPCollectionDetail> poDetail = new MutableLiveData<>();
    private final MutableLiveData<String> sRemarks = new MutableLiveData<>();
    private final MutableLiveData<String> sRemCodex = new MutableLiveData<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> nEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> sAccntNox = new MutableLiveData<>();
    private final MutableLiveData<String> sImgPathx = new MutableLiveData<>();

    public VMIncompleteTransaction(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poImage = new RImageInfo(application);
    }

    public void setParameter(String fsTransNox, int fnEntryNox, String fsRemarksx){
        this.sTransNox.setValue(fsTransNox);
        this.nEntryNox.setValue(fnEntryNox);
        this.sRemCodex.setValue(DCP_Constants.getRemarksCode(fsRemarksx));
        Log.e(TAG, DCP_Constants.getRemarksCode(fsRemarksx));
    }

    public void setRemarks(String Remarksx){
        this.sRemarks.setValue(Remarksx);
    }

    public void setAccountNo(String fsAccntNo){
        this.sAccntNox.setValue(fsAccntNo);
    }

    public void setImagePath(String fsPath){
        this.sImgPathx.setValue(fsPath);
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(sTransNox.getValue(), nEntryNox.getValue());
    }

    public void setCollectioNDetail(EDCPCollectionDetail detail){
        this.poDetail.setValue(Objects.requireNonNull(detail));
    }

    public void saveImageInfo(EImageInfo foImageInfo){
        ImgTransNox = poImage.getImageNextCode();
        foImageInfo.setTransNox(ImgTransNox);
        foImageInfo.setDtlSrcNo(sAccntNox.getValue());
        poImage.insertImageInfo(foImageInfo);
    }

    public void updateCollectionDetail(){
        EDCPCollectionDetail detail = Objects.requireNonNull(poDetail.getValue());
        Objects.requireNonNull(detail).setImageNme(ImgTransNox);
        new UpdateCollectionTask(poDcp, sRemCodex.getValue(), sRemarks.getValue()).execute(detail);
    }

    private static class UpdateCollectionTask extends AsyncTask<EDCPCollectionDetail, Void, String>{
        private final RDailyCollectionPlan poDcp;
        private final String RemarksCode;
        private final String Remarksx;

        public UpdateCollectionTask(RDailyCollectionPlan poDcp, String RemCode, String Remarksx){
            this.poDcp = poDcp;
            this.RemarksCode = RemCode;
            this.Remarksx = Remarksx;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... detail) {
            if(!RemarksCode.equalsIgnoreCase("")) {
                poDcp.updateCollectionDetail(detail[0].getEntryNox(), RemarksCode, Remarksx);
            } else {
                Log.e(TAG, "Unable to update collection detail. Reason: Invalid remarks code.");
            }
            return null;
        }
    }
}