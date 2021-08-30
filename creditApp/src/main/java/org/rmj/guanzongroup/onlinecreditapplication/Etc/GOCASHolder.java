/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 8/27/21 3:44 PM
 * project file last modified : 8/27/21 3:44 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Etc;

import org.rmj.gocas.base.GOCASApplication;

public final class GOCASHolder {
    private static GOCASHolder INSTANCE;
    private static GOCASApplication poGocasxx;

    private GOCASHolder() { }

    public static GOCASHolder getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GOCASHolder();
        }
        return INSTANCE;
    }

    // Initialize it on onCreate() of Activity_CreditApplication.
    public void initGOCASApp() {
        if(poGocasxx == null) {
            poGocasxx = new GOCASApplication();
        }
        return;
    }

    // Call it on Fragment's ViewModel to setData.
    public GOCASApplication getGOCAS() {
        return poGocasxx;
    }

    // You may call GOCASHolder.getInstance().getGocas().toJSONString() to display value.

    // Clear when GOCAS' purpose is done.
    public void clearGOCASHolder() {
        INSTANCE = null;
        // or poGocasxx = null;
    }

}
