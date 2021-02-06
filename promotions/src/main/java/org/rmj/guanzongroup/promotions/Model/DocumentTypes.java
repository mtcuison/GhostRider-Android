package org.rmj.guanzongroup.promotions.Model;

public class DocumentTypes {

    private String Reference;
    private String Descriptx;

    public DocumentTypes(String reference, String descriptx) {
        Reference = reference;
        Descriptx = descriptx;
    }

    public String getReference() {
        return Reference;
    }

    public String getDescriptx() {
        return Descriptx;
    }
}
