package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;

import java.util.List;

public interface ClientService {

    public Client getClientByEmail(String email);                          /*un medio de conexion entre la clase y la base de datos*/

    public List<Client> getAllClients();

    public Client getClientByAccountsNumber(String number);

    public Client getClientByAccount(Account account);

    public Client getClientById(Long id);

    public Client findByName(String name);

    public void saveClient(Client client);
}
