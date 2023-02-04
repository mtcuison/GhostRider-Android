package org.rmj.guanzongroup.ghostrider.settings.Activity;

import static android.view.WindowManager.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.internal.ViewUtils;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.Model.RecycleViewHolder_Model;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMAppVersion;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.adapter.RecyclerViewAppVersionAdapter;

import java.util.ArrayList;
import java.util.List;

public class Activity_AppVersion extends AppCompatActivity {
    TextView update_version;
    TextView lbl_aboutupdate;
    TextView about_update;
    TextView lbl_updatedLog;
    RecyclerView recyclerView;
    Button btn_checkupdate;
    RecyclerViewAppVersionAdapter versionAdapter;
    private VMAppVersion mViewModel;
    private LoadDialog poload;
    private MessageBox pomessage;
    int versionList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);

        //declare View Model for checking/downloading app version updates
        mViewModel = new ViewModelProvider(Activity_AppVersion.this).get(VMAppVersion.class);

        //instantiate dialog and meesage box to appear upon checking update
        poload = new LoadDialog(Activity_AppVersion.this);
        pomessage = new MessageBox(Activity_AppVersion.this);

        //declare ui from layout
        lbl_aboutupdate = findViewById(R.id.lbl_aboutupdate);
        about_update = findViewById(R.id.about_update);
        lbl_updatedLog = findViewById(R.id.lbl_updatedLog);

        btn_checkupdate = findViewById(R.id.btn_checkupdate);
        recyclerView = findViewById(R.id.rec_updatelogs);

        update_version = findViewById(R.id.update_version);

        //display first what to show upon start of activity
        setonDisplay();
        //add the event method for button
        btnCheckUpdate();
    }
    public void setonDisplay(){
        versionList = getAppVersion(false);
        if(versionList <= 0){
            lbl_aboutupdate.setVisibility(View.INVISIBLE);
            about_update.setVisibility(View.INVISIBLE);
            lbl_updatedLog.setVisibility(View.INVISIBLE);
            update_version.setVisibility(View.VISIBLE);
        }else {
            lbl_aboutupdate.setVisibility(View.VISIBLE);
            about_update.setVisibility(View.VISIBLE);
            lbl_updatedLog.setVisibility(View.VISIBLE);
            update_version.setVisibility(View.INVISIBLE);
        }
    }
    public void btnCheckUpdate(){
        btn_checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if successfully retrieved versions, continue and replace text button
                if(getAppVersion(true) > 0){
                    //set button text
                    if (btn_checkupdate.getHint().toString().trim().equals("Check for Updates")){
                        btn_checkupdate.setHint("Download Updates");
                    } else if (btn_checkupdate.getHint().toString().trim().equals("Download Updates")) {
                        btn_checkupdate.setHint("Check for Updates");
                    }
                }
            }
        });
    }
    public int getAppVersion(Boolean showDialog){
        mViewModel.getVersionList(new VMAppVersion.onDownloadVersionList() {
            @Override
            public void onDownload() {
                versionList = 0;
                //display a dialog based on text from button
                if (btn_checkupdate.getHint().toString().trim().equals("Check for Updates") && showDialog == true){
                    //show dialog for checking update
                    poload.initDialog("GCircle App Version", "Checking Updates", false);
                    poload.show();
                }else if (btn_checkupdate.getHint().toString().trim().equals("Download Updates") && showDialog == true) {
                    //create alert dialog obj builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AppVersion.this);
                    //create layout view for dialog builder, using the main activity(Context) and xml file(layout view)
                    View view = LayoutInflater.from(Activity_AppVersion.this).inflate(R.layout.activity_appversion_download_update,null);

                    //attach the layout view created to builder
                    builder.setView(view);
                    //create new obj alert dialog (parent container) and attach to the obj builder created
                    AlertDialog alertDialog = builder.create();
                    //show dialog
                    alertDialog.show();
                }
            }
            @Override
            public void onSuccess(List<VersionInfo> list) {
                poload.dismiss();
                versionList = list.size();

                //if return list of version updates are available, continue
                if(versionList > 0){

                    //attach list of version updates to the Adapter and ListView Object
                    versionAdapter = new RecyclerViewAppVersionAdapter(list, Activity_AppVersion.this);
                    versionAdapter.resource = R.layout.update_version_logs;

                    //validate recycler view obj if visible before attaching the adapter
                    if(recyclerView.getVisibility() == View.VISIBLE){
                        recyclerView.setAdapter(versionAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Activity_AppVersion.this));
                    }
                }
            }
            @Override
            public void onFailed(String message) {
                versionList = 0;
                pomessage.initDialog();

                if (btn_checkupdate.getHint().toString().trim().equals("Check for Updates")){
                    pomessage.setTitle("Failed to check Updates");
                } else if (btn_checkupdate.getHint().toString().trim().equals("Download Updates")) {
                    pomessage.setTitle("Failed to download Updates");
                }
                pomessage.setMessage(message);
                pomessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                    @Override
                    public void OnButtonClick(View view, AlertDialog dialog) {
                        dialog.dismiss();
                    }
                });
                pomessage.show();
            }
        });
        return versionList;
    }
}