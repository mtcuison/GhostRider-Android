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

import java.util.*;
import java.util.stream.*;

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

        ExpandableListAppVersionAdapter listAdapter = new ExpandableListAppVersionAdapter(Activity_AppVersion.this, listUpdatedLogHeader, listUpdatedLogChild);
        explview_updatedlogs.setAdapter(listAdapter);
    }

    public void populateListData(String logKey, String[] logData, String dataPosition){
        if(logData.length > 0){
            for(int i = 0; i < logData.length; i++){

            }
        }
    }
}