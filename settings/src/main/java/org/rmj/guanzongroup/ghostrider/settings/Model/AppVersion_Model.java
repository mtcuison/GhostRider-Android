package org.rmj.guanzongroup.ghostrider.settings.Model;

public class AppVersion_Model {
    public String moduleName;
    public int iconImg;
    public boolean hasChildren, isGroup;

    public AppVersion_Model(String moduleName, int iconImg, boolean isGroup, boolean hasChildren) {
        this.moduleName = moduleName;
        this.iconImg = iconImg;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
