package org.keldoters.pinoyrecipesapi.dto.util;

import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.model.Category;
import org.keldoters.pinoyrecipesapi.model.Ingredient;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.model.RecipeIngredient;
import org.keldoters.pinoyrecipesapi.repository.CategoryRepository;
import org.keldoters.pinoyrecipesapi.repository.IngredientRepository;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class Converter {

    private RecipeRepository recipeRepository;
    private IngredientRepository ingredientRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public Converter(RecipeRepository recipeRepository,
                     IngredientRepository ingredientRepository,
                     CategoryRepository categoryRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
    }

    public Recipe toDAO(RecipeDTO recipeDTO) {
        Recipe newRecipe = new Recipe();
        newRecipe.setName(recipeDTO.getName());
        newRecipe.setCategory(getCategoryInstance(recipeDTO.getCategory()));
        newRecipe.setInstruction(getInstruction(recipeDTO));
        newRecipe.setIngredients(getIngredientsInstance(recipeDTO, newRecipe));
        return newRecipe;
    }

    public Category getCategoryInstance(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        return category.orElse(new Category(name));
    }

    public Set<RecipeIngredient> getIngredientsInstance(RecipeDTO recipeDTO, Recipe recipe) {
        Set<RecipeIngredient> recipeIngredients = new HashSet<>();
        for (int i = 0; i < recipeDTO.getIngredients().size(); i++) {
            String ingredientName = recipeDTO.getIngredients().get(i);
            String measurement = recipeDTO.getMeasurements().get(i);

            Ingredient ingredient = ingredientRepository.findByNameEagerLy(ingredientName)
                    .orElse(new Ingredient(ingredientName));
            RecipeIngredient measuredIngredient = new RecipeIngredient(measurement);
            //synchronized objects
            ingredient.addToRecipe(measuredIngredient);
            recipe.addRecipeIngredient(measuredIngredient);
            recipeIngredients.add(measuredIngredient);
        }
        return recipeIngredients;
    }

    public String getInstruction(RecipeDTO recipeDTO) {
        return String.join("\n", recipeDTO.getInstruction());
    }



}
