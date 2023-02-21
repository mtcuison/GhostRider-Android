package org.rmj.guanzongroup.ghostrider.settings.Activity;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMAppVersion;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.adapter.RecyclerViewAppVersionAdapter;

import java.util.ArrayList;
import java.util.List;

public class Activity_AppVersion extends AppCompatActivity {
    TextView build_version;
    TextView date_build;
    TextView lbl_aboutupdate;
    TextView about_update;
    TextView lbl_updatedLog;
    TextView lbl_updatedFixedConcerns;
    Button btn_checkupdate;
    RecyclerView rec_updatedNewFeatures;
    RecyclerView rec_updatedFixedConcerns;
    RecyclerViewAppVersionAdapter versionAdapter;
    private VMAppVersion mViewModel;
    private LoadDialog poload;
    private MessageBox pomessage;
    List<VersionInfo> versionInfoList;
    AlertDialog alertDialog;
    Boolean btnClicked;

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
        lbl_updatedFixedConcerns = findViewById(R.id.lbl_updatedFixedConcerns);

        btn_checkupdate = findViewById(R.id.btn_checkupdate);
        rec_updatedNewFeatures = findViewById(R.id.rec_updatelogs);
        rec_updatedFixedConcerns = findViewById(R.id.rec_updatedFixedConcerns);

        btnClicked = false;

        //create alert dialog obj builder
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_AppVersion.this);
        //create layout view for dialog builder, using the main activity(Context) and xml file(layout view)
        View view = LayoutInflater.from(Activity_AppVersion.this).inflate(R.layout.dialog_appversion_download_update,null);

        //attach the layout view created to builder
        builder.setView(view);
        //create new obj alert dialog (parent container) and attach to the obj builder created
        alertDialog = builder.create();
        //set background color for the dialog
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        //initialize dialog for displaying message
        pomessage.initDialog();

       /*set by default, the current build version of  app*/
       String build_name = AppConfigPreference.getInstance(Activity_AppVersion.this).getVersionName(); //get build version
       build_version.setText(build_name); //display build version

        //call method to get the list of versions
        getAppVersion();
       //call method to show what to display
        setonDisplay();
       //call method for button listener
        btnCheckUpdate();
    }
    public void setonDisplay() {
        //hide details by default
        lbl_aboutupdate.setVisibility(View.INVISIBLE);
        about_update.setVisibility(View.INVISIBLE);
        lbl_updatedLog.setVisibility(View.INVISIBLE);
        lbl_updatedFixedConcerns.setVisibility(View.INVISIBLE);
        rec_updatedNewFeatures.setVisibility(View.INVISIBLE);
        rec_updatedFixedConcerns.setVisibility(View.INVISIBLE);

        //show details upon button clicked
        if (btnClicked.equals(true)) {
            //show what to display if, list of versions has retrieved more than 0
            if (versionInfoList.size() > 0) {
                lbl_aboutupdate.setVisibility(View.VISIBLE);
                about_update.setVisibility(View.VISIBLE);
                lbl_updatedLog.setVisibility(View.VISIBLE);
                lbl_updatedFixedConcerns.setVisibility(View.VISIBLE);
                rec_updatedNewFeatures.setVisibility(View.VISIBLE);
                rec_updatedFixedConcerns.setVisibility(View.VISIBLE);
            }
        }
    }
    public void btnCheckUpdate(){
        btn_checkupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set listener for this button, when clicked
                btnClicked = true;
                //call method to get the list of versions
                getAppVersion();
                //show details
                setonDisplay();
                //get button text
                String btnText = btn_checkupdate.getHint().toString();
                if(btnText.equals("Check for Updates")){
                    //set list to Adapter, New Features and Fixed Concerns
                    setListToAdapter(Activity_AppVersion.this, R.layout.update_version_logs, rec_updatedNewFeatures);
                    setListToAdapter(Activity_AppVersion.this, R.layout.update_version_logs_fixed_concerns, rec_updatedFixedConcerns);
                    //set button text
                    btn_checkupdate.setHint("Download Updates");
                }else if(btnText.equals("Download Updates")){
                    //Intent intent = new Intent(Activity_AppVersion.this, Activity_AppVersion_Download.class);
                    //startActivity(intent);
                    //set list to Adapter
                    //setListToAdapter(alertDialog.getContext(), R.layout.dialog_appversion_download_update);
                }
            }
        });
    }
    public void getAppVersion(){
        mViewModel.getVersionList(new VMAppVersion.onDownloadVersionList() {
            @Override
            public void onDownload() {
                //show dialog
                poload.initDialog("Check Updates", "Checking Updates", false);
                poload.show();
            }
            @Override
            public void onSuccess(List<VersionInfo> list) {
                //set the list value
                versionInfoList= list;

                //set dialog message and button
                pomessage.setTitle("Check Updates");
                pomessage.setMessage("Finished Getting Updates");

                //show message
                pomessage.show();

                //close message
                poload.dismiss();

                //set action button to close message dialog
                pomessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                    @Override
                    public void OnButtonClick(View view, AlertDialog dialog) {
                        //close dialogs upon clicking
                        dialog.dismiss();
                    }
                });
            }
            @Override
            public void onFailed(String message) {
                //set dialog message and button
                pomessage.setTitle("Failed Checking Updates");
                pomessage.setMessage(message);

                //show message
                pomessage.show();

                //close message
                poload.dismiss();

                pomessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                    @Override
                    public void OnButtonClick(View view, AlertDialog dialog) {
                        //close dialogs upon clicking
                        dialog.dismiss();
                    }
                });
            }
        });
    }
    public void setListToAdapter(Context context, @LayoutRes int res, RecyclerView recyclerView){
        //if return list of version updates are available, continue
        if(versionInfoList.size() > 0){
            //attach list of version updates to the Adapter and ListView Object
            versionAdapter = new RecyclerViewAppVersionAdapter(versionInfoList, context);
            versionAdapter.tvBuildVers = build_version;
            versionAdapter.tvDateBuild = date_build;
            versionAdapter.tvNewUpdate = about_update;
            versionAdapter.resource = res;

            //validate recycler view obj if visible before attaching the adapter
            if(recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setAdapter(versionAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        }
    }
}