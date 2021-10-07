package org.keldoters.pinoyrecipesapi.security.role;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RoleRepository extends PagingAndSortingRepository<Role, Long> {

    Optional<Role> findRoleByName(String name);
}
