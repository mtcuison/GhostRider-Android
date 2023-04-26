package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EInventoryDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RInventoryDetail;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;

public class VMInventoryEntry extends AndroidViewModel {
    private static final String TAG = VMInventoryEntry.class.getSimpleName();

    private final Application instance;
    private final RInventoryDetail poDetail;

    public interface OnInventoryUpdateCallBack{
        void OnUpdate(String message);
        void OnError(String message);
    }

    public VMInventoryEntry(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDetail = new RInventoryDetail(instance);
    }

    public LiveData<EInventoryDetail> getInventoryItemDetail(String TransNox, String PartID, String BarCode){
        return poDetail.getInventoryItemDetail(TransNox, PartID, BarCode);
    }

    public void saveInventoryUpdate(RandomItem foItem, OnInventoryUpdateCallBack callBack){
        new UpdateInventoryUpdateTask(instance, callBack).execute(foItem);
    }

    private static class UpdateInventoryUpdateTask extends AsyncTask<RandomItem, Void, Boolean>{

        private final OnInventoryUpdateCallBack callBack;
        private final RInventoryDetail poDetail;

        private String message;

        public UpdateInventoryUpdateTask(Application instance, OnInventoryUpdateCallBack callBack){
            this.callBack = callBack;
            this.poDetail = new RInventoryDetail(instance);
        }

        @Override
        protected Boolean doInBackground(RandomItem... randomItems) {
            try {
                RandomItem loItem = randomItems[0];
                poDetail.UpdateInventoryItem(loItem.getTransNox(),
                        loItem.getBarCodex(),
                        loItem.getPartIDxx(),
                        loItem.getItemQtyx(),
                        loItem.getRemarksx());
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callBack.OnUpdate("Item updated.");
            } else {
                callBack.OnError(message);
            }
        }
    }
}
