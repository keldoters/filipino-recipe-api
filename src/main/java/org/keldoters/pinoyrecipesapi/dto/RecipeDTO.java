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
    private String youtubeUrl;
    private String cookTime;
    private List<String> instruction;
    private List<String> ingredients = new ArrayList<>();
    private List<String> measurements = new ArrayList<>();


    public RecipeDTO(String name,
                     String category,
                     String youtubeUrl,
                     String cookTime,
                     String instruction,
                     List<String> ingredients,
                     List<String> measurements) {
        this.name = name;
        this.category = category;
        this.youtubeUrl = youtubeUrl;
        this.cookTime = cookTime;
        this.instruction = getInstructionAsList(instruction);
        this.ingredients = ingredients;
        this.measurements = measurements;
    }

    //convert recipe instance into recipeDTO
    public RecipeDTO(Recipe recipe) {
        id = recipe.getId();
        name = recipe.getName();
        category = recipe.getCategory().getName();
        youtubeUrl = recipe.getYoutubeUrl();
        cookTime = recipe.getCookTime();
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
        "\nyoutube url: " + youtubeUrl +
        "\ncook time: " + cookTime +
        "\ninstruction: " + instruction +
        "\ningredients: " + ingredients +
        "\nmeasurements: " + measurements;
    }

    public static class Builder {

        private String name;
        private String category;
        private String youtube_url;
        private String cooktime;
        private String instruction;
        private List<String> ingredients = new ArrayList<>();
        private List<String> measurements = new ArrayList<>();

        public Builder() {

        }

        public List<String> getInstructionAsList(String instruction) {
            return List.of(instruction.split("\n"));
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setCategory(String category) {
            this.category = category;
            return this;
        }

        public Builder setYoutube_url(String youtube_url) {
            this.youtube_url = youtube_url;
            return this;
        }

        public Builder setCooktime(String cooktime) {
            this.cooktime = cooktime;
            return this;
        }

        public Builder setInstruction(String instruction) {
            this.instruction = instruction;
            return this;
        }

        public Builder setIngredients(List<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder setMeasurements(List<String> measurements) {
            this.measurements = measurements;
            return this;
        }

        public RecipeDTO build() {
            return new RecipeDTO(name, category, youtube_url, cooktime,
                                 instruction, ingredients, measurements);
        }

    }











}
