package org.rmj.guanzongroup.ghostrider.epacss.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMSettings;
import org.rmj.guanzongroup.ghostrider.epacss.themeController.ThemeHelper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

import static android.net.wifi.WifiConfiguration.Status.strings;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.LOCATION_REQUEST;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        Toolbar toolbar = findViewById(R.id.toolbar_settings);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private SwitchPreferenceCompat themePreference, phonePref;
        private Preference locationPref, cameraPref;
        private VMSettings mViewModel;
        private GeoLocator poLocator;
        private boolean isContinue = false;
        private boolean isGPS = false;
        private int PERMISION_PHONE_REQUEST_CODE = 103, PERMISION_CAMERA_REQUEST_CODE = 102, PERMISION_LOCATION_REQUEST_CODE = 104;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            themePreference = getPreferenceManager().findPreference("themePrefs");
            cameraPref = getPreferenceManager().findPreference("cameraPrefs");
            locationPref = getPreferenceManager().findPreference("locationPrefs");
            phonePref = getPreferenceManager().findPreference("phonePrefs");

            mViewModel = new ViewModelProvider(this).get(VMSettings.class);

            mViewModel.isLocationPermissionsGranted().observe(this, isGranted -> {
                GetLocation();
            });
            mViewModel.isCameraPermissionsGranted().observe(this, isGranted -> {
               if (isGranted){
                   cameraPref.setSummary(R.string.location_on_summary);
               }else {
                   cameraPref.setSummary(R.string.location_off_summary);

               }
            });

            if (themePreference != null) {
                themePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        Boolean themeOption = (Boolean) newValue;
                        if (themeOption) {
                            themePreference.getSummaryOn();
                        } else {
                            themePreference.getSummaryOff();
                        }
                        ThemeHelper.applyTheme(themeOption);
                        return true;
                    }
                });
            }
            if (cameraPref != null) {
                cameraPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    @Override
                    public boolean onPreferenceClick(Preference preference) {
//                        askPermission(Manifest.permission.CAMERA, PERMISION_CAMERA_REQUEST_CODE);
                        mViewModel.getCameraPermisions().observe(getViewLifecycleOwner(), strings -> ActivityCompat.requestPermissions(getActivity(), strings, PERMISION_CAMERA_REQUEST_CODE));

                        return true;
                    }
                });
            }

            if (locationPref != null) {
                locationPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                    public boolean onPreferenceClick(Preference preference) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),LOCATION_REQUEST);
                        return true;
                    }
                });
            }

        }

        private void askPermission(String permission,int requestCode) {
            if (ContextCompat.checkSelfPermission(getActivity(),permission)!= PackageManager.PERMISSION_GRANTED){
                // We Dont have permission
                mViewModel.getCameraPermisions().observe(getViewLifecycleOwner(), strings -> ActivityCompat.requestPermissions(getActivity(), strings, PERMISION_CAMERA_REQUEST_CODE));

//                ActivityCompat.requestPermissions(getActivity(),new String[]{permission},requestCode);
            }else {
                // We already have permission do what you want
//                Toast.makeText(getActivity(),"Permission is already granted.",Toast.LENGTH_LONG).show();

                mViewModel.setCameraPermissionsGranted(false);
                mViewModel.getCameraPermisions().observe(getViewLifecycleOwner(), strings -> ActivityCompat.requestPermissions(getActivity(), strings, PERMISION_CAMERA_REQUEST_CODE));

            }

        }
        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            if(requestCode == LOCATION_REQUEST){
                boolean lbIsGrnt = true;
                for(int x = 0 ; x < grantResults.length; x++){
                    if(ContextCompat.checkSelfPermission(getActivity(), permissions[x]) != grantResults[x]){
                        lbIsGrnt = false;
                        break;
                    }
                }
                if(lbIsGrnt){
                    mViewModel.setLocationPermissionsGranted(true);
                }
            }
            if(requestCode == PERMISION_CAMERA_REQUEST_CODE){
                boolean lbIsGrnt = true;
                for(int x = 0 ; x < grantResults.length; x++){
                    if(ContextCompat.checkSelfPermission(getActivity(), permissions[x]) != grantResults[x]){
                        lbIsGrnt = false;
                        mViewModel.setCameraPermissionsGranted(false);
                        break;
                    }
                }
                if(lbIsGrnt){
                    mViewModel.setCameraPermissionsGranted(true);

                }
            }
        }
        @Override
        public void onActivityResult(int requestCode, int requestResult, Intent data) {
            if(requestCode == LOCATION_REQUEST){
                mViewModel.setLocationPermissionsGranted(true);
            }
        }
        public void GetLocation(){


            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

                locationPref.setSummary(R.string.location_off_summary);

            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){


                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                        (getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);

                } else {
                    locationPref.setSummary(R.string.location_on_summary);
                }
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}

