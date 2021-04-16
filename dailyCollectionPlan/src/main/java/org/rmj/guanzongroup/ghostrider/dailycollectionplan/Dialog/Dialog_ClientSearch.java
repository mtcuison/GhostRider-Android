package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Adapter.AdapterClientSearchList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.util.List;

public class Dialog_ClientSearch {

    private AlertDialog poDialogx;
    private final Context context;

    public interface OnClientSelectListener{
        void OnSelect(EDCPCollectionDetail detail);
    }

    public Dialog_ClientSearch(Context context) {
        this.context = context;
    }

    public void initDialog(List<EDCPCollectionDetail> collectionDetails, OnClientSelectListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_client_search_list, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_clientSearchList);
        AdapterClientSearchList loAdapter = new AdapterClientSearchList(collectionDetails, listener::OnSelect);
        LinearLayoutManager loManage = new LinearLayoutManager(context);
        loManage.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(loAdapter);
        recyclerView.setLayoutManager(loManage);
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }
}
