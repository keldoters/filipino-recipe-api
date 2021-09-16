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
        getCategoryInstance(recipeDTO.getCategory(), newRecipe);
        getInstruction(recipeDTO, newRecipe);
        getIngredientsInstance(recipeDTO, newRecipe);
        return newRecipe;
    }

    public void getCategoryInstance(String name, Recipe recipe) {
        Category category = categoryRepository.findByNameEagerly(name)
                .orElseGet(() -> categoryRepository.save(new Category(name)));
        category.addRecipe(recipe);
    }

    public void getIngredientsInstance(RecipeDTO recipeDTO, Recipe recipe) {
        for (int i = 0; i < recipeDTO.getIngredients().size(); i++) {
            String ingredientName = recipeDTO.getIngredients().get(i);
            String measurement = recipeDTO.getMeasurements().get(i);

            Ingredient ingredient = ingredientRepository.findByNameEagerLy(ingredientName)
                    .orElseGet(() -> new Ingredient(ingredientName));
            RecipeIngredient measuredIngredient = new RecipeIngredient(measurement);
            //synchronized objects
            ingredient.addToRecipe(measuredIngredient);
            recipe.addRecipeIngredient(measuredIngredient);
            ingredientRepository.save(ingredient);
        }

    }

    public void getInstruction(RecipeDTO recipeDTO, Recipe recipe) {
        String instruction = String.join("\n", recipeDTO.getInstruction());
        recipe.setInstruction(instruction);
    }



}
