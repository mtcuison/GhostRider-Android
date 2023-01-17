/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 4/8/22, 9:37 AM
 * project file last modified : 4/8/22, 9:37 AM
 */

package org.guanzongroup.com.creditevaluation.Model;

import android.view.View;

public class EvaluationCIHistoryInfoModel {
    private boolean isHeader;
    private String psHeader = "";
    private String psLabel = "";
    private String psContent = "";

    public EvaluationCIHistoryInfoModel(boolean isHeader, String psHeader, String psLabel, String psContent) {
        this.isHeader = isHeader;
        this.psHeader = psHeader;
        this.psLabel = psLabel;
        this.psContent = psContent;
    }

    public String getHeader() {
        return psHeader;
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

    public String getLabel() {
        return psLabel + " :";
    }

    public String getContent() {
        if(psContent==null) {
            return "";
        }
        return psContent;
    }
}
