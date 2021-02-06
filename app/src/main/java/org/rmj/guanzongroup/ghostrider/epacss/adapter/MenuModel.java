package org.rmj.guanzongroup.ghostrider.epacss.adapter;

/**
 * Created by anupamchugh on 22/12/17.
 */

public class MenuModel {

    public String menuName, url;
    public int iconImg = -1; // menu icon resource id
    public boolean hasChildren, isGroup;

    public MenuModel(String menuName, int iconImg, boolean isGroup, boolean hasChildren) {

        this.menuName = menuName;
        //this.url = url;
        this.iconImg = iconImg;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
