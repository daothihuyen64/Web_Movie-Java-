package com.webxemphim.demo.dto;

import java.sql.Date;

public class TransactionHistoryDTO {
    private String packageName;
    private Date startDate;
    private Date endDate;
    
    public TransactionHistoryDTO(String packageName, Date startDate, Date endDate) {
        this.packageName = packageName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
