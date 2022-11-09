package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.Account;

import java.util.List;

public interface AccountService {

    public Account getAccountByNumber(String number);

    public List<Account> getAllAccounts();

    public Account getAccountById(Long id);

    public void saveAccount(Account account);
}
