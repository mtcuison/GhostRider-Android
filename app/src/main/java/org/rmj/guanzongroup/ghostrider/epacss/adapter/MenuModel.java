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
}
