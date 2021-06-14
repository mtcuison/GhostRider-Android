/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.adapter;

/**
 * Created by anupamchugh on 22/12/17.
 */

public class MenuModel {

    public String menuName, url;
    public int iconImg = -1; // menu icon resource id
    public boolean hasChildren, isGroup;
    public int visibility;

    /*Edited 2021/03/12
    * Added new parameter int for View Visibility*/

    /**
     *
     * @param menuName set name of menu item
     * @param iconImg set icon of menu item
     * @param isGroup set if menu item has child content. (True if has child content else if it doesn't)
     * @param hasChildren
     * @param visibility set if menu item must not be shown to user base on its position/department.
     */
    public MenuModel(String menuName, int iconImg, boolean isGroup, boolean hasChildren, int visibility) {

        this.menuName = menuName;
        //this.url = url;
        this.iconImg = iconImg;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
        this.visibility = visibility;
    }

    public void setVisibility(int visibility){
        this.visibility = visibility;
    }
}
