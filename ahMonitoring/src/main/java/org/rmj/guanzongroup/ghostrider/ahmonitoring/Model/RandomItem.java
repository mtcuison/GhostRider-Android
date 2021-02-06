package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

public class RandomItem {
    private final String TransNox;
    private final String ItemCode;
    private final String ItemDesc;

    public RandomItem(String transNox, String itemCode, String itemDesc) {
        TransNox = transNox;
        ItemCode = itemCode;
        ItemDesc = itemDesc;
    }

    public String getTransNox() {
        return TransNox;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public String getItemDesc() {
        return ItemDesc;
    }
}
