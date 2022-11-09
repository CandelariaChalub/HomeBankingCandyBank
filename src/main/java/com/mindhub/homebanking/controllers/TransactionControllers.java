package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.PDFService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.DTO.ClientDTO;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")/*asociar una peticion*/
public class TransactionControllers {

    @Autowired
    AccountService accountService;
    @Autowired
    ClientService clientService;
    @Autowired
    TransactionService transactionService;
    @Autowired
    PDFService pdfService;

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> createTransaction(@RequestParam double amount, @RequestParam String description, @RequestParam String sourceNumber, @RequestParam String destinationNumber, Authentication authentication){
        if (description.isEmpty() || sourceNumber.isEmpty() || destinationNumber.isEmpty() || amount == 0.0) {
            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
        } else {
            if (!sourceNumber.equals(destinationNumber)){
                Account sourceAccount = accountService.getAccountByNumber(sourceNumber);
                if (sourceAccount != null){
                    Client client = clientService.getClientByEmail(authentication.getName());
                    if (client != null){
                        if (client.getAccounts().contains(sourceAccount)){
                            Account destinationAccount = accountService.getAccountByNumber(destinationNumber);
                            if (destinationAccount != null){
                                if (sourceAccount.getBalance() >= amount){
                                    Transaction transaction1 = new Transaction(description + " transferencia a " + destinationAccount.getClient().getName() + " " + destinationAccount.getClient().getLastName() , amount,  LocalDateTime.now(),  TransactionType.DEBIT, sourceAccount);
                                    Transaction transaction2 = new Transaction(description + " transferencia de " + sourceAccount.getClient().getName() + " " + sourceAccount.getClient().getLastName() , amount,  LocalDateTime.now(),  TransactionType.CREDIT, destinationAccount);
                                    transactionService.saveTransaction(transaction1);
                                    transactionService.saveTransaction(transaction2);
                                    sourceAccount.setBalance(sourceAccount.getBalance() - amount);
                                    destinationAccount.setBalance(destinationAccount.getBalance() + amount);
                                    return new ResponseEntity<>(HttpStatus.CREATED);
                                } else {
                                    return new ResponseEntity<>("Insufficient amount", HttpStatus.FORBIDDEN);
                                }
                            } else {
                                return new ResponseEntity<>("Destination account not valid", HttpStatus.FORBIDDEN);
                            }
                        } else {
                            return new ResponseEntity<>("The account isn't to the client", HttpStatus.FORBIDDEN);
                        }
                    } else {
                        return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
                    }
                } else {
                    return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
                }
            } else {
                return new ResponseEntity<>("Same account", HttpStatus.FORBIDDEN);
            }
        }
    }

    @GetMapping (path = "/transactions/{destinationNumber}")
    public ClientDTO getClient(@PathVariable String destinationNumber){
        Client client = clientService.getClientByAccountsNumber(destinationNumber);
        Client client1 = new Client(client.getName(), client.getLastName(), client.getEmail());
        ClientDTO clientDTO = new ClientDTO(client1);
        return clientDTO;
    }



    @GetMapping("/transactions/current")
    public ResponseEntity<Object> getTransactionsCurrent(HttpServletResponse response, Authentication authentication, @RequestParam String accountNumber, @RequestParam(required = false) String start, @RequestParam(required = false) String end){
        Client client = clientService.getClientByEmail(authentication.getName());
        Account account = accountService.getAccountByNumber(accountNumber);
        List<Transaction> transactions;
        if (client.getAccounts().contains(account)){
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=CandyBankresume.pdf";
            response.setHeader(headerKey, headerValue);
            if (!(start.isEmpty() || end.isEmpty())){
                LocalDateTime startDate = LocalDateTime.parse(start);
                LocalDateTime endDate = LocalDateTime.parse(end);
                transactions = transactionService.getAllTransactionsByAccountAndDate(account,startDate,endDate);

            } else {
                transactions = transactionService.getAllByAccount(account);
            }
            pdfService.generatePDF(response, transactions, account);
            return  new ResponseEntity<>("", HttpStatus.ACCEPTED);
        } else {
            return null;
        }
    }


}
