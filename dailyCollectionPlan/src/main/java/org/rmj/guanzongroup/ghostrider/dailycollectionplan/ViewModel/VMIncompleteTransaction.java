package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

import java.util.Objects;

public class VMIncompleteTransaction extends AndroidViewModel {
    private static final String TAG = VMIncompleteTransaction.class.getSimpleName();
    private final RDailyCollectionPlan poDcp;
    private final RImageInfo poImage;

    //TODO: Temporary initialization...
    private String ImgTransNox;

    private final MutableLiveData<EDCPCollectionDetail> poDetail = new MutableLiveData<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> nEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> sAccntNox = new MutableLiveData<>();
    private final MutableLiveData<String> sImgPathx = new MutableLiveData<>();

    public VMIncompleteTransaction(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poImage = new RImageInfo(application);
    }

    public void setParameter(String fsTransNox, String fnEntryNox){
        this.sTransNox.setValue(fsTransNox);
        this.nEntryNox.setValue(fnEntryNox);
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
        this.poDetail.setValue(detail);
    }

    public void saveImageInfo(EImageInfo foImageInfo){
        ImgTransNox = poDcp.getImageNextCode();
        foImageInfo.setTransNox(ImgTransNox);
        foImageInfo.setDtlSrcNo(sAccntNox.getValue());
        poImage.insertImageInfo(foImageInfo);
    }

    public void updateCollectionDetail(String RemarksCode){
        EDCPCollectionDetail detail = poDetail.getValue();
        Objects.requireNonNull(detail).setImageNme(ImgTransNox);
        new UpdateCollectionTask(poDcp, RemarksCode).execute(detail);
    }

    private static class UpdateCollectionTask extends AsyncTask<EDCPCollectionDetail, Void, String>{
        private final RDailyCollectionPlan poDcp;
        private final String RemarksCode;

        public UpdateCollectionTask(RDailyCollectionPlan poDcp, String Remarks){
            this.poDcp = poDcp;
            this.RemarksCode = Remarks;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... detail) {
            if(!RemarksCode.equalsIgnoreCase("")) {
                poDcp.updateCollectionDetail(detail[0].getEntryNox(), RemarksCode);
            } else {
                Log.e(TAG, "Unable to update collection detail. Reason: Invalid remarks code.");
            }
            return null;
        }
    }
}