/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 5/26/21 9:48 AM
 * project file last modified : 5/26/21 9:48 AM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Etc;

import android.app.AlertDialog;
import android.app.Application;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

public class DialogDataCheck {
    private static final String TAG = DialogDataCheck.class.getSimpleName();

    private final Application instance;
    private final OnDataCheckListener mlistener;
    private AlertDialog poDialogx;

    public interface OnDataCheckListener{
        void OnCheck();
        void OnCancel();
    }

    public DialogDataCheck(Application application, OnDataCheckListener listener){
        this.instance = application;
        this.mlistener = listener;
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }
}
