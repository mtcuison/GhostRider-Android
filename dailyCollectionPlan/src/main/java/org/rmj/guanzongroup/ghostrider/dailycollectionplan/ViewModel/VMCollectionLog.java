package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMCollectionLog extends AndroidViewModel {
    private static final String TAG = VMCollectionLog.class.getSimpleName();
    private final RDailyCollectionPlan poDcp;
    private final RBranch poBranch;
    private final RImageInfo poImage;
    private final RCollectionUpdate poUpdate;

    private final MutableLiveData<EDCPCollectionMaster> poMaster = new MutableLiveData<>();
    private final MutableLiveData<List<EImageInfo>> plImageLst = new MutableLiveData<>();
    private final MutableLiveData<List<EAddressUpdate>> paAddress = new MutableLiveData<>();
    private final MutableLiveData<List<EMobileUpdate>> paMobile = new MutableLiveData<>();
    private final MutableLiveData<String> dTransact = new MutableLiveData<>();

    private final MutableLiveData<List<DDCPCollectionDetail.CollectionDetail>> plDetail = new MutableLiveData<>();

    public VMCollectionLog(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poImage = new RImageInfo(application);
        this.poUpdate = new RCollectionUpdate(application);
        this.dTransact.setValue(AppConstants.CURRENT_DATE);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return poDcp.getCollectionMaster();
    }

    public void setCollectionMaster(EDCPCollectionMaster collectionMaster){
        try {
            this.poMaster.setValue(collectionMaster);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EImageInfo>> getUnsentImageInfoList(){
        return poImage.getUnsentImageList();
    }

    public void setDateTransact(String fsTransact){
        try {
            @SuppressLint("SimpleDateFormat") Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(fsTransact);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat lsFormatter = new SimpleDateFormat("yyyy-MM-dd");
        this.dTransact.setValue(lsFormatter.format(Objects.requireNonNull(loDate)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public LiveData<String> getDateTransact(){
        return dTransact;
    }

    public LiveData<List<EDCPCollectionDetail>> getCollectionDetailForDate(String dTransact){
        return poDcp.getCollectionDetailForDate(dTransact);
    }

    public void setImageInfoList(List<EImageInfo> imageInfoList){
        this.plImageLst.setValue(imageInfoList);
    }

    public LiveData<List<EAddressUpdate>> getAllAddress(){
        return poUpdate.getAddressList();
    }

    public LiveData<List<EMobileUpdate>> getAllMobileNox(){
        return poUpdate.getMobileList();
    }

    public void setAddressList(List<EAddressUpdate> paAddress) {
        this.paAddress.setValue(paAddress);
    }

    public void setMobileList(List<EMobileUpdate> paMobile) {
        this.paMobile.setValue(paMobile);
    }
}
