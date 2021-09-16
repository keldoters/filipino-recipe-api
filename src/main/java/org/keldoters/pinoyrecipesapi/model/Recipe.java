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
@Table(name = "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "instruction")
    private String instruction;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
                          CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "recipe",
               cascade = {CascadeType.DETACH, CascadeType.MERGE,
                     CascadeType.REFRESH})
    private Set<RecipeIngredient> ingredients;

    @Override
    public String toString() {
        return "id: " + this.id +
                "\ninstruction: " + this.instruction +
                "\ncategory: " + this.category.getName() +
                "\ningredients: " + this.ingredients;
    }

    public void addRecipeIngredient(RecipeIngredient recipeIngredient) {
        if (ingredients == null) {
            ingredients = new HashSet<>();
        }
        ingredients.add(recipeIngredient);
        recipeIngredient.setRecipe(this);
    }




}
