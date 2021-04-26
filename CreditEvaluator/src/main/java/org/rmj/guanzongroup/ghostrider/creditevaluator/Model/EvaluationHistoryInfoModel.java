/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import android.view.View;

public class EvaluationHistoryInfoModel {
    private static final String TAG = EvaluationHistoryInfoModel.class.getSimpleName();

    private final boolean isHeader;
    private final String psHeader;
    private final String psLabel;
    private final String psContent;

    public EvaluationHistoryInfoModel(boolean isHeader, String psHeader, String psLabel, String psContent) {
        this.isHeader = isHeader;
        this.psHeader = psHeader;
        this.psLabel = psLabel;
        this.psContent = psContent;
    }

    public int isHeader() {
        if(isHeader){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public int isContent(){
        if(!isHeader){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public String getHeader() {
        return psHeader;
    }

    public String getLabel() {
        return psLabel + " :";
    }

    public String getContent() {
        return psContent;
    }

}
