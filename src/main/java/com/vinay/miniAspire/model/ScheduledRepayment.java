package com.vinay.miniAspire.model;

import com.vinay.miniAspire.util.LoanStatus;
import com.vinay.miniAspire.util.RepaymentStatus;

import java.time.LocalDate;

public class ScheduledRepayment {
    private String id;
    private String loanId;
    private LocalDate dueDate;
    private double amount;
    private RepaymentStatus status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public RepaymentStatus getStatus() {
        return status;
    }

    public void setStatus(RepaymentStatus status) {
        this.status = status;
    }
}