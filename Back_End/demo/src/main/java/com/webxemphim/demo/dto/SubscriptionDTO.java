package com.webxemphim.demo.dto;

public class SubscriptionDTO {
    private int userId;
    private int packageId;
    private boolean paymentSuccess;
    
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getPackageId() {
        return packageId;
    }
    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }
    public boolean isPaymentSuccess() {
        return paymentSuccess;
    }
    public void setPaymentSuccess(boolean paymentSuccess) {
        this.paymentSuccess = paymentSuccess;
    }

    
}
