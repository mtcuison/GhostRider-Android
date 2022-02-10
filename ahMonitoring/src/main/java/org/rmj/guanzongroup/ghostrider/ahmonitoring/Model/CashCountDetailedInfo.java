package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

import android.view.View;

public class CashCountDetailedInfo {
    private boolean isHeader;
    private String psHeader;
    private String psLabel;
    private String psContent;

    public CashCountDetailedInfo(boolean isHeader, String psHeader, String psLabel, String psContent) {
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
