package org.keldoters.pinoyrecipesapi.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "recipe_ingredient")
public class RecipeIngredient {

    @EmbeddedId
    private RecipeIngredientPK id = new RecipeIngredientPK();

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "measurement")
    private String measurement;

    public RecipeIngredient(String measurement) {
        this.measurement = measurement;
    }

    public String toString() {
        return this.measurement + " " + this.ingredient.getName();
    }

}
