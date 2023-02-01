package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.nfc.Tag;
import android.os.Build;
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
    VMAppVersion vmAppVersion = new VMAppVersion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);

        explview_updatedlogs = findViewById(R.id.exp_updatelogs);
        btn_checkupdate = findViewById(R.id.btn_checkupdate);

        String logKeyHeader = "0";
        String[] logDataHeader = {"Employee Loan", "Update Notification", "System Security"};

        vmAppVersion.imgicon = 0;
        vmAppVersion.isGroup = true;
        vmAppVersion.hasChild = true;
        populateListData(logKeyHeader, logDataHeader, "asHead");

        String logKeyDetailEL = "Employee Loan";
        String[] logDataDetailEL = {"Allow user to apply Loan Application", "Loan Cars", "Loan Motorcycle"};
        vmAppVersion.hasChild = true;
        populateListData(logKeyDetailEL, logDataDetailEL, "asChild");

        /*String logKeyDetailUID = "UI Design";
        String[] logDataDetailUID = {"Improve System Performance", "New System Patches"};
        vmAppVersion.hasChild = true;
        populateListData(logKeyDetailUID, logDataDetailUID);

        String logKeyDetailSP = "Security Patches";
        String[] logDataDetailSP = {"Improve Security Patches"};
        vmAppVersion.hasChild = true;
        populateListData(logKeyDetailSP, logDataDetailSP);*/

        ExpandableListAppVersionAdapter listAdapter = new ExpandableListAppVersionAdapter(Activity_AppVersion.this, listUpdatedLogHeader, listUpdatedLogChild);
        explview_updatedlogs.setAdapter(listAdapter);
    }

    public void populateListData(String logKey, String[] logData, String dataPosition){
        if(logData.length > 0){
            HashMap<String, String[]>  hashlistData = new HashMap<>();
            hashlistData.put(logKey, logData);

            for(String key: hashlistData.keySet()){
                for(int i = 0; i < hashlistData.get(key).length; i++){
                    vmAppVersion.setUpdatedLogData(hashlistData.get(key)[i], key, dataPosition);
                }
            }
        }
    }
}