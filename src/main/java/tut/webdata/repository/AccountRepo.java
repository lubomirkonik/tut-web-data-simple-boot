package tut.webdata.repository;

import org.springframework.data.repository.CrudRepository;

import tut.webdata.domain.Account;

public interface AccountRepo extends CrudRepository<Account, Long> {

		Account findByEmail(String email);
}
