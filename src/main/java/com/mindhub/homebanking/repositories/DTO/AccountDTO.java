package com.mindhub.homebanking.repositories.DTO;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private long id;
    private String number;
    private AccountType type;
    private LocalDateTime createDate;
    private double balance;
    private Set<TransactionDTO> transactions;
    private boolean isActive;






    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber();
        this.type = account.getType();
        this.createDate = account.getCreateDate();
        this.balance = account.getBalance();
        this.transactions = account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toSet());
        this.isActive = account.isActive();

    }

    public boolean isActive() {
        return isActive;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}

