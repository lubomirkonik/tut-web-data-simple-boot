package tut.webdata.services;

import javax.persistence.*;
import javax.inject.Inject;

//import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import tut.webdata.domain.Account;
import tut.webdata.repository.AccountRepository;

//@Repository
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
		
//		entityManager.persist(account);
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

//	in JPA repository - AccountRepository:
	
//	@PersistenceContext
//	private EntityManager entityManager;	
	
//	public Account findByEmail(String email) {
//		try {
//			return entityManager.createNamedQuery(Account.FIND_BY_EMAIL, Account.class)
//				.setParameter("email", email)
//				.getSingleResult();
//		} catch (PersistenceException e) {
//			return null;
//		}
//	}
}
