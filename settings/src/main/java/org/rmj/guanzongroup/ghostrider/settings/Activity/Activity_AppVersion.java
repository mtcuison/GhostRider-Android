package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMAppVersion;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.adapter.RecyclerViewAppVersionAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Activity_AppVersion extends AppCompatActivity {
    TextView build_version;
    TextView date_build;
    TextView lbl_aboutupdate;
    TextView about_update;
    TextView lbl_updatedLog;
    RecyclerView recyclerView;
    Button btn_checkupdate;
    RecyclerViewAppVersionAdapter versionAdapter;
    private VMAppVersion mViewModel;
    private LoadDialog poload;
    private MessageBox pomessage;
    List<VersionInfo> versionInfoList;
    AlertDialog alertDialog;
    String msgdialogTitle;
    Boolean btnClicked;
    Boolean isSuccess;
    String msgDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_version);

        //declare View Model for checking/downloading app version updates
        mViewModel = new ViewModelProvider(Activity_AppVersion.this).get(VMAppVersion.class);

        versionInfoList = new ArrayList<>();

        //instantiate dialog and message box to appear upon checking update
        poload = new LoadDialog(Activity_AppVersion.this);
        pomessage = new MessageBox(Activity_AppVersion.this);

        //declare ui from layout
        build_version = findViewById(R.id.build_version);
        date_build = findViewById(R.id.date_build);
        lbl_aboutupdate = findViewById(R.id.lbl_aboutupdate);
        about_update = findViewById(R.id.about_update);
        lbl_updatedLog = findViewById(R.id.lbl_updatedLog);

        btn_checkupdate = findViewById(R.id.btn_checkupdate);
        recyclerView = findViewById(R.id.rec_updatelogs);

        btnClicked = false;
        isSuccess = false;

        //create alert dialog obj builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AppVersion.this);
        //create layout view for dialog builder, using the main activity(Context) and xml file(layout view)
        View view = LayoutInflater.from(Activity_AppVersion.this).inflate(R.layout.activity_appversion_download_update,null);

        //attach the layout view created to builder
        builder.setView(view);
        //create new obj alert dialog (parent container) and attach to the obj builder created
        alertDialog = builder.create();
        //set background color for the dialog
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize dialog for displaying message
        pomessage.initDialog();

       //set by default, the current build version of  app
        setBuild_version();
       //call method to get the list of versions
        getAppVersion();
       //call method to show what to display
        setonDisplay();
       //call method for button listener
        btnCheckUpdate();
    }
    public void setonDisplay(){
        //show that to display if, list of versions has retrieved more than 0
        if(versionInfoList.size() <= 0){
            lbl_aboutupdate.setVisibility(View.INVISIBLE);
            about_update.setVisibility(View.INVISIBLE);
            lbl_updatedLog.setVisibility(View.INVISIBLE);
        }else {
            lbl_aboutupdate.setVisibility(View.VISIBLE);
            about_update.setVisibility(View.VISIBLE);
            lbl_updatedLog.setVisibility(View.VISIBLE);
        }
    }
    public void showDialog(){
        //get button text
        String btnText = btn_checkupdate.getHint().toString();
        String message = btnText;
        //show what dialog to show
        if(btnText.equals("Check for Updates")){
            message = "Checking Updates";
            if(isSuccess == true){
                msgdialogTitle = "Check Updates";
                msgDialog = "Check Updates Successful";
            }else{
                msgdialogTitle = "Failed to check updates";
            }
        }else if(btnText.equals("Download Updates")){
            message = "Downloading Updates";
            if(isSuccess == true){
                msgdialogTitle = "Download Updates";
                msgDialog = "Download Successful";
            }else{
                msgdialogTitle = "Failed to download updates";
            }
            alertDialog.show();
        }
        //initialize dialog for checking update
        poload.initDialog("GCircle App Version", message, false);
        poload.show();
    }
    public void btnCheckUpdate(){
        btn_checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set listener for this button, when clicked
                btnClicked = true;
                //call method to get the list of versions
                getAppVersion();
                //get button text
                String btnText = btn_checkupdate.getHint().toString();
                if(btnText.equals("Check for Updates")){
                    //set list to Adapter
                    setListToAdapter(Activity_AppVersion.this, R.layout.update_version_logs);
                }else if(btnText.equals("Download Updates")){
                    //set list to Adapter
                    setListToAdapter(alertDialog.getContext(), R.layout.activity_appversion_download_update);
                }
            }
        });
    }
    public void getAppVersion(){
        mViewModel.getVersionList(new VMAppVersion.onDownloadVersionList() {
            @Override
            public void onDownload() {
                versionInfoList.clear();
                if(btnClicked == true){
                    //show dialog
                    showDialog();
                }
            }
            @Override
            public void onSuccess(List<VersionInfo> list) {
                //set the list value
                versionInfoList= list;
                //store result if retrieve, success or failed
                isSuccess = true;

                //show error message, if buttton check update is clicked
                if(btnClicked == true){
                    //set dialog message and button
                    pomessage.setTitle(msgdialogTitle);
                    pomessage.setMessage(msgDialog);
                    pomessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            //close dialogs upon clicking
                            dialog.dismiss();
                            //show message
                            pomessage.show();
                        }
                    });
                }
            }
            @Override
            public void onFailed(String message) {
                //clear the list value and close dialogs
                versionInfoList.clear();
                isSuccess = false;

                //show error message, if buttton check update is clicked
                if(btnClicked == true){
                    //set dialog message and button
                    pomessage.setTitle(msgdialogTitle);
                    pomessage.setMessage(message);
                    pomessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            //close dialogs upon clicking
                            dialog.dismiss();
                            poload.dismiss();
                            alertDialog.dismiss();

                            //show message
                            pomessage.show();
                        }
                    });
                }
            }
        });
    }
    public void setListToAdapter(Context context, @LayoutRes int res){
        //if return list of version updates are available, continue
        if(versionInfoList.size() > 0){

            //attach list of version updates to the Adapter and ListView Object
            versionAdapter = new RecyclerViewAppVersionAdapter(versionInfoList, context);
            versionAdapter.tvBuildVers = build_version;
            versionAdapter.tvDateBuild = date_build;
            versionAdapter.tvNewUpdate = about_update;
            versionAdapter.resource = res;

            //validate recycler view obj if visible before attaching the adapter
            if(recyclerView.getVisibility() == View.VISIBLE){
                recyclerView.setAdapter(versionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        }
    }
    public void setBuild_version()              {
        //declare package manager
        PackageManager packageManager = this.getPackageManager();
        try {
            //get package for this app
            PackageInfo packageInfo = packageManager.getPackageInfo(this.getPackageName(), packageManager.GET_ACTIVITIES);

            //create a date formatter
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

            //get the source directory of application installed on device and convert to file
            File file = new File(packageInfo.applicationInfo.sourceDir);
            if (!packageInfo.versionName.trim().isEmpty()){
                //set build version from package info
                build_version.setText(packageInfo.versionName);

                //set date build by the last modified/installed date of source files
                date_build.setText(simpleDateFormat.format(file.lastModified()));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Build Version Failed", e.getMessage());
        }
    }
}