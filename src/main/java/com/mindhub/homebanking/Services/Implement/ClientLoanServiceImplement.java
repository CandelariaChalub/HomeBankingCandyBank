package com.mindhub.homebanking.Services.Implement;


import com.mindhub.homebanking.Services.ClientLoanService;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientLoanServiceImplement implements ClientLoanService {

    @Autowired
    private ClientLoanRepository clientLoanRepository;


    @Override
    public List<ClientLoan> getAllClientLoan() {
        return clientLoanRepository.findAll();
    }

    @Override
    public ClientLoan getClientLoanById(Long id) {
        return clientLoanRepository.findById(id).get();
    }

    @Override
    public void saveLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }
}
