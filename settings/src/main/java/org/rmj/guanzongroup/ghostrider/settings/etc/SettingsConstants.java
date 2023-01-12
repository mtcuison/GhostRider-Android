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

import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.guanzongroup.ghostrider.settings.R;

public class SettingsConstants {

    public static final int GCARD_SCAN = 111;
    public static int[] getHelpImages(Context context, int help) {
        if (help == AppConstants.INTENT_SELFIE_LOGIN) {
            return SELFIE_LOGIN_HELP_IMAGES;
        } else if (help == AppConstants.INTENT_DOWNLOAD_DCP) {
            return DOWNLOAD_DCP_HELP_IMAGES;
        } else if (help == AppConstants.INTENT_IMPORT_DCP) {
            return IMPORT_DCP_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_ADD_COLLECTION_DCP) {
            return DCP_HELP_ADD_COLLECTION_IMAGES;
        }else if (help == AppConstants.INTENT_TRANSACTION_DCP) {
            return DCP_TRANSACTION_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_POST_COLLECTION) {
            return DCP_POST_COLLECTION_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_REMITTANCE) {
            return DCP_REMITTANCE_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_LOG) {
            return DCP_LOG_HELP_IMAGES;
        }else if (help == AppConstants.INTENT_DCP_LIST) {
            return DCP_HELP_IMAGES;
        }
        return null;
    }
    //    DCP DOWNLOAD COLLECTION INSTRUCTION NOTICE
    private static int[] DOWNLOAD_DCP_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_1
    };
    //    DCP DOWNLOAD COLLECTION INSTRUCTION NOTICE
    private static int[] IMPORT_DCP_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_import_1,
            R.drawable.help_dcp_import_2,
            R.drawable.help_dcp_import_3,
            R.drawable.help_dcp_import_4,
            R.drawable.help_dcp_import_5,
            R.drawable.help_dcp_import_6,
            R.drawable.help_dcp_import_7
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

    //    DCP INSTRUCTION NOTICE
    private static int[] DCP_HELP_IMAGES = new int[]{
            R.drawable.default_help,
            R.drawable.help_dcp_1,
            R.drawable.help_dcp_import_1,
            R.drawable.help_dcp_import_2,
            R.drawable.help_dcp_import_3,
            R.drawable.help_dcp_import_4,
            R.drawable.help_dcp_import_5,
            R.drawable.help_dcp_import_6,
            R.drawable.help_dcp_import_7,
            R.drawable.help_dcp_post_collection_1,
            R.drawable.help_dcp_post_collection_2
    };



}
