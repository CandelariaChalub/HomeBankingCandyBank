package com.mindhub.homebanking.Services;


import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {

    public List<Transaction> getAllTransaction();

    public Transaction getTransactionById(Long id);

    public void saveTransaction(Transaction transaction);

    public List<Transaction> getAllTransactionsByAccountAndDate (Account account, LocalDateTime start, LocalDateTime end);

    public List<Transaction> getAllByAccount (Account account);
}
