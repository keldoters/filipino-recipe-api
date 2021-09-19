package org.keldoters.pinoyrecipesapi.service;

import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.dto.util.Converter;
import org.keldoters.pinoyrecipesapi.model.Category;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.repository.CategoryRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {

    private CategoryRepository categoryRepository;
    private RecipeRepository recipeRepository;
    private Converter converter;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository,
                         CategoryRepository categoryRepository,
                         Converter converter) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
        this.categoryRepository = categoryRepository;
    }

    public List<RecipeDTO> findRecipesByName(String name) {
        return recipeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(recipe -> new RecipeDTO(recipe))
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> findRecipesByFirstLetter(String letter) {
        return recipeRepository.findByFirstLetter(letter)
                .stream()
                .map(recipe -> new RecipeDTO(recipe))
                .collect(Collectors.toList());
    }

    public Optional<RecipeDTO> findById(Long id) {
        return recipeRepository.findByIdEagerly(id)
                .map(recipe -> new RecipeDTO(recipe));
    }

    @Transactional
    public void saveRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = converter.toDAO(recipeDTO);
        recipeRepository.save(recipe);
    }

    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(category -> categories.add(category));
        return categories;
    }

    public List<RecipeDTO> findByIngredient(String ingredient) {
        return recipeRepository.findByIngredient(ingredient)
                .stream()
                .map(recipe -> new RecipeDTO(recipe))
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> findByCategory(String ingredient) {
        return recipeRepository.findByCategory(ingredient)
                .stream()
                .map(recipe -> new RecipeDTO(recipe))
                .collect(Collectors.toList());
    }


}
