package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.content.Intent;

public interface OnInitializeCameraCallback {
    void OnInit();
    void OnSuccess(Intent intent, String[] args);
    void OnFailed(String message, Intent intent, String[] args);
}