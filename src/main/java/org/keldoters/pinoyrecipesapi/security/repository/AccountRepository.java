package org.keldoters.pinoyrecipesapi.security.repository;

import org.keldoters.pinoyrecipesapi.security.model.Account;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends PagingAndSortingRepository<Account, UUID> {
    Optional<Account> findAccountByEmail(String email);
}
