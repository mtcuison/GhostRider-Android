/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.Apps.Inventory;

public class RandomItem {
    private final String TransNox;
    private final String PartIDxx;
    private final String BarCodex;
    private int ItemQtyx = 0;
    private String Remarksx = "";

    public RandomItem(String transNox, String partIDxx, String barCodex) {
        TransNox = transNox;
        PartIDxx = partIDxx;
        BarCodex = barCodex;
    }

    public String getTransNox() {
        return TransNox;
    }

    public String getPartIDxx() {
        return PartIDxx;
    }

    public String getBarCodex() {
        return BarCodex;
    }

    public String getItemQtyx() {
        return String.valueOf(ItemQtyx);
    }

    public void setItemQtyx(int itemQtyx) {
        ItemQtyx = itemQtyx;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }


}