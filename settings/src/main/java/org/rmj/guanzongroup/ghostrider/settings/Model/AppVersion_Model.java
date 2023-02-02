package org.rmj.guanzongroup.ghostrider.settings.Model;

public class AppVersion_Model {
    public String category;
    public String moduleName;
    public int index;
    public int iconImg;
    public boolean hasChildren, isGroup;

    public AppVersion_Model(String category, String moduleName, int index, int iconImg, boolean isGroup, boolean hasChildren) {
        this.category = category;
        this.moduleName = moduleName;
        this.index = index;
        this.iconImg = iconImg;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
