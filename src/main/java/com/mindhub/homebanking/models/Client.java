package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity /*con la clase cliente genera una tabla en la base de datos*/
public class Client {
    @Id /*anota una propiedad clave primaria*/
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") /*me genera el valor*/
    @GenericGenerator(name = "native", strategy = "native") /*se fija en la base que no exista el valor para que no explote la base de datos*/
    private long id;
    private String name, lastName, email, password;
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) /*va a cargar todos los datos*/
    private Set<Account> accounts = new HashSet<>();

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) /* te da acceso inmediato a esos datos sin necesidad de que se los tengas que pedir*/
    private Set<ClientLoan> clientLoans = new HashSet<>(); /*reserve un espacio en la memoria*/

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();


    public Client(){

    }

    public Client(String name, String lastName, String email, String password) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Client(String name, String lastName, String email) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
    }


    public long getId() {
        return id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }
    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<ClientLoan> getClientLoans (){
        return clientLoans;
    }
}
