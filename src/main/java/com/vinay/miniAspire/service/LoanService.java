package com.vinay.miniAspire.service;
import com.vinay.miniAspire.model.Loan;
import com.vinay.miniAspire.model.ScheduledRepayment;
import com.vinay.miniAspire.util.LoanUtil;
import com.vinay.miniAspire.util.LoanStatus;
import com.vinay.miniAspire.util.RepaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LoanService {

    @Autowired
    LoanUtil loanUtil;

    public boolean createLoan(Loan loan, String customerId, String loanId) {
        loan.setId(loanId);
        loan.setCustomerId(customerId);
        boolean saved = false;
        //check if loan already exists
        Optional<Loan> loan1 = loanUtil.findLoanById(loanId);
        if(loan1.isPresent()) {
            return saved;
        }
        saved = loanUtil.saveLoanToFile(loan);
        loanUtil.generateScheduledRepayments(loan);
        return saved;
    }

    public Loan approveLoan(String loanId) {
        Optional<Loan> optionalLoan = loanUtil.findLoanById(loanId);
        //check if already approved
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            loan.setStatus(LoanStatus.APPROVED);
            loanUtil.saveLoanToFile(loan);
            return loan;
        }
        return null;
    }

    public List<Loan> getCustomerLoans(String customerId) {
        List<Loan> loans = loanUtil.getAllLoans();
        return loans.stream()
                .filter(loan -> loan.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }

    public ScheduledRepayment addRepayment(String loanId, double repaymentAmount) {
        Optional<Loan> optionalLoan = loanUtil.findLoanById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            ScheduledRepayment scheduledRepayment = loanUtil.findNextPendingRepayment(loan);
            if (scheduledRepayment != null && repaymentAmount >= scheduledRepayment.getAmount()) {
                scheduledRepayment.setStatus(RepaymentStatus.PAID);
                loanUtil.saveLoanToFile(loan);
                //loanUtil.saveScheduledRepaymentToFile(scheduledRepayment);
                loanUtil.checkAndUpdateLoanStatus(loan);
                return scheduledRepayment;
            }
        }
        return null;
    }
}
