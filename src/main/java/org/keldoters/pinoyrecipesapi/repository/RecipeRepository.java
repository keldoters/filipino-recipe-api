package org.keldoters.pinoyrecipesapi.repository;

import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends PagingAndSortingRepository<Recipe, Long> {

    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE r.id = ?1")
    Optional<Recipe> findByIdEagerly(Long id);

    @Query("SELECT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE r.name = ?1")
    Optional<Recipe> findByNameEagerly(String name);

    //using distinct because without it the query
    //is returning duplicate entities
    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE UPPER(r.name) LIKE CONCAT('%', UPPER(?1), '%')")
    List<Recipe> findByNameContainingIgnoreCase(String name);

    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE UPPER(r.name) LIKE CONCAT(UPPER(?1), '%')")
    List<Recipe> findByFirstLetter(String letter);

    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN r.ingredients i WHERE UPPER(i.ingredient.name) = UPPER(?1)")
    List<Recipe> findAllByIngredient(String ingredient);

    @Query("SELECT DISTINCT r FROM Recipe r LEFT JOIN FETCH r.ingredients WHERE UPPER(r.category.name) = UPPER(?1)")
    List<Recipe> findAllByCategory(String category);




}
