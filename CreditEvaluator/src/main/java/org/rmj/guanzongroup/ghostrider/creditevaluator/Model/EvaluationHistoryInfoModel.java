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
