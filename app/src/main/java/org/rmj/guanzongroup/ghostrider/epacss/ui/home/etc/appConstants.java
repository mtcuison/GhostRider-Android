/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ui.home.etc;

import androidx.fragment.app.Fragment;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Associate_Dashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;

public class appConstants {

    public static Fragment[] getHomePages(int employeLevel){
        if (employeLevel == DeptCode.LEVEL_RANK_FILE){
            return APPLICATION_HOME_PAGES;
        } else if(employeLevel == DeptCode.LEVEL_AREA_MANAGER){
            return APPLICATION_AH_HOME_PAGES;
        } else {
            return APPLICATION_HOME_PAGES;
        }
    }

    private static final Fragment[] APPLICATION_HOME_PAGES = {
            new Fragment_Associate_Dashboard(),
            new Fragment_MessageList(),
            new Fragment_NotificationList()
    };

    private static final Fragment[] APPLICATION_AH_HOME_PAGES = {
            new Fragment_Associate_Dashboard(),
            new Fragment_MessageList(),
            new Fragment_NotificationList()
    };
}
