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
        getRecipeDetails(recipeDTO, newRecipe);
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

    public void getRecipeDetails(RecipeDTO recipeDTO, Recipe recipe) {
        recipe.setName(recipeDTO.getName());
        recipe.setYoutube_url(recipeDTO.getYoutube_url());
        recipe.setCook_time(recipeDTO.getCooktime());
    }

    public void getInstruction(RecipeDTO recipeDTO, Recipe recipe) {
        String instruction = String.join("\n", recipeDTO.getInstruction());
        recipe.setInstruction(instruction);
    }



}
