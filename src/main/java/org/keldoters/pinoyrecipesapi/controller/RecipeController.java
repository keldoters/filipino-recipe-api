package org.keldoters.pinoyrecipesapi.controller;

import com.google.gson.JsonObject;
import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.model.Category;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.xml.catalog.Catalog;
import java.util.*;

@Validated
@RestController
@RequestMapping("/api/v1")
public class RecipeController {

    private RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/search")
    public ResponseEntity<?> searchRecipe(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String letter) {
        if (name != null) {
            List<RecipeDTO> recipeDTOList = recipeService.findRecipesByName(name);
            return new ResponseEntity<>(Map.of("recipes", recipeDTOList), HttpStatus.OK);
        } else if (letter != null && letter.length() == 1) {
            List<RecipeDTO> recipeDTOList = recipeService.findRecipesByFirstLetter(letter);
            return new ResponseEntity<>(Map.of("recipes", recipeDTOList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long recipeId) {
        Optional<RecipeDTO> recipeDTO = recipeService.findById(recipeId);
        Map<String, RecipeDTO> recipeObj = new HashMap<>();
        recipeObj.put("recipe", recipeDTO.orElse(null));
        return new ResponseEntity<>(recipeObj, HttpStatus.OK);
    }

    @GetMapping("/recipe/categories")
    public ResponseEntity<?> getCategories() {
        List<Category> categories = recipeService.findAllCategories();
        return new ResponseEntity<>(Map.of("categories", categories), HttpStatus.OK);
    }

    @GetMapping("/recipe/filter")
    public ResponseEntity<?> filterRecipe(@RequestParam(required = false) String ingredient,
                                          @RequestParam(required = false) String category) {
        if (ingredient != null) {
            List<RecipeDTO> recipeDTOList = recipeService.findByIngredient(ingredient);
            return new ResponseEntity<>(Map.of("recipes", recipeDTOList), HttpStatus.OK);
        } else if (category != null) {
            List<RecipeDTO> recipeDTOList = recipeService.findByCategory(category);
            return new ResponseEntity<>(Map.of("recipes", recipeDTOList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("recipes", new ArrayList<>()), HttpStatus.OK);
        }
    }


}
