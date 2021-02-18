package org.rmj.g3appdriver.GRider.Etc;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.Circle;

import org.rmj.g3appdriver.R;

public class LoadDialog {
    private AlertDialog poDialogx;
    private final Context context;

    public LoadDialog(Context context) {
        this.context = context;
    }

    public void initDialog(String Title, String Message, boolean Cancellable){
        AlertDialog.Builder poBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_progress, null);
        poBuilder.setCancelable(false)
                .setView(view);
        poDialogx = poBuilder.create();
        poDialogx.setCancelable(Cancellable);
        ProgressBar progressBar = view.findViewById(R.id.progress_loading);
        TextView lblTitle = view.findViewById(R.id.lbl_dialogTitle);
        lblTitle.setText(Title);
        TextView lblMsgxx = view.findViewById(R.id.lbl_dialogMessage);
        lblMsgxx.setText(Message);
        Sprite loDrawable = new Circle();
        loDrawable.setColor(context.getResources().getColor(R.color.progressDialogTint));
        progressBar.setIndeterminateDrawable(loDrawable);
    }

    public void show() {
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.getWindow().getAttributes().windowAnimations = R.style.PopupAnimation;
        poDialogx.show();
    }

    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }
}

