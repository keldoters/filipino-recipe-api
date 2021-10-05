package org.keldoters.pinoyrecipesapi.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor

@Entity
@Table(name = "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "ingredient",
            cascade = {CascadeType.DETACH, CascadeType.MERGE,
                       CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<RecipeIngredient> recipes;

    public Ingredient(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public void addToRecipe(RecipeIngredient recipeIngredient) {
        if (recipes == null) {
            recipes = new HashSet<>();
        }
        recipes.add(recipeIngredient);
        recipeIngredient.setIngredient(this);
    }


}
