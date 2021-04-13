package org.rmj.guanzongroup.ghostrider.creditevaluator.Etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIBarangayRecord;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CICharacterTraits;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIDisbursementInfo;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIResidenceInfo;

public class CIConstants {
    public static final String APP_PUBLIC_FOLDER = "/org.rmj.guanzongroup.ghostrider.epacss";
    public static final String SUB_FOLDER_DCP = "/CIEValuation";
    public static Fragment[] CI_HOME_PAGES = {
            new Fragment_CIResidenceInfo(),
            new Fragment_CIBarangayRecord(),
            new Fragment_CIDisbursementInfo(),
            new Fragment_CICharacterTraits()
    };

}
