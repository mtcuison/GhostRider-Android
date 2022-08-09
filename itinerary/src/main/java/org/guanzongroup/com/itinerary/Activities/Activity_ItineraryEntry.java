package org.guanzongroup.com.itinerary.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.guanzongroup.com.itinerary.R;
import org.guanzongroup.com.itinerary.ViewModel.VMItinerary;
import org.rmj.g3appdriver.GRider.Database.Repositories.RItinerary;
import org.rmj.g3appdriver.GRider.Etc.LoadDialog;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;

import java.util.Objects;

public class Activity_ItineraryEntry extends AppCompatActivity {
    private static final String TAG = Activity_ItineraryEntry.class.getSimpleName();

    private VMItinerary mViewModel;
    private LoadDialog poLoad;
    private MessageBox poDialog;

    private Toolbar toolbar;
    private TextInputEditText txtStrt, txtEndx, txtRmrk;
    private AutoCompleteTextView txtLoct;

    public boolean isClicked = false; //use public variable for button click validation to avoid double tap/click...

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ItineraryEntry.this).get(VMItinerary.class);
        setContentView(R.layout.activity_itinerary_entry);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Employee Itinerary");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txtStrt = findViewById(R.id.txt_startTime);
        txtEndx = findViewById(R.id.txt_endTime);
        txtLoct = findViewById(R.id.txt_location);
        txtRmrk = findViewById(R.id.txt_purpose);

        poLoad = new LoadDialog(Activity_ItineraryEntry.this);
        poDialog = new MessageBox(Activity_ItineraryEntry.this);

        findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            if(!isClicked) {
                isClicked = true;
                RItinerary.Itinerary loDetail = new RItinerary.Itinerary();
                loDetail.setTimeStrt(Objects.requireNonNull(txtStrt.getText()).toString().trim());
                loDetail.setTimeEndx(Objects.requireNonNull(txtEndx.getText()).toString().trim());
                loDetail.setLocation(txtLoct.getText().toString().trim());
                loDetail.setRemarksx(Objects.requireNonNull(txtRmrk.getText()).toString().trim());
                isClicked = true;
                mViewModel.SaveItinerary(loDetail, new VMItinerary.OnActionCallback() {
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
                            isClicked = false;
                        });
                    }

                    @Override
                    public void OnFailed(String message) {
                        poLoad.dismiss();
                        poDialog.initDialog();
                        poDialog.setTitle("Employee Itinerary");
                        poDialog.setMessage(message);
                        poDialog.setPositiveButton("Okay", (view, dialog) -> {
                            dialog.dismiss();
                            isClicked = false;
                        });
                    }
                });
            } else {
                Toast.makeText(Activity_ItineraryEntry.this, "Please wait...", Toast.LENGTH_SHORT).show();
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
}