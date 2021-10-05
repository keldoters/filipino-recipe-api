package org.keldoters.pinoyrecipesapi.service;

import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.dto.util.Converter;
import org.keldoters.pinoyrecipesapi.model.Category;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.repository.CategoryRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
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
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> findRecipesByFirstLetter(String letter) {
        return recipeRepository.findByFirstLetter(letter)
                .stream()
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<RecipeDTO> findById(Long id) {
        return recipeRepository.findByIdEagerly(id)
                .map(RecipeDTO::new);
    }

    @Transactional
    public RecipeDTO saveRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = recipeRepository.save(converter.toDAO(recipeDTO));
        return new RecipeDTO(recipe);
    }

    public List<Category> findAllCategories() {
        List<Category> categories = new ArrayList<>();
        categoryRepository.findAll()
                .forEach(categories::add);
        return categories;
    }

    public List<RecipeDTO> findByIngredient(String ingredient) {
        return recipeRepository.findAllByIngredient(ingredient)
                .stream()
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

    public List<RecipeDTO> findByCategory(String category) {
        return recipeRepository.findAllByCategory(category)
                .stream()
                .map(RecipeDTO::new)
                .collect(Collectors.toList());
    }

    public RecipeDTO randomRecipe() {
        int qty = (int) recipeRepository.count();
        int randomNumber = new Random().nextInt(qty - 1) + 1;
        Page<Recipe> recipePage = recipeRepository.findAll(PageRequest.of(randomNumber, 1));
        Recipe recipe = recipePage.getContent().get(0);
        return new RecipeDTO(recipe);
    }

}
