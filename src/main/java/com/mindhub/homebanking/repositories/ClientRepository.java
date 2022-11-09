package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource /*un repositorio que usa los recursos de rest*/ /*estos metodos ya existen yo los estoy sobreescribiendo*/
public interface ClientRepository extends JpaRepository<Client,Long> { /*long el tipo de dato que va a tener la clave primaria de cliente*/

    public Client findByEmail(String email);
    /*un medio de conexion entre la clase y la base de datos*/

    public Client findByAccountsNumber(String number);

    public Client findByAccounts(Account account);

    public Client findByName(String name);


}
