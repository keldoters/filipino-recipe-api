package org.keldoters.pinoyrecipesapi.repository;

import org.keldoters.pinoyrecipesapi.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
