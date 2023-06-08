package org.rmj.guanzongroup.ganado.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import org.rmj.g3appdriver.etc.LoadDialog;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ganado.Adapter.InquiryListAdapter;
import org.rmj.guanzongroup.ganado.Adapter.RecyclerViewAdapter_BrandSelection;
import org.rmj.guanzongroup.ganado.R;
import org.rmj.guanzongroup.ganado.ViewModel.VMBrandList;
import org.rmj.guanzongroup.ganado.ViewModel.VMInquiry;

import java.util.Objects;

public class Activity_Inquiries extends AppCompatActivity {

    private VMInquiry mViewModel;
    private RecyclerView rvInquiries;
    private InquiryListAdapter inquiryList;
    private LoadDialog poLoad;
    private MessageBox poMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inquiries);
        intWidgets();
        mViewModel = new ViewModelProvider(this).get(VMInquiry.class);
        mViewModel.GetInquiries().observe(Activity_Inquiries.this, inquiries ->{
            if (inquiries.size() > 0){
                InquiryListAdapter adapter= new InquiryListAdapter(getApplication(), inquiries, new InquiryListAdapter.OnModelClickListener() {
                    @Override
                    public void OnClick(String TransNox) {
                        Intent intent = new Intent(Activity_Inquiries.this, Activity_ProductSelection.class);
                        intent.putExtra("TransNox",TransNox);
                        startActivity(intent);

                    }

                });

                rvInquiries.setAdapter(adapter);
                rvInquiries.setLayoutManager(new LinearLayoutManager(Activity_Inquiries.this,RecyclerView.VERTICAL,false));

            }
        });
    }
    private void intWidgets(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar_Inquiries);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        rvInquiries = findViewById(R.id.rvInquiries);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}