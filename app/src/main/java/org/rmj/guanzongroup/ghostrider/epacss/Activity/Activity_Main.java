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

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity.Activity_Application;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_LogCollection;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.Service.InternetStatusReciever;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMMainActivity;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.ExpandableListDrawerAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.MenuModel;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.PopulateExpandableList;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.PrepareData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = Activity_Main.class.getSimpleName();
    private VMMainActivity mViewModel;

    private InternetStatusReciever poNetRecvr;

    private AppBarConfiguration mAppBarConfiguration;
    private MessageBox loMessage;

    @SuppressLint("StaticFieldLeak")
    public static ExpandableListDrawerAdapter listAdapter;
    @SuppressLint("StaticFieldLeak")
    public static  ExpandableListView expListView;

    public static  List<MenuModel> listDataHeader = new ArrayList<>();
    public static  HashMap<MenuModel, List<MenuModel>> listDataChild = new HashMap<>();
    private PrepareData prepareData;
    public static DrawerLayout drawer;
    private PopulateExpandableList populateExpandableList;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        mViewModel = new ViewModelProvider(this).get(VMMainActivity.class);
        poNetRecvr = mViewModel.getInternetReceiver();

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
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        prepareData = new PrepareData();
        prepareData.prepareMenuData(this);
        //populateExpandableList();

        populateExpandableList = new PopulateExpandableList();
        populateExpandableList.populate(this, Activity_Main.this, () -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                new REmployee(getApplication()).LogoutUserSession();
                AppConfigPreference.getInstance(Activity_Main.this).setIsAppFirstLaunch(false);
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