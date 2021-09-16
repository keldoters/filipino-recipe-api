package org.keldoters.pinoyrecipesapi.service;

import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.dto.util.Converter;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RecipeService {

    private RecipeRepository recipeRepository;
    private Converter converter;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository, Converter converter) {
        this.recipeRepository = recipeRepository;
        this.converter = converter;
    }

    @Transactional
    public void saveRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = converter.toDAO(recipeDTO);
        recipeRepository.save(recipe);
    }


}
