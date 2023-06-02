package org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo;

public class OrderReview {
    private String OrderID = "";
    private String ListID = "";
    private int Rate = 0;
    private String Remarks = "";

    private String message;

    public OrderReview(String orderID, String listID, int rate, String remarks) {
        OrderID = orderID;
        ListID = listID;
        Rate = rate;
        Remarks = remarks;
    }

    public String getMessage() {
        return message;
    }

    public String getOrderID() {
        return OrderID;
    }

    public String getListID() {
        return ListID;
    }

    public int getRate() {
        return Rate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public boolean isDataValid(){
        if(OrderID.isEmpty()){
            message = "No order id detected.";
            return false;
        }

        if(ListID.isEmpty()){
            message = "No listing id detected.";
            return false;
        }

        if(Rate == 0){
            message = "Please rate your order.";
            return false;
        }

        if(Remarks.isEmpty()){
            message = "Please provide some remarks.";
            return false;
        }

        return true;
    }
}
