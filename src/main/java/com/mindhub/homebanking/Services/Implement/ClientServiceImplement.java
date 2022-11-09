package com.mindhub.homebanking.Services.Implement;

import com.mindhub.homebanking.Services.ClientService;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImplement implements ClientService {
    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientByAccountsNumber(String number) {
        return clientRepository.findByAccountsNumber(number);
    }

    @Override
    public Client getClientByAccount(Account account) {
        return clientRepository.findByAccounts(account);
    }

    @Override
    public Client getClientById (Long id) {
        return clientRepository.findById(id).get();
    }

    @Override
    public Client findByName(String name) {
        return clientRepository.findByName(name);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}

