package com.vinay.miniAspire.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.vinay.miniAspire.model.Loan;
import com.vinay.miniAspire.model.ScheduledRepayment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonParser.Feature;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.Integer.parseInt;

@Component
public class LoanUtil {

    private String LOANS_FOLDER;
    private static final String LOAN_FILE_EXTENSION = "-loanFile.json";

    private ObjectMapper objectMapper;
    public LoanUtil(@Value("${fileAbsolutePath}") String fileFolder) {
        this.LOANS_FOLDER = fileFolder;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
    }

    public boolean saveLoanToFile(Loan loan) {
        File file = new File(getLoanFilePath(loan.getId()));
        try {
            objectMapper.writeValue(file, loan);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void saveScheduledRepaymentToFile(ScheduledRepayment scheduledRepayment) {
        File file = new File(getScheduledRepaymentFilePath(scheduledRepayment.getId()));
        try {
            objectMapper.writeValue(file, scheduledRepayment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = new ArrayList<>();
        File loansFolder = new File(LOANS_FOLDER);
        File[] loanFiles = loansFolder.listFiles();
        if (loanFiles != null) {
            for (File loanFile : loanFiles) {
                try {
                    Loan loan = objectMapper.readValue(loanFile, Loan.class);
                    loans.add(loan);
                } catch (IOException e) {
                    //e.printStackTrace();
                }
            }
        }
        return loans;
    }

    public Optional<Loan> findLoanById(String loanId) {
        List<Loan> loans = getAllLoans();
        return loans.stream()
                .filter(loan -> loan.getId().equals(loanId))
                .findFirst();
    }

    public ScheduledRepayment findNextPendingRepayment(Loan loan) {
        return loan.getScheduledRepayments().stream()
                .filter(repayment -> repayment.getStatus().equals(RepaymentStatus.PENDING))
                .findFirst()
                .orElse(null);
    }

    public void checkAndUpdateLoanStatus(Loan loan) {
        boolean allRepaymentsPaid = loan.getScheduledRepayments().stream()
                .allMatch(repayment -> repayment.getStatus().equals(RepaymentStatus.PAID));

        if (allRepaymentsPaid) {
            loan.setStatus(LoanStatus.PAID);
            saveLoanToFile(loan);
        }
    }

    public void generateScheduledRepayments(Loan loan) {
        double weeklyAmount = loan.getLoanAmount() / loan.getTenure();
        LocalDate dueDate = LocalDate.now().plusWeeks(1);

        for (int i = 1; i <= loan.getTenure(); i++) {
            ScheduledRepayment scheduledRepayment = new ScheduledRepayment();
            scheduledRepayment.setId(String.valueOf(parseInt(loan.getId()) * 100 + i));
            scheduledRepayment.setLoanId(loan.getId());
            scheduledRepayment.setDueDate(dueDate);
            scheduledRepayment.setAmount(weeklyAmount);
            scheduledRepayment.setStatus(RepaymentStatus.PENDING);

            loan.addScheduledRepayments(scheduledRepayment);
            dueDate = dueDate.plusWeeks(1);
        }

        saveLoanToFile(loan);
    }

    private String getLoanFilePath(String loanId) {
        return LOANS_FOLDER + "/" + loanId + LOAN_FILE_EXTENSION;
    }

    private String getScheduledRepaymentFilePath(String repaymentId) {
        return LOANS_FOLDER + "/" + repaymentId + LOAN_FILE_EXTENSION;
    }
}
