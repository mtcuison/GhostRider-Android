package org.rmj.guanzongroup.ghostrider.notifications.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.appbar.MaterialToolbar;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DMessages;
import org.rmj.guanzongroup.ghostrider.notifications.Adapter.AdapterUserMessages;
import org.rmj.guanzongroup.ghostrider.notifications.R;
import org.rmj.guanzongroup.ghostrider.notifications.ViewModel.VMUserMessages;

import java.util.List;

public class Activity_UserMessages extends AppCompatActivity {
    private static final String TAG = Activity_UserMessages.class.getSimpleName();

    private VMUserMessages mViewModel;

    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMUserMessages.class);
        setContentView(R.layout.activity_user_messages);
        initWidgets();
    }

    private void initWidgets(){
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerview);

        if(!getIntent().hasExtra("sUserIDxx")){
            return;
        }

        String lsUserID = getIntent().getStringExtra("sUserIDxx");
        mViewModel.GetSenderName(lsUserID).observe(Activity_UserMessages.this, new Observer<String>() {
            @Override
            public void onChanged(String username) {
                try{
                    if(username == null){
                        return;
                    }

                    toolbar.setTitle(username);
                    setSupportActionBar(toolbar);
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        mViewModel.GetUserMessages(lsUserID).observe(Activity_UserMessages.this, new Observer<List<DMessages.UserMessages>>() {
            @Override
            public void onChanged(List<DMessages.UserMessages> messages) {
                try{
                    if(messages == null){
                        return;
                    }

                    if(messages.size() == 0){
                        return;
                    }

                    AdapterUserMessages loAdapter = new AdapterUserMessages(messages);
                    LinearLayoutManager loManager = new LinearLayoutManager(Activity_UserMessages.this);
                    loManager.setOrientation(RecyclerView.VERTICAL);
                    loManager.setStackFromEnd(true);
                    recyclerView.setLayoutManager(loManager);
                    recyclerView.setAdapter(loAdapter);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_intent_slide_in_left, R.anim.anim_intent_slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}