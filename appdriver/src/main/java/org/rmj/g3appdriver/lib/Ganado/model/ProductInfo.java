package org.rmj.g3appdriver.lib.Ganado.model;

public class ProductInfo {

    private String InvTypCd = "";
    private String BrandIDx = "";
    private String ModelIDx = "";
    private String ColorIDx = "";

    public ProductInfo(){}

    private String message;

    public String getInvTypCd() {
        return InvTypCd;
    }

    public String getMessage() {
        return message;
    }
    public void setInvTypCd(String invTypCd) {
        InvTypCd = invTypCd;
    }

    public String getBrandIDx() {
        return BrandIDx;
    }

    public void setBrandIDx(String brandIDx) {
        BrandIDx = brandIDx;
    }

    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(String modelIDx) {
        ModelIDx = modelIDx;
    }

    public String getColorIDx() {
        return ColorIDx;
    }

    public void setColorIDx(String colorIDx) {
        ColorIDx = colorIDx;
    }
    public boolean isProductValid(){


        if(InvTypCd.trim().isEmpty()){
            message = "Please select inventory code.";
            return false;
        }

        if(BrandIDx == null || BrandIDx.equalsIgnoreCase("")){
            message = "Please select brand.";
            return false;
        }

        if (ModelIDx == null || ModelIDx.trim().isEmpty()){
            message = "Please select model";
            return false;
        }
        if (ColorIDx == null || ColorIDx.trim().isEmpty()){
            message = "Please select color";
            return false;
        }

        return true;
    }

}
