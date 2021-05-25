/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/25/21 1:24 PM
 * project file last modified : 5/25/21 1:24 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

public class CISpecialInfoModel {
    private static final String TAG = CISpecialInfoModel.class.getSimpleName();
    private final String sCiSpeclx;

    public CISpecialInfoModel(String fsCiSpecl) {
        this.sCiSpeclx = fsCiSpecl;
    }

    public String getCiSpecial() {
        return sCiSpeclx;
    }

}
