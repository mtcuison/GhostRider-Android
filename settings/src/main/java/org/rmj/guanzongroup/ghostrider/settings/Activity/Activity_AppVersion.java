package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.rmj.guanzongroup.ghostrider.settings.Model.AppVersion_Model;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMAppVersion;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.adapter.ExpandableListAppVersionAdapter;
import org.rmj.guanzongroup.ghostrider.settings.adapter.ExpandableListHelpAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Activity_AppVersion extends AppCompatActivity {

    ExpandableListView explview_updatedlogs;
    Button btn_checkupdate;
    public static List<AppVersion_Model> listUpdatedLogHeader = new ArrayList<>();
    public static List<AppVersion_Model> listUpdatedLogChildModel = new ArrayList<>();
    public static HashMap<AppVersion_Model, List<AppVersion_Model>> listUpdatedLogChild = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);

        explview_updatedlogs = findViewById(R.id.exp_updatelogs);
        btn_checkupdate = findViewById(R.id.btn_checkupdate);

        List<String> listheaders = new ArrayList<>();
        listheaders.add("Employee Loan");
        listheaders.add("UI Design");

        HashMap<String, String> listchild = new HashMap<>();
        listchild.put("Employee Loan", "New application feature that allows employee to apply a loan for the company.");
        listchild.put("UI Design", "Improved System UI Design");

        VMAppVersion vmAppVersion = new VMAppVersion();
        vmAppVersion.setUpdatedLogDataHeader(listheaders, 0, true, true);

        for(String key: listchild.keySet()){
            int headerIndex = listheaders.indexOf(key);
            if(headerIndex >= 0){
                String listChildData = listchild.get(key);
                vmAppVersion.setUpdatedDataDetails(listChildData, 0, false, true, headerIndex);
            }
        }

        ExpandableListAppVersionAdapter listAdapter = new ExpandableListAppVersionAdapter(Activity_AppVersion.this, listUpdatedLogHeader, listUpdatedLogChild);
        explview_updatedlogs.setAdapter(listAdapter);
    }
}