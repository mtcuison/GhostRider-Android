/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

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
