package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.repositories.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.repositories.DTO.LoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping ("/api")
public class LoanController {
    @Autowired
    LoanService loanService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    ClientLoanService clientLoanService;

    @GetMapping ("/loans")
    public List<LoanDTO> getLoans(){
        return loanService.getAllLoan().stream().map(LoanDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @PostMapping ("/loans")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplication){
        if ( loanApplication.getAmount() == 0 || loanApplication.getPayments() == 0){
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        }
        else {
            Loan loan = loanService.getLoanById(loanApplication.getId());
            if (loan != null){
                if (loanApplication.getAmount() <= loan.getMaxAmount()){
                    if (loan.getPayments().contains(loanApplication.getPayments())){
                        Account destinationAccount = accountService.getAccountByNumber(loanApplication.getDestinationNumberAccount());
                        if (destinationAccount != null){
                            Client client = clientService.getClientByEmail(authentication.getName());
                            if (client.getAccounts().contains(destinationAccount)){
                                transactionService.saveTransaction(new Transaction(loan.getName() + " Approved loan",loanApplication.getAmount(),LocalDateTime.now(),TransactionType.CREDIT,destinationAccount));
                                ClientLoan clientLoan = new ClientLoan(loanApplication.getAmount()*(1 + loan.getInterest()), loanApplication.getPayments(), client, loan);
                                clientLoanService.saveLoan(clientLoan);
                                destinationAccount.setBalance(destinationAccount.getBalance() + loanApplication.getAmount());
                                return new ResponseEntity<>("Approved loan", HttpStatus.CREATED);
                            } else {
                                return new ResponseEntity<>("The account does not belong to the client",HttpStatus.FORBIDDEN);
                            }
                        } else {
                            return new ResponseEntity<>("Destination account does not exist", HttpStatus.FORBIDDEN);
                        }
                    }
                    else {
                        return new ResponseEntity<>("Wrong Payment", HttpStatus.FORBIDDEN);
                    }
                }
                else {
                    return new ResponseEntity<>("Excess amount", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }
        }
    }
}
