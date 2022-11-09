package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}
	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository ){
 		return (args) -> {
			/*Client client1 = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("hola")); //pongo en una variable para poder relacionar en la cuenta despues//
			clientRepository.save(client1);
			Client client2 = new Client("Rick", "Sanchez", "rickysan@gmail.com", passwordEncoder.encode("rickycontra"));
			clientRepository.save(client2);
			Client client3 = new Client("Maria", "Perez", "mariaperez@gmail.com", passwordEncoder.encode("maripriv"));
			clientRepository.save(client3);
			Client admin= new Client("ADMIN", "SI", "admin@gmail.com", passwordEncoder.encode("clave"));
			clientRepository.save(admin);


			Account account1 = new Account("VIN001", AccountType.SAVING, LocalDateTime.now(), 5000.00,client1);//constructor 1//
			accountRepository.save(account1);
			Account account2 = new Account("VIN002",AccountType.SAVING, LocalDateTime.now().plusDays(1), 7500.00, client1); //constructor 2//
			accountRepository.save(account2);
			Account account3 = new Account("VIN003", AccountType.SAVING,LocalDateTime.now(), 10000.00, client2);
			accountRepository.save(account3);
			Account account4 = new Account("VIN004", AccountType.SAVING, LocalDateTime.now(), 10500.00, client3);
			accountRepository.save(account4);

			Transaction transaction1 = new Transaction("Transfer", 1550.00, LocalDateTime.now(), TransactionType.CREDIT, account1);
			transactionRepository.save(transaction1);
			Transaction transaction2 = new Transaction("Mc Donals", 900.00, LocalDateTime.now(), TransactionType.DEBIT, account2);
			transactionRepository.save(transaction2);
			Transaction transaction3 = new Transaction("Steam Store", 5400.00, LocalDateTime.now(), TransactionType.DEBIT, account2);
			transactionRepository.save(transaction3);
			Transaction transaction4 = new Transaction("Epic Games Store", 1099.99, LocalDateTime.now(), TransactionType.DEBIT, account3);
			transactionRepository.save(transaction4);
			Transaction transaction5 = new Transaction("Steam Store", 2999.00, LocalDateTime.now(), TransactionType.DEBIT, account3);
			transactionRepository.save(transaction5);
			Transaction transaction6 = new Transaction("Steam Store", 9900.00, LocalDateTime.now(), TransactionType.CREDIT, account4);
			transactionRepository.save(transaction6);
			Transaction transaction7 = new Transaction("Mc Donalds", 100.00, LocalDateTime.now(), TransactionType.DEBIT, account4);
			transactionRepository.save(transaction7);
			Transaction transaction8 = new Transaction("Steam Store", 3900.00, LocalDateTime.now(), TransactionType.DEBIT, account4);
			transactionRepository.save(transaction8);

			Loan hipotecario = new Loan("Mortgage", 500000.00, List.of(12,24,36,48,60), 0.25);
			Loan personal = new Loan("Personal", 100000.00, List.of(6,12,24), 0.22);
			Loan automotriz = new Loan("Automotive", 300000.00, List.of(6,12,24,36), 0.15);

			loanRepository.save(hipotecario);
			loanRepository.save(personal);
			loanRepository.save(automotriz);


			ClientLoan clientloan1 = new ClientLoan(400.000, 60, client1, hipotecario);
			ClientLoan clientloan2 = new ClientLoan(50.000, 12, client1, personal);
			clientLoanRepository.save(clientloan1);
			clientLoanRepository.save(clientloan2);

			ClientLoan clientloan3 = new ClientLoan(100.000, 24, client2, personal);
			ClientLoan clientloan4 = new ClientLoan(200.000, 36, client2, automotriz);
			clientLoanRepository.save(clientloan3);
			clientLoanRepository.save(clientloan4);


			Card card1 = new Card("3454 6768 8934 8427", CardType.DEBIT, CardColor.GOLD, 551, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1);
			cardRepository.save(card1);
			Card card2 = new Card("5481 8652 7435 8565", CardType.DEBIT, CardColor.TITANIUM, 254, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client1);
			cardRepository.save(card2);
			Card card3 = new Card("9855 2458 5644 8751", CardType.DEBIT, CardColor.SILVER, 448, LocalDateTime.now(), LocalDateTime.now().plusYears(2), client1);
			cardRepository.save(card3);
			Card card4 = new Card("5481 8652 7435 8565", CardType.DEBIT, CardColor.SILVER, 254, LocalDateTime.now(), LocalDateTime.now().plusYears(5), client2);
			cardRepository.save(card4);
			Card card5 = new Card("8954 4872 3512 1547", CardType.CREDIT, CardColor.TITANIUM, 214, LocalDateTime.now(), LocalDateTime.now().plusYears(1), client1);
			cardRepository.save(card5);*/
		 };

	}

}
