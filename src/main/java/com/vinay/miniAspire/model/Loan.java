package com.vinay.miniAspire.model;

import com.vinay.miniAspire.util.LoanStatus;

import java.util.ArrayList;
import java.util.List;

public class Loan {
    private String id;
    private double loanAmount;
    private int tenure;
    private LoanStatus status = LoanStatus.PENDING;
    private String customerId;
    private List<ScheduledRepayment> scheduledRepayments = new ArrayList<>();


    public List<ScheduledRepayment> getScheduledRepayments() {
        return scheduledRepayments;
    }

    public void setScheduledRepayments(List<ScheduledRepayment> scheduledRepayments) {
        this.scheduledRepayments = scheduledRepayments;
    }

    public void addScheduledRepayments(ScheduledRepayment scheduledRepayment){
        this.scheduledRepayments.add(scheduledRepayment);
    }

    public Loan(double loanAmount, int tenure) {
        this.loanAmount = loanAmount;
        this.tenure = tenure;
        //this.status = LoanStatus.PENDING;
        //this.scheduledRepayments = new ArrayList<>();
    }

    public Loan(){}

    public Loan(String id, double loanAmount, int tenure, LoanStatus status, String customerId, List<ScheduledRepayment> scheduledRepayments) {
        this.id = id;
        this.loanAmount = loanAmount;
        this.tenure = tenure;
        this.status = status;
        this.customerId = customerId;
        this.scheduledRepayments = scheduledRepayments;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getTenure() {
        return tenure;
    }

    public void setTenure(int tenure) {
        this.tenure = tenure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
