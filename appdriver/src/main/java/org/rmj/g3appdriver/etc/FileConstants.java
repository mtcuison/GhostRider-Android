/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.etc;

public class FileConstants {

    public static final String ROOT_DIRECTORY = "/org.rmj.guanzongroup.ghostrider.epacss";

    /**
     * Subfolders of DCP must be named to each sRemCode of LR_DCP_Collection_Detail
     */
    public static final String DCP_IMG_DIR = "/DCP";

    /**
     * Subfolders of CreditApp must be named to each loan application sTransNox
     * Upon uploading laon details to server. Update sTransNox on table Credit_Online_Application
     * Also update folder name
     */
    public static final String LOAN_APP_IMG_DIR = "/CREDIT_APP";

    public static final String EXPORTED_FILES = "/Exported Files";
    public static final String DCP_FILE_DIR = "/DCP";
    public static final String LOAN_APP_FILE_DIR = "/CREDIT_APP";
}
