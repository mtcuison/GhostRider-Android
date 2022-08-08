package org.guanzongroup.com.itinerary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import org.guanzongroup.com.itinerary.ViewModel.VMItinerary;

public class Activity_ItineraryLog extends AppCompatActivity {
    private static final String TAG = Activity_ItineraryLog.class.getSimpleName();

    private VMItinerary mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ItineraryLog.this).get(VMItinerary.class);
        setContentView(R.layout.activity_itinerary_log);
    }
}