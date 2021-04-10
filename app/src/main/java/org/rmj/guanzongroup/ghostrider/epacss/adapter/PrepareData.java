package org.rmj.guanzongroup.ghostrider.epacss.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;

import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.guanzongroup.ghostrider.epacss.R;

import java.util.ArrayList;
import java.util.List;

import static org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity.listDataChild;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity.listDataHeader;

public class PrepareData {
    SessionManager sessionManager;
    public void prepareMenuData(Context mContext) {
        listDataHeader.clear();
        listDataChild.clear();
        sessionManager = new SessionManager(mContext);
        MenuModel menuModel = new MenuModel("Home", R.drawable.ic_menu_home, true, false , View.VISIBLE); //Menu of Android Tutorial. No sub menus
        listDataHeader.add(menuModel);

        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        //TODO : Change View value to VISIBLE if Approval code is ready for implementation.
        menuModel = new MenuModel("Approval Code", R.drawable.ic_approval_code, true, true, View.GONE); //Menu of Java Tutorials
        listDataHeader.add(menuModel);

        List<MenuModel> childModelsList = new ArrayList<>();

        MenuModel childModel = new MenuModel("Day-to-Day", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Manual Log", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("By Reference", 0, false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("By Name", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Daily Collection Plan", R.drawable.ic_menu_dcp, true, true, View.VISIBLE);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Collection List", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Transaction Log", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if Credit App is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Credit Application",  R.drawable.ic_menu_creditapp, true, true , View.VISIBLE);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Loan Application", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("User Application List", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Branch Application List", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Document Scanner", 0, false, false, View.VISIBLE);
        childModelsList.add(childModel);

//        childModel = new MenuModel("Camera", 0,false, false);
//        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if CI Evaluator is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("CI Evaluation",  R.drawable.ic_menu_credit_investigate,true, true, View.VISIBLE);
        listDataHeader.add(menuModel);
        childModel = new MenuModel("CI Evaluation List", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);
        childModel = new MenuModel("CI Evaluation History", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if PET Manager is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("PET Manager",  R.drawable.ic_approval_biometric,true, true, View.GONE);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Leave Application", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Business Trip", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Selfie Login", 0, false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Health Checklist", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Samsung Knox",  R.drawable.ic_menu_knox,true, true, View.VISIBLE);
        listDataHeader.add(menuModel);
        Log.e("department", sessionManager.getPositionID());

        if (sessionManager.getPositionID().equalsIgnoreCase(DeptCode.MOBILE_PHONE)){
            childModel = new MenuModel("Activate", 0, false, false , View.VISIBLE);
            childModelsList.add(childModel);
        } else {
            childModel = new MenuModel("Activate", 0, false, false , View.GONE);
            childModelsList.add(childModel);
        }

        if (sessionManager.getPositionID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)){
            childModel = new MenuModel("Unlock", 0,false, false, View.VISIBLE);
            childModelsList.add(childModel);

            childModel = new MenuModel("Get Pin", 0, false, false, View.VISIBLE);
            childModelsList.add(childModel);

            childModel = new MenuModel("Get Offline Pin", 0,false, false, View.VISIBLE);
            childModelsList.add(childModel);
        } else {
            childModel = new MenuModel("Unlock", 0,false, false, View.GONE);
            childModelsList.add(childModel);

            childModel = new MenuModel("Get Pin", 0, false, false, View.GONE);
            childModelsList.add(childModel);

            childModel = new MenuModel("Get Offline Pin", 0,false, false, View.GONE);
            childModelsList.add(childModel);
        }

        childModel = new MenuModel("Check Status", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        //TODO: Change the value of View to VISIBLE if AreaHead Monitoring is ready for implementation...
        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("AH Monitoring",  R.drawable.ic_menu_monitoring,true, true, View.GONE);
        listDataHeader.add(menuModel);
        childModel = new MenuModel("Branch Performance", 0, false, false , View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Inventory", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Cash Count", 0, false, false, View.VISIBLE);
        childModelsList.add(childModel);

        childModel = new MenuModel("Reimbursement", 0,false, false, View.VISIBLE);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Raffle Entry",  R.drawable.ic_approval_reference,false, false, View.GONE);
        listDataHeader.add(menuModel);
        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

    }
}
