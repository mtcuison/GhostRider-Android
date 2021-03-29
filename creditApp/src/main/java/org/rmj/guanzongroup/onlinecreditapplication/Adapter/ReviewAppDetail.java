package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.view.View;

public class ReviewAppDetail {

    private boolean isHeader;
    private String psHeader;
    private String psLabel;
    private String psContent;
    private boolean isFooter;

    /**
     *
     * @param isHeader set
     * @param psHeader set
     * @param psLabel set
     * @param psContent set
     */
    public ReviewAppDetail(boolean isHeader, String psHeader, String psLabel, String psContent) {
        this.isHeader = isHeader;
        this.psHeader = psHeader;
        this.psLabel = psLabel;
        this.psContent = psContent;
    }

    /**
     *
     * @param isFooter set footer if all details are already set to enable save button...
     */
    public ReviewAppDetail(boolean isFooter) {
        this.isFooter = isFooter;
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

    public int isFooter(){
        if(!isFooter){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public String getLabel() {
        return psLabel;
    }

    public String getContent() {
        return psContent;
    }
}
