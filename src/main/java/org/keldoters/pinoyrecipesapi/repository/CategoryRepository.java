package org.keldoters.pinoyrecipesapi.repository;

import org.keldoters.pinoyrecipesapi.model.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    @Query("SELECT c FROM Category c LEFT JOIN FETCH c.recipes WHERE c.name = ?1")
    Optional<Category> findByNameEagerly(String name);
}
