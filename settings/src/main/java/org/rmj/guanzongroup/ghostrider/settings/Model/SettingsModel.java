/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 7/9/21 1:29 PM
 * project file last modified : 7/9/21 1:29 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.Model;

public class SettingsModel {
    public String moduleName;
    public int iconImg = -1; // menu icon resource id
    public boolean hasChildren, isGroup;
    public int visibility;

    /*Added 2021/07/09
     * Added new parameter int for View Visibility*/

    /**
     *
     * @param moduleName set name of menu item
     * @param iconImg set icon of menu item
     * @param visibility set if menu item must not be shown to user base on its position/department.
     */

    public SettingsModel(String moduleName, int iconImg, boolean isGroup, boolean hasChildren, int visibility) {

        this.moduleName = moduleName;
        this.iconImg = iconImg;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.visibility = visibility;
    }

    public void setVisibility(int visibility){
        this.visibility = visibility;
    }
}
