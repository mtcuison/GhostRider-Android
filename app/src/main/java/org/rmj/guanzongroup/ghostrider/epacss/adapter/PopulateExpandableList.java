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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import org.rmj.g3appdriver.etc.AppAssistantConfig;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Browser;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCountLog;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_EvaluationHistory;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Activity.Activity_EvaluationList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogCollection;
import org.rmj.guanzongroup.ghostrider.griderscanner.MainScanner;
import org.rmj.guanzongroup.ghostrider.imgcapture.DCPPhotoCapture;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalCode;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalSelection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.samsungknox.Activity_Knox;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_DigitalGcard;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Help;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_ApplicationHistory;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_BranchApplications;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_IntroductoryQuestion;

import static org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main.expListView;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main.listAdapter;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main.listDataChild;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.Activity_Main.listDataHeader;

public class PopulateExpandableList {
    private static final String TAG = PopulateExpandableList.class.getSimpleName();

    /*Edited by Mike*/
    public void populate(Context context, Activity activity, OnHomeButtonClickListener listener) {
        listAdapter = new ExpandableListDrawerAdapter(context, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnItemClickListener((parent, view, position, id) -> {
        });

        expListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            if (listDataHeader.get(groupPosition).isGroup) {
                if (!listDataHeader.get(groupPosition).hasChildren) {
                    if(groupPosition == 0){
                        if(listener != null){
                            listener.OnHomeButtonClick();
                        }
                    }
//                        if(groupPosition == 2){
//                            context.startActivity(new Intent(context, Activity_CollectionList.class));
//                        }
                } else {
//                        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
//                            if (i != groupPosition) {
//                                expListView.collapseGroup(i);
//                            }
//                        }

                }
            }
            if(groupPosition == 9){
                context.startActivity(new Intent(context, Activity_DigitalGcard.class));
            }

            if(groupPosition == 11){
                Intent intent = new Intent(parent.getContext(), Activity_Browser.class);
                intent.putExtra("url_link", "https://www.google.com/webhp?hl=ceb&sa=X&ved=0ahUKEwj0ne24tI7xAhV8yIsBHbQ7Az0QPAgI");
                context.startActivity(intent);
            }
            return false;
        });

        expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            int childCount = listDataChild.get(listDataHeader.get(groupPosition)).size();

            Intent intent;
            switch (groupPosition)
            {
                case 0:
                    break;
                case 1:
                    switch(childPosition)
                    {
                        case 0 :
                            Toast.makeText(context, "Under develop", Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            intent = new Intent(context, Activity_ApprovalCode.class);
                            intent.putExtra("sysCode", "2");
                            context.startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(context, Activity_ApprovalSelection.class);
                            intent.putExtra("syscode", "1");
                            context.startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(context, Activity_ApprovalSelection.class);
                            intent.putExtra("syscode", "2");
                            context.startActivity(intent);
                            break;
                    }
                    break;
                case 2:
                    switch (childPosition){
                        case 0:
                            if (!AppAssistantConfig.getInstance(context).getHELP_DCP_NOTICE()){
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_DCP_LIST);
                                activity.startActivityForResult(intent, AppConstants.INTENT_DCP_LIST);
                            }else{
                                intent = new Intent(context, Activity_CollectionList.class);
                                intent.putExtra("syscode", "2");
                                context.startActivity(intent);
                            }
                            break;
                        case 1:
                            if (!AppAssistantConfig.getInstance(context).getASSIST_DCP_LOG()){
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_DCP_LOG);
                                activity.startActivityForResult(intent, AppConstants.INTENT_DCP_LOG);
                            }else{
                                intent = new Intent(context, Activity_LogCollection.class);
                                intent.putExtra("syscode", "2");
                                context.startActivity(intent);
                            }
                            break;
                    }
                    break;
                case 3:
                    switch(childPosition)
                    {
                        case 0 :
                            intent = new Intent(context, Activity_IntroductoryQuestion.class);
                            context.startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(context, Activity_ApplicationHistory.class);
                            intent.putExtra("app", AppConstants.INTENT_OB_APPLICATION);
                            context.startActivity(intent);
                            break;
                        case 2:
                            Intent branch = new Intent(parent.getContext(), Activity_BranchApplications.class);
                            context.startActivity(branch);
                            break;
                        case 3:
                            Intent scanner = new Intent(parent.getContext(), MainScanner.class);
                            context.startActivity(scanner);
                            break;
                        case 4:
                            Intent photoCapture= new Intent(parent.getContext(), DCPPhotoCapture.class);
                            context.startActivity(photoCapture);
                            break;
                    }
                    break;
                case 4:
                    switch(childPosition)
                    {
                        case 0 :
                            Intent ciIntent = new Intent(parent.getContext(), Activity_EvaluationList.class);
                            context.startActivity(ciIntent);
                            break;
                        case 1 :
                            Intent intent1 = new Intent(parent.getContext(), Activity_EvaluationHistory.class);
                            context.startActivity(intent1);
                            break;
                    }
                    break;
                case 5:
                    switch(childPosition)
                    {
                        case 0 :
                            intent = new Intent(parent.getContext(), Activity_Application.class);
                            intent.putExtra("app", AppConstants.INTENT_LEAVE_APPLICATION);
                            context.startActivity(intent);
                            break;
                        case 1:
                            intent = new Intent(parent.getContext(), Activity_Application.class);
                            intent.putExtra("app", AppConstants.INTENT_OB_APPLICATION);
                            context.startActivity(intent);
                            break;
                        case 2:
                            intent = new Intent(parent.getContext(), Activity_Application.class);
                            intent.putExtra("app", AppConstants.INTENT_LEAVE_OB_APPLICATION);
                            context.startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(parent.getContext(), Activity_Application.class);
                            intent.putExtra("app", AppConstants.INTENT_SELFIE_LOGIN);
                            context.startActivity(intent);
                            break;
                    }
                    break;
                case 6:
                    for(int i = 0; i< childCount; i++){
                        if (i == childPosition){
                            intent = new Intent(parent.getContext(), Activity_Knox.class);
                            intent.putExtra("knox", i+1);
                            context.startActivity(intent);
                            break;
                        }
                    }
                    break;
                case 7:
                    switch(childPosition)
                    {
                        case 0 :
                        case 1:
                            break;
                        case 2:
                            intent = new Intent(parent.getContext(), Activity_CashCounter.class);
                            context.startActivity(intent);
                            break;
                        case 3:
                            intent = new Intent(parent.getContext(), Activity_CashCountLog.class);
                            context.startActivity(intent);
                            break;
                    }
            }
            return false;
        });
    }

    /*Added by mike
    * listener for handling Home Button on navigation menu*/
    public interface OnHomeButtonClickListener{
        void OnHomeButtonClick();
    }
}
