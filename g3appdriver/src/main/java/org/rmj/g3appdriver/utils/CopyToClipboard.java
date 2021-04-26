/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

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
