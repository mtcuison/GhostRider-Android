package org.rmj.guanzongroup.ganado.Model;

public class InquiryInfo {

    public int getColorType() {
        return ColorType;
    }

    public void setColorType(int colorType) {
        ColorType = colorType;
    }

    public int getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(int paymentType) {
        PaymentType = paymentType;
    }

    private int ColorType = 0;
    private int PaymentType = 0;
    private String sBrandIDxx = "";
    private String sModelIDxx = "";
    private double sDownPaymt = 0;
    private int sAccTermxx = 0;
    private double sMonthlyAm = 0;

    private String message;

    public InquiryInfo() {

    }

    public String getMessage() {
        return message;
    }


    public String getBrandIDxx() {
        return sBrandIDxx;
    }

    public void setBrandIDxx(String sBrandIDxx) {
        this.sBrandIDxx = sBrandIDxx;
    }

    public String getModelIDxx() {
        return sModelIDxx;
    }

    public void setModelIDxx(String sModelIDxx) {
        this.sModelIDxx = sModelIDxx;
    }

    public double getDownPaymt() {
        return sDownPaymt;
    }

    public void setDownPaymt(double sDownPaymt) {
        this.sDownPaymt = sDownPaymt;
    }

    public int getAccTermxx() {
        return sAccTermxx;
    }

    public void setAccTermxx(int args) {
        switch (args) {
            case 0:
                sAccTermxx = 36;
                break;
            case 1:
                sAccTermxx = 24;
                break;
            case 2:
                sAccTermxx = 18;
                break;
            case 3:
                sAccTermxx = 12;
                break;
            case 4:
                sAccTermxx = 6;
                break;
        }
    }

    public double getMonthlyAm() {
        return sMonthlyAm;
    }

    public void setMonthlyAm(double sMonthlyAm) {
        this.sMonthlyAm = sMonthlyAm;
    }

    public boolean isDataValid(){
        if(ColorType < 1){
            message = "Please select color type";
            return false;
        }

        if(PaymentType < 1){
            message = "Please select payment";
            return false;
        }

        if(sBrandIDxx.trim().isEmpty()){
            message = "Please select motorcycle brand";
            return false;
        }
        if(sModelIDxx.trim().isEmpty()){
            message = "Please select mc model";
            return false;
        }
        if(sDownPaymt == 0){
            message = "Please enter preferred downpayment";
            return false;
        }
        if(sAccTermxx == 0){
            message = "Please select installment term";
            return false;
        }
        return true;
    }
}
