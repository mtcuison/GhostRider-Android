package org.rmj.guanzongroup.ghostrider.epacss.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;

import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.ExpandableListDrawerAdapter;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.MenuModel;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.PopulateExpandableList;
import org.rmj.guanzongroup.ghostrider.epacss.adapter.PrepareData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private MessageBox loMessage;

    public static ExpandableListDrawerAdapter listAdapter;
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*Edited by mike*/
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        /*Edited by mike*/
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;

        loMessage = new MessageBox(MainActivity.this);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        expListView.setIndicatorBoundsRelative(width - GetPixelFromDips(50), width - GetPixelFromDips(10));

        prepareData = new PrepareData();
        prepareData.prepareMenuData();
        //populateExpandableList();

        populateExpandableList = new PopulateExpandableList();
        populateExpandableList.populate(this, () -> {
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_logout){
            loMessage.setPositiveButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                finish();
                new REmployee(getApplication()).LogoutUserSession();
                startActivity(new Intent(MainActivity.this, SplashScreenActivity.class));
            });
            loMessage.setNegativeButton("No", (view, dialog) -> {
                dialog.dismiss();
            });
            loMessage.setTitle("GhostRider Session");
            loMessage.setMessage("Are you sure you want to end session/logout?");
            loMessage.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else{
            loMessage.setPositiveButton("No", (view, dialog) -> dialog.dismiss());
            loMessage.setNegativeButton("Yes", (view, dialog) -> {
                dialog.dismiss();
                finish();
                new REmployee(getApplication()).LogoutUserSession();
            });
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
}