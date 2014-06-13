package tut.webdata.repository;

import org.springframework.data.repository.CrudRepository;

import tut.webdata.domain.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

		Account findByEmail(String email);
}
