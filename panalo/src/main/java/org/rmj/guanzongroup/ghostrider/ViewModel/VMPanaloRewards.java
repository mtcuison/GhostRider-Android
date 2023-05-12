package org.rmj.guanzongroup.ghostrider.ViewModel;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.lib.Panalo.Obj.GPanalo;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.ghostrider.Model.PanaloReward;

import java.util.ArrayList;
import java.util.List;

public class VMPanaloRewards extends AndroidViewModel {
    private static final String TAG = VMPanaloRewards.class.getSimpleName();

    private final GPanalo poSys;

    public interface OnRetrieveRewardsListener{
        void OnLoad(String title, String message);
        void OnSuccess(List<PanaloRewards> earned, List<PanaloRewards> claimed);
        void OnFailed(String message);
    }



    public VMPanaloRewards(@NonNull Application application) {
        super(application);
       this.poSys = new GPanalo(application);
    }

    public void GetRewards(int fnArgs, OnRetrieveRewardsListener listener){
        new GetRewardsTask(listener).execute(fnArgs);
    }

    private class GetRewardsTask extends AsyncTask<Integer, Void, Boolean>{

        private final OnRetrieveRewardsListener listener;
        private List<PanaloRewards> earned, claimed;
        private String message;

        public GetRewardsTask(OnRetrieveRewardsListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnLoad("Panalo Rewards", "Checking rewards. Please wait...");
        }

        @Override
        protected Boolean doInBackground(Integer... integers) {
            try {
                earned = poSys.GetRewards("0");
                if (earned == null) {
                    message = poSys.getMessage();
                    Log.e(TAG, message);
                }

                Thread.sleep(1000);
                claimed = poSys.GetRewards("1");
                if (claimed == null) {
                    message = poSys.getMessage();
                    Log.e(TAG, message);
                }
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = getLocalMessage(e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                listener.OnFailed(message);
            } else {
                listener.OnSuccess(earned, claimed);
            }
        }
    }

    public void RedeemReward(){

    }
}
