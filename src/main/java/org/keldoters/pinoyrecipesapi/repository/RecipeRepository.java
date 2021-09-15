package org.keldoters.pinoyrecipesapi.repository;

import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE r.id = ?1")
    Optional<Recipe> findByIdEagerly(Long id);

    boolean existsByName(String name);

}
