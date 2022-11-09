package com.mindhub.homebanking.Services;


import com.mindhub.homebanking.models.ClientLoan;

import java.util.List;

public interface ClientLoanService {

    public List<ClientLoan> getAllClientLoan();

    public ClientLoan getClientLoanById(Long id);

    public void saveLoan(ClientLoan clientLoan);

}
