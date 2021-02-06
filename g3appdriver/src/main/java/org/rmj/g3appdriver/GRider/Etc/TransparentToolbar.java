package org.rmj.g3appdriver.GRider.Etc;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class TransparentToolbar {

    private final Activity poActivity;

    public TransparentToolbar(Activity foActivity) {
        this.poActivity = foActivity;
    }

    public void SetupActionbar(){
        transparentToolbar();
    }

    private void transparentToolbar() {
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(poActivity, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            poActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(poActivity, false);
            poActivity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    private void setWindowFlag(Activity activity, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        } else {
            winParams.flags &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        }
        win.setAttributes(winParams);
    }
}
