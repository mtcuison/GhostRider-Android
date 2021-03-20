package org.rmj.g3appdriver.utils;

import android.content.Context;
import android.content.IntentSender;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class UpdateChecker {
    private static final String TAG = UpdateChecker.class.getSimpleName();

    private Context context;
    private AppCompatActivity mActivity;

    public interface OnUpdateCheckListener{
        void OnCheck(AppUpdateInfo result, AppUpdateManager updateManager);
    }

    public UpdateChecker(Context context, AppCompatActivity activity){
        this.context = context;
        mActivity = activity;
    }

    /**
     *
     */
    public void Check_Update(final OnUpdateCheckListener listener) {
        //create instance of the update manager.
        final AppUpdateManager updateManager = AppUpdateManagerFactory.create(context);

        //returns an intent object that you use to check for an update.
        final Task<AppUpdateInfo> appUpdateInfoTask = updateManager.getAppUpdateInfo();

        appUpdateInfoTask.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                listener.OnCheck(result, updateManager);
                Log.e(TAG, String.valueOf(result.updateAvailability()));
                if(result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE){
                    try {
                        updateManager.startUpdateFlowForResult(result,
                                AppUpdateType.IMMEDIATE,
                                mActivity,
                                InAppUpdateResult.RESULT_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    public interface InAppUpdateResult{
        int RESULT_CODE = 1021;
    }
}
