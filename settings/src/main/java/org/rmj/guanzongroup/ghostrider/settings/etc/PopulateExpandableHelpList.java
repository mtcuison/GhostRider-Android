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

package org.rmj.guanzongroup.ghostrider.settings.etc;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_Help;
import org.rmj.guanzongroup.ghostrider.settings.Model.SettingsModel;
import org.rmj.guanzongroup.ghostrider.settings.adapter.ExpandableListHelpAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList.expHelpView;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList.helpAdapter;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList.listHelpDataChild;
import static org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList.listHelpDataHeader;

public class PopulateExpandableHelpList {
    private static final String TAG = PopulateExpandableHelpList.class.getSimpleName();

    public void populate(Context context) {
        helpAdapter = new ExpandableListHelpAdapter(context, listHelpDataHeader, listHelpDataChild);
        expHelpView.setAdapter(helpAdapter);
        expHelpView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

        expHelpView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Intent intent;
                if(groupPosition == 0) {
                    intent = new Intent(context, Activity_Help.class);
                    intent.putExtra("help", AppConstants.INTENT_SELFIE_LOGIN);
                    context.startActivity(intent);
                }
                return false;
            }
        });

        expHelpView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                int childCount = listHelpDataChild.get(listHelpDataHeader.get(groupPosition)).size();

                Intent intent;
                switch (groupPosition)
                {
                    case 0: 
                        intent = new Intent(context, Activity_Help.class);
                        intent.putExtra("help", AppConstants.INTENT_SELFIE_LOGIN);
                        context.startActivity(intent);
                        break;
                    case 1:
                        switch(childPosition)
                        {
                            case 0 :
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_DOWNLOAD_DCP);
                                context.startActivity(intent);
                                break;
                            case 1:
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_ADD_COLLECTION_DCP);
                                context.startActivity(intent);
                                break;
                            case 2:
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_TRANSACTION_DCP);
                                context.startActivity(intent);
                                break;
                            case 3:
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_DCP_POST_COLLECTION);
                                context.startActivity(intent);
                                break;
                            case 4:
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_DCP_REMITTANCE);
                                context.startActivity(intent);
                                break;
                            case 5:
                                intent = new Intent(context, Activity_Help.class);
                                intent.putExtra("help", AppConstants.INTENT_DCP_LOG);
                                context.startActivity(intent);
                                break;
                        }
                        break;
                }
                return false;
            }
        });
    }

//    /*Added by mike
//    * listener for handling Home Button on navigation menu*/
//    public interface OnHomeButtonClickListener{
//        void OnHomeButtonClick();
//    }
}
