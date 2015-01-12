package tut.webdata.services;

import javax.persistence.*;
import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import tut.webdata.domain.Account;
import tut.webdata.repository.AccountRepository;

@Transactional(readOnly = true)
public class AccountService {
	
	private final AccountRepository accountRepository;
	
	public AccountService(final AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}
	
	@Inject
	private PasswordEncoder passwordEncoder;
	
	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		
		accountRepository.save(account);
		
		return account;
	}
	
	public Account findByEmail(String email) {
		try {
			return accountRepository.findByEmail(email);
		} catch (PersistenceException e) {
			return null;
		}
	}
}
