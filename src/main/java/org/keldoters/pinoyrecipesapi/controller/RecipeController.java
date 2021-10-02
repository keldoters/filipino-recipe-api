package org.keldoters.pinoyrecipesapi.controller;


import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.keldoters.pinoyrecipesapi.dto.RecipeDTO;
import org.keldoters.pinoyrecipesapi.model.Category;
import org.keldoters.pinoyrecipesapi.security.model.Account;
import org.keldoters.pinoyrecipesapi.security.repository.AccountRepository;
import org.keldoters.pinoyrecipesapi.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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

    @ApiOperation(value = "Search recipe by either name or first letter",
            notes = "Use only one parameter")
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

    @ApiOperation(value = "Find recipe by id",
            notes = "Use numerical value")
    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long recipeId) {
        Optional<RecipeDTO> recipeDTO = recipeService.findById(recipeId);
        Map<String, RecipeDTO> recipeObj = new HashMap<>();
        recipeObj.put("recipe", recipeDTO.orElse(null));
        return new ResponseEntity<>(recipeObj, HttpStatus.OK);
    }

    @ApiOperation(value = "find all categories")
    @GetMapping("/recipe/categories")
    public ResponseEntity<?> getCategories() {
        List<Category> categories = recipeService.findAllCategories();
        return new ResponseEntity<>(Map.of("categories", categories), HttpStatus.OK);
    }

    @ApiOperation(value = "Filter recipe by either ingredient or category",
            notes = "Use only one parameter")
    @GetMapping("/recipe/filter")
    public ResponseEntity<?> filterRecipe(@RequestParam(required = false) String ingredient,
                                          @RequestParam(required = false) String category) {
        if (ingredient != null) {
            List<RecipeDTO> recipeDTOList = recipeService.findByIngredient(ingredient);
            System.out.println(recipeDTOList);
            return new ResponseEntity<>(Map.of("recipes", recipeDTOList), HttpStatus.OK);
        } else if (category != null) {
            List<RecipeDTO> recipeDTOList = recipeService.findByCategory(category);
            return new ResponseEntity<>(Map.of("recipes", recipeDTOList), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Map.of("recipes", new ArrayList<>()), HttpStatus.OK);
        }
    }
    @ApiOperation(value = "Save recipe to the database, requires API key", notes = "API key input should begin with \"Bearer \"")
    @PostMapping("/recipe")
    public ResponseEntity<?> saveRecipe(@RequestBody RecipeDTO recipeDTO) {
        List<RecipeDTO> recipes = recipeService.findRecipesByName(recipeDTO.getName());
        if (recipes.size() > 0) {
            return new ResponseEntity<>(Map.of("error","Recipe already exists"), HttpStatus.CONFLICT);
        }
        RecipeDTO recipe = recipeService.saveRecipe(recipeDTO);
        return new ResponseEntity<>(recipe, HttpStatus.OK);

    }
}
