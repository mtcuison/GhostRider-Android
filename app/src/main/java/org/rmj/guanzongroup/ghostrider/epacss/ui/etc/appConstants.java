package org.rmj.guanzongroup.ghostrider.epacss.ui.etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_AH_Dashboard;
import org.rmj.guanzongroup.ghostrider.epacss.ui.home.Fragment_Dashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;

public class appConstants {

    public static Fragment[] APPLICATION_HOME_PAGES = {
            new Fragment_AH_Dashboard(),
            new Fragment_MessageList(),
            new Fragment_NotificationList()
    };
}
