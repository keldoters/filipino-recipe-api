package org.keldoters.pinoyrecipesapi.repository;

import org.keldoters.pinoyrecipesapi.model.Ingredient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IngredientRepository extends PagingAndSortingRepository<Ingredient, Long> {

    boolean existsByName(String name);

    @Query("SELECT i FROM Ingredient i LEFT JOIN FETCH i.recipes WHERE i.name = ?1")
    Optional<Ingredient> findByNameEagerLy(String name);
}
