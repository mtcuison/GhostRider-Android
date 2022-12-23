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

import android.content.Intent;
import android.content.IntentFilter;
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
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.lib.ImportData.ImportEmployeeRole;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogCollection;
import org.rmj.guanzongroup.ghostrider.epacss.Object.ChildObject;
import org.rmj.guanzongroup.ghostrider.epacss.Object.ParentObject;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.DataSyncService;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMMainActivity;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.ExpandableListDrawerAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.AppDeptIcon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = Activity_Main.class.getSimpleName();
    private VMMainActivity mViewModel;

    private DataSyncService poNetRecvr;

    private AppBarConfiguration mAppBarConfiguration;
    private MessageBox loMessage;
    private LoadDialog poDialog;
    private SessionManager poSession;
    private Intent loIntent;
    private boolean cSlfiex;

    private ImageView imgDept;
    private TextView lblDept;
    private ExpandableListDrawerAdapter listAdapter;
    private ExpandableListView expListView;

    private final List<ParentObject> poParentLst = new ArrayList<>();
    private List<ChildObject> poChildLst;
    private final HashMap<ParentObject, List<ChildObject>> poChild = new HashMap<>();

    public static DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMainActivity.class);
        poNetRecvr = mViewModel.getInternetReceiver();
        setContentView(R.layout.activity_main);
        initWidgets();

        mViewModel.getEmployeeInfo().observe(this, eEmployeeInfo -> {
            try{
                imgDept.setImageResource(AppDeptIcon.getIcon(eEmployeeInfo.getDeptIDxx()));
                lblDept.setText(DeptCode.getDepartmentName(eEmployeeInfo.getDeptIDxx()));
                cSlfiex = eEmployeeInfo.getSlfieLog().equalsIgnoreCase("1");
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
                                if("selfie log".equalsIgnoreCase(loChild.getObjectNm().toLowerCase(Locale.ROOT))) {
                                    if(cSlfiex || loChild.getRecdStat().equalsIgnoreCase("1")) {
                                        ChildObject loCMenu = new ChildObject(loChild.getObjectNm());
                                        poChildLst.add(loCMenu);
                                        poChild.put(loParent, poChildLst);
                                    }
                                } else if (loChild.getRecdStat().equalsIgnoreCase("1")){
                                    ChildObject loCMenu = new ChildObject(loChild.getObjectNm());
                                    poChildLst.add(loCMenu);
                                    poChild.put(loParent, poChildLst);
                                }
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
                                loMessage.setTitle("Guanzon Circle");
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
                    expListView.collapseGroup(1);
                });
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

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
        lblDept.setOnClickListener(v -> {
            ImportEmployeeRole loImport = new ImportEmployeeRole(getApplication());
            loImport.RefreshEmployeeRole(new ImportEmployeeRole.OnImportEmployeeRoleCallback() {
                @Override
                public void OnRequest() {
                    poDialog.initDialog("Guanzon Circle", "Refreshing employee access. Please wait...", false);
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
                    loMessage.setTitle("Guanzon Circle");
                    loMessage.setMessage(message);
                    loMessage.setPositiveButton("Okay", (view1, dialog) -> dialog.dismiss());
                    loMessage.show();
                }
            });
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
            loMessage.setTitle("Guanzon Circle");
            loMessage.setMessage("Exit Guanzon Circle?");
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
            startActivity(intent);
        }else if(requestCode == AppConstants.INTENT_DCP_LIST && resultCode == RESULT_OK){
            Intent  intent = new Intent(Activity_Main.this, Activity_CollectionList.class);
            startActivity(intent);
        }
    }
}