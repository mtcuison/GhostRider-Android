/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIBarangayRecord;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CICharacterTraits;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIDisbursementInfo;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIResidenceInfo;

public class CIConstants {
    public static final String APP_PUBLIC_FOLDER = "/org.rmj.guanzongroup.ghostrider.epacss";
    public static final String SUB_FOLDER_DCP = "/CIEValuation";
    public static final String EVALUATION_HISTORY = "Credit Evaluation History";
    public static Fragment[] CI_HOME_PAGES = {
            new Fragment_CIResidenceInfo(),
            new Fragment_CIDisbursementInfo(),
            new Fragment_CIBarangayRecord(),
            new Fragment_CICharacterTraits()
    };
    public static String[] HOUSEHOLDS ={
            "Living with Family(Spouse, Children)",
            "Living with Family(Father, Mother, Sibling)",
            "Living with Relatives"};

    public static String[] HOUSE_TYPE = {
            "Concrete",
            "Concrete and Wood",
            "Wood"};
    public static String[] HOUSE_OWNERSHIP = {
            "Owned",
            "Rent",
            "Care-Taker"};
    public static String[] GARAGEXX = {
            "No",
            "Yes"};
}
