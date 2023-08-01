package com.vinay.miniAspire.controller;

import com.vinay.miniAspire.model.Loan;
import com.vinay.miniAspire.model.ScheduledRepayment;
import com.vinay.miniAspire.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/miniAspire", produces = "application/json")
public class mainController {

    @Autowired
    LoanService loanService;

    @PostMapping("customer/{customerId}/loan/{loanId}")
    public ResponseEntity createLoan(@PathVariable("customerId")String customerId ,
                                     @PathVariable("loanId")String loanId, @RequestBody Loan loan) {
        boolean created = loanService.createLoan(loan, customerId, loanId);
        if(created)
            return new ResponseEntity<>(loan, HttpStatus.CREATED);
        else return new ResponseEntity<>("Error while creating loan", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/customer/{customerId}/loans")
    public ResponseEntity getCustomerLoans(@PathVariable("customerId") String customerId) {
        List<Loan> loanList = loanService.getCustomerLoans(customerId);
        return new ResponseEntity<>(loanList, HttpStatus.OK);
    }

    @PutMapping("loan/{loanId}/approve")
    public ResponseEntity approveLoan(@PathVariable("loanId")String loanId) {
        Loan loan = loanService.approveLoan(loanId);
        if(loan == null)
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(loan, HttpStatus.OK);
    }

    @PutMapping("/loan/{loanId}/repayment")
    public ResponseEntity addRepayment(@PathVariable("loanId") String loadId, @RequestParam double repaymentAmount) {
        ScheduledRepayment scheduledRepayment = loanService.addRepayment(loadId, repaymentAmount);
        if(scheduledRepayment == null)
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        else return new ResponseEntity<>(scheduledRepayment, HttpStatus.OK);
    }
}
