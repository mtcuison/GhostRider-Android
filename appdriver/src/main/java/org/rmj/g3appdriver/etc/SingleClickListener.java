/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/3/21 1:30 PM
 * project file last modified : 6/3/21 1:30 PM
 */

package org.rmj.g3appdriver.etc;

import android.os.SystemClock;
import android.view.View;

public abstract class SingleClickListener implements View.OnClickListener{
    private static final int INTERVAL = 1000;
    private long pnClick = 0;

    @Override
    public void onClick(View v) {
        if (SystemClock.elapsedRealtime() - pnClick < INTERVAL) {
            return;
        }
        pnClick = SystemClock.elapsedRealtime();
        onSingleClick(v);
    }

    public abstract void onSingleClick(View v);

}
