package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.Services.TransactionService;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.repositories.DTO.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")

public class ClientControllers {

    @Autowired //Generamos una instancia del repositorio, inyeccion de dependencia//
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;
    @GetMapping("/clients") //peticion asociada a una ruta//
    public List<ClientDTO> getClients(){
    return clientService.getAllClients().stream().map(ClientDTO::new).collect(Collectors.toList());
    }



    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id){
        return new ClientDTO(clientService.getClientById(id));
    }

    @PostMapping("/clients")

    public ResponseEntity<Object> register(

            @RequestParam String name, @RequestParam String lastName,

            @RequestParam String email, @RequestParam String password) {
        int random = getRandomNumber(0, 99999999);


        if (name.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty() ) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        if (clientService.getClientByEmail(email) !=  null) {
            return new ResponseEntity<>("email already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(name, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        accountRepository.save(new Account("VIN" + random, AccountType.SAVING, LocalDateTime.now(), 00.00, client ));
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @GetMapping ("/clients/current")
    public ClientDTO  getAll(Authentication authentication) {

        Client client = clientService.getClientByEmail(authentication.getName());
        return new ClientDTO(client);
    }


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
