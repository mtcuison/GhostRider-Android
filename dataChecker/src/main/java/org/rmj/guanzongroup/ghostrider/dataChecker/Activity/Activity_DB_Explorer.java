/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dataChecker
 * Electronic Personnel Access Control Security System
 * project file created : 10/16/21, 1:48 PM
 * project file last modified : 10/16/21, 1:34 PM
 */

package org.rmj.guanzongroup.ghostrider.dataChecker.Activity;

import static org.rmj.guanzongroup.ghostrider.dataChecker.ViewModel.VMDBExplorer.PICK_DB_FILE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.dataChecker.Adapter.DCPDataAdapter;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.DCPData;
import org.rmj.guanzongroup.ghostrider.dataChecker.Obj.UserInfo;
import org.rmj.guanzongroup.ghostrider.dataChecker.R;
import org.rmj.guanzongroup.ghostrider.dataChecker.ViewModel.VMDBExplorer;

import java.util.ArrayList;

public class Activity_DB_Explorer extends AppCompatActivity {
    private static final String TAG = Activity_DB_Explorer.class.getSimpleName();

    private VMDBExplorer mViewModel;

    private TextInputEditText txtDataName;
    private MaterialButton btnFind;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_db_explorer);
        mViewModel = new ViewModelProvider(this).get(VMDBExplorer.class);
        setupWidgets();

        btnFind.setOnClickListener(v -> mViewModel.FindDatabase(findDB -> startActivityForResult(findDB, PICK_DB_FILE)));
    }

    private void setupWidgets(){
        txtDataName = findViewById(R.id.txt_dbName);
        btnFind = findViewById(R.id.btn_findDb);
        recyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_DB_FILE && resultCode == RESULT_OK){
            Uri dbData = data.getData();
            mViewModel.ExploreDb(dbData, new VMDBExplorer.ExploreDatabaseCallback() {
                @Override
                public void OnDataOwnerRetrieve(String DataOwner) {
                    txtDataName.setText(DataOwner);
                }

                @Override
                public void OnDCPListRetrieve(ArrayList<DCPData> dcpData) {
                    LinearLayoutManager loManager = new LinearLayoutManager(Activity_DB_Explorer.this);
                    loManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setAdapter(new DCPDataAdapter(dcpData));
                    recyclerView.setLayoutManager(loManager);
                }

                @Override
                public void OnOwnerInfoRetrieve(UserInfo info) {

                }
            });
        }
    }
}