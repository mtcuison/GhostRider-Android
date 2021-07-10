/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 7/8/21 9:57 AM
 * project file last modified : 7/8/21 9:57 AM
 */

package org.rmj.guanzongroup.ghostrider.settings.etc;

import android.content.Context;
import android.util.Log;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.settings.R;

public class SettingsConstants {

    public static int[] getHelpImages(Context context, int help) {
        if (help == AppConstants.INTENT_SELFIE_LOGIN) {
            AppConfigPreference.getInstance(context).setIsHelpLoginNotice(false);
            return SELFIE_LOGIN_HELP_IMAGES;
        } else if (help == AppConstants.INTENT_DOWNLOAD_IMPORT_DCP) {
            AppConfigPreference.getInstance(context).setIsHelpDownloadDCPNotice(false);
            return DOWNLOAD_IMPORT_DCP_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_ADD_COLLECTION_DCP) {
            return DCP_HELP_ADD_COLLECTION_IMAGES;
        }
        return null;
    }

    private static int[] DOWNLOAD_IMPORT_DCP_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_1,
            R.drawable.help_dcp_2,
            R.drawable.help_dcp_3
    };
    private static int[] DCP_HELP_ADD_COLLECTION_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_add_collection_1,
            R.drawable.help_add_collection_2,
            R.drawable.help_add_collection_3
    };

    private static int[] SELFIE_LOGIN_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_login_1,
            R.drawable.help_login_2,
            R.drawable.help_login_3,
            R.drawable.help_login_4,
    };



}
