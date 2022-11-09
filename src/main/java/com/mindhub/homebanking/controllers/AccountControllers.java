package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.Services.AccountService;
import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.DTO.AccountDTO;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.DTO.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController /*js en vez de pagina web, este controlador va a tener las restrictiones de rest solo usar sus metodos post path delete get put*/
@RequestMapping ("/api")
public class AccountControllers {
    @Autowired /*permite inyectar unas dependencias con otras dentro de Spring */
    private AccountService accountService;
    @Autowired
    private ClientService clientService;

    @GetMapping ("/accounts")
    public List<AccountDTO> getAccounts( ){
        return accountService.getAllAccounts().stream().map(AccountDTO::new).collect(Collectors.toList());
    }

    @GetMapping ("/accounts/{id}") /*asociamos una peticion de tipo get*/
    public AccountDTO getAccount (@PathVariable Long id){ /*path viene en la url*/
        return new AccountDTO(accountService.getAccountById(id)); /*que si no encuentra la cuenta por id me retorne nulo*/

    }


    @PostMapping("/clients/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType type) {

        int random = getRandomNumber(11111111, 99999999);
        Client client = clientService.getClientByEmail(authentication.getName());
        ClientDTO clientDTO = new ClientDTO(client);

        if(client == null){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(clientDTO.getAccounts().toArray().length >= 3){
            return new ResponseEntity<>("Max accounts", HttpStatus.FORBIDDEN);
        }

        accountService.saveAccount(new Account("VIN" + random, type, LocalDateTime.now(), 00.00, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PatchMapping("/accounts/delete")
    public ResponseEntity<Object> deleteAccount(Authentication authentication,@RequestParam String accountNumber){
        Client client = clientService.getClientByEmail(authentication.getName());
        Account account = accountService.getAccountByNumber(accountNumber);
        if (client == null){
            return new ResponseEntity<>("Client doesn´t exist", HttpStatus.FORBIDDEN);
        }

        if (account == null){
            return new ResponseEntity<>("Account doesn´t exist", HttpStatus.FORBIDDEN);
        }
        if (account.getBalance() != 0 ){
            return new ResponseEntity<>("The account has balance", HttpStatus.FORBIDDEN);
        }
        account.setActive(false);
        accountService.saveAccount(account);
        return new ResponseEntity<>("", HttpStatus.ACCEPTED);
    }
}
