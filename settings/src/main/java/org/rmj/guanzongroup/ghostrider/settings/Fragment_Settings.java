package org.rmj.guanzongroup.ghostrider.settings;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.settings.themeController.ThemeHelper;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_PHONE_STATE;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CAMERA_REQUEST;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.CONTACT_REQUEST;
import static org.rmj.g3appdriver.GRider.Constants.AppConstants.LOCATION_REQUEST;

public class Fragment_Settings  extends PreferenceFragmentCompat {

    private SwitchPreferenceCompat themePreference;
    private Preference locationPref, cameraPref, phonePref;
    private VMSettings mViewModel;
    private GeoLocator poLocator;
    private boolean isContinue = false;
    private boolean isGPS = false;
    private int PERMISION_PHONE_REQUEST_CODE = 103, PERMISION_CAMERA_REQUEST_CODE = 102, PERMISION_LOCATION_REQUEST_CODE = 104;

    private MessageBox loMessage;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        themePreference = getPreferenceManager().findPreference("themePrefs");
        cameraPref = getPreferenceManager().findPreference("cameraPrefs");
        locationPref = getPreferenceManager().findPreference("locationPrefs");
        phonePref = getPreferenceManager().findPreference("phonePrefs");

        loMessage = new MessageBox(getActivity());
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
                    if ((ActivityCompat.checkSelfPermission(getActivity(), CAMERA) != PackageManager.PERMISSION_GRANTED)) {
                        mViewModel.getCamPermissions().observe(getViewLifecycleOwner(), strings -> {
                            ActivityCompat.requestPermissions(getActivity(),strings, CAMERA_REQUEST);

                        });
                    }else {
                        loMessage.setNegativeButton("Okay", (view, dialog) -> dialog.dismiss());
                        loMessage.setTitle("GhostRider Permissions");
                        loMessage.setMessage("You have already granted this permission.");
                        loMessage.show();
                    }
                    return true;
                }
            });
        }

        if (locationPref != null) {
            locationPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS),LOCATION_REQUEST);
                    return true;
                }
            });
        }
        if (phonePref != null) {
            phonePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {

                    if ((ActivityCompat.checkSelfPermission(getActivity(), READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {
                        mViewModel.getPhPermissions().observe(getViewLifecycleOwner(), strings -> {
                            ActivityCompat.requestPermissions(getActivity(),strings, CONTACT_REQUEST);

                        });
                    }else {
                        loMessage.setNegativeButton("Okay", (view, dialog) -> dialog.dismiss());
                        loMessage.setTitle("GhostRider Permissions");
                        loMessage.setMessage("You have already granted this permission.");
                        loMessage.show();
                    }
                    return true;
                }
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

}