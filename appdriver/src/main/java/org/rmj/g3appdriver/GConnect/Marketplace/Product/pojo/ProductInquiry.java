package org.rmj.g3appdriver.GConnect.Marketplace.Product.pojo;

public class ProductInquiry {

    private String sUserName;
    private String dCreatedx;
    private String sQuestion;
    private String sReplyxxx;

    public ProductInquiry(String sUserName, String dCreatedx, String sQuestion, String sReplyxxx) {
        this.sUserName = sUserName;
        this.dCreatedx = dCreatedx;
        this.sQuestion = sQuestion;
        this.sReplyxxx = sReplyxxx;
    }

    public String getUserName() {
        return sUserName;
    }

    public String getDateCreated() {
        return dCreatedx;
    }

    public String getQuestion() {
        return sQuestion;
    }

    public String getReply() {
        return sReplyxxx;
    }
}


