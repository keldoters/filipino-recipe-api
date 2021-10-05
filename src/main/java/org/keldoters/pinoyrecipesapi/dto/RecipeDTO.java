package org.keldoters.pinoyrecipesapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.keldoters.pinoyrecipesapi.model.Recipe;
import org.keldoters.pinoyrecipesapi.model.RecipeIngredient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ApiModel
public class RecipeDTO {

    @ApiModelProperty(hidden = true)
    private Long id;

    @ApiModelProperty(example = "Pork Adobo", position = 1)
    @NotBlank
    private String name;
    @NotBlank

    @ApiModelProperty(example = "Pork", position = 2)
    private String category;

    @ApiModelProperty(example = "https://www.youtube.com/watch?v=Ix5Dnud1bl0", position = 3)
    private String youtubeUrl;

    @ApiModelProperty(example = "1 hour", position = 4)
    private String cookTime;
    @Size(min = 1)

    @ApiModelProperty(example = "[\"Combine the pork belly, soy sauce, and garlic\",\"marinade for at least 1 hour\"]", position = 5)
    private List<String> instruction;
    @Size(min = 1)

    @ApiModelProperty(example = "[\"pork belly\",\"soy sauce\"]", position = 6)
    private List<String> ingredients = new ArrayList<>();
    @Size(min = 1)

    @ApiModelProperty(example = "[\"2 lbs\",\"1/2 cup\",\"AddLine3\",\"AddLine4\"]", position = 7)
    private List<String> measurements = new ArrayList<>();

    @ApiModelProperty(example = "https://res.cloudinary.com/dcikpgyxv/image/upload/v1632424519/Pork%20Adobo.jpg", position = 8)
    private String imageUrl;


    public RecipeDTO(String name,
                     String category,
                     String youtubeUrl,
                     String cookTime,
                     String instruction,
                     List<String> ingredients,
                     List<String> measurements,
                     String imageUrl) {
        this.name = name;
        this.category = category;
        this.youtubeUrl = youtubeUrl;
        this.cookTime = cookTime;
        this.instruction = getInstructionAsList(instruction);
        this.ingredients = ingredients;
        this.measurements = measurements;
        this.imageUrl = imageUrl;
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
        imageUrl = recipe.getImageUrl();
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
        "\nmeasurements: " + measurements +
        "\nimage url: " + imageUrl;

    }

    public static class Builder {

        private String name;
        private String category;
        private String youtubeUrl;
        private String cookTime;
        private String instruction;
        private List<String> ingredients = new ArrayList<>();
        private List<String> measurements = new ArrayList<>();
        private String imageUrl;

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

        public Builder setYoutubeUrl(String youtubeUrl) {
            this.youtubeUrl = youtubeUrl;
            return this;
        }

        public Builder setCookTime(String cookTime) {
            this.cookTime = cookTime;
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

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RecipeDTO build() {
            return new RecipeDTO(name, category, youtubeUrl, cookTime,
                                 instruction, ingredients, measurements, imageUrl);
        }

    }

}
