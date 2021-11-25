package org.rmj.guanzongroup.ghostrider.ahmonitoring.Dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adaper.AdapterInventoryBranch;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;

import java.util.List;

public class DialogBranchSelection {
    private static final String TAG = DialogBranchSelection.class.getSimpleName();

    private final Context mContext;
    private final List<EBranchInfo> paBranchLst;
    private OnBranchSelectedCallback callback;

    private MessageBox poMessage;

    private AlertDialog poDialogx;

    public interface OnBranchSelectedCallback{
        void OnSelect(String BranchCode);
    }

    public DialogBranchSelection(Context context, List<EBranchInfo> branchList) {
        this.mContext = context;
        this.paBranchLst = branchList;
        this.poMessage = new MessageBox(mContext);
    }

    public void initDialog(OnBranchSelectedCallback foCallback){
        callback = foCallback;

        AlertDialog.Builder poBuilder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_inventory_branch_selection, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialogx = poBuilder.create();
        poDialogx.setCancelable(false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_branch);
        LinearLayoutManager loManager = new LinearLayoutManager(mContext);
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setAdapter(new AdapterInventoryBranch(paBranchLst, (BranchCode, BranchName) -> {
            try{
                poMessage.initDialog();
                poMessage.setTitle("Branch Selected");
                poMessage.setMessage("Select " + BranchName + " for random stock inventory?");
                poMessage.setPositiveButton("Yes", (view12, dialog) -> {
                    callback.OnSelect(BranchCode);
                    dialog.dismiss();
                    poDialogx.dismiss();
                });
                poMessage.setNegativeButton("No", (view1, dialog) -> dialog.dismiss());
                poMessage.show();
            } catch (Exception e){
                e.printStackTrace();
            }
        }));
        recyclerView.setLayoutManager(loManager);

        if(!poDialogx.isShowing()) {
            poDialogx.show();
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
        }
    }
}
