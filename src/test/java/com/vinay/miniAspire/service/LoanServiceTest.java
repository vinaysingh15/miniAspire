package com.vinay.miniAspire.service;

import com.vinay.miniAspire.model.Loan;
import com.vinay.miniAspire.util.LoanStatus;
import com.vinay.miniAspire.util.LoanUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

public class LoanServiceTest {

    @Mock
    private LoanUtil loanUtil;

    @InjectMocks
    private LoanService loanService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateLoan() {
        // Test data
        Loan loan = new Loan();
        loan.setLoanAmount(10000);
        loan.setTenure(3);

        //when(loanUtil.saveLoanToFile(any(Loan.class))).thenReturn(loan);

        Loan result = loanService.createLoan(loan, "1", "1");

        // Assert that the loan is saved with PENDING status
        assertEquals(LoanStatus.PENDING, result.getStatus());
    }
}


