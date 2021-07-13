/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 7/8/21 1:34 PM
 * project file last modified : 7/8/21 1:34 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.settings.etc.SettingsConstants;

public class VMHelp  extends AndroidViewModel {

    private final Application instance;
    private int[] img;
    public VMHelp(@NonNull Application application) {
        super(application);
        this.instance = application;
    }
}
