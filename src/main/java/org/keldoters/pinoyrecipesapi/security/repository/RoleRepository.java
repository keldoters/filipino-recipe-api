package org.keldoters.pinoyrecipesapi.security.repository;

import org.keldoters.pinoyrecipesapi.security.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Optional<Role> findRoleByName(String name);
}
