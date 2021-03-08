package org.rmj.guanzongroup.ghostrider.epacss.adapter;

import android.content.Context;
import android.util.Log;

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
        MenuModel menuModel = new MenuModel("Home", R.drawable.ic_menu_home, true, false ); //Menu of Android Tutorial. No sub menus
        listDataHeader.add(menuModel);

        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        menuModel = new MenuModel("Approval Code", R.drawable.ic_approval_code, true, true); //Menu of Java Tutorials
        listDataHeader.add(menuModel);

        List<MenuModel> childModelsList = new ArrayList<>();

        MenuModel childModel = new MenuModel("Day-to-Day", 0, false, false );
        childModelsList.add(childModel);

        childModel = new MenuModel("Manual Log", 0,false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("By Reference", 0, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("By Name", 0,false, false);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Daily Collection Plan", R.drawable.ic_menu_dcp, true, true);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Collection List", 0, false, false );
        childModelsList.add(childModel);

        childModel = new MenuModel("Transaction Log", 0,false, false);
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Credit Application",  R.drawable.ic_menu_creditapp, true, true );
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Loan Application", 0, false, false );
        childModelsList.add(childModel);

        childModel = new MenuModel("Application List", 0,false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Document Scanner", 0, false, false);
        childModelsList.add(childModel);

//        childModel = new MenuModel("Camera", 0,false, false);
//        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Credit Online Evaluation",  R.drawable.ic_menu_credit_investigate,false, false);
        listDataHeader.add(menuModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("PET Manager",  R.drawable.ic_approval_biometric,true, true);
        listDataHeader.add(menuModel);

        childModel = new MenuModel("Leave Application", 0, false, false );
        childModelsList.add(childModel);

        childModel = new MenuModel("Business Trip", 0,false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Selfie Login", 0, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Health Checklist", 0,false, false);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Samsung Knox",  R.drawable.ic_menu_knox,true, true);
        listDataHeader.add(menuModel);
        Log.e("department", sessionManager.getPositionID());

        if (sessionManager.getPositionID().equalsIgnoreCase("036")){
            childModel = new MenuModel("Activate", 0, false, false );
            childModelsList.add(childModel);
        }

        if (sessionManager.getPositionID().equalsIgnoreCase("068")){

            childModel = new MenuModel("Unlock", 0,false, false);
            childModelsList.add(childModel);

            childModel = new MenuModel("Get Pin", 0, false, false);
            childModelsList.add(childModel);

            childModel = new MenuModel("Get Offline Pin", 0,false, false);
            childModelsList.add(childModel);
        }
        childModel = new MenuModel("Check Status", 0,false, false);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("AH Monitoring",  R.drawable.ic_menu_monitoring,true, true);
        listDataHeader.add(menuModel);
        childModel = new MenuModel("Branch Performance", 0, false, false );
        childModelsList.add(childModel);

        childModel = new MenuModel("Inventory", 0,false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Cash Count", 0, false, false);
        childModelsList.add(childModel);

        childModel = new MenuModel("Reimbursement", 0,false, false);
        childModelsList.add(childModel);
        if (menuModel.hasChildren) {
            listDataChild.put(menuModel, childModelsList);
        }

        menuModel = new MenuModel("Raffle Entry",  R.drawable.ic_approval_reference,false, false);
        listDataHeader.add(menuModel);
        if (!menuModel.hasChildren) {
            listDataChild.put(menuModel, null);
        }

    }
}
