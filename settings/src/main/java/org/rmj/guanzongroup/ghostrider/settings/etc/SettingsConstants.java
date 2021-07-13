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
            AppConfigPreference.getInstance(context).setIsHelpAddDCPCollectionNotice(false);
            return DCP_HELP_ADD_COLLECTION_IMAGES;
        }else if (help == AppConstants.INTENT_TRANSACTION_DCP) {
            AppConfigPreference.getInstance(context).setIsHelpDCPTransactionNotice(false);
            return DCP_TRANSACTION_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_POST_COLLECTION) {
            AppConfigPreference.getInstance(context).setIsHelpDCPPostCollectionNotice(false);
            return DCP_POST_COLLECTION_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_REMITTANCE) {
            AppConfigPreference.getInstance(context).setIsHelpDCPPostCollectionNotice(false);
            return DCP_REMITTANCE_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_LOG) {
            AppConfigPreference.getInstance(context).setIsHelpDCPPostCollectionNotice(false);
            return DCP_LOG_HELP_IMAGES;
        }
        return null;
    }
    //    DCP DOWNLOAD/IMPORT COLLECTION INSTRUCTION NOTICE
    private static int[] DOWNLOAD_IMPORT_DCP_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_1,
            R.drawable.help_dcp_2,
            R.drawable.help_dcp_3
    };
//    DCP ADD COLLECTION INSTRUCTION NOTICE
    private static int[] DCP_HELP_ADD_COLLECTION_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_add_collection_1,
            R.drawable.help_add_collection_2,
            R.drawable.help_add_collection_3
    };
    //    DCP TRANSACTION INSTRUCTION NOTICE
    private static int[] DCP_TRANSACTION_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_transact_1,
            R.drawable.help_dcp_transact_2,
            R.drawable.help_dcp_transact_3,
            R.drawable.help_dcp_transact_4
    };
    //    DCP POST COLLECTION INSTRUCTION NOTICE
    private static int[] DCP_POST_COLLECTION_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_post_collection_1,
            R.drawable.help_dcp_post_collection_2
    };
    //    DCP REMITTANCE INSTRUCTION NOTICE
    private static int[] DCP_REMITTANCE_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_remittance_1,
            R.drawable.help_dcp_remittance_2,
            R.drawable.help_dcp_remittance_3,
            R.drawable.help_dcp_remittance_4
    };

    //    DCP LOG INSTRUCTION NOTICE
    private static int[] DCP_LOG_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_log_1,
            R.drawable.help_dcp_log_2,
            R.drawable.help_dcp_log_3
    };
    //    SELFIE LOGIN INSTRUCTION NOTICE
    private static int[] SELFIE_LOGIN_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_login_1,
            R.drawable.help_login_2,
            R.drawable.help_login_3,
            R.drawable.help_login_4,
    };



}
