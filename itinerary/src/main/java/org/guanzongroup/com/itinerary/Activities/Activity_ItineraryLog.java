package org.guanzongroup.com.itinerary.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.itinerary.Adapter.AdapterItineraries;
import org.guanzongroup.com.itinerary.R;
import org.guanzongroup.com.itinerary.ViewModel.VMItinerary;

import java.util.Objects;

public class Activity_ItineraryLog extends AppCompatActivity {
    private static final String TAG = Activity_ItineraryLog.class.getSimpleName();

    private VMItinerary mViewModel;

    private Toolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(Activity_ItineraryLog.this).get(VMItinerary.class);
        setContentView(R.layout.activity_itinerary_log);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Employee Itinerary");
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager loManager = new LinearLayoutManager(Activity_ItineraryLog.this);
        loManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(loManager);

        mViewModel.GetItineraryEntries().observe(Activity_ItineraryLog.this, eItineraries -> {
            try{
                recyclerView.setAdapter(new AdapterItineraries(eItineraries));
            } catch (Exception e){
                e.printStackTrace();
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