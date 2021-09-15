package org.keldoters.pinoyrecipesapi.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.model.RecipeIngredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor

public class RecipeDTO {

    private Long id;
    private String name;
    private String category;
    private List<String> instruction;
    private List<String> ingredients = new ArrayList<>();
    private List<String> measurements = new ArrayList<>();

    public RecipeDTO(Recipe recipe) {
        id = recipe.getId();
        name = recipe.getName();
        category = recipe.getCategory().getName();
        instruction = getInstructionAsList(recipe.getInstruction());
        fillIngredients(recipe.getIngredients());

    }

    public void fillIngredients(Set<RecipeIngredient> recipeIngredients) {
        recipeIngredients.forEach( recipe -> {
            ingredients.add(recipe.getIngredient().getName());
            measurements.add(recipe.getMeasurement());
        });
    }

    public List<String> getInstructionAsList(String instruction) {
        return List.of(instruction.split("\n"));
    }

    public String toString() {
        return
        "id: " + id +
        "\nname: " + name +
        "\ncategory: " + category +
        "\ninstruction: " + instruction +
        "\ningredients: " + ingredients +
        "\nmeasurements: " + measurements;
    }











}
