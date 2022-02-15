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

package org.rmj.guanzongroup.ghostrider.epacss.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.ImportData.ImportEmployeeRole;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_CashCounter;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogCollection;
import org.rmj.guanzongroup.ghostrider.epacss.Object.ChildObject;
import org.rmj.guanzongroup.ghostrider.epacss.Object.ParentObject;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.InternetStatusReciever;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMMainActivity;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.ExpandableListDrawerAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.AppDeptIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = Activity_Main.class.getSimpleName();
    private VMMainActivity mViewModel;

    private InternetStatusReciever poNetRecvr;

    private AppBarConfiguration mAppBarConfiguration;
    private MessageBox loMessage;
    private LoadDialog poDialog;
    private SessionManager poSession;
    private Intent loIntent;

    private ImageView imgDept;
    private TextView lblDept;
    private ExpandableListDrawerAdapter listAdapter;
    private  ExpandableListView expListView;

    private final List<ParentObject> poParentLst = new ArrayList<>();
    private List<ChildObject> poChildLst;
    private final HashMap<ParentObject, List<ChildObject>> poChild = new HashMap<>();

    public static DrawerLayout drawer;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMMainActivity.class);
        poNetRecvr = mViewModel.getInternetReceiver();

        mViewModel.getEmployeeInfo().observe(this, eEmployeeInfo -> {
            try{
                imgDept.setImageResource(AppDeptIcon.getIcon(eEmployeeInfo.getDeptIDxx()));
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getEmployeeRole().observe(this, roles -> {
            try{
                mViewModel.getChildRoles().observe(this, childMenus -> {
                    poParentLst.clear();
                    poChild.clear();
                    for(int x = 0; x < roles.size(); x++){
                        EEmployeeRole loRole = roles.get(x);
                        ParentObject loParent = new ParentObject(loRole.getObjectNm(), loRole.getHasChild());
                        poParentLst.add(loParent);
                        poChildLst = new ArrayList<>();
                        for (int i = 0; i < childMenus.size(); i++){
                            EEmployeeRole loChild = childMenus.get(i);
                            String lsParent = loRole.getObjectNm();
                            if(lsParent.equalsIgnoreCase(loChild.getParentxx())){
                                ChildObject loCMenu = new ChildObject(loChild.getObjectNm());
                                poChildLst.add(loCMenu);
                                poChild.put(loParent, poChildLst);
                            }
                        }
                    }

                    listAdapter = new ExpandableListDrawerAdapter(Activity_Main.this,
                            poParentLst, poChild);
                    expListView.setAdapter(listAdapter);
                    expListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
                        if(!poParentLst.get(groupPosition).isParent()){
                            loIntent = poParentLst.get(groupPosition).getIntent(Activity_Main.this);
                            if(loIntent == null){
                                loMessage.initDialog();
                                loMessage.setTitle("Dashboard");
                                loMessage.setMessage("No corresponding feature has been set.");
                                loMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                                loMessage.show();
                            } else {
                                startActivity(loIntent);
                                overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                            }
                        }
                        return false;
                    });
                    expListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
                        loIntent = poChild.get(poParentLst.get(groupPosition)).get(childPosition).getIntent(Activity_Main.this);
                        if(loIntent == null){
                            loMessage.initDialog();
                            loMessage.setTitle("Dashboard");
                            loMessage.setMessage("No corresponding feature has been set.");
                            loMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                            loMessage.show();
                        } else {
                            startActivity(loIntent);
                            overridePendingTransition(R.anim.anim_intent_slide_in_right, R.anim.anim_intent_slide_out_left);
                        }
                        return false;
                    });
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });

        mViewModel.getLastSelfieLog().observe(this, selfieLog -> {
            try {
                String cReqCCx = selfieLog.getReqCCntx();
                String lsDate1 = selfieLog.getLogTimex().substring(0, 10);
                if (cReqCCx.equalsIgnoreCase("0") &&
                        poSession.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER)) &&
                        lsDate1.equalsIgnoreCase(AppConstants.CURRENT_DATE)) {
                    loMessage.initDialog();
                    loMessage.setTitle("Cash Count");
                    loMessage.setMessage("You have an unfinish cash count entry. Proceed to Cash Count?");
                    loMessage.setPositiveButton("Proceed", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            Intent loIntent = new Intent(Activity_Main.this, Activity_CashCounter.class);
                            startActivity(loIntent);
                            dialog.dismiss();
                        }
                    });
                    loMessage.setNegativeButton("Cancel", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            dialog.dismiss();
                        }
                    });
                    loMessage.show();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*Edited by mike*/
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        loMessage = new MessageBox(Activity_Main.this);
        poDialog = new LoadDialog(Activity_Main.this);
        poSession = new SessionManager(Activity_Main.this);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        imgDept = view.findViewById(R.id.img_deptLogo);
        lblDept = view.findViewById(R.id.lbl_deptNme);
        lblDept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImportEmployeeRole loImport = new ImportEmployeeRole(getApplication());
                loImport.RefreshEmployeeRole(new ImportEmployeeRole.OnImportEmployeeRoleCallback() {
                    @Override
                    public void OnRequest() {
                        poDialog.initDialog("GhostRider", "Refreshing employee access. Please wait...", false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccess() {
                        poDialog.dismiss();
                    }

                    @Override
                    public void OnFailed(String message) {
                        poDialog.dismiss();
                        loMessage.initDialog();
                        loMessage.setTitle("GhostRider");
                        loMessage.setMessage(message);
                        loMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                        loMessage.show();
                    }
                });
            }
        });
    }

    /*Edited by mike*/
    public int GetPixelFromDips(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter loFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(poNetRecvr, loFilter);
        Log.e(TAG, "Internet status receiver has been registered.");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(poNetRecvr);
        AppConfigPreference.getInstance(Activity_Main.this).setIsAppFirstLaunch(false);
        Log.e(TAG, "Internet status receiver has been unregistered.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            loMessage.initDialog();
            loMessage.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                finish();
//                new REmployee(getApplication()).LogoutUserSession();
//                AppConfigPreference.getInstance(Activity_Main.this).setIsAppFirstLaunch(false);
            });
            loMessage.setNegativeButton("No", (view, dialog) -> dialog.dismiss());
            loMessage.setTitle("GhostRider");
            loMessage.setMessage("Exit Ghostrider app?");
            loMessage.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("request code:", String.valueOf(requestCode));
        Log.e("result code:", String.valueOf(resultCode));
        if(requestCode == AppConstants.INTENT_SELFIE_LOGIN && resultCode == RESULT_OK){
            Intent intent = new Intent(Activity_Main.this, Activity_Application.class);
            intent.putExtra("app", AppConstants.INTENT_SELFIE_LOGIN);
            startActivity(intent);
        }else if(requestCode == AppConstants.INTENT_DCP_LOG && resultCode == RESULT_OK){
            Intent  intent = new Intent(Activity_Main.this, Activity_LogCollection.class);
            intent.putExtra("syscode", "2");
            startActivity(intent);
        }else if(requestCode == AppConstants.INTENT_DCP_LIST && resultCode == RESULT_OK){
            Intent  intent = new Intent(Activity_Main.this, Activity_CollectionList.class);
            intent.putExtra("syscode", "2");
            startActivity(intent);
        }
    }
}