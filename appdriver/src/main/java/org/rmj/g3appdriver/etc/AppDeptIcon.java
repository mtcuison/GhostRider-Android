package org.rmj.g3appdriver.etc;

import static org.rmj.g3appdriver.GCircle.Etc.DeptCode.*;

import org.rmj.g3appdriver.R;


public class AppDeptIcon {

    public static int getIcon(String deptID){
        if(deptID.equalsIgnoreCase(AFTER_SALES_MANAGEMENT))
            return R.drawable.ic_aftersales;
        else if(deptID.equalsIgnoreCase(FINANCE_MANAGEMENT))
            return R.drawable.ic_finance;
        else if(deptID.equalsIgnoreCase(CREDIT_SUPPORT_SERVICES))
            return R.drawable.ic_css;
        else if(deptID.equalsIgnoreCase(DUTY_AND_REQUISITES_SERVICES))
            return R.drawable.ic_drs;
        else if(deptID.equalsIgnoreCase(ENGINEERING_SERVICES))
            return R.drawable.ic_engineering;
        else if(deptID.equalsIgnoreCase(FACILITY_AND_SECURITY_MANAGEMENT))
            return R.drawable.ic_fsm;
        else if(deptID.equalsIgnoreCase(HUMAN_CAPITAL_MANAGEMENT))
            return R.drawable.ic_hcm;
        else if(deptID.equalsIgnoreCase(MARKETING_AND_PROMOTIONS))
            return R.drawable.ic_marketing;
        else if(deptID.equalsIgnoreCase(MANAGEMENT_INFORMATION_SYSTEM))
            return R.drawable.ic_mis;
        else if(deptID.equalsIgnoreCase(PROCUREMENT_SERVICES))
            return R.drawable.ic_proc_services;
        else if(deptID.equalsIgnoreCase(SUPPLY_CHAIN_MANAGEMENT))
            return R.drawable.ic_supplychain;
        else if(deptID.equalsIgnoreCase(TELEMARKETING_MANAGEMENT))
            return R.drawable.ic_telemarket;
        else
        return R.drawable.ic_guanzon_logo;
    }
}
