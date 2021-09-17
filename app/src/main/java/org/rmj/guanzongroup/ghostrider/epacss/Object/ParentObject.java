/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 9/2/21, 2:20 PM
 * project file last modified : 9/2/21, 2:20 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Object;

import android.content.Context;
import android.content.Intent;

import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Browser;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_DigitalGcard;

public class ParentObject {

    private final String psObjName;
    private final String pcParentx;

    public ParentObject(String psObjName, String pcParentx) {
        this.psObjName = psObjName;
        this.pcParentx = pcParentx;
    }

    public String getMenuName(){
        return psObjName;
    }

    public int getMenuIcon(){
        int lnIcon = 0;
        switch (psObjName.toLowerCase()){
            case "samsung knox":
                lnIcon = R.drawable.ic_menu_knox;
                break;
            case "credit application":
                lnIcon = R.drawable.ic_menu_creditapp;
                break;
            case "daily collection plan":
                lnIcon = R.drawable.ic_menu_dcp;
                break;
            case "pet manager":
                lnIcon = R.drawable.ic_approval_biometric;
                break;
            case "health checklist":
                lnIcon = R.drawable.ic_checklist;
                break;
            case "ah monitoring":
                lnIcon = R.drawable.ic_menu_monitoring;
                break;
            case "ci evaluation":
                lnIcon = R.drawable.ic_menu_credit_investigate;
                break;
            case "approval code":
                lnIcon = R.drawable.ic_menu_approval;
                break;
            case "gcard info":
                lnIcon = R.drawable.ic_scan_qr_code;
                break;
        }
        return lnIcon;
    }

    public boolean isParent(){
        return pcParentx.equalsIgnoreCase("1");
    }

    public Intent getIntent(Context context){
        Intent loIntent;
        switch (psObjName.toLowerCase()){
            case "health checklist":
                loIntent = new Intent(context, Activity_Browser.class);
                loIntent.putExtra("url_link", "https://www.google.com/webhp?hl=ceb&sa=X&ved=0ahUKEwj0ne24tI7xAhV8yIsBHbQ7Az0QPAgI");
                loIntent.putExtra("syscode", "2");
                break;

            case "gcard info":
                loIntent = new Intent(context, Activity_DigitalGcard.class);
                break;

            default:
                loIntent = null;
        }
        return loIntent;
    }
}
