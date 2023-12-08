package org.rmj.guanzongroup.pacitareward.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.GCircle.room.Entities.EPacitaRule;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.Obj.PacitaRule;
import org.rmj.g3appdriver.GCircle.Apps.GawadPacita.pojo.BranchRate;
import org.rmj.g3appdriver.lib.Location.LocationRetriever;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchRate;
import org.rmj.guanzongroup.pacitareward.R;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchRate;

import java.util.List;

public class Activity_Branch_Rate extends AppCompatActivity {
    private String TAG = Activity_Branch_Rate.class.getSimpleName();
    private RecyclerView rate_list;
    private MaterialToolbar toolbar;
    private MaterialTextView rate_title;
    private VMBranchRate mViewModel;
    private MessageBox poMessage;
    private LoadDialog poLoad;
    private String intentDataBranchcd;
    private String intentDataBranchName;
    private MaterialButton btn_submit;

    private TextInputEditText txtRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branch_rate);

        mViewModel = new ViewModelProvider(this).get(VMBranchRate.class);

        poLoad = new LoadDialog(Activity_Branch_Rate.this);

        poMessage = new MessageBox(Activity_Branch_Rate.this);

        intentDataBranchcd = getIntent().getStringExtra("Branch Code");
        intentDataBranchName = getIntent().getStringExtra("Branch Name");

        toolbar = findViewById(R.id.toolbar);
        rate_list = findViewById(R.id.rate_list);
        rate_title = findViewById(R.id.rate_title);
        txtRemarks = findViewById(R.id.txtRemarks);
        btn_submit = findViewById(R.id.btn_submit);

        rate_title.setText(intentDataBranchName);

        /*TOOL BAR*/
        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        toolbar.setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mViewModel.InitializeEvaluation(intentDataBranchcd, new VMBranchRate.OnInitializeBranchEvaluationListener() {
            @Override
            public void onInitialize(String message) {
                poLoad.initDialog("Evaluation List", message, false);
                poLoad.show();
            }

            @Override
            public void OnSuccess(String transactNo, String message) {
                poLoad.dismiss();

                RecyclerViewAdapter_BranchRate loAdapter = new RecyclerViewAdapter_BranchRate(new RecyclerViewAdapter_BranchRate.onSelect() {
                    @Override
                    public void onItemSelect(String EntryNox, String result) {
                        mViewModel.setEvaluationResult(transactNo, EntryNox, result);
                    }
                });

                LinearLayoutManager loManager = new LinearLayoutManager(Activity_Branch_Rate.this);
                loManager.setOrientation(RecyclerView.VERTICAL);
                rate_list.setLayoutManager(loManager);
                rate_list.setAdapter(loAdapter);
                initLatLong(transactNo);
                mViewModel.getBranchEvaluation(transactNo).observe(Activity_Branch_Rate.this, new Observer<EPacitaEvaluation>() {
                    @Override
                    public void onChanged(EPacitaEvaluation ePacitaEvaluation) {
                        if (ePacitaEvaluation == null) {
                            return;
                        }
                        ePacitaEvaluation.setTransNox(transactNo);

                        mViewModel.GetCriteria().observe(Activity_Branch_Rate.this, new Observer<List<EPacitaRule>>() {
                            @Override
                            public void onChanged(List<EPacitaRule> ePacitaRules) {
                                if (ePacitaRules == null) {
                                    return;
                                }
                                if (ePacitaRules.size() == 0) {
                                    return;
                                }

                                String lsPayload = ePacitaEvaluation.getPayloadx();
                                List<BranchRate> loRate = PacitaRule.ParseBranchRate(lsPayload, ePacitaRules);
                                loAdapter.setItems(loRate);
                                loAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
                btn_submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mViewModel.setRemarks(transactNo, txtRemarks.getText().toString());
                        if (!transactNo.isEmpty()) {
                            mViewModel.saveBranchRatings(transactNo, new VMBranchRate.BranchRatingsCallback() {
                                @Override
                                public void onSave(String title, String message) {
                                    poLoad.initDialog(title, message, false);
                                    poLoad.show();
                                }

                                @Override
                                public void onSuccess(String message) {
                                    poLoad.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Save Evaluation");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                                        @Override
                                        public void OnButtonClick(View view, AlertDialog dialog) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });
                                    poMessage.show();
                                }

                                @Override
                                public void onFailed(String message) {
                                    poLoad.dismiss();
                                    poMessage.initDialog();
                                    poMessage.setTitle("Error Saving Application");
                                    poMessage.setMessage(message);
                                    poMessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                                        @Override
                                        public void OnButtonClick(View view, AlertDialog dialog) {
                                            dialog.dismiss();
                                        }
                                    });
                                    poMessage.show();
                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void OnError(String message) {
                poLoad.dismiss();
                poMessage.initDialog();
                poMessage.setTitle("Transaction Result");
                poMessage.setMessage(message);
                poMessage.setPositiveButton("OK", new MessageBox.DialogButton() {
                    @Override
                    public void OnButtonClick(View view, AlertDialog dialog) {
                        dialog.dismiss();
                        finish();
                    }
                });
                poMessage.show();
                btn_submit.setEnabled(false);
            }
        });
    }

    private void initLatLong(String transnox) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(Activity_Branch_Rate.this, "Please enable your location service.", Toast.LENGTH_SHORT).show();
            return;
        }
//
//        else{
//
//
//        }
        final String[] lat = {""};
        final String[] lng = {""};
//        LocationListener locationListener = new LocationListener() {
//            @Override
//
//
//            public void
//
//            onLocationChanged(Location location) {
//                double latitude = location.getLatitude();
//                double longitude = location.getLongitude();
//                lat[0] = String.valueOf(location.getLatitude());
//                lng[0] = String.valueOf(location.getLongitude());
//                mViewModel.setLatLong(transnox,lat[0], lng[0], Activity_Branch_Rate.this);
//                // Do something with the latitude and longitude
//            }
//        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request permissions if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
            @Override


            public void

            onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                lat[0] = String.valueOf(location.getLatitude());
                lng[0] = String.valueOf(location.getLongitude());
                mViewModel.setLatLong(transnox,lat[0], lng[0], Activity_Branch_Rate.this);
                // Do something with the latitude and longitude
            }
        });

    }
}