package org.rmj.guanzongroup.ghostrider.epacss.ui.etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.ghostrider.epacss.ui.home.FragmentDashboard;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_MessageList;
import org.rmj.guanzongroup.ghostrider.notifications.Fragment.Fragment_NotificationList;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_CoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_Dependent;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_DisbursementInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_EmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_Finance;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_MeansInfoSelection;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_OtherInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PensionInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_PersonalInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_ResidenceInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SelfEmployedInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseEmploymentInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpousePensionInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseResidenceInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Fragment.Fragment_SpouseSelfEmployedInfo;

public class appConstants {

    public static Fragment[] APPLICATION_HOME_PAGES = {
            new FragmentDashboard(),
            new Fragment_MessageList(),
            new Fragment_NotificationList()
    };
}
