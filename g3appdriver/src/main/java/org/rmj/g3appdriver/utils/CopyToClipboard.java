package org.rmj.g3appdriver.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

public class CopyToClipboard {
    private static final String TAG = CopyToClipboard.class.getSimpleName();

    private Context context;

    public CopyToClipboard(Context context){
        this.context = context;
    }

    public void CopyTextClip(String Label, String Content){
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(Label, Content);
        clipboard.setPrimaryClip(clip);
    }
}
