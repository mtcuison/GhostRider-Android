/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 7/9/21 1:14 PM
 * project file last modified : 7/9/21 1:14 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.etc;

import android.content.Context;

import org.rmj.g3appdriver.GCircle.Account.EmployeeSession;
import org.rmj.guanzongroup.ghostrider.settings.Model.SettingsModel;
import org.rmj.guanzongroup.ghostrider.settings.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.VISIBLE;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList.listHelpDataChild;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList.listHelpDataHeader;

public class SettingsData {
    EmployeeSession sessionManager;
    public void SettingsData(Context mContext) {
        listHelpDataHeader.clear();
        listHelpDataChild.clear();
        sessionManager = new EmployeeSession(mContext);
        SettingsModel helpModel = new SettingsModel("SelfieLogin", R.drawable.ic_camera_black_24, true, false, VISIBLE);
        listHelpDataHeader.add(helpModel);

        if (!helpModel.hasChildren) {
            listHelpDataChild.put(helpModel, null);
        }

        List<SettingsModel> childModelsList = new ArrayList<>();

        helpModel = new SettingsModel("Daily Collection Plan (DCP)", R.drawable.ic_menu_dcp, true, true, VISIBLE);
        listHelpDataHeader.add(helpModel);

        SettingsModel childModel = new SettingsModel("Download DCP List", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new SettingsModel("Import DCP List", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new SettingsModel("Add Collection", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new SettingsModel("DCP Transaction", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new SettingsModel("DCP Post Collection", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new SettingsModel("DCP Remittance", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new SettingsModel("DCP Log", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        if (helpModel.hasChildren) {
            listHelpDataChild.put(helpModel, childModelsList);
        }
//        menuModel = new SettingsModel("CreditApp", R.drawable.ic_menu_credit_investigate, VISIBLE);
//        menuModel = new SettingsModel("SelfieLogin", R.drawable.ic_camera_black_24, true, false, VISIBLE);
    }
}
