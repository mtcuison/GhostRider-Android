package org.rmj.g3appdriver.GRider.Etc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.rmj.g3appdriver.R;

public class GToast {

    private final Toast poToast;
    private final Context context;
    private static String psMessage;
    private static int pnType;

    @SuppressLint("StaticFieldLeak")
    private static GToast poGToast;

    public static GToast CreateMessage(Context context, String fsMessage, int fnType){
        if(poGToast == null){
            poGToast = new GToast(context);
        }
        psMessage = fsMessage;
        pnType = fnType;
        return poGToast;
    }

    private GToast (Context context) {
        this.context = context;
        this.poToast = new Toast(context);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initToast(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null, false);

        TextView lblMessage = view.findViewById(R.id.lbl_toast_message);
        ImageView imgIconxx = view.findViewById(R.id.img_toast_icon);
        lblMessage.setText(psMessage);
        imgIconxx.setImageDrawable(context.getResources().getDrawable(pnType));
        poToast.setView(view);
        poToast.setDuration(Toast.LENGTH_LONG);
        poToast.setGravity(Gravity.CENTER, 0, 0);
    }

    public void show(){
        initToast();
        poToast.show();
    }

    public static int INFORMATION = R.drawable.ic_toast_info;
    public static int WARNING = R.drawable.ic_toast_warning;
    public static int ERROR = R.drawable.ic_toast_error;
}
