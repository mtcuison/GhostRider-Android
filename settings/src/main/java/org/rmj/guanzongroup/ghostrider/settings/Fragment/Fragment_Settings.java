/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/23/21 2:03 PM
 * project file last modified : 6/23/21 2:01 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Fragment;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;


import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_CheckUpdate;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_HelpList;
import org.rmj.guanzongroup.ghostrider.settings.Activity.Activity_LocalData;
import org.rmj.guanzongroup.ghostrider.settings.Dialog.Dialog_ChangePassword;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMSettings;
import org.rmj.guanzongroup.ghostrider.settings.themeController.ThemeHelper;
import org.rmj.guanzongroup.ghostrider.settings.utils.DatabaseExport;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CAMERA_REQUEST;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.LOCATION_REQUEST;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.STORAGE_REQUEST;

public class Fragment_Settings  extends PreferenceFragmentCompat {

    private SwitchPreferenceCompat themePreference;
    private Preference locationPref,
            cameraPref,
            phonePref,
            exportPref,
            localData,
            Accountxx,
            chkUpdate,
            debugMode,
            helpPref;
    private VMSettings mViewModel;
    private DatabaseExport dbExport;
    private MessageBox loMessage;
    private LoadDialog poDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        themePreference = getPreferenceManager().findPreference("themePrefs");
        cameraPref = getPreferenceManager().findPreference("cameraPrefs");
        locationPref = getPreferenceManager().findPreference("locationPrefs");
        phonePref = getPreferenceManager().findPreference("phonePrefs");
        exportPref = getPreferenceManager().findPreference("exportPrefs");
        localData = getPreferenceManager().findPreference("localDataPrefs");
        Accountxx = getPreferenceManager().findPreference("accountPrefs");
        chkUpdate = getPreferenceManager().findPreference("appUpdatePrefs");
        debugMode = getPreferenceScreen().findPreference("appDebugModePref");
        helpPref = getPreferenceManager().findPreference("appHelpPrefs");

        dbExport = new DatabaseExport(getActivity(), "Database", "GGC_ISysDBF.db");
        loMessage = new MessageBox(getActivity());
        poDialog = new LoadDialog(getActivity());

        mViewModel = new ViewModelProvider(this).get(VMSettings.class);

        mViewModel.isLocPermissionGranted().observe(getViewLifecycleOwner(), isGranted -> {
            GetLocation();
        });

        mViewModel.isCamPermissionGranted().observe(getViewLifecycleOwner(), isGranted -> {
            Log.e("Camera ", String.valueOf(isGranted));
            checkPermissionCamera();
        });

        mViewModel.getCameraSummary().observe(getViewLifecycleOwner(),s -> cameraPref.setSummary(s));

        if (themePreference != null) {
            themePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                Boolean themeOption = (Boolean) newValue;
                if (themeOption) {
                    themePreference.getSummaryOn();
                } else {
                    themePreference.getSummaryOff();
                }
                ThemeHelper.applyTheme(themeOption);
                return true;
            });
        }
        if (cameraPref != null) {
            cameraPref.setOnPreferenceClickListener(preference -> {
                if ((ActivityCompat.checkSelfPermission(getActivity(), CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                    mViewModel.getCamPermissions().observe(getViewLifecycleOwner(), strings -> {
                        ActivityCompat.requestPermissions(getActivity(),strings, CAMERA_REQUEST);
                    });
                }else {
                    loMessage.initDialog();
                    loMessage.setNegativeButton("Okay", (view, dialog) -> dialog.dismiss());
                    loMessage.setTitle("GhostRider Permissions");
                    loMessage.setMessage("You have already granted this permission.");
                    loMessage.show();
                }
                return true;
            });
        }

        if (locationPref != null) {
            locationPref.setOnPreferenceClickListener(preference -> {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),LOCATION_REQUEST);
                return true;
            });
        }

        if (phonePref != null) {
            phonePref.setOnPreferenceClickListener(preference -> {
                if ((ActivityCompat.checkSelfPermission(getActivity(), READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
                    mViewModel.getPhPermissions().observe(getViewLifecycleOwner(), strings -> {
                        ActivityCompat.requestPermissions(getActivity(),strings, STORAGE_REQUEST);

                    });
                }else {

                    loMessage.initDialog();
                    loMessage.setNegativeButton("Okay", (view, dialog) -> dialog.dismiss());
                    loMessage.setTitle("GhostRider Permissions");
                    loMessage.setMessage("You have already granted this permission.");
                    loMessage.show();
                }
                return true;
            });
        }
        if (exportPref != null) {
           exportPref.setOnPreferenceClickListener(preference -> {
               try {
                   mViewModel.isStoragePermissionGranted().observe(getViewLifecycleOwner(), isGranted -> {
                      if (!isGranted){
                          mViewModel.getStoragePermission().observe(getViewLifecycleOwner(), strings -> {
                              ActivityCompat.requestPermissions(getActivity(),strings, STORAGE_REQUEST);
                          });
                      }else {
                          showExportDialog(dbExport.export());
                      }
                   });

               }catch (SecurityException e){
                   Log.e("Security Exception " , e.getMessage());
               }
               catch (Exception e){
                   Log.e("Exception " , e.getMessage());
               }

               return false;
           });
        }
        if(localData != null){
            localData.setOnPreferenceClickListener(preference -> {
                Intent loIntent = new Intent(getActivity(), Activity_LocalData.class);
                startActivity(loIntent);
                return false;
            });
        }
        if(Accountxx != null){
            Accountxx.setOnPreferenceClickListener(preference -> {
                Dialog_ChangePassword loPass = new Dialog_ChangePassword(getActivity(), (OldPass, NewPass, dialog) -> mViewModel.ChangePassword(OldPass, NewPass, new VMSettings.ChangePasswordCallback() {
                    @Override
                    public void OnLoad(String Title, String Message) {
                        poDialog.initDialog(Title, Message, false);
                        poDialog.show();
                    }

                    @Override
                    public void OnSuccess() {
                        poDialog.dismiss();
                        loMessage.initDialog();
                        loMessage.setTitle("Change Password");
                        loMessage.setMessage("Account updated successfully");
                        loMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                        loMessage.show();
                        dialog.dismiss();
                    }

                    @Override
                    public void OnFailed(String message) {
                        poDialog.dismiss();
                        loMessage.initDialog();
                        loMessage.setTitle("Change Password");
                        loMessage.setMessage(message);
                        loMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
                        loMessage.show();
                    }
                }));
                loPass.show();
                return false;
            });
        }
        if(chkUpdate != null){
            chkUpdate.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(getActivity(), Activity_CheckUpdate.class));
                return false;
            });
        }

        if(debugMode != null){
            SessionManager poUser = new SessionManager(getActivity());
            AppConfigPreference poConfig = AppConfigPreference.getInstance(getActivity());
            MessageBox loMessage = new MessageBox(getActivity());
            if (!poUser.getDeptID().equalsIgnoreCase(DeptCode.MANAGEMENT_INFORMATION_SYSTEM)){
                debugMode.setVisible(true);
            } else {
                debugMode.setVisible(true);
            }
            debugMode.setOnPreferenceClickListener(preference -> {
                if(!poConfig.isTesting_Phase()){
                    loMessage.initDialog();
                    loMessage.setTitle("Android Testing");
                    loMessage.setMessage("GRider test mode is enabled.");
                    loMessage.setPositiveButton("Okay", (view, dialog) -> {
                        poConfig.setIsTesting(true);
                        dialog.dismiss();
                        requireActivity().finish();
                    });
                    loMessage.show();
                } else {
                    loMessage.initDialog();
                    loMessage.setTitle("Android Testing");
                    loMessage.setMessage("Disable test mode?");
                    loMessage.setPositiveButton("Disable", (view, dialog) -> {
                        poConfig.setIsTesting(false);
                        dialog.dismiss();
                        requireActivity().finish();
                    });
                    loMessage.setNegativeButton("Cancel", (view, dialog) -> {
                        dialog.dismiss();
                    });
                    loMessage.show();
                }
                return false;
            });

        }
        if(helpPref != null){
            helpPref.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(getActivity(), Activity_HelpList.class));
                return false;
            });
        }

    }
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

    }
    @Override
    public void onStart()
    {
        super.onStart();
        checkPermissionCamera();
        checkPermissionPhoneState();
//        Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
    }
    @Override
    public void onResume()
    {
        super.onResume();
        checkPermissionCamera();
        checkPermissionPhoneState();
//        Toast.makeText(getApplicationContext(),"Now onStart() calls", Toast.LENGTH_LONG).show(); //onStart Called
    }

    boolean checkPermissionCamera() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                cameraPref.setSummary("Camera Permission Enabled");
                mViewModel.getCameraSummary().observe(getViewLifecycleOwner(), s -> cameraPref.setSummary(s));
                return true;
            } else {
                cameraPref.setSummary("Camera Permission Disabled");
                mViewModel.getCameraSummary().observe(getViewLifecycleOwner(), s -> cameraPref.setSummary(s));

                return false;
            }
        }
        return true;
    }
    boolean checkPermissionPhoneState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED) {
                phonePref.setSummary("Phone State Permission Enabled");
                return true;
            } else {
                phonePref.setSummary("Phone State Permission Disabled");
                return false;
            }
        }
        return true;
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

    public void showExportDialog(String message){
        loMessage.initDialog();
        loMessage.setNegativeButton("Okay", (view, dialog) -> dialog.dismiss());
        loMessage.setTitle("GhostRider Permissions");

        if (message.equalsIgnoreCase("Exporting failed!")){
            loMessage.setPositiveButton("Retry", (view, dialog) -> dbExport.export());
            loMessage.setMessage(message);
            loMessage.show();
        }else {
            loMessage.setMessage(message);
            loMessage.show();
        }
    }
}