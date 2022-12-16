package org.rmj.guanzongroup.ghostrider.epacss.ui;

import androidx.fragment.app.Fragment;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_AreaManager;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_BranchHead;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_DeptHead;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_GeneralManager;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_RankFile;
import org.rmj.guanzongroup.ghostrider.epacss.ui.Dashboards.Fragment_SuperVisor;

public class Dashboard {

    public Fragment InitializeDashboard(EEmployeeInfo args){
        switch (args.getEmpLevID()){
            case "0":
                return new Fragment_RankFile();
            case "1":
                return new Fragment_SuperVisor();
            case "2":
                return new Fragment_DeptHead();
            case "3":
                return new Fragment_BranchHead();
            case "4":
                return new Fragment_AreaManager();
            default:
                return new Fragment_GeneralManager();
        }
    }
}
