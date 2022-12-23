package org.rmj.guanzongroup.ghostrider.epacss.ui;

import androidx.fragment.app.Fragment;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.dev.PositionCode;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_AreaManagerHome;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_BranchHeadHome;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_DeptHeadHome;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_GeneralManagerHome;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_RankFileHome;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_SuperVisorHome;

public class Dashboard {

    public Fragment InitializeDashboard(EEmployeeInfo args){
        String lsEmpRole = args.getPositnID();
        switch (args.getEmpLevID()){
            case "0":
                if(lsEmpRole.equalsIgnoreCase(PositionCode.Code_Collector)){
                    return new Fragment_RankFileHome();
                } else if(lsEmpRole.equalsIgnoreCase(PositionCode.Code_Collection_Staff)){
                    return new Fragment_RankFileHome();
                } else if(lsEmpRole.equalsIgnoreCase(PositionCode.Code_Junior_Programmer)){
                    return new Fragment_RankFileHome();
                } else {
                    return new Fragment_RankFileHome();
                }
            case "1":
                return new Fragment_SuperVisorHome();
            case "2":
                return new Fragment_DeptHeadHome();
            case "3":
                return new Fragment_BranchHeadHome();
            case "4":
                return new Fragment_AreaManagerHome();
            default:
                return new Fragment_GeneralManagerHome();
        }
    }
}
