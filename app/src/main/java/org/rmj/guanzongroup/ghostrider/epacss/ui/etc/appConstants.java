package org.rmj.guanzongroup.ghostrider.epacss.ui.etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_AH_Dashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Dashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;

public class appConstants {

    public static Fragment[] getHomePages(int employeLevel){
        if (employeLevel == 0){
            return APPLICATION_HOME_PAGES;
        } else {
            return APPLICATION_AH_HOME_PAGES;
        }
    }

    private static final Fragment[] APPLICATION_HOME_PAGES = {
            new Fragment_Dashboard(),
            new Fragment_MessageList(),
            new Fragment_NotificationList()
    };

    private static final Fragment[] APPLICATION_AH_HOME_PAGES = {
            new Fragment_AH_Dashboard(),
            new Fragment_MessageList(),
            new Fragment_NotificationList()
    };
}
