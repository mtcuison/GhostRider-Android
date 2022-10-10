package org.guanzongroup.com.itinerary.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.guanzongroup.com.itinerary.Adapter.AdapterEmployees;
import org.guanzongroup.com.itinerary.Adapter.AdapterItineraries;
import org.guanzongroup.com.itinerary.R;
import org.guanzongroup.com.itinerary.ViewModel.VMItinerary;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Activity_ItineraryLog extends AppCompatActivity {
    private static final String TAG = Activity_ItineraryLog.class.getSimpleName();

    private VMItinerary mViewModel;
    private LoadDialog poLoad;
    private MessageBox poDialog;

    private Toolbar toolbar;
    private TextView lblUser, lblDept;
    private TextInputEditText txtFrom, txtThru;
    private RecyclerView recyclerView;
    private MaterialButton btnFilter;

    private String psFrom, psThru;

    public boolean isFiltered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ItineraryLog.this).get(VMItinerary.class);
        setContentView(R.layout.activity_itinerary_log);

        poLoad = new LoadDialog(Activity_ItineraryLog.this);
        poDialog = new MessageBox(Activity_ItineraryLog.this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Employee Itinerary");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        lblUser = findViewById(R.id.lbl_username);
        lblDept = findViewById(R.id.lbl_userPosition);
        txtFrom = findViewById(R.id.txt_dateFrom);
        txtThru = findViewById(R.id.txt_dateThru);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager loManager = new LinearLayoutManager(Activity_ItineraryLog.this);
        loManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(loManager);
        btnFilter = findViewById(R.id.btn_filter);

        mViewModel.GetUserInfo().observe(Activity_ItineraryLog.this, user -> {
            try {
                lblUser.setText(user.sUserName);
                lblDept.setText(DeptCode.getDepartmentName(user.sDeptIDxx));

                mViewModel.ImportUsers(new VMItinerary.OnImportUsersListener() {
                    @Override
                    public void OnImport(String title, String message) {
                        poLoad.initDialog(title, message, false);
                        poLoad.show();
                    }

                    @Override
                    public void OnSuccess(List<JSONObject> args) {
                        poLoad.dismiss();
                        InitEmployeeList(args);
                    }

                    @Override
                    public void OnFailed(String message) {
                        poLoad.dismiss();
                        poDialog.initDialog();
                        poDialog.setTitle("Employee Itinerary");
                        poDialog.setMessage(message);
                        poDialog.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                        });
                        poDialog.show();
                    }
                });

//                int lnUserLvl = Integer.parseInt(user.nUserLevl);
//                if(lnUserLvl == DeptCode.LEVEL_DEPARTMENT_HEAD){
//                    mViewModel.ImportUsers(new VMItinerary.OnImportUsersListener() {
//                        @Override
//                        public void OnImport(String title, String message) {
//                            poLoad.initDialog(title, message, false);
//                            poLoad.show();
//                        }
//
//                        @Override
//                        public void OnSuccess(List<JSONObject> args) {
//                            poLoad.dismiss();
//                            InitEmployeeList(args);
//                        }
//
//                        @Override
//                        public void OnFailed(String message) {
//                            poLoad.dismiss();
//                            poDialog.initDialog();
//                            poDialog.setTitle("Employee Itinerary");
//                            poDialog.setMessage(message);
//                            poDialog.setPositiveButton("Okay", (view, dialog) -> {
//                                dialog.dismiss();
//                            });
//                            poDialog.show();
//                        }
//                    });
//                } else if(lnUserLvl == DeptCode.LEVEL_GENERAL_MANAGER){
//
//                } else {
//                    mViewModel.DownloadItinerary(psFrom, psThru, new VMItinerary.OnActionCallback() {
//                        @Override
//                        public void OnLoad(String title, String message) {
//                            poLoad.initDialog(title, message, false);
//                            poLoad.show();
//                        }
//
//                        @Override
//                        public void OnSuccess(String args) {
//                            poLoad.dismiss();
//                            poDialog.initDialog();
//                            poDialog.setTitle("Employee Itinerary");
//                            poDialog.setMessage(args);
//                            poDialog.setPositiveButton("Okay", (view, dialog) -> {
//                                dialog.dismiss();
//                            });
//                            poDialog.show();
//                        }
//
//                        @Override
//                        public void OnFailed(String message) {
//                            poLoad.dismiss();
//                            poDialog.initDialog();
//                            poDialog.setTitle("Employee Itinerary");
//                            poDialog.setMessage(message);
//                            poDialog.setPositiveButton("Okay", (view, dialog) -> {
//                                dialog.dismiss();
//                            });
//                            poDialog.show();
//                        }
//                    });
//                    previewIntinerary();
//                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        txtFrom.setText(new AppConstants().CURRENT_DATE_WORD);

        txtFrom.setOnClickListener(v -> {
            if(txtThru.getText().toString().trim().isEmpty()) {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                final DatePickerDialog StartTime = new DatePickerDialog(Activity_ItineraryLog.this, R.style.MyTimePickerDialogTheme, (view131, year, monthOfYear, dayOfMonth) -> {
                    try{
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String lsDate = dateFormatter.format(newDate.getTime());
                        txtFrom.setText(lsDate);
                        Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                        psFrom = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                        Log.d(TAG, "Date start: " + psFrom + ", UI format: " + lsDate);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            } else {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                final DatePickerDialog StartTime = new DatePickerDialog(Activity_ItineraryLog.this, R.style.MyTimePickerDialogTheme, (view131, year, monthOfYear, dayOfMonth) -> {
                    try{
                        Date loThru = new SimpleDateFormat("MMMM dd, yyyy").parse(txtThru.getText().toString().trim());
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String lsDate = dateFormatter.format(newDate.getTime());
                        Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                        if(loThru.before(loDate)){
                            Toast.makeText(Activity_ItineraryLog.this, "Invalid date entry.", Toast.LENGTH_SHORT).show();
                        } else {
                            txtFrom.setText(lsDate);
                            psFrom = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            }
        });

        txtThru.setOnClickListener(v -> {
            if(txtFrom.getText().toString().trim().isEmpty()){
                Toast.makeText(Activity_ItineraryLog.this, "Date started must be set before the set time end.", Toast.LENGTH_SHORT).show();
            } else {
                final Calendar newCalendar = Calendar.getInstance();
                @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
                final DatePickerDialog StartTime = new DatePickerDialog(Activity_ItineraryLog.this, R.style.MyTimePickerDialogTheme, (view131, year, monthOfYear, dayOfMonth) -> {
                    try{
                        Date loFrom = new SimpleDateFormat("MMMM dd, yyyy").parse(txtFrom.getText().toString().trim());
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String lsDate = dateFormatter.format(newDate.getTime());
                        Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                        if(loFrom.after(loDate)){
                            Toast.makeText(Activity_ItineraryLog.this, "Invalid date entry.", Toast.LENGTH_SHORT).show();
                        } else {
                            txtThru.setText(lsDate);
                            psThru = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                StartTime.show();
            }
        });

        btnFilter.setOnClickListener(v -> {
            if(psFrom == null && psThru == null) {
                Toast.makeText(Activity_ItineraryLog.this, "Please select starting and end date to filter.", Toast.LENGTH_SHORT).show();
            } else if(!isFiltered) {
                mViewModel.DownloadItinerary(psFrom, psThru, new VMItinerary.OnActionCallback() {
                    @Override
                    public void OnLoad(String title, String message) {
                        poLoad.initDialog(title, message, false);
                        poLoad.show();
                    }

                    @Override
                    public void OnSuccess(String args) {
                        poLoad.dismiss();
                        poDialog.initDialog();
                        poDialog.setTitle("Employee Itinerary");
                        poDialog.setMessage(args);
                        poDialog.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                        });
                        poDialog.show();
                    }

                    @Override
                    public void OnFailed(String message) {
                        poLoad.dismiss();
                        poDialog.initDialog();
                        poDialog.setTitle("Employee Itinerary");
                        poDialog.setMessage(message);
                        poDialog.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                        });
                        poDialog.show();
                    }
                });

                mViewModel.GetItineraryForFilteredDate(psFrom, psThru).observe(Activity_ItineraryLog.this, eItineraries -> {
                    try {
                        isFiltered = true;
                        recyclerView.setAdapter(new AdapterItineraries(eItineraries));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                btnFilter.setText("Clear Filter");
            } else {
                isFiltered = false;
                previewIntinerary();
                txtFrom.setText(new AppConstants().CURRENT_DATE_WORD);
                txtThru.setText("");
                btnFilter.setText("Filter Records");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void previewIntinerary(){
        if(!isFiltered) {
            mViewModel.GetItineraryListForCurrentDay().observe(Activity_ItineraryLog.this, eItineraries -> {
                try {
                    recyclerView.setAdapter(new AdapterItineraries(eItineraries));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void InitEmployeeList(List<JSONObject> foVal) {
        try {
            recyclerView.setAdapter(new AdapterEmployees(foVal, (args, args1, args2) -> {

            }));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}