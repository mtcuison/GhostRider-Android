package org.rmj.guanzongroup.ghostrider.epacss.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.rmj.guanzongroup.ghostrider.griderscanner.MainScanner;
import org.rmj.guanzongroup.ghostrider.imgcapture.DCPPhotoCapture;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalCode;
import org.rmj.guanzongroup.ghostrider.approvalcode.Activity.Activity_ApprovalSelection;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionLog;
import org.rmj.guanzongroup.ghostrider.griderscanner.GriderScanner;
import org.rmj.guanzongroup.ghostrider.samsungknox.Activity_Knox;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_ApplicationHistory;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_IntroductoryQuestion;
import org.rmj.guanzongroup.promotions.Activity_RaffleEntry;

import static org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity.expListView;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity.listAdapter;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity.listDataChild;
import static org.rmj.guanzongroup.ghostrider.epacss.Activity.MainActivity.listDataHeader;

public class PopulateExpandableList {
    private static final String TAG = PopulateExpandableList.class.getSimpleName();

    /*Edited by Mike*/
    public void populate(Context context, OnHomeButtonClickListener listener) {
        listAdapter = new ExpandableListDrawerAdapter(context, listDataHeader, listDataChild);
        expListView.setAdapter(listAdapter);
        expListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
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
                if(groupPosition == 8){
                    context.startActivity(new Intent(context, Activity_RaffleEntry.class));
                }
                return false;
            }
        });

        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
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
                                intent = new Intent(context, Activity_CollectionList.class);
                                intent.putExtra("syscode", "2");
                                context.startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(context, Activity_CollectionLog.class);
                                intent.putExtra("syscode", "2");
                                context.startActivity(intent);
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
                                Intent scanner = new Intent(parent.getContext(), MainScanner.class);
                                context.startActivity(scanner);
                                break;
                            case 3:
                                Intent photoCapture= new Intent(parent.getContext(), DCPPhotoCapture.class);
                                context.startActivity(photoCapture);
                                break;
                        }
                        break;
                    case 4: break;
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

                                break;
                            case 3:

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
                }
                return false;
            }
        });
    }

    /*Added by mike
    * listener for handling Home Button on navigation menu*/
    public interface OnHomeButtonClickListener{
        void OnHomeButtonClick();
    }
}
