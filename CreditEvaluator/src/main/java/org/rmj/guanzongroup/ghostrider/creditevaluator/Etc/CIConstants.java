package org.rmj.guanzongroup.ghostrider.creditevaluator.Etc;

import androidx.fragment.app.Fragment;

import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIBarangayRecord;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CICharacterTraits;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIDisbursementInfo;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments.Fragment_CIResidenceInfo;

public class CIConstants {

    public static Fragment[] CI_HOME_PAGES = {
            new Fragment_CIResidenceInfo(),
            new Fragment_CIDisbursementInfo(),
            new Fragment_CIBarangayRecord(),
            new Fragment_CICharacterTraits()
    };

}
