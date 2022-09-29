/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 3:39 PM
 * project file last modified : 6/9/21 3:39 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Etc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.lib.integsys.CashCount.QuickSearchNames;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.Adapter_RequestNames;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RequestNamesInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class DialogKwikSearch {
    Context mContex;
    private AlertDialog poDialogx;
    private RequestNamesInfoModel infoModel;
    private String approval = "";
    private RecyclerView recyclerView;
    private ImageButton btnClose;
    private Adapter_RequestNames custAdapter;
    private List<QuickSearchNames> infoList;
    public DialogKwikSearch(Context context, List<QuickSearchNames> infoList){
        this.mContex = context;
        this.infoList = infoList;
    }

    public void initDialogKwikSearch(DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(mContex);
        View view = LayoutInflater.from(mContex).inflate(R.layout.dialog_kwik_search, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        recyclerView = view.findViewById(R.id.rclrvw_requestNames);
        btnClose = view.findViewById(R.id.btn_dialogClose);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContex);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        custAdapter = new Adapter_RequestNames(infoList);
        custAdapter.setOnItemClickListener(position -> listener.OnClick(poDialogx,infoList.get(position).getRequestName(), infoList.get(position).getRequestIDxx()));
        recyclerView.setAdapter(custAdapter);
        recyclerView.setLayoutManager(layoutManager);

        btnClose.setOnClickListener(v -> dismiss());


        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);
        infoModel = new RequestNamesInfoModel();

    }


    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }

    public interface DialogButtonClickListener{
        void OnClick(Dialog dialog, String name, String emploID);
    }

}
