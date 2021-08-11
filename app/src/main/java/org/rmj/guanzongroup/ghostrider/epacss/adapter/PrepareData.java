/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.adapter;

import android.content.Context;
import android.view.View;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.guanzongroup.ghostrider.epacss.R;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main.listDataChild;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main.listDataHeader;

public class PrepareData {
    SessionManager sessionManager;
    public void prepareMenuData(Context mContext) {
        listDataHeader.clear();
        listDataChild.clear();
        sessionManager = new SessionManager(mContext);
        MenuModel menuModel = new MenuModel("Home", R.drawable.ic_menu_home, true, false , VISIBLE); //Menu of Android Tutorial. No sub menus
        listDataHeader.add(menuModel);

        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        //TODO : Change View value to VISIBLE if Approval code is ready for implementation.
        menuModel = new MenuModel("Approval Code", R.drawable.ic_approval_code, true, true, GONE); //Menu of Java Tutorials
        listDataHeader.add(menuModel);

        List<MenuModel> childModelsList = new ArrayList<>();

        MenuModel childModel = new MenuModel("Day-to-Day", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Manual Log", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("By Reference", 0, false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("By Name", 0,false, false, VISIBLE);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        if(sessionManager.getDeptID().equalsIgnoreCase(DeptCode.SALES) || sessionManager.getDeptID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)) {
            menuModel = new MenuModel("Daily Collection Plan", R.drawable.ic_menu_dcp, true, true, VISIBLE);
        } else {
            menuModel = new MenuModel("Daily Collection Plan", R.drawable.ic_menu_dcp, true, true, GONE);
        }
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Collection List", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Transaction Log", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if Credit App is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Credit Application", R.drawable.ic_menu_creditapp, true, true, GONE);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Loan Application", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("User Application List", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Branch Application List", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Document Scanner", 0, false, false, VISIBLE);
        childModelsList.add(childModel);

//        childModel = new MenuModel("Camera", 0,false, false);
//        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if CI Evaluator is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("CI Evaluation",  R.drawable.ic_menu_credit_investigate,true, true, GONE);
        listDataHeader.add(menuModel);
        childModel = new MenuModel("CI Evaluation List", 0, false, false , VISIBLE);
        childModelsList.add(childModel);
        childModel = new MenuModel("CI Evaluation History", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if PET Manager is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("PET Manager",  R.drawable.ic_approval_biometric,true, true, VISIBLE);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Leave Application", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Business Trip", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Selfie Login", 0, false, false, VISIBLE);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        if(sessionManager.getDeptID().equalsIgnoreCase(DeptCode.CREDIT_SUPPORT_SERVICES) ||
        sessionManager.getDeptID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)) {
            menuModel = new MenuModel("Samsung Knox", R.drawable.ic_menu_knox, true, true, VISIBLE);
        } else {
            menuModel = new MenuModel("Samsung Knox", R.drawable.ic_menu_knox, true, true, GONE);
        }
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Activate", 0, false, false , View.GONE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Unlock", 0,false, false, View.GONE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Get Pin", 0, false, false, View.GONE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Get Offline Pin", 0,false, false, View.GONE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Check Status", 0,false, false, GONE);
        childModelsList.add(childModel);

        if (sessionManager.getDeptID().equalsIgnoreCase(DeptCode.MOBILE_PHONE)){
            childModelsList.get(0).setVisibility(VISIBLE);
        } else if (sessionManager.getDeptID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)){
            childModelsList.get(1).setVisibility(VISIBLE);
            childModelsList.get(2).setVisibility(VISIBLE);
            childModelsList.get(3).setVisibility(VISIBLE);
        } else if(sessionManager.getDeptID().equalsIgnoreCase(DeptCode.CREDIT_SUPPORT_SERVICES)){
            childModelsList.get(1).setVisibility(VISIBLE);
            childModelsList.get(2).setVisibility(VISIBLE);
            childModelsList.get(3).setVisibility(VISIBLE);
        }

        childModelsList.get(4).setVisibility(VISIBLE);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if AreaHead Monitoring is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("AH Monitoring",  R.drawable.ic_menu_monitoring,true, true, GONE);
        listDataHeader.add(menuModel);
        childModel = new MenuModel("Branch Performance", 0, false, false , VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Inventory", 0,false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Cash Count", 0, false, false, VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Reimbursement", 0,false, false, VISIBLE);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Raffle Entry",  R.drawable.ic_approval_reference,false, false, GONE);
        listDataHeader.add(menuModel);
        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        if(sessionManager.getDeptID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)) {
            menuModel = new MenuModel("Scan Digital GCard (MIS)", R.drawable.ic_scan_qr_code, false, false, VISIBLE);
        } else {
            menuModel = new MenuModel("Scan Digital GCard (MIS)", R.drawable.ic_scan_qr_code, false, false, GONE);
        }
        listDataHeader.add(menuModel);
        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        if(sessionManager.getDeptID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)) {
            menuModel = new MenuModel("Check Data (MIS)", R.drawable.ic_settings_localdata, false, false, VISIBLE);
        } else {
            menuModel = new MenuModel("Check Data (MIS)", R.drawable.ic_settings_localdata, false, false, GONE);
        }
        listDataHeader.add(menuModel);
        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        menuModel = new MenuModel("Health Checklist", R.drawable.ic_checklist, false, false, VISIBLE);
        listDataHeader.add(menuModel);
        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }
    }
}
